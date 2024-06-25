package com.min01.beyondtheabyss.capabilities;

import com.min01.beyondtheabyss.network.BTANetwork;
import com.min01.beyondtheabyss.network.IllusionSyncPacket;

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
		//this.sendUpdatePacket();
	}
	
	@Override
	public Entity getIllusion() 
	{
		return this.illusion;
	}
	
	@Override
	public void removeIllusion(Entity entity)
	{
		this.sendUpdatePacket();
	}

	@Override
	public void tickIllusion()
	{
		if(this.illusion != null)
		{
			this.illusion.xOld = this.illusion.position().x;
			this.illusion.yOld = this.illusion.position().y;
			this.illusion.zOld = this.illusion.position().z;
			this.illusion.tick();
		}
	}
	
	private void sendUpdatePacket() 
	{
		if(this.entity instanceof ServerPlayer)
		{
			BTANetwork.CHANNEL.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> this.entity), new IllusionSyncPacket(this.entity, this));
		}
	}
}
