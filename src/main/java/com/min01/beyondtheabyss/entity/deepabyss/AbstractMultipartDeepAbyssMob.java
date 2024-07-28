package com.min01.beyondtheabyss.entity.deepabyss;

import com.min01.beyondtheabyss.cerbon.CompoundOrientedBox;
import com.min01.beyondtheabyss.cerbon.EntityBounds;
import com.min01.beyondtheabyss.cerbon.IMultipart;
import com.min01.beyondtheabyss.entity.multipart.ClientEntityPartBuilder;
import com.min01.beyondtheabyss.entity.multipart.EntityPartBuilder;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public abstract class AbstractMultipartDeepAbyssMob<T extends AbstractDeepAbyssMob & IMultipart> extends AbstractDeepAbyssMob implements IMultipart
{
	public Vec3[] posArray;
	
	public EntityPartBuilder<T> partBuilder;
	
	public AbstractMultipartDeepAbyssMob(EntityType<? extends PathfinderMob> p_33002_, Level p_33003_)
	{
		super(p_33002_, p_33003_);
		this.partBuilder = this.createBuilder();
	}
	
	@Override
	public CompoundOrientedBox getCompoundBoundingBox(AABB bounds) 
	{
		return this.partBuilder.hitbox.getBox(bounds);
	}

	@Override
	public EntityBounds getBounds() 
	{
		return this.partBuilder.hitbox;
	}

	@Override
	public void onSetPos(double x, double y, double z) 
	{
		if(this.partBuilder != null)
		{
			this.partBuilder.tick(1.0F);
		}
	}
	
	public abstract EntityPartBuilder<T> createBuilder();
	
	@OnlyIn(Dist.CLIENT)
	public ClientEntityPartBuilder<T> getClientPartBuilder()
	{
		return null;
	}
}
