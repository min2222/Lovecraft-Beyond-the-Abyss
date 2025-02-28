package com.min01.beyondtheabyss.capabilities;

import org.apache.commons.lang3.tuple.Pair;

import com.min01.beyondtheabyss.network.BTANetwork;
import com.min01.beyondtheabyss.network.UpdatePlayerAnimationPacket;
import com.min01.beyondtheabyss.network.UpdatePlayerAnimationTickPacket;
import com.min01.beyondtheabyss.util.BTAUtil;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.network.PacketDistributor;

public class PlayerAnimationCapabilityImpl implements IPlayerAnimationCapability
{
    public static final String ANIMATION_TICK = "AnimationTick";
	private ListTag animations = new ListTag();
	private CompoundTag compoundTag = new CompoundTag();
	private LivingEntity entity;
	
	@Override
	public CompoundTag serializeNBT()
	{
		CompoundTag tag = new CompoundTag();
		tag.put("Animations", this.animations);
		tag.put("Tag", this.compoundTag);
		return tag;
	}

	@Override
	public void deserializeNBT(CompoundTag nbt)
	{
		this.compoundTag = nbt.getCompound("Tag");
		this.animations = nbt.getList("Animations", 10);
	}
	
	@Override
	public void setEntity(LivingEntity entity) 
	{
		this.entity = entity;
	}

	@Override
	public void update() 
	{
    	int tick = this.getAnimationTick();
    	if(tick > 0)
    	{
    		this.setAnimationTick(tick - 1);
    	}
	}

	@Override
    public int getAnimationTick()
    {
    	return this.compoundTag.getInt(ANIMATION_TICK);
    }

	@Override
    public void setAnimationTick(int tick)
    {
        this.compoundTag.putInt(ANIMATION_TICK, tick);
		this.sendUpdateTickPacket();
    }
	
	@Override
	public AnimationState getAnimationState(String name) 
	{
        return BTAUtil.readAnimationState(this.animations, name);
	}
	
	@Override
	public void setAnimationState(AnimationState state, String name)
	{
		BTAUtil.writeAnimationState(this.animations, state, name);
		this.sendUpdatePacket();
	}
	
	@Override
	public Pair<ListTag, CompoundTag> getTag()
	{
		return Pair.of(this.animations, this.compoundTag);
	}
	
	@Override
	public void setTag(Pair<ListTag, CompoundTag> pair)
	{
		this.animations = pair.getLeft();
		this.compoundTag = pair.getRight();
	}
	
	private void sendUpdateTickPacket() 
	{
		if(this.entity instanceof ServerPlayer)
		{
			BTANetwork.CHANNEL.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> this.entity), new UpdatePlayerAnimationTickPacket(this.entity.getUUID(), this));
		}
	}
	
	private void sendUpdatePacket() 
	{
		if(!this.entity.level.isClientSide)
		{
			BTANetwork.CHANNEL.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> this.entity), new UpdatePlayerAnimationPacket(this.entity.getUUID(), this));
		}
	}
}
