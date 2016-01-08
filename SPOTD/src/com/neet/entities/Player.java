package com.neet.entities;


import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.neet.main.Juego;
import com.neet.management.SonidosManager;

public class Player extends SpaceObject{
	
	
	private float[] flamex;
	private float[] flamey;
	
	private final int MAX_BULLETS=10;
	
	private ArrayList<Bala> bullets;
	
	private boolean left;
	private boolean right;
	private boolean up;
	private boolean down;
	
	private float maxSpeed;
	private boolean hit;
	private boolean muerto;
	
	private float acceleration;
	private float deceleration;
	private float acceleratingTimer;
	
	private Line2D.Float[] hitLines;
	private Point2D.Float[] hitLinesVector; //direccion de las lineas al explotar
	private float hitTimer;
	private float hitTime;
	
	private long score;                     //puntuacion, vidad y puntos para conseguir vidas
	private int vidasExtra;
	private long requiredScore;
	public Player(ArrayList<Bala> bullets){  //para que player pueda utilizar bullets dependencia
	
	
    
		
		this.bullets=bullets;
		
		x=Juego.WIDTH/2;	//se centra la nave en el origen
		y=Juego.HEIGHT/2-200;
	
		maxSpeed =200;
		acceleration=200;
		deceleration=10;
		
		
		
		
		
		shapex=new float[4];               //se crea la figura de la nave con 4 puntos
		shapey=new float[4];
		flamex=new float[3];               //3 vertices
		flamey=new float[3];
		
		radians=3.1415f/2;					//mirada hacia rriba
		rotationSpeed=3;
		
		
		hit=false;
		hitTimer=0;
		hitTime =2; //dos segundos despues de ser golpeado, el juegador muere
		
		score=0;			//puntos iniciales, vida iniciales y puntuacion parada vidas
		vidasExtra=3;
		requiredScore=10000;
		
	}
	
	private void setShape(){
		shapex[0]=x+MathUtils.cos(radians)*8;  //8 pixeles en la direccion del cos 
	    shapey[0]=y+MathUtils.sin(radians)*8;
	    
	    shapex[1]=x+MathUtils.cos(radians - 4*3.1415f/5)*8;  //8 pixeles en la direccion del cos 
	    shapey[1]=y+MathUtils.sin(radians - 4*3.1415f/5)*8;
	    
	    shapex[2]=x+MathUtils.cos(radians + 3.1415f)*5;
	    shapey[2]=y+MathUtils.sin(radians + 3.1415f)*5;
	    
	   shapex[3]=x+MathUtils.cos(radians + 4*3.1415f/5)*8;
	   shapey[3]=y+MathUtils.sin(radians + 4*3.1415f/5)*8;
	    
	    
		
	}
	
	
	private void setFlame(){
		
		flamex[0]=x+MathUtils.cos(radians-5*3.1415f /6)*5;
		flamey[0]=y+MathUtils.sin(radians-5*3.1415f /6)*5;
		
		flamex[1]=x+MathUtils.cos(radians-3.1415f)*(6+acceleratingTimer*50);
		flamey[1]=y+MathUtils.sin(radians-3.1415f)*(6+acceleratingTimer*50);
		
		flamex[2]=x+MathUtils.cos(radians+5*3.1415f/6)*5;
		flamey[2]=y+MathUtils.sin(radians+5*3.1415f/6)*5;
	}
	
	
	
	
	
	
	
	public void setLeft(boolean b){
		left=b;
	}
	public void setRight(boolean b){
		right=b;
	}
	public void setUp(boolean b){
		
		up=b;
	}
	public void setDown(boolean b){
		down=b;
	}
	
	public void setPosition(float x, float y){
		super.setPosition(x, y);  //setear y recalcular posicion
		setShape();
		
	}
	
	public void shoot(){
		
		if(bullets.size()==MAX_BULLETS){
			return;
		}
		
		bullets.add(new Bala(x,y,radians)); //se setea en la posicion del jugador para diaprar nuevas balas
		
		SonidosManager.play("shoot");
	}
	
	
	public boolean isHit(){
		return hit;
	}
	
	public boolean isDead(){
		return muerto;
	}
	
	
	public void reset(){		//respawn de la nave al morir
		x=Juego.WIDTH/2;
		y=Juego.HEIGHT/2-200;
		setShape();
		hit=muerto=false;
		
	}
	
	
	public long getScore(){
		return score;
	}
	public int getLives(){
		return vidasExtra;
	}
	public void loseLife(){
		vidasExtra--;
	}
	
	public void incrementScore(long l){
		score+=l;
	}
	
	
	public void hit(){
		if(hit){
			return;
		}
		hit=true;
		dx=dy=0;
		left=right=up=down=false;
		
		hitLines=new Line2D.Float[4];
		for(int i=0, j=hitLines.length-1;i<hitLines.length;j=i++){
			hitLines[i]=new Line2D.Float(
					shapex[i],shapey[i],shapex[j],shapey[j]
					);
			
		}
		
		hitLinesVector=new Point2D.Float[4];
		hitLinesVector[0]=new Point2D.Float(
				MathUtils.cos(radians+1.5f),
				MathUtils.sin(radians+1.5f)
				
				);
		hitLinesVector[1]=new Point2D.Float(
				MathUtils.cos(radians-1.5f),			//descomposicion
				MathUtils.sin(radians-1.5f)
				);
		
	
		hitLinesVector[2]=new Point2D.Float(
				MathUtils.cos(radians-2.8f),
				MathUtils.sin(radians-2.8f)
				);
		
		hitLinesVector[3]=new Point2D.Float(
				MathUtils.cos(radians+2.8f),
				MathUtils.sin(radians+2.8f)
				);
		
		
	}
	
	
	public void update(float dt){
		//chequear colision
		if(hit){
			hitTimer+=dt;
			if(hitTimer>hitTime){
				muerto=true;
				hitTimer=0;
				
			}
			
			for(int i=0;i<hitLines.length;i++){
				hitLines[i].setLine(
					hitLines[i].x1 + hitLinesVector[i].x*10*dt,
					hitLines[i].y1 + hitLinesVector[i].y*10*dt,
					hitLines[i].x2 + hitLinesVector[i].x*10*dt,
					hitLines[i].y2 + hitLinesVector[i].y*10*dt
						);
			}
			
			return;
			
		}
		
		//chequear puntuacion
		if(score>=requiredScore){
			vidasExtra++;
			requiredScore+=1000;
			SonidosManager.play("extralife");
		}
		
		
		
		
		
		//direccionamiento
		 
        if(left){
        	dx +=-6*acceleration*dt;
        	
        }else if(right){
        	dx +=6*acceleration*dt;
        }
		//aceleracion
		
		if(up){
			dy +=6*acceleration*dt;
			acceleratingTimer +=dt;
			if(acceleratingTimer>0.1f){
				acceleratingTimer=0;
			}
				
			
			
		}else if(down){
			dy +=-6*acceleration*dt;
			acceleratingTimer=0;
		}
		
		
		//deceleration
		
		float vec = (float)Math.sqrt(dx * dx + dy *dy);
                if(vec > 0){
                dx -= (dx / vec)  * 16*deceleration * dt;
                dy -= (dy / vec) *16* deceleration * dt;
		}
		
		if(vec>maxSpeed){
			dx=(dx/vec)*maxSpeed;
			dy=(dy/vec)*maxSpeed;
			
		}
		
		
		//setear posicion

	   
		x+=dx*dt;
		y+=dy*dt;
		
		//set shape
		
		setShape();
		 wrap();
		 
		 //setFlame
		 if(up){
			 setFlame();
		 }
		 
     
		//screen wrap (si el juegador quiuere ir a la izquierda o derecha del mapa)	
		
		 wrap();
		
   
    
        }
	
       
		
			

		
	
public void draw(ShapeRenderer sr){
		
		sr.setColor(0, 1, 0, 0);
        sr.begin(ShapeType.Line);
        
        
        //al chequear si choca
        if(hit){
        	for(int i=0;i<hitLines.length;i++){
        		sr.line(
        			hitLines[i].x1,
        			hitLines[i].y1,
        			hitLines[i].x2,
        			hitLines[i].y2
        			
        				);
        	}
        	
        	
        	sr.end();
        	return;
        }
        
        
        

     //se dibuja la nave
        for(int i = 0, j = shapex.length-1; i < shapex.length; j = i++){
                sr.line(shapex[i], shapey[i], shapex[j], shapey[j]);
                
                
        }
        
       
      //dibujar flamas(propulsion)
        
        if(up){
        	 for(int i = 0, j = flamex.length-1; i < flamex.length; j = i++){
                 sr.line(flamex[i], flamey[i], flamex[j], flamey[j]);
         }
         
        }
        
        
        sr.end();
}
	
	

	}	

	
	
	
	
	
	
	