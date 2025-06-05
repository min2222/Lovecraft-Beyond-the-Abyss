package com.min01.beyondtheabyss.blockentity.deepabyss;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import com.min01.beyondtheabyss.block.BTABlocks;
import com.min01.beyondtheabyss.block.deepabyss.ChainTrapBlock;
import com.min01.beyondtheabyss.entity.BTAEntities;
import com.min01.beyondtheabyss.entity.deepabyss.EntityChainTrapMaw;
import com.min01.beyondtheabyss.misc.BTATags;
import com.min01.beyondtheabyss.util.BTAUtil;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.entity.Entity;
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
	public List<UUID> chainedEntities = new ArrayList<>();
	public List<UUID> chains = new ArrayList<>();
	
	public ChainTrapBlockEntity(BlockPos p_155229_, BlockState p_155230_) 
	{
		super(BTABlocks.CHAIN_TRAP_BLOCK_ENTITY.get(), p_155229_, p_155230_);
	}
	
	public static void update(Level level, BlockPos pos, BlockState state, ChainTrapBlockEntity trap)
	{
		boolean isOpened = state.getValue(ChainTrapBlock.OPENED);
		if(isOpened)
		{
			if(trap.chains.size() < 4)
			{
				List<LivingEntity> list = level.getEntitiesOfClass(LivingEntity.class, trap.getAABB(0.5F, state).move(trap.worldPosition), EntitySelector.NO_CREATIVE_OR_SPECTATOR.and(t -> !t.getType().is(Tags.EntityTypes.BOSSES) && !t.getType().is(BTATags.BTAEntity.MINI_BOSS)));
				list.forEach(t -> 
				{
					if(!trap.chainedEntities.contains(t.getUUID()))
					{
						EntityChainTrapMaw maw = new EntityChainTrapMaw(BTAEntities.CHAIN_TRAP_MAW.get(), level);
						maw.setPos(Vec3.atCenterOf(pos));
						maw.setChainPos(Vec3.atBottomCenterOf(pos));
						maw.setTarget(t);
						maw.setChainLength(Math.max((int) Math.floor(maw.position().distanceTo(t.getEyePosition())), 5));
						maw.setTrapPos(pos);
						level.addFreshEntity(maw);
						if(!trap.chains.contains(maw.getUUID()))
						{
							trap.chains.add(maw.getUUID());
						}
						trap.chainedEntities.add(t.getUUID());
					}
				});
			}
		}
		else
		{
			for(Iterator<UUID> itr = trap.chains.iterator(); itr.hasNext();)
			{
				UUID uuid = itr.next();
				Entity entity = BTAUtil.getEntityByUUID(level, uuid);
				if(entity != null)
				{
					entity.discard();
					itr.remove();
				}
			}
			trap.chainedEntities.clear();
		}
		for(Iterator<UUID> itr = trap.chainedEntities.iterator(); itr.hasNext();)
		{
			UUID uuid = itr.next();
			Entity entity = BTAUtil.getEntityByUUID(level, uuid);
			if(entity == null)
			{
				itr.remove();
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
		ListTag list = new ListTag();
		ListTag chains = new ListTag();
		for(Iterator<UUID> itr = this.chainedEntities.iterator(); itr.hasNext();)
		{
			UUID uuid = itr.next();
			CompoundTag tag = new CompoundTag();
			tag.putUUID("ChainedEntity", uuid);
			list.add(tag);
		}
		for(Iterator<UUID> itr = this.chains.iterator(); itr.hasNext();)
		{
			UUID uuid = itr.next();
			CompoundTag tag = new CompoundTag();
			tag.putUUID("Chain", uuid);
			chains.add(tag);
		}
		nbt.put("ChainedEntities", list);
		nbt.put("Chains", chains);
	}
	
	@Override
	public void load(CompoundTag nbt)
	{
		super.load(nbt);
		ListTag list = nbt.getList("ChainedEntities", 10);
		ListTag chains = nbt.getList("Chains", 10);
		for(int i = 0; i < list.size(); ++i)
		{
			CompoundTag tag = list.getCompound(i);
			this.chainedEntities.add(tag.getUUID("ChainedEntity"));
		}
		for(int i = 0; i < chains.size(); ++i)
		{
			CompoundTag tag = chains.getCompound(i);
			this.chains.add(tag.getUUID("Chain"));
		}
	}
}
