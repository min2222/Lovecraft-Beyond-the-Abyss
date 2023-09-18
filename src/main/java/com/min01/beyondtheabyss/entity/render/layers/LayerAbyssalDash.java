package com.min01.beyondtheabyss.entity.render.layers;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.misc.AbyssRenderType;
import com.min01.beyondtheabyss.misc.ClientEventHandler;
import com.min01.beyondtheabyss.util.DeepAbyssUtil;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;

public class LayerAbyssalDash<T extends LivingEntity, M extends EntityModel<T>> extends RenderLayer<T, M>
{
	public static final ResourceLocation TEXTURE = new ResourceLocation(BeyondtheAbyss.MODID, "textures/entity/abyssal_dash.png");
	public static final String BOX = "box";
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(BeyondtheAbyss.MODID, "abyssal_dash"), "main");
	private final ModelPart box;
	   
	public LayerAbyssalDash(RenderLayerParent<T, M> p_117346_) 
	{
		super(p_117346_);
		ModelPart modelpart = ClientEventHandler.MC.getEntityModels().bakeLayer(LAYER_LOCATION);
		this.box = modelpart.getChild("box");
	}
	
	public static LayerDefinition createLayer()
	{
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();
		partdefinition.addOrReplaceChild("box", CubeListBuilder.create().texOffs(0, 0).addBox(-8.0F, -16.0F, -8.0F, 16.0F, 32.0F, 16.0F), PartPose.ZERO);
		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void render(PoseStack p_117349_, MultiBufferSource p_117350_, int p_117351_, T p_117352_, float p_117353_, float p_117354_, float p_117355_, float p_117356_, float p_117357_, float p_117358_) 
	{
		if(DeepAbyssUtil.shouldRenderAbyssalDashLayer(p_117352_)) 
		{
			VertexConsumer vertexconsumer = p_117350_.getBuffer(AbyssRenderType.getGlowingEffect(TEXTURE));

			for(int i = 0; i < 3; ++i)
			{
				p_117349_.pushPose();
				float f = p_117356_ * (float)(-(45 + i * 5));
				p_117349_.mulPose(Vector3f.YP.rotationDegrees(f));
				float f1 = 0.75F * (float)i;
				p_117349_.scale(f1, f1, f1);
				p_117349_.translate(0.0D, (double)(-0.2F + 0.6F * (float)i), 0.0D);
				this.box.render(p_117349_, vertexconsumer, p_117351_, OverlayTexture.NO_OVERLAY);
				p_117349_.popPose();
			}
		}
	}
}
