package com.min01.beyondtheabyss.network;

import com.min01.beyondtheabyss.BeyondtheAbyss;

public class AbyssNetwork 
{
	public static int id = 0;
	public static void registerMessages()
	{
		BeyondtheAbyss.CHANNEL.registerMessage(id++, ItemAnimationSyncPacket.class, ItemAnimationSyncPacket::encode, ItemAnimationSyncPacket::new, ItemAnimationSyncPacket.Handler::onMessage);
		BeyondtheAbyss.CHANNEL.registerMessage(id++, KeyInputPacket.class, KeyInputPacket::encode, KeyInputPacket::new, KeyInputPacket.Handler::onMessage);
		BeyondtheAbyss.CHANNEL.registerMessage(id++, ArmorAbilitySyncPacket.class, ArmorAbilitySyncPacket::encode, ArmorAbilitySyncPacket::new, ArmorAbilitySyncPacket.Handler::onMessage);
	}
}
