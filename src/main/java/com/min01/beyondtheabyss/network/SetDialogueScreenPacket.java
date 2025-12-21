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

	public SetDialogueScreenPacket(String key, int maxIndex, UUID entityUUID)
	{
		this.key = key;
		this.maxIndex = maxIndex;
		this.entityUUID = entityUUID;
	}

	public static SetDialogueScreenPacket read(FriendlyByteBuf buf)
	{
		return new SetDialogueScreenPacket(buf.readUtf(), buf.readInt(), buf.readUUID());
	}

	public void write(FriendlyByteBuf buf)
	{
		buf.writeUtf(this.key);
		buf.writeInt(this.maxIndex);
		buf.writeUUID(this.entityUUID);
	}

	public static boolean handle(SetDialogueScreenPacket message, Supplier<NetworkEvent.Context> ctx)
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
