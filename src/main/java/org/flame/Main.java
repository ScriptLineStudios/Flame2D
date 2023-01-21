package org.flame;

import java.io.IOException;



public class Main {
	public static void main(String[] args) throws IOException
	{
		Engine e = new Engine("test", 600, 600);
		Texture t = new Texture("src/main/resources/cat.png");

		while(e.windowOpen())
		{
			e.clear(0.0f, 0.0f, 0.0f, 1.0f);
			t.render();
			e.update();
		}
	}
}
