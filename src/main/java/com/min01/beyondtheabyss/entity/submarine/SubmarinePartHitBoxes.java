package com.min01.beyondtheabyss.entity.submarine;

import com.min01.beyondtheabyss.multipart.entity.EntityBounds;
import com.min01.beyondtheabyss.multipart.entity.EntityPart;
import com.min01.beyondtheabyss.multipart.entity.MutableBox;

import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class SubmarinePartHitBoxes 
{
    private final SubmarineHatch hatchEntity;
    private final SubmarineDetector detectorEntity;
    private final AABB collisionHitbox = new AABB(Vec3.ZERO, Vec3.ZERO);
    private final String root = "root";
    private final String submarine = "submarine";
    private final String hatch = "hatch";
    private final String detector = "detector";
    
    private final EntityBounds hatchHitBoxes = EntityBounds.builder()
            .add(this.root).setBounds(0.0, 0.0, 0.0).build()
            .add(this.submarine).setBounds(0.0, 0.0, 0.0).setPivot(0, -2.5F, 0).setParent(this.root).build()
            .add(this.hatch).setBounds(1.0F, 0.75F, 1.0F).setParent(this.submarine).build()
            .overrideCollisionBox(this.collisionHitbox)
            .getFactory().create();
    
    private final EntityBounds detectorHitBoxes = EntityBounds.builder()
            .add(this.root).setBounds(0.0, 0.0, 0.0).build()
            .add(this.submarine).setBounds(0.0, 0.0, 0.0).setPivot(0, -2.5F, 0).setParent(this.root).build()
            .add(this.detector).setBounds(2.8F, 2.65F, 3.9F).setParent(this.submarine).build()
            .overrideCollisionBox(this.collisionHitbox)
            .getFactory().create();
    
    public SubmarinePartHitBoxes(SubmarineHatch hatchEntity, SubmarineDetector detectorEntity)
    {
        this.hatchEntity = hatchEntity;
        this.detectorEntity = detectorEntity;
    }

    public EntityBounds getHitbox(boolean isDetector)
    {
    	return isDetector ? this.detectorHitBoxes : this.hatchHitBoxes;
    }

    public void updatePosition(boolean isDetector)
    {
    	if(isDetector)
    	{
    		if(this.detectorEntity != null)
    		{
                EntityPart root = this.detectorHitBoxes.getPart(this.root);
                EntityPart submarine = this.detectorHitBoxes.getPart(this.submarine);
                EntityPart detector = this.detectorHitBoxes.getPart(this.detector);
                
                root.setRotation(0, -this.detectorEntity.getYRot(), 0, true);
                submarine.setRotation(this.detectorEntity.getXRot(), 0, 0, true);
                
                root.setX(this.detectorEntity.getX());
                root.setY(this.detectorEntity.getY());
                root.setZ(this.detectorEntity.getZ());
                
                this.setPartPosition(detector, 0, 3.0F, 0);
                
                MutableBox overrideBox = this.detectorHitBoxes.getOverrideBox();
                if (overrideBox != null)
                {
                    overrideBox.setBox(this.collisionHitbox.move(this.detectorEntity.position()).move(-2.75F, 0.0, -2.75F));
                }
    		}
    	}
    	else
    	{
    		if(this.hatchEntity != null)
    		{
                EntityPart root = this.hatchHitBoxes.getPart(this.root);
                EntityPart submarine = this.hatchHitBoxes.getPart(this.submarine);
                EntityPart hatch = this.hatchHitBoxes.getPart(this.hatch);
                
                root.setRotation(0, -this.hatchEntity.getYRot(), 0, true);
                submarine.setRotation(this.hatchEntity.getXRot(), 0, 0, true);
                
                root.setX(this.hatchEntity.getX());
                root.setY(this.hatchEntity.getY());
                root.setZ(this.hatchEntity.getZ());
                
                this.setPartPosition(hatch, 0, 4.6F, 0);
                
                MutableBox overrideBox = this.hatchHitBoxes.getOverrideBox();
                if (overrideBox != null)
                {
                    overrideBox.setBox(this.collisionHitbox.move(this.hatchEntity.position()).move(-2.75F, 0.0, -2.75F));
                }
    		}
    	}
    }
    
    public void setPartPosition(EntityPart part, double offsetX, double offsetY, double offsetZ) 
    {
    	part.setX(offsetX);
    	part.setY(offsetY);
    	part.setZ(offsetZ);
    }
}
