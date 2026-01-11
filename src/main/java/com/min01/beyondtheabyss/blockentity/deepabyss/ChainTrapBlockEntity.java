package com.min01.beyondtheabyss.blockentity.deepabyss;

import java.util.List;

import com.min01.beyondtheabyss.block.BTABlocks;
import com.min01.beyondtheabyss.block.deepabyss.ChainTrapBlock;
import com.min01.beyondtheabyss.entity.BTAEntities;
import com.min01.beyondtheabyss.entity.deepabyss.EntityChainTrapMaw;
import com.min01.beyondtheabyss.misc.BTATags;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.FaceAttachedHorizontalDirectionalBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.Tags;

public class ChainTrapBlockEntity extends BlockEntity
{
	public int delay;
	public boolean trapped;
	
	public ChainTrapBlockEntity(BlockPos pPos, BlockState pBlockState)
	{
		super(BTABlocks.CHAIN_TRAP_BLOCK_ENTITY.get(), pPos, pBlockState);
	}
	
	public static void update(Level level, BlockPos pos, BlockState state, ChainTrapBlockEntity trap)
	{
		boolean isOpened = state.getValue(ChainTrapBlock.OPENED);
		trap.delay--;
		if(!isOpened)
		{
			trap.trapped = false;
		}
		if(isOpened && trap.delay <= 0 && !trap.trapped)
		{
			List<LivingEntity> list = level.getEntitiesOfClass(LivingEntity.class, trap.getAABB(1.5F, state).move(trap.worldPosition), EntitySelector.NO_CREATIVE_OR_SPECTATOR.and(t -> !t.getType().is(Tags.EntityTypes.BOSSES) && !t.getType().is(BTATags.BTAEntity.MINI_BOSSES)));
			
			Vec3 vec3 = Vec3.atCenterOf(pos);
			double dist = -1.0D;
			LivingEntity nearest = null;
			for(LivingEntity entity : list)
			{
				double dist1 = entity.distanceToSqr(vec3);
				if(dist == -1.0D || dist1 < dist) 
				{
					dist = dist1;
					nearest = entity;
				}
			}
			
			if(nearest != null)
			{
				EntityChainTrapMaw maw = new EntityChainTrapMaw(BTAEntities.CHAIN_TRAP_MAW.get(), level);
				maw.setPos(vec3);
				maw.setTarget(nearest);
				maw.setChainLength(Math.max((int) Math.floor(vec3.distanceTo(nearest.getEyePosition())), 5));
				maw.setTrapPos(pos);
				level.addFreshEntity(maw);
				trap.trapped = true;
			}
		}
	}
	
	public AABB getAABB(float size, BlockState state)
	{
		float ySize = 3.5F;
		switch(state.getValue(FaceAttachedHorizontalDirectionalBlock.FACE))
		{
		case CEILING:
			return new AABB(-size, -ySize, -size, size, 0, size);
		case FLOOR:
			return new AABB(-size, 0, -size, size, ySize, size);
		case WALL:
			switch(state.getValue(HorizontalDirectionalBlock.FACING))
			{
			case EAST:
				return new AABB(0, -size, -size, ySize, size, size);
			case NORTH:
				return new AABB(-size, -size, -ySize, size, size, 0);
			case SOUTH:
				return new AABB(-size, -size, 0, size, size, ySize);
			case WEST:
				return new AABB(-ySize, -size, -size, 0, size, size);
			default:
				return new AABB(-size, 0, -size, size, ySize, size);
			}
		default:
			return new AABB(-size, 0, -size, size, ySize, size);
		}
	}
	
	@Override
	protected void saveAdditional(CompoundTag nbt)
	{
		super.saveAdditional(nbt);
		nbt.putInt("Delay", this.delay);
		nbt.putBoolean("Trapped", this.trapped);
	}
	
	@Override
	public void load(CompoundTag nbt)
	{
		super.load(nbt);
		this.delay = nbt.getInt("Delay");
		this.trapped = nbt.getBoolean("Trapped");
	}
}
