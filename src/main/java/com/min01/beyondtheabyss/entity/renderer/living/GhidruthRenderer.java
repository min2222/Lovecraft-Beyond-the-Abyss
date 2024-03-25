package com.min01.beyondtheabyss.entity.renderer.living;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.entity.deepabyss.EntityGhidruth;
import com.min01.beyondtheabyss.entity.model.ModelGhidruth;
import com.min01.beyondtheabyss.entity.renderer.layer.GlowingLayer;
import com.min01.beyondtheabyss.network.BTANetwork;
import com.min01.beyondtheabyss.network.ModelPosSyncPacket;
import com.min01.beyondtheabyss.network.ModelPosSyncPacket.PosType;
import com.min01.beyondtheabyss.util.BTAClientUtil;
import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;

public class GhidruthRenderer extends MobRenderer<EntityGhidruth, ModelGhidruth>
{
	public static final ResourceLocation TEXTURE = new ResourceLocation(BeyondtheAbyss.MODID, "textures/entity/ghidruth.png");
	public static final ResourceLocation LAYER = new ResourceLocation(BeyondtheAbyss.MODID, "textures/entity/ghidruth_layer.png");
	public static final ResourceLocation DASH_LAYER = new ResourceLocation(BeyondtheAbyss.MODID, "textures/entity/ghidruth_dash_eye_layer.png");
	
	public GhidruthRenderer(Context p_174304_)
	{
		super(p_174304_, new ModelGhidruth(p_174304_.bakeLayer(ModelGhidruth.LAYER_LOCATION)), 0);
		this.addLayer(new GlowingLayer<>(this, LAYER));
		this.addLayer(new GlowingLayer<>(this, DASH_LAYER));
	}
	
	@Override
	public void render(EntityGhidruth p_115455_, float p_115456_, float p_115457_, PoseStack p_115458_, MultiBufferSource p_115459_, int p_115460_) 
	{
		super.render(p_115455_, p_115456_, p_115457_, p_115458_, p_115459_, p_115460_);
	    ModelPart tail = this.model.root().getChild("root2").getChild("Head").getChild("Body").getChild("RearBody").getChild("Tail").getChild("TailPos");
	    ModelPart body = this.model.root().getChild("root2").getChild("Head").getChild("Body").getChild("RearBody").getChild("BodyPos");
	    ModelPart head = this.model.root().getChild("root2").getChild("Head").getChild("HeadPos");
	    ModelPart root2 = this.model.root().getChild("root2");
	    Vec3 vec = new Vec3(root2.xRot, root2.yRot, root2.zRot);
	    Vec3 tailVec = vec.add(new Vec3(body.xRot, body.yRot, body.zRot));
	    Vec3 tailPos = BTAClientUtil.getWorldPosFromModel(p_115455_, tail, tailVec.reverse(), p_115455_.getRotation());
		Vec3 bodyPos = BTAClientUtil.getWorldPosFromModel(p_115455_, body, tailVec.reverse(), p_115455_.getRotation());
		Vec3 headPos = BTAClientUtil.getWorldPosFromModel(p_115455_, head, vec, p_115455_.getRotation());
		BTANetwork.CHANNEL.sendToServer(new ModelPosSyncPacket(p_115455_, (float)tailPos.x, (float)tailPos.y, (float)tailPos.z, PosType.TAIL));
		BTANetwork.CHANNEL.sendToServer(new ModelPosSyncPacket(p_115455_, (float)bodyPos.x, (float)bodyPos.y, (float)bodyPos.z, PosType.BODY));
		BTANetwork.CHANNEL.sendToServer(new ModelPosSyncPacket(p_115455_, (float)headPos.x, (float)headPos.y, (float)headPos.z, PosType.HEAD));
	}
	
	@Override
	protected void scale(EntityGhidruth p_115314_, PoseStack p_115315_, float p_115316_)
	{
		p_115315_.scale(1.5F, 1.5F, 1.5F);
		p_115315_.translate(0, -0.1F, 0);
	}

	@Override
	public ResourceLocation getTextureLocation(EntityGhidruth p_115812_)
	{
		return TEXTURE;
	}
}
