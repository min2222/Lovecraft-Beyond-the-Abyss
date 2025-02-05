package com.min01.beyondtheabyss.animation;

import java.util.Optional;

import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.AnimationState;

public interface IHierarchicalPlayerModel 
{
	public ModelPart root();
	
	public Optional<ModelPart> getAnyDescendantWithName(String p_233394_);
	
	public void animate(AnimationState p_233382_, AnimationDefinition p_233383_, float p_233384_);

	public void animate(AnimationState p_233386_, AnimationDefinition p_233387_, float p_233388_, float p_233389_);
}
