package com.min01.beyondtheabyss.event;

import java.util.HashMap;
import java.util.Map;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.animation.PlayerAnimations;
import com.min01.beyondtheabyss.effect.BTAEffects;
import com.min01.beyondtheabyss.entity.BTAEntities;
import com.min01.beyondtheabyss.entity.deepabyss.GhidruthEntity;
import com.min01.beyondtheabyss.entity.deepabyss.SpineWormHeadEntity;
import com.min01.beyondtheabyss.item.BTAItems;
import com.min01.beyondtheabyss.item.animation.ItemAnimations;
import com.min01.beyondtheabyss.misc.BTABossTracker;
import com.min01.beyondtheabyss.misc.BTAChatTracker;
import com.min01.beyondtheabyss.network.BTANetwork;
import com.min01.beyondtheabyss.network.UpdateStoneSkinEffectPacket;
import com.min01.beyondtheabyss.util.BTAUtil;
import com.min01.beyondtheabyss.util.DeepAbyssUtil;
import com.min01.beyondtheabyss.world.BTABiomes;
import com.min01.beyondtheabyss.world.BTASavedData;
import com.min01.beyondtheabyss.world.BTAStructureFinder;
import com.min01.beyondtheabyss.world.BTAWorlds;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.TickEvent.LevelTickEvent;
import net.minecraftforge.event.TickEvent.PlayerTickEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.EntityMountEvent;
import net.minecraftforge.event.entity.living.LivingBreatheEvent;
import net.minecraftforge.event.entity.living.LivingDrownEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingTickEvent;
import net.minecraftforge.event.entity.living.MobEffectEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@Mod.EventBusSubscriber(modid = BeyondtheAbyss.MODID, bus = Bus.FORGE)
public class EventHandlerForge 
{
	public static final Map<ResourceKey<Level>, BTAChatTracker> CHAT_MAP = new HashMap<>();
    
    @SubscribeEvent
    public static void onPlayerRightClickItem(PlayerInteractEvent.RightClickBlock event)
    {
    	ItemStack stack = event.getItemStack();
    	Level level = event.getLevel();
    	BlockPos pos = event.getPos();
    	BlockState state = level.getBlockState(pos);
    	if(stack.is(Items.BONE_MEAL) && level.dimension() == BTAWorlds.DEEP_ABYSS && !(state instanceof BonemealableBlock))
    	{
        	event.setCanceled(true);
    	}
    }
    
    @SubscribeEvent
    public static void onMobEffectAdded(MobEffectEvent.Added event) 
    {
    	LivingEntity living = event.getEntity();
    	MobEffectInstance instance = event.getEffectInstance();
    	if(instance != null && instance.getEffect() == BTAEffects.STONE_SKIN.get() && !living.level.isClientSide)
    	{
    		BTANetwork.sendToAll(new UpdateStoneSkinEffectPacket(living.getUUID(), instance.getAmplifier(), instance.getDuration(), false));
    	}
    }
    
    @SubscribeEvent
    public static void onMobEffectRemove(MobEffectEvent.Remove event) 
    {
    	LivingEntity living = event.getEntity();
    	MobEffectInstance instance = event.getEffectInstance();
    	if(instance != null && instance.getEffect() == BTAEffects.STONE_SKIN.get() && !living.level.isClientSide)
    	{
    		BTANetwork.sendToAll(new UpdateStoneSkinEffectPacket(living.getUUID(), instance.getAmplifier(), instance.getDuration(), true));
    	}
    }
    
    @SubscribeEvent
    public static void onMobEffectExpired(MobEffectEvent.Expired event) 
    {
    	LivingEntity living = event.getEntity();
    	MobEffectInstance instance = event.getEffectInstance();
    	if(instance != null && instance.getEffect() == BTAEffects.STONE_SKIN.get() && !living.level.isClientSide)
    	{
    		BTANetwork.sendToAll(new UpdateStoneSkinEffectPacket(living.getUUID(), instance.getAmplifier(), instance.getDuration(), true));
    	}
    }
    
    @SubscribeEvent
    public static void onLevelTick(LevelTickEvent event)
    {
    	if(event.level instanceof ServerLevel serverLevel)
    	{
    		if(event.phase == TickEvent.Phase.START)
    		{
    			if(CHAT_MAP.containsKey(serverLevel.dimension()))
    			{
    				BTAChatTracker ticker = CHAT_MAP.get(serverLevel.dimension());
    				ticker.tick();
    			}
    			CHAT_MAP.values().removeIf(t -> t.tickCount > 120);
    		}
    	}
    }
    
    @SubscribeEvent
    public static void onLivingBreath(LivingBreatheEvent event)
    {
    	LivingEntity entity = event.getEntity();
    	if(entity.level.dimension() == BTAWorlds.MOON || entity.level.dimension() == BTAWorlds.OUTER_SPACE)
    	{
    		event.setCanBreathe(false);
    	}
    }
    
    @SubscribeEvent
    public static void onEntityMount(EntityMountEvent event)
    {
    	if(event.isDismounting())
    	{
    		if(event.getEntityBeingMounted() instanceof SpineWormHeadEntity)
    		{
    			boolean flag = event.getEntityMounting() instanceof Player player ? !player.getAbilities().instabuild : true;
    			if(event.getEntityMounting().isShiftKeyDown() && flag)
    			{
            		event.setCanceled(true);
    			}
    		}
    	}
    }
    
    @SubscribeEvent
    public static void onLivingDrown(LivingDrownEvent event)
    {
    	LivingEntity entity = event.getEntity();
    	if(entity.level.dimension() == BTAWorlds.MOON || entity.level.dimension() == BTAWorlds.OUTER_SPACE)
    	{
    		if(!entity.isEyeInFluidType(ForgeMod.WATER_TYPE.get()) && event.isDrowning())
    		{
    			event.setBubbleCount(0);
    		}
    	}
    	if(BTAUtil.canSwimInAir(entity) || DeepAbyssUtil.isInsideSubmarine(entity))
    	{
			event.setBubbleCount(0);
    	}
    }
    
    @SubscribeEvent
    public static void onEntityJoinLevel(EntityJoinLevelEvent event) 
    {
    	Level level = event.getLevel();
    	Entity entity = event.getEntity();
		if(entity instanceof Player player)
		{
			if(level instanceof ServerLevel serverLevel)
			{
				BlockPos blockPos = player.blockPosition();
				BTAStructureFinder.find(serverLevel, blockPos);
			}
		}
    }
    
	@SubscribeEvent
	public static void onPlayerTick(PlayerTickEvent event)
	{
		Player player = event.player;
		BTAUtil.tickPlayerTickCount(player);
		PlayerAnimations.tickPlayerAnimation(event);
		if(!player.level.isClientSide && player.level.getBiome(player.blockPosition()).is(BTABiomes.SPIRE_HOLLOW) && player.getY() <= -30 && player.level.dimension() == BTAWorlds.DEEP_ABYSS && !player.isSpectator() && !player.getAbilities().instabuild)
		{
			BTASavedData data = BTASavedData.get(player.level);
			if(!data.isBossSpawned(BTABossTracker.GHIDRUTH))
			{
				if(Math.random() <= 0.01)
				{
					float yRot = player.level.random.nextFloat() * 360.0F;
					Vec3 lookPos = BTAUtil.getLookPos(new Vec2(0.0F, player.getYHeadRot() + yRot), player.position(), 0, 0, player.level.random.nextInt(25, 30));
					GhidruthEntity ghidruth = new GhidruthEntity(BTAEntities.GHIDRUTH.get(), player.level);
					HitResult result = player.level.clip(new ClipContext(player.position(), lookPos, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, player));
					ghidruth.setPos(result.getLocation());
					player.level.addFreshEntity(ghidruth);
					data.setBossSpawned(BTABossTracker.GHIDRUTH, true);
				}
			}
		}
	}

	@SubscribeEvent
	public static void onLivingTick(LivingTickEvent event)
	{
		LivingEntity entity = event.getEntity();
		if(entity.hasEffect(BTAEffects.AIR_SWIM.get()))
		{
			entity.setOnGround(false);
			entity.resetFallDistance();
		}
		ItemAnimations.tickItemAnimations(event);
	}
    
    @SubscribeEvent
    public static void onPlayerBreakSpeed(PlayerEvent.BreakSpeed event)
    {
    	Player player = event.getEntity();
    	if(player.isEyeInFluidType(Fluids.WATER.getFluidType()))
    	{
        	if(player.getItemBySlot(EquipmentSlot.CHEST).getItem() == BTAItems.FELMETAL_DIVING_SUIT.get())
        	{
        		event.setNewSpeed(event.getOriginalSpeed() * 4.0F);
        	}
    	}
    }
}
