package com.min01.beyondtheabyss.mixin.packet;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

import net.minecraft.network.protocol.game.ClientboundCustomPayloadPacket;

@Mixin(ClientboundCustomPayloadPacket.class)
public class MixinClientboundCustomPayloadPacket
{
    @ModifyConstant(method = {"<init>*"}, constant = @Constant(intValue = 1048576))
    private int init(int constant)
    {
        return 2147483647;
    }
}
