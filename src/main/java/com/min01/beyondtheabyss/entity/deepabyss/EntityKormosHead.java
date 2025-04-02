package com.min01.beyondtheabyss.entity.deepabyss;

import com.min01.beyondtheabyss.entity.AbstractBTAMonster;
import com.min01.beyondtheabyss.entity.BTAEntities;
import com.min01.beyondtheabyss.multipart.EntityPartBuilder;

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

public class EntityKormosHead extends AbstractKormosPart
{
	public EntityKormosHead(EntityType<? extends Monster> p_21683_, Level p_21684_) 
	{
		super(p_21683_, p_21684_);
	}
	
    public static AttributeSupplier.Builder createAttributes()
    {
        return Mob.createMobAttributes()
    			.add(Attributes.MAX_HEALTH, 650.0F)
    			.add(Attributes.MOVEMENT_SPEED, 0.5F)
        		.add(Attributes.ATTACK_DAMAGE, 25.0F)
        		.add(Attributes.FOLLOW_RANGE, 50.0F)
        		.add(Attributes.ARMOR, 30.0F);
    }
    
    @Override
    public boolean isHead()
    {
    	return true;
    }

	@Override
	public EntityPartBuilder<? extends AbstractBTAMonster> createBuilder() 
	{
    	EntityPartBuilder<EntityKormosHead> partBuilder = new EntityPartBuilder<EntityKormosHead>(this);
		return partBuilder;
	}

	@Override
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor p_21434_, DifficultyInstance p_21435_, MobSpawnType p_21436_, SpawnGroupData p_21437_, CompoundTag p_21438_)
	{
		AbstractKormosPart prev = this;
		for(int i = 0; i < this.getChainLength(); i++)
		{
			if(i < this.getChainLength() - 1)
			{
				EntityKormosBody body = new EntityKormosBody(BTAEntities.KORMOS_BODY.get(), this.level);
				body.setPos(this.position());
				body.setOwner(prev);
				body.setIndex(i);
				body.setHead(this);
				prev = body;
				this.level.addFreshEntity(body);
			}
			else
			{
				EntityKormosTail tail = new EntityKormosTail(BTAEntities.KORMOS_TAIL.get(), this.level);
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
		return 65;
	}
	
	@Override
	public int maxTurnY()
	{
		return 4;
	}
	
	@Override
	public int getSwimRadius() 
	{
		return 30;
	}
}
