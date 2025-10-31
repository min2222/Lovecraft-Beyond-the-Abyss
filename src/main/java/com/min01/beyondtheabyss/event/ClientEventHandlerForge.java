package com.min01.beyondtheabyss.event;

import java.util.HashMap;
import java.util.Map;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.animation.IHierarchicalPlayerModel;
import com.min01.beyondtheabyss.config.BTAConfig;
import com.min01.beyondtheabyss.entity.deepabyss.EntitySubmarine;
import com.min01.beyondtheabyss.entity.misc.EntityBTACameraShake;
import com.min01.beyondtheabyss.item.BTAItems;
import com.min01.beyondtheabyss.item.animation.IAnimatableItem;
import com.min01.beyondtheabyss.util.BTAClientUtil;
import com.min01.beyondtheabyss.util.BTAUtil;
import com.min01.beyondtheabyss.util.DeepAbyssUtil;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;

import net.minecraft.client.Camera;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.player.Input;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.MovementInputUpdateEvent;
import net.minecraftforge.client.event.RenderBlockScreenEffectEvent;
import net.minecraftforge.client.event.RenderBlockScreenEffectEvent.OverlayType;
import net.minecraftforge.client.event.RenderHandEvent;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import net.minecraftforge.client.event.RenderLevelStageEvent.Stage;
import net.minecraftforge.client.event.ViewportEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@Mod.EventBusSubscriber(modid = BeyondtheAbyss.MODID, value = Dist.CLIENT, bus = Bus.FORGE)
public class ClientEventHandlerForge 
{
	public static final Map<ResourceKey<Level>, BlockPos> ABYSS_PORTAL_POS = new HashMap<>();
	public static final Map<ResourceKey<Level>, Boolean> ABYSS_PORTAL_ACTIVATED = new HashMap<>();
	
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
    public static void onMovementInputUpdate(MovementInputUpdateEvent event)
    {
    	Player player = event.getEntity();
    	Input input = event.getInput();
    	if(!player.isPassenger()) 
    	{
    		if(player.isHolding(BTAItems.SKELETAL_GUNBLADE.get()))
    		{
    			if(BTAUtil.getPlayerAnimationState(player) == 4)
    			{
            		input.leftImpulse *= 0.2F;
            		input.forwardImpulse *= 0.2F;
    			}
    			if(BTAUtil.getPlayerAnimationState(player) == 5)
    			{
            		input.leftImpulse *= 0.0F;
            		input.forwardImpulse *= 0.0F;
    			}
    		}
    	}
    }
    
    @SubscribeEvent
    public static void onRenderBlockScreenEffect(RenderBlockScreenEffectEvent event)
    {
    	Player player = event.getPlayer();
    	if(event.getOverlayType() == OverlayType.WATER)
    	{
    		if(BTAUtil.canSwimInAir(player) || DeepAbyssUtil.isInsideSubmarine(player))
    		{
        		event.setCanceled(true);
    		}
    	}
    }
    
    @SubscribeEvent
    public static void onRenderLevelStage(RenderLevelStageEvent event)
    {
    	if(event.getStage() == Stage.AFTER_ENTITIES)
    	{
    		Camera cam = event.getCamera();
	    	Vec3 camPos = cam.getPosition();
	    	PoseStack stack = event.getPoseStack();
	    	MultiBufferSource bufferSource = BTAClientUtil.MC.renderBuffers().bufferSource();
	    	ResourceKey<Level> dimension = BTAClientUtil.MC.level.dimension();
	    	float partialTicks = event.getPartialTick();
	    	if(ABYSS_PORTAL_POS.containsKey(dimension) && ABYSS_PORTAL_ACTIVATED.containsKey(dimension))
	    	{
	    		BlockPos blockPos = ABYSS_PORTAL_POS.get(dimension);
	    		boolean isActivated = ABYSS_PORTAL_ACTIVATED.get(dimension);
		    	if(!blockPos.equals(BlockPos.ZERO) && isActivated)
		    	{
		    		Vec3 pos = Vec3.atBottomCenterOf(blockPos);
		            float time = (BTAClientUtil.MC.level.getGameTime() + partialTicks) / 20.0F;
			    	stack.pushPose();
			    	stack.translate(pos.x - camPos.x, pos.y - camPos.y, pos.z - camPos.z);
			    	stack.translate(0, 7.5F, 0);
			    	stack.mulPose(Axis.XP.rotationDegrees(90.0F));
			    	Vec3 color = new Vec3(0.0F, 1.0F, 0.4F);
			    	RenderType renderType1 = RenderType.entityTranslucent(new ResourceLocation(BeyondtheAbyss.MODID, "textures/vfx/water.png"));
			    	RenderType renderType2 = RenderType.entityTranslucent(new ResourceLocation(BeyondtheAbyss.MODID, "textures/vfx/caustics2.png"));
			    	RenderType renderType3 = RenderType.entityTranslucent(new ResourceLocation(BeyondtheAbyss.MODID, "textures/vfx/caustics.png"));
		            BTAClientUtil.drawTorus(3.5F, 3.5F, 0.01F, 24, 24, 0.5F, stack, bufferSource, color, 1, LightTexture.FULL_BRIGHT, renderType1, time, Vec3.ZERO);
		            BTAClientUtil.drawTorus(3.505F, 3.505F, 0.01F, 24, 24, 0.5F, stack, bufferSource, color, 1, LightTexture.FULL_BRIGHT, renderType2, time, Vec3.ZERO);
		            BTAClientUtil.drawTorus(3.51F, 3.51F, 0.01F, 24, 24, 0.5F, stack, bufferSource, color, 1, LightTexture.FULL_BRIGHT, renderType3, time, Vec3.ZERO);
			    	stack.popPose();
		    	}
	    	}
    	}
    }
    
	@SubscribeEvent
	public static void onRenderHand(RenderHandEvent event)
	{
		ItemStack stack = event.getItemStack();
		AbstractClientPlayer player = BTAClientUtil.MC.player;
		if(stack.getItem() instanceof IAnimatableItem item && item.isFirstPersonAnim(stack, player))
		{
			PoseStack poseStack = event.getPoseStack();
			MultiBufferSource bufferSource = event.getMultiBufferSource();
			renderPlayerArm(player, poseStack, bufferSource, event.getPackedLight(), HumanoidArm.RIGHT, stack, item, event.getPartialTick());
			renderPlayerArm(player, poseStack, bufferSource, event.getPackedLight(), HumanoidArm.LEFT, stack, item, event.getPartialTick());
			event.setCanceled(true);
		}
	}
	
	@SuppressWarnings("unchecked")
	public static void renderPlayerArm(AbstractClientPlayer player, PoseStack stack, MultiBufferSource bufferSource, int packedLight, HumanoidArm arm, ItemStack itemStack, IAnimatableItem item, float partialTicks)
	{
		stack.pushPose();
		Vec3 offset = item.getOffset();
		boolean flag = arm != HumanoidArm.LEFT;
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
