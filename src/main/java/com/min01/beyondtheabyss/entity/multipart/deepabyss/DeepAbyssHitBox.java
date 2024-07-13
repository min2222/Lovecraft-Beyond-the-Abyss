package com.min01.beyondtheabyss.entity.multipart.deepabyss;

import com.min01.beyondtheabyss.entity.deepabyss.EntityGhidruth;
import com.min01.beyondtheabyss.entity.deepabyss.EntityLatcher;
import com.min01.beyondtheabyss.entity.multipart.AbstractHitBox;
import com.min01.beyondtheabyss.multipart.entity.EntityBounds;
import com.min01.beyondtheabyss.multipart.entity.EntityPart;
import com.min01.beyondtheabyss.multipart.entity.MutableBox;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class DeepAbyssHitBox
{
	public static class LatcherHitBox extends AbstractHitBox
	{
		public final EntityLatcher entity;
		
	    private final String root = "root";
	    private final String latcher = "latcher";
	    private final String tail1 = "tail1";
	    private final String tail2 = "tail2";

	    private final AABB collisionHitBox = new AABB(new Vec3(0.4F, 0.0F, 0.4F).reverse(), new Vec3(0.4F, 0.3F, 0.4F));
	    
	    private final EntityBounds hitbox = EntityBounds.builder()
	            .add(this.root).setBounds(0.0, 0.0, 0.0).build()
	            .add(this.latcher).setBounds(0.0, 0.0, 0.0).setParent(this.root).build()
	            .add(this.tail1).setBounds(0.3125F, 0.25F, 0.5F).setParent(this.latcher).build()
	            .add(this.tail2).setBounds(0.5625F, 0.1875F, 1.0F).setParent(this.latcher).build()
	            .overrideCollisionBox(this.collisionHitBox)
	            .getFactory().create();
		
		public LatcherHitBox(EntityLatcher entity) 
		{
			this.entity = entity;
		}
		
		@Override
		public EntityBounds getEntityBounds() 
		{
			return this.hitbox;
		}

		@Override
		public void updatePosition() 
		{
			super.updatePosition();
			
	        EntityPart tail1 = this.hitbox.getPart(this.tail1);
	        EntityPart tail2 = this.hitbox.getPart(this.tail2);
	        
	        this.setPartPosition(tail1, this.entity.posArray[0]);
	        this.setPartPosition(tail2, this.entity.posArray[1]);
	        
	        MutableBox overrideBox = this.hitbox.getOverrideBox();
	        if(overrideBox != null)
	        {
	            overrideBox.setBox(this.collisionHitBox.move(this.entity.position()));
	        }
		}
		
		@Override
		public Entity getEntity() 
		{
			return this.entity;
		}
		
		@Override
		public Vec3 getRotation() 
		{
			return new Vec3(this.entity.getXRot(), -this.entity.getYRot() - 180, 0);
		}
		
		@Override
		public EntityPart root() 
		{
			return this.hitbox.getPart(this.root);
		}
		
		@Override
		public EntityPart subRoot() 
		{
			return this.hitbox.getPart(this.latcher);
		}
	}
	
	public static class GhidruthHitBox extends AbstractHitBox
	{
		public final EntityGhidruth entity;
		
	    private final String root = "root";
	    private final String ghidruth = "ghidruth";
	    private final String head = "head";
	    private final String body = "body";
	    private final String tail = "tail";

	    private final AABB collisionHitBox = new AABB(new Vec3(2.6F, 0.0F, 2.6F).reverse(), new Vec3(2.6F, 4.5F, 2.6F));
	    
	    private final EntityBounds hitbox = EntityBounds.builder()
	            .add(this.root).setBounds(0.0, 0.0, 0.0).build()
	            .add(this.ghidruth).setBounds(0.0, 0.0, 0.0).setParent(this.root).build()
	            .add(this.head).setBounds(2.9375F, 3.1875F, 3.9375F).setParent(this.ghidruth).build()
	            .add(this.body).setBounds(2.9375F, 3.1875F, 5.0625F).setParent(this.ghidruth).build()
	            .add(this.tail).setBounds(1.4375F, 1.75F, 2.9375F).setParent(this.ghidruth).build()
	            .overrideCollisionBox(this.collisionHitBox)
	            .getFactory().create();
		
		public GhidruthHitBox(EntityGhidruth entity) 
		{
			this.entity = entity;
		}
		
		@Override
		public EntityBounds getEntityBounds() 
		{
			return this.hitbox;
		}

		@Override
		public void updatePosition() 
		{
			super.updatePosition();
			
	        EntityPart head = this.hitbox.getPart(this.head);
	        EntityPart body = this.hitbox.getPart(this.body);
	        EntityPart tail = this.hitbox.getPart(this.tail);
	        
	        this.setPartPosition(head, this.entity.posArray[0]);
	        this.setPartPosition(body, this.entity.posArray[1]);
	        this.setPartPosition(tail, this.entity.posArray[2]);
	        
	        MutableBox overrideBox = this.hitbox.getOverrideBox();
	        if(overrideBox != null)
	        {
	            overrideBox.setBox(this.collisionHitBox.move(this.entity.position()));
	        }
		}
		
		@Override
		public Entity getEntity() 
		{
			return this.entity;
		}
		
		@Override
		public Vec3 getRotation() 
		{
			return new Vec3(this.entity.getXRot(), -this.entity.getYRot(), 0);
		}
		
		@Override
		public EntityPart root() 
		{
			return this.hitbox.getPart(this.root);
		}
		
		@Override
		public EntityPart subRoot() 
		{
			return this.hitbox.getPart(this.ghidruth);
		}
	}
}
