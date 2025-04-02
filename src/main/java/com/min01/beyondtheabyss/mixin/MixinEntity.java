package com.min01.beyondtheabyss.mixin;

import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.min01.beyondtheabyss.effect.BTAEffects;
import com.min01.beyondtheabyss.item.deepabyss.FlashlightItem;
import com.min01.beyondtheabyss.lights.DynamicLights;
import com.min01.beyondtheabyss.lights.IDynamicLight;
import com.min01.beyondtheabyss.multipart.IMultipart;
import com.min01.beyondtheabyss.util.BTAClientUtil;
import com.min01.beyondtheabyss.util.BTAUtil;
import com.min01.beyondtheabyss.world.BTAWorlds;

import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.SectionPos;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
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
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.fluids.FluidType;

@Mixin(Entity.class)
public abstract class MixinEntity implements IDynamicLight
{
	@Shadow
	public Level level;

	@Shadow
	public abstract double getX();

	@Shadow
	public abstract double getEyeY();

	@Shadow
	public abstract double getZ();

	@Shadow
	public abstract double getY();

	@Shadow
	public abstract BlockPos getOnPos();

	@Shadow
	public abstract boolean isRemoved();

	@Shadow
	public abstract ChunkPos chunkPosition();

	@Unique
	protected int luminance = 0;
	
	@Unique
	private int lastLuminance = 0;
	
	@Unique
	private long slastUpdate = 0;
	
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
		if(this.level.isClientSide)
		{
			if(this.isRemoved()) 
			{
				this.setDynamicLightEnabled(false);
			}
			else
			{
				if(Entity.class.cast(this) instanceof Player player)
				{
					ItemStack mainHandStack = player.getMainHandItem();
					ItemStack offHandStack = player.getOffhandItem();
					boolean flag1 = !mainHandStack.isEmpty() && mainHandStack.getItem() instanceof FlashlightItem && FlashlightItem.isOn(mainHandStack);
					boolean flag2 = !offHandStack.isEmpty() && offHandStack.getItem() instanceof FlashlightItem && FlashlightItem.isOn(offHandStack);
					if(flag1 || flag2)
					{
						this.dynamicLightTick();
						DynamicLights.updateTracking(this);
					}
					else
					{
						this.setDynamicLightEnabled(false);
					}
				}
			}
		}
	}

	@Inject(method = "remove", at = @At("TAIL"))
	private void remove(CallbackInfo ci) 
	{
		if(this.level.isClientSide)
		{
			this.setDynamicLightEnabled(false);
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
		Entity entity = Entity.class.cast(this);
    	Vec3 lightPos = BTAUtil.getLookPos(entity.getXRot(), entity.getYRot(), 0, 8.0F);
    	HitResult result = entity.level.clip(new ClipContext(entity.getEyePosition(), entity.getEyePosition().add(lightPos), ClipContext.Block.OUTLINE, ClipContext.Fluid.NONE, entity));
    	return result.getLocation();
	}

	@Override
	public Level getDynamicLightLevel() 
	{
		return this.level;
	}

	@Override
	public void resetDynamicLight() 
	{
		this.lastLuminance = 0;
	}

	@Override
	public boolean shouldUpdateDynamicLight()
	{
		if(Entity.class.cast(this) instanceof Player player)
		{
			ItemStack mainHandStack = player.getMainHandItem();
			ItemStack offHandStack = player.getOffhandItem();
			boolean flag1 = !mainHandStack.isEmpty() && mainHandStack.getItem() instanceof FlashlightItem && FlashlightItem.isOn(mainHandStack);
			boolean flag2 = !offHandStack.isEmpty() && offHandStack.getItem() instanceof FlashlightItem && FlashlightItem.isOn(offHandStack);
			return flag1 || flag2;
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
	public boolean updateDynamicLight(@NotNull LevelRenderer renderer) 
	{
		if(!this.shouldUpdateDynamicLight())
		{
			return false;
		}
		double deltaX = this.getX() - this.prevX;
		double deltaY = this.getY() - this.prevY;
		double deltaZ = this.getZ() - this.prevZ;

		int luminance = this.getLuminance();

		if(Math.abs(deltaX) > 0.1D || Math.abs(deltaY) > 0.1D || Math.abs(deltaZ) > 0.1D || luminance != this.lastLuminance) 
		{
			this.prevX = this.getX();
			this.prevY = this.getY();
			this.prevZ = this.getZ();
			this.lastLuminance = luminance;

			var newPos = new LongOpenHashSet();

			if(luminance > 0) 
			{
				var entityChunkPos = this.chunkPosition();
				var chunkPos = new BlockPos.MutableBlockPos(entityChunkPos.x, SectionPos.blockToSectionCoord(this.getEyeY()), entityChunkPos.z);

				DynamicLights.scheduleChunkRebuild(renderer, chunkPos);
				DynamicLights.updateTrackedChunks(chunkPos, this.trackedLitChunkPos, newPos);

				var directionX = (this.getOnPos().getX() & 15) >= 8 ? Direction.EAST : Direction.WEST;
				var directionY = ((int) Mth.floor(this.getEyeY()) & 15) >= 8 ? Direction.UP : Direction.DOWN;
				var directionZ = (this.getOnPos().getZ() & 15) >= 8 ? Direction.SOUTH : Direction.NORTH;

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
					else if(i % 4 == 2) {
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
	public void scheduleTrackedChunksRebuild(@NotNull LevelRenderer renderer)
	{
		if(BTAClientUtil.MC.level == this.level)
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
		if(Entity.class.cast(this) instanceof ItemEntity item)
		{
			if(item.level.dimension() == BTAWorlds.DEEP_ABYSS)
			{
				item.setDeltaMovement(item.getDeltaMovement().subtract(0, 0.01F, 0));
			}
		}
    }
    
    @Inject(method = "setPosRaw", at = @At("TAIL"))
    private void setPosRaw(double x, double y, double z, CallbackInfo ci)
    {
        if(Entity.class.cast(this) instanceof IMultipart multipart)
        {
        	multipart.onSetPos(x, y, z);
        }
    }

    @Inject(method = "isInWater", at = @At("TAIL"), cancellable = true)
    private void isInWater(CallbackInfoReturnable<Boolean> cir)
    {
    	if(Entity.class.cast(this) instanceof LivingEntity living)
    	{
    		if(living.hasEffect(BTAEffects.AIR_SWIM.get()))
    		{
    			cir.setReturnValue(true);
    		}
    	}
    }
    
    @Inject(method = "getEyeInFluidType", at = @At("TAIL"), cancellable = true, remap = false)
    private void getEyeInFluidType(CallbackInfoReturnable<FluidType> cir)
    {
    	if(Entity.class.cast(this) instanceof LivingEntity living)
    	{
    		if(living.hasEffect(BTAEffects.AIR_SWIM.get()))
    		{
    			cir.setReturnValue(ForgeMod.WATER_TYPE.get());
    		}
    	}
    }
}
