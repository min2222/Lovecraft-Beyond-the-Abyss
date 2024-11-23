package com.min01.beyondtheabyss.mixin;

import java.util.function.Consumer;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.mojang.datafixers.util.Pair;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Climate;
import net.minecraft.world.level.biome.OverworldBiomeBuilder;

@Mixin(OverworldBiomeBuilder.class)
public class MixinOverworldBiomeBuilder
{
	@Shadow
	@Final
	private Climate.Parameter FULL_RANGE;
	
	@Shadow
	@Final
	private Climate.Parameter coastContinentalness;

	@Shadow
	@Final
	private Climate.Parameter[] erosions;
	
    @Inject(method = "addBiomes", at = @At("HEAD"), cancellable = true)
    private void addBiomes(Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> p_187176_, CallbackInfo ci)
    {
    	//TODO custom biome in overworld;
    	//this.addUndergroundBiome(p_187176_, this.FULL_RANGE, Climate.Parameter.span(0.7F, 1.0F), this.FULL_RANGE, this.FULL_RANGE, this.FULL_RANGE, 0.0F, BTABiomes.SPIRE_HOLLOW.getKey());
    }
    
    @Shadow
    private void addUndergroundBiome(Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> p_187201_, Climate.Parameter p_187202_, Climate.Parameter p_187203_, Climate.Parameter p_187204_, Climate.Parameter p_187205_, Climate.Parameter p_187206_, float p_187207_, ResourceKey<Biome> p_187208_)
    {
    	
    }
    
    @Shadow
    private void addSurfaceBiome(Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> p_187181_, Climate.Parameter p_187182_, Climate.Parameter p_187183_, Climate.Parameter p_187184_, Climate.Parameter p_187185_, Climate.Parameter p_187186_, float p_187187_, ResourceKey<Biome> p_187188_)
    {
    	
    }
}
