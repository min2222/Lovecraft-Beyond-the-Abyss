package com.min01.beyondtheabyss.entity.deepabyss;

import com.min01.beyondtheabyss.entity.AbstractBTAMob;
import com.min01.beyondtheabyss.entity.part.AbstractBTAEntityPart;
import com.min01.beyondtheabyss.entity.part.CrabShellPart;
import com.min01.beyondtheabyss.util.BTAUtil;

import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;

public class EntityAbyssalHermitCrab extends AbstractMultipartDeepAbyssMob 
{
	public CrabShellPart shell = new CrabShellPart(this, 0.8F, 0.8F);
	public CrabShellPart shell2 = new CrabShellPart(this, 0.6F, 0.6F);
	public CrabShellPart[] parts = { this.shell, this.shell2 };
	
	public EntityAbyssalHermitCrab(EntityType<? extends PathfinderMob> p_21683_, Level p_21684_) 
	{
		super(p_21683_, p_21684_);
		this.xpReward = 25;
	}
	
    public static AttributeSupplier.Builder createAttributes()
    {
        return Mob.createMobAttributes()
    			.add(Attributes.MAX_HEALTH, 30)
    			.add(Attributes.ATTACK_DAMAGE, 5)
    			.add(Attributes.KNOCKBACK_RESISTANCE, 1)
    			.add(Attributes.MOVEMENT_SPEED, 0.35F);
    }
    
    @Override
    public int getMaxSpawnClusterSize()
    {
    	return 1;
    }
    
	public static boolean checkHermitCrabSpawnRules(EntityType<? extends AbstractDeepAbyssMob> type, ServerLevelAccessor pServerLevel, MobSpawnType pMobSpawnType, BlockPos pPos, RandomSource pRandom) 
    {
		return pRandom.nextInt(40) == 0 && pPos.getY() >= -400 && pServerLevel.getBlockState(pPos.below()).is(Blocks.STONE) && pServerLevel.getBlockState(pPos.above()).is(Blocks.WATER);
    }

    //TODO add custom crab attack goal
    @Override
    protected void registerGoals() 
    {
    	super.registerGoals();
        this.goalSelector.addGoal(4, new RandomStrollGoal(this, 0.15D));
    }
    
    @Override
    public void tick() 
    {
    	super.tick();
    	Vec3 shell2Pos = BTAUtil.getLookPos(this.getXRot(), this.getYRot(), 0, -1.0F);
    	Vec3 shellPos = BTAUtil.getLookPos(this.getXRot(), this.getYRot(), 0, -0.2F);
    	
        this.setPartPosition(this.shell2, shell2Pos.x, 0.1F, shell2Pos.z);
        this.setPartPosition(this.shell, shellPos.x, 0.5F, shellPos.z);
    }

	@Override
	public AbstractBTAEntityPart<AbstractBTAMob>[] getDeepAbyssEntityParts() 
	{
		return this.parts;
	}
	
	@Override
	public float getInsideWaterSpeed() 
	{
		return 0.25F;
	}
	
	@Override
	public boolean isNetural()
	{
		return true;
	}
	
	@Override
	public boolean isSwimable() 
	{
		return false;
	}
	
	@Override
	public boolean canBreatheOutsideWater()
	{
		return true;
	}
}
