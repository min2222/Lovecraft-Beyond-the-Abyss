package com.min01.beyondtheabyss.mixin.packet;

import net.minecraft.network.CompressionDecoder;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(CompressionDecoder.class)
public class MixinCompressionDecoder
{
	@ModifyConstant(method = "decode",constant = @Constant(intValue = CompressionDecoder.MAXIMUM_UNCOMPRESSED_LENGTH))
	private int decode(int old) 
	{
		return 2_147_483_647;
	}
}
