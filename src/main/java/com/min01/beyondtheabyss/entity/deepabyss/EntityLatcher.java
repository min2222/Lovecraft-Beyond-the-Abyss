package com.min01.beyondtheabyss.entity.deepabyss;

import com.min01.beyondtheabyss.entity.AbstractBTAMob;
import com.min01.beyondtheabyss.entity.part.AbstractBTAEntityPart;
import com.min01.beyondtheabyss.entity.part.BasicBTAEntityPart;
import com.min01.beyondtheabyss.util.BTAUtil;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;

public class EntityLatcher extends AbstractMultipartDeepAbyssMob 
{
	public BasicBTAEntityPart tail = new BasicBTAEntityPart(this, 0.5F, 0.5F);
	public BasicBTAEntityPart tail2 = new BasicBTAEntityPart(this, 0.5F, 0.5F);
	
	public BasicBTAEntityPart[] parts = { this.tail, this.tail2 };
	
	public EntityLatcher(EntityType<? extends PathfinderMob> p_21683_, Level p_21684_)
	{
		super(p_21683_, p_21684_);
		this.xpReward = 10;
	}
	
    public static AttributeSupplier.Builder createAttributes()
    {
        return Mob.createMobAttributes()
    			.add(Attributes.MAX_HEALTH, 10)
    			.add(Attributes.MOVEMENT_SPEED, 0.7F)
        		.add(Attributes.FOLLOW_RANGE, 30)
        		.add(Attributes.ARMOR, 1);
    }
    
    @Override
    public int getMaxSpawnClusterSize()
    {
    	return 1;
    }
    
	public static boolean checkLatcherSpawnRules(EntityType<? extends AbstractDeepAbyssMob> type, ServerLevelAccessor pServerLevel, MobSpawnType pMobSpawnType, BlockPos pPos, RandomSource pRandom) 
    {
		return pRandom.nextInt(40) == 0 && pPos.getY() >= -400 && pServerLevel.getFluidState(pPos.below()).is(FluidTags.WATER) && pServerLevel.getBlockState(pPos.above()).is(Blocks.WATER);
    }
    
    @Override
    public void aiStep() 
    {
    	super.aiStep();
    	BTAUtil.fishFlopping(this);
    	
    	Vec3 tail2Pos = BTAUtil.getLookPos((float) (this.getXRot() + this.getTailRot().x), this.yHeadRot, 0, -1.3F);
    	Vec3 tailPos = BTAUtil.getLookPos((float) (this.getXRot() + this.getBodyRot().x), this.yHeadRot, 0, -0.8F);
    	
        this.setPartPosition(this.tail2, tail2Pos.x, tail2Pos.y + this.getTailPos().y, tail2Pos.z);
        this.setPartPosition(this.tail, tailPos.x, tailPos.y + this.getTailPos().y, tailPos.z);
    }

	@Override
	public AbstractBTAEntityPart<AbstractBTAMob>[] getDeepAbyssEntityParts()
	{
		return this.parts;
	}
	
	@Override
	public boolean isHostile() 
	{
		return false;
	}
}
