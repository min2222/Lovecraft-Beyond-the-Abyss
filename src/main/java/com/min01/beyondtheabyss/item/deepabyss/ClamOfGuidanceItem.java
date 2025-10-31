package com.min01.beyondtheabyss.item.deepabyss;

import java.util.List;

import com.min01.beyondtheabyss.block.BTABlocks;
import com.min01.beyondtheabyss.entity.misc.EntityBTACameraShake;
import com.min01.beyondtheabyss.item.BTAItems;
import com.min01.beyondtheabyss.world.BTASavedData;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class ClamOfGuidanceItem extends Item
{
	public ClamOfGuidanceItem()
	{
		super(new Item.Properties().stacksTo(1).rarity(BTAItems.RARITY_DEEP_ABYSS));
	}
	
	@Override
	public boolean isFoil(ItemStack p_41453_)
	{
		return true;
	}
	
	@Override
	public InteractionResult useOn(UseOnContext p_41427_) 
	{
		Level level = p_41427_.getLevel();
		BlockPos pos = p_41427_.getClickedPos();
		Player player = p_41427_.getPlayer();
		ItemStack stack = p_41427_.getItemInHand();
		BTASavedData data = BTASavedData.get(level);
		if(data != null)
		{
			BlockPos blockPos = data.getAbyssPortalPos().offset(0, 0, 7);
			if(pos.equals(blockPos) && !data.isAbyssPortalActivated())
			{
				level.setBlockAndUpdate(pos, BTABlocks.ENERGIZED_ORIVINE.get().defaultBlockState());
				EntityBTACameraShake.cameraShake(level, Vec3.atBottomCenterOf(blockPos), 100, 0.05F, 10, 40);
				data.setAbyssPortalActivated(level.dimension(), true);
				if(!player.getAbilities().instabuild)
				{
					stack.shrink(1);
				}
				return InteractionResult.SUCCESS;
			}
		}
		return super.useOn(p_41427_);
	}
	
	@Override
	public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) 
	{
		return newStack.getItem() != this;
	}
	
	@Override
	public void appendHoverText(ItemStack p_41421_, Level p_41422_, List<Component> p_41423_, TooltipFlag p_41424_) 
	{
		if(p_41421_.getTag() != null && p_41421_.getTag().contains("PortalPos"))
		{
			BlockPos pos = NbtUtils.readBlockPos(p_41421_.getTag().getCompound("PortalPos"));
			p_41423_.add(Component.literal(pos.toShortString()).withStyle(ChatFormatting.AQUA));
		}
	}
}
