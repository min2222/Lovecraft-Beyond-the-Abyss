package com.min01.beyondtheabyss.item.deepabyss;

import java.util.List;
import java.util.function.Consumer;

import org.jetbrains.annotations.Nullable;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.min01.beyondtheabyss.animation.PlayerAnimations;
import com.min01.beyondtheabyss.item.animation.IAnimatableItem;
import com.min01.beyondtheabyss.item.renderer.BTAItemRenderer;
import com.min01.beyondtheabyss.misc.Laser;
import com.min01.beyondtheabyss.misc.Laser.LaserHitResult;
import com.min01.beyondtheabyss.sound.BTASounds;
import com.min01.beyondtheabyss.util.BTAClientUtil;
import com.min01.beyondtheabyss.util.BTAUtil;

import net.minecraft.client.model.HumanoidModel.ArmPose;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;

public class SkeletalGunbladeItem extends SwordItem implements IAnimatableItem
{
	public SkeletalGunbladeItem(Item.Properties properties) 
	{
		super(Tiers.DIAMOND, 0, 0.0F, properties);
	}
	
	@Override
	public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand)
	{
		ItemStack stack = pPlayer.getItemInHand(pUsedHand);
		boolean isGunMode = isGunMode(stack);
		if(PlayerAnimations.getPlayerAnimationState(pPlayer) == 0)
		{
			if(pPlayer.isShiftKeyDown())
			{
				if(isGunMode)
				{
					pPlayer.playSound(BTASounds.GUNBLADE_GUN_TO_BLADE.get());
					pPlayer.getCooldowns().addCooldown(stack.getItem(), 20);
				}
				else
				{
					pPlayer.playSound(BTASounds.GUNBLADE_BLADE_TO_GUN.get());
					pPlayer.getCooldowns().addCooldown(stack.getItem(), 20);
				}
	        	setGunMode(stack, !isGunMode);
			}
			else
			{
				if(isGunMode)
				{
					pPlayer.playSound(BTASounds.GUNBLADE_CHARGE.get());
					pPlayer.startUsingItem(pUsedHand);
					PlayerAnimations.play(pPlayer, 3, this.getUseDuration(stack));
				}
			}
			return InteractionResultHolder.consume(stack);
		}
		return InteractionResultHolder.fail(stack);
	}
	
	@Override
	public void onUseTick(Level pLevel, LivingEntity pLivingEntity, ItemStack pStack, int pRemainingUseDuration) 
	{
		if(getCharge(pStack) < 3 && pRemainingUseDuration % 25 == 0)
		{
			setCharge(pStack, getCharge(pStack) + 1);
		}
	}
	
	@Override
	public boolean onLeftClickEntity(ItemStack stack, Player player, Entity entity)
	{
		return true;
	}
	
	@Override
	public boolean onEntitySwing(ItemStack stack, LivingEntity entity) 
	{
		boolean isGunMode = isGunMode(stack);
		if(!isGunMode && !PlayerAnimations.isAnimationPlaying(entity))
		{
			entity.playSound(BTASounds.GUNBLADE_SWING.get());
			PlayerAnimations.play(entity, 5, 20);
		}
		return true;
	}
	
	@Override
	public void inventoryTick(ItemStack pStack, Level pLevel, Entity pEntity, int pSlotId, boolean pIsSelected)
	{
		if(!pIsSelected && PlayerAnimations.isAnimationPlaying(pEntity))
		{
			PlayerAnimations.stop(pEntity);
		}
		if(getCharge(pStack) > 0 && (!pIsSelected || !PlayerAnimations.isAnimationPlaying(pEntity)))
		{
			this.stop(pStack, pEntity);
		}
		else if(pEntity instanceof Player player)
		{
			int state = PlayerAnimations.getPlayerAnimationState(player);
			int tick = PlayerAnimations.getPlayerAnimationTick(player);
			if(state == 4)
			{
				Laser laser = new Laser();
	        	Vec3 startPos = BTAUtil.getLookPos(new Vec2(player.getXRot(), player.getYHeadRot()), player.getEyePosition(), 0.0F, -0.25F, 0.5F);
				Vec3 lookPos = BTAUtil.getLookPos(new Vec2(player.getXRot(), player.getYHeadRot()), startPos, 0.0F, 0.0F, 50.0F);
				LaserHitResult laserHit = laser.raytrace(player.level, startPos, lookPos, 0.375F, t -> t != player && !t.isAlliedTo(player), player);
	            if(player.level.isClientSide)
	            {
	                setLaserLength(pStack, laser.getLaserLength());
	            }
	            laserHit.entities.forEach(t -> 
	            {
	            	t.hurt(player.damageSources().indirectMagic(player, player), 6.0F);
	            });
			}
			if(state == 5 && tick == 10)
			{
				float size = 1.5F;
				Vec3 lookPos = BTAUtil.getLookPos(new Vec2(player.getXRot(), player.getYHeadRot()), player.getEyePosition(), 0, 0, 1.5F);
				AABB aabb = new AABB(-size, -size, -size, size, size, size).move(lookPos);
				List<LivingEntity> list = player.level.getEntitiesOfClass(LivingEntity.class, aabb, t -> t != player && !t.isAlliedTo(player));
				list.forEach(t -> 
				{
					t.hurt(player.damageSources().mobAttack(player), 8.0F);
				});
			}
		}
	}
	
	public void stop(ItemStack stack, Entity entity)
	{
		setLaserVisible(stack, false);
		setLaserLength(stack, 0);
		setCharge(stack, 0);
		if(entity instanceof Player player)
		{
			player.getCooldowns().addCooldown(stack.getItem(), 100);
		}
	}
	
	@Override
	public void releaseUsing(ItemStack stack, Level level, LivingEntity entity, int pTimeCharged)
	{
		if(getCharge(stack) > 0)
		{
			PlayerAnimations.play(entity, 4, 46);
			setLaserVisible(stack, true);
			//FIXME ahhhh
			//BTANetwork.sendToServer(new UpdateSkeletalGunbladeItemPacket(stack, entity.getUUID()));
		}
	}
	
	public void releaseUsingServer(ItemStack stack, LivingEntity entity)
	{
		PlayerAnimations.play(entity, 4, 46);
	}

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slot, ItemStack stack) 
    {
    	return ImmutableMultimap.of();
    }
    
	@Override
	public boolean isTwoHanded() 
	{
		return true;
	}
	
	@Override
	public boolean isFirstPersonAnim(ItemStack stack, Entity entity)
	{
		return PlayerAnimations.getPlayerAnimationState(entity) != 0;
	}
	
	@Override
	public int getUseDuration(ItemStack pStack) 
	{
		if(isGunMode(pStack))
		{
			return 72000;
		}
		return 0;
	}
	
	@Override
	public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) 
	{
		return newStack.getItem() != this;
	}
	
	public static int getCharge(ItemStack stack) 
	{
		CompoundTag tag = stack.getTag();
		return tag != null ? tag.getInt("Charge") : 0;
	}
	
	public static void setCharge(ItemStack stack, int charge) 
	{
		CompoundTag tag = stack.getOrCreateTag();
		tag.putInt("Charge", charge);
	}
	
	public static boolean isGunMode(ItemStack stack) 
	{
		CompoundTag tag = stack.getTag();
		return tag != null && tag.getBoolean("GunMode");
	}
	
	public static void setGunMode(ItemStack stack, boolean isGunMode) 
	{
		CompoundTag tag = stack.getOrCreateTag();
		tag.putBoolean("GunMode", isGunMode);
	}
	
	public static float getLaserLength(ItemStack stack) 
	{
		CompoundTag tag = stack.getTag();
		return tag != null ? tag.getFloat("LaserLength") : 0.0F;
	}
	
	public static void setLaserLength(ItemStack stack, float length) 
	{
		CompoundTag tag = stack.getOrCreateTag();
		tag.putFloat("LaserLength", length);
	}
	
	public static boolean isLaserVisible(ItemStack stack) 
	{
		CompoundTag tag = stack.getTag();
		return tag != null && tag.getBoolean("LaserVisible");
	}
	
	public static void setLaserVisible(ItemStack stack, boolean LaserVisible) 
	{
		CompoundTag tag = stack.getOrCreateTag();
		tag.putBoolean("LaserVisible", LaserVisible);
	}
	
	@Override
	public void initializeClient(Consumer<IClientItemExtensions> consumer) 
	{
		consumer.accept(new IClientItemExtensions() 
		{
			@Override
			public BlockEntityWithoutLevelRenderer getCustomRenderer()
			{
				return new BTAItemRenderer(BTAClientUtil.MC.getBlockEntityRenderDispatcher(), BTAClientUtil.MC.getEntityModels());
			}
			
			@Override
			public @Nullable ArmPose getArmPose(LivingEntity entityLiving, InteractionHand hand, ItemStack itemStack) 
			{
				return ArmPose.EMPTY;
			}
		});
	}
}
