package ca.uqac.pat;

import javax.swing.ImageIcon;

import java.util.ArrayList;
import java.util.List;

public class CLightCycle extends Thread{

	protected IEcranGUI 	Ecran;
	protected double		PosX;
	protected double		PosY;
	protected int 		Direction;
	protected int		Vitesse;
	protected String Color;
	protected List<Point> lastPos;
	protected boolean alive;
	
	public CLightCycle(	IEcranGUI ecran, int posX, int posY, 
			int direction, 	int vitesse, String color) {
		this.Ecran 	= ecran;	
		this.Direction = direction;
		this.PosX 	= posX;
		this.PosY 	= posY;		
		this.Vitesse = vitesse;
		this.Color = color;
		this.lastPos = new ArrayList<Point>();
		alive= true;
		start();
	}
	
	protected void display(){
		ImageIcon Icon = new ImageIcon (Color + ".gif");
		Ecran.setIcon ((int)PosY, (int)PosX, Icon);
		Point p = new Point(this.PosX, this.PosY);
		lastPos.add(p);
	}

	protected void move() {
		switch (this.direction) {
			case UP: //HAUT
				this.posY = posY - 1;
				break;
			case DOWN: //BAS
				this.posY = posY + 1;
				break;
			case LEFT: //GAUCHE
				this.posX = posX - 1;
				break;
			case RIGHT: //DROITE
				this.posX = posX + 1;
				break;
		}
		
		collided();
	}
	
	protected void collided(){
		//Collision avec les bords
		//Bord gauche
		if(this.PosX == 0)
			this.Ecran.gameOver();
		
		//Bord droit
		if(this.PosX == this.Ecran.getNbrColonnes())
			System.out.println("Collision bord droit");
			
		//Bord haut
		if(this.PosY == 0)
			System.out.println("Collision bord haut");
		
		//Bord bas
		if(this.PosY == this.Ecran.getNbrLignes())
			System.out.println("Collision bord bas");
		
		//Collision avec le player et l'IA
		for(Point p : lastPos){
			//System.out.println("x :"+p.x+", y:"+p.y);
			if(this.PosX == p.x && this.PosY == p.y){
				System.out.println("Collision");
				//Stopper thread, le tuer, et recr�er l'�cran de jeu
				try {
					sleep(2000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
				
		}
	}
	
}
