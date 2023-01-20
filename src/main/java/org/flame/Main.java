package org.flame;

import static org.lwjgl.glfw.GLFW.*;


public class Main {


	public static void main(String[] args)
	{
		Engine e = new Engine("test", 303, 300);
		Texture t = new Texture("");

		e.clear(1.0f, 1.0f, 0.0f, 1.0f);

		while(!glfwWindowShouldClose(e.window))
		{
			t.render();
			e.update();
		}
	}
}
