package com.neet.main;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
public class Main {
	public static void main(String[] args){ //ejecutar juego en main
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration(); 
		cfg.title="SPOTD";                          //titulo ventana
		cfg.width=800;                              //ancho
		cfg.height=600;                             //alto
		cfg.useGL20=false;                          //no se usara
		cfg.resizable=false;                        //no se usara
		new LwjglApplication(new Juego(), cfg);     //se crea la aplicacion de escritorio con la configuracion
		
	}

}
