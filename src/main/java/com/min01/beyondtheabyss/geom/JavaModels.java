package com.min01.beyondtheabyss.geom;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import com.min01.beyondtheabyss.block.model.BoneLeverModel;
import com.min01.beyondtheabyss.block.model.BoneLeverOnModel;
import com.min01.beyondtheabyss.block.model.BonePilesModel;
import com.min01.beyondtheabyss.block.model.BoneTorchModel;
import com.min01.beyondtheabyss.block.model.BoneWallTorchModel;
import com.min01.beyondtheabyss.block.model.ChainTrapModel;
import com.min01.beyondtheabyss.block.model.FallenSkeletonModel;
import com.min01.beyondtheabyss.block.model.FangSkullModel;
import com.min01.beyondtheabyss.block.model.LargeSkullModel;
import com.min01.beyondtheabyss.block.model.SittingSkeletonModel;

import net.minecraft.client.model.geom.builders.LayerDefinition;

public final class JavaModels
{
	private static final Map<String, Supplier<LayerDefinition>> LAYERS = new HashMap<>();

	static
	{
		register("fang_skull", FangSkullModel::createBodyLayer);
		register("large_skull", LargeSkullModel::createBodyLayer);
		register("bone_piles", BonePilesModel::createBodyLayer);
		register("sitting_skeleton", SittingSkeletonModel::createBodyLayer);
		register("fallen_skeleton", FallenSkeletonModel::createBodyLayer);
		register("bone_torch", BoneTorchModel::createBodyLayer);
		register("bone_wall_torch", BoneWallTorchModel::createBodyLayer);
		register("bone_lever", BoneLeverModel::createBodyLayer);
		register("bone_lever_on", BoneLeverOnModel::createBodyLayer);
		register("chain_trap", ChainTrapModel::createBodyLayer);
	}
	
	public static void register(String name, Supplier<LayerDefinition> layer)
	{
		LAYERS.put(name, layer);
	}

	public static Supplier<LayerDefinition> getLayer(String name)
	{
		return LAYERS.get(name);
	}

	public static boolean hasLayer(String name)
	{
		return LAYERS.containsKey(name);
	}
}
