package com.min01.beyondtheabyss.entity.deepabyss;

import org.jetbrains.annotations.Nullable;

import com.min01.beyondtheabyss.entity.parts.BasicAbyssEntityPart;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
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
	
	public abstract BasicAbyssEntityPart[] getDeepAbyssEntityParts();
	
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
    	for(BasicAbyssEntityPart parts : this.getDeepAbyssEntityParts())
    	{
    		parts.tick();
    	}
    	
        Vec3[] avector3d = new Vec3[this.getDeepAbyssEntityParts().length];
        
        for(int j = 0; j < this.getDeepAbyssEntityParts().length; j++)
        {
        	avector3d[j] = new Vec3(this.getDeepAbyssEntityParts()[j].getX(), this.getDeepAbyssEntityParts()[j].getY(), this.getDeepAbyssEntityParts()[j].getZ());
        }
        
        for(int l = 0; l < this.getDeepAbyssEntityParts().length; l++) 
        {
        	this.getDeepAbyssEntityParts()[l].xo = avector3d[l].x;
        	this.getDeepAbyssEntityParts()[l].yo = avector3d[l].y;
        	this.getDeepAbyssEntityParts()[l].zo = avector3d[l].z;
        	this.getDeepAbyssEntityParts()[l].xOld = avector3d[l].x;
        	this.getDeepAbyssEntityParts()[l].yOld = avector3d[l].y;
        	this.getDeepAbyssEntityParts()[l].zOld = avector3d[l].z;
        }
    }
    
    public void setPartPosition(BasicAbyssEntityPart part, double offsetX, double offsetY, double offsetZ) 
    {
    	part.setPos(this.getX() + offsetX, this.getY() + offsetY, this.getZ() + offsetZ);
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
