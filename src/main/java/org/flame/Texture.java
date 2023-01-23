package org.flame;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.BufferUtils;

import static org.lwjgl.stb.STBImage.*;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.lwjgl.opengl.GL30.*;

public class Texture
{
    Shader s;
    int texture;
    IntBuffer width, height, channels;

    Renderer renderer;
    public Texture(String path) throws IOException
    {
        this.s = new Shader();
        this.initTexture(path);
    }

    public Texture(String path, Shader shader) throws IOException
    {
        this.s = shader;
        this.initTexture(path);
    }

    private void initTexture(String path)
    {
        this.renderer = new Renderer();

        this.texture = glGenTextures();
        glActiveTexture(GL_TEXTURE0);
        glBindTexture(GL_TEXTURE_2D, this.texture);

        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);

        this.width = BufferUtils.createIntBuffer(1);
        this.height = BufferUtils.createIntBuffer(1);
        this.channels = BufferUtils.createIntBuffer(1);
        ByteBuffer image = stbi_load(path, width, height, channels, 0);
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width.get(0), height.get(0), 0, GL_RGBA, GL_UNSIGNED_BYTE, image);

        glBindTexture(GL_TEXTURE_3D, this.texture);
        this.s.uploadTex(0, "img");
    }

    private void rotate(float _x, float _y, float _r)
    {
        float r = _r * ((float) Math.PI / 180);


        Vector3f center = new Vector3f(_x / 600, _y / 600, 0.0f);
        FloatBuffer floatBuffer = BufferUtils.createFloatBuffer(16);
        try{
            FloatBuffer matrixBuffer = new Matrix4f().translate(center).rotate(r, 0.0f, 0.0f, 1.0f).translate(center.negate()).get(floatBuffer);
            s.uploadMatrix("transform", matrixBuffer);
        } catch(Exception e)
        {}
    }

    public void render(float _x, float _y, float _r) throws IOException
    {
        this.rotate(_x, _y, _r);
        this.renderer.render(_x, _y, width.get(0), height.get(0), s.shaderProgram, texture, this.width.get(0), this.height.get(0));
    }

    public void render(float _x, float _y, float _w, float _h, float _r) throws IOException
    {
        this.rotate(_x, _y, _r);
        this.renderer.render(_x, _y, _w, _h, s.shaderProgram, texture, this.width.get(0), this.height.get(0));
    }

    public void render(float _x, float _y) throws IOException
    {
        this.renderer.render(_x, _y, width.get(0), height.get(0), s.shaderProgram, texture, this.width.get(0), this.height.get(0));
    }

    public void render(float _x, float _y, float _w, float _h) throws IOException
    {
        this.renderer.render(_x, _y, _w, _h, s.shaderProgram, texture, this.width.get(0), this.height.get(0));
    }
}