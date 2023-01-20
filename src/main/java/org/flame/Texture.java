package org.flame;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.lwjgl.opengl.GL20.*;

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

    int shdProg;

    int vrtShd = glCreateShader(GL_VERTEX_SHADER);
    glShaderSource(vrtShd, "src/main/resources/vertex.glsl");
    glCompileShader(vrtShd);

    int fragShd = glCreateShader(GL_FRAGMENT_SHADER);
    glShaderSource(fragShd, "src/main/resources/fragment.glsl");
    glCompileShader(fragShd);


    }


    public static String loadAsString(String filePath) throws IOException
    {
        return Files.readString(Path.of(filePath));
    }
}
