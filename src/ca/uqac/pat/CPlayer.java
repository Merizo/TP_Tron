package ca.uqac.pat;

public class CPlayer extends CLightCycle{

	public CPlayer(	IEcranGUI ecran, int posX, int posY, Direction direction, int vitesse, String color) {
		super(ecran, posX, posY, direction, vitesse, color);
	}
	
	@Override
	public void run() {
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
}
