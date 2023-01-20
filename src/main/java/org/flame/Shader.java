package org.flame;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.lwjgl.opengl.GL20.*;

public class Shader
{
    int shaderProgram;

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
}
