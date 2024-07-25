package com.min01.beyondtheabyss.event;

import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.block.BTABlocks;
import com.min01.beyondtheabyss.block.model.ModelBonePiles;
import com.min01.beyondtheabyss.block.model.ModelFallenSkeleton;
import com.min01.beyondtheabyss.block.model.ModelFangSkull;
import com.min01.beyondtheabyss.block.model.ModelLargeSkull;
import com.min01.beyondtheabyss.block.model.ModelRiftwellingAltar;
import com.min01.beyondtheabyss.block.model.ModelSittingSkeleton;
import com.min01.beyondtheabyss.blockentity.renderer.NoRotationLimitRenderer;
import com.min01.beyondtheabyss.blockentity.renderer.RiftwellingAltarRenderer;
import com.min01.beyondtheabyss.entity.BTAEntities;
import com.min01.beyondtheabyss.entity.misc.EntityBTACameraShake;
import com.min01.beyondtheabyss.entity.model.ModelAbyssalBulbray;
import com.min01.beyondtheabyss.entity.model.ModelAbyssalHermitCrab;
import com.min01.beyondtheabyss.entity.model.ModelAmarumGhost;
import com.min01.beyondtheabyss.entity.model.ModelDeepVampire;
import com.min01.beyondtheabyss.entity.model.ModelGhidruth;
import com.min01.beyondtheabyss.entity.model.ModelLatcher;
import com.min01.beyondtheabyss.entity.model.ModelPhasmozoa;
import com.min01.beyondtheabyss.entity.model.ModelRunicFish;
import com.min01.beyondtheabyss.entity.model.ModelSubmarine;
import com.min01.beyondtheabyss.entity.renderer.DeepAbyssPortalRenderer;
import com.min01.beyondtheabyss.entity.renderer.NoneRenderer;
import com.min01.beyondtheabyss.entity.renderer.SubmarineRenderer;
import com.min01.beyondtheabyss.entity.renderer.ThrownHarpoonRenderer;
import com.min01.beyondtheabyss.entity.renderer.layer.AbyssalScalesLayer;
import com.min01.beyondtheabyss.entity.renderer.living.AbyssalBulbrayRenderer;
import com.min01.beyondtheabyss.entity.renderer.living.AbyssalHermitCrabRenderer;
import com.min01.beyondtheabyss.entity.renderer.living.AmarumGhostRenderer;
import com.min01.beyondtheabyss.entity.renderer.living.DeepVampireRenderer;
import com.min01.beyondtheabyss.entity.renderer.living.GhidruthRenderer;
import com.min01.beyondtheabyss.entity.renderer.living.LatcherRenderer;
import com.min01.beyondtheabyss.entity.renderer.living.PhasmozoaRenderer;
import com.min01.beyondtheabyss.entity.renderer.living.RunicFishRenderer;
import com.min01.beyondtheabyss.entity.submarine.SubmarinePart;
import com.min01.beyondtheabyss.gui.overlay.HallucinationOverlay;
import com.min01.beyondtheabyss.item.BTAItems;
import com.min01.beyondtheabyss.item.deepabyss.GuidingClamItem;
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
import com.mojang.datafixers.util.Pair;

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
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.client.event.RegisterShadersEvent;
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.util.ObfuscationReflectionHelper;

@Mod.EventBusSubscriber(modid = BeyondtheAbyss.MODID, value = Dist.CLIENT, bus = Bus.MOD)
public class ClientEventHandler
{
	//public static final KeyMapping ABYSSAL_DASH = new KeyMapping("key." + BeyondtheAbyss.MODID + ".abyssal_dash", KeyConflictContext.IN_GAME, InputConstants.Type.KEYSYM, InputConstants.KEY_Y, "key.categories." + BeyondtheAbyss.MODID);
	
    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event)
    {
        BlockEntityRenderers.register(BTABlocks.RIFTWELLING_ALTAR_BLOCK_ENTITY.get(), RiftwellingAltarRenderer::new);
        BlockEntityRenderers.register(BTABlocks.NO_ROTATION_LIMIT_BLOCK_ENTITY.get(), NoRotationLimitRenderer::new);
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
        	return GuidingClamItem.isOpen(p_174585_) ? 1.0F : 0.0F;
        });
    }
    
    @SubscribeEvent
    public static void onRegisterGuiOverlays(RegisterGuiOverlaysEvent event)
    {
    	event.registerBelow(VanillaGuiOverlay.HOTBAR.id(), "hallucination", HallucinationOverlay::draw);
    }
    
    @SubscribeEvent
    public static void onRegisterShaders(RegisterShadersEvent event)
    {
        for(Pair<ShaderInstance, Consumer<ShaderInstance>> pair : BTARenderType.registerShaders(event.getResourceManager())) 
        {
            event.registerShader(pair.getFirst(), pair.getSecond());
        }
    }
    
	@SubscribeEvent
	public static void onRegisterParticleProviders(RegisterParticleProvidersEvent event)
	{
		event.register(BTAParticles.SHOCKWAVE.get(), ShockwaveParticle.Provider::new);
	}
    
	@SubscribeEvent
	public static void onModelRegisterAdditional(ModelEvent.RegisterAdditional event)
	{
		event.register(new ModelResourceLocation(new ResourceLocation(BeyondtheAbyss.MODID, "ghidruth_scale_harpoon_in_hand"), "inventory"));
	}
    
    @SubscribeEvent
    public static void onModelBakingCompleted(ModelEvent.BakingCompleted event)
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
	public static void onRegisterClientReloadListeners(RegisterClientReloadListenersEvent e)
	{
		e.registerReloadListener(new BTAShaders());
	}
	
    @SubscribeEvent
    public static void onRegisterEntityRenderers(EntityRenderersEvent.RegisterRenderers event)
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
    	event.registerEntityRenderer(BTAEntities.PHASMOZOA.get(), PhasmozoaRenderer::new);
    	event.registerEntityRenderer(BTAEntities.AMARUM_GHOST.get(), AmarumGhostRenderer::new);
    }
    
    @SubscribeEvent
    public static void onRegisterLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event)
    {
    	event.registerLayerDefinition(ModelGhidruth.LAYER_LOCATION, ModelGhidruth::createBodyLayer);
    	event.registerLayerDefinition(ModelDeepVampire.LAYER_LOCATION, ModelDeepVampire::createBodyLayer);
    	event.registerLayerDefinition(ModelRunicFish.LAYER_LOCATION, ModelRunicFish::createBodyLayer);
    	event.registerLayerDefinition(ModelLatcher.LAYER_LOCATION, ModelLatcher::createBodyLayer);
    	event.registerLayerDefinition(ModelAbyssalHermitCrab.LAYER_LOCATION, ModelAbyssalHermitCrab::createBodyLayer);
    	event.registerLayerDefinition(ModelAbyssalBulbray.LAYER_LOCATION, ModelAbyssalBulbray::createBodyLayer);
    	event.registerLayerDefinition(ModelSubmarine.LAYER_LOCATION, ModelSubmarine::createBodyLayer);
    	event.registerLayerDefinition(ModelPhasmozoa.LAYER_LOCATION, ModelPhasmozoa::createBodyLayer);
    	event.registerLayerDefinition(ModelAmarumGhost.LAYER_LOCATION, ModelAmarumGhost::createBodyLayer);
    	
    	event.registerLayerDefinition(ModelDiverSet.LAYER_LOCATION, ModelDiverSet::createBodyLayer);
    	event.registerLayerDefinition(ModelAdvancedDiverSet.LAYER_LOCATION, ModelAdvancedDiverSet::createBodyLayer);
    	event.registerLayerDefinition(ModelGhidruthDiverSet.LAYER_LOCATION, ModelGhidruthDiverSet::createBodyLayer);
    	
    	event.registerLayerDefinition(ModelRiftwellingAltar.LAYER_LOCATION, ModelRiftwellingAltar::createBodyLayer);
    	event.registerLayerDefinition(ModelFangSkull.LAYER_LOCATION, ModelFangSkull::createBodyLayer);
    	event.registerLayerDefinition(ModelLargeSkull.LAYER_LOCATION, ModelLargeSkull::createBodyLayer);
    	event.registerLayerDefinition(ModelBonePiles.LAYER_LOCATION, ModelBonePiles::createBodyLayer);
    	event.registerLayerDefinition(ModelSittingSkeleton.LAYER_LOCATION, ModelSittingSkeleton::createBodyLayer);
    	event.registerLayerDefinition(ModelFallenSkeleton.LAYER_LOCATION, ModelFallenSkeleton::createBodyLayer);
    	
    	event.registerLayerDefinition(ModelHarpoon.LAYER_LOCATION, ModelHarpoon::createBodyLayer);
    	event.registerLayerDefinition(ModelGhidruthHarpoon.LAYER_LOCATION, ModelGhidruthHarpoon::createBodyLayer);
    }
    
    @SubscribeEvent
    public static void onRegisterKeyMappings(RegisterKeyMappingsEvent event)
    {
    	//event.register(ABYSSAL_DASH);
    }
    
	@SubscribeEvent
	public static void onAddLayers(EntityRenderersEvent.AddLayers event)
	{
		Map<EntityType<?>, EntityRenderer<?>> renderers = ObfuscationReflectionHelper.getPrivateValue(EntityRenderersEvent.AddLayers.class, event, "renderers");
		renderers.values().stream()
		.filter(LivingEntityRenderer.class::isInstance)
		.map(LivingEntityRenderer.class::cast)
		.forEach(ClientEventHandler::addLayer);
		
		event.getSkins().forEach(renderer -> 
		{
			LivingEntityRenderer<Player, EntityModel<Player>> skin = event.getSkin(renderer);
			addLayer(Objects.requireNonNull(skin));
		});
	}
	
	private static <T extends LivingEntity, M extends EntityModel<T>> void addLayer(LivingEntityRenderer<T, M> renderer)
	{
		renderer.addLayer(new AbyssalScalesLayer<>(renderer));
	}
}
