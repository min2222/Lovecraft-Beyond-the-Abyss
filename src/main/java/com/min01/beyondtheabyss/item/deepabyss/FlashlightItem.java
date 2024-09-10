package com.min01.beyondtheabyss.item.deepabyss;

import com.min01.beyondtheabyss.block.BTABlocks;
import com.min01.beyondtheabyss.util.BTAUtil;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public class FlashlightItem extends Item
{
	public static final String PREV_POS = "PrevPos";
	public static final String ON = "On";
	
	public FlashlightItem()
	{
		super(new Item.Properties().stacksTo(1));
	}
	
	@Override
	public InteractionResultHolder<ItemStack> use(Level p_41432_, Player p_41433_, InteractionHand p_41434_) 
	{
		ItemStack stack = p_41433_.getItemInHand(p_41434_);
		boolean isOn = isOn(stack);
		setOn(stack, !isOn);
		BlockPos pos = getPrevPos(stack);
		if(pos != null)
		{
			if(!p_41432_.isClientSide)
			{
				this.removePrevLight(p_41432_, pos);
			}
		}
		return InteractionResultHolder.success(stack);
	}
	
	@Override
	public boolean onDroppedByPlayer(ItemStack item, Player player) 
	{
		BlockPos pos = getPrevPos(item);
		if(!player.level.isClientSide)
		{
			if(pos != null)
			{
				this.removePrevLight(player.level, pos);
			}
		}
		return super.onDroppedByPlayer(item, player);
	}
	
	@Override
	public void inventoryTick(ItemStack p_41404_, Level p_41405_, Entity p_41406_, int p_41407_, boolean p_41408_)
	{
		if(!p_41405_.isClientSide)
		{
			boolean isOn = isOn(p_41404_);
			if(isOn)
			{
				BlockPos pos = getPrevPos(p_41404_);
				if(p_41408_ || ((LivingEntity) p_41406_).getOffhandItem() == p_41404_)
				{
		        	Vec3 lightPos = BTAUtil.getLookPos(p_41406_.getXRot(), p_41406_.getYRot(), 0, 8.0F);
		        	HitResult result = p_41405_.clip(new ClipContext(p_41406_.getEyePosition(), p_41406_.getEyePosition().add(lightPos), ClipContext.Block.OUTLINE, ClipContext.Fluid.NONE, p_41406_));
		            if(result instanceof BlockHitResult blockHit)
		            {
		                BlockPos blockPos = blockHit.getBlockPos().relative(blockHit.getDirection());
		                if(blockPos != pos)
		                {
							this.removePrevLight(p_41405_, pos);
		                	if(p_41405_.isEmptyBlock(blockPos) || p_41405_.getBlockState(blockPos).is(Blocks.WATER))
		                	{
			                	setPrevPos(p_41404_, blockPos);
			                	if(p_41405_.isEmptyBlock(blockPos))
			                	{
				                	p_41405_.setBlockAndUpdate(blockPos, BTABlocks.BTA_LIGHT.get().defaultBlockState());
			                	}
			                	else if(p_41405_.getBlockState(blockPos).is(Blocks.WATER))
			                	{
				                	p_41405_.setBlockAndUpdate(blockPos, BTABlocks.BTA_LIGHT.get().defaultBlockState().setValue(BlockStateProperties.WATERLOGGED, true));
			                	}
		                	}
		                }
		            }
				}
				else
				{
					this.removePrevLight(p_41405_, pos);
				}
			}
		}
	}
	
	public void removePrevLight(Level level, BlockPos pos)
	{
		BlockState state = level.getBlockState(pos);
    	if(state.is(BTABlocks.BTA_LIGHT.get()))
    	{
    		if(!state.getValue(BlockStateProperties.WATERLOGGED))
    		{
    			level.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
    		}
    		else
    		{
    			level.setBlockAndUpdate(pos, Blocks.WATER.defaultBlockState());
    		}
    	}
	}
	
	@Override
	public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) 
	{
		return newStack.getItem() != this;
	}
	
    public static BlockPos getPrevPos(ItemStack stack)
    {
        CompoundTag compoundtag = stack.getTag();
        return compoundtag != null ? BlockPos.of(compoundtag.getLong(PREV_POS)) : null;
    }

    public static void setPrevPos(ItemStack stack, BlockPos pos)
    {
        CompoundTag compoundtag = stack.getOrCreateTag();
        compoundtag.putLong(PREV_POS, pos.asLong());
    }
	
    public static boolean isOn(ItemStack stack)
    {
        CompoundTag compoundtag = stack.getTag();
        return compoundtag != null ? compoundtag.getBoolean(ON) : false;
    }

    public static void setOn(ItemStack stack, boolean on)
    {
        CompoundTag compoundtag = stack.getOrCreateTag();
        compoundtag.putBoolean(ON, on);
    }
}
