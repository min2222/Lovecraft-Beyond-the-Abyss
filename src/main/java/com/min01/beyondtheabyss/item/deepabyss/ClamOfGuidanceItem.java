package com.min01.beyondtheabyss.item.deepabyss;

import java.util.List;

import javax.annotation.Nullable;

import com.min01.beyondtheabyss.block.BTABlocks;
import com.min01.beyondtheabyss.entity.EntityBTACameraShake;
import com.min01.beyondtheabyss.item.BTAItems;
import com.min01.beyondtheabyss.misc.BTAResourceKeys.BTAStructures;
import com.min01.beyondtheabyss.util.BTAUtil;
import com.min01.beyondtheabyss.world.BTAPortalTracker;
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
	public boolean isFoil(ItemStack pStack)
	{
		return true;
	}
	
	@Override
	public InteractionResult useOn(UseOnContext pContext) 
	{
		Level level = pContext.getLevel();
		BlockPos pos = pContext.getClickedPos();
		Player player = pContext.getPlayer();
		ItemStack stack = pContext.getItemInHand();
		BTASavedData data = BTASavedData.get(level);
		if(data != null)
		{
			BlockPos blockPos = data.getStructurePos(BTAStructures.DEEP_ABYSS_PORTAL).offset(0, 0, 7);
    		int y = BTAUtil.getSpecificGroundPos(level, blockPos.getX(), blockPos.getY() + 100, blockPos.getZ(), BTABlocks.ORIVINE.get()).getY();
			blockPos = BlockPos.containing(blockPos.getX(), y - 1, blockPos.getZ());
			if(pos.equals(blockPos) && !data.isPortalActivated(BTAPortalTracker.DEEP_ABYSS_PORTAL))
			{
				level.setBlockAndUpdate(pos, BTABlocks.ENERGIZED_ORIVINE.get().defaultBlockState());
				EntityBTACameraShake.cameraShake(level, Vec3.atBottomCenterOf(blockPos), 100, 0.05F, 10, 40);
				data.setPortalActivated(BTAPortalTracker.DEEP_ABYSS_PORTAL, true);
				if(!player.getAbilities().instabuild)
				{
					stack.shrink(1);
				}
				return InteractionResult.SUCCESS;
			}
		}
		return super.useOn(pContext);
	}
	
	@Override
	public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) 
	{
		return newStack.getItem() != this;
	}
	
	@Override
	public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) 
	{
		if(pStack.getTag() != null && pStack.getTag().contains("PortalPos"))
		{
			BlockPos pos = NbtUtils.readBlockPos(pStack.getTag().getCompound("PortalPos"));
			pTooltipComponents.add(Component.literal(pos.toShortString()).withStyle(ChatFormatting.AQUA));
		}
	}
}
