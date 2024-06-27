package com.min01.beyondtheabyss.event;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.capabilities.BTACapabilities;
import com.min01.beyondtheabyss.config.BTAConfig;
import com.min01.beyondtheabyss.effect.BTAEffects;
import com.min01.beyondtheabyss.entity.deepabyss.EntityGhidruth;
import com.min01.beyondtheabyss.entity.misc.EntityBTACameraShake;
import com.min01.beyondtheabyss.entity.submarine.EntitySubmarine;
import com.min01.beyondtheabyss.item.BTAItems;
import com.min01.beyondtheabyss.util.BTAClientUtil;
import com.min01.beyondtheabyss.world.deepabyss.DeepAbyssDimensionSpecialEffects;
import com.min01.beyondtheabyss.world.deepabyss.DeepAbyssSkyRenderer;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.FogRenderer;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
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
import net.minecraftforge.event.level.LevelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@Mod.EventBusSubscriber(modid = BeyondtheAbyss.MODID, value = Dist.CLIENT, bus = Bus.FORGE)
public class ClientEventHandlerForge 
{
	public static final Minecraft MC = Minecraft.getInstance();
    
    //TEST
    //@SubscribeEvent
    public static void onRenderTick(RenderLevelStageEvent event)
    {
    	if(event.getStage() != Stage.AFTER_PARTICLES)
    		return;
    	if(MC.player != null)
    	{
    		BTAClientUtil.testShader(event.getPartialTick());
    	}
    }
    
    @SubscribeEvent
    public static void onRenderLevelStage(RenderLevelStageEvent event)
    {
    	if(event.getStage() == Stage.AFTER_PARTICLES)
    	{
    		PoseStack poseStack = event.getPoseStack();
    		MC.player.getCapability(BTACapabilities.ILLUSION).ifPresent(cap -> 
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
            			livingRenderer.render(ghidruth, f, partialTick, event.getPoseStack(), MC.renderBuffers().bufferSource(), LightTexture.FULL_BRIGHT);
                		poseStack.popPose();
        			}
        		}
    		});
    	}
    }
    
    @SubscribeEvent
    public static void onRenderGuiOverlayEvent(RenderGuiOverlayEvent event)
    {
    	if(MC.player.hasEffect(BTAEffects.HALLUCINATION.get()))
    	{
        	if(event.getOverlay() == VanillaGuiOverlay.FOOD_LEVEL.type())
        	{
        		PoseStack poseStack = event.getPoseStack();
                int screenWidth = MC.getWindow().getGuiScaledWidth();
                int screenHeight = MC.getWindow().getGuiScaledHeight();
                IGuiOverlay overlay = GuiOverlayManager.findOverlay(VanillaGuiOverlay.FOOD_LEVEL.id()).overlay();
        		event.setCanceled(true);
        		poseStack.pushPose();
        		poseStack.translate(-100, 0, 0);
        		overlay.render((ForgeGui) MC.gui, poseStack, event.getPartialTick(), screenWidth, screenHeight);
        		poseStack.popPose();
        	}
        	
        	if(event.getOverlay() == VanillaGuiOverlay.PLAYER_HEALTH.type())
        	{
        		PoseStack poseStack = event.getPoseStack();
                int screenWidth = MC.getWindow().getGuiScaledWidth();
                int screenHeight = MC.getWindow().getGuiScaledHeight();
                IGuiOverlay overlay = GuiOverlayManager.findOverlay(VanillaGuiOverlay.PLAYER_HEALTH.id()).overlay();
        		event.setCanceled(true);
        		poseStack.pushPose();
        		poseStack.translate(100, 0, 0);
        		overlay.render((ForgeGui) MC.gui, poseStack, event.getPartialTick(), screenWidth, screenHeight);
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
            poseStack.mulPose(Vector3f.YP.rotationDegrees(f2));
            poseStack.mulPose(Vector3f.XP.rotationDegrees(f6));
    	}
    }
    
    @SubscribeEvent
    public static void onSetupCamera(ViewportEvent.ComputeCameraAngles event) 
    {
        Player player = MC.player;
        float delta = Minecraft.getInstance().getFrameTime();
        float ticksExistedDelta = player.tickCount + delta;
        if(player != null && BTAConfig.cameraShakes.get())
        {
            float shakeAmplitude = 0.0f;
            for(EntityBTACameraShake cameraShake : player.level.getEntitiesOfClass(EntityBTACameraShake.class, player.getBoundingBox().inflate(100.0))) 
            {
                if(cameraShake.distanceTo(player) < cameraShake.getRadius())
                {
                    shakeAmplitude += cameraShake.getShakeAmount(player, delta);
                }
            }
            if(shakeAmplitude > 1.0f)
            {
                shakeAmplitude = 1.0f;
            }
            event.setPitch((float)(event.getPitch() + shakeAmplitude * Math.cos(ticksExistedDelta * 3.0f + 2.0f) * 25.0));
            event.setYaw((float)(event.getYaw() + shakeAmplitude * Math.cos(ticksExistedDelta * 5.0f + 1.0f) * 25.0));
            event.setRoll((float)(event.getRoll() + shakeAmplitude * Math.cos(ticksExistedDelta * 4.0f) * 25.0));
        }
    }
	
    @SubscribeEvent
    public static void onClientTickEvent(ClientTickEvent event) 
    {
        ClientLevel world = MC.level;
        if(world != null && !MC.isPaused() && MC.player != null)
        {
        	if(MC.gameRenderer.currentEffect() == null)
        	{
            	if(world.dimension().location().getPath().equals("deep_abyss"))
            	{
                    if(BTAConfig.enableAbyssShader.get())
                    {
                    	MC.gameRenderer.loadEffect(new ResourceLocation(BeyondtheAbyss.MODID, "shaders/post/abyss.json"));
                    }
            	}
            	
            	if(MC.player.hasEffect(BTAEffects.HALLUCINATION.get()))
            	{
            		MC.gameRenderer.loadEffect(new ResourceLocation("shaders/post/deconverge.json"));
            	}
        	}
        	else
        	{
            	if(MC.gameRenderer.currentEffect().getName().equals("beyondtheabyss:shaders/post/abyss.json") && !world.dimension().location().getPath().equals("deep_abyss"))
            	{
            		MC.gameRenderer.shutdownEffect();
            	}
            	
            	if(MC.gameRenderer.currentEffect().getName().equals("minecraft:shaders/post/deconverge.json") && !MC.player.hasEffect(BTAEffects.HALLUCINATION.get()))
            	{
            		MC.gameRenderer.shutdownEffect();
            	}
        	}
        }
    }
    
    @SubscribeEvent
    public static void onLevelLoad(LevelEvent.Load event) 
    {
        if(event.getLevel() instanceof ClientLevel)
        {
        	ClientLevel world = (ClientLevel) event.getLevel();
            if(world.dimension().location().getPath().equals("deep_abyss"))
            {
                world.effects = new DeepAbyssDimensionSpecialEffects();
            }
        }
    }
    
    @SubscribeEvent
    public static void onRenderFog(ViewportEvent.RenderFog event)
    {
    	ClientLevel world = MC.level;
        if(world.dimension().location().getPath().equals("deep_abyss"))
        {
        	FogType fogtype = event.getCamera().getFluidInCamera();
            if(fogtype == FogType.WATER)
            {
            	if(!MC.player.isSpectator() && !MC.player.getAbilities().instabuild && MC.player.isInWater())
            	{
                	if(MC.player.getItemBySlot(EquipmentSlot.HEAD).getItem() != BTAItems.GHIDRUTH_DIVING_HELMET.get())
                	{
                		int amount = 40;
                    	if(MC.player.getItemBySlot(EquipmentSlot.HEAD).getItem() == BTAItems.DIVING_HELMET.get())
                    	{
                    		amount = 25;
                    	}
                    	else if(MC.player.getItemBySlot(EquipmentSlot.HEAD).getItem() == BTAItems.ADVANCED_DIVING_HELMET.get())
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
    public static void onFogColors(ViewportEvent.ComputeFogColor event)
    {
    	ClientLevel world = MC.level;
        if(world.dimension().location().getPath().equals("deep_abyss"))
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
    
    //useless (already handled in onWorldLoad) + cause lag in dimension
    //@SubscribeEvent
    public static void onRenderLevel(RenderLevelStageEvent event) 
    {
        ClientLevel world = MC.level;
        if(world.dimension().location().getPath().equals("deep_abyss"))
        {
        	DeepAbyssSkyRenderer skyRenderer = new DeepAbyssSkyRenderer();
        	PoseStack stack = new PoseStack();
        	skyRenderer.renderSky(stack, stack.last().pose(), (float) event.getPartialTick(), event.getCamera(), false, new Runnable()
        	{
				@Override
				public void run() 
				{
					FogRenderer.setupFog(event.getCamera(), FogRenderer.FogMode.FOG_SKY, MC.gameRenderer.getRenderDistance(), false, (float) event.getPartialTick());
				}
			});
        }
    }
}
