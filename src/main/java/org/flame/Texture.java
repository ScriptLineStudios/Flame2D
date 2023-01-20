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
    int vao, vbo, shaderProgram;
    Texture(String path) throws IOException
    {
        this.vao = glGenVertexArrays();
        this.vbo = glGenBuffers();

        int vertexShader = glCreateShader(GL_VERTEX_SHADER);
        glShaderSource(vertexShader, loadAsString("src/main/resources/vertex.glsl"));
        glCompileShader(vertexShader);

        int fragmentShader = glCreateShader(GL_FRAGMENT_SHADER);
        glShaderSource(fragmentShader, loadAsString("src/main/resources/fragment.glsl"));
        glCompileShader(fragmentShader);

        this.shaderProgram = glCreateProgram();
        glAttachShader(this.shaderProgram, vertexShader);
        glAttachShader(this.shaderProgram, fragmentShader);
        glLinkProgram(this.shaderProgram);
    }
    
    public static String loadAsString(String filePath) throws IOException
    {
        return Files.readString(Path.of(filePath));
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

        glUseProgram(this.shaderProgram);
        glBindVertexArray(this.vao);
        glDrawArrays(GL_TRIANGLES, 0, 6);



    }
}
