package com.min01.beyondtheabyss.network;

import java.util.function.Supplier;

import com.min01.beyondtheabyss.blockentity.deepabyss.BlockEntityAbyssalAltar;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkEvent;

public class AltarItemSyncPacket 
{
	private final int entityId;
	private final ItemStack stack;
	private final BlockPos pos;

	public AltarItemSyncPacket(Entity entity, ItemStack stack, BlockPos pos) 
	{
		this.entityId = entity.getId();
		this.stack = stack;
		this.pos = pos;
	}

	public AltarItemSyncPacket(FriendlyByteBuf buf)
	{
		this.entityId = buf.readInt();
		this.stack = buf.readItem();
		this.pos = buf.readBlockPos();
	}

	public void encode(FriendlyByteBuf buf)
	{
		buf.writeInt(this.entityId);
		buf.writeItem(this.stack);
		buf.writeBlockPos(this.pos);
	}

	public static class Handler 
	{
		public static boolean onMessage(AltarItemSyncPacket message, Supplier<NetworkEvent.Context> ctx)
		{
			ctx.get().enqueueWork(() ->
			{
				if(ctx.get().getDirection().getReceptionSide().isClient()) 
				{
					Minecraft.getInstance().doRunTask(() -> 
					{
						Minecraft mc = Minecraft.getInstance();
						Level level = mc.level;
						Entity entity = level.getEntity(message.entityId);
						if(entity.level.getBlockEntity(message.pos) instanceof BlockEntityAbyssalAltar altar)
						{
							altar.setItem(message.stack);
						}
					});
				}
			});
			ctx.get().setPacketHandled(true);
			return true;
		}
	}
}
