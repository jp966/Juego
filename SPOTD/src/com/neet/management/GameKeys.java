package com.neet.management;

public class GameKeys {
	private static boolean[] keys;        //señala el estado de la tecla (presionado true o no presionado false)
	private static boolean[] pkeys;       //estado previo de la tecla

	private static final int NUM_KEYS=12;
	public static final int UP=0;
	public static final int LEFT=1;
	public static final int DOWN=2;
	public static final int RIGHT=3;
	public static final int ENTER=4;
	public static final int ESCAPE=5;
	public static final int SPACE=6;
	public static final int SHIFT=7;
	public static final int ALT=8;
	public static final int Z=9;
	public static final int X=10;
	public static final int V=11;
	
	static{
		keys=new boolean[NUM_KEYS];
		pkeys=new boolean [NUM_KEYS];
	}
	
	
	public static void Update(){       //se actualizan los estados previos
		for(int i=0;i<NUM_KEYS;i++){
			pkeys[i]=keys[i];
			
		}
	}
	
	public static void setKey(int k, boolean b){    //se setean las teclas en el arreglo
		keys[k]=b;
		
	}
	
	public static boolean isDown(int k){                     //tecla presionada
		return keys[k];
	}
	
	public static boolean isPressed(int k){
		return keys[k] && !pkeys[k];                       //la tecla esta presionada y estado previo era up
		
	}	
}
