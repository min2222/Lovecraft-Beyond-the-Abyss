package com.min01.beyondtheabyss.event;

import java.util.Objects;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.animation.PlayerAnimation;
import com.min01.beyondtheabyss.animation.PlayerAnimations;
import com.min01.beyondtheabyss.block.BTABlocks;
import com.min01.beyondtheabyss.block.model.BiocrafterModel;
import com.min01.beyondtheabyss.block.model.BoneLeverModel;
import com.min01.beyondtheabyss.block.model.BoneLeverOnModel;
import com.min01.beyondtheabyss.block.model.BonePilesModel;
import com.min01.beyondtheabyss.block.model.BoneTorchModel;
import com.min01.beyondtheabyss.block.model.BoneWallTorchModel;
import com.min01.beyondtheabyss.block.model.ChainTrapModel;
import com.min01.beyondtheabyss.block.model.FallenSkeletonModel;
import com.min01.beyondtheabyss.block.model.FangSkullModel;
import com.min01.beyondtheabyss.block.model.GlaringBarnacleModel;
import com.min01.beyondtheabyss.block.model.LargeSkullModel;
import com.min01.beyondtheabyss.block.model.RiftwellingAltarModel;
import com.min01.beyondtheabyss.block.model.SittingSkeletonModel;
import com.min01.beyondtheabyss.blockentity.renderer.AnimatableBlockRenderer;
import com.min01.beyondtheabyss.blockentity.renderer.BiocrafterRenderer;
import com.min01.beyondtheabyss.blockentity.renderer.RiftwellingAltarRenderer;
import com.min01.beyondtheabyss.entity.BTAEntities;
import com.min01.beyondtheabyss.entity.model.ChainTrapChainModel;
import com.min01.beyondtheabyss.entity.model.ChainTrapMawModel;
import com.min01.beyondtheabyss.entity.model.CorpseAnglerModel;
import com.min01.beyondtheabyss.entity.model.CystShrapnelModel;
import com.min01.beyondtheabyss.entity.model.DeepAbyssPortalModel;
import com.min01.beyondtheabyss.entity.model.DuneDevourerBodyModel;
import com.min01.beyondtheabyss.entity.model.DuneDevourerHeadModel;
import com.min01.beyondtheabyss.entity.model.DuneDevourerTailModel;
import com.min01.beyondtheabyss.entity.model.EnergyBallModel;
import com.min01.beyondtheabyss.entity.model.ForneusBodyModel;
import com.min01.beyondtheabyss.entity.model.ForneusHeadModel;
import com.min01.beyondtheabyss.entity.model.ForneusTailModel;
import com.min01.beyondtheabyss.entity.model.FulgastraModel;
import com.min01.beyondtheabyss.entity.model.GhidruthModel;
import com.min01.beyondtheabyss.entity.model.GloomfishModel;
import com.min01.beyondtheabyss.entity.model.GnasherLeaderModel;
import com.min01.beyondtheabyss.entity.model.GnasherModel;
import com.min01.beyondtheabyss.entity.model.LithoshrimpModel;
import com.min01.beyondtheabyss.entity.model.MissileModel;
import com.min01.beyondtheabyss.entity.model.MutavoreCystModel;
import com.min01.beyondtheabyss.entity.model.MutavoreModel;
import com.min01.beyondtheabyss.entity.model.MysteriousGuyModel;
import com.min01.beyondtheabyss.entity.model.NecroshellModel;
import com.min01.beyondtheabyss.entity.model.ObserverModel;
import com.min01.beyondtheabyss.entity.model.OverseerModel;
import com.min01.beyondtheabyss.entity.model.PipeModel;
import com.min01.beyondtheabyss.entity.model.PutridBubbleModel;
import com.min01.beyondtheabyss.entity.model.SiamserpentBlasterModel;
import com.min01.beyondtheabyss.entity.model.SiamserpentBoneModel;
import com.min01.beyondtheabyss.entity.model.SiamserpentMiddleBoneModel;
import com.min01.beyondtheabyss.entity.model.SiamserpentSlasherModel;
import com.min01.beyondtheabyss.entity.model.SpineWormBodyModel;
import com.min01.beyondtheabyss.entity.model.SpineWormHeadModel;
import com.min01.beyondtheabyss.entity.model.SplittedFulgastraModel;
import com.min01.beyondtheabyss.entity.model.SubmarineModel;
import com.min01.beyondtheabyss.entity.model.ToothBulletModel;
import com.min01.beyondtheabyss.entity.model.ToothBulletShrapnel2Model;
import com.min01.beyondtheabyss.entity.model.ToothBulletShrapnelModel;
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
import com.min01.beyondtheabyss.geom.JavaModelUnbakedGeometry;
import com.min01.beyondtheabyss.gui.screen.BiocrafterScreen;
import com.min01.beyondtheabyss.item.BTAItems;
import com.min01.beyondtheabyss.item.animation.ClamOfGuidanceAnimation;
import com.min01.beyondtheabyss.item.animation.ItemAnimations;
import com.min01.beyondtheabyss.item.animation.SkeletalGunbladeAnimation;
import com.min01.beyondtheabyss.item.animation.ToothShotgunAnimation;
import com.min01.beyondtheabyss.item.deepabyss.ClamOfGuidanceItem;
import com.min01.beyondtheabyss.item.deepabyss.FlashlightItem;
import com.min01.beyondtheabyss.item.deepabyss.SkeletalGunbladeItem;
import com.min01.beyondtheabyss.item.model.ClamOfGuidanceModel;
import com.min01.beyondtheabyss.item.model.FelmetalDiverSetModel;
import com.min01.beyondtheabyss.item.model.FlashlightModel;
import com.min01.beyondtheabyss.item.model.SkeletalGunbladeModel;
import com.min01.beyondtheabyss.item.model.ToothShotgunModel;
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
import net.minecraftforge.client.event.ModelEvent;
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
	        BlockEntityRenderers.register(BTABlocks.ANIMATABLE_BLOCK_ENTITY.get(), AnimatableBlockRenderer::new);
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
	        ItemAnimations.register(BTAItems.CLAM_OF_GUIDANCE.get(), ClamOfGuidanceAnimation.CLAM_OPEN, (t, u, v) -> ClamOfGuidanceItem.isOpen(u));
	        ItemAnimations.register(BTAItems.SKELETAL_GUNBLADE.get(), SkeletalGunbladeAnimation.GUNBLADE_OPEN, (t, u, v) -> SkeletalGunbladeItem.isGunMode(u));
	        ItemAnimations.register(BTAItems.SKELETAL_GUNBLADE.get(), SkeletalGunbladeAnimation.GUNBLADE_CLOSE, (t, u, v) -> !SkeletalGunbladeItem.isGunMode(u));
	        ItemAnimations.register(BTAItems.TOOTH_SHOTGUN.get(), ToothShotgunAnimation.FREAKY, (t, u, v) -> t == 1);
	        ItemAnimations.register(BTAItems.TOOTH_SHOTGUN.get(), ToothShotgunAnimation.RELOAD, (t, u, v) -> t == 2);
	        ItemAnimations.register(BTAItems.TOOTH_SHOTGUN.get(), ToothShotgunAnimation.SHOOT, (t, u, v) -> t == 3);
	        ItemAnimations.register(BTAItems.TOOTH_SHOTGUN.get(), ToothShotgunAnimation.EMPTY, (t, u, v) -> t == 4);
	        ItemAnimations.register(BTAItems.TOOTH_SHOTGUN.get(), ToothShotgunAnimation.EMPTY2, (t, u, v) -> t == 5);
	        PlayerAnimations.register(true, PlayerAnimation.ToothShotgunAnimation.SHOTGUN_FIRE, (t, u) -> t == 1 && u.isHolding(BTAItems.TOOTH_SHOTGUN.get()));
	        PlayerAnimations.register(true, PlayerAnimation.ToothShotgunAnimation.SHOTGUN_HOLD, (t, u) -> t == 0 && u.isHolding(BTAItems.TOOTH_SHOTGUN.get()) && !u.isSprinting());
	        PlayerAnimations.register(true, PlayerAnimation.ToothShotgunAnimation.SHOTGUN_RUNNING, (t, u) -> t == 0 && u.isHolding(BTAItems.TOOTH_SHOTGUN.get()) && u.isSprinting());
	        PlayerAnimations.register(false, PlayerAnimation.SkeletalGunbladeAnimation.CHARGE, (t, u) -> t == 3 && u.isHolding(BTAItems.SKELETAL_GUNBLADE.get()));
	        PlayerAnimations.register(false, PlayerAnimation.SkeletalGunbladeAnimation.SHOOT_BEAM, (t, u) -> t == 4 && u.isHolding(BTAItems.SKELETAL_GUNBLADE.get()));
	        PlayerAnimations.register(false, PlayerAnimation.SkeletalGunbladeAnimation.SWING, (t, u) -> t == 5 && u.isHolding(BTAItems.SKELETAL_GUNBLADE.get()));
		});
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
	public static void onRegisterGeometryLoaders(ModelEvent.RegisterGeometryLoaders event)
	{
		event.register("java_model", JavaModelUnbakedGeometry.Loader.INSTANCE);
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
    	event.registerLayerDefinition(MysteriousGuyModel.LAYER_LOCATION, MysteriousGuyModel::createBodyLayer);
    	event.registerLayerDefinition(GhidruthModel.LAYER_LOCATION, GhidruthModel::createBodyLayer);
    	event.registerLayerDefinition(SubmarineModel.LAYER_LOCATION, SubmarineModel::createBodyLayer);
    	event.registerLayerDefinition(GnasherModel.LAYER_LOCATION, GnasherModel::createBodyLayer);
    	event.registerLayerDefinition(GnasherLeaderModel.LAYER_LOCATION, GnasherLeaderModel::createBodyLayer);
    	event.registerLayerDefinition(SiamserpentSlasherModel.LAYER_LOCATION, SiamserpentSlasherModel::createBodyLayer);
    	event.registerLayerDefinition(SiamserpentBlasterModel.LAYER_LOCATION, SiamserpentBlasterModel::createBodyLayer);
    	event.registerLayerDefinition(SiamserpentBoneModel.LAYER_LOCATION, SiamserpentBoneModel::createBodyLayer);
    	event.registerLayerDefinition(SiamserpentMiddleBoneModel.LAYER_LOCATION, SiamserpentMiddleBoneModel::createBodyLayer);
    	event.registerLayerDefinition(SpineWormHeadModel.LAYER_LOCATION, SpineWormHeadModel::createBodyLayer);
    	event.registerLayerDefinition(SpineWormBodyModel.LAYER_LOCATION, SpineWormBodyModel::createBodyLayer);
    	event.registerLayerDefinition(ChainTrapMawModel.LAYER_LOCATION, ChainTrapMawModel::createBodyLayer);
    	event.registerLayerDefinition(ChainTrapChainModel.LAYER_LOCATION, ChainTrapChainModel::createBodyLayer);
    	event.registerLayerDefinition(GloomfishModel.LAYER_LOCATION, GloomfishModel::createBodyLayer);
    	event.registerLayerDefinition(CorpseAnglerModel.LAYER_LOCATION, CorpseAnglerModel::createBodyLayer);
    	event.registerLayerDefinition(MutavoreModel.LAYER_LOCATION, MutavoreModel::createBodyLayer);
    	event.registerLayerDefinition(FulgastraModel.LAYER_LOCATION, FulgastraModel::createBodyLayer);
    	event.registerLayerDefinition(SplittedFulgastraModel.LAYER_LOCATION, SplittedFulgastraModel::createBodyLayer);
    	event.registerLayerDefinition(NecroshellModel.LAYER_LOCATION, NecroshellModel::createBodyLayer);
    	event.registerLayerDefinition(LithoshrimpModel.LAYER_LOCATION, LithoshrimpModel::createBodyLayer);
    	event.registerLayerDefinition(ForneusHeadModel.LAYER_LOCATION, ForneusHeadModel::createBodyLayer);
    	event.registerLayerDefinition(ForneusBodyModel.LAYER_LOCATION, ForneusBodyModel::createBodyLayer);
    	event.registerLayerDefinition(ForneusTailModel.LAYER_LOCATION, ForneusTailModel::createBodyLayer);
    	event.registerLayerDefinition(EnergyBallModel.LAYER_LOCATION, EnergyBallModel::createBodyLayer);
    	event.registerLayerDefinition(PutridBubbleModel.LAYER_LOCATION, PutridBubbleModel::createBodyLayer);
    	event.registerLayerDefinition(ToothBulletModel.LAYER_LOCATION, ToothBulletModel::createBodyLayer);
    	event.registerLayerDefinition(ToothBulletShrapnelModel.LAYER_LOCATION, ToothBulletShrapnelModel::createBodyLayer);
    	event.registerLayerDefinition(ToothBulletShrapnel2Model.LAYER_LOCATION, ToothBulletShrapnel2Model::createBodyLayer);
    	event.registerLayerDefinition(MutavoreCystModel.LAYER_LOCATION, MutavoreCystModel::createBodyLayer);
    	event.registerLayerDefinition(CystShrapnelModel.LAYER_LOCATION, CystShrapnelModel::createBodyLayer);
    	event.registerLayerDefinition(DeepAbyssPortalModel.LAYER_LOCATION, DeepAbyssPortalModel::createBodyLayer);
    	event.registerLayerDefinition(PipeModel.LAYER_LOCATION, PipeModel::createBodyLayer);
    	
    	event.registerLayerDefinition(OverseerModel.LAYER_LOCATION, OverseerModel::createBodyLayer);
    	event.registerLayerDefinition(ObserverModel.LAYER_LOCATION, ObserverModel::createBodyLayer);
    	event.registerLayerDefinition(MissileModel.LAYER_LOCATION, MissileModel::createBodyLayer);
    	
    	event.registerLayerDefinition(DuneDevourerHeadModel.LAYER_LOCATION, DuneDevourerHeadModel::createBodyLayer);
    	event.registerLayerDefinition(DuneDevourerBodyModel.LAYER_LOCATION, DuneDevourerBodyModel::createBodyLayer);
    	event.registerLayerDefinition(DuneDevourerTailModel.LAYER_LOCATION, DuneDevourerTailModel::createBodyLayer);

    	//armors
    	event.registerLayerDefinition(FelmetalDiverSetModel.LAYER_LOCATION, FelmetalDiverSetModel::createBodyLayer);

    	//blocks
    	event.registerLayerDefinition(RiftwellingAltarModel.LAYER_LOCATION, RiftwellingAltarModel::createBodyLayer);
    	event.registerLayerDefinition(FangSkullModel.LAYER_LOCATION, FangSkullModel::createBodyLayer);
    	event.registerLayerDefinition(LargeSkullModel.LAYER_LOCATION, LargeSkullModel::createBodyLayer);
    	event.registerLayerDefinition(BonePilesModel.LAYER_LOCATION, BonePilesModel::createBodyLayer);
    	event.registerLayerDefinition(SittingSkeletonModel.LAYER_LOCATION, SittingSkeletonModel::createBodyLayer);
    	event.registerLayerDefinition(FallenSkeletonModel.LAYER_LOCATION, FallenSkeletonModel::createBodyLayer);
    	event.registerLayerDefinition(BoneTorchModel.LAYER_LOCATION, BoneTorchModel::createBodyLayer);
    	event.registerLayerDefinition(BoneWallTorchModel.LAYER_LOCATION, BoneWallTorchModel::createBodyLayer);
    	event.registerLayerDefinition(BoneLeverModel.LAYER_LOCATION, BoneLeverModel::createBodyLayer);
    	event.registerLayerDefinition(BoneLeverOnModel.LAYER_LOCATION, BoneLeverOnModel::createBodyLayer);
    	event.registerLayerDefinition(ChainTrapModel.LAYER_LOCATION, ChainTrapModel::createBodyLayer);
    	event.registerLayerDefinition(BiocrafterModel.LAYER_LOCATION, BiocrafterModel::createBodyLayer);
    	event.registerLayerDefinition(GlaringBarnacleModel.LAYER_LOCATION, GlaringBarnacleModel::createBodyLayer);

    	//items
    	event.registerLayerDefinition(FlashlightModel.LAYER_LOCATION, FlashlightModel::createBodyLayer);
    	event.registerLayerDefinition(SkeletalGunbladeModel.LAYER_LOCATION, SkeletalGunbladeModel::createBodyLayer);
    	event.registerLayerDefinition(ToothShotgunModel.LAYER_LOCATION, ToothShotgunModel::createBodyLayer);
    	event.registerLayerDefinition(ClamOfGuidanceModel.LAYER_LOCATION, ClamOfGuidanceModel::createBodyLayer);
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
