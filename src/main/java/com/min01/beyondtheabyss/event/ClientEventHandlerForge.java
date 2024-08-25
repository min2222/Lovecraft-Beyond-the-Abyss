package com.min01.beyondtheabyss.event;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.capabilities.BTACapabilities;
import com.min01.beyondtheabyss.config.BTAConfig;
import com.min01.beyondtheabyss.effect.BTAEffects;
import com.min01.beyondtheabyss.entity.deepabyss.EntityGhidruth;
import com.min01.beyondtheabyss.entity.misc.EntityBTACameraShake;
import com.min01.beyondtheabyss.entity.submarine.EntitySubmarine;
import com.min01.beyondtheabyss.gui.overlay.HallucinationOverlay;
import com.min01.beyondtheabyss.item.BTAItems;
import com.min01.beyondtheabyss.util.BTAClientUtil;
import com.min01.beyondtheabyss.world.BTAWorlds;
import com.min01.beyondtheabyss.world.deepabyss.DeepAbyssDimensionSpecialEffects;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.material.FogType;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import net.minecraftforge.client.event.RenderLevelStageEvent.Stage;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.client.event.ViewportEvent;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import net.minecraftforge.client.gui.overlay.GuiOverlayManager;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;
import net.minecraftforge.event.TickEvent.ClientTickEvent;
import net.minecraftforge.event.entity.living.MobEffectEvent;
import net.minecraftforge.event.level.LevelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@Mod.EventBusSubscriber(modid = BeyondtheAbyss.MODID, value = Dist.CLIENT, bus = Bus.FORGE)
public class ClientEventHandlerForge 
{
    @SubscribeEvent
    public static void onRenderLevelStage(RenderLevelStageEvent event)
    {
    	if(event.getStage() == Stage.AFTER_PARTICLES)
    	{
    		PoseStack poseStack = event.getPoseStack();
    		BTAClientUtil.MC.player.getCapability(BTACapabilities.ILLUSION).ifPresent(cap -> 
    		{
        		EntityGhidruth ghidruth = (EntityGhidruth) cap.getIllusion();
        		if(ghidruth != null)
        		{
        			EntityRenderer<? super LivingEntity> renderer = Minecraft.getInstance().getEntityRenderDispatcher().getRenderer(ghidruth);
        			if(renderer instanceof LivingEntityRenderer)
        			{
        	    		poseStack.pushPose();
            			LivingEntityRenderer<? super LivingEntity, ?> livingRenderer = (LivingEntityRenderer<? super LivingEntity, ?>) renderer;
                		float partialTick = event.getPartialTick();
            			float f = Mth.lerp(partialTick, ghidruth.yRotO, ghidruth.getYRot());
            			double x = Mth.lerp((double)partialTick, ghidruth.xOld, ghidruth.getX());
            			double y = Mth.lerp((double)partialTick, ghidruth.yOld, ghidruth.getY());
            			double z = Mth.lerp((double)partialTick, ghidruth.zOld, ghidruth.getZ());
            			Vec3 lerpPos = new Vec3(x, y, z);	
            			Vec3 pos = lerpPos.subtract(event.getCamera().getPosition());
            	        poseStack.translate(pos.x, pos.y, pos.z);
            			livingRenderer.render(ghidruth, f, partialTick, event.getPoseStack(), BTAClientUtil.MC.renderBuffers().bufferSource(), LightTexture.FULL_BRIGHT);
                		poseStack.popPose();
        			}
        		}
    		});
    	}
    }
    
    //test
    //@SubscribeEvent
    public static void onRenderGuiOverlayEvent(RenderGuiOverlayEvent event)
    {
    	if(BTAClientUtil.MC.player.hasEffect(BTAEffects.HALLUCINATION.get()))
    	{
        	if(event.getOverlay() == VanillaGuiOverlay.FOOD_LEVEL.type())
        	{
        		GuiGraphics guiGraphics = event.getGuiGraphics();
        		PoseStack poseStack = guiGraphics.pose();
                int screenWidth = BTAClientUtil.MC.getWindow().getGuiScaledWidth();
                int screenHeight = BTAClientUtil.MC.getWindow().getGuiScaledHeight();
                IGuiOverlay overlay = GuiOverlayManager.findOverlay(VanillaGuiOverlay.FOOD_LEVEL.id()).overlay();
        		event.setCanceled(true);
        		poseStack.pushPose();
        		poseStack.translate(-100, 0, 0);
        		overlay.render((ForgeGui) BTAClientUtil.MC.gui, guiGraphics, event.getPartialTick(), screenWidth, screenHeight);
        		poseStack.popPose();
        	}
        	
        	if(event.getOverlay() == VanillaGuiOverlay.PLAYER_HEALTH.type())
        	{
        		GuiGraphics guiGraphics = event.getGuiGraphics();
        		PoseStack poseStack = guiGraphics.pose();
                int screenWidth = BTAClientUtil.MC.getWindow().getGuiScaledWidth();
                int screenHeight = BTAClientUtil.MC.getWindow().getGuiScaledHeight();
                IGuiOverlay overlay = GuiOverlayManager.findOverlay(VanillaGuiOverlay.PLAYER_HEALTH.id()).overlay();
        		event.setCanceled(true);
        		poseStack.pushPose();
        		poseStack.translate(100, 0, 0);
        		overlay.render((ForgeGui) BTAClientUtil.MC.gui, guiGraphics, event.getPartialTick(), screenWidth, screenHeight);
        		poseStack.popPose();
        	}
    	}
    }
    
	//FIXME
    @SubscribeEvent
    public static void onRenderPlayer(RenderPlayerEvent event)
    {
    	Player player = event.getEntity();
    	float partialTick = event.getPartialTick();
    	if(player.getVehicle() != null && player.getVehicle() instanceof EntitySubmarine submarine)
    	{
    		PoseStack poseStack = event.getPoseStack();
    		float f = Mth.rotLerp(partialTick, submarine.yBodyRotO, submarine.yBodyRot);
    		float f1 = Mth.rotLerp(partialTick, submarine.yHeadRotO, submarine.yHeadRot);
    		float f2 = f1 - f;
            float f6 = Mth.lerp(partialTick, submarine.xRotO, submarine.getXRot());
            poseStack.mulPose(Axis.YP.rotationDegrees(f2 + 180));
            poseStack.mulPose(Axis.XP.rotationDegrees(f6));
    	}
    }
    
    @SubscribeEvent
    public static void onComputeCameraAngles(ViewportEvent.ComputeCameraAngles event) 
    {
        Player player = BTAClientUtil.MC.player;
        float delta = Minecraft.getInstance().getFrameTime();
        float ticksExistedDelta = player.tickCount + delta;
        if(player != null && BTAConfig.cameraShakes.get())
        {
            float shakeAmplitude = 0.0F;
            for(EntityBTACameraShake cameraShake : player.level.getEntitiesOfClass(EntityBTACameraShake.class, player.getBoundingBox().inflate(100.0F))) 
            {
                if(cameraShake.distanceTo(player) < cameraShake.getRadius())
                {
                    shakeAmplitude += cameraShake.getShakeAmount(player, delta);
                }
            }
            if(shakeAmplitude > 1.0F)
            {
                shakeAmplitude = 1.0F;
            }
            event.setPitch((float)(event.getPitch() + shakeAmplitude * Math.cos(ticksExistedDelta * 3.0F + 2.0F) * 25.0));
            event.setYaw((float)(event.getYaw() + shakeAmplitude * Math.cos(ticksExistedDelta * 5.0F + 1.0F) * 25.0));
            event.setRoll((float)(event.getRoll() + shakeAmplitude * Math.cos(ticksExistedDelta * 4.0F) * 25.0));
        }
    }
    
	@SubscribeEvent
	public static void onMobEffectAdded(MobEffectEvent.Added event)
	{
		MobEffectInstance instance = event.getEffectInstance();
		MobEffect effect = instance.getEffect();
		if(effect == BTAEffects.HALLUCINATION.get())
		{
			HallucinationOverlay.ADD = true;
			HallucinationOverlay.reset();
		}
	}
	
    @SubscribeEvent
    public static void onClientTick(ClientTickEvent event) 
    {
        ClientLevel level = BTAClientUtil.MC.level;
        if(level != null && !BTAClientUtil.MC.isPaused() && BTAClientUtil.MC.player != null)
        {
        	if(BTAClientUtil.MC.gameRenderer.currentEffect() == null)
        	{
            	if(BTAClientUtil.MC.player.hasEffect(BTAEffects.HALLUCINATION.get()))
            	{
            		BTAClientUtil.MC.gameRenderer.loadEffect(new ResourceLocation("shaders/post/deconverge.json"));
            	}
        	}
        	else
        	{
            	if(BTAClientUtil.MC.gameRenderer.currentEffect().getName().equals("minecraft:shaders/post/deconverge.json") && !BTAClientUtil.MC.player.hasEffect(BTAEffects.HALLUCINATION.get()))
            	{
            		BTAClientUtil.MC.gameRenderer.shutdownEffect();
            	}
        	}
        }
    }
    
    @SubscribeEvent
    public static void onLevelLoad(LevelEvent.Load event) 
    {
        if(event.getLevel() instanceof ClientLevel level)
        {
            if(level.dimension() == BTAWorlds.DEEP_ABYSS)
            {
            	level.effects = new DeepAbyssDimensionSpecialEffects();
            }
        }
    }
    
    @SubscribeEvent
    public static void onRenderFog(ViewportEvent.RenderFog event)
    {
    	ClientLevel level = BTAClientUtil.MC.level;
        if(level.dimension() == BTAWorlds.DEEP_ABYSS)
        {
        	FogType fogtype = event.getCamera().getFluidInCamera();
            if(fogtype == FogType.WATER)
            {
            	if(!BTAClientUtil.MC.player.isSpectator() && !BTAClientUtil.MC.player.getAbilities().instabuild && BTAClientUtil.MC.player.isInWater())
            	{
                	if(BTAClientUtil.MC.player.getItemBySlot(EquipmentSlot.HEAD).getItem() != BTAItems.GHIDRUTH_DIVING_HELMET.get())
                	{
                		int amount = 40;
                    	if(BTAClientUtil.MC.player.getItemBySlot(EquipmentSlot.HEAD).getItem() == BTAItems.DIVING_HELMET.get())
                    	{
                    		amount = 25;
                    	}
                    	else if(BTAClientUtil.MC.player.getItemBySlot(EquipmentSlot.HEAD).getItem() == BTAItems.ADVANCED_DIVING_HELMET.get())
                    	{
                    		amount = 10;
                    	}
                        RenderSystem.setShaderFogStart(-8.0F + amount);
                        RenderSystem.setShaderFogEnd(50.0F - amount);
                	}
            	}
            }
        }
    }
    
    @SubscribeEvent
    public static void onComputeFogColor(ViewportEvent.ComputeFogColor event)
    {
    	ClientLevel level = BTAClientUtil.MC.level;
        if(level.dimension() == BTAWorlds.DEEP_ABYSS)
        {
        	FogType fogtype = event.getCamera().getFluidInCamera();
            if(fogtype == FogType.WATER)
            {
            	Vec3 color = Vec3.fromRGB24(65811);
                event.setRed((float) color.x);
                event.setGreen((float) color.y);
                event.setBlue((float) color.z);
            }
        }
    }
}
