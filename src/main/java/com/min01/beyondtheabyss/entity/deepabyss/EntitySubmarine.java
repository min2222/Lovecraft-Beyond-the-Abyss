package com.min01.beyondtheabyss.entity.deepabyss;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.jetbrains.annotations.Nullable;

import com.min01.beyondtheabyss.entity.part.SubmarineHitBoxes;
import com.min01.beyondtheabyss.entity.part.SubmarinePart;
import com.min01.beyondtheabyss.multipart.entity.EntityBounds;
import com.min01.beyondtheabyss.multipart.entity.MultipartAwareEntity;
import com.min01.beyondtheabyss.multipart.util.CompoundOrientedBox;
import com.min01.beyondtheabyss.util.BTAUtil;

import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.entity.PartEntity;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.fml.util.ObfuscationReflectionHelper;

public class EntitySubmarine extends LivingEntity implements MultipartAwareEntity
{
    public final SubmarineHitBoxes hitboxHelper = new SubmarineHitBoxes(this);
	
	public static final EntityDataAccessor<Optional<UUID>> CONTROLLING_PLAYER = SynchedEntityData.defineId(EntitySubmarine.class, EntityDataSerializers.OPTIONAL_UUID);
	public static final EntityDataAccessor<Optional<UUID>> SEAT1_PLAYER = SynchedEntityData.defineId(EntitySubmarine.class, EntityDataSerializers.OPTIONAL_UUID);
	public static final EntityDataAccessor<Optional<UUID>> SEAT2_PLAYER = SynchedEntityData.defineId(EntitySubmarine.class, EntityDataSerializers.OPTIONAL_UUID);
	public static final EntityDataAccessor<Optional<UUID>> SEAT3_PLAYER = SynchedEntityData.defineId(EntitySubmarine.class, EntityDataSerializers.OPTIONAL_UUID);
	public static final EntityDataAccessor<Optional<UUID>> SEAT4_PLAYER = SynchedEntityData.defineId(EntitySubmarine.class, EntityDataSerializers.OPTIONAL_UUID);
	public static final EntityDataAccessor<Boolean> HATCH_OPENED = SynchedEntityData.defineId(EntitySubmarine.class, EntityDataSerializers.BOOLEAN);
	
    public float brightness;
    public float brightnessOld;
    public int glowingTicks;
    
    public SubmarinePart hatch = new SubmarinePart(this, 1.0F, 0.75F)
    {
    	@Override
    	public InteractionResult interact(Player p_19978_, InteractionHand p_19979_) 
    	{
			this.parentMob.setHatchOpened(!this.parentMob.hatchOpened());
    		return InteractionResult.SUCCESS;
    	}
    };
    
	public SubmarinePart[] parts = { this.hatch };
	
	public EntitySubmarine(EntityType<? extends LivingEntity> p_19870_, Level p_19871_)
	{
		super(p_19870_, p_19871_);
	}
	
	@Override
	protected void defineSynchedData()
	{
		super.defineSynchedData();
		this.entityData.define(CONTROLLING_PLAYER, Optional.empty());
		this.entityData.define(SEAT1_PLAYER, Optional.empty());
		this.entityData.define(SEAT2_PLAYER, Optional.empty());
		this.entityData.define(SEAT3_PLAYER, Optional.empty());
		this.entityData.define(SEAT4_PLAYER, Optional.empty());
		this.entityData.define(HATCH_OPENED, false);
	}
	
	@Override
	public boolean isMultipartEntity() 
	{
		return true;
	}
	
	@Override
	public @Nullable PartEntity<?>[] getParts() 
	{
		return this.parts;
	}
	
	@Override
	public void positionRider(Entity p_20312_) 
	{
    	Vec3 seatForwardPos = BTAUtil.getLookPos(this.getXRot(), this.getYRot(), 0, 1.2F);
    	Vec3 seatBackwardPos = BTAUtil.getLookPos(this.getXRot(), this.getYRot(), 0, -0.6F);
        
		if(this.getControllingPlayer() != null)
		{
	    	Vec3 controllerPos = BTAUtil.getLookPos(this.getXRot(), this.getYRot(), 0, 2.2F);
	    	this.getControllingPlayer().setPos(this.position().add(0, 1.8F, 0).add(controllerPos));
	    	
	    	if(this.getControllingPlayer().isShiftKeyDown())
	    	{
	    		this.getControllingPlayer().stopRiding();
	    	}
		}
		
		if(this.getSeat1Player() != null)
		{
	    	Vec3 seat1Pos = seatForwardPos.add(BTAUtil.getLookPos(this.getXRot(), this.getYRot() - 90, 0, 0.8F));
	    	this.getSeat1Player().setPos(this.position().add(0, 1.8F, 0).add(seat1Pos));
	    	
	    	if(this.getSeat1Player().isShiftKeyDown())
	    	{
	    		this.getSeat1Player().stopRiding();
	    	}
		}
		
		if(this.getSeat2Player() != null)
		{
	    	Vec3 seat2Pos = seatForwardPos.add(BTAUtil.getLookPos(this.getXRot(), this.getYRot() + 90, 0, 0.8F));
	    	this.getSeat2Player().setPos(this.position().add(0, 1.8F, 0).add(seat2Pos));
	    	
	    	if(this.getSeat2Player().isShiftKeyDown())
	    	{
	    		this.getSeat2Player().stopRiding();
	    	}
		}
		
		if(this.getSeat3Player() != null)
		{
	    	Vec3 seat3Pos = seatBackwardPos.add(BTAUtil.getLookPos(this.getXRot(), this.getYRot() - 90, 0, 0.8F));
	    	this.getSeat3Player().setPos(this.position().add(0, 1.8F, 0).add(seat3Pos));
	    	
	    	if(this.getSeat3Player().isShiftKeyDown())
	    	{
	    		this.getSeat3Player().stopRiding();
	    	}
		}
		
		if(this.getSeat4Player() != null)
		{
	    	Vec3 seat4Pos = seatBackwardPos.add(BTAUtil.getLookPos(this.getXRot(), this.getYRot() + 90, 0, 0.8F));
	    	this.getSeat4Player().setPos(this.position().add(0, 1.8F, 0).add(seat4Pos));
	    	
	    	if(this.getSeat4Player().isShiftKeyDown())
	    	{
	    		this.getSeat4Player().stopRiding();
	    	}
		}
	}
	
    @Override
    public void tick() 
    {
    	super.tick();
    	
    	this.refreshDimensions();
    	
        if (this.level.isClientSide) 
        {
            ++this.glowingTicks;
            this.brightness += (0.0F - this.brightness) * 0.8F;
        }
        
    	Vec3 hatchPos = BTAUtil.getLookPos(this.getXRot(), this.getYRot(), 0, -0.25F);
    	Vec3 pos = new Vec3(this.getX(), this.getY() + 4.2F, this.getZ()).add(hatchPos);
    	this.hatch.setPos(pos);
        
    	for(PartEntity<?> parts : this.getParts())
    	{
    		parts.tick();
    	}
    	
        Vec3[] avector3d = new Vec3[this.getParts().length];
        
        for(int j = 0; j < this.getParts().length; j++)
        {
        	avector3d[j] = new Vec3(this.getParts()[j].getX(), this.getParts()[j].getY(), this.getParts()[j].getZ());
        }
        
        for(int l = 0; l < this.getParts().length; l++) 
        {
        	this.getParts()[l].xo = avector3d[l].x;
        	this.getParts()[l].yo = avector3d[l].y;
        	this.getParts()[l].zo = avector3d[l].z;
        	this.getParts()[l].xOld = avector3d[l].x;
        	this.getParts()[l].yOld = avector3d[l].y;
        	this.getParts()[l].zOld = avector3d[l].z;
        }
        
        if(this.isInWater())
        {
			this.setDeltaMovement(this.getDeltaMovement().add(0, 0.05F, 0));
        }
        
		if(this.getControllingPlayer() != null && !this.hasPassenger(this.getControllingPlayer()))
		{
			this.removeSeatPlayer(0);
		}
		
		if(this.getSeat1Player() != null && !this.hasPassenger(this.getSeat1Player()))
		{
			this.removeSeatPlayer(1);
		}
		
		if(this.getSeat2Player() != null && !this.hasPassenger(this.getSeat2Player()))
		{
			this.removeSeatPlayer(2);
		}
		
		if(this.getSeat3Player() != null && !this.hasPassenger(this.getSeat3Player()))
		{
			this.removeSeatPlayer(3);
		}
		
		if(this.getSeat4Player() != null && !this.hasPassenger(this.getSeat4Player()))
		{
			this.removeSeatPlayer(4);
		}
    }
    
    @Override
	public void travel(Vec3 veci)
	{
		if(this.getControllingPlayer() != null)
		{
            if(this.getControllingPlayer().zza != 0)
            {
                this.setYRot(this.getControllingPlayer().getYRot());
                this.setXRot(this.getControllingPlayer().getXRot());
            }
            
			if(this.isInWater())
			{
				boolean jumping = ObfuscationReflectionHelper.getPrivateValue(LivingEntity.class, this.getControllingPlayer(), "f_20899_");
				Vec3 travelVector = new Vec3(this.getControllingPlayer().xxa, this.getControllingPlayer().yya, this.getControllingPlayer().zza);
	            if(this.isEffectiveAi())
	            {
	    			this.moveRelative(0.3F, travelVector);
	    			this.move(MoverType.SELF, this.getDeltaMovement());
	    			this.setDeltaMovement(this.getDeltaMovement().scale(0.01D));
	            }
				if(jumping)
				{
					this.setDeltaMovement(this.getDeltaMovement().add(0, 0.1F, 0));
				}
			}
		}
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
    public boolean canBeCollidedWith() 
    {
    	return true;
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
    protected boolean isAffectedByFluids()
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
    	Vec3 dismountPos = BTAUtil.getLookPos(this.getXRot(), this.getYRot(), 0, 0);
    	return this.position().add(0, dismountPos.y + 1.8F, 0);
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
    
    public void setPartPosition(SubmarinePart part, double offsetX, double offsetY, double offsetZ) 
    {
    	part.setPos(this.getX() + offsetX, this.getY() + offsetY, this.getZ() + offsetZ);
    }
	
	public void setHatchOpened(boolean value)
	{
		this.entityData.set(HATCH_OPENED, value);
	}
	
	public boolean hatchOpened()
	{
		return this.entityData.get(HATCH_OPENED);
	}
	
	public Player getControllingPlayer()
	{
		if(this.entityData.get(CONTROLLING_PLAYER).isPresent()) 
		{
			return BTAUtil.getEntityByUUID(this.level, this.entityData.get(CONTROLLING_PLAYER).get());
		}
		return null;
	}
	
	public Player getSeat1Player()
	{
		if(this.entityData.get(SEAT1_PLAYER).isPresent()) 
		{
			return BTAUtil.getEntityByUUID(this.level, this.entityData.get(SEAT1_PLAYER).get());
		}
		return null;
	}
	
	public Player getSeat2Player()
	{
		if(this.entityData.get(SEAT2_PLAYER).isPresent()) 
		{
			return BTAUtil.getEntityByUUID(this.level, this.entityData.get(SEAT2_PLAYER).get());
		}
		return null;
	}
	
	public Player getSeat3Player()
	{
		if(this.entityData.get(SEAT3_PLAYER).isPresent()) 
		{
			return BTAUtil.getEntityByUUID(this.level, this.entityData.get(SEAT3_PLAYER).get());
		}
		return null;
	}
	
	public Player getSeat4Player()
	{
		if(this.entityData.get(SEAT4_PLAYER).isPresent()) 
		{
			return BTAUtil.getEntityByUUID(this.level, this.entityData.get(SEAT4_PLAYER).get());
		}
		return null;
	}
	
	public void removeSeatPlayer(int seatId)
	{
		switch(seatId)
		{
		case 0:
			this.entityData.set(CONTROLLING_PLAYER, Optional.empty());
			break;
		case 1:
			this.entityData.set(SEAT1_PLAYER, Optional.empty());
			break;
		case 2:
			this.entityData.set(SEAT2_PLAYER, Optional.empty());
			break;
		case 3:
			this.entityData.set(SEAT3_PLAYER, Optional.empty());
			break;
		case 4:
			this.entityData.set(SEAT4_PLAYER, Optional.empty());
			break;
		}
	}
	
	public void setSeatPlayer(int seatId, Player player)
	{
		player.startRiding(this);
		switch(seatId)
		{
		case 0:
			this.entityData.set(CONTROLLING_PLAYER, Optional.of(player.getUUID()));
			break;
		case 1:
			this.entityData.set(SEAT1_PLAYER, Optional.of(player.getUUID()));
			break;
		case 2:
			this.entityData.set(SEAT2_PLAYER, Optional.of(player.getUUID()));
			break;
		case 3:
			this.entityData.set(SEAT3_PLAYER, Optional.of(player.getUUID()));
			break;
		case 4:
			this.entityData.set(SEAT4_PLAYER, Optional.of(player.getUUID()));
			break;
		}
	}

	@Override
	public CompoundOrientedBox getCompoundBoundingBox(AABB bounds) 
	{
		return this.hitboxHelper.getHitbox().getBox(bounds);
	}

	@Override
	public EntityBounds getBounds() 
	{
		return this.hitboxHelper.getHitbox();
	}

	@Override
	public void onSetPos(double x, double y, double z) 
	{
        if(this.hitboxHelper != null)
        {
        	this.hitboxHelper.updatePosition();
        }
	}
	
	@Override
	public InteractionResult interact(Entity entity, InteractionHand hand, String part)
	{
		if(entity instanceof Player player)
		{
			if(part == "controllerSeat")
			{
				this.setSeatPlayer(0, player);
			}
			else if(part == "seat1")
			{
				this.setSeatPlayer(1, player);
			}
			else if(part == "seat2")
			{
				this.setSeatPlayer(2, player);
			}
			else if(part == "seat3")
			{
				this.setSeatPlayer(3, player);
			}
			else if(part == "seat4")
			{
				this.setSeatPlayer(4, player);
			}
			return InteractionResult.SUCCESS;
		}
		return MultipartAwareEntity.super.interact(entity, hand, part);
	}
	
	@Override
	public boolean isPickable() 
	{
		return true;
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
}
