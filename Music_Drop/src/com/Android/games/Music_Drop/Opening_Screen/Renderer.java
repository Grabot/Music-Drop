package com.Android.games.Music_Drop.Opening_Screen;

import java.util.ArrayList;

import com.Android.games.Music_Drop.Objects.ScoreBall;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Renderer
{
	public static final String LOG = Renderer.class.getSimpleName();
	
	private OrthographicCamera camera;
	private SpriteBatch batch;
	private Sprite sprite;

	private Texture blue_scoreballs;
	private Texture green_scoreballs;
	private Texture red_scoreballs;
	private Texture yellow_scoreballs;
	
	private Texture button_touched;
	private Texture Back_Button_texture;
	
	private Texture Music_Drop;
	private Texture Play_Game;
	
	private float scaling_x = 0;
	private float scaling_y = 0;
	
	public Renderer( OrthographicCamera camera, SpriteBatch batch )
	{

		this.camera = camera;
		this.batch = batch;
		
		blue_scoreballs = new Texture(Gdx.files.internal("graphics/music_notes/music_note_glow_blue_64.png"));
		green_scoreballs = new Texture(Gdx.files.internal("graphics/music_notes/music_note_glow_green_64.png"));
		red_scoreballs = new Texture( Gdx.files.internal( "graphics/music_notes/music_note_glow_red_64.png" ));
		yellow_scoreballs = new Texture( Gdx.files.internal( "graphics/music_notes/music_note_glow_yellow_64.png" ));
		
		button_touched = new Texture( Gdx.files.internal( "graphics/font/Play_Game_button_overlay.png" ));
		Back_Button_texture = new Texture( Gdx.files.internal( "graphics/music_notes/back_button.png" ));
		
		Music_Drop = new Texture( Gdx.files.internal( "graphics/font/Music_Drop.png" ));
		Play_Game = new Texture( Gdx.files.internal( "graphics/font/Play_Game.png" ));
		
		float width = Gdx.graphics.getWidth();
		float height = Gdx.graphics.getHeight();

		scaling_x = width/480;
		scaling_y = height/800;
	}
	
	public void DrawImages( Simulation simulation )
	{

		TextureRegion Music_Drop_region = new TextureRegion( Music_Drop, 0, 0, Music_Drop.getWidth(), Music_Drop.getHeight() );
		TextureRegion play_game_region = new TextureRegion( Play_Game, 0, 0, Play_Game.getWidth(), Play_Game.getHeight() );
		
		batch.draw( Music_Drop_region, -250, 150, 64, 64, 200, 500, 1, 1, -90, false);
		batch.draw( play_game_region, 0, 300, -(Play_Game.getWidth()/2), -(Play_Game.getHeight()/2), 120, 400, 1, 1, -90, false );
		
		Blue_ScoreBalls( simulation.blue_scoreballs );
		Green_ScoreBalls( simulation.green_scoreballs );
		Red_ScoreBalls( simulation.red_scoreballs );
		Yellow_ScoreBalls( simulation.yellow_scoreballs );
		
		Back_Button();
    	
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
	}
	
}