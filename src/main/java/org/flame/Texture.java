package org.flame;

import org.lwjgl.BufferUtils;

import java.io.IOException;
import java.nio.FloatBuffer;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

public class Texture
{
    int vao, vbo;
    int shadProg;



    Texture(String path) throws IOException
    {
        float[] vertices =
        {
            0.5f, 0.5f, 0.0f,
            0.5f, -0.5f, 0.0f,
            -0.5f, -0.5f, 0.0f
        };


        int vertShad = glCreateShader(GL_VERTEX_SHADER);
        glShaderSource(vertShad, loadAsString("src/main/resources/vertex.glsl"));
        glCompileShader(vertShad);

        int fragShad = glCreateShader(GL_FRAGMENT_SHADER);
        glShaderSource(fragShad, loadAsString("src/main/resources/fragment.glsl"));
        glCompileShader(fragShad);

        this.shadProg = glCreateProgram();
        glAttachShader(this.shadProg, vertShad);
        glAttachShader(this.shadProg, fragShad);
        glLinkProgram(this.shadProg);


        this.vao = glGenVertexArrays();
        glBindVertexArray(this.vao);

        FloatBuffer vertexBuffer = BufferUtils.createFloatBuffer(vertices.length);
        vertexBuffer.put(vertices).flip();

        this.vbo = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, this.vbo);
        glBufferData(GL_ARRAY_BUFFER,vertexBuffer, GL_STATIC_DRAW);

        glVertexAttribPointer(0, 3, GL_FLOAT, false, 3, 3 * Float.BYTES);
        glEnableVertexAttribArray(0);


        glUseProgram(shadProg);


    }


    public static String loadAsString(String filePath) throws IOException
    {
        return Files.readString(Path.of(filePath));
    }


    public void render()
    {
        float[] vertices =
        {
                0.5f, 0.5f, 0.0f,
                0.5f, -0.5f, 0.0f,
                -0.5f, -0.5f, 0.0f
        };



        glBindVertexArray(this.vao);

        FloatBuffer vertexBuffer = BufferUtils.createFloatBuffer(vertices.length);
        vertexBuffer.put(vertices).flip();

        glBindBuffer(GL_ARRAY_BUFFER, this.vbo);
        glBufferData(GL_ARRAY_BUFFER, vertexBuffer, GL_STATIC_DRAW);

        glUseProgram(shadProg);
        glDrawArrays(GL_TRIANGLES, 0, 3);
    }
}
