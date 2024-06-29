package com.min01.beyondtheabyss.entity.deepabyss;

import com.min01.beyondtheabyss.entity.AbstractBTAMob;
import com.min01.beyondtheabyss.entity.part.AbstractBTAEntityPart;
import com.min01.beyondtheabyss.entity.part.BasicBTAEntityPart;
import com.min01.beyondtheabyss.misc.BTAMobType;
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

public class EntityAbyssalBulbray extends AbstractMultipartDeepAbyssMob
{
	public BasicBTAEntityPart tail1 = new BasicBTAEntityPart(this, 0.8F, 0.5F);
	public BasicBTAEntityPart tail2 = new BasicBTAEntityPart(this, 0.8F, 0.5F);
	public BasicBTAEntityPart tail3 = new BasicBTAEntityPart(this, 0.8F, 0.5F);
	public BasicBTAEntityPart tail4 = new BasicBTAEntityPart(this, 0.8F, 0.5F);
	public BasicBTAEntityPart finRight = new BasicBTAEntityPart(this, 1.4F, 0.1F);
	public BasicBTAEntityPart finRight2 = new BasicBTAEntityPart(this, 0.9F, 0.1F);
	public BasicBTAEntityPart finLeft = new BasicBTAEntityPart(this, 1.4F, 0.1F);
	public BasicBTAEntityPart finLeft2 = new BasicBTAEntityPart(this, 0.9F, 0.1F);
	public BasicBTAEntityPart[] parts = { this.finRight, this.finLeft, this.finRight2, this.finLeft2, this.tail1, this.tail2, this.tail3, this.tail4 };
	
	public EntityAbyssalBulbray(EntityType<? extends PathfinderMob> p_21683_, Level p_21684_)
	{
		super(p_21683_, p_21684_);
		this.xpReward = this.random.nextInt(4);
	}
	
    public static AttributeSupplier.Builder createAttributes()
    {
        return Mob.createMobAttributes()
    			.add(Attributes.MAX_HEALTH, 80)
    			.add(Attributes.MOVEMENT_SPEED, 0.8F)
        		.add(Attributes.ARMOR, 2);
    }
    
    @Override
    public int getMaxSpawnClusterSize()
    {
    	return 1;
    }
    
	public static boolean checkBulbraySpawnRules(EntityType<? extends AbstractDeepAbyssMob> type, ServerLevelAccessor pServerLevel, MobSpawnType pMobSpawnType, BlockPos pPos, RandomSource pRandom) 
    {
		return pRandom.nextInt(70) == 0 && pPos.getY() >= -400 && pServerLevel.getFluidState(pPos.below()).is(FluidTags.WATER) && pServerLevel.getBlockState(pPos.above()).is(Blocks.WATER);
    }
    
    @Override
    public void tick() 
    {
    	super.tick();

    	Vec3 tail4Pos = BTAUtil.getLookPos(this.getXRot(), this.yHeadRot, 0, -4.5F);
    	Vec3 tail3Pos = BTAUtil.getLookPos(this.getXRot(), this.yHeadRot, 0, -3.5F);
    	Vec3 tail2Pos = BTAUtil.getLookPos(this.getXRot(), this.yHeadRot, 0, -2.5F);
    	Vec3 tailPos = BTAUtil.getLookPos(this.getXRot(), this.yHeadRot, 0, -1.5F);
    	
    	Vec3 finRightPos = BTAUtil.getLookPos(this.getXRot(), this.yHeadRot - 90, 0, 1.8F);
    	Vec3 finRight2Pos1 = BTAUtil.getLookPos(this.getXRot(), this.yHeadRot - 90, 0, 1.0F);
    	Vec3 finRight2Pos2 = BTAUtil.getLookPos(this.getXRot(), this.yHeadRot - 180, 0, 1.2F);
    	Vec3 finRight2Pos = finRight2Pos1.add(finRight2Pos2);
    	Vec3 finLeftPos = BTAUtil.getLookPos(this.getXRot(), this.yHeadRot + 90, 0, 1.8F);
    	Vec3 finLeft2Pos1 = BTAUtil.getLookPos(this.getXRot(), this.yHeadRot + 90, 0, 1.0F);
    	Vec3 finLeft2Pos2 = BTAUtil.getLookPos(this.getXRot(), this.yHeadRot + 180, 0, 1.2F);
    	Vec3 finLeft2Pos = finLeft2Pos1.add(finLeft2Pos2);

        this.setPartPosition(this.tail4, this.getX() + tail4Pos.x, this.getY() + tail4Pos.y, this.getZ() + tail4Pos.z);
        this.setPartPosition(this.tail3, this.getX() + tail3Pos.x, this.getY() + tail3Pos.y, this.getZ() + tail3Pos.z);
        this.setPartPosition(this.tail2, this.getX() + tail2Pos.x, this.getY() + tail2Pos.y, this.getZ() + tail2Pos.z);
        this.setPartPosition(this.tail1, this.getX() + tailPos.x, this.getY() + tailPos.y, this.getZ() + tailPos.z);

        this.setPartPosition(this.finLeft2, this.getX() + finLeft2Pos.x, this.getY() + finLeft2Pos.y + 0.4F, this.getZ() + finLeft2Pos.z);
        this.setPartPosition(this.finLeft, this.getX() + finLeftPos.x, this.getY() + finLeftPos.y + 0.4F, this.getZ() + finLeftPos.z);
        this.setPartPosition(this.finRight2, this.getX() + finRight2Pos.x, this.getY() + finRight2Pos.y + 0.4F, this.getZ() + finRight2Pos.z);
        this.setPartPosition(this.finRight, this.getX() + finRightPos.x, this.getY() + finRightPos.y + 0.4F, this.getZ() + finRightPos.z);
    }

	@Override
	public AbstractBTAEntityPart<AbstractBTAMob>[] getDeepAbyssEntityParts()
	{
		return this.parts;
	}
	
	@Override
	public BTAMobType getBTAMobType()
	{
		return BTAMobType.PASSIVE;
	}
}
