package com.min01.beyondtheabyss.util;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import org.joml.Math;

import com.google.common.collect.ImmutableList;
import com.min01.beyondtheabyss.capabilities.ItemAnimationCapabilityImpl;
import com.min01.beyondtheabyss.capabilities.PlayerAnimationCapabilityImpl;
import com.min01.beyondtheabyss.capabilities.PlayerTickCountCapabilityImpl;
import com.min01.beyondtheabyss.effect.BTAEffects;
import com.min01.beyondtheabyss.item.animation.IAnimatableItem;
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
import net.minecraft.world.level.block.Block;
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
	
	public static void forceTick(Entity entity)
	{
		if(entity.level instanceof ServerLevel serverLevel)
		{
			serverLevel.getChunkSource().updateChunkForced(entity.chunkPosition(), true);
		}
	}
    
	@SuppressWarnings("unchecked")
	public static Iterable<Entity> getAllEntities(Level level)
	{
		try 
		{
			LevelEntityGetter<Entity> entities = (LevelEntityGetter<Entity>) GET_ENTITY.invoke(level);
			return entities.getAll();
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return null;
	}
	
    public static float distanceToXZ(Entity entity, Entity target)
    {
        float f = (float)(entity.getX() - target.getX());
        float f2 = (float)(entity.getZ() - target.getZ());
        return Mth.sqrt(f * f + f2 * f2);
    }
    
	public static void moveStructurePiece(Structure.GenerationContext pContext, Heightmap.Types types, StructurePiece piece, StructureTemplate template, Rotation rotation, Mirror mirror, Consumer<Integer> consumer)
	{
		ChunkPos chunkPos = pContext.chunkPos();
		ChunkGenerator chunkGenerator = pContext.chunkGenerator();
		RandomSource random = pContext.random();
		RandomState randomState = pContext.randomState();
		LevelHeightAccessor heightAccessor = pContext.heightAccessor();
		BlockPos blockPos = chunkPos.getWorldPosition();
		BlockPos blockPos1 = new BlockPos(template.getSize().getX() / 2, 0, template.getSize().getZ() / 2);
		BoundingBox boundingBox = template.getBoundingBox(blockPos, rotation, blockPos1, mirror);
		BlockPos blockPos2 = boundingBox.getCenter();
		int i = chunkGenerator.getBaseHeight(blockPos2.getX(), blockPos2.getZ(), types, heightAccessor, randomState);
		int j = findSuitableY(types, random, chunkGenerator, i, piece.getBoundingBox(), heightAccessor, randomState);
		consumer.accept(j);
	}
	
	//copied from RuinedPortalStructure
	public static int findSuitableY(Heightmap.Types types, RandomSource pRandom, ChunkGenerator pChunkGenerator, int pHeight, BoundingBox pBox, LevelHeightAccessor pLevel, RandomState pRandomState)
	{
		int j = pLevel.getMinBuildHeight() + 15;
		int i = pHeight;
		List<BlockPos> list1 = ImmutableList.of(new BlockPos(pBox.minX(), 0, pBox.minZ()), new BlockPos(pBox.maxX(), 0, pBox.minZ()), new BlockPos(pBox.minX(), 0, pBox.maxZ()), new BlockPos(pBox.maxX(), 0, pBox.maxZ()));
		List<NoiseColumn> list = list1.stream().map(t ->
		{
			return pChunkGenerator.getBaseColumn(t.getX(), t.getZ(), pLevel, pRandomState);
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
	
    public static PlayerTickCountCapabilityImpl getPlayerTickCountCapability(LivingEntity player)
    {
    	PlayerTickCountCapabilityImpl cap = (PlayerTickCountCapabilityImpl) player.getCapability(PlayerTickCountCapabilityImpl.PLAYER_TICK_COUNT).orElse(new PlayerTickCountCapabilityImpl(player));
		return cap;
    }
	
    public static void tickPlayerTickCount(LivingEntity player)
    {
		getPlayerTickCountCapability(player).tick(player);
    }
    
    public static void setPlayerTickCount(LivingEntity player, int tickCount)
    {
		getPlayerTickCountCapability(player).setTickCount(tickCount);
    }
    
    public static int getPlayerTickCount(LivingEntity player)
    {
		return getPlayerTickCountCapability(player).getTickCount();
    }
	
    public static void tickItemAnimation(Player player)
    {
		for(int i = 0; i < player.getInventory().getContainerSize(); i++)
		{
			ItemStack stack = player.getInventory().getItem(i);
			if(stack.getItem() instanceof IAnimatableItem)
			{
				stack.getCapability(ItemAnimationCapabilityImpl.ITEM_ANIMATION).ifPresent(t -> 
				{
					t.setEntity(player);
					t.tick(player, stack);
				});
			}
		}
    }
    
    public static PlayerAnimationCapabilityImpl getPlayerAnimationCapability(Entity player)
    {
    	PlayerAnimationCapabilityImpl cap = (PlayerAnimationCapabilityImpl) player.getCapability(PlayerAnimationCapabilityImpl.PLAYER_ANIMATION).orElse(new PlayerAnimationCapabilityImpl(player));
		return cap;
    }
    
    public static void tickPlayerAnimation(LivingEntity player)
    {
    	getPlayerAnimationCapability(player).tick(player);
    }
    
    public static void setPlayerAnimationState(Entity player, int state)
    {
    	getPlayerAnimationCapability(player).setAnimationState(state);
    }
    
    public static int getPlayerAnimationState(Entity player)
    {
    	return getPlayerAnimationCapability(player).getAnimationState();
    }
    
    public static void setPlayerAnimationTick(Entity player, int tick)
    {
    	getPlayerAnimationCapability(player).setAnimationTick(tick);
    }
    
    public static int getPlayerAnimationTick(Entity player)
    {
    	return getPlayerAnimationCapability(player).getAnimationTick();
    }
    
    public static ItemAnimationCapabilityImpl getItemAnimationCapability(Entity entity, ItemStack stack)
    {
    	ItemAnimationCapabilityImpl cap = (ItemAnimationCapabilityImpl) stack.getCapability(ItemAnimationCapabilityImpl.ITEM_ANIMATION).orElse(new ItemAnimationCapabilityImpl(entity, stack));
		return cap;
    }
    
    public static void setItemAnimationState(Entity entity, ItemStack stack, int state)
    {
    	getItemAnimationCapability(entity, stack).setAnimationState(state);
    }
    
    public static int getItemAnimationState(Entity entity, ItemStack stack)
    {
    	return getItemAnimationCapability(entity, stack).getAnimationState();
    }
    
    public static void setItemAnimationTick(Entity entity, ItemStack stack, int tick)
    {
    	getItemAnimationCapability(entity, stack).setAnimationTick(tick);
    }
    
    public static int getItemAnimationTick(Entity entity, ItemStack stack)
    {
    	return getItemAnimationCapability(entity, stack).getAnimationTick();
    }
    
    public static int getItemTickCount(Entity entity, ItemStack stack)
    {
    	return getItemAnimationCapability(entity, stack).getTickCount();
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
	
	public static BlockPos getSpecificGroundPos(BlockGetter level, double x, double startY, double z, Block block)
    {
        BlockPos.MutableBlockPos mutablePos = new BlockPos.MutableBlockPos(x, startY, z);
        do
        {
        	mutablePos.move(Direction.DOWN);
        }
        while(!level.getBlockState(mutablePos).is(block) && mutablePos.getY() > level.getMinBuildHeight());
        return mutablePos.immutable();
    }
	
	public static Vec3 getGroundPosVec3(BlockGetter level, double x, double startY, double z)
	{
		BlockPos blockPos = getGroundPos(level, x, startY, z);
		return Vec3.atCenterOf(blockPos);
	}
	
	public static BlockPos getGroundPos(BlockGetter level, double x, double startY, double z, int maxStep)
    {
        BlockPos.MutableBlockPos mutablePos = new BlockPos.MutableBlockPos(x, startY, z);
        int step = 0;
        do
        {
        	mutablePos.move(Direction.DOWN);
        	step++;
        }
        while(step < maxStep && (level.getBlockState(mutablePos).isAir() || !level.getFluidState(mutablePos).isEmpty() || !level.getBlockState(mutablePos).isCollisionShapeFullBlock(level, mutablePos)) && mutablePos.getY() > level.getMinBuildHeight());
        return mutablePos.immutable();
    }
	
	public static BlockPos getGroundPos(BlockGetter level, double x, double startY, double z)
    {
        BlockPos.MutableBlockPos mutablePos = new BlockPos.MutableBlockPos(x, startY, z);
        do
        {
        	mutablePos.move(Direction.DOWN);
        }
        while((level.getBlockState(mutablePos).isAir() || !level.getFluidState(mutablePos).isEmpty() || !level.getBlockState(mutablePos).isCollisionShapeFullBlock(level, mutablePos)) && mutablePos.getY() > level.getMinBuildHeight());
        return mutablePos.immutable();
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
	
    public static float rotlerp(float start, float end, float maxStep) 
    {
        float delta = Mth.wrapDegrees(end - start);
        float clampedDelta = Mth.clamp(delta, -maxStep, maxStep);
        return Mth.wrapDegrees(start + clampedDelta);
    }
	
	public static Vec3 getVelocityTowards(Vec3 from, Vec3 to, float speed)
	{
		Vec3 motion = to.subtract(from).normalize();
		return motion.scale(speed);
	}
	
	public static Vec3 getVelocityTowards(Vec3 from, Vec3 to)
	{
		return getVelocityTowards(from, to, 1.0F);
	}
	
	public static Vec3 getPosTowards(Vec3 from, Vec3 to)
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
    
	public static BlockPos getSurfacePos(BlockGetter level, double x, double startY, double z)
    {
        BlockPos.MutableBlockPos mutablePos = new BlockPos.MutableBlockPos(x, startY, z);
        do
        {
        	mutablePos.move(Direction.DOWN);
        }
        while(level.getBlockState(mutablePos).isAir() && mutablePos.getY() > level.getMinBuildHeight());
        return mutablePos.immutable();
    }
	
	public static BlockPos getCeilingPos(BlockGetter level, double x, double startY, double z, int maxStep)
    {
        BlockPos.MutableBlockPos mutablePos = new BlockPos.MutableBlockPos(x, startY, z);
        int i = 0;
        do
        {
        	mutablePos.move(Direction.UP);
        	i++;
        }
        while(i < maxStep && (level.getBlockState(mutablePos).isAir() || !level.getFluidState(mutablePos).isEmpty() || !level.getBlockState(mutablePos).isCollisionShapeFullBlock(level, mutablePos)) && mutablePos.getY() < level.getMaxBuildHeight());
        return mutablePos.immutable();
    }
	
	public static BlockPos getCeilingPos(BlockGetter level, double x, double startY, double z)
    {
        BlockPos.MutableBlockPos mutablePos = new BlockPos.MutableBlockPos(x, startY, z);
        do
        {
        	mutablePos.move(Direction.UP);
        }
        while((level.getBlockState(mutablePos).isAir() || !level.getFluidState(mutablePos).isEmpty() || !level.getBlockState(mutablePos).isCollisionShapeFullBlock(level, mutablePos)) && mutablePos.getY() < level.getMaxBuildHeight());
        return mutablePos.immutable();
    }
}
