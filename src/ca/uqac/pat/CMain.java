package ca.uqac.pat;


import java.util.Random;
import javax.swing.*;


public class CMain {
	public static void main(String[] args) {
		//System.setProperty("java.util.Arrays.useLegacyMergeSort", "true");
		int Largeur = 65;
		int Hauteur = 65;
		
		ImageIcon Im = new ImageIcon ("Black.gif");		
		CEcranGUI Ecran = new CEcranGUI (Hauteur, Largeur, Im);

	}	
}
