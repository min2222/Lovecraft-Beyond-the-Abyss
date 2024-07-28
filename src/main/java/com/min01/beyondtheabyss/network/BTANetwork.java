package com.min01.beyondtheabyss.network;

import com.min01.beyondtheabyss.BeyondtheAbyss;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;
import net.minecraftforge.server.ServerLifecycleHooks;

public class BTANetwork 
{
	private static final String PROTOCOL_VERSION = "1";
	public static final SimpleChannel CHANNEL = NetworkRegistry.newSimpleChannel(new ResourceLocation(BeyondtheAbyss.MODID, "beyondtheabyss"),
			() -> PROTOCOL_VERSION,
			PROTOCOL_VERSION::equals,
			PROTOCOL_VERSION::equals
	);
	
	public static int ID = 0;
	public static void registerMessages()
	{
		CHANNEL.registerMessage(ID++, ItemAnimationSyncPacket.class, ItemAnimationSyncPacket::encode, ItemAnimationSyncPacket::new, ItemAnimationSyncPacket.Handler::onMessage);
		CHANNEL.registerMessage(ID++, KeyInputPacket.class, KeyInputPacket::encode, KeyInputPacket::new, KeyInputPacket.Handler::onMessage);
		CHANNEL.registerMessage(ID++, BTAAbilitySyncPacket.class, BTAAbilitySyncPacket::encode, BTAAbilitySyncPacket::new, BTAAbilitySyncPacket.Handler::onMessage);
		CHANNEL.registerMessage(ID++, PartPositionUpdatePacket.class, PartPositionUpdatePacket::encode, PartPositionUpdatePacket::new, PartPositionUpdatePacket.Handler::onMessage);
		CHANNEL.registerMessage(ID++, SubmarinePartUpdatePacket.class, SubmarinePartUpdatePacket::encode, SubmarinePartUpdatePacket::new, SubmarinePartUpdatePacket.Handler::onMessage);
		CHANNEL.registerMessage(ID++, AltarItemSyncPacket.class, AltarItemSyncPacket::encode, AltarItemSyncPacket::new, AltarItemSyncPacket.Handler::onMessage);
		CHANNEL.registerMessage(ID++, VehicleUpdatePacket.class, VehicleUpdatePacket::encode, VehicleUpdatePacket::new, VehicleUpdatePacket.Handler::onMessage);
		CHANNEL.registerMessage(ID++, IllusionSyncPacket.class, IllusionSyncPacket::encode, IllusionSyncPacket::new, IllusionSyncPacket.Handler::onMessage);
		CHANNEL.registerMessage(ID++, IllusionAddPacket.class, IllusionAddPacket::encode, IllusionAddPacket::new, IllusionAddPacket.Handler::onMessage);
		CHANNEL.registerMessage(ID++, MultiPartBuildPacket.class, MultiPartBuildPacket::encode, MultiPartBuildPacket::new, MultiPartBuildPacket.Handler::onMessage);
		CHANNEL.registerMessage(ID++, MultiPartUpdatePacket.class, MultiPartUpdatePacket::encode, MultiPartUpdatePacket::new, MultiPartUpdatePacket.Handler::onMessage);
	}
	
    public static <MSG> void sendToServer(MSG message) 
    {
    	CHANNEL.sendToServer(message);
    }
    
    public static <MSG> void sendToAll(MSG message)
    {
    	for(ServerPlayer player : ServerLifecycleHooks.getCurrentServer().getPlayerList().getPlayers()) 
    	{
    		CHANNEL.sendTo(message, player.connection.connection, NetworkDirection.PLAY_TO_CLIENT);
    	}
    }
}
