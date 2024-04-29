package com.min01.beyondtheabyss.event;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.config.BTAConfig;
import com.min01.beyondtheabyss.entity.misc.EntityBTACameraShake;
import com.min01.beyondtheabyss.entity.submarine.EntitySubmarine;
import com.min01.beyondtheabyss.item.BTAItems;
import com.min01.beyondtheabyss.network.BTANetwork;
import com.min01.beyondtheabyss.network.KeyInputPacket;
import com.min01.beyondtheabyss.network.KeyInputPacket.InputType;
import com.min01.beyondtheabyss.world.deepabyss.DeepAbyssDimensionSpecialEffects;
import com.min01.beyondtheabyss.world.deepabyss.DeepAbyssSkyRenderer;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.FogRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.material.FogType;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.client.event.ViewportEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.level.LevelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@Mod.EventBusSubscriber(modid = BeyondtheAbyss.MODID, value = Dist.CLIENT, bus = Bus.FORGE)
public class ClientEventHandlerForge 
{
	private static final Minecraft MC = Minecraft.getInstance();
    private static boolean YkeyPressed = false;
    
    //FIXME
    //@SubscribeEvent
    public static void onRenderPlayer(RenderPlayerEvent.Pre event)
    {
    	Player player = event.getEntity();
    	if(player.getVehicle() != null && player.getVehicle() instanceof EntitySubmarine submarine)
    	{
    		PoseStack poseStack = event.getPoseStack();
            float f1 = Mth.rotLerp(event.getPartialTick(), submarine.yRotO, submarine.getYRot());
            float f6 = Mth.lerp(event.getPartialTick(), submarine.xRotO, submarine.getXRot());
            poseStack.mulPose(Vector3f.YP.rotationDegrees(180 - f1));
            poseStack.mulPose(Vector3f.XP.rotationDegrees(-f6));
    	}
    }
    
    @SubscribeEvent
    public static void onSetupCamera(ViewportEvent.ComputeCameraAngles event) 
    {
        Player player = MC.player;
        float delta = Minecraft.getInstance().getFrameTime();
        float ticksExistedDelta = player.tickCount + delta;
        if (player != null && BTAConfig.cameraShakesAllowed.get())
        {
            float shakeAmplitude = 0.0f;
            for (EntityBTACameraShake cameraShake : player.level.getEntitiesOfClass(EntityBTACameraShake.class, player.getBoundingBox().inflate(100.0))) 
            {
                if (cameraShake.distanceTo(player) < cameraShake.getRadius())
                {
                    shakeAmplitude += cameraShake.getShakeAmount(player, delta);
                }
            }
            if (shakeAmplitude > 1.0f)
            {
                shakeAmplitude = 1.0f;
            }
            event.setPitch((float)(event.getPitch() + shakeAmplitude * Math.cos(ticksExistedDelta * 3.0f + 2.0f) * 25.0));
            event.setYaw((float)(event.getYaw() + shakeAmplitude * Math.cos(ticksExistedDelta * 5.0f + 1.0f) * 25.0));
            event.setRoll((float)(event.getRoll() + shakeAmplitude * Math.cos(ticksExistedDelta * 4.0f) * 25.0));
        }
    }
	
    @SubscribeEvent
    public static void onTickEvent(TickEvent.ClientTickEvent event) 
    {
        if (event.phase == TickEvent.Phase.END) 
        	return;
        if(ClientEventHandler.ABYSSAL_DASH.isDown())
        {
            if(!YkeyPressed) 
            {
            	YkeyPressed = true;
            	BTANetwork.CHANNEL.sendToServer(new KeyInputPacket(InputType.ABYSSAL_DASH));
            }
        } 
        else
        {
        	YkeyPressed = false;
        }

        ClientLevel world = MC.level;
        if(world != null)
        {
        	if(world.dimension().location().getPath().equals("deep_abyss"))
        	{
                if(!MC.isPaused() && MC.player != null && BTAConfig.enableAbyssShader.get())
                {
                	MC.gameRenderer.loadEffect(new ResourceLocation(BeyondtheAbyss.MODID, "shaders/post/abyss.json"));
                }
        	}
            else
            {
            	if(MC.gameRenderer.currentEffect() != null)
            	{
                	if(MC.gameRenderer.currentEffect().getName().equals("beyondtheabyss:shaders/post/abyss.json"))
                	{
                		MC.gameRenderer.shutdownEffect();
                	}
            	}
            }
        }
    }
    
    @SubscribeEvent
    public static void onWorldLoad(LevelEvent.Load event) 
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
