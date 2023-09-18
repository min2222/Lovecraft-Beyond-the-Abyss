package com.min01.beyondtheabyss.entity.deepabyss;

import org.jetbrains.annotations.Nullable;

import com.min01.beyondtheabyss.entity.parts.AbstractDeepAbyssEntityPart;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.level.Level;
import net.minecraftforge.entity.PartEntity;

public abstract class AbstractMultipartDeepAbyssEntity extends AbstractDeepAbyssEntity
{
	public AbstractMultipartDeepAbyssEntity(EntityType<? extends PathfinderMob> p_33002_, Level p_33003_)
	{
		super(p_33002_, p_33003_);
	}
	
	@Override
	public boolean isMultipartEntity() 
	{
		return true;
	}
	
	public abstract AbstractDeepAbyssEntityPart<?>[] getDeepAbyssEntityParts();
	
	@Override
	public @Nullable PartEntity<?>[] getParts() 
	{
		return this.getDeepAbyssEntityParts();
	}
	
    @Override
    public void aiStep() 
    {
    	super.aiStep();
    	this.refreshDimensions();
    	for(AbstractDeepAbyssEntityPart<?> parts : this.getDeepAbyssEntityParts())
    	{
    		parts.tick();
    	}
    }
	
	@Override
	public void setId(int p_20235_) 
	{
		super.setId(p_20235_);
		for (int i = 0; i < this.getDeepAbyssEntityParts().length; i++) 
		{
			this.getDeepAbyssEntityParts()[i].setId(p_20235_ + i + 1);
		}
	}
}
