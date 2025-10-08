package com.min01.beyondtheabyss.multipart;

import org.jetbrains.annotations.Nullable;

import net.minecraft.core.Direction;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class OrientedBox
{
    private final Vec3 center;
    private final Vec3 halfExtents;
    private final QuaternionD rotation;
    private AABB extents;
    private Matrix3d matrix;
    private Matrix3d inverse;
    private Vec3[] vertices;
    private Vec3[] basis;
    public boolean collide;

    public OrientedBox(AABB box, boolean collide) 
    {
    	this.center = box.getCenter();
    	this.halfExtents = new Vec3(box.getXsize() / 2, box.getYsize() / 2, box.getZsize() / 2);
    	this.rotation = QuaternionD.IDENTITY;
    	this.collide = collide;
    }

    public OrientedBox(Vec3 center, Vec3 halfExtents, QuaternionD rotation)
    {
        this.center = center;
        this.halfExtents = halfExtents;
        this.rotation = rotation;
    }

    public OrientedBox(double minX, double minY, double minZ, double maxX, double maxY, double maxZ, QuaternionD rotation)
    {
    	this.center = new Vec3((minX + maxX) / 2, (minY + maxY) / 2, (minZ + maxZ) / 2);
        this.halfExtents = new Vec3((maxX - minX) / 2, (maxY - minY) / 2, (maxZ - minZ) / 2);
        this.rotation = rotation;
    }

    private OrientedBox(Vec3 center, Vec3 halfExtents, QuaternionD rotation, Matrix3d matrix, Matrix3d inverse, Vec3[] basis)
    {
        this.center = center;
        this.halfExtents = halfExtents;
        this.rotation = rotation;
        this.matrix = matrix;
        this.inverse = inverse;
        this.basis = basis;
    }

    public Matrix3d getMatrix() 
    {
        if(this.matrix == null)
        {
        	this.matrix = new Matrix3d(this.rotation);
        }
        return this.matrix;
    }

    public Matrix3d getInverse() 
    {
        if(this.inverse == null) 
        {
        	this.inverse = this.getMatrix().invert();
        }
        return this.inverse;
    }

    public AABB getExtents() 
    {
        if(this.extents == null) 
        {
        	this.extents = new AABB(this.halfExtents.multiply(-1, -1, -1), this.halfExtents);
        }
        return this.extents;
    }

    public Vec3[] getBasis()
    {
        if(this.basis == null) 
        {
        	this.basis = this.getMatrix().getBasis();
        }
        return this.basis;
    }

    public OrientedBox rotate(QuaternionD quaternion)
    {
        if(QuaternionD.IDENTITY.equals(quaternion))
        {
            return this;
        }
        return new OrientedBox(this.center, this.halfExtents, this.rotation.hamiltonProduct(quaternion));
    }

    public OrientedBox translate(double x, double y, double z) 
    {
        if(x == 0 && y == 0 && z == 0) 
        {
            return this;
        }
        Matrix3d matrix = this.getMatrix();
        double transX = matrix.transformX(x, y, z);
        double transY = matrix.transformY(x, y, z);
        double transZ = matrix.transformZ(x, y, z);
        return new OrientedBox(this.center.add(transX, transY, transZ), this.halfExtents, this.rotation, matrix, this.inverse, this.basis);
    }

    public OrientedBox transform(double x, double y, double z, double pivotX, double pivotY, double pivotZ, QuaternionD quaternion) 
    {
        Vec3 vec = this.getMatrix().transform(x - pivotX, y - pivotY, z - pivotZ);
        boolean bl = quaternion.equals(QuaternionD.IDENTITY);
        return new OrientedBox(this.center.add(vec), this.halfExtents, this.rotation.hamiltonProduct(quaternion), bl ? this.matrix : null, bl ? this.inverse : null, bl ? this.basis : null).translate(pivotX, pivotY, pivotZ);
    }

    public QuaternionD getRotation() 
    {
        return this.rotation;
    }

    public Vec3 getCenter() 
    {
        return this.center;
    }

    public Vec3 getHalfExtents() 
    {
        return this.halfExtents;
    }

    public OrientedBox offset(double x, double y, double z)
    {
        return new OrientedBox(this.center.add(x, y, z), this.halfExtents, this.rotation, this.matrix, this.inverse, this.basis);
    }

    public void computeVertices() 
    {
        AABB box = this.getExtents();
        Vec3[] vertices = getVertices(box);
        this.vertices = new Vec3[8];
        Matrix3d matrix = this.getMatrix();
        for(int i = 0; i < vertices.length; i++)
        {
            this.vertices[i] = matrix.transform(vertices[i]).add(this.center);
        }
    }

    public static Vec3[] getVertices(AABB box) 
    {
        Vec3[] vertices = new Vec3[8];
        int index = 0;
        Direction.AxisDirection[] axisDirections = Direction.AxisDirection.values();
        for(Direction.AxisDirection x : axisDirections)
        {
            for(Direction.AxisDirection y : axisDirections) 
            {
                for(Direction.AxisDirection z : axisDirections) 
                {
                    vertices[index++] = new Vec3(getPoint(box, x, Direction.Axis.X), getPoint(box, y, Direction.Axis.Y), getPoint(box, z, Direction.Axis.Z));
                }
            }
        }
        return vertices;
    }

    private static double getPoint(AABB box, Direction.AxisDirection direction, Direction.Axis axis)
    {
        return direction == Direction.AxisDirection.NEGATIVE ? box.min(axis) : box.max(axis);
    }

    public boolean intersects(AABB other)
    {
        Vec3 aabbCenter = other.getCenter();
        Vec3 aabbHalfExtents = new Vec3(other.getXsize() / 2.0, other.getYsize() / 2.0, other.getZsize() / 2.0);

        Vec3 obbCenter = this.getCenter();
        Vec3 obbHalfExtents = this.getHalfExtents();
        Vec3[] obbAxes = this.getBasis();

        Vec3 T = obbCenter.subtract(aabbCenter);

        Matrix3d R = this.getMatrix();
        Matrix3d AbsR = new Matrix3d(new QuaternionD(0, 0, 0, 1));
        AbsR.m00 = Math.abs(R.m00); AbsR.m01 = Math.abs(R.m01); AbsR.m02 = Math.abs(R.m02);
        AbsR.m10 = Math.abs(R.m10); AbsR.m11 = Math.abs(R.m11); AbsR.m12 = Math.abs(R.m12);
        AbsR.m20 = Math.abs(R.m20); AbsR.m21 = Math.abs(R.m21); AbsR.m22 = Math.abs(R.m22);

        double ra, rb;

        ra = aabbHalfExtents.x;
        rb = obbHalfExtents.x * AbsR.m00 + obbHalfExtents.y * AbsR.m01 + obbHalfExtents.z * AbsR.m02;
        if(Math.abs(T.x) > ra + rb)
        {
        	return false;
        }
        
        ra = aabbHalfExtents.y;
        rb = obbHalfExtents.x * AbsR.m10 + obbHalfExtents.y * AbsR.m11 + obbHalfExtents.z * AbsR.m12;
        if(Math.abs(T.y) > ra + rb) 
        {
        	return false;
        }

        ra = aabbHalfExtents.z;
        rb = obbHalfExtents.x * AbsR.m20 + obbHalfExtents.y * AbsR.m21 + obbHalfExtents.z * AbsR.m22;
        if(Math.abs(T.z) > ra + rb)
        {
        	return false;
        }

        ra = aabbHalfExtents.x * AbsR.m00 + aabbHalfExtents.y * AbsR.m10 + aabbHalfExtents.z * AbsR.m20;
        rb = obbHalfExtents.x;
        if(Math.abs(T.dot(obbAxes[0])) > ra + rb)
        {
        	return false;
        }

        ra = aabbHalfExtents.x * AbsR.m01 + aabbHalfExtents.y * AbsR.m11 + aabbHalfExtents.z * AbsR.m21;
        rb = obbHalfExtents.y;
        if(Math.abs(T.dot(obbAxes[1])) > ra + rb)
        {
        	return false;
        }

        ra = aabbHalfExtents.x * AbsR.m02 + aabbHalfExtents.y * AbsR.m12 + aabbHalfExtents.z * AbsR.m22;
        rb = obbHalfExtents.z;
        if(Math.abs(T.dot(obbAxes[2])) > ra + rb)
        {
        	return false;
        }

        ra = aabbHalfExtents.y * AbsR.m20 + aabbHalfExtents.z * AbsR.m10;
        rb = obbHalfExtents.y * AbsR.m02 + obbHalfExtents.z * AbsR.m01;
        if(Math.abs(T.z * R.m10 - T.y * R.m20) > ra + rb)
        {
        	return false;
        }

        ra = aabbHalfExtents.y * AbsR.m21 + aabbHalfExtents.z * AbsR.m11;
        rb = obbHalfExtents.x * AbsR.m02 + obbHalfExtents.z * AbsR.m00;
        if(Math.abs(T.z * R.m11 - T.y * R.m21) > ra + rb)
        {
        	return false;
        }

        ra = aabbHalfExtents.y * AbsR.m22 + aabbHalfExtents.z * AbsR.m12;
        rb = obbHalfExtents.x * AbsR.m01 + obbHalfExtents.y * AbsR.m00;
        if(Math.abs(T.z * R.m12 - T.y * R.m22) > ra + rb)
        {
        	return false;
        }

        ra = aabbHalfExtents.x * AbsR.m20 + aabbHalfExtents.z * AbsR.m00;
        rb = obbHalfExtents.y * AbsR.m12 + obbHalfExtents.z * AbsR.m11;
        if(Math.abs(T.x * R.m20 - T.z * R.m00) > ra + rb)
        {
        	return false;
        }

        ra = aabbHalfExtents.x * AbsR.m21 + aabbHalfExtents.z * AbsR.m01;
        rb = obbHalfExtents.x * AbsR.m12 + obbHalfExtents.z * AbsR.m10;
        if(Math.abs(T.x * R.m21 - T.z * R.m01) > ra + rb)
        {
        	return false;
        }

        ra = aabbHalfExtents.x * AbsR.m22 + aabbHalfExtents.z * AbsR.m02;
        rb = obbHalfExtents.x * AbsR.m11 + obbHalfExtents.y * AbsR.m10;
        if(Math.abs(T.x * R.m22 - T.z * R.m02) > ra + rb)
        {
        	return false;
        }

        ra = aabbHalfExtents.x * AbsR.m10 + aabbHalfExtents.y * AbsR.m00;
        rb = obbHalfExtents.y * AbsR.m22 + obbHalfExtents.z * AbsR.m21;
        if(Math.abs(T.y * R.m00 - T.x * R.m10) > ra + rb)
        {
        	return false;
        }

        ra = aabbHalfExtents.x * AbsR.m11 + aabbHalfExtents.y * AbsR.m01;
        rb = obbHalfExtents.x * AbsR.m22 + obbHalfExtents.z * AbsR.m20;
        if(Math.abs(T.y * R.m01 - T.x * R.m11) > ra + rb)
        {
        	return false;
        }

        ra = aabbHalfExtents.x * AbsR.m12 + aabbHalfExtents.y * AbsR.m02;
        rb = obbHalfExtents.x * AbsR.m21 + obbHalfExtents.y * AbsR.m20;
        if(Math.abs(T.y * R.m02 - T.x * R.m12) > ra + rb) 
        {
        	return false;
        }

        return true;
    }

    public static Vec3 cross(Vec3 first, Vec3 second)
    {
        return new Vec3(first.y * second.z - first.z * second.y, first.z * second.x - first.x * second.z, first.x * second.y - first.y * second.x);
    }

    public double raycast(Vec3 start, Vec3 end)
    {
        Matrix3d inverse = getInverse();
        Vec3 d = inverse.transform(start.x - this.center.x, start.y - this.center.y, start.z - this.center.z);
        Vec3 e = inverse.transform(end.x - this.center.x, end.y - this.center.y, end.z - this.center.z);
        return raycast0(d, e);
    }

    private double raycast0(Vec3 start, Vec3 end) 
    {
        double d = end.x - start.x;
        double e = end.y - start.y;
        double f = end.z - start.z;
        double[] t = new double[]{1};
        Direction direction = traceCollisionSide(this.getExtents(), start, t, d, e, f);
        if(direction != null)
        {
            return t[0];
        }
        return -1;
    }

    @Nullable
    private static Direction traceCollisionSide(AABB box, Vec3 intersectingVector, double[] traceDistanceResult, double xDelta, double yDelta, double zDelta) 
    {
        Direction approachDirection = null;
        if(xDelta > 1.0E-7D)
        {
            approachDirection = traceCollisionSide(traceDistanceResult, approachDirection, xDelta, yDelta, zDelta, box.minX, box.minY, box.maxY, box.minZ, box.maxZ, Direction.WEST, intersectingVector.x, intersectingVector.y, intersectingVector.z);
        }
        else if(xDelta < -1.0E-7D)
        {
            approachDirection = traceCollisionSide(traceDistanceResult, approachDirection, xDelta, yDelta, zDelta, box.maxX, box.minY, box.maxY, box.minZ, box.maxZ, Direction.EAST, intersectingVector.x, intersectingVector.y, intersectingVector.z);
        }
        if(yDelta > 1.0E-7D)
        {
            approachDirection = traceCollisionSide(traceDistanceResult, approachDirection, yDelta, zDelta, xDelta, box.minY, box.minZ, box.maxZ, box.minX, box.maxX, Direction.DOWN, intersectingVector.y, intersectingVector.z, intersectingVector.x);
        }
        else if(yDelta < -1.0E-7D)
        {
            approachDirection = traceCollisionSide(traceDistanceResult, approachDirection, yDelta, zDelta, xDelta, box.maxY, box.minZ, box.maxZ, box.minX, box.maxX, Direction.UP, intersectingVector.y, intersectingVector.z, intersectingVector.x);
        }
        if(zDelta > 1.0E-7D)
        {
            approachDirection = traceCollisionSide(traceDistanceResult, approachDirection, zDelta, xDelta, yDelta, box.minZ, box.minX, box.maxX, box.minY, box.maxY, Direction.NORTH, intersectingVector.z, intersectingVector.x, intersectingVector.y);
        }
        else if(zDelta < -1.0E-7D)
        {
            approachDirection = traceCollisionSide(traceDistanceResult, approachDirection, zDelta, xDelta, yDelta, box.maxZ, box.minX, box.maxX, box.minY, box.maxY, Direction.SOUTH, intersectingVector.z, intersectingVector.x, intersectingVector.y);
        }
        return approachDirection;
    }

    @Nullable
    private static Direction traceCollisionSide(double[] traceDistanceResult, Direction approachDirection, double xDelta, double yDelta, double zDelta, double begin, double minX, double maxX, double minZ, double maxZ, Direction resultDirection, double startX, double startY, double startZ)
    {
        double d = (begin - startX) / xDelta;
        double e = startY + d * yDelta;
        double f = startZ + d * zDelta;
        if(0.0D < d && d < traceDistanceResult[0] && minX - 1.0E-7D < e && e < maxX + 1.0E-7D && minZ - 1.0E-7D < f && f < maxZ + 1.0E-7D)
        {
            traceDistanceResult[0] = d;
            return resultDirection;
        } 
        else
        {
            return approachDirection;
        }
    }

    public boolean contains(double x, double y, double z) 
    {
        x -= this.center.x;
        y -= this.center.y;
        z -= this.center.z;
        double transX = this.getMatrix().transformX(x, y, z);
        double transY = this.getMatrix().transformY(x, y, z);
        double transZ = this.getMatrix().transformZ(x, y, z);
        return this.getExtents().contains(transX, transY, transZ);
    }

    public double getMax(Direction.Axis axis) 
    {
        Matrix3d matrix = this.getMatrix();
        return switch(axis) 
        {
            case X -> Math.max(matrix.m00, Math.max(matrix.m01, matrix.m02)) * this.halfExtents.x + this.center.x;
            case Y -> Math.max(matrix.m10, Math.max(matrix.m11, matrix.m12)) * this.halfExtents.y + this.center.y;
            case Z -> Math.max(matrix.m20, Math.max(matrix.m21, matrix.m22)) * this.halfExtents.z + this.center.z;
        };
    }

    public double getMin(Direction.Axis axis)
    {
        Matrix3d matrix = this.getMatrix();
        return switch(axis) 
        {
            case X -> Math.min(matrix.m00, Math.min(matrix.m01, matrix.m02)) * this.halfExtents.x + this.center.x;
            case Y -> Math.min(matrix.m10, Math.min(matrix.m11, matrix.m12)) * this.halfExtents.y + this.center.y;
            case Z -> Math.min(matrix.m20, Math.min(matrix.m21, matrix.m22)) * this.halfExtents.z + this.center.z;
        };
    }

    public OrientedBox expand(double x, double y, double z)
    {
        if(x == 0 && y == 0 && z == 0)
        {
            return this;
        }
        return new OrientedBox(this.center, this.halfExtents.add(x / 2, y / 2, z / 2), this.rotation, this.matrix, this.inverse, this.basis);
    }
}
