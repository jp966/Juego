package com.neet.management;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

public class SonidosManager {
	private static HashMap<String,Sound> sonidos;
	
	static{
		sonidos =new HashMap<String,Sound>();
		
		
	}
	
	public static void load(String path, String nombre){
		Sound sonido=Gdx.audio.newSound(Gdx.files.internal(path));
		sonidos.put(nombre, sonido);
		
	}
	
	
	
	

	
	
	
	public static void play(String name){
		sonidos.get(name).play();
		
	}
	
	public static void loop(String name){
		sonidos.get(name).loop();
		
	}
	
	public static void stop(String name){
		sonidos.get(name).stop();
	}
	
	
	public static void stopAll(){
		for(Sound s: sonidos.values()){
			s.stop();
		}
	}

}
