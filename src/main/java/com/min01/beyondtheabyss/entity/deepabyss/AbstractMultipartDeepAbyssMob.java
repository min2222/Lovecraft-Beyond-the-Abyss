package com.min01.beyondtheabyss.entity.deepabyss;

import org.jetbrains.annotations.Nullable;

import com.min01.beyondtheabyss.entity.AbstractBTAMob;
import com.min01.beyondtheabyss.entity.part.AbstractBTAEntityPart;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.entity.PartEntity;

public abstract class AbstractMultipartDeepAbyssMob extends AbstractDeepAbyssMob
{
	public Vec3[] posArray;
	
	public AbstractMultipartDeepAbyssMob(EntityType<? extends PathfinderMob> p_33002_, Level p_33003_)
	{
		super(p_33002_, p_33003_);
	}
	
	@Override
	public boolean isMultipartEntity() 
	{
		return true;
	}
	
	@Override 
	@Nullable
	public PartEntity<?>[] getParts() 
	{
		return this.getDeepAbyssEntityParts();
	}
	
    @Override
    public void tick() 
    {
    	super.tick();
    	this.refreshDimensions();
    	
    	for(AbstractBTAEntityPart<AbstractBTAMob> parts : this.getDeepAbyssEntityParts())
    	{
    		parts.tick();
    	}
    	
        Vec3[] vec3 = new Vec3[this.getDeepAbyssEntityParts().length];
        
        for(int i = 0; i < this.getDeepAbyssEntityParts().length; i++)
        {
        	vec3[i] = new Vec3(this.getDeepAbyssEntityParts()[i].getX(), this.getDeepAbyssEntityParts()[i].getY(), this.getDeepAbyssEntityParts()[i].getZ());
        	
        	this.getDeepAbyssEntityParts()[i].xo = vec3[i].x;
        	this.getDeepAbyssEntityParts()[i].yo = vec3[i].y;
        	this.getDeepAbyssEntityParts()[i].zo = vec3[i].z;
        	this.getDeepAbyssEntityParts()[i].xOld = vec3[i].x;
        	this.getDeepAbyssEntityParts()[i].yOld = vec3[i].y;
        	this.getDeepAbyssEntityParts()[i].zOld = vec3[i].z;
        }
    }
    
    public void setPartPosition(AbstractBTAEntityPart<AbstractBTAMob> part, double x, double y, double z) 
    {
    	this.setPartPosition(part, new Vec3(x, y, z));
    }
    
    public void setPartPosition(AbstractBTAEntityPart<AbstractBTAMob> part, Vec3 pos) 
    {
    	if(pos != null)
    	{
        	part.setPos(pos);
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
	
	public abstract AbstractBTAEntityPart<AbstractBTAMob>[] getDeepAbyssEntityParts();
}
