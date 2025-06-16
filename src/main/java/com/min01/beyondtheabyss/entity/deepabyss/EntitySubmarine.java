package com.min01.beyondtheabyss.entity.deepabyss;

import java.util.List;

import com.min01.beyondtheabyss.entity.IPosArray;
import com.min01.beyondtheabyss.multipart.CompoundOrientedBox;
import com.min01.beyondtheabyss.multipart.EntityBounds;
import com.min01.beyondtheabyss.multipart.EntityPartBuilder;
import com.min01.beyondtheabyss.multipart.IMultipart;
import com.min01.beyondtheabyss.util.BTAUtil;

import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.fml.util.ObfuscationReflectionHelper;

public class EntitySubmarine extends LivingEntity implements IMultipart, IPosArray
{
	public static final EntityDataAccessor<Boolean> HATCH_OPENED = SynchedEntityData.defineId(EntitySubmarine.class, EntityDataSerializers.BOOLEAN);
	
	public final AnimationState openHatchAnimationState = new AnimationState();
	public final AnimationState closeHatchAnimationState = new AnimationState();
	
    public float brightness;	
    public float brightnessOld;
    public int glowingTicks;
    
    public Vec3[] posArray = new Vec3[5];
    
	public final EntityPartBuilder<EntitySubmarine> partBuilder;
    
	public EntitySubmarine(EntityType<? extends LivingEntity> p_19870_, Level p_19871_)
	{
		super(p_19870_, p_19871_);
		this.partBuilder = new EntityPartBuilder<EntitySubmarine>(this);
		this.noCulling = true;
	}
	
	@Override
	protected void defineSynchedData()
	{
		super.defineSynchedData();
		this.entityData.define(HATCH_OPENED, false);
	}
	
    @Override
    public void tick() 
    {
    	super.tick();
        if(this.level.isClientSide) 
        {
            ++this.glowingTicks;
            this.brightness += (0.0F - this.brightness) * 0.8F;
        }
        
		if(this.partBuilder != null)
		{
			this.partBuilder.tick(1.0F);
		}
    }
    
    @Override
    public void travel(Vec3 vec3) 
    {
    	if(this.getFirstPassenger() instanceof Player player)
    	{
    		if(this.isInWater())
    		{
            	Vec3 motion = vec3;
            	boolean jumping = ObfuscationReflectionHelper.getPrivateValue(LivingEntity.class, player, "f_20899_");
                if(player.zza != 0 || player.xxa != 0)
                {
                	if(!this.level.isClientSide)
                	{
                    	this.setXRot(BTAUtil.rotlerp(this.getXRot(), player.getXRot(), 5));
                    	this.setYRot(BTAUtil.rotlerp(this.getYRot(), player.getYRot(), 5));
                    	this.setYHeadRot(this.getYRot());
                    	this.setYBodyRot(this.getYRot());
                	}
                	Vec3 lookPos = BTAUtil.getLookPos(this.getRotationVector(), Vec3.ZERO, 0.0F, 0.0F, 2.5F);
                	motion = motion.add(lookPos);
                }
            	if(jumping)
            	{
            		motion = motion.add(0, 1.5F, 0);
            	}
            	this.setDeltaMovement(motion.scale(0.1F));
            	super.travel(motion);
    		}
    	}
    	else
    	{
        	super.travel(vec3);
    	}
    }
    
    @Override
    public Vec3 getFluidFallingAdjustedMovement(double p_20995_, boolean p_20996_, Vec3 p_20997_)
    {
    	Vec3 motion = super.getFluidFallingAdjustedMovement(p_20995_, p_20996_, p_20997_);
    	return new Vec3(motion.x, 0.0F, motion.z);
    }
	
	@Override
	public void positionRider(Entity p_20312_, MoveFunction fuction) 
	{
        if(!this.touchingUnloadedChunk()) 
        {
	    	if(this.posArray[0] != null)
	    	{
	    		Vec3 pos = this.posArray[0].subtract(0, 0.25F, 0);
	    		fuction.accept(p_20312_, pos.x, pos.y, pos.z);
	    	}
        }
	}
	
	@Override
	public Vec3[] getPosArray() 
	{
		return this.posArray;
	}
	
	@Override
	public EntityPartBuilder<?> getPartBuilder() 
	{
		return this.partBuilder;
	}
	
	@Override
	public boolean showVehicleHealth() 
	{
		return false;
	}
    
    @Override
    protected Entity.MovementEmission getMovementEmission() 
    {
    	return MovementEmission.EVENTS;
    }
    
    @Override
    public boolean canBeRiddenUnderFluidType(FluidType type, Entity rider)
    {
        return type.supportsBoating(null);
    }
    
    @Override
    public void push(double p_20286_, double p_20287_, double p_20288_) 
    {
    	
    }
    
    @Override
    protected void pushEntities()
    {
    	
    }
    
    @Override
    public boolean canDrownInFluidType(FluidType type)
    {
    	return false;
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
    protected void tickDeath()
    {
    	if(!this.level.isClientSide) 
        {
        	this.level.broadcastEntityEvent(this, (byte)60);
            this.remove(Entity.RemovalReason.KILLED);
        }
    }
	
	@Override
	public List<String> getCollidePart()
	{
		return List.of("bottom", "r_wall", "l_wall", "back", "hatch", "top", "front");
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
	public InteractionResult interact(Player player, InteractionHand hand) 
	{
        String part = BTAUtil.getMultiPart(this.getBounds(), player);
        if(part != null)
        {
        	if(part.equals("controller") && this.getFirstPassenger() == null)
        	{
    			player.startRiding(this);
        	}
        	//TODO proper animation tick so only close/open when animation is finished;
        	if(part.equals("hatch") || part.equals("valve"))
        	{
    			if(!this.hatchOpened() && !this.openHatchAnimationState.isStarted())
    			{
        			this.closeHatchAnimationState.stop();
        			this.openHatchAnimationState.start(this.tickCount);
        			this.setHatchOpened(true);
    			}
        		else if(this.openHatchAnimationState.isStarted())
        		{
    				this.openHatchAnimationState.stop();
    				this.closeHatchAnimationState.start(this.tickCount);
        			this.setHatchOpened(false);
        		}
        	}
			return InteractionResult.SUCCESS;
        }
		return super.interact(player, hand);
	}

	@Override
	public Iterable<ItemStack> getArmorSlots()
	{
		return List.of();
	}

	@Override
	public ItemStack getItemBySlot(EquipmentSlot p_21127_) 
	{
		return ItemStack.EMPTY;
	}

	@Override
	public void setItemSlot(EquipmentSlot p_21036_, ItemStack p_21037_)
	{
		
	}

	@Override
	public HumanoidArm getMainArm()
	{
		return HumanoidArm.RIGHT;
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
