package com.min01.beyondtheabyss.mixin;

import java.util.List;
import java.util.Map;
import java.util.function.IntSupplier;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.mojang.blaze3d.shaders.Uniform;
import com.mojang.blaze3d.systems.RenderSystem;

import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;
import net.minecraft.client.renderer.EffectInstance;

@Mixin(EffectInstance.class)
public class MixinEffectInstance
{
	@Shadow
	private @Final Map<String, IntSupplier> samplerMap;
	@Shadow
	private @Final List<String> samplerNames;
	@Shadow
	private @Final List<Integer> samplerLocations;
	@Shadow
	private @Final List<Uniform> uniforms;
	@Shadow
	private @Final List<Integer> uniformLocations;
	@Shadow
	private @Final Map<String, Uniform> uniformMap;
	@Shadow
	private @Final int programId;
	   
	@Inject(at = @At("HEAD"), method = "updateLocations()V", cancellable = true)
	private void updateLocations(CallbackInfo ci) 
	{
		ci.cancel();
		RenderSystem.assertOnRenderThread();
		IntList intlist = new IntArrayList();

		for(int i = 0; i < this.samplerNames.size(); ++i) 
		{
			String s = this.samplerNames.get(i);
			int j = Uniform.glGetUniformLocation(this.programId, s);
			if (j == -1) 
			{
				this.samplerMap.remove(s);
				intlist.add(i);
			} 
			else
			{
				this.samplerLocations.add(j);
			}
		}

		for(int l = intlist.size() - 1; l >= 0; --l) 
		{
			this.samplerNames.remove(intlist.getInt(l));
		}

		for(Uniform uniform : this.uniforms)
		{
	         String s1 = uniform.getName();
	         int k = Uniform.glGetUniformLocation(this.programId, s1);
	         if (k == -1) 
	         {
	        	 
	         }
	         else
	         {
	        	 this.uniformLocations.add(k);
	        	 uniform.setLocation(k);
	        	 this.uniformMap.put(s1, uniform);
	         }
		}
	}
}
