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


public class Renderer {
    int vao, vbo;
    public Renderer() {
        this.vao = glGenVertexArrays();
        this.vbo = glGenBuffers();
    }

    public void render(float _x, float _y, float _w, float _h,
                int shaderProgram, int targetWidth, int targetHeight) {
        float x = _x / 600;
        float y = _y / 600;

        float w = _w / targetWidth;
        float h = _h / targetHeight;

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

        glUseProgram(shaderProgram);
        glDrawArrays(GL_TRIANGLES, 0, 6);
    }

    public float normx(float _x, int targetWidth) {
        return 0;
    }

    public void render(float _x, float _y, float _w, float _h,
                int shaderProgram, int texture, int targetWidth, int targetHeight) {
        float x = _x / 600;
        float y = _y / 600;

        float w = _w / targetWidth;
        float h = _h / targetHeight;

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

        glUseProgram(shaderProgram);
        glBindTexture(GL_TEXTURE_2D, texture);
        glDrawArrays(GL_TRIANGLES, 0, 6);
    }
}
