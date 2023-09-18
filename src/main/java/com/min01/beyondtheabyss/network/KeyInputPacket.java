package com.min01.beyondtheabyss.network;

import java.util.function.Supplier;

import com.min01.beyondtheabyss.util.DeepAbyssUtil;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

public class KeyInputPacket
{
    private InputType inputType;

    public enum InputType 
    {
        ABYSSAL_DASH
    }

    public KeyInputPacket(InputType type)
    {
        this.inputType = type;
    }

    public KeyInputPacket(FriendlyByteBuf buf)
    {
        this.inputType = InputType.values()[buf.readInt()];
    }

    public void encode(FriendlyByteBuf buf)
    {
        buf.writeInt(inputType.ordinal());
    }
    
    public static class Handler
    {
        public static boolean onMessage(KeyInputPacket message, Supplier<NetworkEvent.Context> ctx)
        {
            ctx.get().enqueueWork(() ->
            {
                ServerPlayer player = ctx.get().getSender();
                if(player != null)
                {
                    switch(message.inputType) 
                    {
					case ABYSSAL_DASH:
						if(DeepAbyssUtil.isAbyssalDash(player))
						{
							DeepAbyssUtil.startAbyssalDash(player);
						}
						break;
					default:
						break;
                    }
                }
            });
            ctx.get().setPacketHandled(true);
            return true;
        }
    }
}
