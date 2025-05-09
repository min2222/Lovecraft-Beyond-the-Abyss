package com.min01.beyondtheabyss.mixin;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.min01.beyondtheabyss.animation.IHierarchicalPlayerModel;
import com.min01.beyondtheabyss.animation.KeyframePlayerAnimations;
import com.min01.beyondtheabyss.util.BTAClientUtil;
import com.mojang.math.Vector3f;

import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.LivingEntity;

@Mixin(PlayerModel.class)
public class MixinPlayerModel<T extends LivingEntity> implements IHierarchicalPlayerModel
{
	private Map<String, ModelPart> modelMap = new HashMap<>();
	
	private static final Vector3f ANIMATION_VECTOR_CACHE = new Vector3f();
    
	@Inject(at = @At("HEAD"), method = "setupAnim", cancellable = true)
    private void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, CallbackInfo ci)
    {	
    	if(this.modelMap.isEmpty())
    	{
    		this.modelMap.put("head", PlayerModel.class.cast(this).head);
    		this.modelMap.put("headwear", PlayerModel.class.cast(this).hat);
    		this.modelMap.put("body", PlayerModel.class.cast(this).body);
    		this.modelMap.put("jacket", PlayerModel.class.cast(this).jacket);
    		this.modelMap.put("left_arm", PlayerModel.class.cast(this).leftArm);
    		this.modelMap.put("left_sleeve", PlayerModel.class.cast(this).leftSleeve);
    		this.modelMap.put("right_arm", PlayerModel.class.cast(this).rightArm);
    		this.modelMap.put("right_sleeve", PlayerModel.class.cast(this).rightSleeve);
    		this.modelMap.put("left_leg", PlayerModel.class.cast(this).leftLeg);
    		this.modelMap.put("left_pants", PlayerModel.class.cast(this).leftPants);
    		this.modelMap.put("right_leg", PlayerModel.class.cast(this).rightLeg);
    		this.modelMap.put("right_pants", PlayerModel.class.cast(this).rightPants);
    	}
    	
    	this.modelMap.values().forEach(ModelPart::resetPose);
    }
    
    @Inject(at = @At("TAIL"), method = "setupAnim", cancellable = true)
    private void setupAnimTail(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, CallbackInfo ci)
    {
    	//TODO
    	/*this.animate(BTAUtil.getPlayerAnimationState(entity, SkeletalGunbladeItem.GUNBLADE_OPEN), PlayerAnimation.GunbladeAnimation.GUNBLADE_OPEN, ageInTicks);
    	this.animate(BTAUtil.getPlayerAnimationState(entity, SkeletalGunbladeItem.GUNBLADE_CLOSE), PlayerAnimation.GunbladeAnimation.GUNBLADE_CLOSE, ageInTicks);
    	this.animate(BTAUtil.getPlayerAnimationState(entity, SkeletalGunbladeItem.GUNBLADE_BRING_OUT), PlayerAnimation.GunbladeAnimation.GUNBLADE_BRING_OUT, ageInTicks);
    	this.animate(BTAUtil.getPlayerAnimationState(entity, SkeletalGunbladeItem.GUNBLADE_PUT_DOWN), PlayerAnimation.GunbladeAnimation.GUNBLADE_PUT_DOWN, ageInTicks);
    	this.animate(BTAUtil.getPlayerAnimationState(entity, SkeletalGunbladeItem.GUNBLADE_SWING), PlayerAnimation.GunbladeAnimation.GUNBLADE_SWING, ageInTicks);
    	this.animate(BTAUtil.getPlayerAnimationState(entity, SkeletalGunbladeItem.GUNBLADE_SHOOT_LIGHT), PlayerAnimation.GunbladeAnimation.GUNBLADE_SHOOT_LIGHT, ageInTicks);*/
    }
    
	@Override
	public ModelPart root() 
	{
		return BTAClientUtil.MC.getEntityModels().bakeLayer(ModelLayers.PLAYER);
	}

	@Override
	public Optional<ModelPart> getAnyDescendantWithName(String p_233394_) 
	{
		return this.root().getAllParts().findFirst().map((p_233397_) ->
		{
			return this.modelMap.get(p_233394_);
		});
	}

	@Override
	public void animate(AnimationState p_233382_, AnimationDefinition p_233383_, float p_233384_)
	{
		this.animate(p_233382_, p_233383_, p_233384_, 1.0F);
	}

	@Override
	public void animate(AnimationState p_233386_, AnimationDefinition p_233387_, float p_233388_, float p_233389_) 
	{
		p_233386_.updateTime(p_233388_, p_233389_);
		p_233386_.ifStarted((p_233392_) ->
		{
			KeyframePlayerAnimations.animate(PlayerModel.class.cast(this), p_233387_, p_233392_.getAccumulatedTime(), 1.0F, ANIMATION_VECTOR_CACHE);
		});
	}
}
