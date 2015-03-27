package ca.uqac.pat;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.ImageIcon;

public class CPlayer extends CLightCycle{

	public CPlayer(	IEcranGUI ecran, int posX, int posY, CLightCycle.Direction direction, int vitesse, String color) {
		super(ecran, posX, posY, direction, vitesse, color);
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		super.run();

		super.display();
		
		while (alive){
			long startTime = System.currentTimeMillis();
			long fps = 1000/10;
					
			super.move();
			super.display();
	
			long endTime = System.currentTimeMillis();
			
			if (endTime < startTime + fps)
				try{
					sleep ((fps - (endTime - startTime)) / vitesse);
				} catch (InterruptedException e){
					e.printStackTrace();
				}
		}	
	}
	
	@Override
	protected boolean collided() {
		// TODO Auto-generated method stub
		if(super.collided())
			this.Ecran.gameOver();
		
		
		
		return false;
	}
}
