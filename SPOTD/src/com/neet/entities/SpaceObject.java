package com.neet.entities;

import com.neet.main.Juego;

public class SpaceObject {
	protected float x;					//posicion
	protected float y; 	
	
	
	protected float dx;				    //vector
	protected float dy;
				                            //vector
	
	
	protected float radians;			//dirección
	protected float speed;
	protected float rotationSpeed;
	
	protected int width;
	protected int height;
	
	protected float[] shapex;
	protected float[] shapey;
	
	
	public float getx(){
		return x;
	}
	public float gety(){
		return y;
	}
	
	
	public float[] getShapex(){
		return shapex;								//poligono
	}
	
	public float[] getShapey(){
		return shapey;
	}
	
	public void setPosition(float x, float y){
		this.x=x;
		this.y=y;
	}
	
	public boolean intersecta(SpaceObject otro){
		float sx[]=otro.getShapex();
		float sy[]=otro.getShapey();
		for(int i=0;i<sx.length;i++){
			if(contiene(sx[i],sy[i])){
				return true;
			}
		}
		return false;
	}
	
	public boolean contiene(float x, float y){
		boolean b=false;
		for(int i=0,j=shapex.length-1;i<shapex.length;j=i++){
			if((shapey[i] > y) != (shapey [j] > y ) &&
					(x < (shapex[j] - shapex[i])*
					(y - shapey[i]) / (shapey[j] - shapey[i]) 
					+ shapex[i])){
				
					b= !b;
						
					}
				
		}
		
		return b;
		
	}
	
	protected void wrap(){
		
		
		
		
		
		if(x<0){
			x=Juego.WIDTH;
		}
		if(x>Juego.WIDTH){
			x=0;
		}
		
		if(y<0){
			y=Juego.HEIGHT;
		}
		
		if(y>Juego.HEIGHT){
			y=0;
		}
		
		
	}
}
