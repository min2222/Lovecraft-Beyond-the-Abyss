package com.min01.beyondtheabyss.network;

import java.lang.reflect.Method;
import java.util.function.Supplier;

import com.min01.beyondtheabyss.cerbon.IMultipart;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraftforge.fml.util.ObfuscationReflectionHelper;
import net.minecraftforge.network.NetworkEvent;

public class MultiPartInteractionPacket 
{
	private final int entityId;
	private final int projectileId;
    private final String part;
    private final InteractionHand hand;
    private final boolean isSneaking;
    private final InteractionType interactionType;
	
    public enum InteractionType 
    {
        ATTACK,
        INTERACT,
        PROJECTILE
    }

    public MultiPartInteractionPacket(int entityId, int projectileId, String part, InteractionHand hand, boolean isSneaking, InteractionType interactionType)
    {
        this.entityId = entityId;
        this.projectileId = projectileId;
        this.part = part;
        this.hand = hand;
        this.isSneaking = isSneaking;
        this.interactionType = interactionType;
    }

	public MultiPartInteractionPacket(FriendlyByteBuf buf)
	{
		this.entityId = buf.readInt();
		this.projectileId = buf.readInt();
        this.part = buf.readUtf(32767);
        this.hand = buf.readEnum(InteractionHand.class);
        this.isSneaking = buf.readBoolean();
        this.interactionType = buf.readEnum(InteractionType.class);
	}

	public void encode(FriendlyByteBuf buf)
	{
        buf.writeInt(this.entityId);
        buf.writeInt(this.projectileId);
        buf.writeUtf(this.part);
        buf.writeEnum(this.hand);
        buf.writeBoolean(this.isSneaking);
        buf.writeEnum(this.interactionType);
	}

	public static class Handler 
	{
		public static boolean onMessage(MultiPartInteractionPacket message, Supplier<NetworkEvent.Context> ctx)
		{
			ctx.get().enqueueWork(() ->
			{
	            ServerPlayer serverPlayer = ctx.get().getSender();
	            if(serverPlayer != null)
	            {
		            ServerLevel serverLevel = (ServerLevel) serverPlayer.level;
		            Entity entity = serverLevel.getEntity(message.entityId);
		            if(entity != null)
		            {
			            if(message.interactionType == InteractionType.INTERACT)
			            {
				            serverPlayer.setShiftKeyDown(message.isSneaking);
			                entity.interact(serverPlayer, message.hand);
			            }
			            else if(message.interactionType == InteractionType.ATTACK)
			            {
				            serverPlayer.setShiftKeyDown(message.isSneaking);
			                serverPlayer.attack(entity);
			            }
			            else if(message.interactionType == InteractionType.PROJECTILE)
			            {
				            Projectile projectile = (Projectile) serverLevel.getEntity(message.projectileId);
			                if(entity instanceof IMultipart multipart)
			                {
								Method m = ObfuscationReflectionHelper.findMethod(Projectile.class, "m_5790_", EntityHitResult.class);
								try 
								{
									m.invoke(projectile, new EntityHitResult(entity));
								}
								catch(Exception e) 
								{
									
								}
			                }
			            }
		            }
	            }
			});
			ctx.get().setPacketHandled(true);
			return true;
		}
	}
}
