package com.min01.beyondtheabyss.world.carver;

import java.util.function.Function;

import com.mojang.serialization.Codec;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.chunk.CarvingMask;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.levelgen.Aquifer;
import net.minecraft.world.level.levelgen.carver.CanyonCarverConfiguration;
import net.minecraft.world.level.levelgen.carver.CarvingContext;
import net.minecraft.world.level.levelgen.carver.WorldCarver;

public class TrenchCarver extends WorldCarver<CanyonCarverConfiguration> 
{
	public TrenchCarver(Codec<CanyonCarverConfiguration> p_64711_) 
	{
		super(p_64711_);
	}

	@Override
	public boolean isStartChunk(CanyonCarverConfiguration p_224797_, RandomSource p_224798_) 
	{
		return p_224798_.nextFloat() <= p_224797_.probability;
	}

	@Override
	public boolean carve(CarvingContext p_224813_, CanyonCarverConfiguration p_224814_, ChunkAccess p_224815_, Function<BlockPos, Holder<Biome>> p_224816_, RandomSource p_224817_, Aquifer p_224818_, ChunkPos p_224819_, CarvingMask p_224820_)
	{
		int i = (this.getRange() * 2 - 1) * 16;
		double d0 = (double) p_224819_.getBlockX(p_224817_.nextInt(16));
		int j = p_224814_.y.sample(p_224817_, p_224813_);
		double d1 = (double) p_224819_.getBlockZ(p_224817_.nextInt(16));
		float f = p_224817_.nextFloat() * ((float) Math.PI * 2F);
		float f1 = p_224814_.verticalRotation.sample(p_224817_);
		double d2 = (double) p_224814_.yScale.sample(p_224817_);
		float f2 = p_224814_.shape.thickness.sample(p_224817_);
		int k = (int) ((float) i * p_224814_.shape.distanceFactor.sample(p_224817_));
		this.doCarve(p_224813_, p_224814_, p_224815_, p_224816_, p_224817_.nextLong(), p_224818_, d0, (double) j, d1, f2, f, f1, 0, k, d2, p_224820_);
		return true;
	}

	//ChatGPT ahh;
	private void doCarve(CarvingContext context, CanyonCarverConfiguration config, ChunkAccess chunk, Function<BlockPos, Holder<Biome>> biomeGetter, long seed, Aquifer aquifer, double x, double y, double z, float thickness, float horizontalAngle, float verticalAngle, int startStep, int totalSteps, double yScale, CarvingMask mask)
	{
	    RandomSource random = RandomSource.create(seed);
	    float[] widthFactors = this.initWidthFactors(context, config, random);

	    float horizontalWiggleAmplitude = 0.02F;
	    float horizontalWiggleSpeed = 0.02F;
	    float verticalWiggleAmplitude = 0.005F;

	    float meanderPhase = random.nextFloat() * 1000F;

	    for(int i = startStep; i < totalSteps; ++i)
	    {
	        float meanderOffset = Mth.sin(meanderPhase + i * horizontalWiggleSpeed) * horizontalWiggleAmplitude;
	        horizontalAngle += meanderOffset;

	        verticalAngle += (random.nextFloat() - 0.5F) * verticalWiggleAmplitude;
	        verticalAngle = Mth.clamp(verticalAngle, -0.1F, 0.1F);

	        double horizontalRadius = thickness;
	        double verticalRadius = thickness * yScale;

	        horizontalRadius *= config.shape.horizontalRadiusFactor.sample(random);
	        verticalRadius = this.updateVerticalRadius(config, random, verticalRadius, (float) totalSteps, (float) i);

	        float pitchCos = Mth.cos(verticalAngle);
	        float pitchSin = Mth.sin(verticalAngle);

	        x += Mth.cos(horizontalAngle) * pitchCos;
	        y += pitchSin * 0.5;
	        z += Mth.sin(horizontalAngle) * pitchCos;

	        if(random.nextInt(3) != 0)
	        {
	            if(!canReach(chunk.getPos(), x, z, i, totalSteps, thickness))
	                return;

	            this.carveEllipsoid(context, config, chunk, biomeGetter, aquifer, x, y, z, horizontalRadius, verticalRadius, mask, (ctx, dx, dy, dz, index) -> this.shouldSkip(ctx, widthFactors, dx, dy, dz, index));
	        }
	    }
	}

	private float[] initWidthFactors(CarvingContext p_224809_, CanyonCarverConfiguration p_224810_, RandomSource p_224811_)
	{
		int i = p_224809_.getGenDepth();
		float[] afloat = new float[i];
		float f = 1.0F;
		for(int j = 0; j < i; ++j) 
		{
			if(j == 0 || p_224811_.nextInt(p_224810_.shape.widthSmoothness) == 0) 
			{
				f = 1.0F + p_224811_.nextFloat() * p_224811_.nextFloat();
			}
			afloat[j] = f * f;
		}
		return afloat;
	}

	private double updateVerticalRadius(CanyonCarverConfiguration p_224800_, RandomSource p_224801_, double p_224802_, float p_224803_, float p_224804_)
	{
	    float f = 1.0F - Mth.abs(0.5F - p_224804_ / p_224803_) * 2.0F;
	    float widenAtBottom = Mth.clamp((0.5F - p_224804_ / p_224803_) * 2.0F, 0, 1);
	    float factor = p_224800_.shape.verticalRadiusDefaultFactor + p_224800_.shape.verticalRadiusCenterFactor * f + 1.0F * widenAtBottom;
	    return factor * p_224802_ * Mth.randomBetween(p_224801_, 0.75F, 1.0F);
	}

	//ChatGPT ahh;
	private boolean shouldSkip(CarvingContext context, float[] widthFactors, double dx, double dy, double dz, int y)
	{
	    int heightIndex = y - context.getMinGenY();
	    if(heightIndex < 0 || heightIndex >= widthFactors.length) 
	    {
	    	return true;
	    }
	    double distXZ = Math.sqrt(dx * dx + dz * dz);
	    double maxDist = 1.5;
	    double slopeDepth = 1.5;
	    double allowed = (dy + slopeDepth) / slopeDepth * maxDist;
	    return distXZ > allowed;
	}
}
