package com.neet.gamestates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.neet.main.Juego;
import com.neet.management.GameKeys;
import com.neet.management.GameStateManager;
import com.neet.management.SonidosManager;
import com.neet.management.Save;

public class GameOverState extends GameState {
	private SpriteBatch sb;
	private ShapeRenderer sr;
	private boolean newHighScore;
	private char[] newName;
	private int currentChar;
	
	private BitmapFont gameOverFont;
	private BitmapFont font;
	
	private SpriteBatch fondo;
	private Texture texturaFondo;
	
	public GameOverState(GameStateManager gsm){
		super(gsm);
	}
	
	public void init(){
		sb=new SpriteBatch();
		sr=new ShapeRenderer();
		
		fondo=new SpriteBatch();
		texturaFondo=new Texture(Gdx.files.internal("imagenes/muerte.jpg"));
		SonidosManager.play("muerte");
		newHighScore=Save.gd.isHighScore(Save.gd.getTentativeScore()); //guardar puntos
		if(newHighScore){
			newName=new char[]{'A','A','A'}; //nombre por defecto
			currentChar=0;
		}
		FreeTypeFontGenerator gen= new FreeTypeFontGenerator(Gdx.files.internal("fonts/enervate.ttf"));
		gameOverFont=gen.generateFont(90);
		gameOverFont.setColor(Color.RED);
		font=gen.generateFont(50);
		font.setColor(Color.RED);
	}
	
	public void update(float dt){
		fondo.begin();
		fondo.draw(texturaFondo,0,0,800,600);
		fondo.end();
		handleInput();
		
	}
	
	public void draw(){
		sb.setProjectionMatrix(Juego.cam.combined);
		sb.begin();
		String s;
		float w;
		s="GAME OVER";
		w=gameOverFont.getBounds(s).width;
		gameOverFont.draw(sb, s, (Juego.WIDTH-w)/2, 400);
		if(!newHighScore){ //si no hay una nueva puntuacion terminar
			sb.end();
			return;
		}
		s="Nueva Puntuacion Alta: "+ Save.gd.getTentativeScore();
		w=font.getBounds(s).width;
		font.draw(sb, s, ((Juego.WIDTH-w)/2)+70, 300);
		for(int i=0; i<newName.length;i++){
			font.draw(
					sb, 
					Character.toString(newName[i]), 390+14*i, 250);
		}
		
		
		sb.end();
		
		sr.begin(ShapeType.Line);
		sr.line(390+14*currentChar, 215, 404+14*currentChar, 215);
	
		sr.end();
	}
	
	public void handleInput(){
		if(GameKeys.isPressed(GameKeys.ENTER)){
			if(newHighScore){
				Save.gd.addHighScore(Save.gd.getTentativeScore(), new String(newName));
				Save.save();
			}
			gsm.setState(GameStateManager.MENU);
		}
		
		if(GameKeys.isPressed(GameKeys.UP)){
			if(newName[currentChar]==' '){
				newName[currentChar]='Z';
			}else{
				newName[currentChar]--;
				if(newName[currentChar]<'A'){
					newName[currentChar]=' ';
				}
			}
			
		}
		
		
		if(GameKeys.isPressed(GameKeys.DOWN)){
			if(newName[currentChar]==' '){
				newName[currentChar]='A';
				
			}else{
				newName[currentChar]++;
				if(newName[currentChar]>'Z'){
					newName[currentChar]=' ';
				}
			}
		}
		
		if(GameKeys.isPressed(GameKeys.RIGHT)){
			if(currentChar<newName.length-1){
				currentChar++;
			}
		}
		if(GameKeys.isPressed(GameKeys.LEFT)){
			if(currentChar>0){
				currentChar--;
			}
		}
		
	}
	
	public void dispose(){
		sb.dispose();
		sr.dispose();
		gameOverFont.dispose();
		font.dispose();
		SonidosManager.stop("muerte");
	}

}
