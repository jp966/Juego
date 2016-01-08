package com.neet.gamestates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.neet.main.Juego;
import com.neet.management.GameKeys;
import com.neet.management.GameStateManager;
import com.neet.management.Save;

public class HighScoreState extends GameState {
	
	private SpriteBatch sb;
	private BitmapFont font;
	private long[] highScores;		//en caso extremo de que se haga mas de 2 billones de puntos
	private String [] names;
	
	private SpriteBatch fondo;
	private Texture texturaFondo;
	
	public HighScoreState(GameStateManager gsm){
		super(gsm);
		
	}

	public void init(){
		fondo=new SpriteBatch();
		texturaFondo=new Texture(Gdx.files.internal("imagenes/hangar.jpg"));
		
		sb=new SpriteBatch();
		FreeTypeFontGenerator gen=new FreeTypeFontGenerator(Gdx.files.internal("fonts/DS-DIGI.TTF"));
		font=gen.generateFont(40);
		
		Save.load();
		highScores=Save.gd.getHighScores();
		names=Save.gd.getNames();
		
		
	}
	
	public void update (float dt){
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
		s="Mejores Puntuaciones";
		w=font.getBounds(s).width;
		font.draw(sb, s, (Juego.WIDTH-w)/2, 590);       //posicion titulo (mejores puntuaciones)
		for(int i=0;i<highScores.length;i++){
			s=String.format("%2d. %7s %s",i+1,highScores[i], names[i]);
			w=font.getBounds(s).width;
			font.draw(sb, s, ((Juego.WIDTH)/3), 500-50*i); //posicion puntuaciones
			font.setColor(Color.RED);
			
		}
		sb.end();
		
	}
	
	public void handleInput(){
		if(GameKeys.isPressed(GameKeys.ENTER) || (GameKeys.isPressed(GameKeys.ESCAPE))){
			gsm.setState(GameStateManager.MENU);
		}
		
	}
	
	public void dispose(){
		sb.dispose();
		font.dispose();
		
	}
}


