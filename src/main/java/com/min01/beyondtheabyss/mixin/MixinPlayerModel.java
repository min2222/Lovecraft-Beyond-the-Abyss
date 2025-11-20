package com.min01.beyondtheabyss.mixin;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.lang3.tuple.Pair;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.min01.beyondtheabyss.animation.IHierarchicalPlayerModel;
import com.min01.beyondtheabyss.animation.PlayerAnimation;
import com.min01.beyondtheabyss.item.BTAItems;
import com.min01.beyondtheabyss.item.deepabyss.SkeletalGunbladeItem;
import com.min01.beyondtheabyss.item.deepabyss.ToothShotgunItem;
import com.min01.beyondtheabyss.misc.SmoothAnimationState;
import com.min01.beyondtheabyss.util.BTAClientUtil;
import com.min01.beyondtheabyss.util.BTAUtil;

import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.LivingEntity;

@Mixin(PlayerModel.class)
public class MixinPlayerModel<T extends LivingEntity> implements IHierarchicalPlayerModel<T>
{
	private Map<String, Pair<ModelPart, ModelPart>> modelMap = new HashMap<>();
	
    @Inject(at = @At("HEAD"), method = "setupAnim", cancellable = true)
    private void setupAnim(T p_103395_, float p_103396_, float p_103397_, float p_103398_, float p_103399_, float p_103400_, CallbackInfo ci)
    {	
    	this.setupMap();
    }
    
    @Inject(at = @At("TAIL"), method = "setupAnim", cancellable = true)
    private void setupAnimTail(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, CallbackInfo ci)
    {
    	this.animate(entity, SkeletalGunbladeItem.GUNBLADE_CHARGE, PlayerAnimation.SkeletalGunbladeAnimation.CHARGE, ageInTicks);
    	this.animate(entity, SkeletalGunbladeItem.GUNBLADE_SHOOT, PlayerAnimation.SkeletalGunbladeAnimation.SHOOT_BEAM, ageInTicks);
    	this.animate(entity, SkeletalGunbladeItem.GUNBLADE_SHOOT_LIGHT, PlayerAnimation.SkeletalGunbladeAnimation.SHOOT_LIGHT, ageInTicks);
    	this.animate(entity, SkeletalGunbladeItem.GUNBLADE_SWING, PlayerAnimation.SkeletalGunbladeAnimation.SWING, ageInTicks);
    	
    	if(entity.isHolding(BTAItems.SKELETAL_GUNBLADE.get()) && BTAUtil.getPlayerAnimationState(entity) == 4)
    	{
    		
    	}
    }
    
    @Override
    public void setupAnimFirstPerson(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) 
    {
    	this.setupMap();
    	this.animate(entity, ToothShotgunItem.SHOTGUN_FIRE, PlayerAnimation.ToothShotgunAnimation.SHOTGUN_FIRE, ageInTicks);
    	this.animate(entity, ToothShotgunItem.SHOTGUN_HOLD, PlayerAnimation.ToothShotgunAnimation.SHOTGUN_HOLD, ageInTicks);
    	this.animate(entity, ToothShotgunItem.SHOTGUN_RUNNING, PlayerAnimation.ToothShotgunAnimation.SHOTGUN_RUNNING, ageInTicks);
    	this.animate(entity, SkeletalGunbladeItem.GUNBLADE_CHARGE, PlayerAnimation.SkeletalGunbladeAnimation.CHARGE, ageInTicks);
    	this.animate(entity, SkeletalGunbladeItem.GUNBLADE_SHOOT, PlayerAnimation.SkeletalGunbladeAnimation.SHOOT_BEAM, ageInTicks);
    	this.animate(entity, SkeletalGunbladeItem.GUNBLADE_SHOOT_LIGHT, PlayerAnimation.SkeletalGunbladeAnimation.SHOOT_LIGHT, ageInTicks);
    	this.animate(entity, SkeletalGunbladeItem.GUNBLADE_SWING, PlayerAnimation.SkeletalGunbladeAnimation.SWING, ageInTicks);
    }
    
	@Override
	public ModelPart root() 
	{
		return BTAClientUtil.MC.getEntityModels().bakeLayer(ModelLayers.PLAYER);
	}

	@Override
	public Optional<Pair<ModelPart, ModelPart>> getAnyDescendantWithName(String name) 
	{
		return this.root().getAllParts().findFirst().map((p_233397_) ->
		{
			return this.modelMap.get(name);
		});
	}

	@SuppressWarnings("unchecked")
	@Override
	public void animate(T entity, String name, AnimationDefinition definition, float ageInTicks)
	{
		SmoothAnimationState state = BTAUtil.getPlayerAnimationStateByName(entity, name);
		state.animatePlayer(PlayerModel.class.cast(this), definition, ageInTicks);
	}
	
	public void setupMap()
	{
    	if(this.modelMap.isEmpty())
    	{
    		this.modelMap.put("Head", Pair.of(PlayerModel.class.cast(this).head, PlayerModel.class.cast(this).hat));
    		this.modelMap.put("Body", Pair.of(PlayerModel.class.cast(this).body, PlayerModel.class.cast(this).jacket));
    		this.modelMap.put("LeftArm", Pair.of(PlayerModel.class.cast(this).leftArm, PlayerModel.class.cast(this).leftSleeve));
    		this.modelMap.put("RightArm", Pair.of(PlayerModel.class.cast(this).rightArm, PlayerModel.class.cast(this).rightSleeve));
    		this.modelMap.put("LeftLeg", Pair.of(PlayerModel.class.cast(this).leftLeg, PlayerModel.class.cast(this).leftPants));
    		this.modelMap.put("RightLeg", Pair.of(PlayerModel.class.cast(this).rightLeg, PlayerModel.class.cast(this).rightPants));
    	}
    	
    	this.modelMap.values().forEach(t ->
    	{
    		t.getLeft().resetPose();
    		t.getRight().resetPose();
    	});
	}
}
