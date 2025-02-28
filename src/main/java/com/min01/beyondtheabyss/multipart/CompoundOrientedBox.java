package com.min01.beyondtheabyss.multipart;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.jetbrains.annotations.NotNull;

import com.google.common.collect.Iterators;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class CompoundOrientedBox extends AABB implements Iterable<OrientedBox> 
{
    private final Collection<OrientedBox> boxes;

    public CompoundOrientedBox(AABB bounds, Collection<OrientedBox> boxes)
    {
        this(bounds.minX, bounds.minY, bounds.minZ, bounds.maxX, bounds.maxY, bounds.maxZ, boxes);
    }

    public CompoundOrientedBox(double minX, double minY, double minZ, double maxX, double maxY, double maxZ, Collection<OrientedBox> boxes) 
    {
        super(minX, minY, minZ, maxX, maxY, maxZ);
        this.boxes = boxes;
    }
    
    //ChatGPT ahh;
    public VoxelShape convertToVoxelShape() 
    {
        VoxelShape voxelShape = Shapes.empty();
        
        for(OrientedBox box : this.boxes) 
        {
            AABB boundingBox = box.getExtents();
            List<BlockPos> occupiedVoxels = new ArrayList<>();
            
            for(double x = boundingBox.minX; x <= boundingBox.maxX; x++) 
            {
                for(double y = boundingBox.minY; y <= boundingBox.maxY; y++)
                {
                    for(double z = boundingBox.minZ; z <= boundingBox.maxZ; z++)
                    {
                        if(box.contains(x, y, z)) 
                        {
                            occupiedVoxels.add(new BlockPos(x, y, z));
                        }
                    }
                }
            }
            
            for(BlockPos pos : occupiedVoxels) 
            {
                voxelShape = Shapes.or(voxelShape, Shapes.box(pos.getX(), pos.getY(), pos.getZ(), pos.getX() + 1, pos.getY() + 1, pos.getZ() + 1));
            }
        }
        return voxelShape;
    }

    @Override
    public AABB inflate(double x, double y, double z)
    {
    	AABB aabb = super.inflate(x, y, z);
        List<OrientedBox> orientedBoxes = new ObjectArrayList<>(this.boxes.size());
        for(OrientedBox box : this.boxes)
        {
            orientedBoxes.add(box.expand(x, y, z));
        }
        return new CompoundOrientedBox(aabb, orientedBoxes);
    }

    @Override
    public AABB move(double x, double y, double z) 
    {
    	AABB aabb = super.move(x, y, z);
        List<OrientedBox> orientedBoxes = new ObjectArrayList<>(this.boxes.size());
        for(OrientedBox box : this.boxes)
        {
            orientedBoxes.add(box.offset(x, y, z));
        }
        return new CompoundOrientedBox(aabb, orientedBoxes);
    }

    @Override
    public AABB move(BlockPos blockPos) 
    {
        return this.move(blockPos.getX(), blockPos.getY(), blockPos.getZ());
    }

    @Override
    public Optional<Vec3> clip(final @NotNull Vec3 min, final @NotNull Vec3 max)
    {
        double t = Double.MAX_VALUE;
        for(OrientedBox box : this.boxes) 
        {
            double tmp = box.raycast(min, max);
            if(tmp != -1)
            {
                t = Math.min(t, tmp);
            }
        }
        if(t != Double.MAX_VALUE) 
        {
            double d = max.x - min.x;
            double e = max.y - min.y;
            double f = max.z - min.z;
            return Optional.of(min.add(t * d, t * e, t * f));
        }
        return Optional.empty();
    }
    
    @Override
    public Iterator<OrientedBox> iterator() 
    {
        return Iterators.unmodifiableIterator(this.boxes.iterator());
    }

    @Override
    public boolean intersects(double minX, double minY,  double minZ,  double maxX, double maxY, double maxZ)
    {
        return this.intersects(new AABB(minX, minY, minZ, maxX, maxY, maxZ));
    }
    
    @Override
    public boolean intersects(AABB box) 
    {
        for(OrientedBox orientedBox : this.boxes)
        {
            if(orientedBox.intersects(box))
            {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean contains(double x, double y, double z) 
    {
        for(OrientedBox box : this.boxes) 
        {
            if(box.contains(x, y, z))
            {
                return true;
            }
        }
        return false;
    }

    public CompoundOrientedBox withBounds(AABB bounds) 
    {
        return new CompoundOrientedBox(bounds, new ObjectArrayList<>(this.boxes));
    }
}
