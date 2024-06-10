package com.min01.beyondtheabyss.entity.renderer.living;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.entity.deepabyss.EntityLatcher;
import com.min01.beyondtheabyss.entity.model.ModelLatcher;
import com.min01.beyondtheabyss.network.BTANetwork;
import com.min01.beyondtheabyss.network.PartPositionUpdatePacket;
import com.min01.beyondtheabyss.util.BTAClientUtil;
import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;

public class LatcherRenderer extends MobRenderer<EntityLatcher, ModelLatcher>
{
	public static final String[] TAIL2 = new String[] {"Body", "Tails", "Tails2", "TailEdge"};
	public static final String[] TAIL = new String[] {"Body", "Tails", "Tails2"};
	public LatcherRenderer(Context p_174304_)
	{
		super(p_174304_, new ModelLatcher(p_174304_.bakeLayer(ModelLatcher.LAYER_LOCATION)), 0.5F);
	}
	
	//TEST
	/*@Override
	public void render(EntityLatcher p_115455_, float p_115456_, float p_115457_, PoseStack p_115458_, MultiBufferSource p_115459_, int p_115460_) 
	{
		super.render(p_115455_, p_115456_, p_115457_, p_115458_, p_115459_, p_115460_);
    	ExtendedPostChain shaderChain = BTAShaders.getTest();
    	shaderChain.isEnabled = true;
	}
	
	@Override
	protected RenderType getRenderType(EntityLatcher p_115322_, boolean p_115323_, boolean p_115324_, boolean p_115325_)
	{
		return BTARenderType.test(this.getTextureLocation(p_115322_));
	}*/
	
	@Override
	public void render(EntityLatcher p_115455_, float p_115456_, float p_115457_, PoseStack p_115458_, MultiBufferSource p_115459_, int p_115460_) 
	{
		super.render(p_115455_, p_115456_, p_115457_, p_115458_, p_115459_, p_115460_);
		Vec3 tail2Pos = BTAClientUtil.getWorldPosition(p_115455_, p_115455_.yHeadRot, this.model.root(), TAIL2);
		Vec3 tailPos = BTAClientUtil.getWorldPosition(p_115455_, p_115455_.yHeadRot, this.model.root(), TAIL);
		p_115455_.posArray[1] = tail2Pos;
		p_115455_.posArray[0] = tailPos;
	    BTANetwork.sendToAll(new PartPositionUpdatePacket(p_115455_, tail2Pos, 1));
	    BTANetwork.sendToAll(new PartPositionUpdatePacket(p_115455_, tailPos, 0));
	}

	@Override
	public ResourceLocation getTextureLocation(EntityLatcher p_115812_) 
	{
		return new ResourceLocation(BeyondtheAbyss.MODID, "textures/entity/latcher.png");
	}
}
