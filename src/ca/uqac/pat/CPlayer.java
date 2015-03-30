package ca.uqac.pat;

public class CPlayer extends CLightCycle{

	public CPlayer(	CEcranGUI ecran, int posX, int posY, Direction direction, int vitesse, String color) {
		super(ecran, posX, posY, direction, vitesse, color);
	}
	
	@Override
	public void run() {
		super.run();

		display();
		
		while (isRunning){
			long startTime = System.currentTimeMillis();
			long fps = 1000/10;
					
			move();
			collided();
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

	public double getPosX() { return PosX; }
	public double getPosY() { return PosY; }
}
