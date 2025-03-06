package com.min01.beyondtheabyss.entity.deepabyss;

import com.min01.beyondtheabyss.misc.BTAMobType;
import com.min01.beyondtheabyss.misc.WormChain;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;

public abstract class AbstractKormosPart extends AbstractOwnableDeepAbyssMonster<AbstractKormosPart>
{
	public AbstractKormosPart(EntityType<? extends Monster> p_21683_, Level p_21684_) 
	{
		super(p_21683_, p_21684_);
	}

	@Override
	public BTAMobType getBTAMobType() 
	{
		return BTAMobType.BOSS;
	}
	
	@Override
	public float insideWaterSpeed() 
	{
		return 0.8F;
	}
	
	public boolean isHead()
	{
		return false;
	}
	
	@Override
	protected void registerGoals() 
	{
		if(this.isHead())
		{
			super.registerGoals();
		}
	}
	
	@Override
	protected void doPush(Entity p_21294_)
	{
		if(!(p_21294_ instanceof AbstractKormosPart))
		{
			super.doPush(p_21294_);
		}
	}
	
	@Override
	public void tick() 
	{
		super.tick();
		
		if(this.getOwner() != null)
		{
    		this.hurtTime = this.getOwner().hurtTime;
    		this.deathTime = this.getOwner().deathTime;
			WormChain.tick(this, this.getOwner(), this.getSegmentDistance(), 0.35F);
		}
	}
	
	public float getSegmentDistance()
	{
		return 5.0F;
	}
    
    @Override
    public boolean isInvulnerableTo(DamageSource p_20122_)
    {
    	return super.isInvulnerableTo(p_20122_) || p_20122_ == DamageSource.IN_WALL || p_20122_.isFall();
    }
}
