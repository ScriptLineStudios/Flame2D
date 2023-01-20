package org.flame;

import org.lwjgl.opengl.GL;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Engine
{
    public long window;

    Engine(String name, int width, int height)
    {
        if(!glfwInit())
            throw new IllegalStateException("Unable to initialize GLFW");

        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);

        this.window = glfwCreateWindow(width, height, name, NULL, NULL);

        if (this.window == NULL )
            throw new RuntimeException("Failed to create the GLFW window");

        glfwMakeContextCurrent(this.window);
        glfwSwapInterval(1);

        glfwShowWindow(this.window);

    }

    void update()
    {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

        glfwSwapBuffers(this.window);
        glfwPollEvents();
    }

    void clear(float r, float g, float b, float a)
    {
        GL.createCapabilities();
        glClearColor(r, g, b, a);
    }


}
