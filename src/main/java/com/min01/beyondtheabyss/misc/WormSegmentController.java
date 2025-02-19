package com.min01.beyondtheabyss.misc;

import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;

public class WormSegmentController 
{
	///ChatGPT ahh; (origin by deepspace mod)
    public static void tick(LivingEntity living, LivingEntity owner, float distance, float speed)
    {
        Vec3 direction = owner.getLookAngle().normalize().scale(distance);
        Vec3 targetPos = owner.position().subtract(direction);

        living.teleportTo(targetPos.x, targetPos.y, targetPos.z);

        float prevYRot = living.getYRot();
        float prevXRot = living.getXRot();

        living.setYRot(owner.getYRot());
        living.setXRot(owner.getXRot());

        float yRotDiff = Mth.wrapDegrees(owner.getYRot() - prevYRot);
        float xRotDiff = Mth.wrapDegrees(owner.getXRot() - prevXRot);

        living.setYRot(prevYRot + yRotDiff * speed);
        living.setXRot(prevXRot + xRotDiff * speed);

        living.setYBodyRot(living.getYRot());
        living.setYHeadRot(living.getYRot());

        living.yRotO = living.getYRot();
        living.xRotO = living.getXRot();
        living.yBodyRotO = living.getYRot();
        living.yHeadRotO = living.getYRot();
    }
}