package com.Android.games.Music_Drop.Opening_Screen;

import com.Android.games.Music_Drop.Main;
import com.Android.games.Music_Drop.Load.Screen;
import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Opening_Screen implements Screen
{
	
	private Simulation simulation;
	private Renderer renderer;
	
	private boolean game_finished = false;
	
	int level;
	
	public Opening_Screen( Application app, OrthographicCamera camera, SpriteBatch batch )
	{
		
		Gdx.app.log( Main.LOG, "Creating opening screen" );
		
		simulation = new Simulation( this );
		renderer = new Renderer( camera, batch );
		
	}

	@Override
	public void update(float delta, float touchX, float touchY, float width, float height, boolean touched_down, boolean fast_press, boolean back_pressed ) 
	{
		simulation.update( delta );
		simulation.variables( touchX, touchY, width, height, touched_down, back_pressed );
	}

	@Override
	public void dispose() 
	{
		renderer.dispose();
	}

	public void Game_Finished( int level )
	{
		game_finished = true;
		this.level = level;
	}

	@Override
	public boolean isDone() 
	{
		return game_finished;
	}


	@Override
	public int level() 
	{
		return level;
	}

	@Override
	public void render( Application app ) 
	{
		 renderer.DrawImages( simulation );
	}

}
