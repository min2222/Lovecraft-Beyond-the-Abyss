package com.min01.beyondtheabyss.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.min01.beyondtheabyss.misc.BTATags;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.entity.Entity;

@Mixin(ServerLevel.class)
public class MixinServerLevel
{
	@Inject(at = @At("HEAD"), method = "lambda$tick$6", cancellable = true)
	private void tick(ProfilerFiller profilerfiller, Entity p_184065_, CallbackInfo ci)
	{
		ServerLevel level = ServerLevel.class.cast(this);
		if(p_184065_.getType().is(BTATags.BTAEntity.FAR_RANGE_TICKING))
		{
			ci.cancel();
			if(!p_184065_.isRemoved())
			{
				if(this.shouldDiscardEntity(p_184065_))
				{
					p_184065_.discard();
				}
				else
				{
					profilerfiller.push("checkDespawn");
					p_184065_.checkDespawn();
					profilerfiller.pop();
					Entity entity = p_184065_.getVehicle();
					if(entity != null)
					{
						if(!entity.isRemoved() && entity.hasPassenger(p_184065_))
						{
							return;
						}
						p_184065_.stopRiding();
					}
					profilerfiller.push("tick");
					if(!p_184065_.isRemoved() && !(p_184065_ instanceof net.minecraftforge.entity.PartEntity))
					{
						level.guardEntityTick(level::tickNonPassenger, p_184065_);
					}
					profilerfiller.pop();
				}
			}
		}
	}
	
	@Shadow
	private boolean shouldDiscardEntity(Entity p_143343_)
	{
		throw new IllegalStateException();
	}
}
