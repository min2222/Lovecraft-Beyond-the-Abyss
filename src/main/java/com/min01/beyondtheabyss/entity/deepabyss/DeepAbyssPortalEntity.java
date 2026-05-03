package com.min01.beyondtheabyss.entity.deepabyss;

import java.util.List;

import com.min01.beyondtheabyss.item.BTAItems;
import com.min01.beyondtheabyss.misc.SmoothAnimationState;
import com.min01.beyondtheabyss.util.BTAUtil;
import com.min01.beyondtheabyss.world.BTAWorlds;
import com.min01.solomonlib.multipart.CompoundOrientedBox;
import com.min01.solomonlib.multipart.EntityBounds;
import com.min01.solomonlib.multipart.EntityPartBuilder;
import com.min01.solomonlib.multipart.IMultipart;
import com.min01.solomonlib.util.SolomonUtil;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkHooks;

public class DeepAbyssPortalEntity extends Entity implements IMultipart
{
	public static final EntityDataAccessor<Integer> ANIMATION_STATE = SynchedEntityData.defineId(DeepAbyssPortalEntity.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<Integer> ANIMATION_TICK = SynchedEntityData.defineId(DeepAbyssPortalEntity.class, EntityDataSerializers.INT);
	
	public final EntityPartBuilder<DeepAbyssPortalEntity> partBuilder;
	
	public final SmoothAnimationState idleAnimationState = new SmoothAnimationState();
	public final SmoothAnimationState openAnimationState = new SmoothAnimationState();
	
	public DeepAbyssPortalEntity(EntityType<?> pEntityType, Level pLevel)
	{
		super(pEntityType, pLevel);
		this.noCulling = true;
		this.partBuilder = new EntityPartBuilder<>(this);
	}

	@Override
	protected void defineSynchedData() 
	{
		this.entityData.define(ANIMATION_STATE, 0);
		this.entityData.define(ANIMATION_TICK, 0);
	}

	@Override
	public EntityBounds getBounds()
	{
		return this.partBuilder.hitbox;
	}

	@Override
	public CompoundOrientedBox getCompoundBoundingBox(AABB bounds) 
	{
		return this.partBuilder.hitbox.getBox(bounds);
	}

	@Override
	public EntityPartBuilder<?> getPartBuilder()
	{
		return this.partBuilder;
	}
	
    @Override
    public void tick() 
    {
		super.tick();
		if(this.partBuilder != null)
		{
			this.partBuilder.tick(1.0F);
		}
		
		if(this.level.isClientSide)
		{
			this.idleAnimationState.animateWhen(this.getAnimationState() == 1, this.tickCount);
			this.openAnimationState.animateWhen(this.getAnimationState() == 1, this.tickCount);
		}
		
		if(this.getAnimationState() == 1)
		{
			List<LivingEntity> list = this.level.getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().inflate(1.5F));
			list.forEach(t -> 
			{
				if(t.getServer() != null)
				{
					String part = SolomonUtil.getCollidingMultiPart(this, t);
					if(part != null && part.equals("plate"))
					{
						if(t.distanceToSqr(this) <= 6.0F)
						{
							BTAUtil.teleportEntityToDimension(t, t.getServer().getLevel(BTAWorlds.DEEP_ABYSS), BlockPos.containing(0, 100, 0));
						}
					}
				}
			});
		}
    }
    
    @Override
    public List<String> getCollidePart() 
    {
    	return List.of("door1", "door2", "door3", "plate", "gem", "pipe1", "pipe2", "pipe3", "pipe4", "edge");
    }
    
    @Override
    public boolean isPickable() 
    {
    	return true;
    }
    
    @Override
    public InteractionResult interact(Player pPlayer, InteractionHand pHand) 
    {
    	ItemStack stack = pPlayer.getItemInHand(pHand);
    	if(stack.is(BTAItems.CLAM_OF_GUIDANCE.get()))
    	{
    		if(this.getAnimationState() == 0)
    		{
        		if(!pPlayer.getAbilities().instabuild)
        		{
        			stack.shrink(1);
        		}
        		this.setAnimationState(1);
    		}
    		return InteractionResult.SUCCESS;
    	}
    	return super.interact(pPlayer, pHand);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag pCompound) 
    {
    	this.setAnimationTick(pCompound.getInt("AnimationTick"));
    	this.setAnimationState(pCompound.getInt("AnimationState"));
    }
    
    @Override
    public void addAdditionalSaveData(CompoundTag pCompound) 
    {
    	pCompound.putInt("AnimationTick", this.getAnimationTick());
    	pCompound.putInt("AnimationState", this.getAnimationState());
    }
	
	@Override
	public Packet<ClientGamePacketListener> getAddEntityPacket() 
	{
		return NetworkHooks.getEntitySpawningPacket(this);
	}
	
	public Vec3 getAnchorPos(int index)
	{
		BlockPos blockPos = this.blockPosition();
		int dist = 5;
		if(index == 0)
		{
			blockPos = blockPos.offset(dist, 0, dist);
		}
		if(index == 1)
		{
			blockPos = blockPos.offset(-dist, 0, dist);
		}
		if(index == 2)
		{
			blockPos = blockPos.offset(dist, 0, -dist);
		}
		if(index == 3)
		{
			blockPos = blockPos.offset(-dist, 0, -dist);
		}
		return Vec3.atCenterOf(blockPos.above());
	}
	
	public Vec3 getTargetPos(int index)
	{
		BlockPos blockPos = this.blockPosition();
		int dist = 20;
		if(index == 0)
		{
			blockPos = blockPos.offset(dist, 0, dist);
		}
		if(index == 1)
		{
			blockPos = blockPos.offset(-dist, 0, dist);
		}
		if(index == 2)
		{
			blockPos = blockPos.offset(dist, 0, -dist);
		}
		if(index == 3)
		{
			blockPos = blockPos.offset(-dist, 0, -dist);
		}
		blockPos = BTAUtil.getGroundPos(this.level, blockPos.getX(), blockPos.getY(), blockPos.getZ());
		return Vec3.atCenterOf(blockPos.above());
	}
    
    public void setAnimationTick(int value)
    {
        this.entityData.set(ANIMATION_TICK, value);
    }
    
    public int getAnimationTick()
    {
        return this.entityData.get(ANIMATION_TICK);
    }
    
    public void setAnimationState(int value)
    {
        this.entityData.set(ANIMATION_STATE, value);
    }
    
    public int getAnimationState()
    {
        return this.entityData.get(ANIMATION_STATE);
    }
}
