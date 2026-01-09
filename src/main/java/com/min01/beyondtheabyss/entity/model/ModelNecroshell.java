package com.min01.beyondtheabyss.entity.model;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.entity.animation.NecroshellAnimation;
import com.min01.beyondtheabyss.entity.deepabyss.EntityNecroshell;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

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

public class ModelNecroshell extends HierarchicalModel<EntityNecroshell>
{
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(BeyondtheAbyss.MODID, "necroshell"), "main");
	private final ModelPart root;
	private final ModelPart necroshell;
	private final ModelPart Body;
	private final ModelPart BlasterSkull;
	private final ModelPart SlasherSkull;

	public ModelNecroshell(ModelPart root) 
	{
		this.root = root.getChild("root");
		this.necroshell = this.root.getChild("necroshell");
		this.Body = this.necroshell.getChild("Body");
		this.BlasterSkull = this.Body.getChild("BlasterSkull");
		this.SlasherSkull = this.Body.getChild("SlasherSkull");
	}

	public static LayerDefinition createBodyLayer() 
	{
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition necroshell = root.addOrReplaceChild("necroshell", CubeListBuilder.create(), PartPose.offset(0.0F, 2.0F, 0.0F));

		PartDefinition Body = necroshell.addOrReplaceChild("Body", CubeListBuilder.create(), PartPose.offset(0.0F, -11.75F, 0.0F));

		Body.addOrReplaceChild("Body2", CubeListBuilder.create().texOffs(91, 29).addBox(-5.5F, -4.5F, -5.0F, 11.0F, 9.0F, 10.0F, new CubeDeformation(0.0F))
		.texOffs(0, 118).addBox(-4.5F, -4.5F, 5.0F, 9.0F, 8.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.25F, -5.0F));

		Body.addOrReplaceChild("Mandibleright", CubeListBuilder.create().texOffs(91, 49).addBox(2.25F, 1.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(61, 87).addBox(-0.75F, -1.5F, -0.5F, 4.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-3.75F, -0.75F, -10.5F));

		Body.addOrReplaceChild("Mandibleleft", CubeListBuilder.create().texOffs(96, 49).addBox(-3.25F, 1.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(61, 92).addBox(-3.25F, -1.5F, -0.5F, 4.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(3.75F, -0.75F, -10.5F));

		Body.addOrReplaceChild("Lefteye", CubeListBuilder.create().texOffs(61, 97).addBox(-2.0F, -4.0F, 0.0F, 4.0F, 4.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.5F, -4.25F, -10.0F, 0.3491F, 0.0F, 0.0F));

		Body.addOrReplaceChild("Righteye", CubeListBuilder.create().texOffs(61, 102).addBox(-2.0F, -4.0F, 0.0F, 4.0F, 4.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.5F, -4.25F, -10.0F, 0.3491F, 0.0F, 0.0F));

		PartDefinition BlasterSkull = Body.addOrReplaceChild("BlasterSkull", CubeListBuilder.create().texOffs(101, 0).addBox(-6.0F, -11.0F, -5.5F, 12.0F, 9.0F, 17.0F, new CubeDeformation(0.0F))
		.texOffs(75, 53).addBox(-6.0F, -2.0F, -13.5F, 12.0F, 2.0F, 25.0F, new CubeDeformation(0.0F))
		.texOffs(98, 108).addBox(-6.0F, -13.0F, -13.5F, 12.0F, 11.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.75F, 1.5F, -0.5236F, 0.0F, 0.0F));

		BlasterSkull.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(61, 108).mirror().addBox(0.0F, -7.0F, -7.5F, 0.0F, 7.0F, 18.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(6.0F, -11.0F, 4.0F, 0.0F, 0.0F, 0.6109F));

		BlasterSkull.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(61, 108).addBox(0.0F, -7.0F, -7.5F, 0.0F, 7.0F, 18.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-6.0F, -11.0F, 4.0F, 0.0F, 0.0F, -0.6109F));

		Body.addOrReplaceChild("SlasherSkull", CubeListBuilder.create().texOffs(0, 53).addBox(-7.0F, -4.3333F, -3.9167F, 14.0F, 10.0F, 23.0F, new CubeDeformation(0.0F))
		.texOffs(0, 29).addBox(-11.0F, 2.6667F, -3.9167F, 22.0F, 0.0F, 23.0F, new CubeDeformation(0.0F))
		.texOffs(75, 81).addBox(-7.0F, 5.6667F, -3.9167F, 14.0F, 3.0F, 23.0F, new CubeDeformation(0.0F))
		.texOffs(98, 128).addBox(0.0F, -7.3333F, 5.0833F, 0.0F, 3.0F, 14.0F, new CubeDeformation(0.0F))
		.texOffs(0, 87).addBox(-2.0F, -4.3333F, -29.9167F, 4.0F, 4.0F, 26.0F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(-11.0F, -2.3333F, -31.9167F, 22.0F, 0.0F, 28.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -9.9167F, -3.0833F, -0.4363F, 0.0F, 0.0F));

		PartDefinition Legs = necroshell.addOrReplaceChild("Legs", CubeListBuilder.create(), PartPose.offset(0.0F, 1.0F, 1.0F));

		PartDefinition Midleg = Legs.addOrReplaceChild("Midleg", CubeListBuilder.create().texOffs(39, 118).addBox(-2.0F, -1.5F, -2.0F, 4.0F, 8.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(5.5F, -9.5F, -5.0F, 0.0F, 0.0F, -1.309F));

		Midleg.addOrReplaceChild("Claw", CubeListBuilder.create().texOffs(134, 42).addBox(-4.0F, -1.5F, -1.5F, 5.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.0F, 5.0F, 0.0F));

		PartDefinition Midleg2 = Legs.addOrReplaceChild("Midleg2", CubeListBuilder.create().texOffs(25, 148).addBox(-2.0F, -1.5F, -2.0F, 4.0F, 8.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.5F, -9.5F, -5.0F, 0.0F, 0.0F, 1.309F));

		Midleg2.addOrReplaceChild("Claw2", CubeListBuilder.create().texOffs(139, 120).addBox(-1.0F, -1.5F, -1.5F, 5.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(1.0F, 5.0F, 0.0F));

		PartDefinition Backleg = Legs.addOrReplaceChild("Backleg", CubeListBuilder.create().texOffs(39, 118).addBox(-2.0F, -1.5F, -2.0F, 4.0F, 8.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(5.5F, -9.5F, 1.0F, 0.4363F, 0.0F, -1.2217F));

		Backleg.addOrReplaceChild("Claw3", CubeListBuilder.create().texOffs(134, 42).addBox(-3.0F, -1.5F, -1.5F, 5.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.0F, 5.0F, 0.0F));

		PartDefinition Backleg2 = Legs.addOrReplaceChild("Backleg2", CubeListBuilder.create().texOffs(39, 118).mirror().addBox(-2.0F, -1.5F, -2.0F, 4.0F, 8.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-5.5F, -9.5F, 1.0F, 0.4363F, 0.0F, 1.2217F));

		Backleg2.addOrReplaceChild("Claw4", CubeListBuilder.create().texOffs(134, 42).mirror().addBox(-2.0F, -1.5F, -1.5F, 5.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(2.0F, 5.0F, 0.0F));

		PartDefinition Arms = necroshell.addOrReplaceChild("Arms", CubeListBuilder.create(), PartPose.offset(0.0F, -10.0F, -7.0F));

		PartDefinition Left = Arms.addOrReplaceChild("Left", CubeListBuilder.create(), PartPose.offsetAndRotation(8.0F, 1.25F, -1.0F, 0.0F, 0.3054F, 0.0F));

		Left.addOrReplaceChild("Up", CubeListBuilder.create().texOffs(39, 134).addBox(-2.0F, 0.0F, -9.0F, 4.0F, 3.0F, 10.0F, new CubeDeformation(0.0F))
		.texOffs(127, 128).addBox(-2.0F, -4.0F, -9.0F, 4.0F, 4.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -0.25F, 0.0F));

		Left.addOrReplaceChild("Down", CubeListBuilder.create().texOffs(0, 137).addBox(-1.5F, -0.15F, -9.0F, 3.0F, 2.0F, 9.0F, new CubeDeformation(0.0F))
		.texOffs(139, 108).addBox(-1.5F, -2.15F, -9.0F, 3.0F, 2.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.1F, 1.0F, 0.6981F, 0.0F, 0.0F));

		PartDefinition Right = Arms.addOrReplaceChild("Right", CubeListBuilder.create(), PartPose.offsetAndRotation(-8.0F, 1.25F, -1.0F, 0.0F, -0.3054F, 0.0F));

		Right.addOrReplaceChild("Up2", CubeListBuilder.create().texOffs(68, 134).addBox(-2.0F, 0.0F, -9.0F, 4.0F, 3.0F, 10.0F, new CubeDeformation(0.0F))
		.texOffs(134, 27).addBox(-2.0F, -4.0F, -9.0F, 4.0F, 4.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -0.25F, 0.0F));

		Right.addOrReplaceChild("Down2", CubeListBuilder.create().texOffs(127, 143).addBox(-1.5F, -0.15F, -9.0F, 3.0F, 2.0F, 9.0F, new CubeDeformation(0.0F))
		.texOffs(97, 146).addBox(-1.5F, -2.15F, -9.0F, 3.0F, 2.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.1F, 1.0F, 0.6981F, 0.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 256, 256);
	}

	@Override
	public void setupAnim(EntityNecroshell entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) 
	{
		this.root().getAllParts().forEach(ModelPart::resetPose);
		this.BlasterSkull.visible = entity.isBlasterShell();
		this.SlasherSkull.visible = entity.isSlasherShell();
		entity.idleAnimationState.animate(this, NecroshellAnimation.NECROSHELL_IDLE, ageInTicks, limbSwingAmount, 2.5F);
		entity.attackAnimationState.animate(this, NecroshellAnimation.NECROSHELL_ATTACK, ageInTicks);
		entity.intimidateAnimationState.animate(this, NecroshellAnimation.NECROSHELL_INTIMIDATE, ageInTicks);
		entity.hideAnimationState.animate(this, NecroshellAnimation.NECROSHELL_HIDE, ageInTicks);
		entity.unhideAnimationState.animate(this, NecroshellAnimation.NECROSHELL_UNHIDE, ageInTicks);
		
		this.animateWalk(NecroshellAnimation.NECROSHELL_WALK, limbSwing, limbSwingAmount, 2.5F, 2.5F);
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