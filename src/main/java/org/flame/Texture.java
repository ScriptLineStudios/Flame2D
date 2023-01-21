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
    int vao, vbo, ebo;
    Shader s;

    Texture(String path) throws IOException
    {
        s = new Shader("src/main/resources/vertex.glsl", "src/main/resources/fragment.glsl");
        this.vao = glGenVertexArrays();
        this.vbo = glGenBuffers();
        this.ebo = glGenBuffers();

        IntBuffer width = BufferUtils.createIntBuffer(1);
        IntBuffer height = BufferUtils.createIntBuffer(1);
        IntBuffer channels = BufferUtils.createIntBuffer(1);
        ByteBuffer image = stbi_load(path, width, height, channels, 0);
        System.out.println(image);

        int texture = glGenTextures();
        glBindTexture(GL_TEXTURE_2D, texture);

        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);

        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGB, width.get(0), height.get(0),
                0, GL_RGB, GL_UNSIGNED_BYTE, image);

        glActiveTexture(GL_TEXTURE0);
        s.uploadTex(0, "tex0");

    }
    


    public void render() throws IOException
    {
//        float vertices[] = {
//                -0.9f, -0.9f, 0.0f,     0.0f, 0.0f,
//                0.9f, -0.9f, 0.0f,      0.0f, 1.0f,
//                0.9f,  0.9f, 0.0f,      1.0f, 1.0f,
//                -0.9f,  0.9f, 0.0f,     1.0f, 0.0f
//        };

//        int[] indices =
//        {
//                0, 2, 1,
//                0, 3, 2,
//        };

        float vertices[] = {
            0.0f, 1.0f, 0.0f,       0.0f, 0.0f,
            1.0f, 1.0f, 0.0f,       1.0f, 0.0f,
            0.0f, 0.0f, 0.0f,       0.0f, 1.0f,
            0.0f, 0.0f, 0.0f,       0.0f, 1.0f,
            1.0f, 0.0f, 0.0f,       1.0f, 1.0f,
            1.0f, 1.0f, 0.0f,       1.0f, 0.0f
        };




        FloatBuffer vertexBuffer = BufferUtils.createFloatBuffer(vertices.length);
        vertexBuffer.put(vertices).flip();

//        IntBuffer indicesBuffer = BufferUtils.createIntBuffer(indices.length);
//        indicesBuffer.put(indices).flip();


        glBindBuffer(GL_ARRAY_BUFFER, this.vbo);
        glBufferData(GL_ARRAY_BUFFER, vertexBuffer, GL_STATIC_DRAW);

//        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, this.ebo);
//        glBufferData(GL_ELEMENT_ARRAY_BUFFER, indicesBuffer, GL_STATIC_DRAW);
        
        glVertexAttribPointer(0, 3, GL_FLOAT, false, 5*Float.BYTES, 0);
        glEnableVertexAttribArray(0);

        glVertexAttribPointer(1, 2, GL_FLOAT, false, 5*Float.BYTES, 3*Float.BYTES);
        glEnableVertexAttribArray(1);

        glUseProgram(s.shaderProgram);
        glBindVertexArray(this.vao);
        glBindTexture(GL_TEXTURE_2D, 0);
//        glDrawElements(GL_TRIANGLES, indices.length, GL_UNSIGNED_INT, 0);
        glDrawArrays(GL_TRIANGLES, 0, 6);



    }
}
