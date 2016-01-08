package com.neet.management;

import java.io.Serializable;

public class GameData implements Serializable {
	private static final long serialVersionUID=1;
	private static final int MAX_CANTIDAD=10;
	private long[] highScores;
	private String[] names;
	private long tentativeScore;			//puntuacion preliminar
	
	public GameData(){
		highScores=new long[MAX_CANTIDAD];
		names=new String [MAX_CANTIDAD];
		
		
	}
	//tabla de posiciones vacia 
	public void init(){
		for(int i=0; i<MAX_CANTIDAD; i++){
			highScores[i]=0;
			names[i]="------";
		}
	}
	
	public long[] getHighScores(){
		return highScores;
	}

	public String[] getNames(){
		return names;
	}
	public long getTentativeScore(){				
		return tentativeScore;
	}
	public void setTentativeScore(long l){
		tentativeScore=l;
	}
	
	public boolean isHighScore(long score){
		return score>highScores[MAX_CANTIDAD-1];
	}
	
	
	public void addHighScore(long newScore,String nombre){
		
		if(isHighScore(newScore)){
			highScores[MAX_CANTIDAD-1]=newScore;
			names[MAX_CANTIDAD-1]=nombre;
			ordenarHighScores();
		}
	
	}
	
	//ordenar las puntuaciones de mayor a menor
	
	public void ordenarHighScores(){
		for(int i=0; i<MAX_CANTIDAD;i++){
			long score=highScores[i];
			String nombre=names[i];
			int j;
			for(j=i-1;j>=0 && highScores[j]<score;j--){			
				highScores[j+1]=highScores[j];
				names[j+1]=names[j];
			}
			highScores[j+1]=score;
			names[j+1]=nombre;
		}
	}
	
	
	
	
	
}
