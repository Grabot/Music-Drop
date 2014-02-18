package com.Android.games.Music_Drop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class MainActivityDesktop implements IReqHandler 
{
	private static MainActivityDesktop application;
	
	public static void main(String[] args) 
	{
		
		if (application == null) 
		{
            application = new MainActivityDesktop();
        }
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "Music_Drop";
		cfg.useGL20 = false;
		cfg.width = 480;
		cfg.height = 800;
		
		new LwjglApplication(new Main(application), cfg);
	}

	@Override
	public void showAds(boolean show) {
		// TODO Auto-generated method stub
		
	}
}
