package com.min01.beyondtheabyss.block.model.geometry;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import com.mojang.math.Transformation;

import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.resources.model.ModelState;
import net.minecraftforge.client.model.SimpleModelState;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.Direction;
import net.minecraftforge.client.model.IQuadTransformer;
import net.minecraftforge.client.model.QuadTransformers;

import org.joml.Matrix4f;
import org.joml.Vector3f;

public final class ModelPartBaker
{
	public static List<BakedQuad> bake(LayerDefinition layer, TextureAtlasSprite sprite)
	{
		return bake(layer.bakeRoot(), sprite, new SimpleModelState(Transformation.identity()));
	}

	public static List<BakedQuad> bake(LayerDefinition layer, TextureAtlasSprite sprite, ModelState modelState)
	{
		return bake(layer.bakeRoot(), sprite, modelState);
	}

	public static List<BakedQuad> bake(ModelPart root, TextureAtlasSprite sprite, ModelState modelState)
	{
		List<BakedQuad> quads = new ArrayList<>();
		PoseStack poseStack = new PoseStack();
		poseStack.translate(0.5F, 0.5F, 0.5F);
		poseStack.scale(-1.0F, -1.0F, 1.0F);
		poseStack.translate(0.0F, -1.0F, 0.0F);
		QuadGatheringConsumer consumer = new QuadGatheringConsumer(quads, sprite);
		root.visit(poseStack, (pose, path, index, cube) -> cube.compile(pose, consumer, 0, 0, 1.0F, 1.0F, 1.0F, 1.0F));
		List<BakedQuad> result = new ArrayList<>(quads.size() * 2);
		for(BakedQuad quad : quads)
		{
			result.add(quad);
			result.add(flipQuad(quad));
		}
		return applyModelState(result, modelState);
	}

	public static List<BakedQuad> applyModelState(List<BakedQuad> quads, ModelState modelState)
	{
		Transformation transform = modelState.getRotation().applyOrigin(new Vector3f(0.5F, 0.5F, 0.5F));
		if(transform.isIdentity())
		{
			return quads;
		}
		IQuadTransformer transformer = QuadTransformers.applying(transform);
		Matrix4f rotationMatrix = modelState.getRotation().getMatrix();
		List<BakedQuad> transformed = new ArrayList<>(quads.size());
		for(BakedQuad quad : quads)
		{
			BakedQuad baked = transformer.process(quad);
			Direction direction = Direction.rotate(rotationMatrix, baked.getDirection());
			transformed.add(new BakedQuad(baked.getVertices(), baked.getTintIndex(), direction, baked.getSprite(), baked.isShade(), baked.hasAmbientOcclusion()));
		}
		return transformed;
	}

	private static BakedQuad flipQuad(BakedQuad quad)
	{
		int[] vertices = Arrays.copyOf(quad.getVertices(), quad.getVertices().length);
		swapVertices(vertices, 0, 3);
		swapVertices(vertices, 1, 2);
		for(int i = 0; i < 4; ++i)
		{
			negateNormal(vertices, i);
		}
		return new BakedQuad(vertices, quad.getTintIndex(), quad.getDirection().getOpposite(), quad.getSprite(), quad.isShade(), quad.hasAmbientOcclusion());
	}

	private static void swapVertices(int[] vertices, int a, int b)
	{
		int offsetA = a * IQuadTransformer.STRIDE;
		int offsetB = b * IQuadTransformer.STRIDE;
		for(int i = 0; i < IQuadTransformer.STRIDE; ++i)
		{
			int temp = vertices[offsetA + i];
			vertices[offsetA + i] = vertices[offsetB + i];
			vertices[offsetB + i] = temp;
		}
	}

	private static void negateNormal(int[] vertices, int vertexIndex)
	{
		int offset = vertexIndex * IQuadTransformer.STRIDE + IQuadTransformer.NORMAL;
		int normal = vertices[offset];
		if((normal & 0x00FFFFFF) == 0)
		{
			return;
		}
		float x = (byte)(normal & 0xFF) / 127.0F;
		float y = (byte)((normal >> 8) & 0xFF) / 127.0F;
		float z = (byte)((normal >> 16) & 0xFF) / 127.0F;
		vertices[offset] = packNormal(-x, -y, -z) | (normal & 0xFF000000);
	}

	private static int packNormal(float x, float y, float z)
	{
		return ((int)(x * 127.0F) & 0xFF) | (((int)(y * 127.0F) & 0xFF) << 8) | (((int)(z * 127.0F) & 0xFF) << 16);
	}

	private static final class QuadGatheringConsumer implements VertexConsumer
	{
		private final List<BakedQuad> quads;
		private final TextureAtlasSprite sprite;
		private final int[] quadData = new int[IQuadTransformer.STRIDE * 4];
		private int vertexIndex;
		private float normalX;
		private float normalY;
		private float normalZ;

		private QuadGatheringConsumer(List<BakedQuad> quads, TextureAtlasSprite sprite)
		{
			this.quads = quads;
			this.sprite = sprite;
		}

		@Override
		public VertexConsumer vertex(double x, double y, double z)
		{
			return this;
		}

		@Override
		public VertexConsumer color(int red, int green, int blue, int alpha)
		{
			return this;
		}

		@Override
		public VertexConsumer uv(float u, float v)
		{
			return this;
		}

		@Override
		public VertexConsumer overlayCoords(int u, int v)
		{
			return this;
		}

		@Override
		public VertexConsumer uv2(int u, int v)
		{
			return this;
		}

		@Override
		public VertexConsumer normal(float x, float y, float z)
		{
			return this;
		}

		@Override
		public void endVertex()
		{
			
		}

		@Override
		public void vertex(float x, float y, float z, float red, float green, float blue, float alpha, float texU, float texV, int overlay, int light, float nx, float ny, float nz)
		{
			int offset = this.vertexIndex * IQuadTransformer.STRIDE;
			this.quadData[offset] = Float.floatToRawIntBits(x);
			this.quadData[offset + 1] = Float.floatToRawIntBits(y);
			this.quadData[offset + 2] = Float.floatToRawIntBits(z);
			this.quadData[offset + IQuadTransformer.COLOR] = 0xFFFFFFFF;
			this.quadData[offset + IQuadTransformer.UV0] = Float.floatToRawIntBits(this.sprite.getU(texU * 16.0D));
			this.quadData[offset + IQuadTransformer.UV0 + 1] = Float.floatToRawIntBits(this.sprite.getV(texV * 16.0D));
			this.quadData[offset + IQuadTransformer.UV2] = 0;
			this.quadData[offset + IQuadTransformer.NORMAL] = packNormal(nx, ny, nz);
			if(this.vertexIndex == 0)
			{
				this.normalX = nx;
				this.normalY = ny;
				this.normalZ = nz;
			}
			if(++this.vertexIndex == 4)
			{
				Direction direction = Direction.getNearest(this.normalX, this.normalY, this.normalZ);
				this.quads.add(new BakedQuad(Arrays.copyOf(this.quadData, this.quadData.length), -1, direction, this.sprite, true, true));
				this.vertexIndex = 0;
			}
		}

		@Override
		public void defaultColor(int r, int g, int b, int a)
		{
			
		}

		@Override
		public void unsetDefaultColor()
		{
			
		}
	}
}
