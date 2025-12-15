package com.min01.beyondtheabyss.entity.deepabyss;

import java.util.List;

import com.min01.beyondtheabyss.entity.AbstractOwnableEntity;
import com.min01.beyondtheabyss.misc.SmoothAnimationState;
import com.min01.beyondtheabyss.multipart.CompoundOrientedBox;
import com.min01.beyondtheabyss.multipart.EntityBounds;
import com.min01.beyondtheabyss.multipart.EntityPartBuilder;
import com.min01.beyondtheabyss.multipart.IMultipart;
import com.min01.beyondtheabyss.network.BTANetwork;
import com.min01.beyondtheabyss.network.UpdateVehiclePacket;
import com.min01.beyondtheabyss.util.BTAUtil;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.fml.util.ObfuscationReflectionHelper;

public class EntitySubmarine extends AbstractOwnableEntity<LivingEntity> implements IMultipart
{
	public static final EntityDataAccessor<Boolean> HATCH_OPENED = SynchedEntityData.defineId(EntitySubmarine.class, EntityDataSerializers.BOOLEAN);
	public static final EntityDataAccessor<Integer> ANIMATION_STATE = SynchedEntityData.defineId(EntitySubmarine.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<Integer> ANIMATION_TICK = SynchedEntityData.defineId(EntitySubmarine.class, EntityDataSerializers.INT);
	
	public final SmoothAnimationState openHatchAnimationState = new SmoothAnimationState();
	public final SmoothAnimationState closeHatchAnimationState = new SmoothAnimationState();
	
    public float brightness;	
    public float brightnessOld;
    public int glowingTicks;
    
	private int lerpSteps;
	private double lerpX;
	private double lerpY;
	private double lerpZ;
	private double lerpYRot;
	private double lerpXRot;

	public final EntityPartBuilder<EntitySubmarine> partBuilder;
    
	public EntitySubmarine(EntityType<? extends Entity> p_19870_, Level p_19871_)
	{
		super(p_19870_, p_19871_);
		this.noCulling = true;
		this.partBuilder = new EntityPartBuilder<EntitySubmarine>(this);
	}
	
	@Override
	protected void defineSynchedData()
	{
		super.defineSynchedData();
		this.entityData.define(HATCH_OPENED, false);
		this.entityData.define(ANIMATION_STATE, 0);
		this.entityData.define(ANIMATION_TICK, 0);
	}
	
	@Override
	public CompoundOrientedBox getCompoundBoundingBox(AABB bounds) 
	{
		return this.partBuilder.hitbox.getBox(bounds);
	}

	@Override
	public EntityBounds getBounds() 
	{
		return this.partBuilder.hitbox;
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
		this.tickLerp();
		if(this.partBuilder != null)
		{
			this.partBuilder.tick(1.0F);
		}
		
        if(this.level.isClientSide) 
        {
        	this.openHatchAnimationState.updateWhen(this.getAnimationState() == 1, this.tickCount);
        	this.closeHatchAnimationState.updateWhen(this.getAnimationState() == 2, this.tickCount);
            ++this.glowingTicks;
            this.brightness += (0.0F - this.brightness) * 0.8F;
        }
		
		if(this.getAnimationTick() > 0)
		{
			this.setAnimationTick(this.getAnimationTick() - 1);
		}
		
    	if(this.getFirstPassenger() instanceof Player player)
    	{
    		if(this.isInWater() && this.isControlledByLocalInstance())
    		{
            	Vec3 motion = this.getDeltaMovement();
            	boolean jumping = ObfuscationReflectionHelper.getPrivateValue(LivingEntity.class, player, "f_20899_");
                if(player.xxa != 0 || player.zza != 0)
                {
                	this.setXRot(BTAUtil.rotlerp(this.getXRot(), player.getXRot(), 15));
                	this.setYRot(BTAUtil.rotlerp(this.getYRot(), player.getYRot(), 15));
                	Vec3 lookPos = BTAUtil.getLookPos(this.getRotationVector(), Vec3.ZERO, 0.0F, 0.0F, 2.5F);
                	motion = motion.add(lookPos);
                }
            	if(jumping)
            	{
            		motion = motion.add(0, 1.5F, 0);
            	}
            	this.setDeltaMovement(motion.scale(0.1F));
    		}
    		else
    		{
    			this.setDeltaMovement(Vec3.ZERO);
    		}
    		if(player.isShiftKeyDown())
    		{
    			player.stopRiding();
    			this.setDeltaMovement(Vec3.ZERO);
    		}
    	}
    	
    	this.move(MoverType.SELF, this.getDeltaMovement());
    }
	
	@Override
	public void positionRider(Entity entity, MoveFunction fuction) 
	{
    	Vec3 pos = BTAUtil.getLookPos(this.getRotationVector(), this.position(), 0.0F, 1.75F, 2.0F);
		fuction.accept(entity, pos.x, pos.y, pos.z);
	}
	
	@Override
	public void lerpTo(double p_38299_, double p_38300_, double p_38301_, float p_38302_, float p_38303_, int p_38304_, boolean p_38305_)
	{
		this.lerpX = p_38299_;
		this.lerpY = p_38300_;
		this.lerpZ = p_38301_;
		this.lerpYRot = (double)p_38302_;
		this.lerpXRot = (double)p_38303_;
		this.lerpSteps = 10;
	}
	
	private void tickLerp() 
	{
		if(this.isControlledByLocalInstance()) 
		{
			this.lerpSteps = 0;
			this.syncPacketPositionCodec(this.getX(), this.getY(), this.getZ());
		}
		if(this.lerpSteps > 0) 
		{
			double d0 = this.getX() + (this.lerpX - this.getX()) / (double)this.lerpSteps;
			double d1 = this.getY() + (this.lerpY - this.getY()) / (double)this.lerpSteps;
			double d2 = this.getZ() + (this.lerpZ - this.getZ()) / (double)this.lerpSteps;
			double d3 = Mth.wrapDegrees(this.lerpYRot - (double)this.getYRot());
			this.setYRot(this.getYRot() + (float)d3 / (float)this.lerpSteps);
			this.setXRot(this.getXRot() + (float)(this.lerpXRot - (double)this.getXRot()) / (float)this.lerpSteps);
			--this.lerpSteps;
			this.setPos(d0, d1, d2);
			this.setRot(this.getYRot(), this.getXRot());
		}
	}
	
	@Override
	protected void addPassenger(Entity passenger)
	{
		super.addPassenger(passenger);
		if(this.isControlledByLocalInstance() && this.lerpSteps > 0) 
		{
			this.lerpSteps = 0;
			this.absMoveTo(this.lerpX, this.lerpY, this.lerpZ, (float)this.lerpYRot, (float)this.lerpXRot);
		}
	}
    
    @Override
    public boolean isPushable() 
    {
    	return false;
    }
    
    @Override
    public boolean isPushedByFluid(FluidType type)
    {
    	return false;
    }

    @Override
    public boolean isPushedByFluid() 
    {
        return false;
    }
    
    @Override
    public Vec3 getDismountLocationForPassenger(LivingEntity p_20123_) 
    {
    	return new Vec3(this.getX(), this.getBoundingBox().minY + 2.0F, this.getZ());
    }
    
    @Override
    public boolean hurt(DamageSource p_21016_, float p_21017_)
    {
    	if(!p_21016_.is(DamageTypeTags.BYPASSES_INVULNERABILITY))
    	{
    		return false;
    	}
    	return super.hurt(p_21016_, p_21017_);
    }
	
	@Override
	public List<String> getCollidePart()
	{
		return List.of("bottom", "r_wall", "l_wall", "back", "hatch", "top_part1", "top_part2", "top_part3","top_part4", "front");
	}
	
	@Override
	public List<String> getIgnorePart() 
	{
		return List.of("top_part0");
	}
	
	@Override
	public boolean isPickable()
	{
		return true;
	}
	
	@Override
	public InteractionResult interact(Player player, InteractionHand hand) 
	{
        String part = BTAUtil.getMultiPart(this.getBounds(), player);
        if(part != null)
        {
        	if(part.equals("controller") && this.getFirstPassenger() == null)
        	{
    			if(this.level.isClientSide)
    			{
        			player.startRiding(this);
    				BTANetwork.sendToServer(new UpdateVehiclePacket(player, this));
    			}
				return InteractionResult.SUCCESS;
        	}
        	if(part.equals("hatch") || part.equals("valve"))
        	{
    			if(this.getAnimationTick() <= 0)
    			{
    				int state = this.getAnimationState() == 1 ? 2 : 1;
    				this.setAnimationState(state);
        			this.setHatchOpened(!this.hatchOpened());
        			this.setAnimationTick(30);
        			return InteractionResult.SUCCESS;
    			}
        	}
        }
		return InteractionResult.FAIL;
	}
	
    @Override
    public void readAdditionalSaveData(CompoundTag p_21450_) 
    {
    	super.readAdditionalSaveData(p_21450_);
    	this.setAnimationTick(p_21450_.getInt("AnimationTick"));
    	this.setAnimationState(p_21450_.getInt("AnimationState"));
    }
    
    @Override
    public void addAdditionalSaveData(CompoundTag p_21484_) 
    {
    	super.addAdditionalSaveData(p_21484_);
    	p_21484_.putInt("AnimationTick", this.getAnimationTick());
    	p_21484_.putInt("AnimationState", this.getAnimationState());
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

	public void setHatchOpened(boolean value)
	{
		this.entityData.set(HATCH_OPENED, value);
	}
	
	public boolean hatchOpened()
	{
		return this.entityData.get(HATCH_OPENED);
	}
}
