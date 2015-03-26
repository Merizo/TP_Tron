package ca.uqac.pat;

import javax.swing.ImageIcon;

import java.util.ArrayList;
import java.util.List;

import static ca.uqac.pat.CLightCycle.Direction.*;

public class CLightCycle extends Thread {
    protected static enum Direction {UP, DOWN, LEFT, RIGHT}

    protected IEcranGUI   ecran;
    protected double      posX;
    protected double      posY;
    protected Direction   direction;
    protected int         vitesse;
    protected String      color;
    protected List<Point> previousPositions;

    public CLightCycle(IEcranGUI ecran, int posX, int posY,
                       Direction direction, int vitesse, String color) {
        this.ecran = ecran;
        this.direction = direction;
        this.posX = posX;
        this.posY = posY;
        this.vitesse = vitesse;
        this.color = color;
        this.previousPositions = new ArrayList<>();
        start();
    }

    protected void display() {
        ImageIcon Icon = new ImageIcon(color + ".jpg");
        ecran.setIcon((int) posY, (int) posX, Icon);
        Point p = new Point(this.posX, this.posY);
        previousPositions.add(p);
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

    protected void collided() {
        //Collision avec les bords
        //Bord gauche
        if (this.posX == 0)
            this.ecran.gameOver();

        //Bord droit
        if (this.posX == this.ecran.getNbrColonnes())
            System.out.println("Collision bord droit");

        //Bord haut
        if (this.posY == 0)
            System.out.println("Collision bord haut");

        //Bord bas
        if (this.posY == this.ecran.getNbrLignes())
            System.out.println("Collision bord bas");

        //Collision avec le player et l'IA
        for (Point p : previousPositions) {
            //System.out.println("x :"+p.x+", y:"+p.y);
            if (this.posX == p.x && this.posY == p.y) {
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
