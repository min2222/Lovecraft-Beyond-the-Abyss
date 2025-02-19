package com.min01.beyondtheabyss.world.worldgen;

import net.minecraft.util.KeyDispatchDataCodec;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.SurfaceRules;

public class DeathValleyRuleSource extends SurfaceRules implements SurfaceRules.RuleSource
{
    public static final KeyDispatchDataCodec<DeathValleyRuleSource> CODEC = KeyDispatchDataCodec.of(BlockState.CODEC.xmap(DeathValleyRuleSource::new, DeathValleyRuleSource::resultState).fieldOf("result_state"));

	private BlockState resultState;
	private DeathValleySurfaceRule.StateRule rule;
	
	public DeathValleyRuleSource(BlockState state) 
	{
		this.resultState = state;
		this.rule = new DeathValleySurfaceRule.StateRule(state);
	}
	
	@Override
	public SurfaceRule apply(Context t) 
	{
		return this.rule;
	}

	@Override
	public KeyDispatchDataCodec<? extends RuleSource> codec() 
	{
		return CODEC;
	}
	
	public BlockState resultState()
	{
		return this.resultState;
	}
}
