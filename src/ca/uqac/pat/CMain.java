package ca.uqac.pat;

import javax.swing.*;


public class CMain {
	public static void main(String[] args) {
		int Largeur = 65;
		int Hauteur = 65;
		
		ImageIcon Im = new ImageIcon ("Black.jpg");		
		CEcranGUI Ecran = new CEcranGUI (Hauteur, Largeur, Im);
	}	
}
