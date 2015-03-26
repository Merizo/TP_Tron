package ca.uqac.pat;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.ImageIcon;

public class CPlayer extends CLightCycle{

	public CPlayer(	IEcranGUI ecran, int posX, int posY, int direction, int vitesse, String color) {
		super(ecran, posX, posY, direction, vitesse, color);
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		super.run();

		super.display();
		
		while (true){
			long startTime = System.currentTimeMillis();
			long fps = 60;
					
			super.move();
			super.display();
	
			long endTime = System.currentTimeMillis();
			
			if (endTime < startTime + fps)
				try{
					sleep ((fps - (endTime - startTime)) / Vitesse);
				} catch (InterruptedException e){
					e.printStackTrace();
				}
		}
		
	}
}
