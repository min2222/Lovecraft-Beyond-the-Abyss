package com.min01.beyondtheabyss.event;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.capabilities.BTAAbilityCapability;
import com.min01.beyondtheabyss.capabilities.BTACapabilities;
import com.min01.beyondtheabyss.capabilities.IllusionCapability;
import com.min01.beyondtheabyss.effect.BTAEffects;
import com.min01.beyondtheabyss.item.BTAItems;
import com.min01.beyondtheabyss.misc.BTAAbilities;
import com.min01.beyondtheabyss.misc.BTALootTables;
import com.min01.beyondtheabyss.util.BTAUtil;
import com.min01.beyondtheabyss.world.BTASavedData;
import com.min01.beyondtheabyss.world.BTAWorlds;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.LootTableReference;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingTickEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.MobEffectEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@Mod.EventBusSubscriber(modid = BeyondtheAbyss.MODID, bus = Bus.FORGE)
public class EventHandlerForge 
{
	@SubscribeEvent
	public static void onEntityJoinLevel(EntityJoinLevelEvent event)
	{
		Entity entity = event.getEntity();
		Level level = event.getLevel();
		if(level instanceof ServerLevel serverLevel && entity instanceof Player)
		{
			MinecraftServer server = serverLevel.getServer();
			if(level.dimension() == BTAWorlds.DEEP_ABYSS)
			{
	        	BTASavedData data = BTASavedData.get(serverLevel, BTAWorlds.DEEP_ABYSS);
	        	if(data != null && !data.isUnderwaterBaseGenerated())
	        	{
	            	StructurePlaceSettings settings = (new StructurePlaceSettings()).setMirror(Mirror.NONE).setRotation(Rotation.NONE).setKeepLiquids(false);
	            	ResourceLocation location = new ResourceLocation(BeyondtheAbyss.MODID, "deepabyss/underwaterbase1");
	            	ResourceLocation location2 = new ResourceLocation(BeyondtheAbyss.MODID, "deepabyss/underwaterbase2");
	            	BTAUtil.placeStructure(server, serverLevel, settings, location, entity.blockPosition().offset(-36, -3, -12));
	            	BTAUtil.placeStructure(server, serverLevel, settings, location2, entity.blockPosition().offset(12, -3, -12));
	            	data.setUnderwaterBaseGenerated(true);
	        	}
			}
		}
	}
	
	@SubscribeEvent
	public static void onMobEffectAdded(MobEffectEvent.Added event)
	{
		MobEffectInstance instance = event.getEffectInstance();
		MobEffect effect = instance.getEffect();
		Entity entity = event.getEntity();
		if(effect == BTAEffects.ABYSSAL_SCALES.get())
		{
			entity.getCapability(BTACapabilities.BTA_ABILITY).ifPresent((cap) -> 
			{
				cap.addAbility(BTAAbilities.ABYSSAL_SCALES);
			});
		}
		if(effect == BTAEffects.HALLUCINATION.get())
		{
			//TODO
			/*if(!entity.level.isClientSide)
			{
				BTANetwork.sendToAll(new IllusionAddPacket(entity));
				EntityGhidruth ghidruth = BTAEntities.GHIDRUTH.get().create(entity.level);
				entity.getCapability(BTACapabilities.ILLUSION).ifPresent(t -> 
				{
					t.setIllusion(ghidruth);
				});
			}*/
		}
	}
	
    @SubscribeEvent
    public static void onLootTableLoad(LootTableLoadEvent event)
    {
        if(event.getName().toString().matches("minecraft:chests/buried_treasure")) 
        {
        	event.getTable().addPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(LootTableReference.lootTableReference(BTALootTables.GUIDING_CLAM)).build());
        }
    }
    
	@SubscribeEvent
	public static void onLivingTick(LivingTickEvent event)
	{ 	
		LivingEntity entity = event.getEntity();
        
    	entity.getCapability(BTACapabilities.BTA_ABILITY).ifPresent(BTAAbilityCapability::update);
    	entity.getCapability(BTACapabilities.ILLUSION).ifPresent(IllusionCapability::tickIllusion);
		
		if(entity.hasEffect(BTAEffects.AIR_SWIM.get()))
		{
			entity.setOnGround(false);
			entity.resetFallDistance();
		}
	}
	
	@SubscribeEvent
	public static void onLivingHurt(LivingHurtEvent event)
	{
		DamageSource source = event.getSource();
		LivingEntity living = event.getEntity();
		if(source.is(DamageTypes.DROWN))
		{
			if(living.hasEffect(BTAEffects.LUNGSPORE.get()))
			{
				MobEffectInstance effect = living.getEffect(BTAEffects.LUNGSPORE.get());
				event.setAmount(event.getAmount() + (effect.getAmplifier() * 0.5F));
			}
		}
	}
    
    @SubscribeEvent
    public static void onPlayerBreakSpeed(PlayerEvent.BreakSpeed event)
    {
    	Player player = event.getEntity();
    	if(player.isEyeInFluidType(Fluids.WATER.getFluidType()))
    	{
        	if(player.getItemBySlot(EquipmentSlot.CHEST).getItem() == BTAItems.ADVANCED_DIVING_SUIT.get())
        	{
        		event.setNewSpeed(event.getOriginalSpeed() * 5.0F);
        	}
        	else if(player.getItemBySlot(EquipmentSlot.CHEST).getItem() == BTAItems.GHIDRUTH_DIVING_SUIT.get())
        	{
        		event.setNewSpeed(event.getOriginalSpeed() * 7.0F);
        	}
    	}
    }
}
