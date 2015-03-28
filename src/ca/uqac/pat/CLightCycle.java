package ca.uqac.pat;

import javax.swing.ImageIcon;


import java.util.Iterator;
import java.util.ArrayList;
import java.util.List;

public class CLightCycle extends Thread{

	protected IEcranGUI 	Ecran;
	protected double		PosX;
	protected double		PosY;
	protected int 		Direction;
	protected int		Vitesse;
	protected String Color;
	protected boolean isRunning;
	
	public CLightCycle(	IEcranGUI ecran, int posX, int posY, 
			int direction, 	int vitesse, String color) {
		this.Ecran 	= ecran;	
		this.Direction = direction;
		this.PosX 	= posX;
		this.PosY 	= posY;		
		this.Vitesse = vitesse;
		this.Color = color;
		this.isRunning = true;
		start();
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		super.run();

		display();
		
		while (isRunning){
			long startTime = System.currentTimeMillis();
			long fps = 60;
					
			move();
			display();
	
			long endTime = System.currentTimeMillis();
			
			if (endTime < startTime + fps)
				try{
					sleep ((fps - (endTime - startTime)) / Vitesse);
				} catch (InterruptedException e){
					e.printStackTrace();
				}
		}	
	}
	
	protected void display(){
		ImageIcon Icon = new ImageIcon (Color + ".gif");
		Ecran.setIcon ((int)PosY, (int)PosX, Icon);
		Point p = new Point(this.PosX, this.PosY);
		synchronized(CGame.usedCoord) {
			CGame.usedCoord.add(p);
		}
	}
	
	
	protected void move(){
		switch(this.Direction){
			case 1 : //HAUT
				this.PosY = PosY + 1;
				break;
			case 2 : //BAS
				this.PosY = PosY - 1;
				break;
			case 3 : //GAUCHE
				this.PosX = PosX - 1;
				break;
			case 4 : //DROITE
				this.PosX = PosX + 1;
				break;
		}
		
		collided();
	}
	
	protected void kill(){
		this.isRunning = false;
	}
	
	protected boolean collided(){
		//Collision avec les bords
		//Bord gauche
		if(this.PosX == 0){
			this.kill();
			return true;
		}
			
		
		//Bord droit
		if(this.PosX == this.Ecran.getNbrColonnes()){
			this.kill();
			return true;
		}
			
		//Bord haut
		if(this.PosY == 0){
			this.kill();
			return true;
		}
		
		//Bord bas
		if(this.PosY == this.Ecran.getNbrLignes()){
			this.kill();
			return true;
		}
		
		/*//Collision avec le player et l'IA
		for(Point p : CGame.usedCoord){
			//System.out.println("x :"+p.x+", y:"+p.y);
			if(this.PosX == p.x && this.PosY == p.y){
					this.kill();
					return true;
			}		
		}*/
		
	synchronized(CGame.usedCoord) {
		for(Point p : CGame.usedCoord){
			//System.out.println("x :"+p.x+", y:"+p.y);
			if(this.PosX == p.x && this.PosY == p.y){
					this.kill();
					return true;
			}		
		}
	}
		
		return false;
	}
	
}
