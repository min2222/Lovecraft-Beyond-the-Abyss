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
    
    public static double collide(Direction.Axis pMovementAxis, AABB pCollisionBox, Iterable<OrientedBox> pPossibleHits, double pDesiredOffset)
    {
        for(OrientedBox obb : pPossibleHits) 
        {
        	if(Math.abs(pDesiredOffset) < 1.0E-7D) 
        	{
        		return 0.0D;
        	}
        	pDesiredOffset = obb.collide(pMovementAxis, pCollisionBox, pDesiredOffset);
        }
        return pDesiredOffset;
    }
    
    public double collide(Direction.Axis axis, AABB aabb, double desiredMove) 
    {
        if(Math.abs(desiredMove) < 1.0E-7D) 
        {
            return 0.0D;
        }
        
        if(this.intersects(aabb)) 
        {
            return desiredMove;
        }

        double sign = Math.signum(desiredMove);
        Vec3 axisVec = switch (axis) 
        {
            case X -> new Vec3(sign, 0, 0);
            case Y -> new Vec3(0, sign, 0);
            case Z -> new Vec3(0, 0, sign);
        };

        AABB finalMovedAABB = aabb.move(axisVec.scale(Math.abs(desiredMove)));
        if(!this.intersects(finalMovedAABB)) 
        {
            return desiredMove;
        }

        double low = 0.0;
        double high = Math.abs(desiredMove);

        for(int i = 0; i < 10; i++)
        {
            double mid = (low + high) / 2.0;
            if (mid == low || mid == high) 
            {
                break;
            }

            AABB testAABB = aabb.move(axisVec.scale(mid));
            if(this.intersects(testAABB)) 
            {
                high = mid;
            } 
            else
            {
                low = mid;
            }
        }

        return low * sign;
    }
    
    public Vec3 getDepenetrationVector(AABB other) {
        // 1. 일단 겹치는지 확인 (기존 intersects 로직 활용)
        // 겹치지 않는다면 밀어낼 필요가 없으므로 (0,0,0) 반환
        if (!this.intersects(other)) {
            return Vec3.ZERO;
        }

        // 2. 정점 데이터 준비
        if (this.vertices == null) this.computeVertices();
        Vec3[] obbVerts = this.vertices;
        Vec3[] aabbVerts = getVertices(other);

        double minOverlap = Double.MAX_VALUE; // 가장 작은 겹침 깊이를 저장할 변수
        Vec3 pushAxis = Vec3.ZERO;            // 밀어낼 방향을 저장할 변수

        // 3. 검사할 축(Axis) 목록 정의
        // OBB의 3개 축 + AABB의 3개 축 = 총 6개 축 검사
        // (블로그 설명처럼 정밀한 모서리 충돌까지 하려면 Cross Product 축 9개도 추가해야 하지만, 
        // 마인크래프트 플레이어 충돌 수준에서는 6개면 충분하고 성능상 이득입니다.)
        Vec3[] axes = new Vec3[] {
            this.getBasis()[0], this.getBasis()[1], this.getBasis()[2], // OBB의 x,y,z 축
            new Vec3(1, 0, 0), new Vec3(0, 1, 0), new Vec3(0, 0, 1)     // AABB의 x,y,z 축
        };

        for (Vec3 axis : axes) {
            // 0 벡터 방지
            if (axis.lengthSqr() < 1.0E-9) continue;

            // --- 블로그의 핵심 로직: 투영(Projection) 후 겹침 길이 계산 ---
            
            // OBB 투영
            double min1 = Double.MAX_VALUE, max1 = -Double.MAX_VALUE;
            for (Vec3 v : obbVerts) {
                double proj = v.dot(axis);
                min1 = Math.min(min1, proj);
                max1 = Math.max(max1, proj);
            }

            // AABB 투영
            double min2 = Double.MAX_VALUE, max2 = -Double.MAX_VALUE;
            for (Vec3 v : aabbVerts) {
                double proj = v.dot(axis);
                min2 = Math.min(min2, proj);
                max2 = Math.max(max2, proj);
            }

            // 겹치는 길이(Overlap) 계산
            // (구간 [min1, max1]과 [min2, max2]의 교집합 길이)
            double overlap = Math.min(max1, max2) - Math.max(min1, min2);

            // 만약 겹치는 길이가 0보다 작거나 같다면 분리된 상태임 (충돌 아님)
            if (overlap <= 0) {
                return Vec3.ZERO; 
            }

            // 가장 작은 겹침(Minimum Overlap)을 찾음
            if (overlap < minOverlap) {
                minOverlap = overlap;
                pushAxis = axis;
            }
        }

        // 4. 방향 보정
        // 찾은 축(pushAxis)이 플레이어를 OBB '바깥'으로 밀어내는지 확인해야 함.
        // 플레이어 중심에서 OBB 중심을 뺀 벡터와 내적(dot)하여 방향을 판별
        Vec3 centerDiff = other.getCenter().subtract(this.center);
        if (centerDiff.dot(pushAxis) < 0) {
            pushAxis = pushAxis.scale(-1); // 반대 방향이면 뒤집음
        }

        // 5. 최종 MTV 반환 (방향 * 깊이)
        // 1.0001 같은 아주 작은 값을 더해줘서 부동소수점 오차로 다시 겹치는 것을 방지
        return pushAxis.normalize().scale(minOverlap + 1.0E-4);
    }
    
    public boolean intersects(AABB other)
    {
        return this.intersects(getVertices(other));
    }

    public boolean intersects(Vec3[] otherVertices)
    {
        if(this.vertices == null)
        {
            this.computeVertices();
        }
        Vec3[] vertices1 = this.vertices;
        Vec3[] normals1 = this.getBasis();
        for(Vec3 normal : normals1)
        {
            if(!sat(normal, vertices1, otherVertices))
            {
                return false;
            }
        }
        Vec3[] normals2 = Matrix3d.IDENTITY_BASIS;
        for(Vec3 normal : normals2) 
        {
            if(!sat(normal, vertices1, otherVertices))
            {
                return false;
            }
        }
        for(int i = 0; i < normals1.length; i++)
        {
            for(int j = 0; j < normals2.length; j++) 
            {
                Vec3 normal = cross(normals1[i], normals2[j]);
                if (normal.lengthSqr() < 1.0E-9) {
                    continue;
                }
                if(!sat(normal, vertices1, otherVertices))
                {
                    return false;
                }
            }
        }
        return true;
    }
    
    private static boolean sat(Vec3 normal, Vec3[] vertices1, Vec3[] vertices2)
    {
        double min1 = Double.MAX_VALUE;
        double max1 = -Double.MAX_VALUE;
        for(Vec3 d : vertices1)
        {
        	if(d != null)
        	{
                double v = d.dot(normal);
                min1 = Math.min(min1, v);
                max1 = Math.max(max1, v);
        	}
        }
        double min2 = Double.MAX_VALUE;
        double max2 = -Double.MAX_VALUE;
        for(Vec3 vec3d : vertices2)
        {
            double v = vec3d.dot(normal);
            min2 = Math.min(min2, v);
            max2 = Math.max(max2, v);
        }
        return min1 <= min2 && min2 <= max1 || min2 <= min1 && min1 <= max2;
    }

    public static Vec3 cross(Vec3 first, Vec3 second)
    {
        return new Vec3(first.y * second.z - first.z * second.y, first.z * second.x - first.x * second.z, first.x * second.y - first.y * second.x);
    }

    public double raycast(Vec3 start, Vec3 end)
    {
        Matrix3d inverse = this.getInverse();
        Vec3 d = inverse.transform(start.x - this.center.x, start.y - this.center.y, start.z - this.center.z);
        Vec3 e = inverse.transform(end.x - this.center.x, end.y - this.center.y, end.z - this.center.z);
        return this.raycast0(d, e);
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
