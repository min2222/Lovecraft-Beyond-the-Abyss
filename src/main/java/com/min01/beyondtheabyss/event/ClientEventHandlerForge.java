package com.min01.beyondtheabyss.event;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.cerbon.IMultipart;
import com.min01.beyondtheabyss.config.BTAConfig;
import com.min01.beyondtheabyss.effect.BTAEffects;
import com.min01.beyondtheabyss.entity.deepabyss.EntitySubmarine;
import com.min01.beyondtheabyss.entity.misc.EntityBTACameraShake;
import com.min01.beyondtheabyss.entity.multipart.EntityPartBuilder;
import com.min01.beyondtheabyss.gui.overlay.HallucinationOverlay;
import com.min01.beyondtheabyss.util.BTAClientUtil;
import com.min01.beyondtheabyss.world.BTAWorlds;
import com.min01.beyondtheabyss.world.deepabyss.DeepAbyssDimensionSpecialEffects;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;

import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.material.FogType;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
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
    public static void onRenderEntity(RenderEntityEvent<?> event)
    {
    	LivingEntity living = event.getEntity();
    	if(living instanceof IMultipart partBuilder)
    	{
    		EntityPartBuilder<?> builder = partBuilder.getPartBuilder();
    		HierarchicalModel<?> model = BTAClientUtil.getModelFromEntity(living);
    		builder.clientTick(model);
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
        		PoseStack poseStack = event.getPoseStack();
                int screenWidth = BTAClientUtil.MC.getWindow().getGuiScaledWidth();
                int screenHeight = BTAClientUtil.MC.getWindow().getGuiScaledHeight();
                IGuiOverlay overlay = GuiOverlayManager.findOverlay(VanillaGuiOverlay.FOOD_LEVEL.id()).overlay();
        		event.setCanceled(true);
        		poseStack.pushPose();
        		poseStack.translate(-100, 0, 0);
        		overlay.render((ForgeGui) BTAClientUtil.MC.gui, poseStack, event.getPartialTick(), screenWidth, screenHeight);
        		poseStack.popPose();
        	}
        	
        	if(event.getOverlay() == VanillaGuiOverlay.PLAYER_HEALTH.type())
        	{
        		PoseStack poseStack = event.getPoseStack();
                int screenWidth = BTAClientUtil.MC.getWindow().getGuiScaledWidth();
                int screenHeight = BTAClientUtil.MC.getWindow().getGuiScaledHeight();
                IGuiOverlay overlay = GuiOverlayManager.findOverlay(VanillaGuiOverlay.PLAYER_HEALTH.id()).overlay();
        		event.setCanceled(true);
        		poseStack.pushPose();
        		poseStack.translate(100, 0, 0);
        		overlay.render((ForgeGui) BTAClientUtil.MC.gui, poseStack, event.getPartialTick(), screenWidth, screenHeight);
        		poseStack.popPose();
        	}
    	}
    }
    
	//FIXME
    //@SubscribeEvent
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
    public static void onComputeCameraAngles(ViewportEvent.ComputeCameraAngles event) 
    {
        Player player = BTAClientUtil.MC.player;
        float delta = BTAClientUtil.MC.getFrameTime();
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
    public static void onComputeFogColor(ViewportEvent.ComputeFogColor event)
    {
    	ClientLevel level = BTAClientUtil.MC.level;
    	Entity entity = event.getCamera().getEntity();
        if(level.dimension() == BTAWorlds.DEEP_ABYSS)
        {
        	FogType fogType = event.getCamera().getFluidInCamera();
            if(fogType == FogType.WATER)
            {
            	if(level.getBiome(entity.blockPosition()).is(new ResourceLocation(BeyondtheAbyss.MODID, "death_valley")))
            	{
                	Vec3 color = Vec3.fromRGB24(6704177);
                    event.setRed((float) color.x);
                    event.setGreen((float) color.y);
                    event.setBlue((float) color.z);
            	}
            }
        }
    }
}
