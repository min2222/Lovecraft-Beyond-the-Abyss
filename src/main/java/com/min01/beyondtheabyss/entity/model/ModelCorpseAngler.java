package com.min01.beyondtheabyss.entity.model;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.entity.animation.CorpseAnglerAnimation;
import com.min01.beyondtheabyss.entity.deepabyss.EntityCorpseAngler;
import com.min01.beyondtheabyss.util.BTAClientUtil;
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
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec2;

public class ModelCorpseAngler extends HierarchicalModel<EntityCorpseAngler>
{
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(BeyondtheAbyss.MODID, "corpse_angler"), "main");
	private final ModelPart root;

	public ModelCorpseAngler(ModelPart root)
	{
		this.root = root.getChild("root");
	}

	public static LayerDefinition createBodyLayer() 
	{
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition corpse_angler = root.addOrReplaceChild("corpse_angler", CubeListBuilder.create().texOffs(0, 131).addBox(-15.25F, 4.9055F, -38.878F, 31.0F, 30.0F, 78.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -35.0F, 0.0F));

		corpse_angler.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(90, 239).addBox(-31.5F, -35.2122F, -44.7094F, 34.0F, 45.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(14.5F, 1.0F, 0.0F, 0.4363F, 0.0F, 0.0F));

		corpse_angler.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(0, 0).addBox(-31.0F, -30.0F, -40.0F, 33.0F, 46.0F, 85.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(14.5F, 1.0F, 0.0F, 0.3054F, 0.0F, 0.0F));

		PartDefinition angler = corpse_angler.addOrReplaceChild("angler", CubeListBuilder.create().texOffs(435, 59).addBox(-4.5F, -6.5F, 0.0F, 9.0F, 13.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -23.5F, -43.5F));

		PartDefinition part1 = angler.addOrReplaceChild("1", CubeListBuilder.create().texOffs(406, 73).addBox(-1.5F, -16.0F, 0.0F, 3.0F, 16.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -6.5F, 0.0F));

		PartDefinition part2 = part1.addOrReplaceChild("2", CubeListBuilder.create().texOffs(413, 73).addBox(-1.5F, -15.0F, 0.0F, 3.0F, 15.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -16.0F, 0.0F));

		PartDefinition part3 = part2.addOrReplaceChild("3", CubeListBuilder.create().texOffs(420, 73).addBox(-1.5F, -15.0F, 0.0F, 3.0F, 15.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -15.0F, 0.0F));

		PartDefinition part4 = part3.addOrReplaceChild("4", CubeListBuilder.create().texOffs(427, 73).addBox(-1.5F, -15.0F, 0.0F, 3.0F, 15.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -15.0F, 0.0F));

		PartDefinition part5 = part4.addOrReplaceChild("5", CubeListBuilder.create().texOffs(434, 73).addBox(-1.5F, -15.0F, 0.0F, 3.0F, 15.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -15.0F, 0.0F));

		PartDefinition part6 = part5.addOrReplaceChild("6", CubeListBuilder.create().texOffs(441, 73).addBox(-1.5F, -15.0F, 0.0F, 3.0F, 15.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -15.0F, 0.0F));

		PartDefinition Bait = part6.addOrReplaceChild("Bait", CubeListBuilder.create().texOffs(454, 59).addBox(-2.5F, -20.125F, -2.4813F, 5.0F, 5.0F, 5.0F, new CubeDeformation(0.0F))
		.texOffs(457, 0).addBox(-2.5F, -15.125F, 0.0187F, 5.0F, 15.0F, 0.0F, new CubeDeformation(0.0F))
		.texOffs(434, 18).addBox(-2.5F, -22.125F, -2.4813F, 0.0F, 2.0F, 5.0F, new CubeDeformation(0.0F))
		.texOffs(456, 23).addBox(-2.5F, -22.125F, 2.5187F, 5.0F, 2.0F, 0.0F, new CubeDeformation(0.0F))
		.texOffs(445, 23).addBox(-2.5F, -22.125F, -2.4813F, 5.0F, 2.0F, 0.0F, new CubeDeformation(0.0F))
		.texOffs(408, 46).addBox(2.5F, -22.125F, -2.4813F, 0.0F, 2.0F, 5.0F, new CubeDeformation(0.0F))
		.texOffs(408, 54).addBox(-1.5F, -21.125F, -1.4813F, 3.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(445, 18).addBox(-1.5F, -22.125F, -1.4813F, 3.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -14.875F, -0.0187F));

		PartDefinition Gnasher = Bait.addOrReplaceChild("Gnasher", CubeListBuilder.create(), PartPose.offset(0.0F, -16.375F, -0.0063F));

		PartDefinition Body2 = Gnasher.addOrReplaceChild("Body2", CubeListBuilder.create().texOffs(385, 0).addBox(-5.0F, -6.3333F, -6.8333F, 10.0F, 12.0F, 14.0F, new CubeDeformation(0.0F))
		.texOffs(434, 0).addBox(0.0F, -12.3333F, -1.8333F, 0.0F, 6.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -5.4167F, 1.8333F));

		PartDefinition Fins = Body2.addOrReplaceChild("Fins", CubeListBuilder.create(), PartPose.offset(0.0F, 1.6667F, -3.8333F));

		Fins.addOrReplaceChild("Left", CubeListBuilder.create().texOffs(454, 70).addBox(0.0F, -6.5F, 0.0F, 10.0F, 10.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(5.0F, 2.5F, 0.0F, 0.0F, -1.2217F, 0.0F));

		Fins.addOrReplaceChild("Right", CubeListBuilder.create().texOffs(385, 71).addBox(-10.0F, -6.5F, 0.0F, 10.0F, 10.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.0F, 2.5F, 0.0F, 0.0F, 1.2217F, 0.0F));

		PartDefinition Head = Gnasher.addOrReplaceChild("Head", CubeListBuilder.create(), PartPose.offset(0.0F, -3.75F, -5.0F));

		Head.addOrReplaceChild("Up", CubeListBuilder.create().texOffs(385, 27).addBox(-4.0F, -6.0F, -11.0F, 8.0F, 6.0F, 12.0F, new CubeDeformation(0.001F))
		.texOffs(426, 43).addBox(-3.5F, -5.85F, -10.95F, 7.0F, 4.0F, 11.0F, new CubeDeformation(0.001F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		Head.addOrReplaceChild("Jaw2", CubeListBuilder.create().texOffs(426, 27).addBox(-4.0F, -0.5F, -11.0F, 8.0F, 3.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.5F, 0.0F));

		PartDefinition Tails = Gnasher.addOrReplaceChild("Tails", CubeListBuilder.create().texOffs(408, 59).addBox(-2.5F, -2.5F, -1.0F, 5.0F, 5.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -7.25F, 9.0F));

		Tails.addOrReplaceChild("TailEdge", CubeListBuilder.create().texOffs(385, 46).addBox(0.0F, -6.5F, -1.0F, 0.0F, 13.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 7.0F));

		PartDefinition jaw = corpse_angler.addOrReplaceChild("jaw", CubeListBuilder.create(), PartPose.offset(0.0F, 30.0F, -39.3F));

		jaw.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(270, 279).addBox(-18.8798F, -28.3432F, -48.3486F, 0.0F, 42.0F, 18.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(14.5F, -29.0F, 39.3F, 0.3155F, 0.2494F, 0.0804F));

		jaw.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(270, 279).addBox(-8.6491F, -28.3432F, -40.9723F, 0.0F, 42.0F, 18.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(14.5F, -29.0F, 39.3F, 0.3155F, -0.2494F, -0.0804F));

		jaw.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(0, 239).addBox(-30.75F, -28.3432F, -53.8147F, 33.0F, 51.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(14.5F, -29.0F, 39.3F, 0.3054F, 0.0F, 0.0F));

		corpse_angler.addOrReplaceChild("leftfin", CubeListBuilder.create().texOffs(236, 84).addBox(-6.25F, -0.0945F, -6.878F, 21.0F, 0.0F, 26.0F, new CubeDeformation(0.0F)), PartPose.offset(15.0F, 23.0F, -7.0F));

		corpse_angler.addOrReplaceChild("rightfin", CubeListBuilder.create().texOffs(176, 279).addBox(-13.25F, -0.0945F, -6.878F, 21.0F, 0.0F, 26.0F, new CubeDeformation(0.0F)), PartPose.offset(-15.0F, 23.0F, -7.0F));

		corpse_angler.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(218, 131).addBox(-0.5F, -33.0F, -23.0F, 0.0F, 68.0F, 80.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -8.0F, 30.0F));

		return LayerDefinition.create(meshdefinition, 512, 512);
	}

	@Override
	public void setupAnim(EntityCorpseAngler entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch)
	{
		this.root().getAllParts().forEach(ModelPart::resetPose);
		float partialTicks = ageInTicks - entity.tickCount;
        float yBodyRot = Mth.rotLerp(partialTicks, entity.yBodyRotO, entity.yBodyRot);
		boolean isBurrow = entity.getAnimationState() == 3 && entity.getAnimationTick() <= 0;
		int tick = 40 - entity.getAnimationTick();
		float yRot = entity.getAnimationState() != 0 ? Mth.lerp(tick / 40.0F, 0.0F, 42.5F) : 0.0F;
        ModelPart root = this.root.getChild("corpse_angler");
		ModelPart angler = root.getChild("angler");
        ModelPart part1 = angler.getChild("1");
        ModelPart part2 = part1.getChild("2");
        ModelPart part3 = part2.getChild("3");
        ModelPart part4 = part3.getChild("4");
        ModelPart part5 = part4.getChild("5");
        ModelPart part6 = part5.getChild("6");
        ModelPart bait = part6.getChild("Bait").getChild("Gnasher");
		Vec2 rot = entity.worm.getRot(partialTicks);
		Vec2 rot1 = entity.worm1.getRot(partialTicks);
		Vec2 rot2 = entity.worm2.getRot(partialTicks);
		Vec2 rot3 = entity.worm3.getRot(partialTicks);
		Vec2 rot4 = entity.worm4.getRot(partialTicks);
		Vec2 rot5 = entity.worm5.getRot(partialTicks);
		Vec2 rot6 = entity.worm6.getRot(partialTicks);
		BTAClientUtil.animateHead(root, netHeadYaw, headPitch);
		BTAClientUtil.animateHead(angler, rot.y - netHeadYaw - yBodyRot, rot.x - headPitch + yRot);
		BTAClientUtil.animateHead(part1, rot1.y - netHeadYaw - yBodyRot, rot1.x - headPitch);
		BTAClientUtil.animateHead(part2, rot2.y - netHeadYaw - yBodyRot, rot2.x - headPitch);
		BTAClientUtil.animateHead(part3, rot3.y - netHeadYaw - yBodyRot, rot3.x - headPitch);
		BTAClientUtil.animateHead(part4, rot4.y - netHeadYaw - yBodyRot, rot4.x - headPitch);
		BTAClientUtil.animateHead(part5, rot5.y - netHeadYaw - yBodyRot, rot5.x - headPitch);
		BTAClientUtil.animateHead(part6, rot6.y - netHeadYaw - yBodyRot, rot6.x - headPitch);
		bait.visible = isBurrow;
		
		this.animateWalk(CorpseAnglerAnimation.CORPSE_ANGLER_SWIM, limbSwing, limbSwingAmount, 2.5F, 1.5F);
		entity.idleAnimationState.animate(this, CorpseAnglerAnimation.CORPSE_ANGLER_IDLE, ageInTicks, limbSwingAmount, 1.5F);
		entity.openMouthAnimationState.animate(this, CorpseAnglerAnimation.CORPSE_ANGLER_OPEN_MOUTH, ageInTicks);
		entity.burrowAnimationState.animate(this, CorpseAnglerAnimation.CORPSE_ANGLER_BURROW, ageInTicks);
		entity.unburrowAnimationState.animate(this, CorpseAnglerAnimation.CORPSE_ANGLER_UNBURROW, ageInTicks);
		entity.ambushAnimationState.animate(this, CorpseAnglerAnimation.CORPSE_ANGLER_AMBUSH, ageInTicks);
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