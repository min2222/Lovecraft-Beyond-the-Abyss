package com.min01.beyondtheabyss.entity.model;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.entity.deepabyss.EntityFallenDiver;
import com.min01.beyondtheabyss.util.BTAClientUtil;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.minecraft.client.model.AnimationUtils;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;

public class ModelFallenDiver extends HierarchicalModel<EntityFallenDiver>
{
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(BeyondtheAbyss.MODID, "fallen_diver"), "main");
	public float swimAmount;
	public final ModelPart root;
	public final ModelPart bone;
	public final ModelPart Body;
	public final ModelPart RightArm;
	public final ModelPart LeftArm;
	public final ModelPart Head;
	public final ModelPart LeftLeg;
	public final ModelPart RightLeg;
	
	public ModelFallenDiver(ModelPart root) 
	{
		this.root = root.getChild("root");
		this.bone = this.root.getChild("bone");
		this.Body = this.bone.getChild("Body");
		this.RightArm = this.Body.getChild("RightArm");
		this.LeftArm = this.Body.getChild("LeftArm");
		this.Head = this.Body.getChild("Head");
		this.LeftLeg = this.Body.getChild("LeftLeg");
		this.RightLeg = this.Body.getChild("RightLeg");
	}

	public static LayerDefinition createBodyLayer()
	{
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition bone = root.addOrReplaceChild("bone", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition Body = bone.addOrReplaceChild("Body", CubeListBuilder.create().texOffs(16, 16).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -24.0F, 0.0F));

		Body.addOrReplaceChild("RightArm", CubeListBuilder.create().texOffs(40, 16).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(87, 17).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.3F)), PartPose.offset(-5.0F, 2.0F, 0.0F));

		Body.addOrReplaceChild("LeftArm", CubeListBuilder.create().texOffs(40, 16).mirror().addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(87, 17).mirror().addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.3F)).mirror(false), PartPose.offset(5.0F, 2.0F, 0.0F));

		PartDefinition Head = Body.addOrReplaceChild("Head", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F))
		.texOffs(90, 39).addBox(0.0F, -13.0F, -4.0F, 0.0F, 8.0F, 10.0F, new CubeDeformation(0.0F))
		.texOffs(42, 43).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.4F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		Head.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(63, 7).mirror().addBox(0.0F, -2.0F, 0.0F, 0.0F, 5.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-4.0F, -5.0F, -1.0F, 0.0873F, -0.2618F, -0.0524F));

		Head.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(63, 7).addBox(0.0F, -2.0F, 0.0F, 0.0F, 5.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.0F, -5.0F, -1.0F, 0.0873F, 0.2618F, 0.0524F));

		Head.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(1, 1).addBox(0.0F, -1.0F, -1.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.0F, -4.5F, -4.0F, 0.0F, -0.4363F, 0.0F));

		Head.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(4, 69).addBox(-2.0F, -1.5F, -1.0F, 2.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.0F, -4.5F, -4.0F, 0.0F, 0.3054F, 0.0F));

		Body.addOrReplaceChild("LeftLeg", CubeListBuilder.create().texOffs(26, 77).mirror().addBox(-2.0F, 12.0F, -4.0F, 4.0F, 0.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(0, 16).mirror().addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(1.9F, 12.0F, 0.0F));

		Body.addOrReplaceChild("RightLeg", CubeListBuilder.create().texOffs(0, 16).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(26, 77).addBox(-2.0F, 12.0F, -4.0F, 4.0F, 0.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.9F, 12.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 120, 120);
	}
	
	@Override
	public void prepareMobModel(EntityFallenDiver p_102614_, float p_102615_, float p_102616_, float p_102617_) 
	{	
		this.swimAmount = p_102614_.getSwimAmount(p_102617_);
		super.prepareMobModel(p_102614_, p_102615_, p_102616_, p_102617_);
	}

	@Override
	public void setupAnim(EntityFallenDiver entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) 
	{
		this.root().getAllParts().forEach(ModelPart::resetPose);
		BTAClientUtil.animateHead(this.Head, netHeadYaw, headPitch);
		AnimationUtils.animateZombieArms(this.LeftArm, this.RightArm, entity.isAggressive(), this.attackTime, ageInTicks);
		
		this.RightArm.xRot += Mth.cos(limbSwing * 0.6662F + (float)Math.PI) * 2.0F * limbSwingAmount * 0.5F;
		this.LeftArm.xRot += Mth.cos(limbSwing * 0.6662F) * 2.0F * limbSwingAmount * 0.5F;
		this.RightLeg.xRot += Mth.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount * 0.5F;
		this.LeftLeg.xRot += Mth.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.4F * limbSwingAmount * 0.5F;
		
		if(this.swimAmount > 0.0F) 
		{
			float f5 = limbSwing % 26.0F;
			HumanoidArm humanoidarm = this.getAttackArm(entity);
			float f1 = humanoidarm == HumanoidArm.RIGHT && this.attackTime > 0.0F ? 0.0F : this.swimAmount;
			float f2 = humanoidarm == HumanoidArm.LEFT && this.attackTime > 0.0F ? 0.0F : this.swimAmount;
			if(f5 < 14.0F)
			{
				this.LeftArm.xRot = this.rotlerpRad(f2, this.LeftArm.xRot, 0.0F);
				this.RightArm.xRot = Mth.lerp(f1, this.RightArm.xRot, 0.0F);
				this.LeftArm.yRot = this.rotlerpRad(f2, this.LeftArm.yRot, (float)Math.PI);
                this.RightArm.yRot = Mth.lerp(f1, this.RightArm.yRot, (float)Math.PI);
                this.LeftArm.zRot = this.rotlerpRad(f2, this.LeftArm.zRot, (float)Math.PI + 1.8707964F * this.quadraticArmUpdate(f5) / this.quadraticArmUpdate(14.0F));
                this.RightArm.zRot = Mth.lerp(f1, this.RightArm.zRot, (float)Math.PI - 1.8707964F * this.quadraticArmUpdate(f5) / this.quadraticArmUpdate(14.0F));
			} 
			else if(f5 >= 14.0F && f5 < 22.0F) 
			{
				float f6 = (f5 - 14.0F) / 8.0F;
                this.LeftArm.xRot = this.rotlerpRad(f2, this.LeftArm.xRot, ((float)Math.PI / 2.0F) * f6);
                this.RightArm.xRot = Mth.lerp(f1, this.RightArm.xRot, ((float)Math.PI / 2.0F) * f6);
                this.LeftArm.yRot = this.rotlerpRad(f2, this.LeftArm.yRot, (float)Math.PI);
                this.RightArm.yRot = Mth.lerp(f1, this.RightArm.yRot, (float)Math.PI);
                this.LeftArm.zRot = this.rotlerpRad(f2, this.LeftArm.zRot, 5.012389F - 1.8707964F * f6);
                this.RightArm.zRot = Mth.lerp(f1, this.RightArm.zRot, 1.2707963F + 1.8707964F * f6);
			}
			else if(f5 >= 22.0F && f5 < 26.0F)
			{
				float f3 = (f5 - 22.0F) / 4.0F;
                this.LeftArm.xRot = this.rotlerpRad(f2, this.LeftArm.xRot, ((float)Math.PI / 2.0F) - ((float)Math.PI / 2.0F) * f3);
                this.RightArm.xRot = Mth.lerp(f1, this.RightArm.xRot, ((float)Math.PI / 2.0F) - ((float)Math.PI / 2.0F) * f3);
                this.LeftArm.yRot = this.rotlerpRad(f2, this.LeftArm.yRot, (float)Math.PI);
                this.RightArm.yRot = Mth.lerp(f1, this.RightArm.yRot, (float)Math.PI);
                this.LeftArm.zRot = this.rotlerpRad(f2, this.LeftArm.zRot, (float)Math.PI);
                this.RightArm.zRot = Mth.lerp(f1, this.RightArm.zRot, (float)Math.PI);
			}
			this.LeftLeg.xRot = Mth.lerp(this.swimAmount, this.LeftLeg.xRot, 0.3F * Mth.cos(limbSwing * 0.33333334F + (float)Math.PI));
			this.RightLeg.xRot = Mth.lerp(this.swimAmount, this.RightLeg.xRot, 0.3F * Mth.cos(limbSwing * 0.33333334F));
		}
	}
	
	protected float rotlerpRad(float p_102836_, float p_102837_, float p_102838_) 
	{
		float f = (p_102838_ - p_102837_) % ((float)Math.PI * 2.0F);
		if(f < -(float)Math.PI) 
		{
			f += ((float)Math.PI * 2.0F);
		}

		if(f >= (float)Math.PI)
		{
			f -= ((float)Math.PI * 2.0F);
		}

		return p_102837_ + p_102836_ * f;
	}

	private float quadraticArmUpdate(float p_102834_)
	{
		return -65.0F * p_102834_ + p_102834_ * p_102834_;
	}
	
	private HumanoidArm getAttackArm(LivingEntity p_102857_) 
	{
		HumanoidArm humanoidarm = p_102857_.getMainArm();
		return p_102857_.swingingArm == InteractionHand.MAIN_HAND ? humanoidarm : humanoidarm.getOpposite();
	}
	
	@Override
	public ModelPart root()
	{
		return this.root;
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) 
	{
		root.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}
}
