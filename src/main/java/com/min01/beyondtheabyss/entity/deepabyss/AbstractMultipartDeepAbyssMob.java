package com.min01.beyondtheabyss.entity.deepabyss;

import org.jetbrains.annotations.Nullable;

import com.min01.beyondtheabyss.entity.AbstractBTAMob;
import com.min01.beyondtheabyss.entity.part.AbstractBTAEntityPart;
import com.min01.beyondtheabyss.misc.BTAEntityDataSerializers;

import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.entity.PartEntity;

public abstract class AbstractMultipartDeepAbyssMob extends AbstractDeepAbyssMob
{
	public static final EntityDataAccessor<Vec3> HEAD_ROT = SynchedEntityData.defineId(AbstractMultipartDeepAbyssMob.class, BTAEntityDataSerializers.VEC3.get());
	public static final EntityDataAccessor<Vec3> BODY_ROT = SynchedEntityData.defineId(AbstractMultipartDeepAbyssMob.class, BTAEntityDataSerializers.VEC3.get());
	public static final EntityDataAccessor<Vec3> TAIL_ROT = SynchedEntityData.defineId(AbstractMultipartDeepAbyssMob.class, BTAEntityDataSerializers.VEC3.get());
	
	public static final EntityDataAccessor<Vec3> HEAD_POS = SynchedEntityData.defineId(AbstractMultipartDeepAbyssMob.class, BTAEntityDataSerializers.VEC3.get());
	public static final EntityDataAccessor<Vec3> BODY_POS = SynchedEntityData.defineId(AbstractMultipartDeepAbyssMob.class, BTAEntityDataSerializers.VEC3.get());
	public static final EntityDataAccessor<Vec3> TAIL_POS = SynchedEntityData.defineId(AbstractMultipartDeepAbyssMob.class, BTAEntityDataSerializers.VEC3.get());
	
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
        this.entityData.define(HEAD_ROT, Vec3.ZERO);
        this.entityData.define(BODY_ROT, Vec3.ZERO);
        this.entityData.define(TAIL_ROT, Vec3.ZERO);
        this.entityData.define(HEAD_POS, Vec3.ZERO);
        this.entityData.define(BODY_POS, Vec3.ZERO);
        this.entityData.define(TAIL_POS, Vec3.ZERO);
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
    
    public void setPartPosition(AbstractBTAEntityPart<AbstractBTAMob> part, double offsetX, double offsetY, double offsetZ) 
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
	
    public void setHeadPos(Vec3 pos)
    {
    	this.entityData.set(HEAD_POS, pos);
    }
      
    public Vec3 getHeadPos() 
    {
    	return this.entityData.get(HEAD_POS);
    }
    
    public void setBodyPos(Vec3 pos)
    {
    	this.entityData.set(HEAD_POS, pos);
    }
      
    public Vec3 getBodyPos() 
    {
    	return this.entityData.get(HEAD_POS);
    }
    
    public void setTailPos(Vec3 pos)
    {
    	this.entityData.set(HEAD_POS, pos);
    }
      
    public Vec3 getTailPos() 
    {
    	return this.entityData.get(HEAD_POS);
    }
	
    public void setHeadRot(Vec3 rot)
    {
    	this.entityData.set(HEAD_ROT, rot);
    }
      
    public Vec3 getHeadRot() 
    {
    	return this.entityData.get(HEAD_ROT);
    }
    
    public void setBodyRot(Vec3 rot)
    {
    	this.entityData.set(BODY_ROT, rot);
    }
      
    public Vec3 getBodyRot() 
    {
    	return this.entityData.get(BODY_ROT);
    }
    
    public void setTailRot(Vec3 rot)
    {
    	this.entityData.set(TAIL_ROT, rot);
    }
      
    public Vec3 getTailRot() 
    {
    	return this.entityData.get(TAIL_ROT);
    }
	
	public abstract AbstractBTAEntityPart<AbstractBTAMob>[] getDeepAbyssEntityParts();
}
