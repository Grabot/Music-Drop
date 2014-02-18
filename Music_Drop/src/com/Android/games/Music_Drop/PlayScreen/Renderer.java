package com.Android.games.Music_Drop.PlayScreen;

import java.util.ArrayList;

import com.Android.games.Music_Drop.Load.Skin_Setup;
import com.Android.games.Music_Drop.Load.Spectrum_Analyzer;
import com.Android.games.Music_Drop.Objects.ScoreBall;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;

public class Renderer
{
	
	public static final String LOG = Renderer.class.getSimpleName();
	
	private OrthographicCamera camera;
	private SpriteBatch batch;
	
	private TextField text;
	private TextField multiplier;
	
	private Texture timer;
	private Texture blue_scoreballs;
	private Texture yellow_scoreballs;
	private Texture green_scoreballs;
	private Texture red_scoreballs;
	private Texture Continue_texture;
	private Texture Exit_texture;
	private Texture pause_button;
	
	private float bar_length = 460;
	
	private float song_length = 0;
	
	int score = 0;
	
	private String total_score = null;
	private String multiplier_bonus = null;
	
	private Stage stage;
	private Skin skin;
	
	private Skin_Setup skin_setup;
	
	private Table text_table;
	
	public Renderer( Spectrum_Analyzer spectrum, OrthographicCamera camera, Stage stage, SpriteBatch batch )
	{
		this.camera = camera;
		this.stage = stage;
		this.batch = batch;
		
		timer = new Texture(Gdx.files.internal("graphics/music_notes/loading_bar.png"));
		
		blue_scoreballs = new Texture(Gdx.files.internal("graphics/music_notes/music_note_glow_blue_64.png"));
		yellow_scoreballs = new Texture(Gdx.files.internal("graphics/music_notes/music_note_glow_yellow_64.png"));
		green_scoreballs = new Texture(Gdx.files.internal("graphics/music_notes/music_note_glow_green_64.png"));
		red_scoreballs = new Texture(Gdx.files.internal("graphics/music_notes/music_note_glow_red_64.png"));
		
		Continue_texture = new Texture( Gdx.files.internal( "graphics/font/Continue.png" ));
		Exit_texture = new Texture( Gdx.files.internal( "graphics/font/Exit.png" ));
		pause_button = new Texture( Gdx.files.internal( "graphics/music_notes/pause_button.png" ));
		
		skin_setup = new Skin_Setup();

		float width = Gdx.graphics.getWidth();
		float height = Gdx.graphics.getHeight();
		
		skin = new Skin();
		
		skin = skin_setup.set_skin();
		
		song_length = spectrum.get_song_length();
		
		text = new TextField( "Score: ", skin );
		multiplier = new TextField( "Multiplier: ", skin );
		
		text.setDisabled( true );
		multiplier.setDisabled( true );
		
		text_table = new Table();
		text_table.setFillParent( true );
		text_table.setX( - ( 480/2 ));
		text_table.setY( - ( 800/2 ) + 280 );

		text_table.add( text ).width( 400 ).row();
		text_table.add( multiplier ).width( 400 );

		stage.addActor( text_table );
	}
	
	public void dispose()
	{
		text_table.clear();
	}
	
	public void DrawImages( Simulation simulation )
	{

		score = simulation.getScore();
		total_score = simulation.Score();
		multiplier_bonus = simulation.multipier();
		
		float elapsed_time = simulation.get_elapsed_time();
		boolean pause = simulation.pause();

		int textureWidth_continue = 40;
		int textureHeight_continue = 40;
		
		TextureRegion pause_region = new TextureRegion( pause_button, 0, 0, pause_button.getWidth(), pause_button.getHeight() );
		
		batch.draw( pause_region, -240, -400, (textureHeight_continue/2f), (textureWidth_continue/2f), textureHeight_continue, textureWidth_continue, 1, 1, -90, false);
    
		Time( elapsed_time );

		Blue_ScoreBalls( simulation.blue_scoreballs );
		Yellow_ScoreBalls( simulation.yellow_scoreballs );
		Green_ScoreBalls( simulation.green_scoreballs );
		Red_ScoreBalls( simulation.red_scoreballs );
		
		text.setText( "Score: " + total_score );
		multiplier.setText( "Multiplier: " + multiplier_bonus +"x" );
		
		if( pause )
		{
			Menu();
		}
		
		
		//stage.act(Gdx.graphics.getDeltaTime());
		//stage.draw();

		//stage.setCamera( camera );
	}
	
	private void Menu()
	{
		int textureWidth_continue = 250;
		int textureHeight_continue = 100;
		
		TextureRegion continue_region = new TextureRegion( Continue_texture, 0, 0, Continue_texture.getWidth(), Continue_texture.getHeight() );
		
		batch.draw( continue_region, -50, -50, (textureHeight_continue/2f), (textureWidth_continue/2f), textureHeight_continue, textureWidth_continue, 1, 1, -90, false);
    
		int textureWidth_exit = 150;
		int textureHeight_exit = 100;
		
		TextureRegion exit_region = new TextureRegion( Exit_texture, 0, 0, Exit_texture.getWidth(), Exit_texture.getHeight() );
		
		batch.draw( exit_region, -50, -100, (textureHeight_exit/2f), (textureWidth_exit/2f), textureHeight_exit, textureWidth_exit, 1, 1, -90, false);

	}
	
	private void Time( float elapsed_time )
	{
		int textureWidth = 32;
		int textureHeight = 32;
		
		TextureRegion time_region = new TextureRegion( timer, 0, 0, timer.getWidth(), timer.getHeight() );
		
		float bar_timer = ((elapsed_time/song_length) * bar_length);	
		
		batch.draw( time_region, -230, 320, (textureWidth/2f), (textureHeight/2f), textureHeight, bar_timer, 1, 1, -90, false);
    	
	}
	
	private void Blue_ScoreBalls ( ArrayList<ScoreBall> scoreballs )
    {
		int textureWidth = 64;
		int textureHeight = 64;
		
		TextureRegion region = new TextureRegion( blue_scoreballs, 0, 0, blue_scoreballs.getWidth(), blue_scoreballs.getHeight() );
		
    	for( int i = 0; i < scoreballs.size(); i++ )
        {
            ScoreBall scoreball = scoreballs.get(i);
            
            float x = ((scoreball.position.x) - (blue_scoreballs.getWidth()/2));
    		float y = ((scoreball.position.y) - (blue_scoreballs.getHeight()/2));
    		
    		batch.draw(region, x, y, (textureWidth/2f), (textureHeight/2f), textureWidth, textureHeight, 1, 1, scoreball.rotation, false);
    	}
    }
	
	private void Yellow_ScoreBalls ( ArrayList<ScoreBall> scoreballs )
    {
		TextureRegion region = new TextureRegion( yellow_scoreballs, 0, 0, yellow_scoreballs.getWidth(), blue_scoreballs.getHeight() );
		
		int textureWidth = yellow_scoreballs.getWidth();
		int textureHeight = yellow_scoreballs.getHeight();
		
    	for( int i = 0; i < scoreballs.size(); i++ )
        {
            ScoreBall scoreball = scoreballs.get(i);
            
    		float x = ((scoreball.position.x) - (yellow_scoreballs.getWidth()/2));
    		float y = ((scoreball.position.y) - (yellow_scoreballs.getHeight()/2));
    		
    		batch.draw(region, x, y, (textureWidth/2f), (textureHeight/2f), textureWidth, textureHeight, 1, 1, scoreball.rotation, false);
    	}
    }
	
	private void Green_ScoreBalls ( ArrayList<ScoreBall> scoreballs )
    {
		TextureRegion region = new TextureRegion( green_scoreballs, 0, 0, green_scoreballs.getWidth(), green_scoreballs.getHeight() );
		
		int textureWidth = green_scoreballs.getWidth();
		int textureHeight = green_scoreballs.getHeight();
		
    	for( int i = 0; i < scoreballs.size(); i++ )
        {
            ScoreBall scoreball = scoreballs.get(i);
            
            float x = ((scoreball.position.x) - (green_scoreballs.getWidth()/2));
    		float y = ((scoreball.position.y) - (green_scoreballs.getHeight()/2));
    		
    		batch.draw(region, x, y, (textureWidth/2f), (textureHeight/2f), textureWidth, textureHeight, 1, 1, scoreball.rotation, false);
    	}
    }
	
	private void Red_ScoreBalls ( ArrayList<ScoreBall> scoreballs )
    {
		TextureRegion region = new TextureRegion( red_scoreballs, 0, 0, red_scoreballs.getWidth(), red_scoreballs.getHeight() );
		
		int textureWidth = red_scoreballs.getWidth();
		int textureHeight = red_scoreballs.getHeight();
		
    	for( int i = 0; i < scoreballs.size(); i++ )
        {
            ScoreBall scoreball = scoreballs.get(i);
            
            float x = ((scoreball.position.x) - (red_scoreballs.getWidth()/2));
    		float y = ((scoreball.position.y) - (red_scoreballs.getHeight()/2));
    		
    		batch.draw(region, x, y, (textureWidth/2f), (textureHeight/2f), textureWidth, textureHeight, 1, 1, scoreball.rotation, false);
    	}
    }
	
}