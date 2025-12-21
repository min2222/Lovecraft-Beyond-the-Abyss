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
	public static final SimpleChannel CHANNEL = NetworkRegistry.newSimpleChannel(ResourceLocation.fromNamespaceAndPath(BeyondtheAbyss.MODID, BeyondtheAbyss.MODID),
			() -> PROTOCOL_VERSION,
			PROTOCOL_VERSION::equals,
			PROTOCOL_VERSION::equals
	);
	
	public static int ID = 0;
	public static void registerMessages()
	{
		CHANNEL.registerMessage(ID++, UpdatePosArrayPacket.class, UpdatePosArrayPacket::write, UpdatePosArrayPacket::read, UpdatePosArrayPacket::handle);
		CHANNEL.registerMessage(ID++, UpdateAltarItemPacket.class, UpdateAltarItemPacket::write, UpdateAltarItemPacket::read, UpdateAltarItemPacket::handle);
		CHANNEL.registerMessage(ID++, UpdateVehiclePacket.class, UpdateVehiclePacket::write, UpdateVehiclePacket::read, UpdateVehiclePacket::handle);
		CHANNEL.registerMessage(ID++, BuildMultipartPacket.class, BuildMultipartPacket::write, BuildMultipartPacket::read, BuildMultipartPacket::handle);
 		CHANNEL.registerMessage(ID++, UpdatePartPacket.class, UpdatePartPacket::write, UpdatePartPacket::read, UpdatePartPacket::handle);
		CHANNEL.registerMessage(ID++, UpdateItemAnimationPacket.class, UpdateItemAnimationPacket::write, UpdateItemAnimationPacket::read, UpdateItemAnimationPacket::handle);
		CHANNEL.registerMessage(ID++, SetDialogueScreenPacket.class, SetDialogueScreenPacket::write, SetDialogueScreenPacket::read, SetDialogueScreenPacket::handle);
		CHANNEL.registerMessage(ID++, UpdateSynchedEntityDataPacket.class, UpdateSynchedEntityDataPacket::write, UpdateSynchedEntityDataPacket::read, UpdateSynchedEntityDataPacket::handle);
		CHANNEL.registerMessage(ID++, UpdatePlayerAnimationPacket.class, UpdatePlayerAnimationPacket::write, UpdatePlayerAnimationPacket::read, UpdatePlayerAnimationPacket::handle);
		CHANNEL.registerMessage(ID++, UpdateAbyssPortalPosPacket.class, UpdateAbyssPortalPosPacket::write, UpdateAbyssPortalPosPacket::read, UpdateAbyssPortalPosPacket::handle);
		CHANNEL.registerMessage(ID++, UpdateAbyssPortalActivationPacket.class, UpdateAbyssPortalActivationPacket::write, UpdateAbyssPortalActivationPacket::read, UpdateAbyssPortalActivationPacket::handle);
		CHANNEL.registerMessage(ID++, UpdateSkeletalGunbladeItemPacket.class, UpdateSkeletalGunbladeItemPacket::write, UpdateSkeletalGunbladeItemPacket::read, UpdateSkeletalGunbladeItemPacket::handle);
		CHANNEL.registerMessage(ID++, UpdateStoneSkinEffectPacket.class, UpdateStoneSkinEffectPacket::write, UpdateStoneSkinEffectPacket::read, UpdateStoneSkinEffectPacket::handle);
		CHANNEL.registerMessage(ID++, UpdateBossBarPacket.class, UpdateBossBarPacket::write, UpdateBossBarPacket::read, UpdateBossBarPacket::handle);
	}
	
    public static <MSG> void sendToServer(MSG message) 
    {
    	CHANNEL.sendToServer(message);
    }
    
    public static <MSG> void sendNonLocal(MSG msg, ServerPlayer player) 
    {
        CHANNEL.sendTo(msg, player.connection.connection, NetworkDirection.PLAY_TO_CLIENT);
    }
    
    public static <MSG> void sendToAll(MSG message)
    {
    	for(ServerPlayer player : ServerLifecycleHooks.getCurrentServer().getPlayerList().getPlayers()) 
    	{
    		CHANNEL.sendTo(message, player.connection.connection, NetworkDirection.PLAY_TO_CLIENT);
    	}
    }
}
