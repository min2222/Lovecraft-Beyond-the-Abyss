package com.min01.beyondtheabyss.entity.deepabyss;

import com.min01.solomonlib.multipart.EntityPartBuilder;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class EntityForneusBody extends AbstractForneusPart
{
	public EntityForneusBody(EntityType<? extends AbstractForneusPart> pEntityType, Level pLevel)
	{
		super(pEntityType, pLevel);
	}

	@Override
	public EntityPartBuilder<? extends AbstractForneusPart> createBuilder() 
	{
    	EntityPartBuilder<EntityForneusBody> partBuilder = new EntityPartBuilder<EntityForneusBody>(this)
    	{
    		@Override
    		public Vec3 getOffset()
    		{
    			return new Vec3(0.0F, 2.25F, 0.0F);
    		}
    		
    		@Override
    		public float getRenderScale() 
    		{
    			return 1.5F;
    		}
    	};
		return partBuilder;
	}
}
