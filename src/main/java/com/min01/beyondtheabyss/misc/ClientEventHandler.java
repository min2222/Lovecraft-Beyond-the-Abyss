package com.min01.beyondtheabyss.misc;

import java.util.Map;
import java.util.Objects;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.block.BTABlocks;
import com.min01.beyondtheabyss.block.model.ModelAltarOfDeep;
import com.min01.beyondtheabyss.blockentity.renderer.AltarOfDeepRenderer;
import com.min01.beyondtheabyss.entity.BTAEntities;
import com.min01.beyondtheabyss.entity.EntityBTACameraShake;
import com.min01.beyondtheabyss.entity.model.ModelGhidruth;
import com.min01.beyondtheabyss.entity.renderer.DeepAbyssPortalRenderer;
import com.min01.beyondtheabyss.entity.renderer.NoneRenderer;
import com.min01.beyondtheabyss.entity.renderer.layers.AbyssalDashLayer;
import com.min01.beyondtheabyss.entity.renderer.layers.GhidruthScaleArmorLayer;
import com.min01.beyondtheabyss.entity.renderer.living.GhidruthRenderer;
import com.min01.beyondtheabyss.item.model.ModelAdvancedDiverSet;
import com.min01.beyondtheabyss.item.model.ModelDiverSet;
import com.min01.beyondtheabyss.item.model.ModelGhidruthDiverSet;
import com.min01.beyondtheabyss.shaders.BTAShaders;
import com.mojang.blaze3d.platform.InputConstants;

import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterClientReloadListenersEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.util.ObfuscationReflectionHelper;

@Mod.EventBusSubscriber(modid = BeyondtheAbyss.MODID, value = Dist.CLIENT, bus = Bus.MOD)
public class ClientEventHandler
{
	public static final Minecraft MC = Minecraft.getInstance();
	public static final KeyMapping ABYSSAL_DASH = new KeyMapping("key." + BeyondtheAbyss.MODID + ".abyssal_dash", KeyConflictContext.IN_GAME, InputConstants.Type.KEYSYM, InputConstants.KEY_Y, "key.categories." + BeyondtheAbyss.MODID);
	
    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event)
    {
        BlockEntityRenderers.register(BTABlocks.ALTAR_OF_DEEP_BLOCK_ENTITY.get(), AltarOfDeepRenderer::new);
    }
    
	@SubscribeEvent
	public static void registerReloadListeners(RegisterClientReloadListenersEvent e)
	{
		e.registerReloadListener(new BTAShaders());
	}
	
    @SubscribeEvent
    public static void entityRenderers(EntityRenderersEvent.RegisterRenderers event)
    {
    	//misc
    	event.registerEntityRenderer(BTAEntities.DEEP_ABYSS_PORTAL.get(), DeepAbyssPortalRenderer::new);
    	event.registerEntityRenderer(BTAEntities.BTA_CAMERA_SHAKE.get(), NoneRenderer<EntityBTACameraShake>::new);
    	
    	//living
    	event.registerEntityRenderer(BTAEntities.GHIDRUTH.get(), GhidruthRenderer::new);
    }
    
    @SubscribeEvent
    public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event)
    {
    	event.registerLayerDefinition(ModelGhidruth.LAYER_LOCATION, ModelGhidruth::createBodyLayer);
    	event.registerLayerDefinition(ModelDiverSet.LAYER_LOCATION, ModelDiverSet::createBodyLayer);
    	event.registerLayerDefinition(ModelAdvancedDiverSet.LAYER_LOCATION, ModelAdvancedDiverSet::createBodyLayer);
    	event.registerLayerDefinition(ModelGhidruthDiverSet.LAYER_LOCATION, ModelGhidruthDiverSet::createBodyLayer);
    	event.registerLayerDefinition(AbyssalDashLayer.LAYER_LOCATION, AbyssalDashLayer::createLayer);
    	event.registerLayerDefinition(ModelAltarOfDeep.LAYER_LOCATION, ModelAltarOfDeep::createBodyLayer);
    }
    
    @SubscribeEvent
    public static void registerKeyBindings(RegisterKeyMappingsEvent event)
    {
    	event.register(ABYSSAL_DASH);
    }
    
	@SubscribeEvent
	public static void layerRendering(EntityRenderersEvent.AddLayers event)
	{
		Map<EntityType<?>, EntityRenderer<?>> renderers = ObfuscationReflectionHelper.getPrivateValue(EntityRenderersEvent.AddLayers.class, event, "renderers");
		renderers.values().stream()
		.filter(LivingEntityRenderer.class::isInstance)
		.map(LivingEntityRenderer.class::cast)
		.forEach(ClientEventHandler::attachRenderLayers);
		
		event.getSkins().forEach(renderer -> 
		{
			LivingEntityRenderer<Player, EntityModel<Player>> skin = event.getSkin(renderer);
			attachRenderLayers(Objects.requireNonNull(skin));
		});
	}
	
	private static <T extends LivingEntity, M extends EntityModel<T>> void attachRenderLayers(LivingEntityRenderer<T, M> renderer)
	{
		renderer.addLayer(new AbyssalDashLayer<>(renderer));
		renderer.addLayer(new GhidruthScaleArmorLayer<>(renderer));
	}
}
