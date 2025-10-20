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
		CHANNEL.registerMessage(ID++, UpdatePosArrayPacket.class, UpdatePosArrayPacket::encode, UpdatePosArrayPacket::new, UpdatePosArrayPacket.Handler::onMessage);
		CHANNEL.registerMessage(ID++, UpdateAltarItemPacket.class, UpdateAltarItemPacket::encode, UpdateAltarItemPacket::new, UpdateAltarItemPacket.Handler::onMessage);
		CHANNEL.registerMessage(ID++, UpdateVehiclePacket.class, UpdateVehiclePacket::encode, UpdateVehiclePacket::new, UpdateVehiclePacket.Handler::onMessage);
		CHANNEL.registerMessage(ID++, BuildMultipartPacket.class, BuildMultipartPacket::encode, BuildMultipartPacket::new, BuildMultipartPacket.Handler::onMessage);
 		CHANNEL.registerMessage(ID++, UpdatePartPacket.class, UpdatePartPacket::encode, UpdatePartPacket::new, UpdatePartPacket.Handler::onMessage);
		CHANNEL.registerMessage(ID++, UpdateItemAnimationPacket.class, UpdateItemAnimationPacket::encode, UpdateItemAnimationPacket::new, UpdateItemAnimationPacket.Handler::onMessage);
		CHANNEL.registerMessage(ID++, SetDialogueScreenPacket.class, SetDialogueScreenPacket::encode, SetDialogueScreenPacket::new, SetDialogueScreenPacket.Handler::onMessage);
		CHANNEL.registerMessage(ID++, UpdateSynchedEntityDataPacket.class, UpdateSynchedEntityDataPacket::encode, UpdateSynchedEntityDataPacket::new, UpdateSynchedEntityDataPacket.Handler::onMessage);
		CHANNEL.registerMessage(ID++, UpdatePlayerAnimationPacket.class, UpdatePlayerAnimationPacket::encode, UpdatePlayerAnimationPacket::new, UpdatePlayerAnimationPacket.Handler::onMessage);
		CHANNEL.registerMessage(ID++, UpdateAbyssPortalPosPacket.class, UpdateAbyssPortalPosPacket::encode, UpdateAbyssPortalPosPacket::new, UpdateAbyssPortalPosPacket.Handler::onMessage);
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
