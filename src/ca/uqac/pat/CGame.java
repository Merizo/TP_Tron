package ca.uqac.pat;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javafx.embed.swing.JFXPanel;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javax.swing.ImageIcon;

public class CGame {

    private final boolean     isRunning;
    public static List<Point> usedCoord;
    Media bgmusic;
    MediaPlayer mediaPlayer;
    public CGame() {
        this.isRunning = true;

        //Initialisation de la liste synchronized pour gérer les accès multiples
        usedCoord = Collections.synchronizedList(new ArrayList<>());
    }

    public void play() {
        new JFXPanel();

        bgmusic = new Media(Paths.get("bgmusic.mp3").toUri().toString());
        mediaPlayer = new MediaPlayer(bgmusic);

        int       Largeur = 100;
        int       Hauteur = 80;
        ImageIcon Im      = new ImageIcon("Black.jpg");
        CEcranGUI ecran   = new CEcranGUI(Hauteur, Largeur, Im);

        mediaPlayer.play();


        while (isRunning) {
            //SI l'utilisateur veut rejouer
            if (ecran.getGameOver()) {
                mediaPlayer.stop();
                ecran.Fen.setVisible(false);
                ecran = new CEcranGUI(Hauteur, Largeur, Im);
                mediaPlayer.play();
            }
        }
    }
}
