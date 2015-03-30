package ca.uqac.pat;


import javax.swing.*;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;


public class CEcranGUI extends JComponent implements IEcranGUI, KeyListener {
    public final JFrame Fen;

    private final int                    NbrLignes;
    private final int                    NbrColonnes;
    private final JLabel[][]             Grille;
    private       ArrayList<CLightCycle> LightCycles;
    private boolean gameOver;


    public CEcranGUI(int nbrLignes, int hauteur, ImageIcon fond) {
        this(nbrLignes, 10, hauteur, 10, fond);
        Fen.addKeyListener(this);

    }

    /**
     * Le constructeur complet.
     */
    private CEcranGUI(int nbrLignes, int hauteur,
                      int nbrColonnes, int largeur, ImageIcon fond) {
        NbrLignes = nbrLignes;
        NbrColonnes = nbrColonnes;


        setLayout(new GridLayout(NbrLignes, NbrColonnes, 0, 0));
        Grille = new JLabel[NbrLignes][NbrColonnes];


        JLabel Bareme = new JLabel("Bareme");
        Font petit = new Font(Bareme.getFont().getName(),
                Bareme.getFont().getStyle(), 8);
        for (int y = 0; y < Grille.length; y++)
            for (int x = 0; x < Grille[y].length; x++) {
                Grille[y][x] = new JLabel(fond);
                add(Grille[y][x]);

                Grille[y][x].setFont(petit);
                Grille[y][x].setBorder(BorderFactory.createEmptyBorder());
            }
        Fen = new CFrameEcran(this);


        Fen.setTitle("Lab 3 : Tr0n game");
        CGame.usedCoord.clear();

        drawWalls();

        LightCycles = new ArrayList<>();
        LightCycles.add(new CPlayer(this, 12, 20, CLightCycle.Direction.RIGHT, 1, "White"));
        LightCycles.add(new CBot(this, 50, 50, CLightCycle.Direction.LEFT, 1, "Green", CBot.Difficulty.RANDOM));
        LightCycles.add(new CBot(this, 10, 50, CLightCycle.Direction.UP, 1, "Purple", CBot.Difficulty.COWARD));
        LightCycles.add(new CBot(this, 80, 10, CLightCycle.Direction.DOWN, 1, "Orange", CBot.Difficulty.CHASER));
    }

    public void gameOver() {

        int makeAChoice = JOptionPane.showConfirmDialog(
                this.Fen,
                "T'as tu envie de rejouer?",
                null,
                JOptionPane.YES_NO_OPTION);


        //Si oui, réinitialise le jeu
        if (makeAChoice == 0) {
            this.gameOver = true;
        } else {
            System.exit(0);
        }

    }

    public boolean getGameOver() {
        return this.gameOver;
    }


    public boolean setIcon(int Lig, int Col, Icon Im) {
        if (Lig >= Grille.length || Lig < 0) return false;
        if (Col >= Grille[Lig].length || Col < 0) return false;

        Grille[Lig][Col].setIcon(Im);
        return true;
    }


    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_DOWN:
                //Si la direction n'est pas déjà haut ou bas
                if (LightCycles.get(0).direction != CLightCycle.Direction.UP
                        && LightCycles.get(0).direction != CLightCycle.Direction.DOWN)
                    LightCycles.get(0).direction = CLightCycle.Direction.DOWN;
                break;
            case KeyEvent.VK_LEFT:

                if (LightCycles.get(0).direction != CLightCycle.Direction.RIGHT
                        && LightCycles.get(0).direction != CLightCycle.Direction.LEFT)
                    LightCycles.get(0).direction = CLightCycle.Direction.LEFT;
                break;
            case KeyEvent.VK_RIGHT:

                if (LightCycles.get(0).direction != CLightCycle.Direction.RIGHT
                        && LightCycles.get(0).direction != CLightCycle.Direction.LEFT)
                    LightCycles.get(0).direction = CLightCycle.Direction.RIGHT;
                break;
            case KeyEvent.VK_UP:

                if (LightCycles.get(0).direction != CLightCycle.Direction.UP
                        && LightCycles.get(0).direction != CLightCycle.Direction.DOWN)
                    LightCycles.get(0).direction = CLightCycle.Direction.UP;
                break;
            case KeyEvent.VK_ESCAPE:
                System.exit(0);
                break;
            default:
        }
    }


    @Override
    public void keyReleased(KeyEvent e) {

    }


    @Override
    public void keyTyped(KeyEvent e) {

    }

    public int getNbrLignes() {
        return NbrLignes;
    }

    public int getNbrColonnes() {
        return NbrColonnes;
    }

    public JLabel[][] getGrille() {
        return Grille;
    }

    public CPlayer getPlayer() {
        return (CPlayer)LightCycles.get(0);
    }

    private void drawWalls() {
        for (int i = 0; i < getNbrLignes(); i++) {
            Grille[i][0].setIcon(new ImageIcon("res/Wall.jpg"));
            Grille[i][getNbrColonnes() - 1].setIcon(new ImageIcon("res/Wall.jpg"));

            CGame.usedCoord.add(new Point(0, i));
            CGame.usedCoord.add(new Point(getNbrColonnes() - 1, i));
        }

        for (int j = 0; j < getNbrColonnes(); j++) {
            Grille[0][j].setIcon(new ImageIcon("res/Wall.jpg"));
            Grille[getNbrLignes() - 1][j].setIcon(new ImageIcon("res/Wall.jpg"));

            CGame.usedCoord.add(new Point(j, 0));
            CGame.usedCoord.add(new Point(j, getNbrLignes()));
        }
    }
}

