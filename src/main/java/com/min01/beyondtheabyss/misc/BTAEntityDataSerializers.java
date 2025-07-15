package com.min01.beyondtheabyss.misc;

import com.min01.beyondtheabyss.BeyondtheAbyss;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BTAEntityDataSerializers 
{
	public static final DeferredRegister<EntityDataSerializer<?>> SERIALIZERS = DeferredRegister.create(ForgeRegistries.Keys.ENTITY_DATA_SERIALIZERS, BeyondtheAbyss.MODID);
	
	public static final RegistryObject<EntityDataSerializer<Vec3>> VEC3 = SERIALIZERS.register("serializer_vec3", () -> EntityDataSerializer.simple(BTAEntityDataSerializers::writeVec3, BTAEntityDataSerializers::readVec3));
	public static final RegistryObject<EntityDataSerializer<Vec2>> VEC2 = SERIALIZERS.register("serializer_vec2", () -> EntityDataSerializer.simple(BTAEntityDataSerializers::writeVec2, BTAEntityDataSerializers::readVec2));
	public static final RegistryObject<EntityDataSerializer<Double>> DOUBLE = SERIALIZERS.register("serializer_double", () -> EntityDataSerializer.simple((t, u) -> t.writeDouble(u), t -> t.readDouble()));
	
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
	
	public static ByteBuf writeVec2(FriendlyByteBuf buf, Vec2 vec)
	{
		buf.writeFloat(vec.x);
		buf.writeFloat(vec.y);
		return buf;
	}
	
	public static Vec2 readVec2(ByteBuf buf)
	{
		return new Vec2(buf.readFloat(), buf.readFloat());
	}
}
