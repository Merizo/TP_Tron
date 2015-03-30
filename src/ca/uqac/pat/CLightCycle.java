package ca.uqac.pat;

import javax.swing.ImageIcon;
import java.util.ArrayList;
import java.util.Random;

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
        ImageIcon Icon = new ImageIcon("res/"+ Color + ".jpg");
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

    protected Direction getRandomDirection() {
        ArrayList<Direction> correctDirections = new ArrayList<>();
        // Get correct surrounding positions
        if (!isWall(Direction.RIGHT) && PosX < Ecran.getNbrColonnes() - 1)
            correctDirections.add(Direction.RIGHT);

        if (!isWall(Direction.LEFT) && PosX > 0)
            correctDirections.add(Direction.LEFT);

        if (!isWall(Direction.DOWN) && PosY < Ecran.getNbrLignes() - 1)
            correctDirections.add(Direction.DOWN);

        if (!isWall(Direction.UP) && PosY > 0)
            correctDirections.add(Direction.UP);

        if (correctDirections.size() == 0) {
            isRunning = false;
            return Direction.UP;
        } else {
            Random randomDirection = new Random();
            int newDirection = randomDirection.nextInt(correctDirections.size());
            return correctDirections.get(newDirection);
        }
    }

    protected boolean isWall(Direction dir) {
        return !getAdjacentTileName(dir).equals("res/Black.jpg");
    }

    protected String getAdjacentTileName(Direction dir) {
        if (dir == Direction.UP)
            return Ecran.getGrille()[PosY - 1][PosX].getIcon().toString();

        else if (dir == Direction.DOWN)
            return Ecran.getGrille()[PosY + 1][PosX].getIcon().toString();

        else if (dir == Direction.LEFT)
            return Ecran.getGrille()[PosY][PosX - 1].getIcon().toString();

        else // if (dir == Direction.RIGHT)
            return Ecran.getGrille()[PosY][PosX + 1].getIcon().toString();
    }

}
