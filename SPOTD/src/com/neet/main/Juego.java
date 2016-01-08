package com.neet.main;
import javax.swing.text.AbstractDocument.Content;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.neet.management.GameInputProcessor;
import com.neet.management.GameKeys;
import com.neet.management.GameStateManager;
import com.neet.management.SonidosManager;


public class Juego implements ApplicationListener { 	
	
	
	
	public static Music musica1;
	public static int WIDTH;
	public static int HEIGHT;
	
	private SpriteBatch batch;
	
	private Texture textura;
	
	
	public static OrthographicCamera cam;          //2D -> XY
	
	public GameStateManager gsm;
	
	
	public void create(){     
		 musica1 = Gdx.audio.newMusic(Gdx.files.internal("musica/menu.mp3"));
		 musica1.setLooping(true);
		 
		 musica1.setVolume(1);
		Texture.setEnforcePotImages(false);					//solucion pantalla negra
		
		textura=new Texture(Gdx.files.internal("imagenes/agujero.jpg"));
		
		batch=new SpriteBatch();
		
		WIDTH = Gdx.graphics.getWidth();         
		HEIGHT = Gdx.graphics.getHeight();
		
		cam=new OrthographicCamera(WIDTH, HEIGHT);   		//crea una camara centrado en el origen de-1 a 1, por lo que se parametriza
		cam.translate(WIDTH/2, HEIGHT/2);            		//se especifica la nueva ubicacion de la camara
		cam.update();                                		//se aplica la configuracion de la camara

		Gdx.input.setInputProcessor(new GameInputProcessor());
		
		
		
		SonidosManager.load("sonido/explode.wav", "explode");
		SonidosManager.load("sonido/extralife.wav", "extralife");
		SonidosManager.load("sonido/largesaucer.wav", "largesaucer");
		SonidosManager.load("sonido/pulsehigh.wav", "pulsehigh");
		SonidosManager.load("sonido/pulselow.wav", "pulselow");
		SonidosManager.load("sonido/saucershoot.wav", "saucershoot");
		SonidosManager.load("sonido/XWING-Laser.wav", "shoot");
		SonidosManager.load("sonido/smallsaucer.wav", "smallsaucer");
		SonidosManager.load("sonido/thruster.wav", "thuster");
		SonidosManager.load("sonido/OPM.ogg", "fondo");
		SonidosManager.load("sonido/aparicion.wav","aparicion");
		SonidosManager.load("sonido/laser.wav", "laser");
		SonidosManager.load("sonido/Starcraft 2 - Cursor2.mp3","cursor");
		SonidosManager.load("sonido/Starcraft 2 - Select3.mp3", "seleccionar");
		SonidosManager.load("sonido/muerte.mp3", "muerte");
		
		
		gsm = new GameStateManager();
 		
	}
	public void render(){
		
		
	
		//color de la pantalla-->(rojo,verde,azul,negro)
		//Gdx.gl.glClearColor(0, 0, 0,1);
		//Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		batch.begin();
		batch.draw(textura,0,0,800,600);
		batch.end();
		
			 
		
		gsm.update(Gdx.graphics.getDeltaTime());   
		gsm.draw();
		
		
		GameKeys.Update();   //para aplicar la configuracion de teclas
		
		
		
	}
	public void resize(int ancho, int largo){
		
	}
	public void pause(){
		
	}
	public void resume(){
		
	}
	public void dispose(){                                  
		batch.dispose();
		textura.dispose();
		musica1.dispose();
		
	}
	

}
