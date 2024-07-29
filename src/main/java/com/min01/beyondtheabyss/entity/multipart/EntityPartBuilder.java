package com.min01.beyondtheabyss.entity.multipart;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.min01.beyondtheabyss.cerbon.EntityBounds;
import com.min01.beyondtheabyss.cerbon.EntityPart;
import com.min01.beyondtheabyss.cerbon.IMultipart;
import com.min01.beyondtheabyss.cerbon.MutableBox;
import com.min01.beyondtheabyss.cerbon.QuaternionD;
import com.min01.beyondtheabyss.entity.deepabyss.AbstractMultipartDeepAbyssMob;
import com.min01.beyondtheabyss.network.BTANetwork;
import com.min01.beyondtheabyss.network.MultiPartBuildPacket;
import com.min01.beyondtheabyss.network.MultiPartUpdatePacket;
import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;

import net.minecraft.ChatFormatting;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.PlayerModelPart;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.util.ObfuscationReflectionHelper;

public class EntityPartBuilder<T extends LivingEntity & IMultipart>
{
	public static final String ROOT = "root";
	public static final float SCALE = 0.0625F;
	public final T entity;
	public EntityBounds hitbox = EntityBounds.builder()
	        .add(ROOT).setBounds(0.0, 0.0, 0.0).build()
	        .overrideCollisionBox(new AABB(Vec3.ZERO, new Vec3(1, 1, 1)))
	        .getFactory().create();
	public final Map<String, Vec3> partOffset = new HashMap<>();
	public final Map<String, String> parts = new HashMap<>();
	public final List<Part> allParts = new ArrayList<>();
	
	public EntityPartBuilder(T entity) 
	{
		this(entity, false);
	}

	public EntityPartBuilder(T entity, boolean isClient) 
	{
		this.entity = entity;
		if(this.entity.level.isClientSide && !isClient)
		{
			this.buildClientHitBox();
			BTANetwork.sendToServer(new MultiPartBuildPacket(this.entity));
		}
	}
	
	@OnlyIn(Dist.CLIENT)
	public void buildClientHitBox()
	{
    	ClientEntityPartBuilder<?> clientBuilder = ((AbstractMultipartDeepAbyssMob<?>) this.entity).getClientPartBuilder();
    	this.hitbox = clientBuilder.buildHitBox();
    	this.parts.putAll(clientBuilder.parts);
    	this.partOffset.putAll(clientBuilder.partOffset);
	}
	
	public void tick(float partialTick)
	{	
        double posX = Mth.lerp((double)partialTick, this.entity.xOld, this.entity.getX());
        double posY = Mth.lerp((double)partialTick, this.entity.yOld, this.entity.getY());
        double posZ = Mth.lerp((double)partialTick, this.entity.zOld, this.entity.getZ());
        
		if(this.entity.level.isClientSide)
		{
			this.clientTick();
			BTANetwork.sendToServer(new MultiPartUpdatePacket(this.entity));
		}
        
        EntityPart root = this.hitbox.getPart(ROOT);
        
        Vec3 renderOffset = this.getOffset();
        //FIXME
        float waterOffset = this.isInWater() && !this.isInWater(this.entity) ? -0.5F : 0.0F;
        root.setOffX(posX + renderOffset.x + waterOffset);
        root.setOffY(posY + renderOffset.y);
        root.setOffZ(posZ + renderOffset.z);
        
        this.allParts.forEach((part) -> 
        {
        	String name = part.name;
        	EntityPart entityPart = part.part;
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
            Quaternion rotation = new Quaternion(0, 0, 0, 1);
            rotation.mul(Vector3f.ZP.rotation(part.zRot));
            rotation.mul(Vector3f.YP.rotation(-part.yRot));
            rotation.mul(Vector3f.XP.rotation(-part.xRot));
            entityPart.setRotation(new QuaternionD((double)rotation.i(), (double)rotation.j(), (double)rotation.k(), (double)rotation.r()));
        });
        
        QuaternionD rotation = this.defaultEntityRotation(this.entity, partialTick);
        root.rotate(rotation);
        
        MutableBox overrideBox = this.hitbox.getOverrideBox();
        if(overrideBox != null) 
        {
        	overrideBox.setBox(this.getBoundingBox(new Vec3(posX, posY, posZ)));
        }
	}
	
	@OnlyIn(Dist.CLIENT)
	public void clientTick()
	{
    	ClientEntityPartBuilder<?> clientBuilder = ((AbstractMultipartDeepAbyssMob<?>) this.entity).getClientPartBuilder();
    	clientBuilder.tick(1.0F);
    	clientBuilder.model.root().getAllParts().forEach(part -> 
    	{
            String name = clientBuilder.getModelPartName(clientBuilder.model.root(), part);
            EntityPart entityPart = this.hitbox.getPart(name);
            if(entityPart != null)
            {
            	this.allParts.add(new Part(name, entityPart, part.x, part.y, part.z, part.xRot, part.yRot, part.zRot));
            }
    	});
	}

    public Vec2 defaultHeadRotation(LivingEntity entity, float partialTick)
    {
        boolean shouldSit = entity.isPassenger() && entity.getVehicle() != null && entity.getVehicle().shouldRiderSit();
        float headPitch = Mth.lerp(partialTick, entity.xRotO, entity.getXRot());
        float headRot = Mth.rotLerp(partialTick, entity.yHeadRotO, entity.yHeadRot);
        float bodyRot = Mth.rotLerp(partialTick, entity.yBodyRotO, entity.yBodyRot);
        float realHeadRot = headRot - bodyRot;
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

                realHeadRot = headRot - bodyRot;
            }
        }

        if(this.isEntityUpsideDown(entity)) 
        {
            headPitch *= -1.0F;
            realHeadRot *= -1.0F;
        }

        return new Vec2(headPitch, realHeadRot);
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
            float sleepRot = direction != null ? this.sleepDirectionToRotation(direction) : bodyRot;
            rotation.mul(Vector3f.YP.rotationDegrees(sleepRot));
            rotation.mul(Vector3f.ZP.rotationDegrees(90.0F));
            rotation.mul(Vector3f.YP.rotationDegrees(270.0F));
        }
        else if(this.isEntityUpsideDown(entity)) 
        {
        	rotation.mul(Vector3f.ZP.rotationDegrees(180.0F));
        }
        
        if(this.isInWater() && !this.isInWater(entity))
        {
        	rotation.mul(Vector3f.ZP.rotationDegrees(90.0F));
        }

        return new QuaternionD((double)rotation.i(), (double)rotation.j(), (double)rotation.k(), (double)rotation.r());
    }
    
    public float sleepDirectionToRotation(Direction direction) 
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
    
    public boolean isEntityUpsideDown(LivingEntity entity) 
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
	
	public float getRenderScale()
	{
		return 1.0F;
	}
	
	public Vec3 getOffset()
	{
		return new Vec3(0.0F, 1.5F, 0.0F);
	}
	
	public boolean isInWater(Entity entity)
	{
		boolean wasTouchingWater = ObfuscationReflectionHelper.getPrivateValue(Entity.class, this.entity, "f_19798_");
		return wasTouchingWater;
	}
	
	public boolean isInWater()
	{
		return false;
	}
	
    public AABB getBoundingBox(Vec3 pos) 
    {
    	return this.entity.getDimensions(this.entity.getPose()).makeBoundingBox(pos);
    }
    
    public static class Part
    {
    	public String name;
    	
    	public EntityPart part;
    	
    	public float x;
    	public float y;
    	public float z;
    	
    	public float xRot;
    	public float yRot;
    	public float zRot;
    	
    	public Part(String name, EntityPart part, float x, float y, float z, float xRot, float yRot, float zRot)
    	{
    		this.name = name;
    		this.part = part;
    		this.x = x;
    		this.y = y;
    		this.z = z;
    		this.xRot = xRot;
    		this.yRot = yRot;
    		this.zRot = zRot;
    	}
    }
}
