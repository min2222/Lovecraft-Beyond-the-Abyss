package com.min01.beyondtheabyss.entity.deepabyss;

import java.util.List;

import com.min01.beyondtheabyss.util.BTAUtil;
import com.min01.beyondtheabyss.world.BTAWorlds;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkHooks;

public class EntityDeepAbyssPortal extends Entity
{
	public EntityDeepAbyssPortal(EntityType<?> p_19870_, Level p_19871_)
	{
		super(p_19870_, p_19871_);
	}
	
	@Override
	public void tick() 
	{
		super.tick();
		
		if(this.isEyeInFluidType(Fluids.WATER.getFluidType()) && this.tickCount > 35)
		{
			if (this.random.nextInt(200) == 0) 
			{
				this.level.playLocalSound(this.getX(), this.getY(), this.getZ(), SoundEvents.BUBBLE_COLUMN_WHIRLPOOL_AMBIENT, SoundSource.MASTER, 0.2F + this.random.nextFloat() * 0.2F, 0.9F + this.random.nextFloat() * 0.15F, false);
			}
			
			for(int i = 0; i < 5; i++)
			{
	    		double spawnRange = this.getBbWidth();
	            double x = (double) this.getX() + (this.level.random.nextDouble() - this.level.random.nextDouble()) * (double)spawnRange;
	            double z = (double) this.getZ() + (this.level.random.nextDouble() - this.level.random.nextDouble()) * (double)spawnRange;
				this.level.addAlwaysVisibleParticle(ParticleTypes.BUBBLE_COLUMN_UP, x, this.getY(), z, 0, 0.05F, 0);
			}
		}
		
		List<Entity> list = this.level.getEntitiesOfClass(Entity.class, this.getBoundingBox().inflate(6));
		for(int i = 0; i < list.size(); i++)
		{
			Entity entity = list.get(i);
			if(entity != this)
			{
				if(entity.isEyeInFluidType(Fluids.WATER.getFluidType()) && entity.distanceTo(this) > 2F)
				{
					entity.setDeltaMovement(BTAUtil.moveToEntity(entity.position(), this.position(), entity, this, 15.0F));
				}
				
				if(entity.distanceTo(this) <= 2F)
				{
			        boolean abyss = entity.getLevel().dimension().location().getPath().equals("deep_abyss");
			        if (!entity.level.isClientSide) 
			        {
			            MinecraftServer server = entity.level.getServer();
			            ServerLevel dimWorld = server.getLevel(BTAWorlds.DEEP_ABYSS);
			            if (!abyss && dimWorld != null)
			            {
			            	if(entity instanceof ServerPlayer)
			            	{
			                    ServerPlayer thePlayer = (ServerPlayer) entity;
			                    BTAUtil.teleportEntity(thePlayer, dimWorld, new BlockPos(entity.getX(), 500, entity.getZ()));
			            	}
			            	else
			            	{
			                    BTAUtil.teleportEntity(entity, dimWorld, new BlockPos(entity.getX(), 500, entity.getZ()));
			            	}
			            }
			        }
				}
			}
		}
	}
	
	@Override
	public void setDeltaMovement(Vec3 p_20257_) 
	{
		//prevent portal moving
	}
	
	@Override
	public Vec3 getDeltaMovement()
	{
		//prevent portal moving
		return Vec3.ZERO;
	}

	@Override
	protected void defineSynchedData()
	{
		
	}

	@Override
	protected void readAdditionalSaveData(CompoundTag p_20052_)
	{
		
	}

	@Override
	protected void addAdditionalSaveData(CompoundTag p_20139_) 
	{
		
	}

	@Override
	public Packet<?> getAddEntityPacket() 
	{
		return NetworkHooks.getEntitySpawningPacket(this);
	}
}
