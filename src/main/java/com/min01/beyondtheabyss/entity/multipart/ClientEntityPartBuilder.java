package com.min01.beyondtheabyss.entity.multipart;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

import org.jetbrains.annotations.Nullable;

import com.min01.beyondtheabyss.cerbon.EntityBounds;
import com.min01.beyondtheabyss.cerbon.IMultipart;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.fml.util.ObfuscationReflectionHelper;

public class ClientEntityPartBuilder<T extends LivingEntity & IMultipart> extends EntityPartBuilder<T> 
{
	public final HierarchicalModel<T> model;
	
	public ClientEntityPartBuilder(T entity, HierarchicalModel<T> model) 
	{
		super(entity);
		this.model = model;
	}
	
	@Override
	public void tick(float partialTick) 
	{
        this.defaultAnimation(this.model, this.entity, partialTick);
	}
	
	public EntityBounds buildHitBox()
	{
        EntityBounds.EntityBoundsBuilder builder = EntityBounds.builder();
        builder = this.addPart(builder, this.model.root(), null);
        return builder.overrideCollisionBox(this.getBoundingBox(Vec3.ZERO)).getFactory().create();
	}
	
    public EntityBounds.EntityBoundsBuilder addPart(EntityBounds.EntityBoundsBuilder builder, ModelPart part, @Nullable String parent)
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
    
    public AABB getPartSize(ModelPart part, String name)
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
    
    public void defaultAnimation(EntityModel<T> model, T entity, float partialTick)
    {
        boolean shouldSit = entity.isPassenger() && entity.getVehicle() != null && entity.getVehicle().shouldRiderSit();
        float limbSwing = !shouldSit && entity.isAlive() ? entity.animationPosition - entity.animationSpeed * (1.0F - partialTick) : 0.0F;
        float limbSwingAmount = !shouldSit && entity.isAlive() ? Mth.lerp(partialTick, entity.animationSpeedOld, entity.animationSpeed) : 0.0F;
        model.attackTime = entity.getAttackAnim(partialTick);
        model.young = entity.isBaby();
        model.riding = shouldSit;
        Vec2 headRot = this.defaultHeadRotation(entity, partialTick);
        model.prepareMobModel(entity, limbSwing, limbSwingAmount, partialTick);
        model.setupAnim(entity, limbSwing, limbSwingAmount, (float)entity.tickCount + partialTick, headRot.y, headRot.x);
    }
}
