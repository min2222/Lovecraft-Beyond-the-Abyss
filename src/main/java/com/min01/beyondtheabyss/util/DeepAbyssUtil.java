package com.min01.beyondtheabyss.util;

import com.min01.beyondtheabyss.capabilities.AbyssCapabilities;
import com.min01.beyondtheabyss.capabilities.ArmorAbilityCapabilityHandler;
import com.min01.beyondtheabyss.capabilities.ArmorAbilityCapabilityHandler.AbyssArmorAbilities;
import com.min01.beyondtheabyss.capabilities.IArmorAbilityCapability;
import com.min01.beyondtheabyss.item.AbyssItems;

import net.minecraft.network.protocol.game.ClientboundSetEntityMotionPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.material.Fluids;

public class DeepAbyssUtil 
{
	public static boolean isAbyssalDash(LivingEntity entity)
	{
		boolean isPlayer = entity instanceof Player player ? 
				!player.getCooldowns().isOnCooldown(entity.getItemBySlot(EquipmentSlot.HEAD).getItem())
				&& !player.getCooldowns().isOnCooldown(entity.getItemBySlot(EquipmentSlot.CHEST).getItem())
				&& !player.getCooldowns().isOnCooldown(entity.getItemBySlot(EquipmentSlot.LEGS).getItem())
				&& !player.getCooldowns().isOnCooldown(entity.getItemBySlot(EquipmentSlot.FEET).getItem()) : true;
		boolean flag = entity.getItemBySlot(EquipmentSlot.HEAD).getItem() == AbyssItems.GHIDRUTH_DIVING_HELMET.get() 
				&& entity.getItemBySlot(EquipmentSlot.CHEST).getItem() == AbyssItems.GHIDRUTH_DIVING_SUIT.get()
				&& entity.getItemBySlot(EquipmentSlot.LEGS).getItem() == AbyssItems.GHIDRUTH_DIVING_LEGGINGS.get()
				&& entity.getItemBySlot(EquipmentSlot.FEET).getItem() == AbyssItems.GHIDRUTH_DIVING_BOOTS.get() 
				&& entity.isEyeInFluidType(Fluids.WATER.getFluidType())
				&& isPlayer;
		
		return flag;
	}
	
	public static boolean shouldRenderAbyssalDashLayer(LivingEntity entity)
	{
		IArmorAbilityCapability handler = entity.getCapability(AbyssCapabilities.ARMOR_ABILITY).orElse(new ArmorAbilityCapabilityHandler());
		return handler.getAbility() == AbyssArmorAbilities.ABYSSAL_DASH;
	}
	
	public static void startAbyssalDash(LivingEntity entity)
	{
		float f7 = entity.getYRot();
        float f = entity.getXRot();
        float f1 = -Mth.sin(f7 * ((float)Math.PI / 180F)) * Mth.cos(f * ((float)Math.PI / 180F));
        float f2 = -Mth.sin(f * ((float)Math.PI / 180F));
        float f3 = Mth.cos(f7 * ((float)Math.PI / 180F)) * Mth.cos(f * ((float)Math.PI / 180F));
        float f4 = Mth.sqrt(f1 * f1 + f2 * f2 + f3 * f3);
        float f5 = 3.0F * ((1.0F + 2) / 4.0F);
        f1 *= f5 / f4;
        f2 *= f5 / f4;
        f3 *= f5 / f4;
        entity.push((double)f1, (double)f2, (double)f3);
        if(entity instanceof ServerPlayer player)
        {
        	player.connection.send(new ClientboundSetEntityMotionPacket(player));
        }
        if(entity instanceof Player player)
        {
            int cooldown = 100;
            player.getCooldowns().addCooldown(entity.getItemBySlot(EquipmentSlot.HEAD).getItem(), cooldown);
            player.getCooldowns().addCooldown(entity.getItemBySlot(EquipmentSlot.CHEST).getItem(), cooldown);
            player.getCooldowns().addCooldown(entity.getItemBySlot(EquipmentSlot.LEGS).getItem(), cooldown);
            player.getCooldowns().addCooldown(entity.getItemBySlot(EquipmentSlot.FEET).getItem(), cooldown);
        }
        
		entity.getCapability(AbyssCapabilities.ARMOR_ABILITY).ifPresent((cap) -> 
		{
			cap.setAbility(AbyssArmorAbilities.ABYSSAL_DASH);
		});
	}
}
