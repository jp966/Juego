package com.neet.management;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import com.badlogic.gdx.Gdx;

public class Save {
	public static GameData gd;
	
	public static void save(){					
		
		try{
			ObjectOutputStream out= new ObjectOutputStream(				
					new FileOutputStream("puntuaciones.sav")
					);
			out.writeObject(gd);			//se esta escribiendo un objeto (output  )en un archivo
			out.close();
			
			
		}catch(Exception e){
			e.printStackTrace();				//solo en caso
			Gdx.app.exit();
		}
		
		
		
	}

	public static void load(){
		
		try{
			
			if(!saveFileExists()){			//si no existe se creara una y se guardara
				init();
				return;
			}
			
			ObjectInputStream lArchivo =new ObjectInputStream(
					new FileInputStream("puntuaciones.sav")
					);
			gd=(GameData) lArchivo.readObject();    //se lee el objeto (input)             
			lArchivo.close();
			
		}catch(Exception e){
			e.printStackTrace();
			Gdx.app.exit();
		}
		
		
		
	}
	
	public static boolean saveFileExists(){
		File f= new File("puntuaciones.sav");
		return f.exists();
	}
	
	public static void init(){
		gd=new GameData();
		gd.init();
		save();
	}
}
