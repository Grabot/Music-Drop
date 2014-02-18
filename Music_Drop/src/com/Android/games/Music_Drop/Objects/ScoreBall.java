package com.Android.games.Music_Drop.Objects;

import com.Android.games.Music_Drop.Load.Vector;

public class ScoreBall
{
	
    public Vector position = new Vector( );
   
    public boolean scoreball_weg = false;
    
    public float speed = 0;
    public float deceleration = 0;
    
    public float rotation = 0;
    public float rotation_speed = 0;
    
    public void update()
    {
    	position.y -= speed;
    	
    	speed -= deceleration;
    	
    	if( rotation > 360 )
    	{
    		rotation = 0;
    	}
    	else if( rotation < 0 )
    	{
    		rotation = 360;
    	}
    	rotation += rotation_speed;
    	
    	if( (speed < 0.2) && (speed > -0.2) )
    	{
    		scoreball_weg = true;
    	}
    	else
    	{
    		scoreball_weg = false;
    	}
    }
    
    public void scoreball_gone()
    {
    	scoreball_weg = true;
    }
    
    public void set_speed( float speed )
    {
    	this.speed = speed;
    }
    
    public void set_rotation ( float starting_point, float rotation_speed )
    {
    	this.rotation = starting_point;
    	this.rotation_speed = rotation_speed;
    }
    
    public void set_deceleration( float deceleration )
    {
    	this.deceleration = deceleration;
    }
    
}