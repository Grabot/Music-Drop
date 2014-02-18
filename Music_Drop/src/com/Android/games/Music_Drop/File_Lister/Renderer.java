package com.Android.games.Music_Drop.File_Lister;

import java.io.File;

import com.Android.games.Music_Drop.Load.Skin_Setup;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class Renderer
{
	public static final String LOG = Renderer.class.getSimpleName();
	
	private Skin skin;
	
	private List list;
	
	private Table table;
	private Table button_table;
	private TextButton music;
	private TextButton back;
	private TextButton select;
	
	private ScrollPane scrollpane;
	
	private float width = 0;
	private float height = 0;
	
	private int song_index = 0;
	
	private File_Lister game;
	
	private Skin_Setup skin_setup;
	
	private InputMultiplexer inputMultiplexer = new InputMultiplexer();
	
	private Stage stage;
	private OrthographicCamera camera;
	private SpriteBatch batch;
	
	private String[] phone_data;
	private File directory;
	
	private String Back_directory = null;
	private File Back_File = null;
	
	private boolean Select_Song = false;
	
	public Renderer( File_Lister game, OrthographicCamera camera, Stage stage, SpriteBatch batch )
	{
		this.stage = stage;
		this.camera = camera;
		this.batch = batch;
		
		this.game = game;
		
		skin_setup = new Skin_Setup();
		
		System.out.println("added");
		
		skin = new Skin();
		
		Menu_Setup();
		
	}
	
	private void Menu_Setup()
	{
		float width = 480;
		float height = 800;
		
		skin = skin_setup.set_skin();
		
		phone_data = game.get_phone_data();
		list = new List( phone_data, skin );
		
		scrollpane = new ScrollPane( list, skin );
		scrollpane.setScrollingDisabled(false, false);

		select = new TextButton( "Select Folder", skin );
		back = new TextButton( "Back", skin );
		
		table = new Table();
		table.setFillParent(true);
		table.setX( -(width/2) );
		table.setY( -(height/2) -100 );
		
		button_table = new Table();
		button_table.setFillParent(true);
		button_table.setX( -( width/2 ));
		button_table.setY( -( height/2 ) + 260 );
		
		music = new TextButton( "Music", skin );
		
		//table.add( music ).width(( 100 * scaling_x )).height(( 60 * scaling_y ));

		table.add( scrollpane ).expandX().expandY().width( 400 ).height( 600 );
		button_table.add( select ).pad( 15 ).width( 150 ).height( 100 );
		button_table.add( back ).pad( 15 ).width( 150 ).height( 100 );
		
		stage.addActor( table );
		stage.addActor( button_table );
		
		scrollpane.addListener( new InputListener() 
		{
			public boolean touchDown( InputEvent event, float x, float y, int pointer, int button )
			{
				return true;
			}
			public void touchUp( InputEvent event, float x, float y, int pointer, int button )
			{
				ScrollPane_Select();
			}
		});
		
		select.addListener( new InputListener()
		{
			public boolean touchDown ( InputEvent event, float x, float y, int pointer, int button )
			{
				return true;
			}
			
			public void touchUp( InputEvent event, float x, float y, int pointer, int button )
			{
				Select_Button();
			}
		});
		
		back.addListener(new InputListener() 
		{
		 	public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) 
		 	{
				return true;
		 	}
		 
		 	public void touchUp (InputEvent event, float x, float y, int pointer, int button) 
		 	{
		 		Back_Button();
		 	}
		});
		
		select.setColor( Color.LIGHT_GRAY );
		back.setColor( Color.LIGHT_GRAY );
		
		if( !Select_Song && game.get_valid() )
		{
			ScrollPane_Select();
		}
	}
	
	
	
	private void Back_Button()
	{
		Back_directory = "";
		Back_File = null;
		Back_Option();
		table.clear();
		button_table.clear();
		
		Back_File = Gdx.files.external( Back_directory ).file();
		System.out.println("back file: " + Back_File.toString() );
		
		game.Fill_List( Back_File );
		
		Menu_Setup();
		//game.Game_Finished( 0 );
	}
	
	private void Select_Button()
	{
		System.out.println("index of list " + list.getSelectedIndex() );
		
		table.clear();
		if( Select_Song )
		{
			button_table.clear();
			if( list.getSelectedIndex() == 0 )
			{
				song_index = 1;
			}
			song_index = list.getSelectedIndex();
			
			game.set_song_file( song_index );
			
			game.Game_Finished( 3 );
		}
		game.Fill_List( directory );
		
		button_table.clear();
		Menu_Setup();
	}
	
	private void ScrollPane_Select()
	{
		if( game.get_valid() )
		{
			song_index = list.getSelectedIndex();
			directory = game.get_Path( song_index );
			//game.set_song_file( file_paths[song_index] );
			Gdx.app.log( "file lister", "send song: " + directory );
			
	
			if( directory.isDirectory() )
			{
				select.setColor( Color.LIGHT_GRAY );
				select.setTouchable( Touchable.enabled );
	
				select.setText("Select Folder");
				Select_Song = false;
			}
			else if( !directory.getName().endsWith("mp3") )
			{
				select.setColor( Color.RED );
				select.setTouchable( Touchable.disabled );
				
				select.setText("can't be used \n as a song");
			}
			else
			{
				select.setTouchable( Touchable.enabled );
				
				select.setColor( Color.GREEN );
				select.setText("Select Song");
				Select_Song = true;
			}
		}
	}
	
	
	private void Back_Option()
	{
		String file = directory.toString();
		
		System.out.println("back option: " + file );
		
		String[] data;
		data = file.split("\\\\|/");
		
		System.out.println("data size: " + data.length );
		if( data.length > 4 )
		{
			
			int end_loop = 0;
			if( game.get_valid() )
			{
				end_loop = ((data.length)-2);
			}
			else
			{
				end_loop = ((data.length)-1);
			}
			
			for( int k = 0; k < end_loop; k++ )
			{
				if( !(k == 0 || k == 1 || k == 2) )
				{
					if( Back_directory == null )
					{
						Back_directory = ( data[k] );
					}
					else
					{
						Back_directory = ( Back_directory + "/" +  data[k] );
					}
				}
			}
		}
		else
		{
			game.Game_Finished( 0 );
		}
		System.out.println("back is: " + Back_directory );
	}
	
	
	public void DrawImages()
	{
	}
	
	public void dispose()
	{
		table.clear();
		button_table.clear();
	}
	
}