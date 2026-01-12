package com.min01.beyondtheabyss.entity.deepabyss;

import java.util.Optional;
import java.util.UUID;

import com.min01.beyondtheabyss.block.deepabyss.ChainTrapBlock;
import com.min01.beyondtheabyss.blockentity.deepabyss.ChainTrapBlockEntity;
import com.min01.beyondtheabyss.misc.KinematicChain;
import com.min01.beyondtheabyss.misc.KinematicChain.ChainSegment;
import com.min01.beyondtheabyss.util.BTAUtil;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundSetEntityMotionPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkHooks;

public class EntityChainTrapMaw extends Entity
{
	public static final EntityDataAccessor<BlockPos> TRAP_POS = SynchedEntityData.defineId(EntityChainTrapMaw.class, EntityDataSerializers.BLOCK_POS);
	public static final EntityDataAccessor<Optional<UUID>> TARGET_UUID = SynchedEntityData.defineId(EntityChainTrapMaw.class, EntityDataSerializers.OPTIONAL_UUID);
	public static final EntityDataAccessor<Integer> CHAIN_LENGTH = SynchedEntityData.defineId(EntityChainTrapMaw.class, EntityDataSerializers.INT);
	public KinematicChain chain;
	
	public EntityChainTrapMaw(EntityType<?> pEntityType, Level pLevel)
	{
		super(pEntityType, pLevel);
		this.noCulling = true;
	}

	@Override
	protected void defineSynchedData() 
	{
		this.entityData.define(TRAP_POS, BlockPos.ZERO);
		this.entityData.define(TARGET_UUID, Optional.empty());
		this.entityData.define(CHAIN_LENGTH, 5);
	}
	
	@Override
	public void tick() 
	{
		super.tick();
		BlockEntity blockEntity = this.level.getBlockEntity(this.getTrapPos());
		BlockState blockState = this.level.getBlockState(this.getTrapPos());
		if(blockState.getBlock() instanceof ChainTrapBlock)
		{
			if(!blockState.getValue(ChainTrapBlock.OPENED))
			{
				this.discard();
			}
		}
		else
		{
			this.discard();
		}
		
		if(this.chain == null)
		{
			this.chain = new KinematicChain(this, this.getChainLength(), 0.925F);
			this.chain.setAnchorPos(this.getAnchorPos());
			this.chain.speed = 0.5F;
		}
		else
		{
 			this.chain.setOldPosAndRot();
			this.chain.tickBobbit();
			
			Entity target = this.getTarget();
			if(target != null)
			{
				if(this.chain.getTarget().equals(Vec3.ZERO))
				{
					this.chain.setTarget(target.getEyePosition());
				}
				else if(this.position().distanceTo(this.chain.getTarget()) <= 2.5F)
				{
					target.setDeltaMovement(BTAUtil.getVelocityTowards(target.position(), this.position(), 0.1F));
					if(target instanceof ServerPlayer player)
					{
		    			player.connection.send(new ClientboundSetEntityMotionPacket(target));
					}
				}
			}

			if(target == null || !EntitySelector.NO_CREATIVE_OR_SPECTATOR.test(target) || this.tickCount >= 100)
			{
				this.chain.setTarget(Vec3.ZERO);
			}
			
			if(this.tickCount >= 2 && this.chain.getTarget().equals(Vec3.ZERO))
			{
				if(this.position().distanceTo(this.getAnchorPos()) <= 1.0F)
				{
					if(blockEntity instanceof ChainTrapBlockEntity trap)
					{
						trap.delay = 100;
						trap.trapped = false;
					}
					this.discard();
				}
			}

			ChainSegment segment = this.chain.getTipSegment();
			Vec3 pos = segment.getPos();
			Vec2 rot = segment.getRot();
			
			this.setPos(pos);
			this.setXRot(rot.x);
			this.setYRot(rot.y);
		}
	}

	@Override
	protected void readAdditionalSaveData(CompoundTag pCompound) 
	{
		this.setTrapPos(NbtUtils.readBlockPos(pCompound.getCompound("TrapPos")));
		if(pCompound.hasUUID("Target")) 
		{
			this.entityData.set(TARGET_UUID, Optional.of(pCompound.getUUID("Target")));
		}
	}

	@Override
	protected void addAdditionalSaveData(CompoundTag pCompound)
	{
		pCompound.put("TrapPos", NbtUtils.writeBlockPos(this.getTrapPos()));
		if(this.entityData.get(TARGET_UUID).isPresent())
		{
			pCompound.putUUID("Target", this.entityData.get(TARGET_UUID).get());
		}
	}
	
	public void setChainLength(int length)
	{
		this.entityData.set(CHAIN_LENGTH, length);
	}
	
	public int getChainLength()
	{
		return this.entityData.get(CHAIN_LENGTH);
	}
	
	public void setTarget(Entity entity)
	{
		this.entityData.set(TARGET_UUID, Optional.of(entity.getUUID()));
	}
	
	public Entity getTarget()
	{
		if(this.entityData.get(TARGET_UUID).isPresent())
		{
			return BTAUtil.getEntityByUUID(this.level, this.entityData.get(TARGET_UUID).get());
		}
		return null;
	}
	
	public Vec3 getAnchorPos()
	{
		return Vec3.atBottomCenterOf(this.getTrapPos());
	}
	
	public void setTrapPos(BlockPos pos)
	{
		this.entityData.set(TRAP_POS, pos);
	}
	
	public BlockPos getTrapPos()
	{
		return this.entityData.get(TRAP_POS);
	}

	@Override
	public Packet<ClientGamePacketListener> getAddEntityPacket()
	{
		return NetworkHooks.getEntitySpawningPacket(this);
	}
}
