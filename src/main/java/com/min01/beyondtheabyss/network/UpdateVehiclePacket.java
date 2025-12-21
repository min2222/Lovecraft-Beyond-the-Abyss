package com.min01.beyondtheabyss.network;

import java.util.function.Supplier;

import com.min01.beyondtheabyss.util.BTAUtil;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkEvent;

public class UpdateVehiclePacket 
{
	public final int rider;
	public final int vehicle;

	public UpdateVehiclePacket(int rider, int vehicle) 
	{
		this.rider = rider;
		this.vehicle = vehicle;
	}

	public static UpdateVehiclePacket read(FriendlyByteBuf buf)
	{
		return new UpdateVehiclePacket(buf.readInt(), buf.readInt());
	}

	public void write(FriendlyByteBuf buf)
	{
		buf.writeInt(this.rider);
		buf.writeInt(this.vehicle);
	}

	public static boolean handle(UpdateVehiclePacket message, Supplier<NetworkEvent.Context> ctx)
	{
		ctx.get().enqueueWork(() ->
		{
			if(ctx.get().getDirection().getReceptionSide().isClient()) 
			{
				BTAUtil.getClientLevel(level -> 
				{
					Entity rider = level.getEntity(message.rider);
					Entity vehicle = level.getEntity(message.vehicle);
					if(rider != null && vehicle != null)
					{
						rider.startRiding(vehicle);
					}
				});
			}
			else
			{
				Level level = ctx.get().getSender().level;
				Entity rider = level.getEntity(message.rider);
				Entity vehicle = level.getEntity(message.vehicle);
				if(rider != null && vehicle != null)
				{
					rider.startRiding(vehicle);
				}
			}
		});
		ctx.get().setPacketHandled(true);
		return true;
	}
}
