package com.neet.gamestates;

import com.neet.management.GameStateManager;

public abstract class GameState {
	protected GameStateManager gsm;
	protected GameState(GameStateManager gsm){
		this.gsm=gsm;
	    init();
		
	}
	public abstract void init();                     //inicio aplicacion
	public abstract void update(float dt);          
	public abstract void draw();                    
	public abstract void handleInput();             
	public abstract void dispose();        
	
	

}
