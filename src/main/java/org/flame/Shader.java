package org.flame;

import org.lwjgl.BufferUtils;

import java.io.IOException;
import java.nio.FloatBuffer;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

public class Shader
{
    int shaderProgram, vao, vbo;
    Renderer renderer;
    Shader(String vPath, String fPath) throws IOException
    {
        int vertexShader = glCreateShader(GL_VERTEX_SHADER);
        glShaderSource(vertexShader, loadAsString(vPath));
        glCompileShader(vertexShader);

        int fragmentShader = glCreateShader(GL_FRAGMENT_SHADER);
        glShaderSource(fragmentShader, loadAsString(fPath));
        glCompileShader(fragmentShader);

        this.shaderProgram = glCreateProgram();
        glAttachShader(this.shaderProgram, vertexShader);
        glAttachShader(this.shaderProgram, fragmentShader);
        glLinkProgram(this.shaderProgram);

        this.renderer = new Renderer();
    }

    Shader() throws IOException
    {
        String vertex = "#version 330 core\n" +
                "layout (location=0) in vec3 aPos;\n" +
                "layout (location=1) in vec2 coord;\n" +
                "\n" +
                "uniform mat4 transform = mat4(  vec4(1.0, 0.0, 0.0, 0.0),\n" +
                "vec4(0.0, 1.0, 0.0, 0.0),\n" +
                "vec4(0.0, 0.0, 1.0, 0.0),\n" +
                "vec4(0.0, 0.0, 0.0, 1.0));\n" +
                "out vec2 outCoord;\n" +
                "\n" +
                "void main()\n" +
                "{\n" +
                "    gl_Position = transform * vec4(aPos, 1.0);\n" +
                "    outCoord = coord;\n" +
                "}";

        String fragment = "#version 330 core\n" +
                "uniform sampler2D img;\n" +
                "\n" +
                "in vec2 outCoord;\n" +
                "//uniform sampler2D img;\n" +
                "\n" +
                "void main()\n" +
                "{\n" +
                "    gl_FragColor = texture(img, outCoord);\n" +
                "}";
        int vertexShader = glCreateShader(GL_VERTEX_SHADER);
        glShaderSource(vertexShader, vertex);
        glCompileShader(vertexShader);

        int fragmentShader = glCreateShader(GL_FRAGMENT_SHADER);
        glShaderSource(fragmentShader, fragment);
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

    public void uploadFloat(float t, String name)
    {
        int variable = glGetUniformLocation(this.shaderProgram, name);
        glUseProgram(this.shaderProgram);
        glUniform1f(variable, t);
    }

    public void uploadMatrix(String name, FloatBuffer value)
    {
        int textVar = glGetUniformLocation(this.shaderProgram, name);
        glUseProgram(this.shaderProgram);
        glUniformMatrix4fv(textVar, false, value);
    }

    public void uploadTex(int index, String name)
    {
        int location = glGetUniformLocation(shaderProgram, name);
        glUseProgram(shaderProgram);
        glUniform1i(location, index);
    }

    public void render(float _x, float _y, float _w, float _h) throws IOException {
//        this.backEndRender(_x, _y, _w, _h);
        renderer.render(_x, _y, _w, _h, shaderProgram, 600, 600);
    }
}
