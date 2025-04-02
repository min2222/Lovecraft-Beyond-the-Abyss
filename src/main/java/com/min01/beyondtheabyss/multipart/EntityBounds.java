package com.min01.beyondtheabyss.multipart;

import java.util.List;
import java.util.Map;

import org.jetbrains.annotations.Nullable;

import it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class EntityBounds 
{
    private CompoundOrientedBox cache;
    private final Map<String, EntityPart> partMap;
    private @Nullable

    EntityBounds(Map<String, EntityPart> partMap)
    {
        this.partMap = partMap;
    }

    public boolean hasPart(String name) 
    {
        return partMap.get(name) != null;
    }

    public EntityPart getPart(String name)
    {
        return partMap.get(name);
    }
    
    @Nullable
    public String raycast(Vec3 start, Vec3 end)
    {
        double t = 1.00001;
        String result = null;
        for(Map.Entry<String, EntityPart> entry : this.partMap.entrySet())
        {
            double tmp = entry.getValue().getBox().raycast(start, end);
            if(tmp != -1 && tmp < t) 
            {
                t = tmp;
                result = entry.getKey();
            }
        }
        return result;
    }

    public CompoundOrientedBox getBox(AABB bounds) 
    {
        boolean changed = this.cache == null;
        for(EntityPart value : this.partMap.values())
        {
            if(value.isChanged())
            {
                changed = true;
                value.setChanged(false);
            }
        }
        if(changed) 
        {
            List<OrientedBox> parts = new ObjectArrayList<>(this.partMap.size());
            for(EntityPart value : this.partMap.values())
            {
                parts.add(value.getBox());
            }
            this.cache = new CompoundOrientedBox(bounds, parts);
        }
        return this.cache.withBounds(bounds);
    }

    public static EntityBoundsBuilder builder() 
    {
        return new EntityBoundsBuilder();
    }

    public interface Factory
    {
        EntityBounds create();
    }

    public static class EntityBoundsBuilder
    {
        private Map<String, EntityPartInfo> partInfos = new Object2ObjectLinkedOpenHashMap<>();

        EntityBoundsBuilder() 
        {
        	
        }

        EntityBoundsBuilder addInfo(EntityPartInfo info) 
        {
            if(info.parent != null && !this.partInfos.containsKey(info.parent))
            {
                throw new RuntimeException("Unknown part: " + info.parent + ", did you register a child before a parent");
            }
            this.partInfos.put(info.name, info);
            return this;
        }

        public EntityPartInfoBuilder add(String name) 
        {
            if(this.partInfos.containsKey(name))
            {
                throw new RuntimeException("Duplicate part: " + name);
            }
            return new EntityPartInfoBuilder(this, name);
        }

        public Factory getFactory()
        {
            //defensive copy
            Map<String, EntityPartInfo> copy = new Object2ObjectLinkedOpenHashMap<>(this.partInfos);
            return () -> {
                Map<String, EntityPart> partMap = new Object2ObjectOpenHashMap<>();
                for(Map.Entry<String, EntityPartInfo> entry : copy.entrySet())
                {
                    EntityPartInfo info = entry.getValue();
                    EntityPart entityPart = new EntityPart(info.parent != null ? partMap.get(info.parent) : null, info.bounds, false, info.x, info.y, info.z);
                    entityPart.setPivotX(info.px);
                    entityPart.setPivotY(info.py);
                    entityPart.setPivotZ(info.pz);
                    partMap.put(entry.getKey(), entityPart);
                }
                return new EntityBounds(partMap);
            };
        }
    }

    public static class EntityPartInfoBuilder 
    {
        EntityBoundsBuilder builder;
        @Nullable 
        String parent;
        String name;
        double x, y, z;
        double px, py, pz;
        AABB bounds;

        EntityPartInfoBuilder(EntityBoundsBuilder builder, String name) 
        {
            this.builder = builder;
            this.name = name;
        }

        public EntityPartInfoBuilder setOffset(double x, double y, double z)
        {
            this.x = x;
            this.y = y;
            this.z = z;
            return this;
        }

        public EntityPartInfoBuilder setPivot(double x, double y, double z) 
        {
        	this.px = x;
            this.py = y;
            this.pz = z;
            return this;
        }

        public EntityPartInfoBuilder setParent(@Nullable String parent) 
        {
            this.parent = parent;
            return this;
        }

        public EntityPartInfoBuilder setBounds(AABB bounds)
        {
            this.bounds = bounds;
            return this;
        }

        public EntityPartInfoBuilder setBounds(double xLength, double yLength, double zLength) 
        {
        	this.bounds = new AABB(-xLength / 2, -yLength / 2, -zLength / 2, xLength / 2, yLength / 2, zLength / 2);
            return this;
        }

        public EntityBoundsBuilder build() 
        {
            return this.builder.addInfo(new EntityPartInfo(this.parent, this.name, this.x, this.y, this.z, this.px, this.py, this.pz, this.bounds));
        }
    }

    private record EntityPartInfo(@Nullable String parent, String name, double x, double y, double z, double px, double py, double pz, AABB bounds) 
    {
    	
    }
}
