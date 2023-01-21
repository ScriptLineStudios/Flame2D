package org.flame;

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
    int vao, vbo, ebo, texture;
    Shader s;
    IntBuffer width, height, channels;
    Texture(String path) throws IOException
    {
        s = new Shader("src/main/resources/vertex.glsl", "src/main/resources/fragment.glsl");
        this.vao = glGenVertexArrays();
        this.vbo = glGenBuffers();


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

    public void render(float _x, float _y) throws IOException {
        this.backEndRender(_x, _y, this.width.get(0), this.height.get(0));
    }

    public void render(float _x, float _y, float _w, float _h) throws IOException {
        this.backEndRender(_x, _y, _w, _h);
    }

    private void backEndRender(float _x, float _y, float _w, float _h) throws IOException
    {
        float x = _x / 600;
        float y = _y / 600;

        float w = _w / this.width.get(0);
        float h = _h / this.height.get(0);

        float vertices[] = {
                x,   y+h,   0.0f,  0.0f, 0.0f,
                x+w, y+h,   0.0f,  1.0f, 0.0f,
                x,   y,     0.0f,  0.0f, 1.0f,
                x,   y,     0.0f,  0.0f, 1.0f,
                x+w, y,     0.0f,  1.0f, 1.0f,
                x+w, y+h,   0.0f,  1.0f, 0.0f
        };

        FloatBuffer vertexBuffer = BufferUtils.createFloatBuffer(vertices.length);
        vertexBuffer.put(vertices).flip();

        glBindBuffer(GL_ARRAY_BUFFER, this.vbo);
        glBufferData(GL_ARRAY_BUFFER, vertexBuffer, GL_STATIC_DRAW);

        glBindVertexArray(this.vao);
        glVertexAttribPointer(0, 3, GL_FLOAT, false, 5 * Float.BYTES, 0);
        glEnableVertexAttribArray(0);

        glVertexAttribPointer(1, 2, GL_FLOAT, false, 5 * Float.BYTES, 3 * Float.BYTES);
        glEnableVertexAttribArray(1);

        glBindVertexArray(this.vao);
    
        glUseProgram(s.shaderProgram);
        glBindTexture(GL_TEXTURE_2D, this.texture);
        glDrawArrays(GL_TRIANGLES, 0, 6);
    }
}