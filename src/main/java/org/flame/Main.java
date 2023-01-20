package org.flame;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL20.glUseProgram;


public class Main {


	public static void main(String[] args)
	{
		Engine e = new Engine("test", 303, 300);
		Texture t = new Texture("");

		e.clear(0.0f, 0.0f, 0.0f, 1.0f);



		while(!glfwWindowShouldClose(e.window))
		{
			glUseProgram(t.shadProg);
			t.render();
			e.update();
		}
	}
}
