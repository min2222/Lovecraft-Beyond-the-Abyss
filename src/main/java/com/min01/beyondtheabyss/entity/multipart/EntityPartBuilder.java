package com.min01.beyondtheabyss.entity.multipart;

import java.util.Map;

import com.min01.beyondtheabyss.cerbon.EntityBounds;
import com.min01.beyondtheabyss.cerbon.EntityPart;
import com.min01.beyondtheabyss.cerbon.IMultipart;
import com.min01.beyondtheabyss.cerbon.MutableBox;
import com.min01.beyondtheabyss.cerbon.QuaternionD;
import com.mojang.math.Quaternion;

import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.util.ObfuscationReflectionHelper;

@OnlyIn(Dist.CLIENT)
public class EntityPartBuilder<T extends LivingEntity & IMultipart>
{
	public final T entity;
	public final HierarchicalModel<T> model;
	private final EntityBounds hitbox;
	public static final String ROOT = "root";
	
	public EntityPartBuilder(T entity, HierarchicalModel<T> model) 
	{
		this.entity = entity;
		this.model = model;
		this.hitbox = this.buildHitBox();
	}
	
	public void tick()
	{
		Map<String, ModelPart> children = ObfuscationReflectionHelper.getPrivateValue(ModelPart.class, this.model.root(), "f_104213_");
		EntityPart root = this.hitbox.getPart(ROOT);
        root.setRotation(this.entity.getXRot(), -this.entity.getYRot(), 0, true);
		root.setX(this.entity.getX());
		root.setY(this.entity.getY());
		root.setZ(this.entity.getZ());
		
		for(Map.Entry<String, ModelPart> entry : children.entrySet())
		{
			String name = entry.getKey();
			ModelPart part = entry.getValue();
			EntityPart entityPart = this.hitbox.getPart(name);
			Vec3 pos = new Vec3(-part.x, -part.y, part.z).scale(0.0625D);
			entityPart.setX(pos.x);
			entityPart.setY(pos.y);
			entityPart.setZ(pos.z);
            Quaternion rotation = new Quaternion(part.zRot, -part.yRot, -part.xRot, true);
            entityPart.setRotation(new QuaternionD((double)rotation.i(), (double)rotation.j(), (double)rotation.k(), (double)rotation.r()));
		}
		
        MutableBox overrideBox = this.hitbox.getOverrideBox();
        if(overrideBox != null)
        {
            overrideBox.setBox(this.entity.getBoundingBox().move(this.entity.position()));
        }
	}
	
	public EntityBounds buildHitBox()
	{
		EntityBounds.EntityBoundsBuilder builder = EntityBounds.builder().add(ROOT).setBounds(0.0F, 0.0F, 0.0F).build();
		builder = this.addPart(builder, this.model.root());
		return builder.overrideCollisionBox(this.entity.getBoundingBox()).getFactory().create();
	}
	
	public EntityBounds.EntityBoundsBuilder addPart(EntityBounds.EntityBoundsBuilder builder, ModelPart root)
	{
		Map<String, ModelPart> children = ObfuscationReflectionHelper.getPrivateValue(ModelPart.class, root, "f_104213_");
		for(Map.Entry<String, ModelPart> entry : children.entrySet())
		{
			String name = entry.getKey();
			ModelPart part = entry.getValue();
			builder.add(name).setBounds(part.xScale, part.yScale, part.zScale).setParent(ROOT).build();
		}
		return builder;
	}
}
