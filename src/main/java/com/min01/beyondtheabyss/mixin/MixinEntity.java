package com.min01.beyondtheabyss.mixin;

import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;

import javax.annotation.Nullable;

import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.google.common.collect.ImmutableList;
import com.min01.beyondtheabyss.entity.deepabyss.EntitySubmarine;
import com.min01.beyondtheabyss.item.deepabyss.FlashlightItem;
import com.min01.beyondtheabyss.lights.DynamicLights;
import com.min01.beyondtheabyss.lights.IDynamicLight;
import com.min01.beyondtheabyss.multipart.CompoundOrientedBox;
import com.min01.beyondtheabyss.multipart.IMultipart;
import com.min01.beyondtheabyss.multipart.OrientedBox;
import com.min01.beyondtheabyss.util.BTAClientUtil;
import com.min01.beyondtheabyss.util.BTAUtil;
import com.min01.beyondtheabyss.util.DeepAbyssUtil;
import com.min01.beyondtheabyss.util.MirroredCityUtil;
import com.min01.beyondtheabyss.world.BTAWorlds;
import com.min01.gravityapi.api.GravityChangerAPI;
import com.min01.gravityapi.capabilities.GravityCapabilityImpl;

import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.SectionPos;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.fluids.FluidType;

@Mixin(Entity.class)
public abstract class MixinEntity implements IDynamicLight
{
	@Unique
	protected int luminance = 0;
	
	@Unique
	private int lastLuminance = 0;
	
	@Unique
	private double prevX;
	
	@Unique
	private double prevY;
	
	@Unique
	private double prevZ;
	
	@Unique
	private LongOpenHashSet trackedLitChunkPos = new LongOpenHashSet();

	@Inject(method = "tick", at = @At("TAIL"))
	private void tickTail(CallbackInfo ci) 
	{
		if(Entity.class.cast(this).level.isClientSide)
		{
			if(Entity.class.cast(this).isRemoved()) 
			{
				this.setBTADynamicLightEnabled(false);
			}
			else
			{
				if(this.shouldUpdateDynamicLight())
				{
					this.dynamicLightTick();
					DynamicLights.updateTracking(this);
				}
				else
				{
					this.setBTADynamicLightEnabled(false);
				}
			}
		}
		if(MirroredCityUtil.isUpsideDown(Entity.class.cast(this)))
		{
			GravityCapabilityImpl cap = GravityChangerAPI.getGravityComponent(Entity.class.cast(this));
			cap.applyGravityDirectionEffect(Direction.UP, null, Double.MAX_VALUE);
		}
		BTAUtil.updateGravity(Entity.class.cast(this));
	}
	
	@Inject(method = "checkBelowWorld", at = @At("HEAD"), cancellable = true)
	private void checkBelowWorld(CallbackInfo ci) 
	{
		if(Entity.class.cast(this).level.dimension() == BTAWorlds.OUTER_SPACE)
		{
			ci.cancel();
		}
	}

	@Inject(method = "remove", at = @At("TAIL"))
	private void remove(CallbackInfo ci) 
	{
		if(Entity.class.cast(this).level.isClientSide)
		{
			this.setBTADynamicLightEnabled(false);
		}
	}

	@Override
	public double getDynamicLightX() 
	{
		return this.getLightPos().x;
	}

	@Override
	public double getDynamicLightY() 
	{
		return this.getLightPos().y;
	}

	@Override
	public double getDynamicLightZ() 
	{
		return this.getLightPos().z;
	}
	
	@Unique
	public Vec3 getLightPos()
	{
		if(Entity.class.cast(this) instanceof Player player)
		{
	    	Vec3 lightPos = BTAUtil.getLookPos(player.getRotationVector(), player.getEyePosition(), 0.0F, 0.0F, 8.0F);
	    	HitResult result = player.level.clip(new ClipContext(player.getEyePosition(), lightPos, ClipContext.Block.OUTLINE, ClipContext.Fluid.NONE, player));
	    	return result.getLocation();
		}
		if(Entity.class.cast(this) instanceof EntitySubmarine submarine)
		{
	    	Vec3 lightPos = BTAUtil.getLookPos(submarine.getRotationVector(), submarine.position(), 0.0F, 2.0F, 6.0F);
	    	HitResult result = submarine.level.clip(new ClipContext(submarine.position(), lightPos, ClipContext.Block.OUTLINE, ClipContext.Fluid.NONE, submarine));
	    	return result.getLocation();
		}
		return Vec3.ZERO;
	}

	@Override
	public Level getDynamicLightLevel() 
	{
		return Entity.class.cast(this).level;
	}

	@Override
	public void resetDynamicLight() 
	{
		this.lastLuminance = 0;
		this.luminance = 0;
	}

	@Override
	public boolean shouldUpdateDynamicLight()
	{
		if(Entity.class.cast(this) instanceof Player player)
		{
			ItemStack mainHandStack = player.getMainHandItem();
			ItemStack offHandStack = player.getOffhandItem();
			boolean flag1 = mainHandStack.getItem() instanceof FlashlightItem && FlashlightItem.isOn(mainHandStack);
			boolean flag2 = offHandStack.getItem() instanceof FlashlightItem && FlashlightItem.isOn(offHandStack);
			return flag1 || flag2;
		}
		if(Entity.class.cast(this) instanceof EntitySubmarine submarine)
		{
			return submarine.getFirstPassenger() != null;
		}
		return false;
	}

	@Override
	public void dynamicLightTick() 
	{
		this.luminance = 0;
		int luminance = 15;
		if(luminance > this.luminance)
		{
			this.luminance = luminance;
		}
	}

	@Override
	public int getLuminance()
	{
		return this.luminance;
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public boolean updateDynamicLight(@NotNull LevelRenderer renderer) 
	{
		if(!this.shouldUpdateDynamicLight())
		{
			return false;
		}
		double deltaX = Entity.class.cast(this).getX() - this.prevX;
		double deltaY = Entity.class.cast(this).getY() - this.prevY;
		double deltaZ = Entity.class.cast(this).getZ() - this.prevZ;

		int luminance = this.getLuminance();

		if(Math.abs(deltaX) > 0.1D || Math.abs(deltaY) > 0.1D || Math.abs(deltaZ) > 0.1D || luminance != this.lastLuminance) 
		{
			this.prevX = Entity.class.cast(this).getX();
			this.prevY = Entity.class.cast(this).getY();
			this.prevZ = Entity.class.cast(this).getZ();
			this.lastLuminance = luminance;

			LongOpenHashSet newPos = new LongOpenHashSet();

			if(luminance > 0) 
			{
				ChunkPos entityChunkPos = Entity.class.cast(this).chunkPosition();
				BlockPos.MutableBlockPos chunkPos = new BlockPos.MutableBlockPos(entityChunkPos.x, SectionPos.blockToSectionCoord(Entity.class.cast(this).getEyeY()), entityChunkPos.z);

				DynamicLights.scheduleChunkRebuild(renderer, chunkPos);
				DynamicLights.updateTrackedChunks(chunkPos, this.trackedLitChunkPos, newPos);

				Direction directionX = (Entity.class.cast(this).getOnPos().getX() & 15) >= 8 ? Direction.EAST : Direction.WEST;
				Direction directionY = ((int) Mth.floor(Entity.class.cast(this).getEyeY()) & 15) >= 8 ? Direction.UP : Direction.DOWN;
				Direction directionZ = (Entity.class.cast(this).getOnPos().getZ() & 15) >= 8 ? Direction.SOUTH : Direction.NORTH;

				for(int i = 0; i < 7; i++) 
				{
					if(i % 4 == 0) 
					{
						chunkPos.move(directionX);
					} 
					else if(i % 4 == 1) 
					{
						chunkPos.move(directionZ);
					}
					else if(i % 4 == 2) 
					{
						chunkPos.move(directionX.getOpposite());
					} 
					else 
					{
						chunkPos.move(directionZ.getOpposite());
						chunkPos.move(directionY);
					}
					DynamicLights.scheduleChunkRebuild(renderer, chunkPos);
					DynamicLights.updateTrackedChunks(chunkPos, this.trackedLitChunkPos, newPos);
				}
			}

			this.scheduleTrackedChunksRebuild(renderer);
			this.trackedLitChunkPos = newPos;
			return true;
		}
		return false;
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public void scheduleTrackedChunksRebuild(@NotNull LevelRenderer renderer)
	{
		if(BTAClientUtil.MC.level == Entity.class.cast(this).level)
		{
			for(long pos : this.trackedLitChunkPos)
			{
				DynamicLights.scheduleChunkRebuild(renderer, pos);
			}
		}
	}
	
    @Inject(method = "getBoundingBox", at = @At("RETURN"), cancellable = true)
    private void getBoundingBox(CallbackInfoReturnable<AABB> cir)
    {
        if(Entity.class.cast(this) instanceof IMultipart multipart)
        {
            cir.setReturnValue(multipart.getCompoundBoundingBox(cir.getReturnValue()));
        }
    }
    
    @Inject(method = "tick", at = @At("HEAD"), cancellable = true)
    private void tick(CallbackInfo ci)
    {
    	Entity entity = Entity.class.cast(this);
		if(entity instanceof ItemEntity item)
		{
			if(item.level.dimension() == BTAWorlds.DEEP_ABYSS)
			{
				item.setDeltaMovement(item.getDeltaMovement().subtract(0, 0.01F, 0));
			}
		}
    }

    @Inject(method = "isInWater", at = @At("HEAD"), cancellable = true)
    private void isInWater(CallbackInfoReturnable<Boolean> cir)
    {
    	if(Entity.class.cast(this) instanceof LivingEntity living)
    	{
    		if(BTAUtil.canSwimInAir(living))
    		{
    			cir.setReturnValue(true);
    		}
    		if(DeepAbyssUtil.isInsideSubmarine(living))
    		{
    			cir.setReturnValue(false);
    		}
    	}
    }
    
    @Inject(method = "getEyeInFluidType", at = @At("TAIL"), cancellable = true, remap = false)
    private void getEyeInFluidType(CallbackInfoReturnable<FluidType> cir)
    {
    	if(Entity.class.cast(this) instanceof LivingEntity living)
    	{
    		if(BTAUtil.canSwimInAir(living))
    		{
    			cir.setReturnValue(ForgeMod.WATER_TYPE.get());
    		}
    		if(DeepAbyssUtil.isInsideSubmarine(living))
    		{
    			cir.setReturnValue(ForgeMod.EMPTY_TYPE.get());
    		}
    	}
    }

    @ModifyVariable(method = "collide", at = @At("HEAD"), argsOnly = true)
    private Vec3 collide(Vec3 originalMovement) 
    {
        Entity entity = Entity.class.cast(this);
        AABB aabb = entity.getBoundingBox();
        List<OrientedBox> obbList = this.getOBBEntityCollisions(entity.level(), entity, aabb.expandTowards(originalMovement));
        if(obbList.isEmpty())
        {
            return originalMovement;
        }
        return this.collideWithOrientedBoxes(originalMovement, aabb, obbList);
    }

    private Vec3 collideWithOrientedBoxes(Vec3 movement, AABB entityAABB, List<OrientedBox> obbs)
    {
        if(obbs.isEmpty() || movement.equals(Vec3.ZERO)) 
        {
            return movement;
        }

        double moveX = movement.x;
        double moveY = movement.y;
        double moveZ = movement.z;
        AABB currentAABB = entityAABB;

        if(moveY != 0.0D) 
        {
            for(OrientedBox obb : obbs) 
            {
                moveY = obb.collide(Direction.Axis.Y, currentAABB, moveY);
            }
            if(Math.abs(moveY) > 1.0E-7D) 
            {
                currentAABB = currentAABB.move(0.0D, moveY, 0.0D);
            }
        }

        boolean checkZFirst = Math.abs(moveX) < Math.abs(moveZ);
        if(checkZFirst && moveZ != 0.0D) 
        {
            for(OrientedBox obb : obbs)
            {
                moveZ = obb.collide(Direction.Axis.Z, currentAABB, moveZ);
            }
            if(Math.abs(moveZ) > 1.0E-7D) 
            {
                currentAABB = currentAABB.move(0.0D, 0.0D, moveZ);
            }
        }

        if(moveX != 0.0D) 
        {
            for(OrientedBox obb : obbs)
            {
                moveX = obb.collide(Direction.Axis.X, currentAABB, moveX);
            }
            if(Math.abs(moveX) > 1.0E-7D) 
            {
                currentAABB = currentAABB.move(moveX, 0.0D, 0.0D);
            }
        }

        if(!checkZFirst && moveZ != 0.0D) 
        {
            for(OrientedBox obb : obbs) 
            {
                moveZ = obb.collide(Direction.Axis.Z, currentAABB, moveZ);
            }
        }

        return new Vec3(moveX, moveY, moveZ);
    }
    
    private List<OrientedBox> getOBBEntityCollisions(Level level, @Nullable Entity p_186451_, AABB p_186452_) 
    {
    	if(p_186452_.getSize() < 1.0E-7D)
        {
        	return List.of();
        } 
        else 
        {
        	Predicate<Entity> predicate = EntitySelector.NO_SPECTATORS.and(t -> t instanceof IMultipart multipart && !multipart.getCollidePart().isEmpty());
        	if(p_186451_ != null)
        	{
        		predicate = predicate.and(t -> !p_186451_.isPassengerOfSameVehicle(t));
        	}
        	List<Entity> list = level.getEntities(p_186451_, p_186452_.inflate(1.0E-7D), predicate);
        	if(list.isEmpty())
        	{
        		return List.of();
        	}
        	else
        	{
        		ImmutableList.Builder<OrientedBox> builder = ImmutableList.builderWithExpectedSize(list.size());
        		for(Entity entity : list) 
        		{
        			if(entity.getBoundingBox() instanceof CompoundOrientedBox compoundBox)
        			{
        				Collection<OrientedBox> boxes = compoundBox.boxes;
        				boxes.removeIf(t -> !t.collide);
        				builder.addAll(boxes);
        			}
        		}
        		return builder.build();
        	}
        }
    }
}
