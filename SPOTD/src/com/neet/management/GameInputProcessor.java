package com.neet.management;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;

public class GameInputProcessor extends InputAdapter {
	public boolean keyDown(int k){  //se presiona
		if(k==Keys.UP){                                      //si la tecla presionada es up, sera true y se asignara
			GameKeys.setKey(GameKeys.UP, true);  
		}
		if(k==Keys.LEFT){
			GameKeys.setKey(GameKeys.LEFT, true);  
		}
		if(k==Keys.DOWN){
			GameKeys.setKey(GameKeys.DOWN, true);  
		}
		if(k==Keys.RIGHT){
			GameKeys.setKey(GameKeys.RIGHT, true);  
		}
		if(k==Keys.ENTER){
			GameKeys.setKey(GameKeys.ENTER, true);  
		}
		if(k==Keys.ESCAPE){
			GameKeys.setKey(GameKeys.ESCAPE, true);  
		}
		if(k==Keys.SPACE){
			GameKeys.setKey(GameKeys.SPACE, true);  
		}
		if(k==Keys.SHIFT_LEFT || k==Keys.SHIFT_RIGHT){
			GameKeys.setKey(GameKeys.SHIFT, true);  
		}
		if(k==Keys.Z){
			GameKeys.setKey(GameKeys.Z, true);  
		}
		if(k==Keys.X){
			GameKeys.setKey(GameKeys.X, true);  
		}
		
		if(k==Keys.ALT_LEFT || k==Keys.ALT_RIGHT){
			GameKeys.setKey(GameKeys.ALT, true);  
		}
		
		if(k==Keys.V){
			GameKeys.setKey(GameKeys.V, true);  
		}
		
		return true;
		
		
	}
	
	public boolean keyUp(int k){     
		if(k==Keys.UP){
			GameKeys.setKey(GameKeys.UP, false);  
		}
		if(k==Keys.LEFT){
			GameKeys.setKey(GameKeys.LEFT, false);  
		}
		if(k==Keys.DOWN){
			GameKeys.setKey(GameKeys.DOWN, false);  
		}
		if(k==Keys.RIGHT){
			GameKeys.setKey(GameKeys.RIGHT, false);  
		}
		if(k==Keys.ENTER){
			GameKeys.setKey(GameKeys.ENTER, false);  
		}
		if(k==Keys.ESCAPE){
			GameKeys.setKey(GameKeys.ESCAPE, false);  
		}
		if(k==Keys.SPACE){
			GameKeys.setKey(GameKeys.SPACE, false);  
		}
		if(k==Keys.SHIFT_LEFT || k==Keys.SHIFT_RIGHT){
			GameKeys.setKey(GameKeys.SHIFT, false);  
		}
		if(k==Keys.Z){
			GameKeys.setKey(GameKeys.Z, false);  
		}
		
		if(k==Keys.X){
			GameKeys.setKey(GameKeys.X, false);  
		}
		if(k==Keys.ALT_LEFT || k==Keys.ALT_RIGHT){
			GameKeys.setKey(GameKeys.ALT, false);  
		}
		if(k==Keys.V){
			GameKeys.setKey(GameKeys.V, false);  
		}
		return false;
		
	}

}
