package com.min01.beyondtheabyss.particle;

import java.awt.Color;
import java.util.Locale;

import javax.annotation.Nullable;

import com.min01.beyondtheabyss.util.BTAClientUtil;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.commands.arguments.blocks.BlockStateParser;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.GameData;

//https://github.com/AlexModGuy/AlexsCaves/blob/main/src/main/java/com/github/alexmodguy/alexscaves/client/particle/BigBlockDustParticle.java
public class DustCloudParticle extends TextureSheetParticle 
{
    public static final Object2IntMap<String> TEXTURES_TO_COLOR = new Object2IntOpenHashMap<>();
    private float initialAlpha = 0.5F;
    
    protected DustCloudParticle(ClientLevel world, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed, float size, BlockState state) 
    {
        super(world, x, y, z, xSpeed, ySpeed, zSpeed);
        this.setSize(size, size);
        this.quadSize = size + world.random.nextFloat() * 0.4F;
        this.lifetime = 100 + world.random.nextInt(4);
        this.friction = 0.96F;
        this.setColor(getBlockColor(state, world, BlockPos.containing(x, y, z)));
        this.initialAlpha = 0.0F;
        this.setAlpha(this.initialAlpha);
    }

    @Override
    public void tick()
    {
        this.xo = this.x;
        this.yo = this.y;
        this.zo = this.z;
        if(this.age++ >= this.lifetime)
        {
            this.remove();
        }
        else 
        {
            this.move(this.xd, this.yd, this.zd);
            this.xd *= (double) this.friction;
            this.yd *= (double) this.friction;
            this.zd *= (double) this.friction;
        }
        float f = (float)this.age / this.lifetime;
        this.setAlpha(0.8F * (1.0F - f));
    }

    @Override
    public ParticleRenderType getRenderType()
    {
        return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }

    private void setColor(int intcolor)
    {
        float f = (float) ((intcolor & 16711680) >> 16) / 255.0F;
        float f1 = (float) ((intcolor & '\uff00') >> 8) / 255.0F;
        float f2 = (float) ((intcolor & 255) >> 0) / 255.0F;
        float f3 = random.nextFloat() * 0.3F + 0.7F;
        this.setColor(f * f3, f1 * f3, f2 * f3);
    }

    public static int getBlockColor(BlockState blockState, @Nullable BlockAndTintGetter level, @Nullable BlockPos pos) 
    {
        String blockName = blockState.toString();
        int colorizer = -1;
        try
        {
            colorizer = BTAClientUtil.MC.getBlockColors().getColor(blockState, level, pos, 0);
        }
        catch(Exception e)
        {
        	
        }
        if(TEXTURES_TO_COLOR.containsKey(blockName))
        {
            if(colorizer == -1)
            {
                return TEXTURES_TO_COLOR.getInt(blockName);
            }
            else
            {
                return colorizer;
            }
        }
        else 
        {
            int color = 0XFFFFFF;
            if(colorizer == -1)
            {
                try 
                {
                    Color texColour = getAverageColour(getTextureAtlas(blockState));
                    color = texColour.getRGB();
                } 
                catch(NullPointerException e)
                {
                    e.printStackTrace();
                }
            }
            else
            {
                color = colorizer;
            }
            TEXTURES_TO_COLOR.put(blockName, color);
            return color;
        }
    }

    private static Color getAverageColour(TextureAtlasSprite image)
    {
        float red = 0;
        float green = 0;
        float blue = 0;
        float count = 0;
        int uMax = image.contents().width();
        int vMax = image.contents().height();
        for(float i = 0; i < uMax; i++)
        {
            for(float j = 0; j < vMax; j++)
            {
                int alpha = image.getPixelRGBA(0, (int) i, (int) j) >> 24 & 0xFF;
                if(alpha == 0) 
                {
                    continue;
                }
                red += image.getPixelRGBA(0, (int) i, (int) j) >> 0 & 0xFF;
                green += image.getPixelRGBA(0, (int) i, (int) j) >> 8 & 0xFF;
                blue += image.getPixelRGBA(0, (int) i, (int) j) >> 16 & 0xFF;
                count++;
            }
        }
        //Average color
        return new Color((int) (red / count), (int) (green / count), (int) (blue / count));
    }

    @SuppressWarnings("deprecation")
	private static TextureAtlasSprite getTextureAtlas(BlockState state) 
    {
        return BTAClientUtil.MC.getBlockRenderer().getBlockModelShaper().getBlockModel(state).getParticleIcon();
    }
    
    public static class DustCloudParticleOption implements ParticleOptions
    {
        @SuppressWarnings("deprecation")
		public static final ParticleOptions.Deserializer<DustCloudParticleOption> DESERIALIZER = new ParticleOptions.Deserializer<DustCloudParticleOption>() 
        {
			@Override
			public DustCloudParticleOption fromCommand(ParticleType<DustCloudParticleOption> pParticleType, StringReader pReader) throws CommandSyntaxException 
			{
				pReader.expect(' ');
                float size = pReader.readFloat();
                pReader.expect(' ');
                BlockState state = BlockStateParser.parseForBlock(BuiltInRegistries.BLOCK.asLookup(), pReader, false).blockState();
                return new DustCloudParticleOption(state, size);
			}

			@Override
			public DustCloudParticleOption fromNetwork(ParticleType<DustCloudParticleOption> pParticleType, FriendlyByteBuf pBuffer)
			{
				return new DustCloudParticleOption(pBuffer.readById(Block.BLOCK_STATE_REGISTRY), pBuffer.readFloat());
			}
        };
        
        private final BlockState state;
    	private final float size;
    	
		public DustCloudParticleOption(BlockState state, float size)
		{
			this.state = state;
			this.size = size;
		}
        
		@Override
		public ParticleType<?> getType() 
		{
			return BTAParticles.DUST_CLOUD.get();
		}

		@Override
		public void writeToNetwork(FriendlyByteBuf pBuffer)
		{
			pBuffer.writeId(GameData.getBlockStateIDMap(), this.state);
			pBuffer.writeFloat(this.size);
		}

		@Override
		public String writeToString()
		{
			return String.format(Locale.ROOT, "%s, %.2f", ForgeRegistries.PARTICLE_TYPES.getKey(this.getType()), this.size);
		}
		
		public BlockState getState()
		{
			return this.state;
		}
		
		public float getSize()
		{
			return this.size;
		}
		
		public static Codec<DustCloudParticleOption> CODEC(ParticleType<DustCloudParticleOption> particleType)
		{
			return RecordCodecBuilder.create((codecBuilder) -> codecBuilder.group(
					BlockState.CODEC.fieldOf("state").forGetter(DustCloudParticleOption::getState),
					Codec.FLOAT.fieldOf("size").forGetter(DustCloudParticleOption::getSize)).apply(codecBuilder, (size, state) -> new DustCloudParticleOption(size, state)));
		}
    }

	@OnlyIn(Dist.CLIENT)
    public static class Provider implements ParticleProvider<DustCloudParticleOption> 
    {
        private final SpriteSet sprites;

        public Provider(SpriteSet sprites)
        {
            this.sprites = sprites;
        }

        @Override
        public Particle createParticle(DustCloudParticleOption typeIn, ClientLevel worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) 
        {
            DustCloudParticle particle = new DustCloudParticle(worldIn, x, y, z, xSpeed, ySpeed, zSpeed, typeIn.getSize(), typeIn.getState());
            particle.pickSprite(this.sprites);
            return particle;
        }
    }
}