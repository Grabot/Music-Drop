package com.Android.games.Music_Drop.PlayScreen;

import com.Android.games.Music_Drop.Main;
import com.Android.games.Music_Drop.Load.Screen;
import com.Android.games.Music_Drop.Load.Spectrum_Analyzer;
import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class PlayScreen implements Screen
{
	private Spectrum_Analyzer spectrum;
	
	private Renderer renderer;
	private Simulation simulation;
	
	private boolean game_finished = false;
	
	private Preferences prefs;
	
	int level;
	
	private String full_song_path = null;
	private String song_name = null;
	private String[] song_path;
	
	public PlayScreen( Application app, OrthographicCamera camera, Stage stage, SpriteBatch batch )
	{        
		prefs = Gdx.app.getPreferences("my-preferences");
		
		full_song_path = prefs.getString( "song_file_name" );
		song_path = full_song_path.split("/");
		song_name = song_path[(song_path.length)-1];

		Gdx.app.log( Main.LOG, "Create music game with: " + song_name );
		
		spectrum = new Spectrum_Analyzer( prefs.getString("song_file_name") );
		
		simulation = new Simulation( this, spectrum );
		renderer = new Renderer( spectrum, camera, stage, batch );
		
	}

	
	@Override
	public void dispose() 
	{
		renderer.dispose();
	}

	@Override
	public void update( float delta, float touchX, float touchY, float width, float height, boolean touched_down, boolean fast_press, boolean back_pressed ) 
	{
		simulation.update( delta );
		simulation.variables( touchX, touchY, width, height, touched_down, back_pressed );
	}

	@Override
	public void render(Application app ) 
	{
		  renderer.DrawImages( simulation );
	}
	
	public void Game_Finished( int level, String total_score, double total_score_double )
	{
		game_finished = true;
		this.level = level;
		
		double high_level_score = 0.0;
		
		high_level_score = (double)prefs.getFloat( "high_score_value" );	
			
		if( total_score_double >= high_level_score )
		{
			prefs.putFloat( "high_score_value", (float)total_score_double );
			prefs.putString( "high_score", total_score );
			prefs.putString( "high_score_song", song_name );
		}

		prefs.putString("total_score", total_score );
		prefs.putString( "total_score_song", song_name);
		prefs.flush();
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
}
