package ca.uqac.pat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.ImageIcon;

public class CGame {

    private final boolean     isRunning;
    public static List<Point> usedCoord;

    public CGame() {
        this.isRunning = true;

        //Initialisation de la liste synchronized pour gérer les accès multiples
        usedCoord = Collections.synchronizedList(new ArrayList<>());
    }

    public void play() {
        int       Largeur = 80;
        int       Hauteur = 80;
        ImageIcon Im      = new ImageIcon("Black.jpg");
        CEcranGUI ecran   = new CEcranGUI(Hauteur, Largeur, Im);
        while (isRunning) {

            //SI l'utilisateur veut rejouer
            if (ecran.getGameOver()) {
                ecran.Fen.setVisible(false);
                ecran = new CEcranGUI(Hauteur, Largeur, Im);
            }
        }
    }
}
