package com.Android.games.Music_Drop.PlayScreen;


import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Random;

import com.Android.games.Music_Drop.Load.Spectrum_Analyzer;
import com.Android.games.Music_Drop.Load.Vector;
import com.Android.games.Music_Drop.Objects.ScoreBall;
import com.badlogic.gdx.Gdx;

public class Simulation 
{   
		private float emma_bass = 0; //de huidige waarde van de bass_range
		private float watson_bass = 0; //de vorige waarde van de bass_range
		private float emma_watson_bass = 0; //totale waarde van het liedje tot nu toe in bass
		private float emma_stone_bass = 0; //gemiddelde waarde van het liedje tot nu toe in bass
		
		private float emma_total = 0;
		
		private float emma_mid = 0; //de huidige waarde van de mid_range
		private float watson_mid = 0; //de vorige waarde van de mid_range
		private float emma_watson_mid = 0;//totale waarde van het liedje tot nu toe in mid
		private float emma_stone_mid = 0; //gemiddelde waarde van het liedje tot nu toe in mid
		
		private float watson_total = 0;

		private float width = 0;
		private float height = 0;

		private float touchX = 0;
		private float touchY = 0;
				
		private float bass_range = 0;
		private float mid_range = 0;
		
		private float button_distance;
    	
		private boolean touched_down = false;
		private boolean back_pressed = false;
		
		private float touch_distance_x = 0;
		private float touch_distance_y = 0;
		
		private int section = 0;
		
		public static final String LOG = Simulation.class.getSimpleName();
                
        private PlayScreen game;
        
        private int seconds = 0;
        
        public ArrayList<ScoreBall> red_scoreballs = new ArrayList<ScoreBall>( );
        public ArrayList<ScoreBall> yellow_scoreballs = new ArrayList<ScoreBall>( );
        public ArrayList<ScoreBall> blue_scoreballs = new ArrayList<ScoreBall>( );
        public ArrayList<ScoreBall> green_scoreballs = new ArrayList<ScoreBall>( );
    	
    	ScoreBall scoreball;
    	
        private Spectrum_Analyzer spectrum;
        
        Random ran = new Random();
        
        private int point;
        
        private int game_score = 0;
        
        private double total_score = 0.0;
        
        private float song_length = 0;
        
        private float elapsed_time = 0;
        
        private double multiplier_bonus = 1;
        
        private DecimalFormat oneDigit;
        
        private boolean game_ended = false;
        private boolean pause = false;
        private boolean paused_touched = false;
        private boolean continue_touched = false;
        private boolean exit_touched = false;
        
        private boolean just_pressed = false;
        
        public Simulation( PlayScreen game, Spectrum_Analyzer spectrum )
        {
        		this.game = game;
        		this.spectrum = spectrum;

                populate( );
        }
        
        public void populate( )
        {
        	oneDigit = new DecimalFormat("#,##0.0");//format to 1 decimal place
        	
        	song_length = spectrum.get_song_length();
        	
            Gdx.app.log( Simulation.LOG, "Game Started" );
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
    		spectrum.set_pause( pause );
    		
        	if( pause )
        	{
        		check_pause();
        	}
        	else
        	{
	        	bass_range = spectrum.get_Bass_Spectrum();
	        	mid_range = spectrum.get_Mid_Spectrum();
	        	
	    		check_spectrum();
	        	UpdateScoreBalls_Green();
	        	UpdateScoreBalls_Red();
	        	UpdateScoreBalls_Yellow();
	        	UpdateScoreBalls_Blue();
	        	
	        	UpdateSongLength( delta );
	        	
	        	check_next_level();
        	}
        	
        	check_touch();
        	
        }
        
        private void check_pause()
        {
        	if((( touch_distance_x > -75 ) && ( touch_distance_x < 75 )) && (( touch_distance_y > 50 ) && ( touch_distance_y < 100 )))
        	{
        		continue_touched = true;
        	}
        	else
        	{
        		continue_touched = false;
        	}
        	
        	if( !touched_down && continue_touched )
        	{
        		pause = false;
        	}
        	
        	if((( touch_distance_x > -75 ) && ( touch_distance_x < 75 )) && (( touch_distance_y > -50 ) && ( touch_distance_y < 0 )))
        	{
        		exit_touched = true;
        	}
        	else
        	{
        		exit_touched = false;
        	}
        	
        	if( !touched_down && exit_touched )
        	{
        		spectrum.stop_playing();
        		game.Game_Finished( 2, "0.0", 0.0 );
        	}

        	if( back_pressed )
        	{
        		pause = false;
        	}
        }
        
        
        private void UpdateSongLength( float delta )
        {
        	elapsed_time += delta;
        	
        	if( elapsed_time > song_length )
        	{
        		elapsed_time -= delta;
        		game_ended = true;
        	}
        	else
        	{
        	}
        }
        
        private void check_spectrum()
        {
        	if( seconds == 0 )
        	{
        		Check_Bass_Peaks();
        		Check_Mid_Peaks();
        		Check_Bonus_Peaks();
        		
        		seconds ++;
            	point++;
            }
        	else if( seconds > 0 )
        	{
        		seconds = 0;
        	}
        	else
        	{
        		seconds++;
        	}
        
        }
        
        private void Check_Bonus_Peaks()
        {
        	emma_total = bass_range + mid_range;

        	if( green_scoreballs.size() > 2 )
        	{
        	}
        	else
        	{
	        	if(( emma_total - watson_total ) > 4000 )
	        	{
	        		Add_Green_ScoreBall(( emma_total - watson_total) );
	        		Add_Red_ScoreBall(( emma_total - watson_total ));
	        	}
	        	else
	        	{
	        	}
        	}
        }
        
        
        private void Check_Bass_Peaks()
        {
			emma_bass = bass_range;
			if( (emma_bass - watson_bass) > 200 )
			{
				Add_Blue_ScoreBall(( emma_bass - watson_bass ));
			}
			else
			{
			}
			
			watson_bass = emma_bass;
			
			emma_watson_bass += emma_bass;
			emma_stone_bass = (emma_watson_bass/point);
        }
        
        private void Check_Mid_Peaks()
        {
        	emma_mid = mid_range;
        	
        	if( ( emma_mid - watson_mid) > 200 )
        	{
        		Add_Blue_ScoreBall(( emma_mid - watson_mid ));
        	}
        	
    		watson_mid = emma_mid;

    		emma_watson_mid += emma_mid;
    		emma_stone_mid = (emma_watson_mid/point);

        }

        private void Add_Blue_ScoreBall( float spectrum_rize )
        {
        	int number_of_balls = (int)(spectrum_rize/800);

        	for( int i = 0; i < number_of_balls; i++ )
        	{
        		scoreball = new ScoreBall();
    	    	scoreball.position.set( ( ran.nextInt( 480 ) - 240 ), 400 );
    	    	scoreball.set_rotation( ran.nextInt( 360 ), ((ran.nextFloat()*8)-4) );
    	    	scoreball.set_speed( 7 );
    	    	scoreball.set_deceleration( (float)0.03 );
    	    	blue_scoreballs.add( scoreball );
        	}
    	 }
        
        private void Add_Red_ScoreBall( float spectrum_rize )
        {
        	int number_of_balls = (int)(spectrum_rize/4000);
			
        	for( int i = 0; i < number_of_balls; i++ )
        	{
        		scoreball = new ScoreBall();
    	    	scoreball.position.set( ( ran.nextInt(480) - 240 ), 400 );
    	    	scoreball.set_rotation( ran.nextInt( 360 ), ((ran.nextFloat()*8)-4) );
    	    	scoreball.set_speed( 7 );
    	    	scoreball.set_deceleration( (float)0.03 );
    	    	red_scoreballs.add( scoreball );
        	}
    	}
        
        private void Add_Yellow_ScoreBall( float spectrum_rize )
        {
        	int number_of_balls = (int)(spectrum_rize/500);
        	
        	for( int i = 0; i < number_of_balls; i++ )
        	{
        		scoreball = new ScoreBall();
        		scoreball.position.set( ( ran.nextInt(480) - 240 ), 400 );
        		scoreball.set_rotation( ran.nextInt( 360 ), ((ran.nextFloat()*8)-4) );
    	    	scoreball.set_speed( 7 );
    	    	scoreball.set_deceleration( (float)0.03 );
        		yellow_scoreballs.add( scoreball );
        	}
    	 }
        
        private void Add_Green_ScoreBall( float spectrum_rize )
        {
        	int number_of_balls = (int)(spectrum_rize/4000);
        	
        	for( int i = 0; i < number_of_balls; i++ )
        	{
        		scoreball = new ScoreBall();
        		scoreball.position.set( ( ran.nextInt(480) - 240 ), 400 );
        		scoreball.set_rotation( ran.nextInt( 360 ), ((ran.nextFloat()*8)-4) );
    	    	scoreball.set_speed( 7 );
    	    	scoreball.set_deceleration( (float)0.03 );
        		green_scoreballs.add( scoreball );
        	}
    	}
        
        private void check_next_level()
        {
        	if( back_pressed )
        	{
        		pause = true;
        	}
        	
        	if(( touch_distance_x < -200 ) && ( touch_distance_y < -360 ))
        	{
        		paused_touched = true;
        	}
        	else
        	{
        	}
        	
        	if( !touched_down && paused_touched )
        	{
        		paused_touched = false;
        		pause = true;
        	}
        	
        	if( game_ended )
        	{
        		game.Game_Finished( 4, oneDigit.format(total_score), total_score );
        	}
        }
        
        ArrayList<ScoreBall> scoreball_green_weg = new ArrayList<ScoreBall>();
        private void UpdateScoreBalls_Green()
        {
        	scoreball_green_weg.clear();
        	for( int i = 0; i < green_scoreballs.size(); i++ )
            {
        			ScoreBall scoreball = green_scoreballs.get(i);
        			scoreball.update();
        			
        			Check_BonusBall_Collision(scoreball);
        			
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
        
        
        ArrayList<ScoreBall> scoreball_yellow_weg = new ArrayList<ScoreBall>();
        private void UpdateScoreBalls_Yellow()
        {
        	scoreball_yellow_weg.clear();
        	for( int i = 0; i < yellow_scoreballs.size(); i++ )
            {
        			ScoreBall scoreball = yellow_scoreballs.get(i);
        			scoreball.update();
        			
        			Check_ScoreBall_Collision(scoreball);
        			
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
        			
        			Check_DeBonusBall_Collision( scoreball );
        			
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
        
        
        ArrayList<ScoreBall> scoreball_blue_weg = new ArrayList<ScoreBall>();
        private void UpdateScoreBalls_Blue()
        {
        	scoreball_blue_weg.clear();
        	for( int i = 0; i < blue_scoreballs.size(); i++ )
            {
        			ScoreBall scoreball = blue_scoreballs.get(i);
        			scoreball.update();
        			
        			Check_ScoreBall_Collision(scoreball);
        			
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
        
        private void Check_DeBonusBall_Collision( ScoreBall check_scoreball )
        {
        	float scoreball_distance = check_scoreball.position.distance( new Vector( touch_distance_x, touch_distance_y ));
			
			if( scoreball_distance < 50 )
			{
				multiplier_bonus -= 0.2;
				check_scoreball.scoreball_gone();
			}
        }
        
        private void Check_BonusBall_Collision( ScoreBall check_scoreball )
        {
        	float scoreball_distance = check_scoreball.position.distance( new Vector( touch_distance_x, touch_distance_y ));
			
			if( scoreball_distance < 50 )
			{
				multiplier_bonus += 0.1;
				check_scoreball.scoreball_gone();
			}
        }
        
        private void Check_ScoreBall_Collision( ScoreBall check_scoreball )
        {
        	float scoreball_distance = check_scoreball.position.distance( new Vector( touch_distance_x, touch_distance_y ));
			
			if( scoreball_distance < 50 )
			{
				game_score += (1 * multiplier_bonus);
				total_score += (1 * multiplier_bonus);
				check_scoreball.scoreball_gone();
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
        
        public float get_elapsed_time()
        {
        	return elapsed_time;
        }
        
        public int getScore()
        {
        	return game_score;
        }
        
        public double getTotalScore()
        {
        	return total_score;
        }
        
        public String Score()
        {
        	return oneDigit.format(total_score);
        }
        
        public String multipier()
        {
			return oneDigit.format(multiplier_bonus);
        }
        
        public boolean pause()
        {
        	return pause;
        }
        
}
