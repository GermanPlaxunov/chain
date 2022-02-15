package org.project.html;

import org.project.core.GameLauncher;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;

public class GameLauncherHtml extends GwtApplication {
	@Override
	public ApplicationListener getApplicationListener () {
		return new GameLauncher();
	}
	
	@Override
	public GwtApplicationConfiguration getConfig () {
		return new GwtApplicationConfiguration(480, 320);
	}
}
