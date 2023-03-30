package me.wpkg.wpkgos.bombelium;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.stb.STBImage.*;
import static org.lwjgl.system.MemoryUtil.*;

import static org.lwjgl.opengl.GL11.*;

public class Texture
{
    public int id;
    public int width;
    public int height;

    private ByteBuffer pixels;

    public Texture(String filename) throws IOException
    {
        load(new FileInputStream(filename));
    }


    public Texture(InputStream input)
    {
        try
        {
            load(input);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    private void load(InputStream input) throws IOException
    {
        IntBuffer w = memAllocInt(1);
        IntBuffer h = memAllocInt(1);
        IntBuffer comp = memAllocInt(1);

        byte[] pixels_raw = input.readAllBytes();

        ByteBuffer imageBuffer = memAlloc(pixels_raw.length);
        imageBuffer.put(pixels_raw);
        imageBuffer.flip();

        pixels = stbi_load_from_memory(imageBuffer, w, h, comp, STBI_rgb_alpha);
        memFree(imageBuffer);

        this.width = w.get();
        this.height = h.get();

        memFree(w);
        memFree(h);
        memFree(comp);

        input.close();
    }

    public void bind()
    {
        if (id == 0)
        {
            id = glGenTextures();

            glBindTexture(GL_TEXTURE_2D, id);

            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);

            glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, pixels);

            stbi_image_free(pixels);
        }

        glBindTexture(GL_TEXTURE_2D, id);
    }

    public void unbind()
    {
        glBindTexture(GL_TEXTURE_2D, 0);
    }

    public void destroy()
    {
        if (id != 0)
            glDeleteTextures(id);
    }
}
