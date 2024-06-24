package com.min01.beyondtheabyss.entity.renderer;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.entity.model.ModelSubmarine;
import com.min01.beyondtheabyss.entity.submarine.EntitySubmarine;
import com.min01.beyondtheabyss.network.BTANetwork;
import com.min01.beyondtheabyss.network.SubmarinePartUpdatePacket;
import com.min01.beyondtheabyss.util.BTAClientUtil;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;

public class SubmarineRenderer extends EntityRenderer<EntitySubmarine>
{
	private static final ResourceLocation TEXTURE = new ResourceLocation(BeyondtheAbyss.MODID, "textures/entity/submarine.png");
	private static final ResourceLocation LAYER_TEXTURE = new ResourceLocation(BeyondtheAbyss.MODID, "textures/entity/submarine_layer.png");
	private final ModelSubmarine model;
	public SubmarineRenderer(Context p_174008_)
	{
		super(p_174008_);
		this.model = new ModelSubmarine(p_174008_.bakeLayer(ModelSubmarine.LAYER_LOCATION));
	}
	
	@Override
	public void render(EntitySubmarine p_114485_, float p_114486_, float p_114487_, PoseStack p_114488_, MultiBufferSource p_114489_, int p_114490_)
	{
		p_114488_.pushPose();
		p_114488_.scale(-1.0F, -1.0F, 1.0F);
		p_114488_.translate(0, -1.5F, 0);
		VertexConsumer consumer = p_114489_.getBuffer(RenderType.entityTranslucent(TEXTURE));
		float f = Mth.rotLerp(p_114487_, p_114485_.yBodyRotO, p_114485_.yBodyRot);
		float f1 = Mth.rotLerp(p_114487_, p_114485_.yHeadRotO, p_114485_.yHeadRot);
		float f2 = f1 - f;
        float f6 = Mth.lerp(p_114487_, p_114485_.xRotO, p_114485_.getXRot());
		this.model.setupAnim(p_114485_, 0, 0, 0, f2 + 180, f6);
		this.model.renderToBuffer(p_114488_, consumer, p_114490_, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
		float yRot = f2;
		Vec3 detectorPos = BTAClientUtil.getWorldPosition(p_114485_, yRot, this.model.root(), new String[] {"submarine"});
		Vec3 topPos = BTAClientUtil.getWorldPosition(p_114485_, yRot, this.model.root(), new String[] {"submarine", "top"});
		Vec3 leftWallPos = BTAClientUtil.getWorldPosition(p_114485_, yRot, this.model.root(), new String[] {"submarine", "l_wall"});
		Vec3 rightWallPos = BTAClientUtil.getWorldPosition(p_114485_, yRot, this.model.root(), new String[] {"submarine", "r_wall"});
		Vec3 hatchPos = BTAClientUtil.getWorldPosition(p_114485_, yRot, this.model.root(), new String[] {"submarine", "hatch"});
		Vec3 frontPos = BTAClientUtil.getWorldPosition(p_114485_, yRot, this.model.root(), new String[] {"submarine", "front"});
		Vec3 backPos = BTAClientUtil.getWorldPosition(p_114485_, yRot, this.model.root(), new String[] {"submarine", "back"});
		Vec3 bottomPos = BTAClientUtil.getWorldPosition(p_114485_, yRot, this.model.root(), new String[] {"submarine", "bottom"});
		Vec3 seat4Pos = BTAClientUtil.getWorldPosition(p_114485_, yRot, this.model.root(), new String[] {"submarine", "seat4"});
		Vec3 seat3Pos = BTAClientUtil.getWorldPosition(p_114485_, yRot, this.model.root(), new String[] {"submarine", "seat3"});
		Vec3 seat2Pos = BTAClientUtil.getWorldPosition(p_114485_, yRot, this.model.root(), new String[] {"submarine", "seat2"});
		Vec3 seat1Pos = BTAClientUtil.getWorldPosition(p_114485_, yRot, this.model.root(), new String[] {"submarine", "seat1"});
		Vec3 controllerPos = BTAClientUtil.getWorldPosition(p_114485_, yRot, this.model.root(), new String[] {"submarine", "controller"});
		p_114485_.posArray[12] = detectorPos;
		p_114485_.posArray[11] = topPos;
		p_114485_.posArray[10] = leftWallPos;
		p_114485_.posArray[9] = rightWallPos;
		p_114485_.posArray[8] = hatchPos;
		p_114485_.posArray[7] = frontPos;
		p_114485_.posArray[6] = backPos;
		p_114485_.posArray[5] = bottomPos;
		p_114485_.posArray[4] = seat4Pos;
		p_114485_.posArray[3] = seat3Pos;
		p_114485_.posArray[2] = seat2Pos;
		p_114485_.posArray[1] = seat1Pos;
		p_114485_.posArray[0] = controllerPos;
	    BTANetwork.sendToAll(new SubmarinePartUpdatePacket(p_114485_, detectorPos, 12));
	    BTANetwork.sendToAll(new SubmarinePartUpdatePacket(p_114485_, topPos, 11));
	    BTANetwork.sendToAll(new SubmarinePartUpdatePacket(p_114485_, leftWallPos, 10));
	    BTANetwork.sendToAll(new SubmarinePartUpdatePacket(p_114485_, rightWallPos, 9));
	    BTANetwork.sendToAll(new SubmarinePartUpdatePacket(p_114485_, hatchPos, 8));
	    BTANetwork.sendToAll(new SubmarinePartUpdatePacket(p_114485_, frontPos, 7));
	    BTANetwork.sendToAll(new SubmarinePartUpdatePacket(p_114485_, backPos, 6));
	    BTANetwork.sendToAll(new SubmarinePartUpdatePacket(p_114485_, bottomPos, 5));
	    BTANetwork.sendToAll(new SubmarinePartUpdatePacket(p_114485_, seat4Pos, 4));
	    BTANetwork.sendToAll(new SubmarinePartUpdatePacket(p_114485_, seat3Pos, 3));
	    BTANetwork.sendToAll(new SubmarinePartUpdatePacket(p_114485_, seat2Pos, 2));
	    BTANetwork.sendToAll(new SubmarinePartUpdatePacket(p_114485_, seat1Pos, 1));
	    BTANetwork.sendToAll(new SubmarinePartUpdatePacket(p_114485_, controllerPos, 0));

		if(p_114485_.getControllingPlayer() != null)
		{
	        float strength = 0.5F + Mth.clamp(((float) Math.cos((p_114485_.glowingTicks + p_114487_) * 0.1F)) - 0.5F, -0.5F, 0.5F);

	        strength += Mth.lerp(p_114487_, p_114485_.brightnessOld, p_114485_.brightness) * Mth.PI;
	        strength = Mth.clamp(strength, 0.1F, 1);
	        
			VertexConsumer eyeConsumer = p_114489_.getBuffer(RenderType.eyes(LAYER_TEXTURE));
			this.model.setupAnim(p_114485_, 0, 0, 0, f2 + 180, f6);
			this.model.renderToBuffer(p_114488_, eyeConsumer, p_114490_, OverlayTexture.NO_OVERLAY, strength, strength, strength, 1.0F);
		}
		p_114488_.popPose();
	}

	@Override
	public ResourceLocation getTextureLocation(EntitySubmarine p_114482_) 
	{
		return TEXTURE;
	}
}
