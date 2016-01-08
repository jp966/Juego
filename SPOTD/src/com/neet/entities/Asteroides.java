package com.neet.entities;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.MathUtils;

public class Asteroides extends SpaceObject {

		private int tipo; //tipo de asteroide
		public static int SMALL=0;
		public static int MEDIUM=1;
		public static int LARGE=2;
		public boolean remover;
		private int score;
		private boolean hit;
		
		private int numPoints; //puntos al azar del numero de radios establecidos en la figura del asteroide
		private float[] dists;
		
		public Asteroides(float x, float y, int tipo){
			this.x=x;
			this.y=y;
			this.tipo=tipo;
			hit=false;
		
		
		if(tipo==SMALL){
			numPoints=8;
			width=height=12;
			speed=MathUtils.random(70,100);
			score=100;
		}else if(tipo==MEDIUM){
			numPoints=10;
			width=height=20;
			speed=MathUtils.random(50,60);
			score=50;
			}else if(tipo==LARGE){
				numPoints=12;
				width=height=40;
				speed=MathUtils.random(20,30);
				score=20;
			}
		
		rotationSpeed=MathUtils.random(-1,1);
		radians=MathUtils.random(2*3.1415f);
		dx=MathUtils.cos(radians)*speed;
		dy=MathUtils.sin(radians)*speed;
		
		shapex=new float[numPoints];
		shapey=new float[numPoints];
		dists=new float[numPoints];
		
		int radius=width/2;
		for(int i=0;i<numPoints;i++){
			dists[i]=MathUtils.random(radius/2,radius);
			
		}
		
		setShape();
		
		
		}
		
		private void setShape(){
			float angle =0;
			
			
			for(int i=0;i<numPoints;i++){
				shapex[i]=x+MathUtils.cos(angle +radians)*dists[i];
				shapey[i]=y+MathUtils.sin(angle +radians)*dists[i];
				angle+=2*3.1415f/ numPoints;
			}
			
			
		}
		
		
		public int getTipo(){
			return tipo;
		}
		
		public boolean shouldRemove(){
			return remover;
			
		}
		
		public int getScore(){
			return score;
		}
		
		public void update(float dt){
			x+=dx*dt;
			y+=dy*dt;
			radians+=rotationSpeed*dt;
			setShape();
			wrap();
			
		}
		
		public void draw(ShapeRenderer sr){
			sr.setColor(1, 0, 0, 0);
			sr.begin(ShapeType.Line);
			for(int i = 0, j = shapex.length-1; i < shapex.length; j = i++){
                sr.line(shapex[i], shapey[i], shapex[j], shapey[j]);
        }
        
			sr.end();
			
			
			
		}
		
		
		
}
