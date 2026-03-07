package com.min01.beyondtheabyss.mixin;

import java.util.Map;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import com.min01.beyondtheabyss.misc.IOptimizedModelPart;

import net.minecraft.client.model.geom.ModelPart;

@Mixin(ModelPart.class)
public class MixinModelPart implements IOptimizedModelPart
{
	@Shadow
	@Final
	private Map<String, ModelPart> children;
	   
	@Override
	public Map<String, ModelPart> bta_getChildren()
	{
		return this.children;
	}
}
