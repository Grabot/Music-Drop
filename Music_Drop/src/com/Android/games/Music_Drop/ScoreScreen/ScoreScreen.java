package com.Android.games.Music_Drop.ScoreScreen;

import com.Android.games.Music_Drop.Main;
import com.Android.games.Music_Drop.Load.Screen;
import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class ScoreScreen implements Screen
{
	
	private OrthographicCamera camera;
	private SpriteBatch batch;
	private Texture texture;
	private Sprite sprite;
	
	private Simulation simulation;
	private Renderer renderer;
	
	private boolean game_finished = false;
	
	private Preferences prefs;
	
	int level;
	
	public ScoreScreen( Application app, OrthographicCamera camera, Stage stage, SpriteBatch batch )
	{
		Gdx.app.log( Main.LOG, "Creating Score Screen" );
		
		prefs = Gdx.app.getPreferences("my-preferences");
		
		simulation = new Simulation( this );
		renderer = new Renderer( prefs.getString("total_score"), prefs.getString( "total_score_song"), prefs.getString( "high_score" ), prefs.getString("high_score_song"), camera, stage, batch );
		
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
