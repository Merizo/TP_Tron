package ca.uqac.pat;

import javax.swing.*;


public interface IEcranGUI {	
	public abstract boolean setIcon (int Lig, int Col, Icon Im);
	public abstract void gameOver();
	public abstract int getNbrLignes();
	public abstract int getNbrColonnes();
	public abstract JLabel[][] getGrille();
}
