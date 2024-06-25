package com.min01.beyondtheabyss.entity.model;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.entity.deepabyss.EntityPhasmozoa;
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

public class ModelPhasmozoa extends HierarchicalModel<EntityPhasmozoa> 
{
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(BeyondtheAbyss.MODID, "phasmozoa"), "main");
	private final ModelPart root;

	public ModelPhasmozoa(ModelPart root)
	{
		this.root = root.getChild("root");
	}

	public static LayerDefinition createBodyLayer() 
	{
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition Phasmozoa = root.addOrReplaceChild("Phasmozoa", CubeListBuilder.create(), PartPose.offset(0.0F, -12.0F, 0.0F));

		PartDefinition Outer = Phasmozoa.addOrReplaceChild("Outer", CubeListBuilder.create().texOffs(0, 36).addBox(-8.0F, -32.0F, -8.0F, 16.0F, 26.0F, 16.0F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(-11.0F, -6.0F, -11.0F, 22.0F, 13.0F, 22.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -36.0F, 0.0F));

		Outer.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(47, 116).mirror().addBox(-3.0F, -13.0F, 0.0F, 3.0F, 26.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-8.0F, -19.0F, 8.0F, 0.0F, 0.6109F, 0.0F));

		Outer.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(47, 116).mirror().addBox(-3.0F, -13.0F, 0.0F, 3.0F, 26.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-8.0F, -19.0F, -8.0F, 0.0F, -0.6109F, 0.0F));

		Outer.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(47, 116).addBox(0.0F, -13.0F, 0.0F, 3.0F, 26.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(8.0F, -19.0F, -8.0F, 0.0F, 0.6109F, 0.0F));

		Outer.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(47, 116).addBox(0.0F, -13.0F, 0.0F, 3.0F, 26.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(8.0F, -19.0F, 8.0F, 0.0F, -0.6109F, 0.0F));

		PartDefinition FrontFin = Outer.addOrReplaceChild("FrontFin", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.001F, -11.0F, 1.309F, 0.0F, 0.0F));

		PartDefinition Leftfrontfin = FrontFin.addOrReplaceChild("Leftfrontfin", CubeListBuilder.create().texOffs(46, 71).addBox(-5.0F, 0.0F, -19.0F, 10.0F, 0.0F, 19.0F, new CubeDeformation(0.0F)), PartPose.offset(-8.0F, 0.0F, 0.0F));

		PartDefinition Leftfrontfin2 = Leftfrontfin.addOrReplaceChild("Leftfrontfin2", CubeListBuilder.create().texOffs(85, 102).addBox(-5.0F, 0.0F, -13.0F, 10.0F, 0.0F, 13.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, -19.0F));

		Leftfrontfin2.addOrReplaceChild("Leftfrontfin3", CubeListBuilder.create().texOffs(0, 90).addBox(-5.0F, 0.0F, -15.0F, 10.0F, 0.0F, 15.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, -13.0F));

		PartDefinition Rightfrontfin = FrontFin.addOrReplaceChild("Rightfrontfin", CubeListBuilder.create().texOffs(46, 71).mirror().addBox(-5.0F, 0.0F, -19.0F, 10.0F, 0.0F, 19.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(8.0F, 0.0F, 0.0F));

		PartDefinition Rightfrontfin2 = Rightfrontfin.addOrReplaceChild("Rightfrontfin2", CubeListBuilder.create().texOffs(85, 102).mirror().addBox(-5.0F, 0.0F, -13.0F, 10.0F, 0.0F, 13.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 0.0F, -19.0F));

		Rightfrontfin2.addOrReplaceChild("Rightfrontfin3", CubeListBuilder.create().texOffs(0, 90).mirror().addBox(-5.0F, 0.0F, -15.0F, 10.0F, 0.0F, 15.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 0.0F, -13.0F));

		PartDefinition BackFin = Outer.addOrReplaceChild("BackFin", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 11.0F, -1.309F, 0.0F, 0.0F));

		PartDefinition Leftbackfin = BackFin.addOrReplaceChild("Leftbackfin", CubeListBuilder.create().texOffs(67, 0).addBox(-5.0F, 0.0F, 0.0F, 10.0F, 0.0F, 19.0F, new CubeDeformation(0.0F)), PartPose.offset(8.0F, 0.001F, 0.0F));

		PartDefinition Leftbackfin2 = Leftbackfin.addOrReplaceChild("Leftbackfin2", CubeListBuilder.create().texOffs(38, 102).addBox(-5.0F, 0.0F, 0.0F, 10.0F, 0.0F, 13.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 19.0F));

		Leftbackfin2.addOrReplaceChild("Leftbackfin3", CubeListBuilder.create().texOffs(89, 20).addBox(-5.0F, 0.0F, 0.0F, 10.0F, 0.0F, 15.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 13.0F));

		PartDefinition Rightbackfin = BackFin.addOrReplaceChild("Rightbackfin", CubeListBuilder.create().texOffs(67, 0).mirror().addBox(-5.0F, 0.0F, 0.0F, 10.0F, 0.0F, 19.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-8.0F, 0.001F, 0.0F));

		PartDefinition Rightbackfin2 = Rightbackfin.addOrReplaceChild("Rightbackfin2", CubeListBuilder.create().texOffs(38, 102).mirror().addBox(-5.0F, 0.0F, 0.0F, 10.0F, 0.0F, 13.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 0.0F, 19.0F));

		Rightbackfin2.addOrReplaceChild("Rightbackfin3", CubeListBuilder.create().texOffs(89, 20).mirror().addBox(-5.0F, 0.0F, 0.0F, 10.0F, 0.0F, 15.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 0.0F, 13.0F));

		PartDefinition Leftbackfin4 = BackFin.addOrReplaceChild("Leftbackfin4", CubeListBuilder.create().texOffs(67, 0).addBox(-5.0F, 0.0F, 0.0F, 10.0F, 0.0F, 19.0F, new CubeDeformation(0.0F)), PartPose.offset(8.0F, 0.001F, 0.0F));

		PartDefinition Leftbackfin5 = Leftbackfin4.addOrReplaceChild("Leftbackfin5", CubeListBuilder.create().texOffs(38, 102).addBox(-5.0F, 0.0F, 0.0F, 10.0F, 0.0F, 13.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 19.0F));

		Leftbackfin5.addOrReplaceChild("Leftbackfin6", CubeListBuilder.create().texOffs(89, 20).addBox(-5.0F, 0.0F, 0.0F, 10.0F, 0.0F, 15.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 13.0F));

		PartDefinition Leftfin = Outer.addOrReplaceChild("Leftfin", CubeListBuilder.create(), PartPose.offsetAndRotation(11.0F, 0.001F, 0.0F, 0.0F, 0.0F, 1.309F));

		PartDefinition Leftleftfin = Leftfin.addOrReplaceChild("Leftleftfin", CubeListBuilder.create().texOffs(86, 71).addBox(0.0F, 0.0F, -5.0F, 19.0F, 0.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, -7.0F));

		PartDefinition Leftleftfin2 = Leftleftfin.addOrReplaceChild("Leftleftfin2", CubeListBuilder.create().texOffs(0, 106).addBox(0.0F, 0.0F, -5.0F, 13.0F, 0.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(19.0F, 0.0F, 0.0F));

		Leftleftfin2.addOrReplaceChild("Leftleftfin3", CubeListBuilder.create().texOffs(87, 91).addBox(0.0F, 0.0F, -5.0F, 15.0F, 0.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(13.0F, 0.0F, 0.0F));

		PartDefinition Rightleftfin = Leftfin.addOrReplaceChild("Rightleftfin", CubeListBuilder.create().texOffs(0, 79).addBox(0.0F, 0.0F, -5.0F, 19.0F, 0.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 7.0F));

		PartDefinition Rightleftfin2 = Rightleftfin.addOrReplaceChild("Rightleftfin2", CubeListBuilder.create().texOffs(99, 36).addBox(0.0F, 0.0F, -5.0F, 13.0F, 0.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(19.0F, 0.0F, 0.0F));

		Rightleftfin2.addOrReplaceChild("Rightleftfin3", CubeListBuilder.create().texOffs(36, 91).addBox(0.0F, 0.0F, -5.0F, 15.0F, 0.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(13.0F, 0.0F, 0.0F));

		PartDefinition Rightfin = Outer.addOrReplaceChild("Rightfin", CubeListBuilder.create(), PartPose.offsetAndRotation(-11.0F, 0.001F, 0.0F, 0.0F, 0.0F, -1.309F));

		PartDefinition Rightrightfin = Rightfin.addOrReplaceChild("Rightrightfin", CubeListBuilder.create().texOffs(86, 71).mirror().addBox(-19.0F, 0.0F, -5.0F, 19.0F, 0.0F, 10.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 0.0F, -7.0F));

		PartDefinition Rightrightfin2 = Rightrightfin.addOrReplaceChild("Rightrightfin2", CubeListBuilder.create().texOffs(0, 106).mirror().addBox(-13.0F, 0.0F, -5.0F, 13.0F, 0.0F, 10.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-19.0F, 0.0F, 0.0F));

		Rightrightfin2.addOrReplaceChild("Rightrightfin3", CubeListBuilder.create().texOffs(87, 91).mirror().addBox(-15.0F, 0.0F, -5.0F, 15.0F, 0.0F, 10.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-13.0F, 0.0F, 0.0F));

		PartDefinition Leftrightfin4 = Rightfin.addOrReplaceChild("Leftrightfin4", CubeListBuilder.create().texOffs(0, 79).mirror().addBox(-19.0F, 0.0F, -5.0F, 19.0F, 0.0F, 10.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 0.0F, 7.0F));

		PartDefinition Leftrightfin5 = Leftrightfin4.addOrReplaceChild("Leftrightfin5", CubeListBuilder.create().texOffs(99, 36).mirror().addBox(-13.0F, 0.0F, -5.0F, 13.0F, 0.0F, 10.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-19.0F, 0.0F, 0.0F));

		Leftrightfin5.addOrReplaceChild("Leftrightfin6", CubeListBuilder.create().texOffs(36, 91).mirror().addBox(-15.0F, 0.0F, -5.0F, 15.0F, 0.0F, 10.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-13.0F, 0.0F, 0.0F));

		PartDefinition Body = Phasmozoa.addOrReplaceChild("Body", CubeListBuilder.create().texOffs(65, 36).addBox(-5.5F, -19.0F, -5.5F, 11.0F, 23.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -46.0F, 0.0F));

		PartDefinition Body2 = Body.addOrReplaceChild("Body2", CubeListBuilder.create().texOffs(107, 0).addBox(-4.5F, -2.5F, -4.5F, 9.0F, 8.0F, 9.0F, new CubeDeformation(0.0F))
		.texOffs(72, 102).addBox(-2.5F, 4.5F, -2.5F, 5.0F, 3.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 4.5F, 0.0F));

		Body2.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(75, 0).addBox(-2.5F, 0.0F, 0.0F, 5.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 5.5F, -2.5F, -0.7854F, 0.0F, 0.0F));

		Body2.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(75, 11).mirror().addBox(0.0F, 0.0F, -2.5F, 0.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-2.5F, 5.5F, 0.0F, 0.0F, 0.0F, 0.7854F));

		Body2.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(75, 11).addBox(0.0F, 0.0F, -2.5F, 0.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.5F, 5.5F, 0.0F, 0.0F, 0.0F, -0.7854F));

		Body2.addOrReplaceChild("cube_r8", CubeListBuilder.create().texOffs(65, 36).addBox(-2.5F, 0.0F, 0.0F, 5.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 5.5F, 2.5F, 0.7854F, 0.0F, 0.0F));

		PartDefinition Armleft = Body2.addOrReplaceChild("Armleft", CubeListBuilder.create().texOffs(89, 20).addBox(-1.5F, -1.25F, -1.5F, 3.0F, 11.0F, 3.0F, new CubeDeformation(0.01F)), PartPose.offset(-5.0F, 3.75F, 0.0395F));

		PartDefinition Armleft2 = Armleft.addOrReplaceChild("Armleft2", CubeListBuilder.create().texOffs(0, 117).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 11.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 9.75F, 0.0F, -0.5236F, 0.0F, 0.0F));

		Armleft2.addOrReplaceChild("Nail1", CubeListBuilder.create().texOffs(0, 79).addBox(-0.5F, -1.0F, 0.0F, 4.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 10.0F, 0.0F));

		Armleft2.addOrReplaceChild("Nail2", CubeListBuilder.create().texOffs(49, 79).addBox(-0.5F, 0.0F, -2.0F, 4.0F, 0.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 9.0F, -1.0F, 1.0472F, 0.0F, 0.0F));

		Armleft2.addOrReplaceChild("Nail3", CubeListBuilder.create().texOffs(49, 47).addBox(-0.5F, 0.0F, -1.0F, 4.0F, 0.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 9.0F, 1.0F, -1.2217F, 0.0F, 0.0F));

		PartDefinition Armright = Body2.addOrReplaceChild("Armright", CubeListBuilder.create().texOffs(89, 20).mirror().addBox(-1.5F, -1.25F, -1.5F, 3.0F, 11.0F, 3.0F, new CubeDeformation(0.01F)).mirror(false), PartPose.offset(5.0F, 3.75F, 0.0395F));

		PartDefinition Armright2 = Armright.addOrReplaceChild("Armright2", CubeListBuilder.create().texOffs(0, 117).mirror().addBox(-1.0F, -1.0F, -1.0F, 2.0F, 11.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 9.75F, 0.0F, -0.5236F, 0.0F, 0.0F));

		Armright2.addOrReplaceChild("Nail4", CubeListBuilder.create().texOffs(0, 79).mirror().addBox(-3.5F, -1.0F, 0.0F, 4.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 10.0F, 0.0F));

		Armright2.addOrReplaceChild("Nail5", CubeListBuilder.create().texOffs(49, 79).mirror().addBox(-3.5F, 0.0F, -2.0F, 4.0F, 0.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 9.0F, -1.0F, 1.0472F, 0.0F, 0.0F));

		Armright2.addOrReplaceChild("Nail6", CubeListBuilder.create().texOffs(49, 47).mirror().addBox(-3.5F, 0.0F, -1.0F, 4.0F, 0.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 9.0F, 1.0F, -1.2217F, 0.0F, 0.0F));

		PartDefinition Tentacle = Body.addOrReplaceChild("Tentacle", CubeListBuilder.create(), PartPose.offset(0.0F, 1.0F, 0.0F));

		PartDefinition Right = Tentacle.addOrReplaceChild("Right", CubeListBuilder.create().texOffs(77, 91).addBox(-4.0F, 0.0F, -2.5F, 4.0F, 0.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(-4.5F, 5.5F, 0.0F));

		PartDefinition t2 = Right.addOrReplaceChild("t2", CubeListBuilder.create().texOffs(100, 116).addBox(0.0F, 0.0F, -2.5F, 0.0F, 10.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(-4.0F, 0.0F, 0.0F));

		PartDefinition t3 = t2.addOrReplaceChild("t3", CubeListBuilder.create().texOffs(89, 116).addBox(0.0F, 0.0F, -2.5F, 0.0F, 10.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 10.0F, 0.0F));

		PartDefinition t4 = t3.addOrReplaceChild("t4", CubeListBuilder.create().texOffs(54, 116).addBox(0.0F, 0.0F, -2.5F, 1.0F, 10.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 10.0F, 0.0F));

		PartDefinition t5 = t4.addOrReplaceChild("t5", CubeListBuilder.create().texOffs(110, 47).addBox(0.0F, 0.0F, -2.5F, 1.0F, 10.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 10.0F, 0.0F));

		t5.addOrReplaceChild("t6", CubeListBuilder.create().texOffs(49, 36).addBox(0.0F, -5.0F, -2.5F, 5.0F, 5.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 10.0F, 0.0F));

		PartDefinition Left = Tentacle.addOrReplaceChild("Left", CubeListBuilder.create().texOffs(86, 82).addBox(0.0F, 0.0F, -2.5F, 4.0F, 0.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(4.5F, 5.5F, 0.0F));

		PartDefinition t7 = Left.addOrReplaceChild("t7", CubeListBuilder.create().texOffs(78, 116).addBox(0.0F, 0.0F, -2.5F, 0.0F, 10.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(4.0F, 0.0F, 0.0F));

		PartDefinition t8 = t7.addOrReplaceChild("t8", CubeListBuilder.create().texOffs(67, 116).addBox(0.0F, 0.0F, -2.5F, 0.0F, 10.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 10.0F, 0.0F));

		PartDefinition t9 = t8.addOrReplaceChild("t9", CubeListBuilder.create().texOffs(67, 0).addBox(-1.0F, 0.0F, -2.5F, 1.0F, 10.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 10.0F, 0.0F));

		PartDefinition t10 = t9.addOrReplaceChild("t10", CubeListBuilder.create().texOffs(0, 36).addBox(-1.0F, 0.0F, -2.5F, 1.0F, 10.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 10.0F, 0.0F));

		t10.addOrReplaceChild("t11", CubeListBuilder.create().texOffs(0, 11).addBox(-5.0F, -5.0F, -2.5F, 5.0F, 5.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 10.0F, 0.0F));

		PartDefinition Backleft = Tentacle.addOrReplaceChild("Backleft", CubeListBuilder.create().texOffs(100, 82).addBox(-2.5F, 0.0F, 0.0F, 5.0F, 0.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(3.0F, 5.5F, 4.5F));

		PartDefinition t17 = Backleft.addOrReplaceChild("t17", CubeListBuilder.create().texOffs(20, 117).addBox(-2.5F, 0.0F, 0.0F, 5.0F, 10.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 4.0F));

		PartDefinition t18 = t17.addOrReplaceChild("t18", CubeListBuilder.create().texOffs(9, 117).addBox(-2.5F, 0.0F, 0.0F, 5.0F, 10.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 10.0F, 0.0F));

		PartDefinition t19 = t18.addOrReplaceChild("t19", CubeListBuilder.create().texOffs(111, 116).addBox(-2.5F, 0.0F, -1.0F, 5.0F, 10.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 10.0F, 0.0F));

		PartDefinition t20 = t19.addOrReplaceChild("t20", CubeListBuilder.create().texOffs(0, 90).addBox(-2.5F, 0.0F, -1.0F, 5.0F, 10.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 10.0F, 0.0F));

		t20.addOrReplaceChild("t21", CubeListBuilder.create().texOffs(0, 0).addBox(-2.5F, -5.0F, -5.0F, 5.0F, 5.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 10.0F, 0.0F));

		PartDefinition Backright = Tentacle.addOrReplaceChild("Backright", CubeListBuilder.create().texOffs(100, 82).mirror().addBox(-2.5F, 0.0F, 0.0F, 5.0F, 0.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-3.0F, 5.5F, 4.5F));

		PartDefinition t22 = Backright.addOrReplaceChild("t22", CubeListBuilder.create().texOffs(20, 117).mirror().addBox(-2.5F, 0.0F, 0.0F, 5.0F, 10.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 0.0F, 4.0F));

		PartDefinition t23 = t22.addOrReplaceChild("t23", CubeListBuilder.create().texOffs(9, 117).mirror().addBox(-2.5F, 0.0F, 0.0F, 5.0F, 10.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 10.0F, 0.0F));

		PartDefinition t24 = t23.addOrReplaceChild("t24", CubeListBuilder.create().texOffs(111, 116).mirror().addBox(-2.5F, 0.0F, -1.0F, 5.0F, 10.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 10.0F, 0.0F));

		PartDefinition t25 = t24.addOrReplaceChild("t25", CubeListBuilder.create().texOffs(0, 90).mirror().addBox(-2.5F, 0.0F, -1.0F, 5.0F, 10.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 10.0F, 0.0F));

		t25.addOrReplaceChild("t26", CubeListBuilder.create().texOffs(0, 0).mirror().addBox(-2.5F, -5.0F, -5.0F, 5.0F, 5.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 10.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 256, 256);
	}

	@Override
	public void setupAnim(EntityPhasmozoa entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) 
	{
		this.root().getAllParts().forEach(ModelPart::resetPose);
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha)
	{
		root.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}
	
	@Override
	public ModelPart root() 
	{
		return this.root;
	}
}