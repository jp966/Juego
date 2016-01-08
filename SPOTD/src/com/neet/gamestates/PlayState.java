package com.neet.gamestates;

import java.util.ArrayList;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.neet.entities.Asteroides;
import com.neet.entities.Bala;
import com.neet.entities.Enemigo;
import com.neet.entities.Particula;
import com.neet.entities.Player;
import com.neet.main.Juego;
import com.neet.management.GameKeys;
import com.neet.management.GameStateManager;
import com.neet.management.SonidosManager;
import com.neet.management.Save;

public class PlayState extends GameState {
	private SpriteBatch sb;
	private ShapeRenderer sr;
	private Player player;
	
	private Player hudPlayer;
	private Music musica;
	
	private boolean pausa=true;
	
	
	private BitmapFont font;
	private ArrayList<Bala> balas;
	private ArrayList<Bala> enemyBalas;
	private ArrayList<Asteroides> asteroides;
	
	private ArrayList<Enemigo> enemigos;
	
	private Enemigo enemigo;
	private float ETime;
	private float ETimer;
	
	private int level;
	private int totalAsteroides;
	private int numAsteroidesLeft;
	
	private SpriteBatch fondo;
	private Texture texturaFondo;
	
	private SpriteBatch sbPausa;
	private Texture tPausa;
	
	private ArrayList<Particula> particles;
	
	
	
	
	public PlayState(GameStateManager gsm){
		super(gsm);
		
		
	}
	
	public void init(){
		sr = new ShapeRenderer();
		sb=new SpriteBatch();
		
		//new SpriteBatch();
		//tPausa=new Texture(Gdx.files.internal(path));
		
		FreeTypeFontGenerator fontPausa=new FreeTypeFontGenerator(Gdx.files.internal("fonts/DS-DIGI.TTF"));
		font=fontPausa.generateFont(40);
		
		
		Juego.musica1.stop();
		 musica = Gdx.audio.newMusic(Gdx.files.internal("musica/Titanium Sky.mp3"));
		 musica.setLooping(true);
		 musica.play();
		 musica.setVolume(0.4f);
		
		//setear font
		FreeTypeFontGenerator gen=new FreeTypeFontGenerator(Gdx.files.internal("fonts/DS-DIGI.TTF"));
		font=gen.generateFont(50);
		
		texturaFondo=new Texture(Gdx.files.internal("imagenes/fondoJuego.jpg"));
		
		fondo=new SpriteBatch();
		
		
		balas=new ArrayList<Bala>();
		
		player = new Player(balas);
		
		asteroides=new ArrayList<Asteroides>();
		
		
		
		particles=new ArrayList<Particula>();
		
		
		level=1;
		spawnAsteroides();
		hudPlayer=new Player(null);
		
		ETimer=0;
		ETime=3; 									//tiempo de reaparicion de un enemigo
		
		enemigos=new ArrayList<Enemigo>();
		enemyBalas= new ArrayList<Bala>();
		
		
		
	}
	
	private void createParticles(float x, float y){
		for(int i =0;i<6;i++){
			particles.add(new Particula(x,y));
		}
		
	}
	
	
	
	private void splitAsteroides(Asteroides a){
		createParticles(a.getx(),a.gety());
		
		
		numAsteroidesLeft--;
		if(a.getTipo()==Asteroides.LARGE){
			asteroides.add(new Asteroides(a.getx(),a.gety(),Asteroides.MEDIUM));//si calzan, el asteroide LARGE pasara a ser 2 Medium
			asteroides.add(new Asteroides(a.getx(),a.gety(),Asteroides.MEDIUM));
		}
		
		if(a.getTipo()==Asteroides.MEDIUM){
			
			asteroides.add(new Asteroides(a.getx(),a.gety(),Asteroides.SMALL)); //si calzan, el asteroide MEDIUM pasara a ser 2 SMALL
			asteroides.add(new Asteroides(a.getx(),a.gety(),Asteroides.SMALL));
			
		}
	}
	
	
	public void spawnAsteroides(){
		asteroides.clear();
		int numToSpawn=20+level-1;                //el primer nivel tendra 20 asteroides, luego 21
		totalAsteroides=numToSpawn;
		numAsteroidesLeft=totalAsteroides;
		
		for(int i=0; i<numToSpawn;i++){
			float x=MathUtils.random(Juego.WIDTH);
			float y=MathUtils.random(Juego.HEIGHT);
		
			float dx=x-player.getx();
			float dy=y-player.gety();
			float dist=(float) Math.sqrt(dx*10+dy*10); //distancia a la que aparecera un asteroide
			
			while(dist<150){ //si es muy cercano al jugadfor
				x=MathUtils.random(Juego.WIDTH);
				y=MathUtils.random(Juego.HEIGHT);
				
				 dx=x-player.getx();
				 dy=y-player.gety();
				 dist=(float) Math.sqrt(dx*dx+dy*dy);
			}
			
			asteroides.add(new Asteroides(x,y,Asteroides.LARGE));
		}
	}
	
	
	
	
	
	
	public void update(float dt){
		if(GameKeys.isDown(GameKeys.Z)){		//si se presiona Z se pausara
			pausa=false;
		}
		if(GameKeys.isDown(GameKeys.X)){
			pausa=true;
			
		}
		
		if(GameKeys.isDown(GameKeys.V)){
			Save.gd.setTentativeScore(player.getScore()); //se guarda el puntaje para analizar 
			gsm.setState(GameStateManager.GAMEOVER);

		}
		
		
		 if(GameKeys.isDown(GameKeys.ESCAPE)){
			 musica.pause();;
		 }
		
		 if(GameKeys.isDown(GameKeys.SHIFT)){
			
		   musica.play();;
		 }
		 
		 fondo.begin();
		 fondo.draw(texturaFondo,0,0,800,600);
		 fondo.end();
		 
		 if(!pausa==false){
		handleInput();
		
		
		//siguiente nivel
		
		if(asteroides.size()==0){
			level++;
			spawnAsteroides();
			
			
		}
		
		//actualizar jugador

		
		player.update(dt);
		
		
		if(player.isDead()){
			if(player.getLives()==0){		
				SonidosManager.stopAll();
				Save.gd.setTentativeScore(player.getScore());
				gsm.setState(GameStateManager.GAMEOVER);					//se acaban las vidasd= game over
				
				
			}
			player.reset();
			player.loseLife();
			enemigo=null;
			SonidosManager.stop("aparicion");
			
		}
		
		
		//actualizar balas del jugador
		
		for(int i=0;i<balas.size();i++){
			balas.get(i).update(dt);
			if(balas.get(i).shouldRemove()){
		    balas.remove(i);
			i--;
			}
		}
		
		//actualizar enemigos
		if(enemigo==null){
			ETimer+=dt;
			if(ETimer>=ETime){
				ETimer=0;
				int tipo=MathUtils.random()<0.5 ?
						Enemigo.SMALL:Enemigo.LARGE;
				int direction=MathUtils.random()<0.5 ?
						Enemigo.RIGHT: Enemigo.LEFT;
				enemigo=new Enemigo(tipo, direction,player, enemyBalas);
						
			}
		}
		
		
		//si ya hay enemigo
		else{
			enemigo.update(dt);
			if(enemigo.shouldRemove()){
				enemigo=null;
				SonidosManager.stop("aparicion");
				//JukeBox.stop("largesaucer");
				
			}
		}
		
		//actualizar balas enemigas
		for(int i=0 ;i<enemyBalas.size();i++){
			enemyBalas.get(i).update(dt);
			if(enemyBalas.get(i).shouldRemove()){
		    enemyBalas.remove(i);
			i--;
			}
			
		}
		
		
		//actualizar asteroides
		for(int i=0;i<asteroides.size();i++){
			asteroides.get(i).update(dt);
			if(asteroides.get(i).shouldRemove()){
		    asteroides.remove(i);
			i--;
			}
		}
		
		
		//actualizar particulas
		for(int i=0; i<particles.size();i++){
			particles.get(i).update(dt);;
			if(particles.get(i).shouldRemove()){
				particles.remove(i);
				i--;
			}
			
		}
		
		
		
		
		//colisiones
		checkCollisions();
		
		
		}
		
			
		
		
			
		}
	
		
		private void checkCollisions(){
			if(!player.isHit()){                   //si el juegador esta muerto, los asteroides no seguiran destruyendose
			//colision jugador asteroide
			for(int i=0;i<asteroides.size();i++){
				//si el jugador choca con un asteroide
				Asteroides a= asteroides.get(i);
				if(a.intersecta(player)){
					player.hit();
					asteroides.remove(i);
					i--;
					splitAsteroides(a);
					SonidosManager.play("explode");
					break;
					
				}
				
				
			}
			
		}
			
			
			
			//for(int i=0;i<asteroides.size();i++){
				//	Asteroides a= asteroides.get(i);
					
					//for(int j=i+1;j<asteroides.size();j++){
					//si el jugador choca con un asteroide
						//Asteroides b= asteroides.get(j);
					//if(a.contains(b.getx(),b.gety())){
						//asteroides.remove(j);
						//j--;						
						
						
						//splitAsteroides(a);
						//JukeBox.play("explode");
				//		break;
						
		//}
			//	}
				//}
		
			
			//colision de las balas con el asteroide
			for(int i=0;i<balas.size();i++){
				Bala b=balas.get(i);
				for(int j=0;j<asteroides.size();j++){
					Asteroides a=asteroides.get(j);
					//si a contiene a b
					if(a.contiene(b.getx(), b.gety())){
						balas.remove(i);               //las balas desaparecen al calzar con un asteroide
						i--;
						asteroides.remove(j);             //el asteroide desaparece al calzar con una bala
						j--;
						splitAsteroides(a);
						//aumentar puntos
						player.incrementScore(a.getScore());   //si el jugador destrute un asteroide suman punto
						SonidosManager.play("explode");
						
						
						break;
					}
					
				}
			}
			
			
			//colision jugador y enemigo
			if(enemigo !=null){
				if(player.intersecta(enemigo)){
					player.hit();
					createParticles(player.getx(),player.gety());
					createParticles(enemigo.getx(),enemigo.gety());
					enemigo=null;
					SonidosManager.play("explode");
				}
			}
			
			
			//colision bala y enemigo
			if (enemigo !=null){
				for(int i=0;i<balas.size();i++){
					Bala b=balas.get(i);
					if(enemigo.contiene(b.getx(), b.gety())){
						balas.remove(i);
						i--;
						createParticles(enemigo.getx(),enemigo.gety());
						player.incrementScore(enemigo.getScore());
						enemigo=null;
						SonidosManager.play("explode");
						break;
					}
				}
			}
			
			//colision jugado y bala enemiga
			if(!player.isHit()){
				for(int i=0;i<enemyBalas.size();i++){
					Bala b=enemyBalas.get(i);
					if(player.contiene(b.getx(), b.gety())){
						player.hit();
						enemyBalas.remove(i);
						i--;
						SonidosManager.play("explode");
						break;
					}
				}
			}
			
			//enemigo y asteroide
			if(enemigo != null){
				for(int i=0;i<asteroides.size();i++){
					Asteroides a=asteroides.get(i);
					if(a.intersecta(enemigo)){
						asteroides.remove(i);
						i--;
						splitAsteroides(a);
						createParticles(a.getx(),a.gety());
						createParticles(enemigo.getx(),enemigo.gety());
						enemigo=null;
						SonidosManager.play("explode");
						break;
					}
				}
			}
			
			//asteroide y balas enemigas
			for(int i=0;i<enemyBalas.size();i++){
				Bala b= enemyBalas.get(i);
				for(int j=0;j<asteroides.size();j++){
					Asteroides a=asteroides.get(j);
					if(a.contiene(b.getx(), b.gety())){
						asteroides.remove(j);
						j--;
						splitAsteroides(a);
						enemyBalas.remove(i);
						i--;
						createParticles(a.getx(),a.gety());
						SonidosManager.play("explode");
						break;
						
						
					}
					
				}
				
			}
			
			
			
			
			
			
		}
		
	
	
	public void draw(){
		
		sb.setProjectionMatrix(Juego.cam.combined);
		sr.setProjectionMatrix(Juego.cam.combined);
		
		//dibujar jugador
		player.draw(sr);
		//dibujar balas
		for(int i=0;i<balas.size();i++){
			balas.get(i).draw(sr);
		}
		
		//dibujar enemigos
		if(enemigo !=null){
			enemigo.draw(sr);
		}
		
		//dibujar balas del enemigo
		for(int i=0; i<enemyBalas.size();i++){
			enemyBalas.get(i).draw(sr);
		}
		
		
		//dibujar asteroides
		for(int i=0;i<asteroides.size();i++){
			asteroides.get(i).draw(sr);
		}
		//dibujar particulas
		for(int i=0;i<particles.size();i++){
			particles.get(i).draw(sr);
		}
		
		//dibujar puntuacion
		sb.setColor(1,1,1,1);
		sb.begin();
		font.draw(sb, Long.toString(player.getScore()), 40, 400);
		sb.end();
		//dibujar vidas
		for(int i=0;i<player.getLives();i++){
		hudPlayer.setPosition(40+i*10, 350);
		hudPlayer.draw(sr);
		}
		
	}
	
	public void handleInput(){
		if(!player.isHit()){                //si el jugador muere no se podran realizar lo que viene a continuacion
		player.setLeft(GameKeys.isDown(GameKeys.LEFT));
		player.setRight(GameKeys.isDown(GameKeys.RIGHT));
		player.setUp(GameKeys.isDown(GameKeys.UP));
		player.setDown(GameKeys.isDown(GameKeys.DOWN));
		
		if(GameKeys.isPressed(GameKeys.SPACE)){  //se disparara con espacio
			player.shoot();
		}
		
		}
		
	}
	
	public void dispose(){
		sb.dispose();
		sr.dispose();
		font.dispose();
		musica.dispose();
	
	}
	
	

}
