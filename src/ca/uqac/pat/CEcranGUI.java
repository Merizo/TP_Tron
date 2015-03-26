package ca.uqac.pat;


import javax.swing.*;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


public class CEcranGUI extends JComponent implements IEcranGUI, KeyListener{
	private static final long serialVersionUID = 1L;

	public final JFrame 	Fen;		// La fenetre d'interface

	private int 			NbrLignes;
	private int 			NbrColonnes;
	private JLabel[][] 		Grille;
	private CPlayer player;


//	public CEcranGUI(){
//		this(50, 10, 80, 10, 12, null);
//		Fen.addKeyListener(this);
//	}
//
//
//	public CEcranGUI(int nbrLignes, int hauteur){
//		this (nbrLignes, 10, hauteur, 10, 12, null);
//		Fen.addKeyListener(this);
//	}


	public CEcranGUI(int nbrLignes, int hauteur, ImageIcon fond){
		this(nbrLignes, 10, hauteur, 10, 8, fond);
		Fen.addKeyListener(this);
	}


//	public CEcranGUI(int nbrLignes, int hauteur, int size){
//		this(nbrLignes, 10, hauteur, 10, size, null);
//	}

	/** Le constructeur complet. */
	public CEcranGUI(	int nbrLignes, 		int hauteur, 
						int nbrColonnes, 	int largeur,
						int sizeText, 		ImageIcon fond) {
		NbrLignes 		= nbrLignes;
		NbrColonnes		= nbrColonnes;

		setLayout (new GridLayout(NbrLignes, NbrColonnes, 0 ,0));


		Grille = new JLabel[NbrLignes][NbrColonnes];



		JLabel Bareme = new JLabel ("Bareme");
		Font petit = new Font (	Bareme.getFont().getName(),
								Bareme.getFont().getStyle(), sizeText);
		for (int y=0; y < Grille.length; y++)
			for (int x = 0; x < Grille[y].length; x++){
				Grille[y][x] = new JLabel(fond);
				add(Grille[y][x]);

				Grille[y][x].setFont(petit);
				Grille[y][x].setBorder(BorderFactory.createEmptyBorder());
			}
		Fen = new CFrameEcran (this, NbrColonnes*largeur, NbrLignes*hauteur);

		player = new CPlayer(this, 12, 20, CLightCycle.Direction.RIGHT, 2, "White");
	}

	public void gameOver(){
		
		 String name = JOptionPane.showInputDialog(this,
                 "Voulez vous rejouer?", null);
	}
	

	public boolean setIcon (int Lig, int Col, Icon Im){
		if (Lig >= Grille.length || Lig < 0)		return false;
		if (Col >= Grille[Lig].length || Col< 0) 	return false;

		Grille[Lig][Col].setIcon(Im);
		return true;
	}


	public boolean setText (int Lig, int Col, String txt){
		if (Lig >= Grille.length) 		return false;
		if (Col >= Grille[Lig].length) 	return false;

		Grille[Lig][Col].setText (txt);
		return true;
	}		
	
	 @Override
    public void keyPressed(KeyEvent e) 
    {
        switch (e.getKeyCode())
        {
        case KeyEvent.VK_DOWN :
            System.out.println("Bas");
            player.direction = CLightCycle.Direction.DOWN;
            break;
        case KeyEvent.VK_LEFT :
        	player.direction = CLightCycle.Direction.LEFT;
            break;
        case KeyEvent.VK_RIGHT :
        	player.direction = CLightCycle.Direction.RIGHT;
            break;
        case KeyEvent.VK_UP :
        	player.direction = CLightCycle.Direction.UP;
            break;
        default :
    	}
    }


	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	public int getNbrLignes() {
		return NbrLignes;
	}
	
	public int getNbrColonnes() {
		return NbrColonnes;
	}
}

