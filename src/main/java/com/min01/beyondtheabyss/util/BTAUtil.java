package com.min01.beyondtheabyss.util;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import org.joml.Math;

import com.google.common.collect.ImmutableList;
import com.min01.beyondtheabyss.capabilities.BTACapabilities;
import com.min01.beyondtheabyss.capabilities.IItemAnimationCapability;
import com.min01.beyondtheabyss.capabilities.IPlayerAnimationCapability;
import com.min01.beyondtheabyss.capabilities.IPlayerTickCountCapability;
import com.min01.beyondtheabyss.capabilities.ItemAnimationCapabilityImpl;
import com.min01.beyondtheabyss.capabilities.PlayerAnimationCapabilityImpl;
import com.min01.beyondtheabyss.capabilities.PlayerTickCountCapabilityImpl;
import com.min01.beyondtheabyss.effect.BTAEffects;
import com.min01.beyondtheabyss.item.animation.IAnimatableItem;
import com.min01.beyondtheabyss.misc.SmoothAnimationState;
import com.min01.beyondtheabyss.multipart.EntityBounds;
import com.min01.beyondtheabyss.world.BTAWorlds;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.NoiseColumn;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.StructureBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.entity.LevelEntityGetter;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.RandomState;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.level.levelgen.synth.SimplexNoise;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.util.LogicalSidedProvider;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.util.ObfuscationReflectionHelper;

public class BTAUtil 
{
	public static final Method GET_ENTITY = ObfuscationReflectionHelper.findMethod(Level.class, "m_142646_");
	public static final SimplexNoise SIMPLEX_NOISE = new SimplexNoise(RandomSource.create());
    
    public static float distanceToXZ(Entity entity, Entity target)
    {
        float f = (float)(entity.getX() - target.getX());
        float f2 = (float)(entity.getZ() - target.getZ());
        return Mth.sqrt(f * f + f2 * f2);
    }
    
	public static void moveStructurePiece(Structure.GenerationContext p_227387_, Heightmap.Types types, StructurePiece piece, StructureTemplate template, Rotation rotation, Mirror mirror, Consumer<Integer> consumer)
	{
		ChunkPos chunkPos = p_227387_.chunkPos();
		ChunkGenerator chunkGenerator = p_227387_.chunkGenerator();
		RandomSource random = p_227387_.random();
		RandomState randomState = p_227387_.randomState();
		LevelHeightAccessor heightAccessor = p_227387_.heightAccessor();
		BlockPos blockPos = chunkPos.getWorldPosition();
		BlockPos blockPos1 = new BlockPos(template.getSize().getX() / 2, 0, template.getSize().getZ() / 2);
		BoundingBox boundingBox = template.getBoundingBox(blockPos, rotation, blockPos1, mirror);
		BlockPos blockPos2 = boundingBox.getCenter();
		int i = chunkGenerator.getBaseHeight(blockPos2.getX(), blockPos2.getZ(), types, heightAccessor, randomState);
		int j = findSuitableY(types, random, chunkGenerator, i, piece.getBoundingBox(), heightAccessor, randomState);
		consumer.accept(j);
	}
	
	//copied from RuinedPortalStructure
	public static int findSuitableY(Heightmap.Types types, RandomSource p_229267_, ChunkGenerator p_229268_, int p_229271_, BoundingBox p_229273_, LevelHeightAccessor p_229274_, RandomState p_229275_)
	{
		int j = p_229274_.getMinBuildHeight() + 15;
		int i = p_229271_;
		List<BlockPos> list1 = ImmutableList.of(new BlockPos(p_229273_.minX(), 0, p_229273_.minZ()), new BlockPos(p_229273_.maxX(), 0, p_229273_.minZ()), new BlockPos(p_229273_.minX(), 0, p_229273_.maxZ()), new BlockPos(p_229273_.maxX(), 0, p_229273_.maxZ()));
		List<NoiseColumn> list = list1.stream().map((p_229280_) -> 
		{
			return p_229268_.getBaseColumn(p_229280_.getX(), p_229280_.getZ(), p_229274_, p_229275_);
		}).collect(Collectors.toList());
		int l;
		for(l = i; l > j; --l) 
		{
			int i1 = 0;
			for(NoiseColumn noisecolumn : list)
			{
				//fix for ceiling dimension like deep abyss
				if(l > 150)
				{
					continue;
				}
				BlockState blockstate = noisecolumn.getBlock(l);
				if(types.isOpaque().test(blockstate)) 
				{
					++i1;
					if(i1 == 3)
					{
						return l;
					}
				}
			}
		}
		return l;
	}
	
	public static boolean canSwimInAir(LivingEntity living)
	{
		return living.hasEffect(BTAEffects.AIR_SWIM.get()) || living.level.dimension() == BTAWorlds.OUTER_SPACE;
	}
	
	public static void updateGravity(Entity entity)
	{
		if(entity.level.dimension() == BTAWorlds.MOON)
		{
			entity.resetFallDistance();
			if(entity.getDeltaMovement().y <= 0.0D)
			{
				entity.setDeltaMovement(entity.getDeltaMovement().x, entity.getDeltaMovement().y * 0.7, entity.getDeltaMovement().z);
			}
		}
		if(entity.level.dimension() == BTAWorlds.OUTER_SPACE)
		{
			entity.resetFallDistance();
			if(entity.getDeltaMovement().y <= 0.0D)
			{
				entity.setDeltaMovement(entity.getDeltaMovement().x, 0.0, entity.getDeltaMovement().z);
			}
		}
	}
	
	public static void createBallWithStep(Level level, Vec3 pos, double velocity, int size, int step, BiConsumer<Vec3, Vec3> consumer)
	{
		RandomSource random = level.random;
		for(int i = -size; i <= size; i += step) 
		{
			for(int j = -size; j <= size; j += step) 
			{
				for(int k = -size; k <= size; k += step) 
				{
					double d3 = (double) j + (random.nextDouble() - random.nextDouble()) * 0.5D;
					double d4 = (double) i + (random.nextDouble() - random.nextDouble()) * 0.5D;
					double d5 = (double) k + (random.nextDouble() - random.nextDouble()) * 0.5D;
					double d6 = Math.sqrt(d3 * d3 + d4 * d4 + d5 * d5) / velocity + random.nextGaussian() * 0.05D;
					consumer.accept(pos, new Vec3(d3 / d6, d4 / d6, d5 / d6));
					if(i != -size && i != size && j != -size && j != size)
					{
						k += size * 2 - 1;
					}
				}
			}
		}
	}
	
	public static void createBall(Level level, Vec3 pos, double velocity, int size, BiConsumer<Vec3, Vec3> consumer)
	{
		RandomSource random = level.random;
		for(int i = -size; i <= size; ++i) 
		{
			for(int j = -size; j <= size; ++j) 
			{
				for(int k = -size; k <= size; ++k) 
				{
					double d3 = (double) j + (random.nextDouble() - random.nextDouble()) * 0.5D;
					double d4 = (double) i + (random.nextDouble() - random.nextDouble()) * 0.5D;
					double d5 = (double) k + (random.nextDouble() - random.nextDouble()) * 0.5D;
					double d6 = Math.sqrt(d3 * d3 + d4 * d4 + d5 * d5) / velocity + random.nextGaussian() * 0.05D;
					consumer.accept(pos, new Vec3(d3 / d6, d4 / d6, d5 / d6));
					if(i != -size && i != size && j != -size && j != size)
					{
						k += size * 2 - 1;
					}
				}
			}
		}
	}
	
    public static void tickItemAnimation(Player player)
    {
		for(int i = 0; i < player.getInventory().getContainerSize(); i++)
		{
			ItemStack stack = player.getInventory().getItem(i);
			if(stack.getItem() instanceof IAnimatableItem)
			{
				stack.getCapability(BTACapabilities.ITEM_ANIMATION).ifPresent(t -> 
				{
					t.tick(player, stack);
				});
			}
		}
    }
    
    public static void tickPlayerTickCount(LivingEntity player)
    {
		IPlayerTickCountCapability cap = player.getCapability(BTACapabilities.PLAYER_TICKCOUNT).orElse(new PlayerTickCountCapabilityImpl());
		cap.tick(player);
    }
    
    public static int getPlayerTickCount(LivingEntity player)
    {
		IPlayerTickCountCapability cap = player.getCapability(BTACapabilities.PLAYER_TICKCOUNT).orElse(new PlayerTickCountCapabilityImpl());
		return cap.getPlayerTickCount();
    }
    
    public static void tickPlayerAnimation(LivingEntity player)
    {
		IPlayerAnimationCapability cap = player.getCapability(BTACapabilities.PLAYER_ANIMATION).orElse(new PlayerAnimationCapabilityImpl());
		cap.tick(player);
    }
    
    public static void setPlayerAnimationState(Entity player, int state)
    {
		IPlayerAnimationCapability cap = player.getCapability(BTACapabilities.PLAYER_ANIMATION).orElse(new PlayerAnimationCapabilityImpl());
		cap.setAnimationState(state);
    }
    
    public static int getPlayerAnimationState(Entity player)
    {
		IPlayerAnimationCapability cap = player.getCapability(BTACapabilities.PLAYER_ANIMATION).orElse(new PlayerAnimationCapabilityImpl());
		return cap.getAnimationState();
    }
    
    public static SmoothAnimationState getPlayerAnimationStateByName(Entity player, String name)
    {
		IPlayerAnimationCapability cap = player.getCapability(BTACapabilities.PLAYER_ANIMATION).orElse(new PlayerAnimationCapabilityImpl());
		return cap.getAnimationStateByName(name);
    }
    
    public static void setPlayerAnimationTick(Entity player, int tick)
    {
		IPlayerAnimationCapability cap = player.getCapability(BTACapabilities.PLAYER_ANIMATION).orElse(new PlayerAnimationCapabilityImpl());
		cap.setAnimationTick(tick);
    }
    
    public static int getPlayerAnimationTick(Entity player)
    {
		IPlayerAnimationCapability cap = player.getCapability(BTACapabilities.PLAYER_ANIMATION).orElse(new PlayerAnimationCapabilityImpl());
		return cap.getAnimationTick();
    }
	
    public static int getItemTickCount(ItemStack stack)
    {
		IItemAnimationCapability cap = stack.getCapability(BTACapabilities.ITEM_ANIMATION).orElse(new ItemAnimationCapabilityImpl());
		return cap.getTickCount();
    }
	
    public static void setItemAnimationState(ItemStack stack, int state)
    {
		IItemAnimationCapability cap = stack.getCapability(BTACapabilities.ITEM_ANIMATION).orElse(new ItemAnimationCapabilityImpl());
		cap.setAnimationState(state);
    }
    
    public static int getItemAnimationState(ItemStack stack)
    {
		IItemAnimationCapability cap = stack.getCapability(BTACapabilities.ITEM_ANIMATION).orElse(new ItemAnimationCapabilityImpl());
		return cap.getAnimationState();
    }
    
    public static SmoothAnimationState getItemAnimationStateByName(ItemStack stack, String name)
    {
		IItemAnimationCapability cap = stack.getCapability(BTACapabilities.ITEM_ANIMATION).orElse(new ItemAnimationCapabilityImpl());
		return cap.getAnimationStateByName(name);
    }
    
    public static void setItemAnimationTick(ItemStack stack, int tick)
    {
		IItemAnimationCapability cap = stack.getCapability(BTACapabilities.ITEM_ANIMATION).orElse(new ItemAnimationCapabilityImpl());
		cap.setAnimationTick(tick);
    }
    
    public static int getItemAnimationTick(ItemStack stack)
    {
		IItemAnimationCapability cap = stack.getCapability(BTACapabilities.ITEM_ANIMATION).orElse(new ItemAnimationCapabilityImpl());
		return cap.getAnimationTick();
    }
	
	public static boolean isCollisionShapeFullBlock(Level level, BlockPos pos)
	{
		return level.getBlockState(pos).isCollisionShapeFullBlock(level, pos);
	}
     
	public static float smin(float a, float b, float k) 
	{
		float h = Math.max(k - Math.abs(a - b), 0.0F) / k;
		return Math.min(a, b) - h * h * k * (1.0F / 4.0F);
	}
	     
	public static float sampleNoise3D(int x, int y, int z, float simplexSampleRate) 
	{
		return (float) ((SIMPLEX_NOISE.getValue((x + simplexSampleRate) / simplexSampleRate, (y + simplexSampleRate) / simplexSampleRate, (z + simplexSampleRate) / simplexSampleRate)));
	}
	
	@SuppressWarnings("deprecation")
	public static BlockPos getGroundPos(BlockGetter pLevel, double pX, double startY, double pZ, int belowY)
    {
        BlockPos.MutableBlockPos blockpos$mutable = new BlockPos.MutableBlockPos(pX, startY, pZ);
        do
        {
        	blockpos$mutable.move(Direction.DOWN);
        }
        while((pLevel.getBlockState(blockpos$mutable).isAir() || pLevel.getBlockState(blockpos$mutable).liquid() || !pLevel.getBlockState(blockpos$mutable).isCollisionShapeFullBlock(pLevel, blockpos$mutable)) && blockpos$mutable.getY() > pLevel.getMinBuildHeight());
        BlockPos pos = blockpos$mutable.below().below(belowY);
        return pos;
    }
	
	public static String getMultiPart(EntityBounds bounds, Player player)
	{
        Vec3 pos = player.getEyePosition(1.0F);
        Vec3 dir = player.getViewVector(1.0F);
        double reach = player.getBlockReach();
    	return bounds.raycast(pos, pos.add(dir.scale(reach)));
	}
	
	public static void getClientLevel(Consumer<Level> consumer)
	{
		LogicalSidedProvider.CLIENTWORLD.get(LogicalSide.CLIENT).filter(ClientLevel.class::isInstance).ifPresent(level -> 
		{
			consumer.accept(level);
		});
	}
	
	public static Vec3 getRandomPosition(Entity entity, int range)
	{
    	Vec3 vec3 = entity.position().add(Mth.randomBetweenInclusive(entity.level.random, -range, range), Mth.randomBetweenInclusive(entity.level.random, -range, range), Mth.randomBetweenInclusive(entity.level.random, -range, range));
        return vec3;
	}
    
	public static Vec3 getSpreadPosition(Level level, Vec3 startPos, double range)
	{

        double x = startPos.x + (level.random.nextDouble() - level.random.nextDouble()) * range + 0.5D;
        double y = startPos.y + (level.random.nextDouble() - level.random.nextDouble()) * range + 0.5D;
        double z = startPos.z + (level.random.nextDouble() - level.random.nextDouble()) * range + 0.5D;
        return new Vec3(x, y, z);
	}
	
	public static Vec3 getSpreadPosition(Entity entity, Vec3 range)
	{
        double x = entity.getX() + (entity.level.random.nextDouble() - entity.level.random.nextDouble()) * range.x + 0.5D;
        double y = entity.getY() + (entity.level.random.nextDouble() - entity.level.random.nextDouble()) * range.y + 0.5D;
        double z = entity.getZ() + (entity.level.random.nextDouble() - entity.level.random.nextDouble()) * range.z + 0.5D;
        return new Vec3(x, y, z);
	}
	
	public static Vec3 getSpreadPosition(Entity entity, double range)
	{
        double x = entity.getX() + (entity.level.random.nextDouble() - entity.level.random.nextDouble()) * range + 0.5D;
        double y = entity.getY() + (entity.level.random.nextDouble() - entity.level.random.nextDouble()) * range + 0.5D;
        double z = entity.getZ() + (entity.level.random.nextDouble() - entity.level.random.nextDouble()) * range + 0.5D;
        return new Vec3(x, y, z);
	}
	
	public static float percent(float baseValue, float percent)
	{
		return baseValue * percent / 100.0F;
	}
    
	public static Vec2 lookAt(Vec3 startPos, Vec3 pos)
	{
		Vec3 vec3 = startPos;
		double d0 = pos.x - vec3.x;
		double d1 = pos.y - vec3.y;
		double d2 = pos.z - vec3.z;
		double d3 = Math.sqrt(d0 * d0 + d2 * d2);
		float xRot = Mth.wrapDegrees((float)(-(Mth.atan2(d1, d3) * (double)(180.0F / (float)Math.PI))));
		float yRot = Mth.wrapDegrees((float)(Mth.atan2(d2, d0) * (double)(180.0F / (float)Math.PI)) - 90.0F);
	    return new Vec2(xRot, yRot);
	}
	
	public static Vec3 getLookPos(Vec2 rotation, Vec3 position, double left, double up, double forwards) 
	{
		Vec2 vec2 = rotation;
		Vec3 vec3 = position;
		float f = Mth.cos((vec2.y + 90.0F) * ((float)Math.PI / 180.0F));
		float f1 = Mth.sin((vec2.y + 90.0F) * ((float)Math.PI / 180.0F));
		float f2 = Mth.cos(-vec2.x * ((float)Math.PI / 180.0F));
		float f3 = Mth.sin(-vec2.x * ((float)Math.PI / 180.0F));
		float f4 = Mth.cos((-vec2.x + 90.0F) * ((float)Math.PI / 180.0F));
		float f5 = Mth.sin((-vec2.x + 90.0F) * ((float)Math.PI / 180.0F));
		Vec3 vec31 = new Vec3((double)(f * f2), (double)f3, (double)(f1 * f2));
		Vec3 vec32 = new Vec3((double)(f * f4), (double)f5, (double)(f1 * f4));
		Vec3 vec33 = vec31.cross(vec32).scale(-1.0D);
		double d0 = vec31.x * forwards + vec32.x * up + vec33.x * left;
		double d1 = vec31.y * forwards + vec32.y * up + vec33.y * left;
		double d2 = vec31.z * forwards + vec32.z * up + vec33.z * left;
		return new Vec3(vec3.x + d0, vec3.y + d1, vec3.z + d2);
	}
	
	public static void placeStructure(MinecraftServer server, ServerLevel level, StructurePlaceSettings settings, ResourceLocation location, BlockPos pos)
	{
		Optional<StructureTemplate> optional = server.getStructureManager().get(location);
		optional.ifPresent(template -> 
		{
			template.placeInWorld(level, pos, pos, settings, StructureBlockEntity.createRandom((long)0), 2);
		});
	}
	
	public static boolean isModLoaded(String modid)
	{
		return ModList.get().isLoaded(modid);
	}
	
	@SuppressWarnings("unchecked")
	public static <T extends Entity> T getEntityByUUID(Level level, UUID uuid)
	{
		try 
		{
			LevelEntityGetter<Entity> entities = (LevelEntityGetter<Entity>) GET_ENTITY.invoke(level);
			return (T) entities.get(uuid);
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return null;
	}
	
	public static float rotlerp(float p_24992_, float p_24993_, float p_24994_)
	{
		float f = Mth.wrapDegrees(p_24993_ - p_24992_);
		
		if(f > p_24994_) 
		{
			f = p_24994_;
		}

		if(f < -p_24994_) 
		{
			f = -p_24994_;
		}

		float f1 = p_24992_ + f;
		
		if(f1 < 0.0F)
		{
			f1 += 360.0F;
		}
		else if(f1 > 360.0F)
		{
			f1 -= 360.0F;
		}
		
		return f1;
	}
	
	public static Vec3 moveToEntity(Vec3 from, Vec3 to, Entity mover, Entity target, float multiplier)
	{
		double d0 = from.x - to.x;
		double d1 = from.y - to.y;
		double d2 = from.z - to.z;
		double d3 = -d0 / (multiplier * mover.distanceTo(target));
		double d4 = -d1 / (multiplier * mover.distanceTo(target));
		double d5 = -d2 / (multiplier * mover.distanceTo(target));
		return new Vec3(d3, d4, d5);
	}
	
	public static Vec3 fromToVector(Vec3 from, Vec3 to, float scale)
	{
		Vec3 motion = to.subtract(from).normalize();
		return motion.scale(scale);
	}
	
	public static Vec3 fromToVector(Vec3 from, Vec3 to)
	{
		Vec3 motion = to.subtract(from).normalize();
		return motion;
	}
	
	public static Vec3 fromToPos(Vec3 from, Vec3 to)
	{
		Vec3 pos = to.subtract(from);
		return pos;
	}
	
    public static Entity teleportEntityToDimension(Entity entity, ServerLevel serverLevel, BlockPos pos)
    {
        if(entity.level.dimension() != serverLevel.dimension())
        {
            entity.moveTo(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, entity.getYRot(), 0.0F);
        }
        
        if(entity instanceof ServerPlayer serverPlayer) 
        {
        	serverPlayer.teleportTo(serverLevel, pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, entity.getYRot(), entity.getXRot());
            return serverPlayer;
        }

        entity.unRide();
        entity.changeDimension(serverLevel);
        Entity teleportedEntity = entity.getType().create(serverLevel);
        if(teleportedEntity == null)
        {
        	return entity;
        }
        teleportedEntity.restoreFrom(entity);
        teleportedEntity.moveTo(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, entity.getYRot(), entity.getXRot());
        teleportedEntity.setYHeadRot(entity.getYRot());
        serverLevel.addDuringTeleport(teleportedEntity);
    	return teleportedEntity;
    }
    
    public static double getMeleeAttackRangeSqr(float width, LivingEntity target, float multiplier)
    {
    	return (double)(width * multiplier * width * multiplier + target.getBbWidth());
    }
    
    public static double getMeleeAttackRangeSqr(Entity owner, LivingEntity target, float multiplier)
    {
    	return (double)(owner.getBbWidth() * multiplier * owner.getBbWidth() * multiplier + target.getBbWidth());
    }
    
    public static boolean isWithinMeleeAttackRange(Vec3 pos, float width, LivingEntity target, float multiplier)
    {
    	double d0 = pos.distanceToSqr(target.getX(), target.getY(), target.getZ());
    	return d0 <= getMeleeAttackRangeSqr(width, target, multiplier);
    }

    public static boolean isWithinMeleeAttackRange(Entity owner, LivingEntity target, float multiplier)
    {
    	double d0 = owner.distanceToSqr(target.getX(), target.getY(), target.getZ());
    	return d0 <= getMeleeAttackRangeSqr(owner, target, multiplier);
    }

	public static Vec3 getLookPos(float xRot, float yRot, float yPos, double distance)
	{
		float f = -Mth.sin(yRot * ((float)Math.PI / 180F)) * Mth.cos(xRot * ((float)Math.PI / 180F));
		float f1 = -Mth.sin((xRot + yPos) * ((float)Math.PI / 180F));
		float f2 = Mth.cos(yRot * ((float)Math.PI / 180F)) * Mth.cos(xRot * ((float)Math.PI / 180F));
		return new Vec3(f, f1, f2).scale(distance);
	}
	
 	@SuppressWarnings("deprecation")
	public static Vec3 getGroundPosAbove(BlockGetter pLevel, double pX, double startY, double pZ)
 	{
 		BlockPos.MutableBlockPos blockpos$mutable = new BlockPos.MutableBlockPos(pX, startY, pZ);
 		do
 		{
 			blockpos$mutable.move(Direction.DOWN);
 		} 
 		while((pLevel.getBlockState(blockpos$mutable).isAir() || pLevel.getBlockState(blockpos$mutable).liquid() || !pLevel.getBlockState(blockpos$mutable).isCollisionShapeFullBlock(pLevel, blockpos$mutable)) && blockpos$mutable.getY() > pLevel.getMinBuildHeight());
 		
 		BlockPos blockpos = blockpos$mutable.above();
 
 		return Vec3.atCenterOf(blockpos);
 	}
}
