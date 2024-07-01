package com.min01.beyondtheabyss.misc;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.entity.submarine.SubmarinePart.SubmarinePartType;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BTAEntityDataSerializers 
{
	public static final DeferredRegister<EntityDataSerializer<?>> SERIALIZERS = DeferredRegister.create(ForgeRegistries.Keys.ENTITY_DATA_SERIALIZERS, BeyondtheAbyss.MODID);
	
	public static final RegistryObject<EntityDataSerializer<Vec3>> VEC3 = SERIALIZERS.register("serializer_vec3", () -> EntityDataSerializer.simple(BTAEntityDataSerializers::writeVec3, BTAEntityDataSerializers::readVec3));
	public static final RegistryObject<EntityDataSerializer<SubmarinePartType>> SUBMARINE_PART_TYPE = SERIALIZERS.register("serializer_submarine_part_type", () -> EntityDataSerializer.simple(BTAEntityDataSerializers::writePartType, BTAEntityDataSerializers::readPartType));
	
	public static ByteBuf writePartType(FriendlyByteBuf buf, SubmarinePartType type)
	{
		buf.writeInt(type.ordinal());
		return buf;
	}
	
	public static SubmarinePartType readPartType(ByteBuf buf)
	{
		return SubmarinePartType.values()[buf.readInt()];
	}
	
	public static ByteBuf writeVec3(FriendlyByteBuf buf, Vec3 vec)
	{
		buf.writeDouble(vec.x);
		buf.writeDouble(vec.y);
		buf.writeDouble(vec.z);
		return buf;
	}
	
	public static Vec3 readVec3(ByteBuf buf)
	{
		return new Vec3(buf.readDouble(), buf.readDouble(), buf.readDouble());
	}
}
