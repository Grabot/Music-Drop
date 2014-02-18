package com.Android.games.Music_Drop.ScoreScreen;

import java.util.ArrayList;

import com.Android.games.Music_Drop.Load.Skin_Setup;
import com.Android.games.Music_Drop.Objects.ScoreBall;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;

public class Renderer
{
	public static final String LOG = Renderer.class.getSimpleName();
	
	private Texture blue_scoreballs;
	private Texture green_scoreballs;
	private Texture red_scoreballs;
	private Texture yellow_scoreballs;
	
	private Texture Score_text;
	private Texture HighScore_text;
	private Texture With_text;
	
	private Texture Back_Button_texture;
	
	private TextField score;
	private TextField score_song;
	private TextField score_high;
	private TextField score_high_song;
	
	private OrthographicCamera camera;
	private SpriteBatch batch;
	private Texture texture;
	private Sprite sprite;
	
	private Stage stage;
	private Skin skin;
	private Skin_Setup skin_setup;
	private Table text_table;
	
	private float scaling_x = 0;
	private float scaling_y = 0;
	
	private String total_score = null;
	private String total_score_song = null;
	private String high_score = null;
	private String high_score_song = null;
	
	public Renderer( String total_score, String total_score_song, String high_score, String high_score_song, OrthographicCamera camera, Stage stage, SpriteBatch batch )
	{
		this.camera = camera;
		this.stage = stage;
		this.batch = batch;
		
		blue_scoreballs = new Texture(Gdx.files.internal("graphics/music_notes/music_note_glow_blue_64.png"));
		green_scoreballs = new Texture(Gdx.files.internal("graphics/music_notes/music_note_glow_green_64.png"));
		red_scoreballs = new Texture( Gdx.files.internal( "graphics/music_notes/music_note_glow_red_64.png" ));
		yellow_scoreballs = new Texture( Gdx.files.internal( "graphics/music_notes/music_note_glow_yellow_64.png" ));
		
		Score_text = new Texture( Gdx.files.internal( "graphics/font/Score.png" ));
		HighScore_text = new Texture( Gdx.files.internal( "graphics/font/High_Score.png" ));
		With_text = new Texture( Gdx.files.internal( "graphics/font/With.png" ));
		
		Back_Button_texture = new Texture( Gdx.files.internal( "graphics/music_notes/back_button.png" ));
		
		this.total_score = total_score;
		this.total_score_song = total_score_song;
		this.high_score = high_score;
		this.high_score_song = high_score_song;
		
		texture = new Texture(Gdx.files.internal("graphics/music_notes/music_note_glow_blue_64.png"));
		
		skin_setup = new Skin_Setup();
		
		float width = Gdx.graphics.getWidth();
		float height = Gdx.graphics.getHeight();

		scaling_x = width/480;
		scaling_y = height/800;

		
	    skin = new Skin();
		
		skin = skin_setup.set_skin();
		
		score = new TextField( "Score: ", skin );
		score_song = new TextField( "Song name", skin );
		
		score_high = new TextField( "High Score ", skin );
		score_high_song = new TextField( "High Score song ", skin );
		
		score.setDisabled(true);
		score_song.setDisabled(true);
		score_high.setDisabled(true);
		score_high_song.setDisabled(true);
		
		text_table = new Table();
		text_table.setFillParent( true );
		text_table.setX(( -200 ));
		text_table.setY(( -365 ));

		text_table.add( score ).width(( 480 )).row().pad(( 55 ));
		text_table.add( score_song ).width(( 480 )).row().pad(( 55 ));
		text_table.add( score_high ).width(( 480 )).row();
		text_table.add( score_high_song ).width(( 480 ));

		stage.addActor( text_table );
	}
	
	public void DrawImages( Simulation simulation )
	{

		TextureRegion Score_region = new TextureRegion( Score_text, 0, 0, Score_text.getWidth(), Score_text.getHeight() );
		TextureRegion HighScore_region = new TextureRegion( HighScore_text, 0, 0, HighScore_text.getWidth(), HighScore_text.getHeight() );
		TextureRegion With_region = new TextureRegion( With_text, 0, 0, With_text.getWidth(), With_text.getHeight() );
		
		score.setText( total_score );
		score_song.setText( total_score_song );
		score_high.setText( high_score );
		score_high_song.setText( high_score_song );

		Blue_ScoreBalls( simulation.blue_scoreballs );
		Green_ScoreBalls( simulation.green_scoreballs );
		Red_ScoreBalls( simulation.red_scoreballs );
		Yellow_ScoreBalls( simulation.yellow_scoreballs );
		
		Back_Button();
		
		batch.draw( Score_region, -240, 180, 64, 64, 120, 200, 1, 1, -90, false );
		batch.draw( With_region, -240, 60, 64, 64, 80, 160, 1, 1, -90, false );
		
		batch.draw( HighScore_region, -265, -60, 64, 64, 120, 350, 1, 1, -90, false );
		batch.draw( With_region, -240, -170, 64, 64, 80, 160, 1, 1, -90, false );
		
	}
	
	private void Back_Button()
	{
		int textureWidth = 40;
		int textureHeight = 40;
		
		TextureRegion back_button_region = new TextureRegion( Back_Button_texture, 0, 0, Back_Button_texture.getWidth(), Back_Button_texture.getHeight() );
		
		batch.draw( back_button_region, -240, -400, (textureWidth/2f), (textureHeight/2f), textureHeight, textureWidth, 1, 1, -90, false);
    	
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
	
	private void Green_ScoreBalls( ArrayList<ScoreBall> scoreballs )
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
	
	public void dispose()
	{
		text_table.clear();
	}
	
}