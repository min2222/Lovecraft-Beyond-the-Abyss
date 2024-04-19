package com.min01.beyondtheabyss.entity.deepabyss;

import org.jetbrains.annotations.Nullable;

import com.min01.beyondtheabyss.entity.part.BasicBTAEntityPart;

import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.entity.PartEntity;

public abstract class AbstractMultipartDeepAbyssMob extends AbstractDeepAbyssMob
{
	public static final EntityDataAccessor<Float> HEAD_ROT = SynchedEntityData.defineId(AbstractMultipartDeepAbyssMob.class, EntityDataSerializers.FLOAT);
	public static final EntityDataAccessor<Float> BODY_ROT = SynchedEntityData.defineId(AbstractMultipartDeepAbyssMob.class, EntityDataSerializers.FLOAT);
	public static final EntityDataAccessor<Float> TAIL_ROT = SynchedEntityData.defineId(AbstractMultipartDeepAbyssMob.class, EntityDataSerializers.FLOAT);
	
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
	protected void defineSynchedData()
	{
		super.defineSynchedData();
        this.entityData.define(HEAD_ROT, 0.0F);
        this.entityData.define(BODY_ROT, 0.0F);
        this.entityData.define(TAIL_ROT, 0.0F);
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
	
    public void setHeadRot(float rot)
    {
    	this.entityData.set(HEAD_ROT, rot);
    }
      
    public float getHeadRot() 
    {
    	return this.entityData.get(HEAD_ROT);
    }
    
    public void setBodyRot(float rot)
    {
    	this.entityData.set(BODY_ROT, rot);
    }
      
    public float getBodyRot() 
    {
    	return this.entityData.get(BODY_ROT);
    }
    
    public void setTailRot(float rot)
    {
    	this.entityData.set(TAIL_ROT, rot);
    }
      
    public float getTailRot() 
    {
    	return this.entityData.get(TAIL_ROT);
    }
	
	public abstract BasicBTAEntityPart[] getDeepAbyssEntityParts();
}
