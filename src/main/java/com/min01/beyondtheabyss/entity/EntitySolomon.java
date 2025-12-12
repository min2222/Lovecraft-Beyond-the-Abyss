package com.min01.beyondtheabyss.entity;

import com.min01.beyondtheabyss.item.BTAItems;
import com.min01.beyondtheabyss.misc.BTAMobType;
import com.min01.beyondtheabyss.multipart.EntityPartBuilder;
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
import net.minecraft.world.entity.PathfinderMob;
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

public class EntitySolomon extends AbstractBTACreature implements IDialogue, ISynchedEntityData
{
	public static final EntityDataAccessor<Boolean> CAN_TALK = SynchedEntityData.defineId(EntitySolomon.class, EntityDataSerializers.BOOLEAN);
	public static final EntityDataAccessor<Boolean> IS_TALKING = SynchedEntityData.defineId(EntitySolomon.class, EntityDataSerializers.BOOLEAN);
	public static final EntityDataAccessor<Integer> CHAT_INDEX = SynchedEntityData.defineId(EntitySolomon.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<Integer> PREV_CHAT_INDEX = SynchedEntityData.defineId(EntitySolomon.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<ItemStack> KEY_ITEM = SynchedEntityData.defineId(EntitySolomon.class, EntityDataSerializers.ITEM_STACK);
	
	public Player player;
	
	public EntitySolomon(EntityType<? extends PathfinderMob> p_21683_, Level p_21684_)
	{
		super(p_21683_, p_21684_);
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
    	this.entityData.define(KEY_ITEM, BTAItems.CLAM_OF_GUIDANCE.get().getDefaultInstance());
    }

	@Override
	public EntityPartBuilder<? extends AbstractBTACreature> createBuilder()
	{
    	EntityPartBuilder<EntitySolomon> partBuilder = new EntityPartBuilder<EntitySolomon>(this);
    	return partBuilder;
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
				return super.canUse() && EntitySolomon.this.player == null;
			}
		});
	}
	
	@Override
	public void setDeltaMovement(Vec3 p_20257_)
	{
		super.setDeltaMovement(new Vec3(0, p_20257_.y, 0));
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
				BlockPos pos = data.getAbyssPortalPos();
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
	protected InteractionResult mobInteract(Player p_21472_, InteractionHand p_21473_)
	{
		if(this.canTalk() && !this.isTalking())
		{
			if(p_21472_ instanceof ServerPlayer player)
			{
				BTANetwork.CHANNEL.sendTo(new SetDialogueScreenPacket("solomon", 5, this), player.connection.connection, NetworkDirection.PLAY_TO_CLIENT);
			}
			if(!this.level.isClientSide)
			{
				this.player = p_21472_;
			}
			this.setTalking(true);
		}
		return super.mobInteract(p_21472_, p_21473_);
	}
	
	@Override
	public boolean hurt(DamageSource p_21016_, float p_21017_) 
	{
		if(!p_21016_.is(DamageTypeTags.BYPASSES_INVULNERABILITY))
		{
			return false;
		}
		return super.hurt(p_21016_, p_21017_);
	}
	
	@Override
	public void addAdditionalSaveData(CompoundTag p_21484_)
	{
		super.addAdditionalSaveData(p_21484_);
		p_21484_.putBoolean("CanTalk", this.canTalk());
		p_21484_.putBoolean("isTalking", this.isTalking());
		p_21484_.putInt("ChatIndex", this.getChatIndex());
		p_21484_.putInt("PrevChatIndex", this.getPrevChatIndex());
		p_21484_.put("KeyItem", this.getKeyItem().save(new CompoundTag()));
	}
	
	@Override
	public void readAdditionalSaveData(CompoundTag p_21450_) 
	{
		super.readAdditionalSaveData(p_21450_);
		this.setCanTalk(p_21450_.getBoolean("CanTalk"));
		this.setTalking(p_21450_.getBoolean("isTalking"));
		this.setChatIndex(p_21450_.getInt("ChatIndex"));
		this.setPrevChatIndex(p_21450_.getInt("PrevChatIndex"));
		if(p_21450_.contains("KeyItem", 10))
		{
			this.setKeyItem(ItemStack.of(p_21450_.getCompound("KeyItem")));
		}
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor p_21434_, DifficultyInstance p_21435_, MobSpawnType p_21436_, SpawnGroupData p_21437_, CompoundTag p_21438_) 
	{
		if(p_21436_ == MobSpawnType.SPAWN_EGG)
		{
			this.setCanTalk(true);
		}
		return super.finalizeSpawn(p_21434_, p_21435_, p_21436_, p_21437_, p_21438_);
	}
	
	@Override
	public void trigger(int chatIndex)
	{
		if(chatIndex == 4)
		{
			BTANetwork.sendToServer(new UpdateSynchedEntityDataPacket(1, this));
		}
	}
	
	@Override
	public void onClose(int chatIndex) 
	{
		if(chatIndex == 5)
		{
			BTANetwork.sendToServer(new UpdateSynchedEntityDataPacket(0, this));
		}
		else
		{
			BTANetwork.sendToServer(new UpdateSynchedEntityDataPacket(2, this));
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
