package com.min01.beyondtheabyss.entity.renderer;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.entity.misc.EntityChainTrapMaw;
import com.min01.beyondtheabyss.entity.model.ModelChainTrapChain;
import com.min01.beyondtheabyss.entity.model.ModelChainTrapMaw;
import com.min01.beyondtheabyss.misc.KinematicChain.ChainSegment;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;

public class ChainTrapMawRenderer extends EntityRenderer<EntityChainTrapMaw>
{
	public final ModelChainTrapMaw model;
	public final ModelChainTrapChain chainModel;
	public ChainTrapMawRenderer(Context p_174008_) 
	{
		super(p_174008_);
		this.model = new ModelChainTrapMaw(p_174008_.bakeLayer(ModelChainTrapMaw.LAYER_LOCATION));
		this.chainModel = new ModelChainTrapChain(p_174008_.bakeLayer(ModelChainTrapChain.LAYER_LOCATION));
	}
	
	@Override
	public void render(EntityChainTrapMaw p_114485_, float p_114486_, float p_114487_, PoseStack p_114488_, MultiBufferSource p_114489_, int p_114490_)
	{
		if(p_114485_.chain != null)
		{
			p_114488_.pushPose();
			p_114488_.scale(-1.0F, -1.0F, 1.0F);
			p_114488_.mulPose(Vector3f.YP.rotationDegrees(p_114485_.chain.getTipSegment().getRot().y));
			p_114488_.mulPose(Vector3f.XP.rotationDegrees(-p_114485_.chain.getTipSegment().getRot().x - 90.0F));
			p_114488_.translate(0, -1.5F, 0);
			this.model.setupAnim(p_114485_, 0, 0, 0, 0, 0);
			this.model.renderToBuffer(p_114488_, p_114489_.getBuffer(RenderType.entityCutoutNoCull(this.getTextureLocation(p_114485_))), p_114490_, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
			p_114488_.popPose();
			
			for(int i = 0; i < p_114485_.chain.getSegments().length - 1; i++)
			{
				ChainSegment segment = p_114485_.chain.getSegments()[i];
				Vec3 pos = segment.getPos().subtract(p_114485_.position());
				Vec2 rot = segment.getRot();
				p_114488_.pushPose();
				p_114488_.scale(-1.0F, -1.0F, 1.0F);
				p_114488_.translate(-pos.x, -pos.y, pos.z);
				p_114488_.mulPose(Vector3f.YP.rotationDegrees(rot.y));
				p_114488_.mulPose(Vector3f.XP.rotationDegrees(-rot.x - 90.0F));
				p_114488_.translate(0, -1.5F, 0);
				this.chainModel.renderToBuffer(p_114488_, p_114489_.getBuffer(RenderType.entityCutoutNoCull(new ResourceLocation(BeyondtheAbyss.MODID, "textures/entity/chain_trap_chain.png"))), p_114490_, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
				p_114488_.popPose();
			}
		}
	}

	@Override
	public ResourceLocation getTextureLocation(EntityChainTrapMaw p_114482_) 
	{
		return new ResourceLocation(BeyondtheAbyss.MODID, "textures/entity/chain_trap_maw.png");
	}
}
