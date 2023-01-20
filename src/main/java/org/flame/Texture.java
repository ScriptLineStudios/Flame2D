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
    Texture(String path)
    {
        float[] vertices =
        {
            0.5f, 0.5f, 0.0f,
            0.5f, -0.5f, 0.0f,
            -0.5f, -0.5f, 0.0f,
            -0.5f, 0.5f, 0.0f
        };

        int shadProg;

        int vertShad = glCreateShader(GL_VERTEX_SHADER);
        glShaderSource(vertShad, "src/main/resources/vertex.glsl");
        glCompileShader(vertShad);

        int fragShad = glCreateShader(GL_FRAGMENT_SHADER);
        glShaderSource(fragShad, "src/main/resources/fragment.glsl");
        glCompileShader(fragShad);

        shadProg = glCreateProgram();
        glAttachShader(shadProg, vertShad);
        glAttachShader(shadProg, fragShad);
        glLinkProgram(shadProg);

        int vao, vbo;

        vao = glGenVertexArrays();
        glBindVertexArray(vao);

        FloatBuffer vertexBuffer = BufferUtils.createFloatBuffer(vertices.length);
        vertexBuffer.put(vertices).flip();

        vbo = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vbo);
        glBufferData(GL_ARRAY_BUFFER,vertexBuffer, GL_STATIC_DRAW);

        glVertexAttribPointer(0, 3, GL_FLOAT, false, 3, 3 * Float.BYTES);
        glEnableVertexAttribArray(0);


        glUseProgram(shadProg);


    }


    public static String loadAsString(String filePath) throws IOException
    {
        return Files.readString(Path.of(filePath));
    }


    public static void render()
    {
        glDrawArrays(GL_TRIANGLES, 0, 4);
    }
}
