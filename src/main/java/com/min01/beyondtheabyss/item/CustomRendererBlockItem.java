package com.min01.beyondtheabyss.item;

import java.util.function.Consumer;
import java.util.function.Supplier;

import com.min01.beyondtheabyss.item.renderer.BTABlockEntityItemRenderer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;

public class CustomRendererBlockItem extends BlockItem
{
	private final Supplier<BlockEntity> blockEntity;
	
	public CustomRendererBlockItem(Block p_40565_, Properties p_40566_, Supplier<BlockEntity> blockEntity) 
	{
		super(p_40565_, p_40566_);
		this.blockEntity = blockEntity;
	}

	@Override
	public void initializeClient(Consumer<IClientItemExtensions> consumer) 
	{
		consumer.accept(new IClientItemExtensions()
		{
			@Override
			public BlockEntityWithoutLevelRenderer getCustomRenderer() 
			{
				return new BTABlockEntityItemRenderer(CustomRendererBlockItem.this.blockEntity.get(), Minecraft.getInstance().getBlockEntityRenderDispatcher(), Minecraft.getInstance().getEntityModels());
			}
		});
	}
}
