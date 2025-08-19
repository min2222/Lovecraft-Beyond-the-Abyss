package com.min01.beyondtheabyss.misc;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL21;

public class DepthReader 
{
    private int width;
    private int height;
    private int pboId;
    private FloatBuffer depthBuffer;

    public DepthReader(int width, int height)
    {
        this.width = width;
        this.height = height;

        this.pboId = GL15.glGenBuffers();
        GL15.glBindBuffer(GL21.GL_PIXEL_PACK_BUFFER, this.pboId);
        GL15.glBufferData(GL21.GL_PIXEL_PACK_BUFFER, width * height * Float.BYTES, GL15.GL_STREAM_READ);
        GL15.glBindBuffer(GL21.GL_PIXEL_PACK_BUFFER, 0);

        this.depthBuffer = BufferUtils.createFloatBuffer(width * height);
    }

    public FloatBuffer readDepth(int depthTexId)
    {
        GL15.glBindBuffer(GL21.GL_PIXEL_PACK_BUFFER, this.pboId);

        GL11.glBindTexture(GL11.GL_TEXTURE_2D, depthTexId);
        GL11.glGetTexImage(GL11.GL_TEXTURE_2D, 0, GL11.GL_DEPTH_COMPONENT, GL11.GL_FLOAT, 0);

        GL15.glBindBuffer(GL21.GL_PIXEL_PACK_BUFFER, this.pboId);
        ByteBuffer mapped = GL15.glMapBuffer(GL21.GL_PIXEL_PACK_BUFFER, GL15.GL_READ_ONLY, this.width * this.height * Float.BYTES, null);
        if(mapped != null)
        {
        	this.depthBuffer.clear();
            this.depthBuffer.put(mapped.asFloatBuffer());
            this.depthBuffer.flip();
            GL15.glUnmapBuffer(GL21.GL_PIXEL_PACK_BUFFER);
        }

        GL15.glBindBuffer(GL21.GL_PIXEL_PACK_BUFFER, 0);

        return this.depthBuffer;
    }
}