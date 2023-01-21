package org.flame;

import java.io.IOException;
import static org.lwjgl.glfw.GLFW.*;

public class Main {
	public static void main(String[] args) throws IOException
	{
		Engine e = new Engine("test", 600, 600);

		Shader customShader = new Shader("src/main/resources/vertex.glsl", "src/main/resources/fragment.glsl");
		Texture t = new Texture("src/main/resources/cat.png");
		int x = 0;
		int r = 0;
		while(e.windowOpen())
		{
			r++;
			e.clear(0.0f, 0.0f, 0.0f, 1.0f);
			if (e.getKeyDown(GLFW_KEY_A))
			{
				x += 4;
			}
			t.render(x, 0.0f, r);
			e.update();
		}
	}
}
