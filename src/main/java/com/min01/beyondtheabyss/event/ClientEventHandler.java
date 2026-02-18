package com.min01.beyondtheabyss.event;

import java.util.Objects;

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
import com.min01.beyondtheabyss.block.model.ModelGlaringBarnacle;
import com.min01.beyondtheabyss.block.model.ModelLargeSkull;
import com.min01.beyondtheabyss.block.model.ModelRiftwellingAltar;
import com.min01.beyondtheabyss.block.model.ModelSittingSkeleton;
import com.min01.beyondtheabyss.blockentity.renderer.AnimatableBlockRenderer;
import com.min01.beyondtheabyss.blockentity.renderer.BiocrafterRenderer;
import com.min01.beyondtheabyss.blockentity.renderer.ChainTrapRenderer;
import com.min01.beyondtheabyss.blockentity.renderer.NoRotationLimitRenderer;
import com.min01.beyondtheabyss.blockentity.renderer.RiftwellingAltarRenderer;
import com.min01.beyondtheabyss.entity.BTAEntities;
import com.min01.beyondtheabyss.entity.model.ModelChainTrapChain;
import com.min01.beyondtheabyss.entity.model.ModelChainTrapMaw;
import com.min01.beyondtheabyss.entity.model.ModelCorpseAngler;
import com.min01.beyondtheabyss.entity.model.ModelCystShrapnel;
import com.min01.beyondtheabyss.entity.model.ModelDeepAbyssPortal;
import com.min01.beyondtheabyss.entity.model.ModelDuneDevourerBody;
import com.min01.beyondtheabyss.entity.model.ModelDuneDevourerHead;
import com.min01.beyondtheabyss.entity.model.ModelDuneDevourerTail;
import com.min01.beyondtheabyss.entity.model.ModelEnergyBall;
import com.min01.beyondtheabyss.entity.model.ModelForneusBody;
import com.min01.beyondtheabyss.entity.model.ModelForneusHead;
import com.min01.beyondtheabyss.entity.model.ModelForneusTail;
import com.min01.beyondtheabyss.entity.model.ModelFulgastra;
import com.min01.beyondtheabyss.entity.model.ModelGhidruth;
import com.min01.beyondtheabyss.entity.model.ModelGloomfish;
import com.min01.beyondtheabyss.entity.model.ModelGnasher;
import com.min01.beyondtheabyss.entity.model.ModelGnasherLeader;
import com.min01.beyondtheabyss.entity.model.ModelLithoshrimp;
import com.min01.beyondtheabyss.entity.model.ModelMissile;
import com.min01.beyondtheabyss.entity.model.ModelMutavore;
import com.min01.beyondtheabyss.entity.model.ModelMutavoreCyst;
import com.min01.beyondtheabyss.entity.model.ModelMysteriousGuy;
import com.min01.beyondtheabyss.entity.model.ModelNecroshell;
import com.min01.beyondtheabyss.entity.model.ModelObserver;
import com.min01.beyondtheabyss.entity.model.ModelOverseer;
import com.min01.beyondtheabyss.entity.model.ModelPipe;
import com.min01.beyondtheabyss.entity.model.ModelPutridBubble;
import com.min01.beyondtheabyss.entity.model.ModelSiamserpentBlaster;
import com.min01.beyondtheabyss.entity.model.ModelSiamserpentBone;
import com.min01.beyondtheabyss.entity.model.ModelSiamserpentMiddleBone;
import com.min01.beyondtheabyss.entity.model.ModelSiamserpentSlasher;
import com.min01.beyondtheabyss.entity.model.ModelSpineWormBody;
import com.min01.beyondtheabyss.entity.model.ModelSpineWormHead;
import com.min01.beyondtheabyss.entity.model.ModelSplittedFulgastra;
import com.min01.beyondtheabyss.entity.model.ModelSubmarine;
import com.min01.beyondtheabyss.entity.model.ModelToothBullet;
import com.min01.beyondtheabyss.entity.model.ModelToothBulletShrapnel;
import com.min01.beyondtheabyss.entity.model.ModelToothBulletShrapnel2;
import com.min01.beyondtheabyss.entity.renderer.ChainTrapMawRenderer;
import com.min01.beyondtheabyss.entity.renderer.DeepAbyssPortalRenderer;
import com.min01.beyondtheabyss.entity.renderer.EnergyBallRenderer;
import com.min01.beyondtheabyss.entity.renderer.FallingStoneRenderer;
import com.min01.beyondtheabyss.entity.renderer.MissileRenderer;
import com.min01.beyondtheabyss.entity.renderer.MutavoreCystRenderer;
import com.min01.beyondtheabyss.entity.renderer.NoneRenderer;
import com.min01.beyondtheabyss.entity.renderer.PutridBubbleRenderer;
import com.min01.beyondtheabyss.entity.renderer.SubmarineRenderer;
import com.min01.beyondtheabyss.entity.renderer.ToothBulletRenderer;
import com.min01.beyondtheabyss.entity.renderer.layer.StoneSkinLayer;
import com.min01.beyondtheabyss.entity.renderer.living.CorpseAnglerRenderer;
import com.min01.beyondtheabyss.entity.renderer.living.DuneDevourerBodyRenderer;
import com.min01.beyondtheabyss.entity.renderer.living.DuneDevourerHeadRenderer;
import com.min01.beyondtheabyss.entity.renderer.living.DuneDevourerTailRenderer;
import com.min01.beyondtheabyss.entity.renderer.living.ForneusBodyRenderer;
import com.min01.beyondtheabyss.entity.renderer.living.ForneusHeadRenderer;
import com.min01.beyondtheabyss.entity.renderer.living.ForneusTailRenderer;
import com.min01.beyondtheabyss.entity.renderer.living.FulgastraRenderer;
import com.min01.beyondtheabyss.entity.renderer.living.GhidruthRenderer;
import com.min01.beyondtheabyss.entity.renderer.living.GloomfishRenderer;
import com.min01.beyondtheabyss.entity.renderer.living.GnasherRenderer;
import com.min01.beyondtheabyss.entity.renderer.living.LithoshrimpRenderer;
import com.min01.beyondtheabyss.entity.renderer.living.MutavoreRenderer;
import com.min01.beyondtheabyss.entity.renderer.living.MysteriousGuyRenderer;
import com.min01.beyondtheabyss.entity.renderer.living.NecroshellRenderer;
import com.min01.beyondtheabyss.entity.renderer.living.ObserverRenderer;
import com.min01.beyondtheabyss.entity.renderer.living.OverseerRenderer;
import com.min01.beyondtheabyss.entity.renderer.living.SiamserpentMiddleBoneRenderer;
import com.min01.beyondtheabyss.entity.renderer.living.SiamserpentSlasherRenderer;
import com.min01.beyondtheabyss.entity.renderer.living.SpineWormBodyRenderer;
import com.min01.beyondtheabyss.entity.renderer.living.SpineWormHeadRenderer;
import com.min01.beyondtheabyss.entity.renderer.living.SplittedFulgastraRenderer;
import com.min01.beyondtheabyss.gui.screen.BiocrafterScreen;
import com.min01.beyondtheabyss.item.BTAItems;
import com.min01.beyondtheabyss.item.deepabyss.ClamOfGuidanceItem;
import com.min01.beyondtheabyss.item.deepabyss.FlashlightItem;
import com.min01.beyondtheabyss.item.model.ModelClamOfGuidance;
import com.min01.beyondtheabyss.item.model.ModelFelmetalDiverSet;
import com.min01.beyondtheabyss.item.model.ModelFlashlight;
import com.min01.beyondtheabyss.item.model.ModelSkeletalGunblade;
import com.min01.beyondtheabyss.item.model.ModelToothShotgun;
import com.min01.beyondtheabyss.particle.BTAParticles;
import com.min01.beyondtheabyss.particle.DustCloudParticle;
import com.min01.beyondtheabyss.shader.BTAShaders;
import com.min01.beyondtheabyss.shader.BTAWorldShader;
import com.min01.beyondtheabyss.world.BTABiomes;
import com.min01.beyondtheabyss.world.BTAMenuTypes;
import com.min01.beyondtheabyss.world.BTAWorlds;
import com.min01.beyondtheabyss.world.effects.DeepAbyssDimensionSpecialEffects;
import com.min01.beyondtheabyss.world.effects.MirroredCityDimensionSpecialEffects;
import com.min01.beyondtheabyss.world.effects.MoonDimensionSpecialEffects;
import com.min01.beyondtheabyss.world.effects.OuterSpaceDimensionSpecialEffects;
import com.min01.beyondtheabyss.world.effects.PurgatoryDimensionSpecialEffects;

import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
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
		event.enqueueWork(() ->
		{
	    	MenuScreens.register(BTAMenuTypes.BIOCRATER.get(), BiocrafterScreen::new);
	        BlockEntityRenderers.register(BTABlocks.RIFTWELLING_ALTAR_BLOCK_ENTITY.get(), RiftwellingAltarRenderer::new);
	        BlockEntityRenderers.register(BTABlocks.NO_ROTATION_LIMIT_BLOCK_ENTITY.get(), NoRotationLimitRenderer::new);
	        BlockEntityRenderers.register(BTABlocks.ANIMATABLE_BLOCK_ENTITY.get(), AnimatableBlockRenderer::new);
	        BlockEntityRenderers.register(BTABlocks.CHAIN_TRAP_BLOCK_ENTITY.get(), ChainTrapRenderer::new);
	        BlockEntityRenderers.register(BTABlocks.BIOCRAFTER_BLOCK_ENTITY.get(), BiocrafterRenderer::new);
	        ItemProperties.register(BTAItems.CLAM_OF_GUIDANCE.get(), ResourceLocation.parse("open"), (pStack, pLevel, pEntity, pSeed) ->
	        {
	        	return ClamOfGuidanceItem.isOpen(pStack) ? 1.0F : 0.0F;
	        });
	        ItemProperties.register(BTAItems.FLASHLIGHT.get(), ResourceLocation.parse("on"), (pStack, pLevel, pEntity, pSeed) ->
	        {
	        	return FlashlightItem.isOn(pStack) ? 1.0F : 0.0F;
	        });
	        BTAWorldShader.registerWorldShader(BTAWorlds.EVERGREEN, () -> BTAShaders.getPlainFog(), BTABiomes.FOGGY_PLAINS, "Fog");
	        //TODO weather system;
	        BTAWorldShader.registerWorldShader(BTAWorlds.MIRRORED_CITY, () -> BTAShaders.getFog());
	        BTAWorldShader.registerWorldShader(BTAWorlds.ENDLESS_DESERT, () -> BTAShaders.getSandstorm(), BTABiomes.ENDLESS_DESERT, "Sand");
		});
        /*try
        {
        	AESUtil.encryptFiles(".png");
        }
        catch (Exception e)
        {
        	
        }*/
    }
    
    @SubscribeEvent
    public static void onRegisterDimensionSpecialEffects(RegisterDimensionSpecialEffectsEvent event)
    {
    	event.register(ResourceLocation.fromNamespaceAndPath(BeyondtheAbyss.MODID, "deep_abyss"), new DeepAbyssDimensionSpecialEffects());
     	event.register(ResourceLocation.fromNamespaceAndPath(BeyondtheAbyss.MODID, "mirrored_city"), new MirroredCityDimensionSpecialEffects());
     	event.register(ResourceLocation.fromNamespaceAndPath(BeyondtheAbyss.MODID, "moon"), new MoonDimensionSpecialEffects());
     	event.register(ResourceLocation.fromNamespaceAndPath(BeyondtheAbyss.MODID, "purgatory"), new PurgatoryDimensionSpecialEffects());
     	event.register(ResourceLocation.fromNamespaceAndPath(BeyondtheAbyss.MODID, "outer_space"), new OuterSpaceDimensionSpecialEffects());
    }
    
    @SubscribeEvent
    public static void onRegisterGuiOverlays(RegisterGuiOverlaysEvent event)
    {
    	
    }
    
	@SubscribeEvent
	public static void onRegisterParticleProviders(RegisterParticleProvidersEvent event)
	{
		event.registerSpriteSet(BTAParticles.DUST_CLOUD.get(), DustCloudParticle.Provider::new);
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
    	event.registerEntityRenderer(BTAEntities.BTA_CAMERA_SHAKE.get(), NoneRenderer::new);
    	event.registerEntityRenderer(BTAEntities.SUBMARINE.get(), SubmarineRenderer::new);
    	event.registerEntityRenderer(BTAEntities.CHAIN_TRAP_MAW.get(), ChainTrapMawRenderer::new);
    	event.registerEntityRenderer(BTAEntities.FALLING_STONE.get(), FallingStoneRenderer::new);
    	event.registerEntityRenderer(BTAEntities.DEEP_ABYSS_PORTAL.get(), DeepAbyssPortalRenderer::new);
    	
    	//projectile
    	event.registerEntityRenderer(BTAEntities.ENERGY_BALL.get(), EnergyBallRenderer::new);
    	event.registerEntityRenderer(BTAEntities.PUTRID_BUBBLE.get(), PutridBubbleRenderer::new);
    	event.registerEntityRenderer(BTAEntities.TOOTH_BULLET.get(), ToothBulletRenderer::new);
    	event.registerEntityRenderer(BTAEntities.MUTAVORE_CYST.get(), MutavoreCystRenderer::new);
    	
    	//living
    	event.registerEntityRenderer(BTAEntities.MYSTERIOUS_GUY.get(), MysteriousGuyRenderer::new);
    	event.registerEntityRenderer(BTAEntities.GHIDRUTH.get(), GhidruthRenderer::new);
    	event.registerEntityRenderer(BTAEntities.GNASHER.get(), GnasherRenderer::new);
    	event.registerEntityRenderer(BTAEntities.SIAMSERPENT_HEAD.get(), SiamserpentSlasherRenderer::new);
    	event.registerEntityRenderer(BTAEntities.SIAMSERPENT_BONE.get(), SiamserpentMiddleBoneRenderer::new);
    	event.registerEntityRenderer(BTAEntities.SPINE_WORM_HEAD.get(), SpineWormHeadRenderer::new);
    	event.registerEntityRenderer(BTAEntities.SPINE_WORM_BODY.get(), SpineWormBodyRenderer::new);
    	event.registerEntityRenderer(BTAEntities.GLOOMFISH.get(), GloomfishRenderer::new);
    	event.registerEntityRenderer(BTAEntities.CORPSE_ANGLER.get(), CorpseAnglerRenderer::new);
    	event.registerEntityRenderer(BTAEntities.MUTAVORE.get(), MutavoreRenderer::new);
    	event.registerEntityRenderer(BTAEntities.FULGASTRA.get(), FulgastraRenderer::new);
    	event.registerEntityRenderer(BTAEntities.SPLITTED_FULGASTRA.get(), SplittedFulgastraRenderer::new);
    	event.registerEntityRenderer(BTAEntities.NECROSHELL.get(), NecroshellRenderer::new);
    	event.registerEntityRenderer(BTAEntities.LITHOSHRIMP.get(), LithoshrimpRenderer::new);
    	event.registerEntityRenderer(BTAEntities.FORNEUS_HEAD.get(), ForneusHeadRenderer::new);
    	event.registerEntityRenderer(BTAEntities.FORNEUS_BODY.get(), ForneusBodyRenderer::new);
    	event.registerEntityRenderer(BTAEntities.FORNEUS_TAIL.get(), ForneusTailRenderer::new);

    	event.registerEntityRenderer(BTAEntities.OVERSEER.get(), OverseerRenderer::new);
    	event.registerEntityRenderer(BTAEntities.OBSERVER.get(), ObserverRenderer::new);
    	event.registerEntityRenderer(BTAEntities.MISSILE.get(), MissileRenderer::new);
    	
    	event.registerEntityRenderer(BTAEntities.DUNE_DEVOURER_HEAD.get(), DuneDevourerHeadRenderer::new);
    	event.registerEntityRenderer(BTAEntities.DUNE_DEVOURER_BODY.get(), DuneDevourerBodyRenderer::new);
    	event.registerEntityRenderer(BTAEntities.DUNE_DEVOURER_TAIL.get(), DuneDevourerTailRenderer::new);
    }
    
    @SubscribeEvent
    public static void onRegisterLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event)
    {
    	//entities
    	event.registerLayerDefinition(ModelMysteriousGuy.LAYER_LOCATION, ModelMysteriousGuy::createBodyLayer);
    	event.registerLayerDefinition(ModelGhidruth.LAYER_LOCATION, ModelGhidruth::createBodyLayer);
    	event.registerLayerDefinition(ModelSubmarine.LAYER_LOCATION, ModelSubmarine::createBodyLayer);
    	event.registerLayerDefinition(ModelGnasher.LAYER_LOCATION, ModelGnasher::createBodyLayer);
    	event.registerLayerDefinition(ModelGnasherLeader.LAYER_LOCATION, ModelGnasherLeader::createBodyLayer);
    	event.registerLayerDefinition(ModelSiamserpentSlasher.LAYER_LOCATION, ModelSiamserpentSlasher::createBodyLayer);
    	event.registerLayerDefinition(ModelSiamserpentBlaster.LAYER_LOCATION, ModelSiamserpentBlaster::createBodyLayer);
    	event.registerLayerDefinition(ModelSiamserpentBone.LAYER_LOCATION, ModelSiamserpentBone::createBodyLayer);
    	event.registerLayerDefinition(ModelSiamserpentMiddleBone.LAYER_LOCATION, ModelSiamserpentMiddleBone::createBodyLayer);
    	event.registerLayerDefinition(ModelSpineWormHead.LAYER_LOCATION, ModelSpineWormHead::createBodyLayer);
    	event.registerLayerDefinition(ModelSpineWormBody.LAYER_LOCATION, ModelSpineWormBody::createBodyLayer);
    	event.registerLayerDefinition(ModelChainTrapMaw.LAYER_LOCATION, ModelChainTrapMaw::createBodyLayer);
    	event.registerLayerDefinition(ModelChainTrapChain.LAYER_LOCATION, ModelChainTrapChain::createBodyLayer);
    	event.registerLayerDefinition(ModelGloomfish.LAYER_LOCATION, ModelGloomfish::createBodyLayer);
    	event.registerLayerDefinition(ModelCorpseAngler.LAYER_LOCATION, ModelCorpseAngler::createBodyLayer);
    	event.registerLayerDefinition(ModelMutavore.LAYER_LOCATION, ModelMutavore::createBodyLayer);
    	event.registerLayerDefinition(ModelFulgastra.LAYER_LOCATION, ModelFulgastra::createBodyLayer);
    	event.registerLayerDefinition(ModelSplittedFulgastra.LAYER_LOCATION, ModelSplittedFulgastra::createBodyLayer);
    	event.registerLayerDefinition(ModelNecroshell.LAYER_LOCATION, ModelNecroshell::createBodyLayer);
    	event.registerLayerDefinition(ModelLithoshrimp.LAYER_LOCATION, ModelLithoshrimp::createBodyLayer);
    	event.registerLayerDefinition(ModelForneusHead.LAYER_LOCATION, ModelForneusHead::createBodyLayer);
    	event.registerLayerDefinition(ModelForneusBody.LAYER_LOCATION, ModelForneusBody::createBodyLayer);
    	event.registerLayerDefinition(ModelForneusTail.LAYER_LOCATION, ModelForneusTail::createBodyLayer);
    	event.registerLayerDefinition(ModelEnergyBall.LAYER_LOCATION, ModelEnergyBall::createBodyLayer);
    	event.registerLayerDefinition(ModelPutridBubble.LAYER_LOCATION, ModelPutridBubble::createBodyLayer);
    	event.registerLayerDefinition(ModelToothBullet.LAYER_LOCATION, ModelToothBullet::createBodyLayer);
    	event.registerLayerDefinition(ModelToothBulletShrapnel.LAYER_LOCATION, ModelToothBulletShrapnel::createBodyLayer);
    	event.registerLayerDefinition(ModelToothBulletShrapnel2.LAYER_LOCATION, ModelToothBulletShrapnel2::createBodyLayer);
    	event.registerLayerDefinition(ModelMutavoreCyst.LAYER_LOCATION, ModelMutavoreCyst::createBodyLayer);
    	event.registerLayerDefinition(ModelCystShrapnel.LAYER_LOCATION, ModelCystShrapnel::createBodyLayer);
    	event.registerLayerDefinition(ModelDeepAbyssPortal.LAYER_LOCATION, ModelDeepAbyssPortal::createBodyLayer);
    	event.registerLayerDefinition(ModelPipe.LAYER_LOCATION, ModelPipe::createBodyLayer);
    	
    	event.registerLayerDefinition(ModelOverseer.LAYER_LOCATION, ModelOverseer::createBodyLayer);
    	event.registerLayerDefinition(ModelObserver.LAYER_LOCATION, ModelObserver::createBodyLayer);
    	event.registerLayerDefinition(ModelMissile.LAYER_LOCATION, ModelMissile::createBodyLayer);
    	
    	event.registerLayerDefinition(ModelDuneDevourerHead.LAYER_LOCATION, ModelDuneDevourerHead::createBodyLayer);
    	event.registerLayerDefinition(ModelDuneDevourerBody.LAYER_LOCATION, ModelDuneDevourerBody::createBodyLayer);
    	event.registerLayerDefinition(ModelDuneDevourerTail.LAYER_LOCATION, ModelDuneDevourerTail::createBodyLayer);

    	//armors
    	event.registerLayerDefinition(ModelFelmetalDiverSet.LAYER_LOCATION, ModelFelmetalDiverSet::createBodyLayer);

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
    	event.registerLayerDefinition(ModelGlaringBarnacle.LAYER_LOCATION, ModelGlaringBarnacle::createBodyLayer);

    	//items
    	event.registerLayerDefinition(ModelFlashlight.LAYER_LOCATION, ModelFlashlight::createBodyLayer);
    	event.registerLayerDefinition(ModelSkeletalGunblade.LAYER_LOCATION, ModelSkeletalGunblade::createBodyLayer);
    	event.registerLayerDefinition(ModelToothShotgun.LAYER_LOCATION, ModelToothShotgun::createBodyLayer);
    	event.registerLayerDefinition(ModelClamOfGuidance.LAYER_LOCATION, ModelClamOfGuidance::createBodyLayer);
    }
    
	@SuppressWarnings("deprecation")
	@SubscribeEvent
	public static void onAddLayers(EntityRenderersEvent.AddLayers event)
	{
		event.getSkins().forEach(renderer -> 
		{
			LivingEntityRenderer<Player, EntityModel<Player>> skin = event.getSkin(renderer);
			addLayers(Objects.requireNonNull(skin));
		});
	}
	
	private static <T extends LivingEntity, M extends EntityModel<T>> void addLayers(LivingEntityRenderer<T, M> renderer)
	{
		renderer.addLayer(new StoneSkinLayer<>(renderer));
	}
}
