package ca.uqac.pat;

import javax.swing.*;


public interface IEcranGUI {	
	boolean setIcon(int Lig, int Col, Icon Im);
	void gameOver();
	int getNbrLignes();
	int getNbrColonnes();
	JLabel[][] getGrille();

	CPlayer getPlayer();
}
