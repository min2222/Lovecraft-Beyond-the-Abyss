package com.min01.beyondtheabyss.capabilities;

import java.util.List;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.min01.beyondtheabyss.item.BTAItems;
import com.min01.beyondtheabyss.item.animation.IAnimatableItem;
import com.min01.beyondtheabyss.item.deepabyss.SkeletalGunbladeItem;
import com.min01.beyondtheabyss.misc.Laser;
import com.min01.beyondtheabyss.misc.Laser.LaserHitResult;
import com.min01.beyondtheabyss.misc.SmoothAnimationState;
import com.min01.beyondtheabyss.network.BTANetwork;
import com.min01.beyondtheabyss.network.UpdatePlayerAnimationPacket;
import com.min01.beyondtheabyss.util.BTAUtil;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.network.PacketDistributor;

public class PlayerAnimationCapabilityImpl implements IPlayerAnimationCapability
{
	public static final Capability<IPlayerAnimationCapability> PLAYER_ANIMATION = CapabilityManager.get(new CapabilityToken<>() {});
	
	private int animationTick;
	private int animationState;
	private int prevAnimationState;
	
	public final SmoothAnimationState shotgunFireAnimationState = new SmoothAnimationState();
	public final SmoothAnimationState shotgunHoldAnimationState = new SmoothAnimationState();
	public final SmoothAnimationState shotgunRunningAnimationState = new SmoothAnimationState();
	public final SmoothAnimationState gunbladeChargeAnimationState = new SmoothAnimationState();
	public final SmoothAnimationState gunbladeShootAnimationState = new SmoothAnimationState();
	public final SmoothAnimationState gunbladeSwingAnimationState = new SmoothAnimationState();
	
	private final Entity entity;
	private final Laser laser = new Laser();
	
	public PlayerAnimationCapabilityImpl(Entity entity) 
	{
		this.entity = entity;
	}
	
	@Override
	public CompoundTag serializeNBT() 
	{
		CompoundTag nbt = new CompoundTag();
		nbt.putInt("AnimationTick", this.animationTick);
		nbt.putInt("AnimationState", this.animationState);
		nbt.putInt("PrevAnimationState", this.prevAnimationState);
		return nbt;
	}

	@Override
	public void deserializeNBT(CompoundTag nbt)
	{
		this.setAnimationTick(nbt.getInt("AnimationTick"));
		this.setAnimationState(nbt.getInt("AnimationState"));
		this.setPrevAnimationState(nbt.getInt("PrevAnimationState"));
	}

	@Override
	public void tick(LivingEntity entity) 
	{
		ItemStack stack = entity.getItemInHand(entity.getUsedItemHand());
		if(entity.isHolding(BTAItems.SKELETAL_GUNBLADE.get()))
		{
			if(this.getAnimationState() == 4)
			{
	        	Vec3 startPos = BTAUtil.getLookPos(new Vec2(entity.getXRot(), entity.getYHeadRot()), entity.getEyePosition(), 0.0F, -0.25F, 0.5F);
				Vec3 lookPos = BTAUtil.getLookPos(new Vec2(entity.getXRot(), entity.getYHeadRot()), startPos, 0.0F, 0.0F, 50.0F);
				LaserHitResult laserHit = this.laser.raytrace(entity.level, startPos, lookPos, 0.375F, t -> t != entity && !t.isAlliedTo(entity), entity);
	            if(entity.level.isClientSide)
	            {
	                SkeletalGunbladeItem.setLaserLength(stack, this.laser.getLaserLength());
	            }
	            laserHit.entities.forEach(t -> 
	            {
	            	t.hurt(entity.damageSources().indirectMagic(entity, entity), 6.0F);
	            });
			}
			if(this.getAnimationState() == 5 && this.getAnimationTick() == 20)
			{
				float size = 1.5F;
				Vec3 lookPos = BTAUtil.getLookPos(new Vec2(entity.getXRot(), entity.getYHeadRot()), entity.getEyePosition(), 0, 0, 1.5F);
				AABB aabb = new AABB(-size, -size, -size, size, size, size).move(lookPos);
				List<LivingEntity> list = entity.level.getEntitiesOfClass(LivingEntity.class, aabb, t -> t != entity && !t.isAlliedTo(entity));
				list.forEach(t -> 
				{
					t.hurt(entity.damageSources().mobAttack(entity), 8.0F);
				});
			}
		}
		if(this.getAnimationTick() > 0)
		{
			if(!(entity.getItemInHand(entity.getUsedItemHand()).getItem() instanceof IAnimatableItem))
			{
				this.setAnimationTick(0);
			}
			this.setAnimationTick(this.getAnimationTick() - 1);
		}
		else
		{
			if(entity.level.isClientSide)
			{
				if(this.getAnimationState() == 4 && entity.isHolding(BTAItems.SKELETAL_GUNBLADE.get())) 
				{
					SkeletalGunbladeItem.stop(stack, entity);
				}
			}
			this.setAnimationState(0);
			this.setAnimationTick(0);
		}
		if(entity.level.isClientSide)
		{
			this.shotgunFireAnimationState.updateWhen(this.getAnimationState() == 1 && entity.isHolding(BTAItems.TOOTH_SHOTGUN.get()), entity.tickCount);
			this.shotgunHoldAnimationState.updateWhen(this.getAnimationState() == 0 && entity.isHolding(BTAItems.TOOTH_SHOTGUN.get()) && !entity.isSprinting(), entity.tickCount);
			this.shotgunRunningAnimationState.updateWhen(this.getAnimationState() == 0 && entity.isHolding(BTAItems.TOOTH_SHOTGUN.get()) && entity.isSprinting(), entity.tickCount);
			this.gunbladeChargeAnimationState.updateWhen(this.getAnimationState() == 3 && entity.isUsingItem() && entity.isHolding(BTAItems.SKELETAL_GUNBLADE.get()), entity.tickCount);
			this.gunbladeShootAnimationState.updateWhen(this.getAnimationState() == 4 && entity.isHolding(BTAItems.SKELETAL_GUNBLADE.get()), entity.tickCount);
			this.gunbladeSwingAnimationState.updateWhen(this.getAnimationState() == 5 && entity.isHolding(BTAItems.SKELETAL_GUNBLADE.get()), entity.tickCount);
		}
		else
		{
			this.sendUpdatePacket();
		}
	}

	@Override
	public void setAnimationState(int state) 
	{
		this.animationState = state;
	}

	@Override
	public int getAnimationState() 
	{
		return this.animationState;
	}
	
	@Override
	public void setPrevAnimationState(int state) 
	{
		this.prevAnimationState = state;
	}

	@Override
	public int getPrevAnimationState() 
	{
		return this.prevAnimationState;
	}
	
	@Override
	public void setAnimationTick(int tick) 
	{
		this.animationTick = tick;
	}
	
	@Override
	public int getAnimationTick() 
	{
		return this.animationTick;
	}
	
	private void sendUpdatePacket() 
	{
		if(!this.entity.level.isClientSide)
		{
			BTANetwork.CHANNEL.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> this.entity), new UpdatePlayerAnimationPacket(this.entity.getUUID(), this.animationState, this.prevAnimationState, this.animationTick));
		}
	}
	
	@Override
	public <T> @NotNull LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) 
	{
		return PLAYER_ANIMATION.orEmpty(cap, LazyOptional.of(() -> this));
	}
}
