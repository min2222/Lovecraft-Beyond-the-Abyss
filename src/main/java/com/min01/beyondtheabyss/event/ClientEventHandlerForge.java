package com.min01.beyondtheabyss.event;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.config.BTAConfig;
import com.min01.beyondtheabyss.entity.deepabyss.EntitySubmarine;
import com.min01.beyondtheabyss.entity.misc.EntityBTACameraShake;
import com.min01.beyondtheabyss.util.BTAClientUtil;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;

import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.client.event.ViewportEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@Mod.EventBusSubscriber(modid = BeyondtheAbyss.MODID, value = Dist.CLIENT, bus = Bus.FORGE)
public class ClientEventHandlerForge 
{   
    @SubscribeEvent
    public static void onComputeCameraAngles(ViewportEvent.ComputeCameraAngles event) 
    {
        Player player = BTAClientUtil.MC.player;
        float delta = BTAClientUtil.MC.getFrameTime();
        float ticksExistedDelta = player.tickCount + delta;
        if(player != null)
        {
        	if(BTAConfig.cameraShakes.get())
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
        	
            if(player.isPassenger() && player.getVehicle() instanceof EntitySubmarine && event.getCamera().isDetached())
            {
                event.getCamera().move(-event.getCamera().getMaxZoom(15.0F), event.getCamera().getMaxZoom(2.0F), 0);
            }
        }
    }
    
	@SubscribeEvent
	public static void onRenderPlayer(RenderPlayerEvent.Pre event)
	{
		Player player = event.getEntity();
        if(player.isPassenger() && player.getVehicle() instanceof EntitySubmarine submarine)
        {
        	float partialTicks = event.getPartialTick();
        	PoseStack stack = event.getPoseStack();
    		float yBodyRot = Mth.rotLerp(partialTicks, submarine.yBodyRotO, submarine.yBodyRot);
    		float yHeadRot = Mth.rotLerp(partialTicks, submarine.yHeadRotO, submarine.yHeadRot);
    		float yRot = yHeadRot - yBodyRot;
            //float xRot = Mth.lerp(partialTicks, submarine.xRotO, submarine.getXRot());
            stack.mulPose(Axis.YP.rotationDegrees(yRot));
            //stack.mulPose(Axis.XP.rotationDegrees(xRot));
        }
	}
}
