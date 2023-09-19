package com.min01.beyondtheabyss;

import com.min01.beyondtheabyss.block.AbyssBlocks;
import com.min01.beyondtheabyss.capabilities.AbyssCapabilities;
import com.min01.beyondtheabyss.config.AbyssConfig;
import com.min01.beyondtheabyss.effect.AbyssEffects;
import com.min01.beyondtheabyss.entity.AbyssEntityType;
import com.min01.beyondtheabyss.item.AbyssItems;
import com.min01.beyondtheabyss.network.AbyssNetwork;
import com.min01.beyondtheabyss.proxy.ClientProxy;
import com.min01.beyondtheabyss.proxy.CommonProxy;
import com.min01.beyondtheabyss.sound.AbyssSounds;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLPaths;

@Mod(BeyondtheAbyss.MODID)
public class BeyondtheAbyss
{
	public static final String MODID = "beyondtheabyss";
	public static IEventBus MOD_EVENT_BUS;
	public static final CommonProxy PROXY = DistExecutor.safeRunForDist(() -> ClientProxy::new, () -> CommonProxy::new);
	
	public static final CreativeModeTab ABYSS_MOBS = new CreativeModeTab("abyss_mobs") 
	{
		@Override
		public ItemStack makeIcon()
		{
			return new ItemStack(AbyssItems.GHIDRUTH_SPAWN_EGG.get());
		}
	};
	
	public static final CreativeModeTab ABYSS_MATERIALS = new CreativeModeTab("abyss_materials") 
	{
		@Override
		public ItemStack makeIcon()
		{
			return new ItemStack(AbyssItems.GHIDRUTH_SCALE.get());
		}
	};
	
	public static final CreativeModeTab ABYSS_ARMORS = new CreativeModeTab("abyss_armors") 
	{
		@Override
		public ItemStack makeIcon()
		{
			return new ItemStack(AbyssItems.DIVING_HELMET.get());
		}
	};
	
	public BeyondtheAbyss() 
	{
		MOD_EVENT_BUS = FMLJavaModLoadingContext.get().getModEventBus();
		AbyssEntityType.ENTITY_TYPES.register(MOD_EVENT_BUS);
		AbyssItems.ITEMS.register(MOD_EVENT_BUS);
		AbyssBlocks.BLOCKS.register(MOD_EVENT_BUS);
		AbyssSounds.SOUNDS.register(MOD_EVENT_BUS);
		AbyssEffects.EFFECTS.register(MOD_EVENT_BUS);
		AbyssNetwork.registerMessages();
		MinecraftForge.EVENT_BUS.addGenericListener(Entity.class, AbyssCapabilities::attachEntityCapability);
		MinecraftForge.EVENT_BUS.addGenericListener(ItemStack.class, AbyssCapabilities::attachItemStackCapability);
		
        AbyssConfig.loadConfig(AbyssConfig.config, FMLPaths.CONFIGDIR.get().resolve("beyond-the-abyss.toml").toString());
	}
}
