package com.min01.beyondtheabyss.entity;

import javax.annotation.Nullable;

import com.min01.beyondtheabyss.item.BTAItems;
import com.min01.beyondtheabyss.misc.BTAMobType;
import com.min01.beyondtheabyss.misc.BTAResourceKeys;
import com.min01.beyondtheabyss.network.BTANetwork;
import com.min01.beyondtheabyss.network.SetDialogueScreenPacket;
import com.min01.beyondtheabyss.network.UpdateSynchedEntityDataPacket;
import com.min01.beyondtheabyss.world.BTASavedData;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkDirection;

public class MysteriousGuyEntity extends AbstractBTACreature implements IDialogue, ISynchedEntityData
{
	public static final EntityDataAccessor<Boolean> CAN_TALK = SynchedEntityData.defineId(MysteriousGuyEntity.class, EntityDataSerializers.BOOLEAN);
	public static final EntityDataAccessor<Boolean> IS_TALKING = SynchedEntityData.defineId(MysteriousGuyEntity.class, EntityDataSerializers.BOOLEAN);
	public static final EntityDataAccessor<Integer> CHAT_INDEX = SynchedEntityData.defineId(MysteriousGuyEntity.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<Integer> PREV_CHAT_INDEX = SynchedEntityData.defineId(MysteriousGuyEntity.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<ItemStack> KEY_ITEM = SynchedEntityData.defineId(MysteriousGuyEntity.class, EntityDataSerializers.ITEM_STACK);
	
	public Player player;
	
	public MysteriousGuyEntity(EntityType<? extends AbstractAnimatableCreature> pEntityType, Level pLevel)
	{
		super(pEntityType, pLevel);
	}
	
    public static AttributeSupplier.Builder createAttributes()
    {
        return Mob.createMobAttributes()
    			.add(Attributes.MAX_HEALTH, 20.0F)
    			.add(Attributes.MOVEMENT_SPEED, 0.0F);
    }
    
    @Override
    protected void defineSynchedData()
    {
    	super.defineSynchedData();
    	this.entityData.define(CAN_TALK, false);
    	this.entityData.define(IS_TALKING, false);
    	this.entityData.define(CHAT_INDEX, 0);
    	this.entityData.define(PREV_CHAT_INDEX, 0);
    	this.entityData.define(KEY_ITEM, ItemStack.EMPTY);
    }

	@Override
	public BTAMobType getBTAMobType()
	{
		return BTAMobType.MISC;
	}
	
	@Override
	protected void registerGoals() 
	{
		this.goalSelector.addGoal(0, new FloatGoal(this));
		this.goalSelector.addGoal(0, new LookAtPlayerGoal(this, Player.class, 6.0F));
		this.goalSelector.addGoal(0, new RandomLookAroundGoal(this)
		{
			@Override
			public boolean canUse()
			{
				return super.canUse() && MysteriousGuyEntity.this.player == null;
			}
		});
	}
	
	@Override
	public void setDeltaMovement(Vec3 pDeltaMovement)
	{
		super.setDeltaMovement(new Vec3(0, pDeltaMovement.y, 0));
	}
	
	@Override
	public void tick() 
	{
		super.tick();
		MinecraftServer server = this.getServer();
		if(server != null)
		{
			BTASavedData data = BTASavedData.get(server.getLevel(Level.OVERWORLD));
			if(data.isDragonKilled())
			{
				if(!this.canTalk())
				{
					this.setCanTalk(true);
				}
			}
			else
			{
				this.setCanTalk(false);
			}
			if(!this.getKeyItem().is(BTAItems.CLAM_OF_GUIDANCE.get()))
			{
				ItemStack stack = BTAItems.CLAM_OF_GUIDANCE.get().getDefaultInstance();
				BlockPos pos = data.getStructurePos(BTAResourceKeys.BTAStructures.DEEP_ABYSS_PORTAL);
				if(!pos.equals(BlockPos.ZERO))
				{
					//TODO temp mechanic;
					stack.getOrCreateTag().put("PortalPos", NbtUtils.writeBlockPos(pos));
					this.setKeyItem(stack);
				}
			}
		}
		
		if(this.player != null)
		{
			this.getLookControl().setLookAt(this.player, 100.0F, 100.0F);
		}
	}
	
	@Override
	protected InteractionResult mobInteract(Player pPlayer, InteractionHand pHand)
	{
		if(this.canTalk() && !this.isTalking())
		{
			if(pPlayer instanceof ServerPlayer player)
			{
				BTANetwork.CHANNEL.sendTo(new SetDialogueScreenPacket("chat", 5, this.getUUID()), player.connection.connection, NetworkDirection.PLAY_TO_CLIENT);
			}
			if(!this.level.isClientSide)
			{
				this.player = pPlayer;
			}
			this.setTalking(true);
		}
		return super.mobInteract(pPlayer, pHand);
	}
	
	@Override
	public boolean hurt(DamageSource pSource, float pAmount) 
	{
		if(!pSource.is(DamageTypeTags.BYPASSES_INVULNERABILITY))
		{
			return false;
		}
		return super.hurt(pSource, pAmount);
	}
	
	@Override
	public void addAdditionalSaveData(CompoundTag pCompound)
	{
		super.addAdditionalSaveData(pCompound);
		pCompound.putBoolean("CanTalk", this.canTalk());
		pCompound.putBoolean("isTalking", this.isTalking());
		pCompound.putInt("ChatIndex", this.getChatIndex());
		pCompound.putInt("PrevChatIndex", this.getPrevChatIndex());
		pCompound.put("KeyItem", this.getKeyItem().save(new CompoundTag()));
	}
	
	@Override
	public void readAdditionalSaveData(CompoundTag pCompound) 
	{
		super.readAdditionalSaveData(pCompound);
		this.setCanTalk(pCompound.getBoolean("CanTalk"));
		this.setTalking(pCompound.getBoolean("isTalking"));
		this.setChatIndex(pCompound.getInt("ChatIndex"));
		this.setPrevChatIndex(pCompound.getInt("PrevChatIndex"));
		if(pCompound.contains("KeyItem", 10))
		{
			this.setKeyItem(ItemStack.of(pCompound.getCompound("KeyItem")));
		}
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor pLevel, DifficultyInstance pDifficulty, MobSpawnType pReason, @Nullable SpawnGroupData pSpawnData, @Nullable CompoundTag pDataTag) 
	{
		if(pReason == MobSpawnType.SPAWN_EGG)
		{
			this.setCanTalk(true);
		}
		return super.finalizeSpawn(pLevel, pDifficulty, pReason, pSpawnData, pDataTag);
	}
	
	@Override
	public void trigger(int chatIndex)
	{
		if(chatIndex == 4)
		{
			BTANetwork.sendToServer(new UpdateSynchedEntityDataPacket(1, this.getUUID()));
		}
	}
	
	@Override
	public void onClose(int chatIndex) 
	{
		if(chatIndex == 5)
		{
			BTANetwork.sendToServer(new UpdateSynchedEntityDataPacket(0, this.getUUID()));
		}
		else
		{
			BTANetwork.sendToServer(new UpdateSynchedEntityDataPacket(2, this.getUUID()));
		}
	}
	
	@Override
	public void onHandle(int id) 
	{
		if(id == 0)
		{
			this.setCanTalk(false);
		}
		if(id == 1)
		{
			if(this.player != null)
			{
				this.player.getInventory().add(this.getKeyItem());
				this.player = null;
			}
		}
		if(id == 2)
		{
			this.setTalking(false);
		}
	}
	
	public void setCanTalk(boolean value)
	{
		this.entityData.set(CAN_TALK, value);
	}
	
	public boolean canTalk()
	{
		return this.entityData.get(CAN_TALK);
	}
	
	public void setTalking(boolean value)
	{
		this.entityData.set(IS_TALKING, value);
	}
	
	public boolean isTalking()
	{
		return this.entityData.get(IS_TALKING);
	}
	
	@Override
	public void setChatIndex(int chatIndex)
	{
		this.entityData.set(CHAT_INDEX, chatIndex);
	}
	
	@Override
	public int getChatIndex() 
	{
		return this.entityData.get(CHAT_INDEX);
	}
	
	@Override
	public void setPrevChatIndex(int chatIndex)
	{
		this.entityData.set(PREV_CHAT_INDEX, chatIndex);
	}
	
	@Override
	public int getPrevChatIndex() 
	{
		return this.entityData.get(PREV_CHAT_INDEX);
	}
	
	public void setKeyItem(ItemStack stack)
	{
		this.entityData.set(KEY_ITEM, stack);
	}
	
	public ItemStack getKeyItem()
	{
		return this.entityData.get(KEY_ITEM);
	}
}
