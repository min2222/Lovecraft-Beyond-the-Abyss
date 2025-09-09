package com.min01.beyondtheabyss.event;

import org.joml.Quaternionf;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.animation.IHierarchicalPlayerModel;
import com.min01.beyondtheabyss.config.BTAConfig;
import com.min01.beyondtheabyss.entity.deepabyss.EntitySubmarine;
import com.min01.beyondtheabyss.entity.misc.EntityBTACameraShake;
import com.min01.beyondtheabyss.item.animation.IAnimatableItem;
import com.min01.beyondtheabyss.util.BTAClientUtil;
import com.min01.beyondtheabyss.util.BTAUtil;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;

import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderBlockScreenEffectEvent;
import net.minecraftforge.client.event.RenderBlockScreenEffectEvent.OverlayType;
import net.minecraftforge.client.event.RenderHandEvent;
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
        	
            if(player.isPassenger() && player.getVehicle() instanceof EntitySubmarine submarine)
            {
            	if(event.getCamera().isDetached())
            	{
            		event.getCamera().move(-event.getCamera().getMaxZoom(15.0F), event.getCamera().getMaxZoom(2.0F), 0);
            	}
            	else
            	{
            		double partialTick = event.getPartialTick();
            		float partialTicks = BTAClientUtil.MC.getFrameTime();
            		double x = Mth.lerp(partialTick, submarine.xo, submarine.getX());
            		double y = Mth.lerp(partialTick, submarine.yo, submarine.getY());
            		double z = Mth.lerp(partialTick, submarine.zo, submarine.getZ());
            		float yRot = Mth.rotLerp(partialTicks, submarine.yRotO, submarine.getYRot());
                    float xRot = Mth.lerp(partialTicks, submarine.xRotO, submarine.getXRot());
            		Vec3 pos = new Vec3(x, y, z);
            		Vec3 lookPos = BTAUtil.getLookPos(new Vec2(xRot, yRot), pos, 0.0F, 1.75F + 1.5F, 2.0F);
            		event.getCamera().setPosition(lookPos);
            	}
            }
        }
    }
    
    @SubscribeEvent
    public static void onRenderBlockScreenEffect(RenderBlockScreenEffectEvent event)
    {
    	Player player = event.getPlayer();
    	if(event.getOverlayType() == OverlayType.WATER && BTAUtil.canSwimInAir(player))
    	{
    		event.setCanceled(true);
    	}
    }
    
    @SubscribeEvent
	public static void onRenderPlayerPre(RenderPlayerEvent.Pre event)
	{
		Player player = event.getEntity();
        if(player.isPassenger() && player.getVehicle() instanceof EntitySubmarine submarine)
        {
        	float partialTicks = event.getPartialTick();
        	PoseStack stack = event.getPoseStack();
    		float yRot = Mth.rotLerp(partialTicks, submarine.yRotO, submarine.getYRot());
            float xRot = Mth.lerp(partialTicks, submarine.xRotO, submarine.getXRot());
            stack.mulPose(new Quaternionf().rotationZYX(0.0F, (float) Math.toRadians(yRot), (float) Math.toRadians(xRot)));
        }
	}
    
	@SubscribeEvent
	public static void onRenderHand(RenderHandEvent event)
	{
		ItemStack itemStack = event.getItemStack();
		if(itemStack.getItem() instanceof IAnimatableItem item)
		{
			PoseStack stack = event.getPoseStack();
			MultiBufferSource bufferSource = event.getMultiBufferSource();
			renderPlayerArm(stack, bufferSource, event.getPackedLight(), HumanoidArm.RIGHT, itemStack, item, event.getPartialTick());
			renderPlayerArm(stack, bufferSource, event.getPackedLight(), HumanoidArm.LEFT, itemStack, item, event.getPartialTick());
			event.setCanceled(true);
		}
	}
	
	@SuppressWarnings("unchecked")
	public static void renderPlayerArm(PoseStack stack, MultiBufferSource bufferSource, int packedLight, HumanoidArm arm, ItemStack itemStack, IAnimatableItem item, float partialTicks)
	{
		stack.pushPose();
		Vec3 offset = item.getOffset();
		boolean flag = arm != HumanoidArm.LEFT;
		AbstractClientPlayer player = BTAClientUtil.MC.player;
		RenderSystem.setShaderTexture(0, player.getSkinTextureLocation());
		PlayerRenderer renderer = (PlayerRenderer) BTAClientUtil.MC.getEntityRenderDispatcher().<AbstractClientPlayer>getRenderer(player);
		stack.mulPose(Axis.ZP.rotationDegrees(180.0F));
		stack.translate(offset.x / 16.0F, offset.y / 16.0F, offset.z / 16.0F);
		((IHierarchicalPlayerModel<Player>) renderer.getModel()).setupAnimFirstPerson(player, 0, 0, player.tickCount + partialTicks, 0, 0);
		if(flag)
		{
			stack.pushPose();
			renderer.getModel().translateToHand(arm, stack);
			stack.mulPose(Axis.XP.rotationDegrees(-90.0F));
			stack.mulPose(Axis.YP.rotationDegrees(180.0F));
			stack.translate((float)(flag ? 1 : -1) / 16.0F, 0.125F, -0.625F);
	        BTAClientUtil.MC.getEntityRenderDispatcher().getItemInHandRenderer().renderItem(player, itemStack, ItemDisplayContext.THIRD_PERSON_RIGHT_HAND, flag, stack, bufferSource, packedLight);
			stack.popPose();
			renderer.getModel().rightArm.render(stack, bufferSource.getBuffer(RenderType.entitySolid(player.getSkinTextureLocation())), packedLight, OverlayTexture.NO_OVERLAY);
			renderer.getModel().rightSleeve.render(stack, bufferSource.getBuffer(RenderType.entityTranslucent(player.getSkinTextureLocation())), packedLight, OverlayTexture.NO_OVERLAY);
		}
		else
		{
			renderer.getModel().leftArm.render(stack, bufferSource.getBuffer(RenderType.entitySolid(player.getSkinTextureLocation())), packedLight, OverlayTexture.NO_OVERLAY);
			renderer.getModel().leftSleeve.render(stack, bufferSource.getBuffer(RenderType.entityTranslucent(player.getSkinTextureLocation())), packedLight, OverlayTexture.NO_OVERLAY);
		}
		stack.popPose();
	}
}
