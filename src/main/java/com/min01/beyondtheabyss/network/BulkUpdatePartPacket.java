package com.min01.beyondtheabyss.network;

import java.util.Map;
import java.util.UUID;
import java.util.function.Supplier;

import com.min01.beyondtheabyss.multipart.EntityPartBuilder.Part;
import com.min01.beyondtheabyss.multipart.IMultipart;
import com.min01.beyondtheabyss.util.BTAUtil;

import it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.network.NetworkEvent;

public class BulkUpdatePartPacket
{
    private final UUID entityUUID;

    private final Map<String, float[]> parts;

    public BulkUpdatePartPacket(UUID entityUUID, Map<String, float[]> parts)
    {
        this.entityUUID = entityUUID;
        this.parts = parts;
    }

    public static BulkUpdatePartPacket read(FriendlyByteBuf buf)
    {
        UUID uuid = buf.readUUID();
        int count = buf.readVarInt();
        Map<String, float[]> parts = new Object2ObjectLinkedOpenHashMap<>(count);
        for(int i = 0; i < count; i++)
        {
            String name = buf.readUtf();
            float[] state = new float[]{
                buf.readFloat(), buf.readFloat(), buf.readFloat(),
                buf.readFloat(), buf.readFloat(), buf.readFloat()
            };
            parts.put(name, state);
        }
        return new BulkUpdatePartPacket(uuid, parts);
    }

    public void write(FriendlyByteBuf buf)
    {
        buf.writeUUID(this.entityUUID);
        buf.writeVarInt(this.parts.size());
        for(Map.Entry<String, float[]> entry : this.parts.entrySet())
        {
            buf.writeUtf(entry.getKey());
            float[] s = entry.getValue();
            buf.writeFloat(s[0]);
            buf.writeFloat(s[1]);
            buf.writeFloat(s[2]);
            buf.writeFloat(s[3]);
            buf.writeFloat(s[4]);
            buf.writeFloat(s[5]);
        }
    }

    public static boolean handle(BulkUpdatePartPacket message, Supplier<NetworkEvent.Context> ctx)
    {
        ctx.get().enqueueWork(() ->
        {
            if(ctx.get().getDirection().getReceptionSide().isServer())
            {
                Entity entity = BTAUtil.getEntityByUUID(ctx.get().getSender().level, message.entityUUID);
                if(entity instanceof IMultipart multipart)
                {
                    Map<String, Part> partMap = multipart.getPartBuilder().partMap;
                    for(Map.Entry<String, float[]> entry : message.parts.entrySet())
                    {
                        Part part = partMap.get(entry.getKey());
                        if(part != null)
                        {
                            float[] s = entry.getValue();
                            part.tick(s[0], s[1], s[2], s[3], s[4], s[5]);
                        }
                    }
                }
            }
        });
        ctx.get().setPacketHandled(true);
        return true;
    }
}
