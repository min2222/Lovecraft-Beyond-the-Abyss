package com.min01.beyondtheabyss.event;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.block.BTABlocks;
import com.min01.beyondtheabyss.block.model.ModelBiocrafter;
import com.min01.beyondtheabyss.block.model.ModelBoneLever;
import com.min01.beyondtheabyss.block.model.ModelBoneLeverOn;
import com.min01.beyondtheabyss.block.model.ModelBonePiles;
import com.min01.beyondtheabyss.block.model.ModelBoneTorch;
import com.min01.beyondtheabyss.block.model.ModelBoneWallTorch;
import com.min01.beyondtheabyss.block.model.ModelChainTrap;
import com.min01.beyondtheabyss.block.model.ModelFallenSkeleton;
import com.min01.beyondtheabyss.block.model.ModelFangSkull;
import com.min01.beyondtheabyss.block.model.ModelLargeSkull;
import com.min01.beyondtheabyss.block.model.ModelRiftwellingAltar;
import com.min01.beyondtheabyss.block.model.ModelSittingSkeleton;
import com.min01.beyondtheabyss.blockentity.renderer.BiocrafterRenderer;
import com.min01.beyondtheabyss.blockentity.renderer.ChainTrapRenderer;
import com.min01.beyondtheabyss.blockentity.renderer.NoRotationLimitRenderer;
import com.min01.beyondtheabyss.blockentity.renderer.RiftwellingAltarRenderer;
import com.min01.beyondtheabyss.entity.BTAEntities;
import com.min01.beyondtheabyss.entity.misc.EntityBTACameraShake;
import com.min01.beyondtheabyss.entity.model.ModelChainTrapChain;
import com.min01.beyondtheabyss.entity.model.ModelChainTrapMaw;
import com.min01.beyondtheabyss.entity.model.ModelCorpseAngler;
import com.min01.beyondtheabyss.entity.model.ModelFulgastra;
import com.min01.beyondtheabyss.entity.model.ModelGhidruth;
import com.min01.beyondtheabyss.entity.model.ModelGloomfish;
import com.min01.beyondtheabyss.entity.model.ModelGnasher;
import com.min01.beyondtheabyss.entity.model.ModelKormosBody;
import com.min01.beyondtheabyss.entity.model.ModelKormosHead;
import com.min01.beyondtheabyss.entity.model.ModelKormosTail;
import com.min01.beyondtheabyss.entity.model.ModelSiamserpentBone;
import com.min01.beyondtheabyss.entity.model.ModelSiamserpentHead;
import com.min01.beyondtheabyss.entity.model.ModelSpineWormBody;
import com.min01.beyondtheabyss.entity.model.ModelSpineWormHead;
import com.min01.beyondtheabyss.entity.model.ModelSubmarine;
import com.min01.beyondtheabyss.entity.renderer.ChainTrapMawRenderer;
import com.min01.beyondtheabyss.entity.renderer.DeepAbyssPortalRenderer;
import com.min01.beyondtheabyss.entity.renderer.NoneRenderer;
import com.min01.beyondtheabyss.entity.renderer.SubmarineRenderer;
import com.min01.beyondtheabyss.entity.renderer.ThrownHarpoonRenderer;
import com.min01.beyondtheabyss.entity.renderer.living.CorpseAnglerRenderer;
import com.min01.beyondtheabyss.entity.renderer.living.FulgastraRenderer;
import com.min01.beyondtheabyss.entity.renderer.living.GhidruthRenderer;
import com.min01.beyondtheabyss.entity.renderer.living.GloomfishRenderer;
import com.min01.beyondtheabyss.entity.renderer.living.GnasherRenderer;
import com.min01.beyondtheabyss.entity.renderer.living.KormosBodyRenderer;
import com.min01.beyondtheabyss.entity.renderer.living.KormosHeadRenderer;
import com.min01.beyondtheabyss.entity.renderer.living.KormosTailRenderer;
import com.min01.beyondtheabyss.entity.renderer.living.SiamserpentBoneRenderer;
import com.min01.beyondtheabyss.entity.renderer.living.SiamserpentHeadRenderer;
import com.min01.beyondtheabyss.entity.renderer.living.SpineWormBodyRenderer;
import com.min01.beyondtheabyss.entity.renderer.living.SpineWormHeadRenderer;
import com.min01.beyondtheabyss.item.BTAItems;
import com.min01.beyondtheabyss.item.deepabyss.FlashlightItem;
import com.min01.beyondtheabyss.item.deepabyss.GuidingClamItem;
import com.min01.beyondtheabyss.item.model.ModelAdvancedDiverSet;
import com.min01.beyondtheabyss.item.model.ModelDiverSet;
import com.min01.beyondtheabyss.item.model.ModelFlashlight;
import com.min01.beyondtheabyss.item.model.ModelGhidruthDiverSet;
import com.min01.beyondtheabyss.item.model.ModelGhidruthHarpoon;
import com.min01.beyondtheabyss.item.model.ModelHarpoon;
import com.min01.beyondtheabyss.item.model.ModelSkeletalGunblade;
import com.min01.beyondtheabyss.shader.BTAShaders;
import com.min01.beyondtheabyss.world.effects.DeepAbyssDimensionSpecialEffects;
import com.min01.beyondtheabyss.world.effects.MirroredCityDimensionSpecialEffects;

import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterClientReloadListenersEvent;
import net.minecraftforge.client.event.RegisterDimensionSpecialEffectsEvent;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = BeyondtheAbyss.MODID, value = Dist.CLIENT, bus = Bus.MOD)
public class ClientEventHandler
{
    @SubscribeEvent
    public static void onFMLClientSetup(FMLClientSetupEvent event)
    {
        BlockEntityRenderers.register(BTABlocks.RIFTWELLING_ALTAR_BLOCK_ENTITY.get(), RiftwellingAltarRenderer::new);
        BlockEntityRenderers.register(BTABlocks.NO_ROTATION_LIMIT_BLOCK_ENTITY.get(), NoRotationLimitRenderer::new);
        BlockEntityRenderers.register(BTABlocks.CHAIN_TRAP_BLOCK_ENTITY.get(), ChainTrapRenderer::new);
        BlockEntityRenderers.register(BTABlocks.BIOCRAFTER_BLOCK_ENTITY.get(), BiocrafterRenderer::new);
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
        ItemProperties.register(BTAItems.FLASHLIGHT.get(), new ResourceLocation("on"), (p_174585_, p_174586_, p_174587_, p_174588_) ->
        {
        	return FlashlightItem.isOn(p_174585_) ? 1.0F : 0.0F;
        });
    }
    
    @SubscribeEvent
    public static void onRegisterDimensionSpecialEffects(RegisterDimensionSpecialEffectsEvent event)
    {
    	event.register(new ResourceLocation(BeyondtheAbyss.MODID, "deep_abyss"), new DeepAbyssDimensionSpecialEffects());
     	event.register(new ResourceLocation(BeyondtheAbyss.MODID, "mirrored_city"), new MirroredCityDimensionSpecialEffects());
    }
    
    @SubscribeEvent
    public static void onRegisterGuiOverlays(RegisterGuiOverlaysEvent event)
    {
    	
    }
    
	@SubscribeEvent
	public static void onRegisterParticleProviders(RegisterParticleProvidersEvent event)
	{
		
	}
    
	@SubscribeEvent
	public static void onRegisterClientReloadListeners(RegisterClientReloadListenersEvent event)
	{
		event.registerReloadListener(new BTAShaders());
	}
	
    @SubscribeEvent
    public static void onRegisterEntityRenderers(EntityRenderersEvent.RegisterRenderers event)
    {
    	//misc
    	event.registerEntityRenderer(BTAEntities.DEEP_ABYSS_PORTAL.get(), DeepAbyssPortalRenderer::new);
    	event.registerEntityRenderer(BTAEntities.BTA_CAMERA_SHAKE.get(), NoneRenderer<EntityBTACameraShake>::new);
    	event.registerEntityRenderer(BTAEntities.SUBMARINE.get(), SubmarineRenderer::new);
    	event.registerEntityRenderer(BTAEntities.CHAIN_TRAP_MAW.get(), ChainTrapMawRenderer::new);
    	
    	//projectile
    	event.registerEntityRenderer(BTAEntities.THROWN_HARPOON.get(), ThrownHarpoonRenderer::new);
    	
    	//living
    	event.registerEntityRenderer(BTAEntities.GHIDRUTH.get(), GhidruthRenderer::new);
    	event.registerEntityRenderer(BTAEntities.GNASHER.get(), GnasherRenderer::new);
    	event.registerEntityRenderer(BTAEntities.SIAMSERPENT_HEAD.get(), SiamserpentHeadRenderer::new);
    	event.registerEntityRenderer(BTAEntities.SIAMSERPENT_BONE.get(), SiamserpentBoneRenderer::new);
    	event.registerEntityRenderer(BTAEntities.SPINE_WORM_HEAD.get(), SpineWormHeadRenderer::new);
    	event.registerEntityRenderer(BTAEntities.SPINE_WORM_BODY.get(), SpineWormBodyRenderer::new);
    	event.registerEntityRenderer(BTAEntities.GLOOMFISH.get(), GloomfishRenderer::new);
    	event.registerEntityRenderer(BTAEntities.KORMOS_HEAD.get(), KormosHeadRenderer::new);
    	event.registerEntityRenderer(BTAEntities.KORMOS_BODY.get(), KormosBodyRenderer::new);
    	event.registerEntityRenderer(BTAEntities.KORMOS_TAIL.get(), KormosTailRenderer::new);
    	event.registerEntityRenderer(BTAEntities.CORPSE_ANGLER.get(), CorpseAnglerRenderer::new);
    	event.registerEntityRenderer(BTAEntities.FULGASTRA.get(), FulgastraRenderer::new);
    }
    
    @SubscribeEvent
    public static void onRegisterLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event)
    {
    	//entities
    	event.registerLayerDefinition(ModelGhidruth.LAYER_LOCATION, ModelGhidruth::createBodyLayer);
    	event.registerLayerDefinition(ModelSubmarine.LAYER_LOCATION, ModelSubmarine::createBodyLayer);
    	event.registerLayerDefinition(ModelGnasher.LAYER_LOCATION, ModelGnasher::createBodyLayer);
    	event.registerLayerDefinition(ModelSiamserpentHead.LAYER_LOCATION, ModelSiamserpentHead::createBodyLayer);
    	event.registerLayerDefinition(ModelSiamserpentBone.LAYER_LOCATION, ModelSiamserpentBone::createBodyLayer);
    	event.registerLayerDefinition(ModelSpineWormHead.LAYER_LOCATION, ModelSpineWormHead::createBodyLayer);
    	event.registerLayerDefinition(ModelSpineWormBody.LAYER_LOCATION, ModelSpineWormBody::createBodyLayer);
    	event.registerLayerDefinition(ModelChainTrapMaw.LAYER_LOCATION, ModelChainTrapMaw::createBodyLayer);
    	event.registerLayerDefinition(ModelChainTrapChain.LAYER_LOCATION, ModelChainTrapChain::createBodyLayer);
    	event.registerLayerDefinition(ModelGloomfish.LAYER_LOCATION, ModelGloomfish::createBodyLayer);
    	event.registerLayerDefinition(ModelKormosHead.LAYER_LOCATION, ModelKormosHead::createBodyLayer);
    	event.registerLayerDefinition(ModelKormosBody.LAYER_LOCATION, ModelKormosBody::createBodyLayer);
    	event.registerLayerDefinition(ModelKormosTail.LAYER_LOCATION, ModelKormosTail::createBodyLayer);
    	event.registerLayerDefinition(ModelCorpseAngler.LAYER_LOCATION, ModelCorpseAngler::createBodyLayer);
    	event.registerLayerDefinition(ModelFulgastra.LAYER_LOCATION, ModelFulgastra::createBodyLayer);

    	//armors
    	event.registerLayerDefinition(ModelDiverSet.LAYER_LOCATION, ModelDiverSet::createBodyLayer);
    	event.registerLayerDefinition(ModelAdvancedDiverSet.LAYER_LOCATION, ModelAdvancedDiverSet::createBodyLayer);
    	event.registerLayerDefinition(ModelGhidruthDiverSet.LAYER_LOCATION, ModelGhidruthDiverSet::createBodyLayer);

    	//blocks
    	event.registerLayerDefinition(ModelRiftwellingAltar.LAYER_LOCATION, ModelRiftwellingAltar::createBodyLayer);
    	event.registerLayerDefinition(ModelFangSkull.LAYER_LOCATION, ModelFangSkull::createBodyLayer);
    	event.registerLayerDefinition(ModelLargeSkull.LAYER_LOCATION, ModelLargeSkull::createBodyLayer);
    	event.registerLayerDefinition(ModelBonePiles.LAYER_LOCATION, ModelBonePiles::createBodyLayer);
    	event.registerLayerDefinition(ModelSittingSkeleton.LAYER_LOCATION, ModelSittingSkeleton::createBodyLayer);
    	event.registerLayerDefinition(ModelFallenSkeleton.LAYER_LOCATION, ModelFallenSkeleton::createBodyLayer);
    	event.registerLayerDefinition(ModelBoneTorch.LAYER_LOCATION, ModelBoneTorch::createBodyLayer);
    	event.registerLayerDefinition(ModelBoneWallTorch.LAYER_LOCATION, ModelBoneWallTorch::createBodyLayer);
    	event.registerLayerDefinition(ModelBoneLever.LAYER_LOCATION, ModelBoneLever::createBodyLayer);
    	event.registerLayerDefinition(ModelBoneLeverOn.LAYER_LOCATION, ModelBoneLeverOn::createBodyLayer);
    	event.registerLayerDefinition(ModelChainTrap.LAYER_LOCATION, ModelChainTrap::createBodyLayer);
    	event.registerLayerDefinition(ModelBiocrafter.LAYER_LOCATION, ModelBiocrafter::createBodyLayer);

    	//items
    	event.registerLayerDefinition(ModelHarpoon.LAYER_LOCATION, ModelHarpoon::createBodyLayer);
    	event.registerLayerDefinition(ModelGhidruthHarpoon.LAYER_LOCATION, ModelGhidruthHarpoon::createBodyLayer);
    	event.registerLayerDefinition(ModelFlashlight.LAYER_LOCATION, ModelFlashlight::createBodyLayer);
    	event.registerLayerDefinition(ModelSkeletalGunblade.LAYER_LOCATION, ModelSkeletalGunblade::createBodyLayer);
    }
}
