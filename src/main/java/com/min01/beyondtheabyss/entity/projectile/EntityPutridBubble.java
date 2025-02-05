package com.min01.beyondtheabyss.entity.projectile;

import java.util.List;

import com.min01.beyondtheabyss.entity.AbstractOwnableEntity;
import com.min01.beyondtheabyss.entity.deepabyss.EntityMutavore;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class EntityPutridBubble extends AbstractOwnableEntity<EntityMutavore>
{
	public EntityPutridBubble(EntityType<?> p_19870_, Level p_19871_)
	{
		super(p_19870_, p_19871_);
	}
	
	@Override
	public void tick() 
	{
		super.tick();
		this.move(MoverType.SELF, this.getDeltaMovement());
		
		if(this.getOwner() != null)
		{
			List<LivingEntity> list = this.level.getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().inflate(0.5F));
			list.removeIf(t -> (t instanceof Player player && player.getAbilities().instabuild) || t == this.getOwner() || (this.getOwner() != null && t.isAlliedTo(this.getOwner())));
			list.forEach(t -> 
			{
				//TODO custom damage source
				if(t.hurt(this.damageSources().mobAttack(this.getOwner()), 0.2F))
				{
					t.addEffect(new MobEffectInstance(MobEffects.POISON, 60));
					this.playSound(SoundEvents.BUBBLE_COLUMN_BUBBLE_POP);
					this.discard();
				}
			});
		}
		
		if(this.tickCount >= 60)
		{
			this.playSound(SoundEvents.BUBBLE_COLUMN_BUBBLE_POP);
			this.discard();
		}
		
		if(this.horizontalCollision || this.verticalCollision)
		{
			this.discard();
		}
	}
}
