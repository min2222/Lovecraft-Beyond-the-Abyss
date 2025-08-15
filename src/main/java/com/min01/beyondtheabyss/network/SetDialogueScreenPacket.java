package com.min01.beyondtheabyss.network;

import java.util.UUID;
import java.util.function.Supplier;

import com.min01.beyondtheabyss.gui.screen.DialogueScreen;
import com.min01.beyondtheabyss.util.BTAClientUtil;
import com.min01.beyondtheabyss.util.BTAUtil;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkEvent;

public class SetDialogueScreenPacket 
{
	public final String key;
	public final int maxIndex;
	public final UUID entityUUID;

	public SetDialogueScreenPacket(String key, int maxIndex, Entity entity)
	{
		this.key = key;
		this.maxIndex = maxIndex;
		this.entityUUID = entity.getUUID();
	}

	public SetDialogueScreenPacket(FriendlyByteBuf buf)
	{
		this.key = buf.readUtf();
		this.maxIndex = buf.readInt();
		this.entityUUID = buf.readUUID();
	}

	public void encode(FriendlyByteBuf buf)
	{
		buf.writeUtf(this.key);
		buf.writeInt(this.maxIndex);
		buf.writeUUID(this.entityUUID);
	}

	public static class Handler 
	{
		public static boolean onMessage(SetDialogueScreenPacket message, Supplier<NetworkEvent.Context> ctx)
		{
			ctx.get().enqueueWork(() ->
			{
				if(ctx.get().getDirection().getReceptionSide().isClient()) 
				{
					openScreen(message);
				}
			});
			ctx.get().setPacketHandled(true);
			return true;
		}
		
		@OnlyIn(Dist.CLIENT)
		public static void openScreen(SetDialogueScreenPacket message)
		{
			BTAUtil.getClientLevel(t -> 
			{
				Entity entity = BTAUtil.getEntityByUUID(t, message.entityUUID);
				if(entity != null)
				{
					BTAClientUtil.MC.setScreen(new DialogueScreen(message.key, message.maxIndex, entity));
				}
			});
		}
	}
}
