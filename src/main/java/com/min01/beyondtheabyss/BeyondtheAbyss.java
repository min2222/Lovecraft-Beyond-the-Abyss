package com.min01.beyondtheabyss;

import com.min01.beyondtheabyss.block.BTABlocks;
import com.min01.beyondtheabyss.capabilities.BTACapabilities;
import com.min01.beyondtheabyss.config.BTAConfig;
import com.min01.beyondtheabyss.effect.BTAEffects;
import com.min01.beyondtheabyss.entity.BTAEntityType;
import com.min01.beyondtheabyss.item.BTAItems;
import com.min01.beyondtheabyss.network.BTANetwork;
import com.min01.beyondtheabyss.proxy.ClientProxy;
import com.min01.beyondtheabyss.proxy.CommonProxy;
import com.min01.beyondtheabyss.sound.BTASounds;

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
			return new ItemStack(BTAItems.GHIDRUTH_SPAWN_EGG.get());
		}
	};
	
	public static final CreativeModeTab ABYSS_MATERIALS = new CreativeModeTab("abyss_materials") 
	{
		@Override
		public ItemStack makeIcon()
		{
			return new ItemStack(BTAItems.GHIDRUTH_SCALE.get());
		}
	};
	
	public static final CreativeModeTab ABYSS_ARMORS = new CreativeModeTab("abyss_armors") 
	{
		@Override
		public ItemStack makeIcon()
		{
			return new ItemStack(BTAItems.DIVING_HELMET.get());
		}
	};
	
	public static final CreativeModeTab ABYSS_MISC = new CreativeModeTab("abyss_misc") 
	{
		@Override
		public ItemStack makeIcon()
		{
			return new ItemStack(BTAItems.GUIDING_CLAM.get());
		}
	};
	
	public static final CreativeModeTab ABYSS_FOODS = new CreativeModeTab("abyss_foods") 
	{
		@Override
		public ItemStack makeIcon()
		{
			return new ItemStack(BTAItems.RAW_GHIDRUTH_FLESH.get());
		}
	};
	
	public BeyondtheAbyss() 
	{
		MOD_EVENT_BUS = FMLJavaModLoadingContext.get().getModEventBus();
		BTAEntityType.ENTITY_TYPES.register(MOD_EVENT_BUS);
		BTAItems.ITEMS.register(MOD_EVENT_BUS);
		BTABlocks.BLOCKS.register(MOD_EVENT_BUS);
		BTASounds.SOUNDS.register(MOD_EVENT_BUS);
		BTAEffects.EFFECTS.register(MOD_EVENT_BUS);
		BTANetwork.registerMessages();
		MinecraftForge.EVENT_BUS.addGenericListener(Entity.class, BTACapabilities::attachEntityCapability);
		MinecraftForge.EVENT_BUS.addGenericListener(ItemStack.class, BTACapabilities::attachItemStackCapability);
		
        BTAConfig.loadConfig(BTAConfig.config, FMLPaths.CONFIGDIR.get().resolve("beyond-the-abyss.toml").toString());
	}
}
