package com.min01.beyondtheabyss.block.model.geometry;

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

public class ModelPartUnbakedGeometry implements IUnbakedGeometry<ModelPartUnbakedGeometry>
{
	private final String modelName;

	public ModelPartUnbakedGeometry(String modelName)
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
		IModelBuilder<?> builder = IModelBuilder.of(true, true, context.isGui3d(), context.getTransforms(), overrides, particle, renderTypes, renderTypesFast);
		this.addQuads(context, builder, baker, spriteGetter, modelState, modelLocation);
		return builder.build();
	}

	private void addQuads(IGeometryBakingContext context, IModelBuilder<?> modelBuilder, ModelBaker baker, Function<Material, TextureAtlasSprite> spriteGetter, ModelState modelState, ResourceLocation modelLocation)
	{
		if(!ModelPartModels.hasLayer(this.modelName))
		{
			return;
		}

		TextureAtlasSprite sprite = spriteGetter.apply(context.getMaterial("texture"));
		for(BakedQuad quad : ModelPartBaker.bake(ModelPartModels.getLayer(this.modelName).get(), sprite, modelState))
		{
			modelBuilder.addUnculledFace(quad);
		}
	}

	public static final class Loader implements IGeometryLoader<ModelPartUnbakedGeometry>
	{
		public static final Loader INSTANCE = new Loader();

		@Override
		public ModelPartUnbakedGeometry read(JsonObject jsonObject, JsonDeserializationContext deserializationContext) throws JsonParseException
		{
			if(!jsonObject.has("model"))
			{
				throw new JsonParseException("A model_part model must have a \"model\" member.");
			}

			return new ModelPartUnbakedGeometry(GsonHelper.getAsString(jsonObject, "model"));
		}
	}
}
