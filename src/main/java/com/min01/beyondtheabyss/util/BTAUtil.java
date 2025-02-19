package com.min01.beyondtheabyss.util;

import java.lang.reflect.Method;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Consumer;

import com.min01.beyondtheabyss.capabilities.BTAAbilityCapabilityImpl;
import com.min01.beyondtheabyss.capabilities.BTAAbilityCapabilityImpl.BTAAbility;
import com.min01.beyondtheabyss.capabilities.BTACapabilities;
import com.min01.beyondtheabyss.capabilities.IBTAAbilityCapability;
import com.min01.beyondtheabyss.capabilities.IItemAnimationCapability;
import com.min01.beyondtheabyss.capabilities.IPlayerAnimationCapability;
import com.min01.beyondtheabyss.capabilities.ItemAnimationCapabilityImpl;
import com.min01.beyondtheabyss.capabilities.PlayerAnimationCapabilityImpl;
import com.min01.beyondtheabyss.misc.BTASimplexNoise;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.StructureBlockEntity;
import net.minecraft.world.level.entity.LevelEntityGetter;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.util.LogicalSidedProvider;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.util.ObfuscationReflectionHelper;

public class BTAUtil 
{
	//from https://github.com/AlexModGuy/AlexsCaves/blob/main/src/main/java/com/github/alexmodguy/alexscaves/server/misc/ACMath.java

    public static float smin(float a, float b, float k) 
    {
        float h = Math.max(k - Math.abs(a - b), 0.0F) / k;
        return Math.min(a, b) - h * h * k * (1.0F / 4.0F);
    }
    
    public static float sampleNoise2D(int x, int z, float simplexSampleRate)
    {
        return (float) ((BTASimplexNoise.noise((x + simplexSampleRate) / simplexSampleRate, (z + simplexSampleRate) / simplexSampleRate)));
    }
    
    public static float sampleNoise3D(int x, int y, int z, float simplexSampleRate) 
    {
        return (float) ((BTASimplexNoise.noise((x + simplexSampleRate) / simplexSampleRate, (y + simplexSampleRate) / simplexSampleRate, (z + simplexSampleRate) / simplexSampleRate)));
    }
    
    public static float sampleNoise3D(float x, float y, float z, float simplexSampleRate) 
    {
        return (float) ((BTASimplexNoise.noise((x + simplexSampleRate) / simplexSampleRate, (y + simplexSampleRate) / simplexSampleRate, (z + simplexSampleRate) / simplexSampleRate)));
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
	
	public static void getClientLevel(Consumer<Level> consumer)
	{
		LogicalSidedProvider.CLIENTWORLD.get(LogicalSide.CLIENT).filter(ClientLevel.class::isInstance).ifPresent(level -> 
		{
			consumer.accept(level);
		});
	}
    
    public static void updatePlayerTick(LivingEntity player)
    {
    	player.getCapability(BTACapabilities.PLAYER_ANIMATION).ifPresent(t -> 
    	{
    		t.update();
    	});
    }
    
    public static void updateItemTick(LivingEntity player, ItemStack stack)
    {
    	stack.getCapability(BTACapabilities.ITEM_ANIMATION).ifPresent(t -> 
    	{
    		t.update();
    		t.setEntity(player);
    	});
    }
    
    public static int getItemAnimationTick(ItemStack stack)
    {
        IItemAnimationCapability cap = stack.getCapability(BTACapabilities.ITEM_ANIMATION).orElse(new ItemAnimationCapabilityImpl());
        return cap.getAnimationTick();
    }
    public static void setItemAnimationTick(ItemStack stack, int tick)
    {
    	stack.getCapability(BTACapabilities.ITEM_ANIMATION).ifPresent(t -> 
    	{
    		t.setAnimationTick(tick);
    	});
    }
    
    public static void startItemAnimation(ItemStack stack, String animationName, int tickCount)
    {
    	stopAllItemAnimations(stack);
    	AnimationState animationState = getItemAnimationState(stack, animationName);
    	animationState.startIfStopped(tickCount);
    	setItemAnimationState(stack, animationState, animationName);
    }
    
    public static void stopAllItemAnimations(ItemStack stack)
    {
    	stack.getCapability(BTACapabilities.ITEM_ANIMATION).ifPresent(t -> 
    	{
    		ListTag list = t.getTag().getLeft();
    		for(int i = 0; i < list.size(); ++i)
    		{
    			CompoundTag compoundTag = list.getCompound(i);
    	    	AnimationState animationState = getItemAnimationState(stack, compoundTag.getString("Name"));
    	    	animationState.stop();
    	    	setItemAnimationState(stack, animationState, compoundTag.getString("Name"));
    		}
    	});
    }

    public static void stopItemAnimation(ItemStack stack, String animationName)
    {
    	AnimationState animationState = getItemAnimationState(stack, animationName);
    	animationState.stop();
    	setItemAnimationState(stack, animationState, animationName);
    }
    
    public static void startPlayerAnimation(Entity player, String animationName)
    {
    	stopAllPlayerAnimations(player);
    	AnimationState animationState = getPlayerAnimationState(player, animationName);
    	animationState.startIfStopped(player.tickCount);
    	setPlayerAnimationState(player, animationState, animationName);
    }
    
    public static void stopAllPlayerAnimations(Entity player)
    {
    	player.getCapability(BTACapabilities.PLAYER_ANIMATION).ifPresent(t -> 
    	{
    		ListTag list = t.getTag().getLeft();
    		for(int i = 0; i < list.size(); ++i)
    		{
    			CompoundTag compoundTag = list.getCompound(i);
    	    	AnimationState animationState = getPlayerAnimationState(player, compoundTag.getString("Name"));
    	    	animationState.stop();
    	    	setPlayerAnimationState(player, animationState, compoundTag.getString("Name"));
    		}
    	});
    }
    
    public static void stopPlayerAnimation(Entity player, String animationName)
    {
    	AnimationState animationState = getPlayerAnimationState(player, animationName);
    	animationState.stop();
    	setPlayerAnimationState(player, animationState, animationName);
    }
    
    public static AnimationState getPlayerAnimationState(Entity player, String animationName)
    {
        IPlayerAnimationCapability cap = player.getCapability(BTACapabilities.PLAYER_ANIMATION).orElse(new PlayerAnimationCapabilityImpl());
        return cap.getAnimationState(animationName);
    }

    public static void setPlayerAnimationState(Entity player, AnimationState state, String animationName)
    {
    	player.getCapability(BTACapabilities.PLAYER_ANIMATION).ifPresent(t -> 
    	{
    		t.setAnimationState(state, animationName);
    	});
    }

    public static AnimationState getItemAnimationState(ItemStack stack, String animationName)
    {
        IItemAnimationCapability cap = stack.getCapability(BTACapabilities.ITEM_ANIMATION).orElse(new ItemAnimationCapabilityImpl());
        return cap.getAnimationState(animationName);
    }
    
    public static void setItemAnimationState(ItemStack stack, AnimationState state, String animationName)
    {
    	stack.getCapability(BTACapabilities.ITEM_ANIMATION).ifPresent(t -> 
    	{
    		t.setAnimationState(state, animationName);
    	});
    }
	
	public static void writeAnimationState(ListTag list, AnimationState state, String animationName)
	{
		CompoundTag compoundTag = getAnimationTag(list, animationName);
		compoundTag.putString("Name", animationName);
		compoundTag.putLong("LastTime", state.lastTime);
		compoundTag.putLong("AccumulatedTime", state.accumulatedTime);
		if(!hasAnimation(list, animationName))
		{
			list.add(compoundTag);
		}
	}
	
	public static boolean hasAnimation(ListTag list, String animationName)
	{
		boolean flag = false;
		for(int i = 0; i < list.size(); ++i)
		{
			CompoundTag compoundTag = list.getCompound(i);
			if(compoundTag.getString("Name") == animationName)
			{
				flag = true;
				break;
			}
		}
		return flag;
	}
	
	public static CompoundTag getAnimationTag(ListTag list, String animationName)
	{
		for(int i = 0; i < list.size(); ++i)
		{
			CompoundTag compoundTag = list.getCompound(i);
			if(compoundTag.getString("Name") == animationName)
			{
				return compoundTag;
			}
		}
		return new CompoundTag();
	}
	
	public static AnimationState readAnimationState(ListTag list, String animationName)
	{
		AnimationState state = new AnimationState();
		for(int i = 0; i < list.size(); ++i)
		{
			CompoundTag compoundTag = list.getCompound(i);
			if(compoundTag.getString("Name") == animationName)
			{
				state.lastTime = compoundTag.getLong("LastTime");
				state.accumulatedTime = compoundTag.getLong("AccumulatedTime");
				return state;
			}
		}
		return state;
	}
    
	public static Vec3 getSpreadPosition(Level level, Vec3 startPos, double range)
	{
        double x = (double) startPos.x + (level.random.nextDouble() - level.random.nextDouble()) * (double)range + 0.5D;
        double y = (double) startPos.y + (level.random.nextDouble() - level.random.nextDouble()) * (double)range + 0.5D;
        double z = (double) startPos.z + (level.random.nextDouble() - level.random.nextDouble()) * (double)range + 0.5D;
        return new Vec3(x, y, z);
	}
	
	public static Vec3 getSpreadPosition(Entity entity, double range)
	{
        double x = (double) entity.getX() + (entity.level.random.nextDouble() - entity.level.random.nextDouble()) * (double)range + 0.5D;
        double y = (double) entity.getY() + (entity.level.random.nextDouble() - entity.level.random.nextDouble()) * (double)range + 0.5D;
        double z = (double) entity.getZ() + (entity.level.random.nextDouble() - entity.level.random.nextDouble()) * (double)range + 0.5D;
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
		Method m = ObfuscationReflectionHelper.findMethod(Level.class, "m_142646_");
		try 
		{
			LevelEntityGetter<Entity> entities = (LevelEntityGetter<Entity>) m.invoke(level);
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
	
	public static boolean hasAbility(LivingEntity entity, BTAAbility ability)
	{
		IBTAAbilityCapability handler = entity.getCapability(BTACapabilities.BTA_ABILITY).orElse(new BTAAbilityCapabilityImpl());
		return handler.getAbilities().contains(ability);
	}
	   
	public static int getAbilityTickCount(BTAAbility ability, LivingEntity entity)
	{
		IBTAAbilityCapability handler = entity.getCapability(BTACapabilities.BTA_ABILITY).orElse(new BTAAbilityCapabilityImpl());
		return handler.getTickCount(ability);
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
    
    public static boolean isMoving(LivingEntity entity) 
    {
    	return entity.walkAnimation.isMoving();
    }

	public static Vec3 getLookPos(float xRot, float yRot, float yPos, double distance)
	{
		float f = -Mth.sin(yRot * ((float)Math.PI / 180F)) * Mth.cos(xRot * ((float)Math.PI / 180F));
		float f1 = -Mth.sin((xRot + yPos) * ((float)Math.PI / 180F));
		float f2 = Mth.cos(yRot * ((float)Math.PI / 180F)) * Mth.cos(xRot * ((float)Math.PI / 180F));
		return new Vec3(f, f1, f2).scale(distance);
	}
}
