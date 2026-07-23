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
	public static final String PROTOCOL_VERSION = "1";
	public static final SimpleChannel CHANNEL = NetworkRegistry.newSimpleChannel(ResourceLocation.fromNamespaceAndPath(BeyondtheAbyss.MODID, BeyondtheAbyss.MODID), () -> PROTOCOL_VERSION, PROTOCOL_VERSION::equals, PROTOCOL_VERSION::equals);
	
	public static void registerMessages()
	{
		int id = 0;
		CHANNEL.registerMessage(id++, UpdateModelPositionPacket.class, UpdateModelPositionPacket::write, UpdateModelPositionPacket::read, UpdateModelPositionPacket::handle);
		CHANNEL.registerMessage(id++, UpdateAltarItemPacket.class, UpdateAltarItemPacket::write, UpdateAltarItemPacket::read, UpdateAltarItemPacket::handle);
		CHANNEL.registerMessage(id++, UpdateVehiclePacket.class, UpdateVehiclePacket::write, UpdateVehiclePacket::read, UpdateVehiclePacket::handle);
		CHANNEL.registerMessage(id++, UpdateItemAnimationPacket.class, UpdateItemAnimationPacket::write, UpdateItemAnimationPacket::read, UpdateItemAnimationPacket::handle);
		CHANNEL.registerMessage(id++, SetDialogueScreenPacket.class, SetDialogueScreenPacket::write, SetDialogueScreenPacket::read, SetDialogueScreenPacket::handle);
		CHANNEL.registerMessage(id++, UpdateSynchedEntityDataPacket.class, UpdateSynchedEntityDataPacket::write, UpdateSynchedEntityDataPacket::read, UpdateSynchedEntityDataPacket::handle);
		CHANNEL.registerMessage(id++, UpdatePlayerAnimationPacket.class, UpdatePlayerAnimationPacket::write, UpdatePlayerAnimationPacket::read, UpdatePlayerAnimationPacket::handle);
		CHANNEL.registerMessage(id++, UpdateSkeletalGunbladeItemPacket.class, UpdateSkeletalGunbladeItemPacket::write, UpdateSkeletalGunbladeItemPacket::read, UpdateSkeletalGunbladeItemPacket::handle);
		CHANNEL.registerMessage(id++, UpdateStoneSkinEffectPacket.class, UpdateStoneSkinEffectPacket::write, UpdateStoneSkinEffectPacket::read, UpdateStoneSkinEffectPacket::handle);
		CHANNEL.registerMessage(id++, UpdateBossBarPacket.class, UpdateBossBarPacket::write, UpdateBossBarPacket::read, UpdateBossBarPacket::handle);
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
