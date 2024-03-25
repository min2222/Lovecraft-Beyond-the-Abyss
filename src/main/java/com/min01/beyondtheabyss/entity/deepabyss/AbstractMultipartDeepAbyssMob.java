package com.min01.beyondtheabyss.entity.deepabyss;

import org.jetbrains.annotations.Nullable;

import com.min01.beyondtheabyss.entity.part.BasicBTAEntityPart;

import net.minecraft.commands.arguments.EntityAnchorArgument.Anchor;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.entity.PartEntity;

public abstract class AbstractMultipartDeepAbyssMob extends AbstractDeepAbyssMob
{
	public AbstractMultipartDeepAbyssMob(EntityType<? extends PathfinderMob> p_33002_, Level p_33003_)
	{
		super(p_33002_, p_33003_);
	}
	
	@Override
	public boolean isMultipartEntity() 
	{
		return true;
	}
	
	public abstract BasicBTAEntityPart[] getDeepAbyssEntityParts();
	
	@Override
	public @Nullable PartEntity<?>[] getParts() 
	{
		return this.getDeepAbyssEntityParts();
	}
	
    @Override
    public void tick() 
    {
    	super.tick();
    	
    	if(this.getTarget() != null)
    	{
    		this.getNavigation().moveTo(this.getTarget(), this.getAttributeBaseValue(Attributes.MOVEMENT_SPEED));
    		this.lookAt(Anchor.FEET, this.getTarget().position().add(0, 0.5, 0));
    	}
    	
    	this.refreshDimensions();
    	
    	for(BasicBTAEntityPart parts : this.getDeepAbyssEntityParts())
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
    
    public void setPartPosition(BasicBTAEntityPart part, double offsetX, double offsetY, double offsetZ) 
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
