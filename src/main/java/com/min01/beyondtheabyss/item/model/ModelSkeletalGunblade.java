package com.min01.beyondtheabyss.item.model;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.capabilities.ItemAnimationCapabilityImpl;
import com.min01.beyondtheabyss.item.animation.SkeletalGunbladeAnimation;
import com.min01.beyondtheabyss.item.deepabyss.SkeletalGunbladeItem;
import com.min01.beyondtheabyss.util.BTAClientUtil;
import com.min01.beyondtheabyss.util.BTAUtil;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public class ModelSkeletalGunblade extends HierarchicalItemModel
{
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(BeyondtheAbyss.MODID, "skeletal_gunblade"), "main");
	private final ModelPart root;
	private final ModelPart SkeletalGunblade;
	private final ModelPart Blade;
	public final ModelPart EnergyRay;
	private final ModelPart InnerRay;

	public ModelSkeletalGunblade(ModelPart root)
	{
		this.root = root.getChild("root");
		this.SkeletalGunblade = this.root.getChild("SkeletalGunblade");
		this.Blade = this.SkeletalGunblade.getChild("Blade");
		this.EnergyRay = this.Blade.getChild("EnergyRay");
		this.InnerRay = this.EnergyRay.getChild("InnerRay");
	}

	public static LayerDefinition createBodyLayer() 
	{
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition SkeletalGunblade = root.addOrReplaceChild("SkeletalGunblade", CubeListBuilder.create(), PartPose.offset(0.0F, -4.0F, 7.0F));

		PartDefinition Base = SkeletalGunblade.addOrReplaceChild("Base", CubeListBuilder.create().texOffs(63, 19).addBox(-4.0F, -6.0F, -7.0F, 8.0F, 3.0F, 13.0F, new CubeDeformation(0.001F))
		.texOffs(98, 36).addBox(-4.0F, -3.0F, -3.0F, 8.0F, 2.0F, 8.0F, new CubeDeformation(0.001F))
		.texOffs(63, 0).addBox(-4.0F, -10.0F, -7.0F, 8.0F, 5.0F, 13.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 4.0F, -7.0F));

		PartDefinition Skull = Base.addOrReplaceChild("Skull", CubeListBuilder.create().texOffs(0, 46).addBox(-4.5F, -3.9F, -12.7F, 9.0F, 4.0F, 15.0F, new CubeDeformation(0.0F))
		.texOffs(49, 46).addBox(-4.5F, 0.1F, -12.7F, 9.0F, 2.0F, 15.0F, new CubeDeformation(0.0F))
		.texOffs(63, 36).addBox(-4.0F, -6.9F, -12.7F, 8.0F, 3.0F, 5.0F, new CubeDeformation(0.0F))
		.texOffs(99, 88).addBox(-2.5F, -5.9F, -7.7F, 5.0F, 2.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(98, 58).addBox(-4.0F, -6.9F, -7.7F, 8.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -6.1F, 4.7F));

		PartDefinition Horn = Skull.addOrReplaceChild("Horn", CubeListBuilder.create(), PartPose.offset(1.0F, 1.1F, -0.7F));

		Horn.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(91, 99).addBox(-7.0F, -1.0F, -11.0F, 5.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(0, 85).addBox(-9.0F, -1.0F, -11.0F, 2.0F, 2.0F, 12.0F, new CubeDeformation(0.0F))
		.texOffs(13, 100).addBox(-7.0F, -1.0F, -1.0F, 4.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(0, 100).addBox(3.0F, -1.0F, -1.0F, 4.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(99, 94).addBox(2.0F, -1.0F, -11.0F, 5.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(84, 64).addBox(7.0F, -1.0F, -11.0F, 2.0F, 2.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, 0.0F, 0.0F, -1.5708F, 0.0F, 0.0F));

		Base.addOrReplaceChild("bone", CubeListBuilder.create().texOffs(70, 99).addBox(-2.5F, -5.0F, -5.0F, 5.0F, 5.0F, 5.0F, new CubeDeformation(0.0F))
		.texOffs(84, 79).addBox(-2.5F, -5.0F, 0.0F, 5.0F, 0.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(29, 85).addBox(2.5F, -5.0F, 0.0F, 0.0F, 5.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(29, 85).mirror().addBox(-2.5F, -5.0F, 0.0F, 0.0F, 5.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(26, 100).addBox(-2.5F, 0.0F, 0.0F, 5.0F, 0.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -2.25F, 9.0F));

		PartDefinition Blade = SkeletalGunblade.addOrReplaceChild("Blade", CubeListBuilder.create().texOffs(35, 83).addBox(-2.0F, -1.5F, -13.0F, 4.0F, 3.0F, 13.0F, new CubeDeformation(-0.001F)), PartPose.offset(0.0F, -1.0F, -14.0F));

		Blade.addOrReplaceChild("Left", CubeListBuilder.create().texOffs(0, 0).addBox(-2.25F, 0.0F, -20.0F, 9.0F, 0.0F, 22.0F, new CubeDeformation(0.0F))
		.texOffs(49, 64).addBox(-2.25F, -1.5F, -13.0F, 2.0F, 3.0F, 15.0F, new CubeDeformation(0.0F)), PartPose.offset(2.25F, 0.0F, 0.0F));

		Blade.addOrReplaceChild("Right", CubeListBuilder.create().texOffs(0, 23).addBox(-6.75F, 0.0F, -20.0F, 9.0F, 0.0F, 22.0F, new CubeDeformation(0.0F))
		.texOffs(0, 66).addBox(0.25F, -1.5F, -13.0F, 2.0F, 3.0F, 15.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.25F, 0.0F, 0.0F));

		PartDefinition EnergyRay = Blade.addOrReplaceChild("EnergyRay", CubeListBuilder.create().texOffs(99, 79).addBox(-2.0F, -2.0F, -4.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, -2.0F));

		EnergyRay.addOrReplaceChild("InnerRay", CubeListBuilder.create().texOffs(35, 74).addBox(-1.5F, -1.5F, -3.0F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, -0.5F));

		SkeletalGunblade.addOrReplaceChild("Heart", CubeListBuilder.create().texOffs(70, 83).addBox(-3.5F, -0.7415F, -3.5F, 7.0F, 8.0F, 7.0F, new CubeDeformation(0.0F))
		.texOffs(98, 47).addBox(-2.5F, -1.7415F, -4.5F, 5.0F, 5.0F, 5.0F, new CubeDeformation(0.0F))
		.texOffs(35, 66).addBox(-1.5F, -4.7415F, -1.5F, 3.0F, 4.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -1.0085F, -9.25F, 1.5708F, 0.0F, 3.1416F));

		return LayerDefinition.create(meshdefinition, 256, 256);
	}

	@Override
	public void setupAnim(ItemStack stack, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch)
	{
		this.root().getAllParts().forEach(ModelPart::resetPose);
		ItemAnimationCapabilityImpl cap = BTAUtil.getItemAnimationCapability(BTAClientUtil.MC.player, stack);
		
		this.animate(stack, cap.gunBladeOpenAnimationState, SkeletalGunbladeAnimation.GUNBLADE_OPEN, ageInTicks);
		this.animate(stack, cap.gunBladeCloseAnimationState, SkeletalGunbladeAnimation.GUNBLADE_CLOSE, ageInTicks);
		this.EnergyRay.zScale += SkeletalGunbladeItem.getLaserLength(stack);
		this.InnerRay.z += 0.5F;
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