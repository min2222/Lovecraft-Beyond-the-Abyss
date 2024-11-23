package com.min01.beyondtheabyss.entity.deepabyss;

import java.util.ArrayList;
import java.util.List;

import com.min01.beyondtheabyss.entity.AbstractBTAMonster;
import com.min01.beyondtheabyss.entity.BTAEntities;
import com.min01.beyondtheabyss.entity.multipart.EntityPartBuilder;
import com.min01.beyondtheabyss.misc.BTAMobType;

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

public class EntityMutavore extends AbstractDeepAbyssMonster
{
	public final List<EntityMutavoreTentacle> tentacles = new ArrayList<>();
	
	public EntityMutavore(EntityType<? extends Monster> p_21683_, Level p_21684_)
	{
		super(p_21683_, p_21684_);
		this.posArray = new Vec3[5];
	}
	
    public static AttributeSupplier.Builder createAttributes()
    {
        return Mob.createMobAttributes()
    			.add(Attributes.MAX_HEALTH, 150.0F)
    			.add(Attributes.MOVEMENT_SPEED, 0.65F)
        		.add(Attributes.FOLLOW_RANGE, 50.0F)
        		.add(Attributes.ARMOR, 8.0F);
    }

	@Override
	public EntityPartBuilder<? extends AbstractBTAMonster> createBuilder()
	{
		EntityPartBuilder<EntityMutavore> partBuilder = new EntityPartBuilder<EntityMutavore>(this);
		return partBuilder;
	}

	@Override
	public BTAMobType getBTAMobType() 
	{
		return BTAMobType.HOSTILE;
	}
	
	@Override
	public void tick() 
	{
		super.tick();
	}
	
	@Override
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor p_21434_, DifficultyInstance p_21435_, MobSpawnType p_21436_, SpawnGroupData p_21437_, CompoundTag p_21438_)
	{
		for(int i = 0; i < 5; i++)
		{
			EntityMutavoreTentacle tentacle1 = new EntityMutavoreTentacle(BTAEntities.MUTAVORE_TENTACLE.get(), this.level);
			tentacle1.setPos(this.position());
			tentacle1.setIndex(i);
			tentacle1.setBody(this);
			this.tentacles.add(tentacle1);
			this.level.addFreshEntity(tentacle1);
			
			EntityMutavoreTentacle tentacle2 = new EntityMutavoreTentacle(BTAEntities.MUTAVORE_TENTACLE.get(), this.level);
			tentacle2.setPos(this.position());
			tentacle2.setBody(this);
			tentacle2.setOwner(tentacle1);
			this.tentacles.add(tentacle2);
			this.level.addFreshEntity(tentacle2);
			
			EntityMutavoreTentacle tentacle3 = new EntityMutavoreTentacle(BTAEntities.MUTAVORE_TENTACLE.get(), this.level);
			tentacle3.setPos(this.position());
			tentacle3.setBody(this);
			tentacle3.setOwner(tentacle2);
			tentacle3.setEdge(true);
			this.tentacles.add(tentacle3);
			this.level.addFreshEntity(tentacle3);
		}
		return super.finalizeSpawn(p_21434_, p_21435_, p_21436_, p_21437_, p_21438_);
	}
}
