package org.flame;

import org.lwjgl.BufferUtils;

import java.io.IOException;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.lwjgl.opengl.GL30.*;

public class Texture
{
    int vao, vbo;
    Shader s;

    Texture(String path) throws IOException
    {
        s = new Shader("src/main/resources/vertex.glsl", "src/main/resources/fragment.glsl");
        this.vao = glGenVertexArrays();
        this.vbo = glGenBuffers();


    }
    


    public void render() throws IOException
    {
        float vertices[] = {
            0.0f, 0.0f, 0.0f,
            1.0f, 0.0f, 0.0f,
            1.0f, 1.0f, 0.0f,

            1.0f, 1.0f, 0.0f,
            0.0f, 1.0f, 0.0f,
            0.0f, 0.0f, 0.0f,
        };

        FloatBuffer vertexBuffer = BufferUtils.createFloatBuffer(vertices.length);
        vertexBuffer.put(vertices).flip();

        glBindBuffer(GL_ARRAY_BUFFER, this.vbo);
        glBufferData(GL_ARRAY_BUFFER, vertexBuffer, GL_STATIC_DRAW);
        
        glVertexAttribPointer(0, 3, GL_FLOAT, false, 3 * Float.BYTES, 0);
        glEnableVertexAttribArray(0);

        glUseProgram(s.shaderProgram);
        glBindVertexArray(this.vao);
        glDrawArrays(GL_TRIANGLES, 0, 6);



    }
}
