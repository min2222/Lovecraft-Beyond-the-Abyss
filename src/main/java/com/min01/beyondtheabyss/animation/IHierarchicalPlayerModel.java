package com.min01.beyondtheabyss.animation;

import java.util.Optional;

import org.apache.commons.lang3.tuple.Pair;

import com.min01.beyondtheabyss.misc.SmoothAnimationState;

import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.LivingEntity;

public interface IHierarchicalPlayerModel<T extends LivingEntity>
{
	public ModelPart root();
	
	public Optional<Pair<ModelPart, ModelPart>> getAnyDescendantWithName(String name);
	
	public void animate(T entity, SmoothAnimationState state, AnimationDefinition definition, float ageInTicks);
	
	public void setupAnimFirstPerson(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch);
}
