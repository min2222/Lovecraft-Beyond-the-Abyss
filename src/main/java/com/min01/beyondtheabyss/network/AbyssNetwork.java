package com.min01.beyondtheabyss.network;

import com.min01.beyondtheabyss.BeyondtheAbyss;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

public class AbyssNetwork 
{
	private static final String PROTOCOL_VERSION = "1";
	public static final SimpleChannel CHANNEL = NetworkRegistry.newSimpleChannel(
			new ResourceLocation(BeyondtheAbyss.MODID, "abyssal_channel"),
			() -> PROTOCOL_VERSION,
			PROTOCOL_VERSION::equals,
			PROTOCOL_VERSION::equals
	);
	
	public static int ID = 0;
	public static void registerMessages()
	{
		CHANNEL.registerMessage(ID++, ItemAnimationSyncPacket.class, ItemAnimationSyncPacket::encode, ItemAnimationSyncPacket::new, ItemAnimationSyncPacket.Handler::onMessage);
		CHANNEL.registerMessage(ID++, KeyInputPacket.class, KeyInputPacket::encode, KeyInputPacket::new, KeyInputPacket.Handler::onMessage);
		CHANNEL.registerMessage(ID++, ArmorAbilitySyncPacket.class, ArmorAbilitySyncPacket::encode, ArmorAbilitySyncPacket::new, ArmorAbilitySyncPacket.Handler::onMessage);
		CHANNEL.registerMessage(ID++, ModelPosSyncPacket.class, ModelPosSyncPacket::encode, ModelPosSyncPacket::new, ModelPosSyncPacket.Handler::onMessage);
	}
}
