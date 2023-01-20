package org.flame;

import static org.lwjgl.glfw.GLFW.*;


public class Main {


	public static void main(String[] args)
	{
		Engine e = new Engine("test", 303, 300);

		e.clear(0.0f, 1.0f, 0.0f, 1.0f);

		while(!glfwWindowShouldClose(e.window))
		{
			e.update();
		}
	}
}
