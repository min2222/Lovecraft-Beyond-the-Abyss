package com.min01.beyondtheabyss.network;

import java.util.UUID;
import java.util.function.Supplier;

import com.min01.beyondtheabyss.effect.BTAEffects;
import com.min01.beyondtheabyss.util.BTAUtil;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.network.NetworkEvent;

public class UpdateStoneSkinEffectPacket 
{
	public final UUID entityUUID;
	public final int amplifier;
	public final int duration;
	public final boolean remove;

	public UpdateStoneSkinEffectPacket(UUID entityUUID, int amplifier, int duration, boolean remove) 
	{
		this.entityUUID = entityUUID;
		this.amplifier = amplifier;
		this.duration = duration;
		this.remove = remove;
	}

	public static UpdateStoneSkinEffectPacket read(FriendlyByteBuf buf)
	{
		return new UpdateStoneSkinEffectPacket(buf.readUUID(), buf.readInt(), buf.readInt(), buf.readBoolean());
	}

	public void write(FriendlyByteBuf buf)
	{
		buf.writeUUID(this.entityUUID);
		buf.writeInt(this.amplifier);
		buf.writeInt(this.duration);
		buf.writeBoolean(this.remove);
	}

	public static boolean handle(UpdateStoneSkinEffectPacket message, Supplier<NetworkEvent.Context> ctx)
	{
		ctx.get().enqueueWork(() ->
		{
			if(ctx.get().getDirection().getReceptionSide().isClient())
			{
				BTAUtil.getClientLevel(level -> 
				{
					Entity entity = BTAUtil.getEntityByUUID(level, message.entityUUID);
					if(entity instanceof LivingEntity living) 
					{
						if(!message.remove)
						{
							living.addEffect(new MobEffectInstance(BTAEffects.STONE_SKIN.get(), message.duration, message.amplifier));
						}
						else if(living.hasEffect(BTAEffects.STONE_SKIN.get()))
						{
							living.removeEffect(BTAEffects.STONE_SKIN.get());
						}
					}
				});
			}
		});
		ctx.get().setPacketHandled(true);
		return true;
	}
}