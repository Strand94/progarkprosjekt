package com.sketchy.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.sketchy.game.Communicator;
import com.sketchy.game.SketchyGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		new LwjglApplication(new SketchyGame(), config);

		try {
			Communicator communicator = new Communicator();
			communicator.connect();
			communicator.test();
			communicator.joinLobby(1337, "hans gregor");
		} catch (Exception e) {
			System.out.println("Can't connect to server");
		}

	}
}
