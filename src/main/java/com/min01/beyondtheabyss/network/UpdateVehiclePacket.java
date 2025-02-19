package com.min01.beyondtheabyss.network;

import java.util.function.Supplier;

import com.min01.beyondtheabyss.util.BTAUtil;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.network.NetworkEvent;

public class UpdateVehiclePacket 
{
	public final int rider;
	public final int vehicle;

	public UpdateVehiclePacket(Entity rider, Entity vehicle) 
	{
		this.rider = rider.getId();
		this.vehicle = vehicle.getId();
	}

	public UpdateVehiclePacket(FriendlyByteBuf buf)
	{
		this.rider = buf.readInt();
		this.vehicle = buf.readInt();
	}

	public void encode(FriendlyByteBuf buf)
	{
		buf.writeInt(this.rider);
		buf.writeInt(this.vehicle);
	}

	public static class Handler 
	{
		public static boolean onMessage(UpdateVehiclePacket message, Supplier<NetworkEvent.Context> ctx)
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
			});
			ctx.get().setPacketHandled(true);
			return true;
		}
	}
}
