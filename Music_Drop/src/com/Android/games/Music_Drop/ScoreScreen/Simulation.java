package com.Android.games.Music_Drop.ScoreScreen;


import java.util.ArrayList;
import java.util.Random;

import com.Android.games.Music_Drop.Load.Vector;
import com.Android.games.Music_Drop.Objects.ScoreBall;
import com.badlogic.gdx.Gdx;

public class Simulation 
{   
		ScoreBall scoreball;
	
	    public ArrayList<ScoreBall> blue_scoreballs = new ArrayList<ScoreBall>( );
	    public ArrayList<ScoreBall> green_scoreballs = new ArrayList<ScoreBall>( );
	    public ArrayList<ScoreBall> red_scoreballs = new ArrayList<ScoreBall>( );
	    public ArrayList<ScoreBall> yellow_scoreballs = new ArrayList<ScoreBall>( );
	    
	    Random ran = new Random();
    
		private float emma = 0;
		private float watson = 0;

		private float width = 0;
		private float height = 0;

		private float touchX = 0;
		private float touchY = 0;
		
    	private float button_distance = 999;
    	
		private boolean touched_down = false;
		
		private float touch_distance_x = 999;
		private float touch_distance_y = 999;
		
		private int section = 0;
		
		public static final String LOG = Simulation.class.getSimpleName();
                
        private ScoreScreen game;
        
		private float next_note = 0;
    	private boolean just_touched = false;
        private boolean back_touched = false;
        private boolean back_pressed = false;
        
        public Simulation( ScoreScreen game )
        {
        		this.game = game;
        		
                populate( );
        }
        
        public void populate( )
        {
        }
        
        public void variables( float touchX, float touchY, float width, float height, boolean touched_down, boolean back_pressed ) 
        {
    		this.width = width;
    		this.height = height;
    		
    		this.touchX = touchX;
    		this.touchY = touchY;
    		
    		this.touched_down = touched_down;
    		this.back_pressed = back_pressed;
        }
        
        public void update( float delta )
        {
        	check_touch();
        	check_next_level();
        	UpdateScoreBalls_Blue();
        	UpdateScoreBalls_Green();
        	UpdateScoreBalls_Red();
        	UpdateScoreBalls_Yellow();

        	if( next_note < 0 )
        	{
        		int colour = ran.nextInt( 3 );
        		
        		if( colour == 0 )
        		{
            		Add_Blue_ScoreBall();
        		}
        		else if( colour == 1 )
        		{
        			Add_Green_ScoreBall();
        		}
        		else
        		{
        			Add_Red_ScoreBall();
        		}
        		next_note = ran.nextFloat();
        	}
        	else
        	{
        		next_note = next_note-delta;
        	}
        	
        }
        
        private void Add_Red_ScoreBall( )
        {
        		scoreball = new ScoreBall();
    	    	scoreball.position.set( ( ran.nextInt(480) - 240 ), 400 );
    	    	scoreball.set_rotation( ran.nextInt( 360 ), ((ran.nextFloat()*8)-4) );
    	    	scoreball.set_speed( 7 );
    	    	scoreball.set_deceleration( (float)0.03 );
    	    	red_scoreballs.add( scoreball );
    	 }
        
        private void Add_Yellow_ScoreBall( float x, float y )
        {
        	
    		scoreball = new ScoreBall();
    		scoreball.position.set( x, y );
    		scoreball.set_rotation( ran.nextInt( 360 ), ((ran.nextFloat()*8)-4) );
	    	scoreball.set_speed( 7 );
	    	scoreball.set_deceleration( (float)0.03 );
    		yellow_scoreballs.add( scoreball );
    	}
        
        private void Add_Blue_ScoreBall()
        {

    		scoreball = new ScoreBall();
	    	scoreball.position.set( ( ran.nextInt( 480 ) - 240 ), 400 );
	    	scoreball.set_rotation( ran.nextInt( 360 ), ((ran.nextFloat()*8)-4) );
	    	scoreball.set_speed( 7 );
	    	scoreball.set_deceleration( (float)0.03 );
	    	blue_scoreballs.add( scoreball );
    	}
        
        private void Add_Green_ScoreBall()
        {
    		scoreball = new ScoreBall();
    		scoreball.position.set( ( ran.nextInt(480) - 240 ), 400 );
    		scoreball.set_rotation( ran.nextInt( 360 ), ((ran.nextFloat()*8)-4) );
	    	scoreball.set_speed( 7 );
	    	scoreball.set_deceleration( (float)0.03 );
    		green_scoreballs.add( scoreball );
    	}
        
        ArrayList<ScoreBall> scoreball_green_weg = new ArrayList<ScoreBall>();
        private void UpdateScoreBalls_Green()
        {
        	scoreball_green_weg.clear();
        	for( int i = 0; i < green_scoreballs.size(); i++ )
            {
        			ScoreBall scoreball = green_scoreballs.get(i);
        			scoreball.update();
        			
        			if( scoreball.scoreball_weg )
        			{
        				scoreball_green_weg.add(scoreball);
        			}
        			
        			for( int k = 0; k < scoreball_green_weg.size(); k++ )   
                    {
                        green_scoreballs.remove( scoreball_green_weg.get(k) );
                    }
        	}
        }
        
        ArrayList<ScoreBall> scoreball_blue_weg = new ArrayList<ScoreBall>();
        private void UpdateScoreBalls_Blue()
        {
        	scoreball_blue_weg.clear();
        	for( int i = 0; i < blue_scoreballs.size(); i++ )
            {
        			ScoreBall scoreball = blue_scoreballs.get(i);
        			scoreball.update();
        			
        			if( scoreball.scoreball_weg )
        			{
        				scoreball_blue_weg.add(scoreball);
        			}
        			
        			for( int k = 0; k < scoreball_blue_weg.size(); k++ )   
                    {
                        blue_scoreballs.remove( scoreball_blue_weg.get(k) );
                    }
        			
        			
        	}
        }
        
        ArrayList<ScoreBall> scoreball_yellow_weg = new ArrayList<ScoreBall>();
        private void UpdateScoreBalls_Yellow()
        {
        	scoreball_yellow_weg.clear();
        	for( int i = 0; i < yellow_scoreballs.size(); i++ )
            {
        			ScoreBall scoreball = yellow_scoreballs.get(i);
        			scoreball.update();
        			
        			if( scoreball.scoreball_weg )
        			{
        				scoreball_yellow_weg.add(scoreball);
        			}
        			
        			for( int k = 0; k < scoreball_yellow_weg.size(); k++ )   
                    {
                        yellow_scoreballs.remove( scoreball_yellow_weg.get(k) );
                    }
        			
        	}
        }
        
        
        ArrayList<ScoreBall> scoreball_red_weg = new ArrayList<ScoreBall>();
        private void UpdateScoreBalls_Red()
        {
        	scoreball_red_weg.clear();
        	for( int i = 0; i < red_scoreballs.size(); i++ )
            {
        			ScoreBall scoreball = red_scoreballs.get(i);
        			scoreball.update();
        			
        			if( scoreball.scoreball_weg )
        			{
        				scoreball_red_weg.add(scoreball);
        			}
        			
        			for( int k = 0; k < scoreball_red_weg.size(); k++ )   
                    {
                        red_scoreballs.remove( scoreball_red_weg.get(k) );
                    }
        	}
        }
        
        private void check_next_level()
        {
        	if(( touch_distance_x < -200 ) && ( touch_distance_y < -360 ))
        	{
        		back_touched = true;
        	}
        	else
        	{
        	}
        	
        	if( !touched_down && back_touched )
        	{
        		back_touched = false;
        		game.Game_Finished(0);
        	}
        	
        	if( back_pressed )
        	{
        		game.Game_Finished( 0 );
        	}

        	if( touched_down )
        	{
        		if( !just_touched )
        		{
        			Add_Yellow_ScoreBall( touch_distance_x, touch_distance_y );
        		}
        		just_touched = true;
        	}
        	else
        	{
        		just_touched = false;
        	}
        	
        }
        
        private void check_touch()
        {
        	touch_distance_x = new Vector ( width/2, height/2 ).distance( new Vector ( touchX, (height/2)));
        	touch_distance_y = new Vector ( width/2, height/2 ).distance( new Vector ( (width/2), touchY));

        	if (( touchX > ( width / 2) ) && ( touchY > ( height/2 )))
        	{
        		section = 4;
        		
        		touch_distance_x = (touch_distance_x / (width / (float)480));
        		
        		if( touch_distance_y > 0)
        		{
        			touch_distance_y = ((touch_distance_y * -1) / (height / (float)800));
        		}
        	}
        	else if (( touchX > ( width / 2 )) && ( touchY < ( height / 2 )))
        	{
        		section = 2;

        		touch_distance_x = (touch_distance_x / (width / (float)480));     
        		
        		touch_distance_y = (touch_distance_y / (height / (float)800));
        	}
        	else if (( touchX < ( width / 2 )) && ( touchY > ( height / 2 )))
        	{
        		section = 3; 	
        		
        		if( touch_distance_x > 0 )
        		{
        			touch_distance_x = ((touch_distance_x * -1) / (width / (float)480));
        		}	
        		
        		if( touch_distance_y > 0)
        		{
        			touch_distance_y = ((touch_distance_y * -1) / (height / (float)800));
        		}
        	}
        	else if (( touchX < ( width / 2 )) && ( touchY < ( height / 2 )))
        	{
        		section = 1;
        		
        		if( touch_distance_x > 0 )
        		{
        			touch_distance_x = ((touch_distance_x * -1) / (width / (float)480));
        		}
        		
        		touch_distance_y = (touch_distance_y / (height / (float)800));
        	}
        	else
        	{
        		section = 0;
        	}
        }
        
}
