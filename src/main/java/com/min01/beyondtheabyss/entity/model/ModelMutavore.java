package com.min01.beyondtheabyss.entity.model;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.entity.deepabyss.EntityMutavore;
import com.min01.beyondtheabyss.network.BTANetwork;
import com.min01.beyondtheabyss.network.UpdatePosArrayPacket;
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
import net.minecraft.world.phys.Vec3;

public class ModelMutavore extends HierarchicalModel<EntityMutavore>
{
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(BeyondtheAbyss.MODID, "mutavore"), "main");
	private final ModelPart root;
	private final ModelPart bone4;
	private final ModelPart bone8;
	private final ModelPart bone9;
	private final ModelPart bone10;
	private final ModelPart bone11;
	private final ModelPart bone12;
	private final ModelPart bone13;
	private final ModelPart bone17;
	private final ModelPart bone18;
	private final ModelPart bone19;
	private final ModelPart bone14;
	private final ModelPart bone15;
	private final ModelPart bone16;
	private final ModelPart bone20;
	private final ModelPart bone21;
	private final ModelPart bone22;

	public ModelMutavore(ModelPart root) 
	{
		this.root = root.getChild("root");
		this.bone4 = this.root.getChild("bone4");
		this.bone8 = this.bone4.getChild("bone8");
		this.bone9 = this.bone8.getChild("bone9");
		this.bone10 = this.bone9.getChild("bone10");
		this.bone11 = this.bone4.getChild("bone11");
		this.bone12 = this.bone11.getChild("bone12");
		this.bone13 = this.bone12.getChild("bone13");
		this.bone17 = this.bone4.getChild("bone17");
		this.bone18 = this.bone17.getChild("bone18");
		this.bone19 = this.bone18.getChild("bone19");
		this.bone14 = this.bone4.getChild("bone14");
		this.bone15 = this.bone14.getChild("bone15");
		this.bone16 = this.bone15.getChild("bone16");
		this.bone20 = this.bone4.getChild("bone20");
		this.bone21 = this.bone20.getChild("bone21");
		this.bone22 = this.bone21.getChild("bone22");
	}

	public static LayerDefinition createBodyLayer() 
	{
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();
		
		PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition bone4 = root.addOrReplaceChild("bone4", CubeListBuilder.create().texOffs(0, 0).addBox(-14.0F, -19.0F, -26.0F, 28.0F, 34.0F, 37.0F, new CubeDeformation(0.0F))
		.texOffs(13, 146).addBox(0.0F, -36.0F, -21.0F, 0.0F, 17.0F, 24.0F, new CubeDeformation(0.0F))
		.texOffs(0, 155).addBox(8.0F, -24.0F, -26.0F, 0.0F, 5.0F, 38.0F, new CubeDeformation(0.0F))
		.texOffs(0, 155).mirror().addBox(-8.0F, -24.0F, -26.0F, 0.0F, 5.0F, 38.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, -15.0F, 7.0F));

		bone4.addOrReplaceChild("bone3", CubeListBuilder.create().texOffs(137, 0).addBox(-9.0F, 3.0F, -34.0F, 18.0F, 6.0F, 34.0F, new CubeDeformation(0.0F))
		.texOffs(134, 103).addBox(0.0F, 9.0F, -18.0F, 0.0F, 15.0F, 18.0F, new CubeDeformation(0.0F))
		.texOffs(136, 56).addBox(-9.0F, -2.0F, -34.0F, 18.0F, 5.0F, 34.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 4.0F, -25.0F));

		bone4.addOrReplaceChild("bone2", CubeListBuilder.create().texOffs(0, 71).addBox(-10.0F, -15.0F, -35.0F, 20.0F, 11.0F, 35.0F, new CubeDeformation(0.0F))
		.texOffs(0, 120).addBox(-10.0F, -4.0F, -35.0F, 20.0F, 6.0F, 35.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 4.0F, -25.0F));

		bone4.addOrReplaceChild("bone", CubeListBuilder.create().texOffs(117, 154).addBox(0.0F, -1.0F, -5.0F, 4.0F, 2.0F, 14.0F, new CubeDeformation(0.0F))
		.texOffs(96, 180).addBox(1.0F, 0.0F, -6.0F, 33.0F, 0.0F, 15.0F, new CubeDeformation(0.0F)), PartPose.offset(14.0F, 13.0F, -11.0F));

		bone4.addOrReplaceChild("bone5", CubeListBuilder.create().texOffs(96, 180).mirror().addBox(-34.0F, 0.0F, -6.0F, 33.0F, 0.0F, 15.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(117, 154).mirror().addBox(-4.0F, -1.0F, -5.0F, 4.0F, 2.0F, 14.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-14.0F, 13.0F, -11.0F));

		bone4.addOrReplaceChild("bone6", CubeListBuilder.create().texOffs(184, 160).addBox(0.0F, 0.0F, -3.0F, 9.0F, 0.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(14.0F, 13.0F, 8.0F));

		bone4.addOrReplaceChild("bone7", CubeListBuilder.create().texOffs(184, 160).mirror().addBox(-9.0F, 0.0F, -3.0F, 9.0F, 0.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-14.0F, 13.0F, 8.0F));

		PartDefinition bone8 = bone4.addOrReplaceChild("bone8", CubeListBuilder.create().texOffs(0, 218).addBox(-5.0F, -5.0F, 0.0F, 10.0F, 10.0F, 28.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -2.0F, 11.0F));

		PartDefinition bone9 = bone8.addOrReplaceChild("bone9", CubeListBuilder.create().texOffs(78, 213).addBox(-4.0F, -4.0F, 0.0F, 8.0F, 8.0F, 35.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 28.0F));

		bone9.addOrReplaceChild("bone10", CubeListBuilder.create().texOffs(168, 212).addBox(-3.0F, -3.0F, 0.0F, 6.0F, 6.0F, 38.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 35.0F));

		PartDefinition bone11 = bone4.addOrReplaceChild("bone11", CubeListBuilder.create().texOffs(0, 218).addBox(-5.0F, -5.0F, 0.0F, 10.0F, 10.0F, 28.0F, new CubeDeformation(0.0F)), PartPose.offset(8.0F, -13.0F, 11.0F));

		PartDefinition bone12 = bone11.addOrReplaceChild("bone12", CubeListBuilder.create().texOffs(78, 213).addBox(-4.0F, -4.0F, 0.0F, 8.0F, 8.0F, 35.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 28.0F));

		bone12.addOrReplaceChild("bone13", CubeListBuilder.create().texOffs(168, 212).addBox(-3.0F, -3.0F, 0.0F, 6.0F, 6.0F, 38.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 35.0F));

		PartDefinition bone17 = bone4.addOrReplaceChild("bone17", CubeListBuilder.create().texOffs(0, 218).addBox(-5.0F, -5.0F, 0.0F, 10.0F, 10.0F, 28.0F, new CubeDeformation(0.0F)), PartPose.offset(8.0F, 8.0F, 11.0F));

		PartDefinition bone18 = bone17.addOrReplaceChild("bone18", CubeListBuilder.create().texOffs(78, 213).addBox(-4.0F, -4.0F, 0.0F, 8.0F, 8.0F, 35.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 28.0F));

		bone18.addOrReplaceChild("bone19", CubeListBuilder.create().texOffs(168, 212).addBox(-3.0F, -3.0F, 0.0F, 6.0F, 6.0F, 38.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 35.0F));

		PartDefinition bone14 = bone4.addOrReplaceChild("bone14", CubeListBuilder.create().texOffs(0, 218).mirror().addBox(-5.0F, -5.0F, 0.0F, 10.0F, 10.0F, 28.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-8.0F, -13.0F, 11.0F));

		PartDefinition bone15 = bone14.addOrReplaceChild("bone15", CubeListBuilder.create().texOffs(78, 213).mirror().addBox(-4.0F, -4.0F, 0.0F, 8.0F, 8.0F, 35.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 0.0F, 28.0F));

		bone15.addOrReplaceChild("bone16", CubeListBuilder.create().texOffs(168, 212).mirror().addBox(-3.0F, -3.0F, 0.0F, 6.0F, 6.0F, 38.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 0.0F, 35.0F));

		PartDefinition bone20 = bone4.addOrReplaceChild("bone20", CubeListBuilder.create().texOffs(0, 218).mirror().addBox(-5.0F, -5.0F, 0.0F, 10.0F, 10.0F, 28.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-8.0F, 8.0F, 11.0F));

		PartDefinition bone21 = bone20.addOrReplaceChild("bone21", CubeListBuilder.create().texOffs(78, 213).mirror().addBox(-4.0F, -4.0F, 0.0F, 8.0F, 8.0F, 35.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 0.0F, 28.0F));

		bone21.addOrReplaceChild("bone22", CubeListBuilder.create().texOffs(168, 212).mirror().addBox(-3.0F, -3.0F, 0.0F, 6.0F, 6.0F, 38.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 0.0F, 35.0F));

		PartDefinition bone23 = bone4.addOrReplaceChild("bone23", CubeListBuilder.create().texOffs(188, 117).addBox(-2.0F, -2.0F, -18.0F, 4.0F, 4.0F, 18.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 5.0F, -10.0F));

		PartDefinition bone24 = bone23.addOrReplaceChild("bone24", CubeListBuilder.create().texOffs(188, 117).addBox(-2.0F, -2.0F, -18.0F, 4.0F, 4.0F, 18.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, -18.0F));

		bone24.addOrReplaceChild("bone25", CubeListBuilder.create().texOffs(0, 0).addBox(-2.0F, -2.0F, -3.0F, 4.0F, 2.0F, 3.0F, new CubeDeformation(0.01F)), PartPose.offset(0.0F, 0.0F, -18.0F));

		bone24.addOrReplaceChild("bone26", CubeListBuilder.create().texOffs(0, 22).addBox(-2.0F, 0.0F, -3.0F, 4.0F, 2.0F, 3.0F, new CubeDeformation(0.01F)), PartPose.offset(0.0F, 0.0F, -18.0F));

		return LayerDefinition.create(meshdefinition, 256, 256);
	}
	
	@Override
	public void setupAnim(EntityMutavore entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch)
	{
		this.root().getAllParts().forEach(ModelPart::resetPose);
		float partialTicks = ageInTicks - entity.tickCount;
        float yBodyRot = Mth.rotLerp(partialTicks, entity.yBodyRotO, entity.yBodyRot);
		Vec2 rot1 = entity.worm1.getRot(partialTicks);
		Vec2 rot2 = entity.worm2.getRot(partialTicks);
		Vec2 rot3 = entity.worm3.getRot(partialTicks);
		Vec2 rot4 = entity.worm4.getRot(partialTicks);
		Vec2 rot5 = entity.worm5.getRot(partialTicks);
		Vec2 rot6 = entity.worm6.getRot(partialTicks);
		Vec2 rot7 = entity.worm7.getRot(partialTicks);
		Vec2 rot8 = entity.worm8.getRot(partialTicks);
		Vec2 rot9 = entity.worm9.getRot(partialTicks);
		Vec2 rot10 = entity.worm10.getRot(partialTicks);
		Vec2 rot11 = entity.worm11.getRot(partialTicks);
		Vec2 rot12 = entity.worm12.getRot(partialTicks);
		Vec2 rot13 = entity.worm13.getRot(partialTicks);
		Vec2 rot14 = entity.worm14.getRot(partialTicks);
		Vec2 rot15 = entity.worm15.getRot(partialTicks);
		BTAClientUtil.animateHead(this.bone4, netHeadYaw, headPitch);
		BTAClientUtil.animateHead(this.bone8, rot1.y - netHeadYaw - yBodyRot, rot1.x - headPitch);
		BTAClientUtil.animateHead(this.bone9, rot2.y - netHeadYaw - yBodyRot, rot2.x - headPitch);
		BTAClientUtil.animateHead(this.bone10, rot3.y - netHeadYaw - yBodyRot, rot3.x - headPitch);
		BTAClientUtil.animateHead(this.bone11, rot4.y - netHeadYaw - yBodyRot, rot4.x - headPitch);
		BTAClientUtil.animateHead(this.bone12, rot5.y - netHeadYaw - yBodyRot, rot5.x - headPitch);
		BTAClientUtil.animateHead(this.bone13, rot6.y - netHeadYaw - yBodyRot, rot6.x - headPitch);
		BTAClientUtil.animateHead(this.bone14, rot7.y - netHeadYaw - yBodyRot, rot7.x - headPitch);
		BTAClientUtil.animateHead(this.bone15, rot8.y - netHeadYaw - yBodyRot, rot8.x - headPitch);
		BTAClientUtil.animateHead(this.bone16, rot9.y - netHeadYaw - yBodyRot, rot9.x - headPitch);
		BTAClientUtil.animateHead(this.bone17, rot10.y - netHeadYaw - yBodyRot, rot10.x - headPitch);
		BTAClientUtil.animateHead(this.bone18, rot11.y - netHeadYaw - yBodyRot, rot11.x - headPitch);
		BTAClientUtil.animateHead(this.bone19, rot12.y - netHeadYaw - yBodyRot, rot12.x - headPitch);
		BTAClientUtil.animateHead(this.bone20, rot13.y - netHeadYaw - yBodyRot, rot13.x - headPitch);
		BTAClientUtil.animateHead(this.bone21, rot14.y - netHeadYaw - yBodyRot, rot14.x - headPitch);
		BTAClientUtil.animateHead(this.bone22, rot15.y - netHeadYaw - yBodyRot, rot15.x - headPitch);
		
		Vec3 tonguePos = BTAClientUtil.getWorldPosition(entity, this.root, new Vec3(0.0F, entity.yBodyRot, 0.0F), new String[] {"bone4", "bone23", "bone24"});
		entity.posArray[0] = tonguePos;
		BTANetwork.sendToServer(new UpdatePosArrayPacket(entity, tonguePos, 0));
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