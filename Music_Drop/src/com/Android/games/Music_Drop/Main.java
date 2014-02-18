package com.Android.games.Music_Drop;

import com.Android.games.Music_Drop.File_Lister.File_Lister;
import com.Android.games.Music_Drop.Load.Screen;
import com.Android.games.Music_Drop.Opening_Screen.Opening_Screen;
import com.Android.games.Music_Drop.PlayScreen.PlayScreen;
import com.Android.games.Music_Drop.ScoreScreen.ScoreScreen;
import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class Main implements ApplicationListener, InputProcessor
{
	public static IReqHandler ExternalHandler;

	public Main(IReqHandler irh)
	{
		Main.ExternalHandler = irh;
	}
	
	public static final String LOG = Main.class.getSimpleName();
	
	private FPSLogger fpsLogger;
	
	private Screen screen;
	
	private float touchX = 0;
	private float touchY = 0;
	
	private float delta = 0;

	private boolean touched_down = false;
	
	private float width = 0;
	private float height = 0;
	
	private float just_touched = 0;
	
	private boolean fast_press = false;
	private boolean back_pressed = false;
	
	private OrthographicCamera camera;
	private SpriteBatch batch;
	private Texture texture;
	private Sprite sprite;
	
	private Stage stage;

	private InputMultiplexer inputMultiplexer = new InputMultiplexer();
	
	@Override
	public void create()
	{
		Main.ExternalHandler.showAds( true );
		
		float width = Gdx.graphics.getWidth();
		float height = Gdx.graphics.getHeight();
		
		fpsLogger = new FPSLogger();
		
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 480, 800);
		camera.position.x = 0;
	    camera.position.y = 0;
	    
	    batch = new SpriteBatch();
	    
	    stage = new Stage( 480, 800, false );

	    inputMultiplexer.addProcessor( this );
	    inputMultiplexer.addProcessor( stage );
		
		Gdx.input.setInputProcessor( inputMultiplexer );
		
        Gdx.input.setCatchBackKey(true);
        
		screen = new Opening_Screen( Gdx.app, camera, batch );
		
		Gdx.app.log( Main.LOG, "Creating game" );
	}

	@Override
	public void dispose()
	{
		
	}

	@Override
	public void render() 
	{
		//clear the screen with a dark blue color. The
		//arguments to glClearColor are the red, green
		//blue and alpha component in the range [0,1]
		//of the color to be used to clear the screen.
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		//tell the camera to update its matrixses.
		camera.update();
		
		//tell the SpriteBatch to render in the
		//coordinate system specified by the camera.
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		
		touch_update();
		
		Application app = Gdx.app;
		
		fpsLogger.log();
		screen.update( delta, touchX, touchY, width, height, touched_down, fast_press, back_pressed );
		
		back_pressed = false;
		
		screen.render( app );
		
		if (screen.isDone( )) 
	    {
	        // dispose the current screen
	        screen.dispose();
	        if( screen.level() == 0 )
	        {
	        	Main.ExternalHandler.showAds( true );
	        	screen = new Opening_Screen( Gdx.app, camera, batch );
	        }
	        else
	        {
		        if( screen.level() == 2 )
		        {
		        	Main.ExternalHandler.showAds( true );
		        	screen = new File_Lister( Gdx.app, camera, stage, batch );
		        }
		        if( screen.level() == 3 )
		        {
		        	Main.ExternalHandler.showAds( false );
		        	screen = new PlayScreen( Gdx.app, camera, stage, batch );
		        }
		        if( screen.level() == 4 )
		        {
		        	Main.ExternalHandler.showAds( true );
		        	screen = new ScoreScreen( Gdx.app, camera, stage, batch );
		        }
	        }
	    }
		

		batch.end();
		
		stage.act(Gdx.graphics.getDeltaTime());
		stage.draw();

		stage.setCamera( camera );
	}

	@Override
	public void resize(int width, int height) 
	{
		this.width = width;
		this.height = height;
	}
	
	private void touch_update()
	{
		
		if (Gdx.input.isTouched())
		{
			Vector3 touchPos = new Vector3();
			touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
			
			touchX = touchPos.x;
			touchY = touchPos.y;
			
			touched_down = true;
			just_touched += 1;
			
		}
		else
		{
			touched_down = false;
			if( (just_touched < 10) && (just_touched != 0) )
			{
				fast_press = true;
				just_touched = 0;
			}
			else
			{
				fast_press = false;
			}
			just_touched = 0;
			
			touchX = 1200;
			touchY = 1200;
		}
		
		delta = Gdx.graphics.getDeltaTime();
		
	}

	@Override
	public void pause() 
	{
		
	}

	@Override
	public void resume() 
	{
		
	}

	@Override
	public boolean keyDown(int keycode) 
	{
		if( keycode == Keys.ESCAPE || keycode == Keys.BACK || keycode == Keys.ENTER )
		{
			System.out.println( "pressed" );
	        back_pressed = true;
	    }
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}
}
