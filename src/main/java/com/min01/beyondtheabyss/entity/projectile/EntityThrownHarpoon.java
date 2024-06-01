package com.min01.beyondtheabyss.entity.projectile;

import java.util.List;

import javax.annotation.Nullable;

import com.google.common.collect.Lists;
import com.min01.beyondtheabyss.entity.BTAEntities;
import com.min01.beyondtheabyss.item.BTAItems;

import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;

public class EntityThrownHarpoon extends AbstractArrow
{
	private static final EntityDataAccessor<Boolean> IS_FOIL = SynchedEntityData.defineId(EntityThrownHarpoon.class, EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<Boolean> IS_REINFORCED = SynchedEntityData.defineId(EntityThrownHarpoon.class, EntityDataSerializers.BOOLEAN);
	   
	private ItemStack item = new ItemStack(BTAItems.RUSTY_HARPOON.get());
	private boolean dealtDamage;
	
	@Nullable
	private IntOpenHashSet piercingIgnoreEntityIds;
	
	@Nullable
	private List<Entity> piercedAndKilledEntities;
	
	public EntityThrownHarpoon(EntityType<? extends AbstractArrow> p_37561_, Level p_37562_) 
	{
		super(p_37561_, p_37562_);
	}

	public EntityThrownHarpoon(Level p_37569_, LivingEntity p_37570_, ItemStack p_37571_, boolean isReinforced) 
	{
		super(BTAEntities.THROWN_HARPOON.get(), p_37570_, p_37569_);
		this.item = p_37571_.copy();
		this.entityData.set(IS_FOIL, p_37571_.hasFoil());
		this.entityData.set(IS_REINFORCED, isReinforced);
	}
	
	@Override
	protected void defineSynchedData() 
	{
		super.defineSynchedData();
		this.entityData.define(IS_FOIL, false);
		this.entityData.define(IS_REINFORCED, false);
	}
	
	private void resetPiercedEntities() 
	{
		if(this.piercedAndKilledEntities != null) 
		{
			this.piercedAndKilledEntities.clear();
		}

		if(this.piercingIgnoreEntityIds != null) 
		{
			this.piercingIgnoreEntityIds.clear();
		}
	}
	
	@Override
	public void tick()
	{
		super.tick();
		if(this.inGroundTime > 4)
		{
			this.dealtDamage = true;
		}
	      
		Entity entity = this.getOwner();
		if(entity != null && (this.dealtDamage || this.isNoPhysics()))
		{
			float i = !this.isReinforced() ? 1.5F : 2.5F;
			this.setNoPhysics(true);
            Vec3 vec3 = entity.getEyePosition().subtract(this.position());
            this.setPosRaw(this.getX(), this.getY() + vec3.y * 0.015D * (double)i, this.getZ());
            if(this.level.isClientSide)
            {
            	this.yOld = this.getY();
            }

            double d0 = 0.05D * (double)i;
            this.setDeltaMovement(this.getDeltaMovement().scale(0.95D).add(vec3.normalize().scale(d0)));
		}
	}
	
	@Override
	protected boolean tryPickup(Player p_150196_)
	{
		return super.tryPickup(p_150196_) || this.isNoPhysics() && this.ownedBy(p_150196_) && p_150196_.getInventory().add(this.getPickupItem());
	}

	@Override
	public void playerTouch(Player p_37580_)
	{
		if(this.ownedBy(p_37580_) || this.getOwner() == null)
		{
			super.playerTouch(p_37580_);
		}
	}
	
	@Override
	protected void onHitEntity(EntityHitResult p_36757_) 
	{
		Entity entity = p_36757_.getEntity();
		
		float f = !this.isReinforced() ? 6.0F : 8.0F;
		if(entity instanceof LivingEntity livingentity) 
		{
			f += EnchantmentHelper.getDamageBonus(this.item, livingentity.getMobType());
		}

		Entity entity1 = this.getOwner();
		DamageSource damagesource = DamageSource.trident(this, (Entity)(entity1 == null ? this : entity1));
		if(!this.isReinforced())
		{
			this.dealtDamage = true;
			this.setDeltaMovement(this.getDeltaMovement().multiply(-0.01D, -0.1D, -0.01D));
		}
		else
		{
			int maxPierceCount = 15;
			if(this.piercingIgnoreEntityIds == null) 
			{
				this.piercingIgnoreEntityIds = new IntOpenHashSet(maxPierceCount);
			}

			if(this.piercedAndKilledEntities == null) 
			{
				this.piercedAndKilledEntities = Lists.newArrayListWithCapacity(maxPierceCount);
			}

			if(this.piercingIgnoreEntityIds.size() >= maxPierceCount + 1) 
			{
				this.dealtDamage = true;
				return;
			}

			this.piercingIgnoreEntityIds.add(entity.getId());
		}
		if(entity.hurt(damagesource, f))
		{
			this.playSound(SoundEvents.TRIDENT_HIT);
			if(entity.getType() == EntityType.ENDERMAN)
			{
				return;
			}

			if(entity instanceof LivingEntity)
			{
				LivingEntity livingentity1 = (LivingEntity)entity;
				if(entity1 instanceof LivingEntity)
				{
					EnchantmentHelper.doPostHurtEffects(livingentity1, entity1);
					EnchantmentHelper.doPostDamageEffects((LivingEntity)entity1, livingentity1);
				}

				this.doPostHurtEffects(livingentity1);
				
				if(!entity.isAlive() && this.piercedAndKilledEntities != null) 
	            {
					this.piercedAndKilledEntities.add(livingentity1);
	            }
			}
		}
	}
	
	@Override
	protected void onHitBlock(BlockHitResult p_36755_)
	{
		super.onHitBlock(p_36755_);
		this.playSound(SoundEvents.TRIDENT_HIT_GROUND);
		this.resetPiercedEntities();
	}
	
	@Nullable
	@Override
	protected EntityHitResult findHitEntity(Vec3 p_37575_, Vec3 p_37576_) 
	{
		return !this.isReinforced() && this.dealtDamage ? null : super.findHitEntity(p_37575_, p_37576_);
	}
	
	public boolean isFoil()
	{
		return this.entityData.get(IS_FOIL);
	}
	
	public boolean isReinforced()
	{
		return this.entityData.get(IS_REINFORCED);
	}
	
	@Override
	protected ItemStack getPickupItem()
	{
		return this.item.copy();
	}

	@Override
	public void readAdditionalSaveData(CompoundTag p_37578_) 
	{
		super.readAdditionalSaveData(p_37578_);
		if(p_37578_.contains("Harpoon", 10))
		{
			this.item = ItemStack.of(p_37578_.getCompound("Harpoon"));
		}
		this.dealtDamage = p_37578_.getBoolean("DealtDamage");
	}

	@Override
	public void addAdditionalSaveData(CompoundTag p_37582_) 
	{
		super.addAdditionalSaveData(p_37582_);
		p_37582_.put("Harpoon", this.item.save(new CompoundTag()));
		p_37582_.putBoolean("DealtDamage", this.dealtDamage);
	}
	
	@Override
	protected float getWaterInertia()
	{
		return 0.99F;
	}

	@Override
	public boolean shouldRender(double p_37588_, double p_37589_, double p_37590_) 
	{
		return true;
	}
}
