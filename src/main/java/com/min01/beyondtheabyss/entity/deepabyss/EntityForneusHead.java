package com.min01.beyondtheabyss.entity.deepabyss;

import com.min01.beyondtheabyss.entity.AbstractBTAMonster;
import com.min01.beyondtheabyss.entity.BTAEntities;
import com.min01.beyondtheabyss.misc.KinematicChain;
import com.min01.beyondtheabyss.multipart.EntityPartBuilder;
import com.min01.beyondtheabyss.util.BTAUtil;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.Vec3;

public class EntityForneusHead extends AbstractForneusPart
{
	public KinematicChain chain;
	
	public EntityForneusHead(EntityType<? extends Monster> p_21683_, Level p_21684_)
	{
		super(p_21683_, p_21684_);
	}
	
    public static AttributeSupplier.Builder createAttributes()
    {
        return Mob.createMobAttributes()
    			.add(Attributes.MAX_HEALTH, 1000.0F)
    			.add(Attributes.MOVEMENT_SPEED, 0.85F)
        		.add(Attributes.FOLLOW_RANGE, 200.0F)
        		.add(Attributes.ARMOR, 20.0F);
    }
    
    @Override
    public boolean isHead()
    {
    	return true;
    }

	@Override
	public EntityPartBuilder<? extends AbstractBTAMonster> createBuilder()
	{
    	EntityPartBuilder<EntityForneusHead> partBuilder = new EntityPartBuilder<EntityForneusHead>(this)
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
	
	@Override
	public void tick()
	{
		super.tick();
		
		if(this.chain == null)
		{
			this.chain = new KinematicChain(this, this.getChainLength() + 1, this.getSegmentDistance(0));
		}
		else
		{
	        Vec3 lookPos = BTAUtil.getLookPos(this.getRotationVector(), this.position(), 0.0F, 0.0F, -6.25F);
	    	this.chain.setOldPosAndRot();
	    	this.chain.tick();
	    	this.chain.setTarget(lookPos);
		}
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor p_21434_, DifficultyInstance p_21435_, MobSpawnType p_21436_, SpawnGroupData p_21437_, CompoundTag p_21438_)
	{
		AbstractForneusPart prev = this;
		for(int i = 0; i < this.getChainLength(); i++)
		{
			if(i < this.getChainLength() - 1)
			{
				EntityForneusBody body = new EntityForneusBody(BTAEntities.FORNEUS_BODY.get(), this.level);
				body.setPos(this.position());
				body.setOwner(prev);
				body.setIndex(i);
				body.setHead(this);
				prev = body;
				this.level.addFreshEntity(body);
			}
			else
			{
				EntityForneusTail tail = new EntityForneusTail(BTAEntities.FORNEUS_TAIL.get(), this.level);
				tail.setPos(this.position());
				tail.setOwner(prev);
				tail.setIndex(i);
				tail.setHead(this);
				this.level.addFreshEntity(tail);
			}
		}
		return super.finalizeSpawn(p_21434_, p_21435_, p_21436_, p_21437_, p_21438_);
	}
	
	@Override
	public int maxTurnX() 
	{
		return !this.hasTarget() ? 55 : 75;
	}
	
	@Override
	public int maxTurnY() 
	{
		return !this.hasTarget() ? 3 : 5;
	}
	
	@Override
	public int getSwimRadius()
	{
		return 150;
	}
}
