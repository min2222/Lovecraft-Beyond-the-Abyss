package com.min01.beyondtheabyss.entity.multipart;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

import org.jetbrains.annotations.Nullable;

import com.min01.beyondtheabyss.cerbon.EntityBounds;
import com.min01.beyondtheabyss.cerbon.EntityPart;
import com.min01.beyondtheabyss.cerbon.IMultipart;
import com.min01.beyondtheabyss.cerbon.MutableBox;
import com.min01.beyondtheabyss.cerbon.QuaternionD;
import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;

import net.minecraft.ChatFormatting;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.PlayerModelPart;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.fml.util.ObfuscationReflectionHelper;

public class EntityPartBuilder<T extends LivingEntity & IMultipart>
{
	public final T entity;
	public final HierarchicalModel<T> model;
	public final EntityBounds hitbox;
	public static final String ROOT = "root";
	public static final float SCALE = 0.0625F;
	public final Map<String, Vec3> partOffset = new HashMap<>();
	public final Map<String, String> parts = new HashMap<>();
	
	public EntityPartBuilder(T entity, HierarchicalModel<T> model) 
	{
		this.entity = entity;
		this.model = model;
		this.hitbox = this.buildHitBox();
		this.tick(1.0F);
	}
	
	public void tick(float partialTick)
	{
        double posX = Mth.lerp((double)partialTick, this.entity.xOld, this.entity.getX());
        double posY = Mth.lerp((double)partialTick, this.entity.yOld, this.entity.getY());
        double posZ = Mth.lerp((double)partialTick, this.entity.zOld, this.entity.getZ());
        
        EntityPart root = this.hitbox.getPart(ROOT);
        
        root.setOffX(posX);
        root.setOffY(posY + this.getOffset());
        root.setOffZ(posZ);
        
        this.model.root().getAllParts().forEach((part) -> 
        {
            String name = this.getModelPartName(this.model.root(), part);
            EntityPart entityPart = this.hitbox.getPart(name);
            Vec3 partPos = (new Vec3((double)(-part.x), (double)(-part.y), (double)part.z)).scale(SCALE * this.getRenderScale());
            Vec3 pivot = Vec3.ZERO;
            
            if(this.partOffset.containsKey(name))
            {
                Vec3 offset = this.partOffset.get(name);
                pivot = offset;
                partPos = partPos.add(offset);
            }

            if(this.parts.containsKey(name)) 
            {
                String parent = this.parts.get(name);
                if(this.partOffset.containsKey(parent)) 
                {
                    partPos = partPos.subtract(this.partOffset.get(parent));
                }
            }

            entityPart.setX(partPos.x);
            entityPart.setY(partPos.y);
            entityPart.setZ(partPos.z);
            entityPart.setPivotX(pivot.x);
            entityPart.setPivotY(pivot.y);
            entityPart.setPivotZ(pivot.z);
            Quaternion rot = new Quaternion(0, 0, 0, 1);
            rot.mul(Vector3f.ZP.rotation(part.zRot));
            rot.mul(Vector3f.YP.rotation(-part.yRot));
            rot.mul(Vector3f.XP.rotation(-part.xRot));
            entityPart.setRotation(new QuaternionD((double)rot.i(), (double)rot.j(), (double)rot.k(), (double)rot.r()));
        });
        
        QuaternionD rotation = this.defaultEntityRotation(this.entity, partialTick);
        root.rotate(rotation);
        
        MutableBox overrideBox = this.hitbox.getOverrideBox();
        if(overrideBox != null) 
        {
        	overrideBox.setBox(this.getBoundingBox(new Vec3(posX, posY, posZ)));
        }
	}
	
	public EntityBounds buildHitBox()
	{
        EntityBounds.EntityBoundsBuilder builder = EntityBounds.builder();
        builder = this.addPart(builder, this.model.root(), null);
        return builder.overrideCollisionBox(this.getBoundingBox(Vec3.ZERO)).getFactory().create();
	}
	
    protected EntityBounds.EntityBoundsBuilder addPart(EntityBounds.EntityBoundsBuilder builder, ModelPart part, @Nullable String parent)
    {
        String name = this.getModelPartName(this.model.root(), part);
        EntityBounds.EntityPartInfoBuilder partInfo = builder.add(name);
        if(parent != null)
        {
            partInfo.setParent(parent);
            this.parts.put(name, parent);
        }

        partInfo.setBounds(this.getPartSize(part, name));
        partInfo.build();
        builder = partInfo.build();

        ModelPart child;
		Map<String, ModelPart> children = ObfuscationReflectionHelper.getPrivateValue(ModelPart.class, part, "f_104213_");
        for(Iterator<ModelPart> iterator = children.values().iterator(); iterator.hasNext(); builder = this.addPart(builder, child, name)) 
        {
            child = iterator.next();
        }

        return builder;
    }
    
    protected AABB getPartSize(ModelPart part, String name)
    {
        AABB box = null;
    	List<ModelPart.Cube> cubes = ObfuscationReflectionHelper.getPrivateValue(ModelPart.class, part, "f_104212_");
        Iterator<ModelPart.Cube> iterator = cubes.iterator();

        while(iterator.hasNext())
        {
            ModelPart.Cube cube = iterator.next();
            Vec3 min = (new Vec3((double)cube.minX, (double)cube.minY, (double)cube.minZ)).scale(SCALE * this.getRenderScale());
            Vec3 max = (new Vec3((double)cube.maxX, (double)cube.maxY, (double)cube.maxZ)).scale(SCALE * this.getRenderScale());
            AABB cubeBox = new AABB(min, max);
            if(box == null)
            {
                box = cubeBox;
            }
            else
            {
                box = box.minmax(cubeBox);
            }
        }

        AABB box1 = new AABB(Vec3.ZERO, Vec3.ZERO);
        if(box == null) 
        {
            return box1;
        }
        else 
        {
            Vec3 offset = box.getCenter().multiply(-1.0, -1.0, 1.0);
            this.partOffset.put(name, offset);
            return box1.inflate(box.getXsize() / 2.0, box.getYsize() / 2.0, box.getZsize() / 2.0);
        }
    }
    
    public float defaultBodyRotation(LivingEntity entity, float partialTick) 
    {
        boolean shouldSit = entity.isPassenger() && entity.getVehicle() != null && entity.getVehicle().shouldRiderSit();
        float bodyRot = Mth.rotLerp(partialTick, entity.yBodyRotO, entity.yBodyRot);
        float headRot = Mth.rotLerp(partialTick, entity.yHeadRotO, entity.yHeadRot);
        if(shouldSit) 
        {
            if(entity.getVehicle() instanceof LivingEntity vehicle) 
            {
                bodyRot = Mth.rotLerp(partialTick, vehicle.yBodyRotO, vehicle.yBodyRot);
                float delta = Mth.wrapDegrees(headRot - bodyRot);
                delta = Mth.clamp(delta, -85.0F, 85.0F);
                bodyRot = headRot - delta;
                if(delta * delta > 2500.0F)
                {
                    bodyRot += delta * 0.2F;
                }
            }
        }

        return bodyRot;
    }
    
    public QuaternionD defaultEntityRotation(LivingEntity entity, float partialTick)
    {
    	Quaternion rotation = new Quaternion(0, 0, 0, 1);
        float bodyRot = this.defaultBodyRotation(entity, partialTick);
        if(entity.isFullyFrozen()) 
        {
        	bodyRot = (float)((double)bodyRot + Math.cos((double)entity.tickCount * 3.25) * Math.PI * 0.4000000059604645);
        }

        if(!entity.hasPose(Pose.SLEEPING)) 
        {
            rotation.mul(Vector3f.YP.rotationDegrees(180.0F - bodyRot));
        }

        if(entity.deathTime > 0)
        {
            float progress = ((float)entity.deathTime + partialTick - 1.0F) / 20.0F * 1.6F;
            progress = Mth.sqrt(progress);
            if(progress > 1.0F) 
            {
                progress = 1.0F;
            }

            rotation.mul(Vector3f.ZP.rotationDegrees(progress * 90.0F));
        }
        else if(entity.isAutoSpinAttack()) 
        {
        	rotation.mul(Vector3f.XP.rotationDegrees(-90.0F - entity.getXRot()));
            rotation.mul(Vector3f.YP.rotationDegrees(((float)entity.tickCount + partialTick) * -75.0F));
        }
        else if(entity.hasPose(Pose.SLEEPING)) 
        {
            Direction direction = entity.getBedOrientation();
            float sleepRot = direction != null ? sleepDirectionToRotation(direction) : bodyRot;
            rotation.mul(Vector3f.YP.rotationDegrees(sleepRot));
            rotation.mul(Vector3f.ZP.rotationDegrees(90.0F));
            rotation.mul(Vector3f.YP.rotationDegrees(270.0F));
        }
        else if(isEntityUpsideDown(entity)) 
        {
            rotation.mul(Vector3f.ZP.rotationDegrees(180.0F));
        }

        return new QuaternionD((double)rotation.i(), (double)rotation.j(), (double)rotation.k(), (double)rotation.r());
    }
    
    private static float sleepDirectionToRotation(Direction direction) 
    {
        switch(direction)
        {
           case SOUTH:
              return 90.0F;
           case WEST:
              return 0.0F;
           case NORTH:
              return 270.0F;
           case EAST:
              return 180.0F;
           default:
              return 0.0F;
        }
    }
    
    public static boolean isEntityUpsideDown(LivingEntity entity) 
    {
        if(entity instanceof Player || entity.hasCustomName())
        {
           String s = ChatFormatting.stripFormatting(entity.getName().getString());
           if("Dinnerbone".equals(s) || "Grumm".equals(s)) 
           {
              return !(entity instanceof Player) || ((Player)entity).isModelPartShown(PlayerModelPart.CAPE);
           }
        }

        return false;
    }
    
    public String getModelPartName(ModelPart root, ModelPart target) 
    {
        AtomicReference<String> name = new AtomicReference<>(ROOT);
        root.getAllParts().filter(part -> 
        {
    		Map<String, ModelPart> children = ObfuscationReflectionHelper.getPrivateValue(ModelPart.class, part, "f_104213_");
            return children.containsValue(target);
        }).findFirst().ifPresent(part -> 
        {
    		Map<String, ModelPart> children = ObfuscationReflectionHelper.getPrivateValue(ModelPart.class, part, "f_104213_");
            Iterator<Map.Entry<String, ModelPart>> iterator = children.entrySet().iterator();

            while(iterator.hasNext())
            {
                Map.Entry<String, ModelPart> entry = iterator.next();
                if(entry.getValue() == target) 
                {
                    name.set(entry.getKey());
                    break;
                }
            }
        });
        return name.get();
    }
	
	public float getRenderScale()
	{
		return 1.0F;
	}
	
	public float getOffset()
	{
		return 1.5F;
	}
	
    public AABB getBoundingBox(Vec3 pos) 
    {
    	return this.entity.getDimensions(this.entity.getPose()).makeBoundingBox(pos);
    }
}
