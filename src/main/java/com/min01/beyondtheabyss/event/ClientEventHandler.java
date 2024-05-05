package com.min01.beyondtheabyss.event;

import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.block.BTABlocks;
import com.min01.beyondtheabyss.block.model.ModelAltarOfDeep;
import com.min01.beyondtheabyss.blockentity.renderer.BTABlockEntityRenderer;
import com.min01.beyondtheabyss.entity.BTAEntities;
import com.min01.beyondtheabyss.entity.misc.EntityBTACameraShake;
import com.min01.beyondtheabyss.entity.model.ModelAbyssalBulbray;
import com.min01.beyondtheabyss.entity.model.ModelAbyssalHermitCrab;
import com.min01.beyondtheabyss.entity.model.ModelDeepVampire;
import com.min01.beyondtheabyss.entity.model.ModelGhidruth;
import com.min01.beyondtheabyss.entity.model.ModelLatcher;
import com.min01.beyondtheabyss.entity.model.ModelRunicFish;
import com.min01.beyondtheabyss.entity.model.ModelSubmarine;
import com.min01.beyondtheabyss.entity.renderer.DeepAbyssPortalRenderer;
import com.min01.beyondtheabyss.entity.renderer.NoneRenderer;
import com.min01.beyondtheabyss.entity.renderer.SubmarineRenderer;
import com.min01.beyondtheabyss.entity.renderer.ThrownHarpoonRenderer;
import com.min01.beyondtheabyss.entity.renderer.layer.AbyssalDashLayer;
import com.min01.beyondtheabyss.entity.renderer.layer.AbyssalScaleLayer;
import com.min01.beyondtheabyss.entity.renderer.living.AbyssalBulbrayRenderer;
import com.min01.beyondtheabyss.entity.renderer.living.AbyssalHermitCrabRenderer;
import com.min01.beyondtheabyss.entity.renderer.living.DeepVampireRenderer;
import com.min01.beyondtheabyss.entity.renderer.living.GhidruthRenderer;
import com.min01.beyondtheabyss.entity.renderer.living.LatcherRenderer;
import com.min01.beyondtheabyss.entity.renderer.living.RunicFishRenderer;
import com.min01.beyondtheabyss.entity.submarine.SubmarinePart;
import com.min01.beyondtheabyss.item.BTAItems;
import com.min01.beyondtheabyss.item.deepabyss.ItemGuidingClam;
import com.min01.beyondtheabyss.item.model.ModelAdvancedDiverSet;
import com.min01.beyondtheabyss.item.model.ModelDiverSet;
import com.min01.beyondtheabyss.item.model.ModelGhidruthDiverSet;
import com.min01.beyondtheabyss.item.model.ModelGhidruthHarpoon;
import com.min01.beyondtheabyss.item.model.ModelHarpoon;
import com.min01.beyondtheabyss.item.model.SimpleBakedModelWrapper;
import com.min01.beyondtheabyss.misc.BTARenderType;
import com.min01.beyondtheabyss.particle.BTAParticles;
import com.min01.beyondtheabyss.particle.ShockwaveParticle;
import com.min01.beyondtheabyss.shader.BTAShaders;
import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.datafixers.util.Pair;

import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.BlockModelRotation;
import net.minecraft.client.resources.model.Material;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.ModelEvent;
import net.minecraftforge.client.event.RegisterClientReloadListenersEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.client.event.RegisterShadersEvent;
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
        BlockEntityRenderers.register(BTABlocks.ALTAR_OF_DEEP_BLOCK_ENTITY.get(), BTABlockEntityRenderer::new);
        ItemProperties.register(BTAItems.RUSTY_HARPOON.get(), new ResourceLocation("throwing"), (p_174585_, p_174586_, p_174587_, p_174588_) ->
        {
        	return p_174587_ != null && p_174587_.isUsingItem() && p_174587_.getUseItem() == p_174585_ ? 1.0F : 0.0F;
        });
        ItemProperties.register(BTAItems.GHIDRUTH_SCALE_HARPOON.get(), new ResourceLocation("throwing"), (p_174585_, p_174586_, p_174587_, p_174588_) ->
        {
        	return p_174587_ != null && p_174587_.isUsingItem() && p_174587_.getUseItem() == p_174585_ ? 1.0F : 0.0F;
        });
        ItemProperties.register(BTAItems.GUIDING_CLAM.get(), new ResourceLocation("open"), (p_174585_, p_174586_, p_174587_, p_174588_) ->
        {
        	return ItemGuidingClam.isOpen(p_174585_) ? 1.0F : 0.0F;
        });
    }
    
    @SubscribeEvent
    public static void registerShaders(RegisterShadersEvent event)
    {
        for(Pair<ShaderInstance, Consumer<ShaderInstance>> pair : BTARenderType.registerShaders(event.getResourceManager())) 
        {
            event.registerShader(pair.getFirst(), pair.getSecond());
        }
    }
    
	@SubscribeEvent
	public static void registerParticleProviders(RegisterParticleProvidersEvent event)
	{
		event.register(BTAParticles.SHOCKWAVE.get(), ShockwaveParticle.Provider::new);
	}
    
	@SubscribeEvent
	public static void onModelRegistry(ModelEvent.RegisterAdditional event)
	{
		event.register(new ModelResourceLocation(new ResourceLocation(BeyondtheAbyss.MODID, "ghidruth_scale_harpoon_in_hand"), "inventory"));
	}
    
    @SubscribeEvent
    public static void modelBake(ModelEvent.BakingCompleted event)
    {
    	registerItemModel(event, "ghidruth_scale_harpoon");
    }
    
    public static void registerItemModel(ModelEvent.BakingCompleted event, String model)
    {
    	ModelResourceLocation loc = new ModelResourceLocation(new ResourceLocation(BeyondtheAbyss.MODID, model), "inventory");
    	ModelResourceLocation modelLoc = new ModelResourceLocation(new ResourceLocation(BeyondtheAbyss.MODID, model + "_in_hand"), "inventory");
    	BakedModel bakedModel = event.getModelBakery().getModel(modelLoc).bake(event.getModelBakery(), Material::sprite, BlockModelRotation.X0_Y0, new ResourceLocation(BeyondtheAbyss.MODID, model));
    	event.getModels().replace(loc, new SimpleBakedModelWrapper(event.getModels().get(loc), bakedModel));
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
    	event.registerEntityRenderer(BTAEntities.SUBMARINE.get(), SubmarineRenderer::new);
    	event.registerEntityRenderer(BTAEntities.SUBMARINE_PART.get(), NoneRenderer<SubmarinePart>::new);
    	
    	//projectile
    	event.registerEntityRenderer(BTAEntities.THROWN_HARPOON.get(), ThrownHarpoonRenderer::new);
    	
    	//living
    	event.registerEntityRenderer(BTAEntities.GHIDRUTH.get(), GhidruthRenderer::new);
    	event.registerEntityRenderer(BTAEntities.DEEP_VAMPIRE.get(), DeepVampireRenderer::new);
    	event.registerEntityRenderer(BTAEntities.RUNIC_FISH.get(), RunicFishRenderer::new);
    	event.registerEntityRenderer(BTAEntities.LATCHER.get(), LatcherRenderer::new);
    	event.registerEntityRenderer(BTAEntities.ABYSSAL_HERMIT_CRAB.get(), AbyssalHermitCrabRenderer::new);
    	event.registerEntityRenderer(BTAEntities.ABYSSAL_BULBRAY.get(), AbyssalBulbrayRenderer::new);
    }
    
    @SubscribeEvent
    public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event)
    {
    	event.registerLayerDefinition(ModelGhidruth.LAYER_LOCATION, ModelGhidruth::createBodyLayer);
    	event.registerLayerDefinition(ModelDeepVampire.LAYER_LOCATION, ModelDeepVampire::createBodyLayer);
    	event.registerLayerDefinition(ModelRunicFish.LAYER_LOCATION, ModelRunicFish::createBodyLayer);
    	event.registerLayerDefinition(ModelLatcher.LAYER_LOCATION, ModelLatcher::createBodyLayer);
    	event.registerLayerDefinition(ModelAbyssalHermitCrab.LAYER_LOCATION, ModelAbyssalHermitCrab::createBodyLayer);
    	event.registerLayerDefinition(ModelAbyssalBulbray.LAYER_LOCATION, ModelAbyssalBulbray::createBodyLayer);
    	event.registerLayerDefinition(ModelSubmarine.LAYER_LOCATION, ModelSubmarine::createBodyLayer);
    	
    	event.registerLayerDefinition(ModelDiverSet.LAYER_LOCATION, ModelDiverSet::createBodyLayer);
    	event.registerLayerDefinition(ModelAdvancedDiverSet.LAYER_LOCATION, ModelAdvancedDiverSet::createBodyLayer);
    	event.registerLayerDefinition(ModelGhidruthDiverSet.LAYER_LOCATION, ModelGhidruthDiverSet::createBodyLayer);
    	
    	event.registerLayerDefinition(ModelAltarOfDeep.LAYER_LOCATION, ModelAltarOfDeep::createBodyLayer);
    	
    	event.registerLayerDefinition(ModelHarpoon.LAYER_LOCATION, ModelHarpoon::createBodyLayer);
    	event.registerLayerDefinition(ModelGhidruthHarpoon.LAYER_LOCATION, ModelGhidruthHarpoon::createBodyLayer);
    	
    	event.registerLayerDefinition(AbyssalDashLayer.LAYER_LOCATION, AbyssalDashLayer::createLayer);
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
		renderer.addLayer(new AbyssalScaleLayer<>(renderer));
	}
}
