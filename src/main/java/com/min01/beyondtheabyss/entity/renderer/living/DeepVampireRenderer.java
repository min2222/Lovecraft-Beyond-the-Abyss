package com.min01.beyondtheabyss.entity.renderer.living;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.entity.deepabyss.EntityDeepVampire;
import com.min01.beyondtheabyss.entity.model.ModelDeepVampire;
import com.min01.beyondtheabyss.entity.renderer.layer.GlowingLayer;
import com.min01.beyondtheabyss.network.BTANetwork;
import com.min01.beyondtheabyss.network.PartPositionUpdatePacket;
import com.min01.beyondtheabyss.util.BTAClientUtil;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;

public class DeepVampireRenderer extends MobRenderer<EntityDeepVampire, ModelDeepVampire>
{
	public static final String[] BODY = new String[] {"DeepVamp", "Body", "BodyPos"};
	public static final String[] BODY2 = new String[] {"DeepVamp", "Body", "Body2"};
	public static final String[] TAIL = new String[] {"DeepVamp", "Body", "Body2", "TailPos"};
	public static final String[] TAIL2 = new String[] {"DeepVamp", "Body", "Body2", "Tails2"};
	public static final String[] TAIL_EDGE = new String[] {"DeepVamp", "Body", "Body2", "Tails2", "TailEdgePos"};
	public static final String[] TAIL_EDGE2 = new String[] {"DeepVamp", "Body", "Body2", "Tails2", "TailEdge"};
	public DeepVampireRenderer(Context p_174304_)
	{
		super(p_174304_, new ModelDeepVampire(p_174304_.bakeLayer(ModelDeepVampire.LAYER_LOCATION)), 0.5F);
		this.addLayer(new GlowingLayer<>(this, this.model, new ResourceLocation(BeyondtheAbyss.MODID, "textures/entity/deep_vampire_layer.png")));
	}
	
	@Override
	protected void setupRotations(EntityDeepVampire p_116226_, PoseStack p_116227_, float p_116228_, float p_116229_, float p_116230_) 
	{
		super.setupRotations(p_116226_, p_116227_, p_116228_, p_116229_, p_116230_);
		if(!p_116226_.isInWater()) 
		{
			p_116227_.translate(0.5F, 0, 0);
			p_116227_.mulPose(Vector3f.ZP.rotationDegrees(90.0F));
		}
	}
	
	@Override
	public void render(EntityDeepVampire p_115455_, float p_115456_, float p_115457_, PoseStack p_115458_, MultiBufferSource p_115459_, int p_115460_) 
	{
		super.render(p_115455_, p_115456_, p_115457_, p_115458_, p_115459_, p_115460_);
		//FIXME
		Vec3 rotation = new Vec3(0.0F, p_115455_.yHeadRot, p_115455_.isInWater() ? 0.0F : 90.0F);
		Vec3 tailEdge2Pos = BTAClientUtil.getWorldPositionOfMultiPart(p_115455_, this.model.root(), rotation, TAIL_EDGE2);
		Vec3 tailEdgePos = BTAClientUtil.getWorldPositionOfMultiPart(p_115455_, this.model.root(), rotation, TAIL_EDGE);
		Vec3 tail2Pos = BTAClientUtil.getWorldPositionOfMultiPart(p_115455_, this.model.root(), rotation, TAIL2);
		Vec3 tailPos = BTAClientUtil.getWorldPositionOfMultiPart(p_115455_, this.model.root(), rotation, TAIL);
		Vec3 body2Pos = BTAClientUtil.getWorldPositionOfMultiPart(p_115455_, this.model.root(), rotation, BODY2);
		Vec3 bodyPos = BTAClientUtil.getWorldPositionOfMultiPart(p_115455_, this.model.root(), rotation, BODY);
		p_115455_.posArray[5] = tailEdge2Pos;
		p_115455_.posArray[4] = tailEdgePos;
		p_115455_.posArray[3] = tail2Pos;
		p_115455_.posArray[2] = tailPos;
		p_115455_.posArray[1] = body2Pos;
		p_115455_.posArray[0] = bodyPos;
	    BTANetwork.sendToServer(new PartPositionUpdatePacket(p_115455_, tailEdge2Pos, 5));
	    BTANetwork.sendToServer(new PartPositionUpdatePacket(p_115455_, tailEdgePos, 4));
	    BTANetwork.sendToServer(new PartPositionUpdatePacket(p_115455_, tail2Pos, 3));
	    BTANetwork.sendToServer(new PartPositionUpdatePacket(p_115455_, tailPos, 2));
	    BTANetwork.sendToServer(new PartPositionUpdatePacket(p_115455_, body2Pos, 1));
	    BTANetwork.sendToServer(new PartPositionUpdatePacket(p_115455_, bodyPos, 0));
	}

	@Override
	public ResourceLocation getTextureLocation(EntityDeepVampire p_115812_) 
	{
		return new ResourceLocation(BeyondtheAbyss.MODID, "textures/entity/deep_vampire.png");
	}
}
