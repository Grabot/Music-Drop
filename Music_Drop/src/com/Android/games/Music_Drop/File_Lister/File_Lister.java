package com.Android.games.Music_Drop.File_Lister;

import java.io.File;
import java.util.ArrayList;

import com.Android.games.Music_Drop.Main;
import com.Android.games.Music_Drop.Load.Screen;
import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class File_Lister implements Screen
{
	
	private Renderer renderer;
	
	private Preferences prefs;
	
	private boolean game_finished = false;
	
	static ArrayList<File> list_of_files = new ArrayList<File>();
	
	int level;
	
	private String[] phone_data;
	private String[] file_paths;
	
	private File initial_file;
	
	private boolean valid = true;
	
	public File_Lister( Application app, OrthographicCamera camera, Stage stage, SpriteBatch batch )
	{
		initial_file = Gdx.files.external( "/" ).file();
		
		System.out.println("initial file is: " + initial_file.toString() );
		Gdx.app.log( Main.LOG, "Creating opening screen" );
		
		prefs = Gdx.app.getPreferences("my-preferences");

		Fill_List( initial_file );
		
		renderer = new Renderer( this, camera, stage, batch );
		
	}
	
	public void Fill_List( File file_path )
	{
		list_of_files.clear();
		
		list_of_files = getfile( file_path );
		
		System.out.println("file path comparison: " + file_path.toString() );
		
		phone_data = new String[ list_of_files.size() ];
		file_paths = new String[ list_of_files.size() ];
		
		for( int i = 0; i < list_of_files.size(); i++ )
		{
			String file = list_of_files.get(i).toString();
			String[] data;
			data = file.split("\\\\|/");
			phone_data[i] = data[( data.length - 1 )];
			for( int k = 0; k < data.length; k++ )
			{
				if( k == 0 || k == 1 || k == 2 )
				{
				}
				else
				{
					if( file_paths[i] == null )
					{
						file_paths[i] = ( "/" +  data[k] );
					}
					else
					{
						file_paths[i] = ( file_paths[i] + "/" +  data[k] );
					}
				}
			}
		}
	}
	
	public File get_Path( int index )
	{
		return list_of_files.get( index );
	}
	
	public String[] get_phone_data()
	{
		return phone_data;
	}
	
	
	static ArrayList<File> fileList = new ArrayList<File>();
	public ArrayList<File> getfile( File dir )//pass fileType as a music , video, etc.               
	{
	    final File listFile[] = dir.listFiles();
	    if (listFile != null && listFile.length > 0) 
	    {
	    	valid = true;
	        for (int i = 0; i < listFile.length; i++) 
	        {
	        	fileList.add( listFile[i] );
	        }
	    }
	    else
	    {
	    	valid = false;
	    }
	    System.out.println( "valid: " + valid );
	    return fileList;
	}
	
	public boolean get_valid()
	{
		return valid;
	}

	@Override
	public void update(float delta, float touchX, float touchY, float width, float height, boolean touched_down, boolean fast_press, boolean back_pressed ) 
	{
		if( back_pressed )
		{
			Game_Finished( 0 );
		}
	}
	
	
	public void set_song_file( int song_index )
	{
		prefs.putString("song_file_name", file_paths[song_index] );
		prefs.flush();
		Gdx.app.log( Main.LOG, "song set: " + prefs.getString( "song_file_name" ));
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
		 renderer.DrawImages( );
	}
}
