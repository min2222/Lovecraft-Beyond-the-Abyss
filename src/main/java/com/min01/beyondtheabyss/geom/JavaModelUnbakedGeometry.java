package com.min01.beyondtheabyss.geom;

import java.util.function.Function;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.Material;
import net.minecraft.client.resources.model.ModelBaker;
import net.minecraft.client.resources.model.ModelState;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraftforge.client.RenderTypeGroup;
import net.minecraftforge.client.model.IModelBuilder;
import net.minecraftforge.client.model.geometry.IGeometryBakingContext;
import net.minecraftforge.client.model.geometry.IGeometryLoader;
import net.minecraftforge.client.model.geometry.IUnbakedGeometry;

public class JavaModelUnbakedGeometry implements IUnbakedGeometry<JavaModelUnbakedGeometry>
{
	private final String modelName;

	public JavaModelUnbakedGeometry(String modelName)
	{
		this.modelName = modelName;
	}

	@Override
	public BakedModel bake(IGeometryBakingContext context, ModelBaker baker, Function<Material, TextureAtlasSprite> spriteGetter, ModelState modelState, ItemOverrides overrides, ResourceLocation modelLocation)
	{
		TextureAtlasSprite particle = spriteGetter.apply(context.getMaterial("particle"));
		ResourceLocation renderTypeHint = context.getRenderTypeHint();
		RenderTypeGroup renderTypes = renderTypeHint != null ? context.getRenderType(renderTypeHint) : RenderTypeGroup.EMPTY;
		ResourceLocation renderTypeFastHint = context.getRenderTypeFastHint();
		RenderTypeGroup renderTypesFast = renderTypeFastHint != null ? context.getRenderType(renderTypeFastHint) : RenderTypeGroup.EMPTY;
		IModelBuilder<?> builder = IModelBuilder.of(context.useAmbientOcclusion(), context.useBlockLight(), context.isGui3d(), context.getTransforms(), overrides, particle, renderTypes, renderTypesFast);
		this.addQuads(context, builder, baker, spriteGetter, modelState, modelLocation);
		return builder.build();
	}

	private void addQuads(IGeometryBakingContext context, IModelBuilder<?> modelBuilder, ModelBaker baker, Function<Material, TextureAtlasSprite> spriteGetter, ModelState modelState, ResourceLocation modelLocation)
	{
		if(!JavaModels.hasLayer(this.modelName))
		{
			return;
		}

		TextureAtlasSprite sprite = spriteGetter.apply(context.getMaterial("texture"));
		for(BakedQuad quad : JavaModelBaker.bake(JavaModels.getLayer(this.modelName).get(), sprite, modelState))
		{
			modelBuilder.addUnculledFace(quad);
		}
	}

	public static final class Loader implements IGeometryLoader<JavaModelUnbakedGeometry>
	{
		public static final Loader INSTANCE = new Loader();

		@Override
		public JavaModelUnbakedGeometry read(JsonObject jsonObject, JsonDeserializationContext deserializationContext) throws JsonParseException
		{
			if(!jsonObject.has("model"))
			{
				throw new JsonParseException("A java_model model must have a \"model\" member.");
			}
			return new JavaModelUnbakedGeometry(GsonHelper.getAsString(jsonObject, "model"));
		}
	}
}
