package com.min01.beyondtheabyss.util;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import org.joml.Math;

import com.google.common.collect.ImmutableList;
import com.min01.beyondtheabyss.capabilities.PlayerTickCountCapabilityImpl;
import com.min01.beyondtheabyss.effect.BTAEffects;
import com.min01.beyondtheabyss.misc.PositionTypes;

import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
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
	
	public static void getClientLevel(Consumer<Level> consumer)
	{
		LogicalSidedProvider.CLIENTWORLD.get(LogicalSide.CLIENT).filter(ClientLevel.class::isInstance).ifPresent(level -> 
		{
			consumer.accept(level);
		});
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
	
    public static boolean isMoving(Entity entity) 
    {
		return entity.getDeltaMovement().horizontalDistanceSqr() > 1.0E-6D;
    }

	//called in end of tick() method of entity class;
	public static void forceTick(Entity entity)
	{
		if(entity.level instanceof ServerLevel serverLevel)
		{
			serverLevel.getChunkSource().updateChunkForced(entity.chunkPosition(), true);
		}
	}
	
	public static boolean isDone(ServerPlayer serverPlayer, String name)
	{
		Advancement adv = serverPlayer.server.getAdvancements().getAdvancement(ResourceLocation.parse(name));
		AdvancementProgress progress = serverPlayer.getAdvancements().getOrStartProgress(adv);
		return progress.isDone();
	}
	
	public static void awardAdvancement(ServerPlayer serverPlayer, String name)
	{
		Advancement adv = serverPlayer.server.getAdvancements().getAdvancement(ResourceLocation.parse(name));
		AdvancementProgress progress = serverPlayer.getAdvancements().getOrStartProgress(adv);
		if(!progress.isDone())
		{
			progress.getRemainingCriteria().forEach(t ->
			{
				serverPlayer.getAdvancements().award(adv, t);
			});
		}
	}
	
    public static void disableShield(LivingEntity livingEntity, DamageSource source, int ticks)
    {
    	if(livingEntity.isDamageSourceBlocked(source))
    	{
        	if(livingEntity instanceof Player player)
        	{
        		player.disableShield(true);
        	}
        	else
        	{
        		livingEntity.stopUsingItem();
        		livingEntity.level.broadcastEntityEvent(livingEntity, (byte)30);
        	}
    	}
    }
	
    public static boolean isNight(LevelAccessor level)
    {
    	return level.dayTime() % 24000L >= 13000L;
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
    
    public static float rotlerp(float start, float end, float maxStep) 
    {
        float delta = Mth.wrapDegrees(end - start);
        float clampedDelta = Mth.clamp(delta, -maxStep, maxStep);
        return Mth.wrapDegrees(start + clampedDelta);
    }
    
    public static float distanceToXZ(Entity entity, Entity target)
    {
        float x = (float)(entity.getX() - target.getX());
        float z = (float)(entity.getZ() - target.getZ());
        return Mth.sqrt(x * x + z * z);
    }
    
	public static Vec3 getSpreadPosition(RandomSource random, Vec3 startPos, Vec3 range)
	{
        double x = startPos.x + (random.nextDouble() - random.nextDouble()) * range.x + 0.5D;
        double y = startPos.y + (random.nextDouble() - random.nextDouble()) * range.y + 0.5D;
        double z = startPos.z + (random.nextDouble() - random.nextDouble()) * range.z + 0.5D;
        return new Vec3(x, y, z);
	}
	
	public static float percent(float baseValue, float percent)
	{
		return baseValue * percent / 100.0F;
	}
	
	public static boolean isModLoaded(String modid)
	{
		return ModList.get().isLoaded(modid);
	}
	
	public static BlockPos getPosition(BlockGetter level, Vec3 position, PositionTypes types)
    {
		return getPosition(level, position, types, Integer.MAX_VALUE);
    }
	
	public static BlockPos getPosition(BlockGetter level, Vec3 position, PositionTypes types, int maxStep)
    {
		int i = 0;
        BlockPos.MutableBlockPos mutablePos = new BlockPos.MutableBlockPos(position.x, position.y, position.z);
        do
        {
        	mutablePos.move(types.getDirection());
        	i++;
        }
        while(i < maxStep && types.test(level, mutablePos));
        return mutablePos.immutable();
    }
	
	public static void dashToward(Entity entity, Vec3 scale)
	{
        float x = (float) Math.cos(Math.toRadians(entity.getYHeadRot() + 90));
        float z = (float) Math.sin(Math.toRadians(entity.getYHeadRot() + 90));
        entity.push(x * scale.x, scale.y, z * scale.z);
	}
	
	public static void dashBackward(Entity entity, Vec3 scale)
	{
        float x = (float) Math.cos(Math.toRadians(entity.getYHeadRot() - 90));
        float z = (float) Math.sin(Math.toRadians(entity.getYHeadRot() - 90));
        entity.push(x * scale.x, scale.y, z * scale.z);
	}
	
	public static float distanceTo(Entity entity, Vec3 pos)
	{
		float x = (float)(entity.getX() - pos.x);
		float y = (float)(entity.getY() - pos.y);
		float z = (float)(entity.getZ() - pos.z);
		return Mth.sqrt(x * x + y * y + z * z);
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
	
	public static Vec3 getVelocityTowards(Vec3 from, Vec3 to, float speed)
	{
		Vec3 motion = to.subtract(from).normalize();
		return motion.scale(speed);
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
		return living.hasEffect(BTAEffects.AIR_SWIM.get());
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
	
	public static void placeStructure(MinecraftServer server, ServerLevel level, StructurePlaceSettings settings, ResourceLocation location, BlockPos pos)
	{
		Optional<StructureTemplate> optional = server.getStructureManager().get(location);
		optional.ifPresent(template -> 
		{
			template.placeInWorld(level, pos, pos, settings, StructureBlockEntity.createRandom((long)0), 2);
		});
	}
	
	public static Vec3 getPosTowards(Vec3 from, Vec3 to)
	{
		Vec3 pos = to.subtract(from);
		return pos;
	}
}
