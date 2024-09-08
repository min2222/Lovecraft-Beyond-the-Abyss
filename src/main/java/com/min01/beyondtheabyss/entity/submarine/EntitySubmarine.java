package com.min01.beyondtheabyss.entity.submarine;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.min01.beyondtheabyss.cerbon.CompoundOrientedBox;
import com.min01.beyondtheabyss.cerbon.EntityBounds;
import com.min01.beyondtheabyss.cerbon.IMultipart;
import com.min01.beyondtheabyss.entity.BTAEntities;
import com.min01.beyondtheabyss.entity.submarine.SubmarinePart.SubmarinePartType;
import com.min01.beyondtheabyss.util.BTAUtil;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
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

public class EntitySubmarine extends LivingEntity implements IMultipart
{
    public SubmarineHitBox hitbox = new SubmarineHitBox(this);
	
	public static final EntityDataAccessor<Optional<UUID>> CONTROLLING_PLAYER = SynchedEntityData.defineId(EntitySubmarine.class, EntityDataSerializers.OPTIONAL_UUID);
	public static final EntityDataAccessor<Optional<UUID>> SEAT1_PLAYER = SynchedEntityData.defineId(EntitySubmarine.class, EntityDataSerializers.OPTIONAL_UUID);
	public static final EntityDataAccessor<Optional<UUID>> SEAT2_PLAYER = SynchedEntityData.defineId(EntitySubmarine.class, EntityDataSerializers.OPTIONAL_UUID);
	public static final EntityDataAccessor<Optional<UUID>> SEAT3_PLAYER = SynchedEntityData.defineId(EntitySubmarine.class, EntityDataSerializers.OPTIONAL_UUID);
	public static final EntityDataAccessor<Optional<UUID>> SEAT4_PLAYER = SynchedEntityData.defineId(EntitySubmarine.class, EntityDataSerializers.OPTIONAL_UUID);
	public static final EntityDataAccessor<Optional<UUID>> HATCH = SynchedEntityData.defineId(EntitySubmarine.class, EntityDataSerializers.OPTIONAL_UUID);
	public static final EntityDataAccessor<Optional<UUID>> DETECTOR = SynchedEntityData.defineId(EntitySubmarine.class, EntityDataSerializers.OPTIONAL_UUID);
	public static final EntityDataAccessor<Boolean> HATCH_OPENED = SynchedEntityData.defineId(EntitySubmarine.class, EntityDataSerializers.BOOLEAN);
	public static final EntityDataAccessor<BlockPos> PREV_POS = SynchedEntityData.defineId(EntitySubmarine.class, EntityDataSerializers.BLOCK_POS);
	
    public float brightness;
    public float brightnessOld;
    public int glowingTicks;
    
    public Vec3[] posArray = new Vec3[13];
    
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
		this.entityData.define(HATCH, Optional.empty());
		this.entityData.define(DETECTOR, Optional.empty());
		this.entityData.define(HATCH_OPENED, false);
		this.entityData.define(PREV_POS, BlockPos.ZERO);
	}
	
	@Override
	public void travel(Vec3 p_21280_) 
	{
		if(this.getControllingPlayer() != null)
		{
			Player player = this.getControllingPlayer();
			Vec3 travelVector = new Vec3(player.xxa, player.yya, player.zza);
	        if(player.zza != 0 || player.xxa != 0)
	        {
	        	this.setXRot(player.getXRot());
	        	this.setYRot(player.getYRot());
	            this.setYHeadRot(player.getYHeadRot());
	            this.setYBodyRot(player.yBodyRot);
	        }
	        super.travel(travelVector);
		}
		else
		{
			super.travel(p_21280_);
		}
	}
	
	@Override
	public boolean showVehicleHealth() 
	{
		return false;
	}
	
	@Override
	public void onAddedToWorld()
	{
		super.onAddedToWorld();
		if(this.getHatch() == null)
		{
			SubmarinePart hatch = new SubmarinePart(BTAEntities.SUBMARINE_PART.get(), this.level);
			hatch.setPartType(SubmarinePartType.HATCH);
			hatch.setPos(this.position());
			hatch.setOwner(this);
			this.level.addFreshEntity(hatch);
			this.setHatch(hatch);
		}
		if(this.getDetector() == null)
		{
			SubmarinePart detector = new SubmarinePart(BTAEntities.SUBMARINE_PART.get(), this.level);
			detector.setPartType(SubmarinePartType.DETECTOR);
			detector.setPos(this.position());
			detector.setOwner(this);
			this.level.addFreshEntity(detector);
			this.setDetector(detector);
		}
	}
	
	@Override
	public void positionRider(Entity p_20312_) 
	{
		if(this.getControllingPlayer() != null)
		{
			if(this.posArray[0] != null)
			{
		    	this.getControllingPlayer().setPos(this.position().add(this.posArray[0]));
			}
	    	
	    	if(this.getControllingPlayer().isShiftKeyDown())
	    	{
	    		this.getControllingPlayer().stopRiding();
	    		this.removeSeatPlayer(0);
	    	}
		}
		
		if(this.getSeat1Player() != null)
		{
			if(this.posArray[1] != null)
			{
		    	this.getSeat1Player().setPos(this.position().add(this.posArray[1]));
			}
	    	
	    	if(this.getSeat1Player().isShiftKeyDown())
	    	{
	    		this.getSeat1Player().stopRiding();
	    		this.removeSeatPlayer(1);
	    	}
		}
		
		if(this.getSeat2Player() != null)
		{
			if(this.posArray[2] != null)
			{
		    	this.getSeat2Player().setPos(this.position().add(this.posArray[2]));
			}
	    	
	    	if(this.getSeat2Player().isShiftKeyDown())
	    	{
	    		this.getSeat2Player().stopRiding();
	    		this.removeSeatPlayer(2);
	    	}
		}
		
		if(this.getSeat3Player() != null)
		{
			if(this.posArray[3] != null)
			{
		    	this.getSeat3Player().setPos(this.position().add(this.posArray[3]));
			}
	    	
	    	if(this.getSeat3Player().isShiftKeyDown())
	    	{
	    		this.getSeat3Player().stopRiding();
	    		this.removeSeatPlayer(3);
	    	}
		}
		
		if(this.getSeat4Player() != null)
		{
			if(this.posArray[4] != null)
			{
		    	this.getSeat4Player().setPos(this.position().add(this.posArray[4]));
			}
	    	
	    	if(this.getSeat4Player().isShiftKeyDown())
	    	{
	    		this.getSeat4Player().stopRiding();
	    		this.removeSeatPlayer(4);
	    	}
		}
	}
	
	@Override
	public void addAdditionalSaveData(CompoundTag p_21145_) 
	{
		super.addAdditionalSaveData(p_21145_);
		if(this.getControllingPlayer() != null)
		{
			p_21145_.putUUID("ControllerUUID", this.getControllingPlayer().getUUID());
		}
		
		if(this.getSeat1Player() != null)
		{
			p_21145_.putUUID("Seat1UUID", this.getSeat1Player().getUUID());
		}
		
		if(this.getSeat2Player() != null)
		{
			p_21145_.putUUID("Seat2UUID", this.getSeat2Player().getUUID());
		}
		
		if(this.getSeat3Player() != null)
		{
			p_21145_.putUUID("Seat3UUID", this.getSeat3Player().getUUID());
		}
		
		if(this.getSeat4Player() != null)
		{
			p_21145_.putUUID("Seat4UUID", this.getSeat4Player().getUUID());
		}
		
		if(this.getHatch() != null)
		{
			p_21145_.putUUID("HatchUUID", this.getHatch().getUUID());
		}
		
		if(this.getDetector() != null)
		{
			p_21145_.putUUID("DetectorUUID", this.getDetector().getUUID());
		}
	}
	
	@Override
	public void readAdditionalSaveData(CompoundTag p_21096_) 
	{
		super.readAdditionalSaveData(p_21096_);
		if(p_21096_.hasUUID("ControllerUUID")) 
		{
			this.entityData.set(CONTROLLING_PLAYER, Optional.of(p_21096_.getUUID("ControllerUUID")));
		}
		
		if(p_21096_.hasUUID("Seat1UUID")) 
		{
			this.entityData.set(SEAT1_PLAYER, Optional.of(p_21096_.getUUID("Seat1UUID")));
		}
		
		if(p_21096_.hasUUID("Seat2UUID"))
		{
			this.entityData.set(SEAT2_PLAYER, Optional.of(p_21096_.getUUID("Seat2UUID")));
		}
		
		if(p_21096_.hasUUID("Seat3UUID")) 
		{
			this.entityData.set(SEAT3_PLAYER, Optional.of(p_21096_.getUUID("Seat3UUID")));
		}
		
		if(p_21096_.hasUUID("Seat4UUID")) 
		{
			this.entityData.set(SEAT4_PLAYER, Optional.of(p_21096_.getUUID("Seat4UUID")));
		}
		
		if(p_21096_.hasUUID("HatchUUID")) 
		{
			this.entityData.set(HATCH, Optional.of(p_21096_.getUUID("HatchUUID")));
		}
		
		if(p_21096_.hasUUID("DetectorUUID")) 
		{
			this.entityData.set(DETECTOR, Optional.of(p_21096_.getUUID("DetectorUUID")));
		}
	}
	
    @Override
    public void tick() 
    {
    	super.tick();
    	
    	this.refreshDimensions();
    	
        if(this.level.isClientSide) 
        {
            ++this.glowingTicks;
            this.brightness += (0.0F - this.brightness) * 0.8F;
        }
        
    	if(this.isInWater())
    	{
    		/*BlockPos prevPos = this.getPrevPos();
        	Vec3 lightPos = BTAUtil.getLookPos(this.getXRot(), this.getYRot(), 0, 8F);
        	HitResult result = this.level.clip(new ClipContext(this.position().add(0, 1.5F, 0), this.position().add(0, 1.5F, 0).add(lightPos), ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, this));
            if(result instanceof BlockHitResult blockHit)
            {
                BlockPos blockPos = blockHit.getBlockPos().relative(blockHit.getDirection());
                if(blockPos != prevPos && this.level.getFluidState(blockPos).is(Fluids.WATER))
                {
                	this.level.setBlockAndUpdate(prevPos, Blocks.WATER.defaultBlockState());
                	this.setPrevPos(blockPos);
                	this.level.setBlockAndUpdate(blockPos, BTABlocks.BTA_LIGHT.get().defaultBlockState());
                }
            }*/
    	}
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
    	Vec3 dismountPos = BTAUtil.getLookPos(this.getXRot(), this.getYRot(), 0, 0);
    	return this.position().add(0, dismountPos.y + 1.8F, 0);
    }
    
    @Override
    public boolean hurt(DamageSource p_21016_, float p_21017_)
    {
    	if(!p_21016_.isBypassInvul())
    	{
    		return false;
    	}
    	return super.hurt(p_21016_, p_21017_);
    }
    
	public void setPrevPos(BlockPos value)
	{
		this.entityData.set(PREV_POS, value);
	}
	
	public BlockPos getPrevPos()
	{
		return this.entityData.get(PREV_POS);
	}
	
	public void setHatchOpened(boolean value)
	{
		this.entityData.set(HATCH_OPENED, value);
	}
	
	public boolean hatchOpened()
	{
		return this.entityData.get(HATCH_OPENED);
	}
	
	public void setDetector(SubmarinePart part)
	{
		this.entityData.set(DETECTOR, Optional.of(part.getUUID()));
	}
	
	public SubmarinePart getDetector()
	{
		if(this.entityData.get(DETECTOR).isPresent()) 
		{
			return BTAUtil.getEntityByUUID(this.level, this.entityData.get(DETECTOR).get());
		}
		return null;
	}
	
	public void setHatch(SubmarinePart part)
	{
		this.entityData.set(HATCH, Optional.of(part.getUUID()));
	}
	
	public SubmarinePart getHatch()
	{
		if(this.entityData.get(HATCH).isPresent()) 
		{
			return BTAUtil.getEntityByUUID(this.level, this.entityData.get(HATCH).get());
		}
		return null;
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
	public boolean canBeCollidedWith()
	{
		return true;
	}

	@Override
	public CompoundOrientedBox getCompoundBoundingBox(AABB bounds) 
	{
		return this.hitbox.getHitbox().getBox(bounds);
	}

	@Override
	public EntityBounds getBounds() 
	{
		return this.hitbox.getHitbox();
	}

	@Override
	public void onSetPos(double x, double y, double z) 
	{
        if(this.hitbox != null)
        {
        	this.hitbox.updatePosition();
        }
	}
	
	@Override
	public InteractionResult interact(Entity entity, InteractionHand hand, String part)
	{
		if(entity instanceof Player player)
		{
			player.startRiding(this);
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
		return IMultipart.super.interact(entity, hand, part);
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
