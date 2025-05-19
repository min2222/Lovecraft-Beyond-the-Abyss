package com.min01.beyondtheabyss.entity.deepabyss;

import com.min01.beyondtheabyss.entity.AbstractOwnableEntity;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class EntityFishBait extends AbstractOwnableEntity<Player>
{
	public EntityFishBait(EntityType<?> p_19870_, Level p_19871_)
	{
		super(p_19870_, p_19871_);
	}
	
	@Override
	public boolean isPickable()
	{
		return true;
	}
	
	@Override
	public void tick()
	{
		super.tick();
		this.move(MoverType.SELF, this.getDeltaMovement());
	}
}
