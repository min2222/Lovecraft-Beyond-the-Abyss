package com.min01.beyondtheabyss.capabilities;

import com.min01.beyondtheabyss.network.BTANetwork;
import com.min01.beyondtheabyss.network.UpdateIllusionPacket;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.network.PacketDistributor;

public class IllusionImpl implements IllusionCapability
{
	private LivingEntity entity;
	private Entity illusion;
	
	@Override
	public CompoundTag serializeNBT() 
	{
		CompoundTag tag = new CompoundTag();
		return tag;
	}

	@Override
	public void deserializeNBT(CompoundTag nbt) 
	{
		
	}

	@Override
	public void setEntity(LivingEntity entity)
	{
		this.entity = entity;
	}
	
	@Override
	public void setIllusion(Entity entity) 
	{
		this.illusion = entity;
		this.illusion.setPos(this.entity.position());
	}
	
	@Override
	public Entity getIllusion() 
	{
		return this.illusion;
	}
	
	@Override
	public void removeIllusion(Entity entity)
	{
		
	}

	@Override
	public void tickIllusion()
	{
		if(this.illusion != null)
		{
			this.illusion.setOldPosAndRot();
			this.illusion.tick();
			this.sendSyncPacket();
		}
	}
	
	private void sendSyncPacket() 
	{
		if(this.entity instanceof ServerPlayer)
		{
			BTANetwork.CHANNEL.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> this.entity), new UpdateIllusionPacket(this.entity));
		}
	}
}
