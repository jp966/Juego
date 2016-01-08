package com.neet.entities;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.MathUtils;
import com.neet.main.Juego;
import com.neet.management.SonidosManager;

public class Enemigo extends SpaceObject {
	
	private ArrayList<Bala> balas;
	private int tipo;
	public static final int LARGE=0;

	public static final int SMALL=1;
	private int score;
	private float fireTimer; 					//cuanto se demora en disparar
	
	private float fireTime; 					//cuanto tiempo ha pasado desde el ultimo disparo
	private Player player;						 //se necesita saber cual es la ubicacion del jugador
	private float pathTimer;
	private float pathTime1; 					//tiempo que demora en realizar el primer trayecto del patron de movimiento
	
	private float pathTime2;					 //tiempo que demora en realizar un segundo trayecto del patron de movimiento
	private int Direccion;
	public static final int LEFT=0; 			 //solo de mueve en dos direcciones
	public static final int RIGHT=1;
	
	private boolean remover; 					//para que desaparezca cuando muera
	
	public Enemigo(int tipo, int Direccion,Player player, ArrayList<Bala> balas){
		this.tipo=tipo;
		this.Direccion=Direccion;
		this.player=player;
		this.balas=balas;
		speed=70;
		if(Direccion==LEFT){
			dx=-speed;
			x=Juego.WIDTH; 						//se moveran desde la orilla de la pantalla
		}else if(Direccion==RIGHT){
			dx=speed;
			x=0;
		}
		
		y=MathUtils.random(Juego.HEIGHT); // de 0 a Juego.Height
		shapex=new float[6];
		shapey=new float[6];
		setShape();
		
		if(tipo==LARGE){
			score=200;
			SonidosManager.play("aparicion");
		}else if(tipo==SMALL){
			score=1000;
			SonidosManager.play("aparicion");
		
		}
		
		fireTimer=0;
		fireTime=1;
		pathTimer=0;
		pathTime1=1;
		pathTime2=pathTime1+2;
		
	
		
		
	}
	
	
	private void setShape(){
		if(tipo==LARGE){
			shapex[0]=x-10;
			shapey[0]=y;
			
			shapex[1]=x-3;
			shapey[1]=y-5;
			
			shapex[2]=x+3;
			shapey[2]=y-5;
			
			shapex[3]=x+10;
			shapey[3]=y;
			
			shapex[4]=x+3;
			shapey[4]=y+5;
			
			shapex[5]=x-3;
			shapey[5]=y+5;
			
		}else if(tipo==SMALL){
			shapex[0]=x-6;
			shapey[0]=y;
			
			shapex[1]=x-2;
			shapey[1]=y-3;
			
			shapex[2]=x+2;
			shapey[2]=y-3;
			
			shapex[3]=x+6;
			shapey[3]=y;
			
			shapex[4]=x+2;
			shapey[4]=y+3;
			
			shapex[5]=x-2;
			shapey[5]=y+3;
		}
		
	}
	
	public int getScore(){
		return score;
	}
	
	public boolean shouldRemove(){
		return remover;
	}
	
	public void update(float dt){
		//disparar
		if(!player.isHit()){
			fireTimer+=dt;
			if(fireTimer>fireTime){
				fireTimer=0;
				if(tipo==LARGE){
					//radians=MathUtils.random(2*3.1415f);
					radians=MathUtils.atan2(
							player.gety()-y,
							player.getx()-x
							);
				}else if(tipo==SMALL){
					radians=MathUtils.atan2(
							player.gety()-y,
							player.getx()-x
							);
				}
				balas.add(new Bala(x,y,radians));
				SonidosManager.play("laser");
				
			}
		}
		
		//moverse a traves del patron
		pathTimer+=dt;
		//avanzar
		if(pathTimer<pathTime1){
			dy=0;
		}
		//moverse hacia abajo
		if(pathTimer>pathTime1 && pathTimer>pathTime2){
			dy=-speed;
			
			
		}
		
		//moverse hasta salir de la pantalla
		if(pathTimer>pathTime1+pathTime2){
			dy=0;
		}
		
		x+=dx*dt;
		y+=dy*dt;
		
		//salir de la pantalla
		if(y<0){
			y=Juego.HEIGHT;		//solo es en la direccion del eje y ya que el enemigo va bajando
		}
		
		//set shae
		setShape();
		
		//verificar que desaparece
		if((Direccion==RIGHT && x>Juego.WIDTH) || (Direccion==LEFT && x<0)){
			remover=true;
			
		}
		
		
		
		
	}
	
	public void draw(ShapeRenderer sr){
		sr.setColor(0,0,1,0);
		sr.begin(ShapeType.Line);
		for(int i=0,j=shapex.length-1;i<shapex.length;j=i++){
			sr.line(shapex[i], shapey[i], shapex[j], shapey[j]);
			
			
		}
		sr.line(shapex[0], shapey[0], shapex[3], shapey[3]);
		sr.end();
		
	}
	
	
	
	

}
