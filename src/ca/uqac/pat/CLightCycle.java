package ca.uqac.pat;

import javax.swing.ImageIcon;

public class CLightCycle extends Thread {
    public enum Direction {UP, DOWN, LEFT, RIGHT}

    protected final IEcranGUI Ecran;
    protected       int       PosX;
    protected       int       PosY;
    protected       Direction direction;
    protected final int       Vitesse;
    private final   String    Color;
    protected       boolean   isRunning;

    public CLightCycle(IEcranGUI ecran, int posX, int posY,
                       Direction direction, int vitesse, String color) {
        this.Ecran = ecran;
        this.direction = direction;
        this.PosX = posX;
        this.PosY = posY;
        this.Vitesse = vitesse;
        this.Color = color;
        this.isRunning = true;
        start();
    }

    @Override
    public void run() {
        super.run();

        display();

        while (isRunning) {
            long startTime = System.currentTimeMillis();
            long fps = 60;

            move();
            display();

            long endTime = System.currentTimeMillis();

            if (endTime < startTime + fps)
                try {
                    sleep((fps - (endTime - startTime)) / Vitesse);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
        }
    }

    protected void display() {
        ImageIcon Icon = new ImageIcon(Color + ".jpg");
        Ecran.setIcon(PosY, PosX, Icon);
        Point p = new Point(this.PosX, this.PosY);
        synchronized (CGame.usedCoord) {
            CGame.usedCoord.add(p);
        }
    }


    protected void move() {
        switch (this.direction) {
            case UP: //HAUT
                this.PosY = PosY - 1;
                break;
            case DOWN: //BAS
                this.PosY = PosY + 1;
                break;
            case LEFT: //GAUCHE
                this.PosX = PosX - 1;
                break;
            case RIGHT: //DROITE
                this.PosX = PosX + 1;
                break;
        }

        collided();
    }

    private void kill() {
        this.isRunning = false;
    }

    protected boolean collided() {
        //Collision avec les bords
        //Bord gauche
        if (this.PosX < 1) {
            this.kill();
            return true;
        }

        //Bord droit
        if (this.PosX == this.Ecran.getNbrColonnes() - 1) {
            this.kill();
            return true;
        }

        //Bord haut
        if (this.PosY < 1) {
            this.kill();
            return true;
        }

        //Bord bas
        if (this.PosY == this.Ecran.getNbrLignes() - 1) {
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

        synchronized (CGame.usedCoord) {
            for (Point p : CGame.usedCoord) {
                //System.out.println("x :"+p.x+", y:"+p.y);
                if (this.PosX == p.getX() && this.PosY == p.getY()) {
                    this.kill();
                    return true;
                }
            }
        }

        return false;
    }

}
