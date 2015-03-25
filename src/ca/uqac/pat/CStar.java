package ca.uqac.pat;

import javax.swing.ImageIcon;


public class CStar extends Thread{
	private IEcranGUI 	Ecran;
	private double		PosX;
	private double		PosY;
	private double 		Direction;
	private int			Vitesse;
	

	public CStar(	IEcranGUI ecran, int posX, int posY, 
					int direction, 	int vitesse) {
		Ecran 	= ecran;	
		Direction = direction*Math.PI/180;
		PosX 	= posX + 2 * Math.cos(Direction);
		PosY 	= posY + 2 * Math.sin(Direction);		
		Vitesse = vitesse;
		start();
	}	
	
	public void run(){
		ImageIcon Bleu = new ImageIcon ("Black.gif");
		ImageIcon Floc = new ImageIcon ("Star.gif");
		
		boolean OK = false;
		while (OK = Ecran.setIcon ((int)PosY, (int)PosX, Bleu)){
			PosX = (PosX + Math.cos(Direction));
			PosY = (PosY + Math.sin(Direction));
			
			Ecran.setIcon ((int)PosY, (int)PosX, Floc);
			try{
				sleep(Vitesse);
				Vitesse = (int)(Vitesse *0.95);
			}
			catch (InterruptedException e){
				// rien
			}
		}
		System.out.println(".");
	}
}
