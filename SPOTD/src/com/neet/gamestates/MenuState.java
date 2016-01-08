package com.neet.gamestates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.neet.main.Juego;
import com.neet.management.GameKeys;
import com.neet.management.GameStateManager;
import com.neet.management.SonidosManager;
import com.neet.management.Save;

public class MenuState extends GameState {
	
	private SpriteBatch sb;
	private BitmapFont titleFont;
	private BitmapFont font;
	//private Texture texturaFondo;
	//private SpriteBatch fondo;
	
	private final String titulo="Space:Path of the Dead";
	private int currentItem;
	private String[] menuItems;
	public MenuState(GameStateManager gsm){
		super(gsm);
		
	}

	public void init(){
		Save.load();
		Juego.musica1.play();
		 
		sb=new SpriteBatch();
		FreeTypeFontGenerator gen= new FreeTypeFontGenerator(Gdx.files.internal("fonts/enervate.ttf"));
		titleFont=gen.generateFont(70);
		titleFont.setColor(Color.RED);
		font=gen.generateFont(30);
		
		menuItems=new String[]{
				"Jugar",
				"Mejores Puntuaciones",
				"Salir"
		};
		
		
		//fondo=new SpriteBatch();
		//texturaFondo= new Texture(Gdx.files.internal("imagenes/800x600_Wallpaper_Blue_Sky.png"));
		
		
	}
	
	public void update(float dt){
		handleInput();
	
	}
	
	public void draw(){
		
		
		//se dibuja el titulo
		sb.setProjectionMatrix(Juego.cam.combined);
		sb.begin();
		
		
		//fondo.begin();
		//fondo.draw(texturaFondo,0,0,800,600);
		
		
		
		float width=titleFont.getBounds(titulo).width;
		titleFont.draw(sb, titulo, (Juego.WIDTH-width)/2, 400);
		//dibujar menu (opciones)
		for(int i=0; i<menuItems.length;i++){
			width=font.getBounds(menuItems[i]).width;
			if(currentItem==i){
				font.setColor(Color.RED);
			}else{
				font.setColor(Color.WHITE);
			}
			font.draw(sb, menuItems[i], (Juego.WIDTH-width)/2, 310-50*i);
		}
		
		
		
		

		sb.end();
		//fondo.end();
		
	}
	
	public void handleInput(){
		
		if(GameKeys.isPressed(GameKeys.UP)){
			SonidosManager.play("cursor");
			
			if(currentItem>0){
				currentItem--;
			}
			
		}
		
		if(GameKeys.isPressed(GameKeys.DOWN)){
			SonidosManager.play("cursor");
			if(currentItem<menuItems.length-1){
				currentItem++;
			}
		}
		
		if(GameKeys.isPressed(GameKeys.ENTER)){
			SonidosManager.play("seleccionar");
			select();                           //seleccionar
		}
		
	}
	
	
	private void select(){
		
		if(currentItem==0){                            //OPCIONES MENU
			gsm.setState(GameStateManager.PLAY);
			
		}else if(currentItem==1){
			gsm.setState(GameStateManager.HIGHSCORE);
			
		}else if(currentItem==2){
			Gdx.app.exit();
			
		}
	}
	
	public void dispose(){
		sb.dispose();
		titleFont.dispose();
		font.dispose();
		
		
		
	}
}
