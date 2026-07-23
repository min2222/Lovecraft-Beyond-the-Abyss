package com.min01.beyondtheabyss.item.deepabyss;

import java.util.function.Consumer;
import java.util.function.Predicate;

import org.jetbrains.annotations.Nullable;

import com.min01.beyondtheabyss.animation.PlayerAnimations;
import com.min01.beyondtheabyss.enchantment.BTAEnchantments;
import com.min01.beyondtheabyss.entity.projectile.ToothBulletEntity;
import com.min01.beyondtheabyss.item.animation.IAnimatableItem;
import com.min01.beyondtheabyss.item.animation.ItemAnimations;
import com.min01.beyondtheabyss.item.renderer.BTAItemRenderer;
import com.min01.beyondtheabyss.misc.BTATags;
import com.min01.beyondtheabyss.util.BTAClientUtil;

import net.minecraft.client.model.HumanoidModel.ArmPose;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;

public class ToothShotgunItem extends Item implements IAnimatableItem
{
    public static final Predicate<ItemStack> AMMO = (stack) ->
    {
        return stack.is(BTATags.TEETH);
    };
    
	public ToothShotgunItem(Properties pProperties)
	{
		super(pProperties);
	}	
	
	@Override
	public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand)
	{
		ItemStack stack = pPlayer.getItemInHand(pUsedHand);
		if(PlayerAnimations.getPlayerAnimationState(pPlayer) == 0)
		{
	        ItemStack ammo = this.findAmmo(pPlayer);
			int goldenChance = stack.getEnchantmentLevel(BTAEnchantments.GOLDEN_TOOTH.get()) * 2;
			int brittle = stack.getEnchantmentLevel(BTAEnchantments.BRITTLE.get());
			int fracture = stack.getEnchantmentLevel(BTAEnchantments.FRACTURE.get());
			if(getAmmo(stack) > 0 || pPlayer.getAbilities().instabuild)
			{
	        	for(int i = 0; i < 4; i++)
	        	{
	        		ToothBulletEntity bullet = new ToothBulletEntity(pLevel, pPlayer);
	        		bullet.shootFromRotation(pPlayer, pPlayer.getXRot(), pPlayer.yHeadRot, 0.0F, 2.0F, 4.0F);
	        		if(goldenChance > 0)
	        		{
	        			bullet.setGolden(Math.random() <= goldenChance / 10.0F);
	        		}
	        		bullet.setMaxShrapnelCount(bullet.getMaxShrapnelCount() + brittle);
	        		bullet.setFracture(fracture > 0);
	        		pLevel.addFreshEntity(bullet);
	        	}
				ItemAnimations.play(pPlayer, stack, 3, 15);
				PlayerAnimations.play(pPlayer, 1, 10);
		    	pPlayer.getCooldowns().addCooldown(stack.getItem(), 15);
		    	if(!pPlayer.getAbilities().instabuild)
		    	{
		        	setAmmo(stack, getAmmo(stack) - 1);
		    	}
			}
			else if(!ammo.isEmpty())
			{
				ItemAnimations.play(pPlayer, stack, 2, 20);
		    	pPlayer.getCooldowns().addCooldown(stack.getItem(), 20);
	        	if(!pPlayer.getAbilities().instabuild)
	        	{
	                ammo.shrink(2);
	        	}
	        	setAmmo(stack, getAmmo(stack) + 2);
			}
			else
			{
				if(pLevel.random.nextBoolean())
				{
					ItemAnimations.play(pPlayer, stack, 5, 5);
			    	pPlayer.getCooldowns().addCooldown(stack.getItem(), 5);
				}
				else
				{
					ItemAnimations.play(pPlayer, stack, 4, 8);
			    	pPlayer.getCooldowns().addCooldown(stack.getItem(), 8);
				}
			}
		}
		return InteractionResultHolder.pass(stack);
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
	
    @Override
    public int getEnchantmentValue()
    {
        return 1;
    }
    
    @Override
    public boolean onEntitySwing(ItemStack stack, LivingEntity entity) 
    {
    	return true;
    }

    @Override
    public boolean isEnchantable(ItemStack stack)
    {
        return stack.getCount() == 1;
    }
    
	@Override
	public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment)
	{
		return enchantment.category == BTAEnchantments.TOOTH_SHOTGUN;
	}
	
	@Override
	public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) 
	{
		return newStack.getItem() != this;
	}
	
    public ItemStack findAmmo(Player entity) 
    {
        for(int i = 0; i < entity.getInventory().getContainerSize(); i++)
        {
            ItemStack stack = entity.getInventory().getItem(i);
            if(AMMO.test(stack))
            {
                return stack;
            }
        }
        return ItemStack.EMPTY;
    }
	
    public static int getAmmo(ItemStack stack)
    {
        CompoundTag tag = stack.getTag();
        return tag != null ? tag.getInt("Ammo") : 0;
    }

    public static void setAmmo(ItemStack stack, int ammo)
    {
        CompoundTag tag = stack.getOrCreateTag();
        tag.putInt("Ammo", ammo);
    }
    
	@Override
	public Vec3 getOffset() 
	{
		return new Vec3(0.0F, 4.0F, -5.0F);
	}
	
	@Override
	public boolean isTwoHanded() 
	{
		return true;
	}
	
	@Override
	public boolean isFirstPersonAnim(ItemStack stack, Entity entity) 
	{
		return true;
	}
}
