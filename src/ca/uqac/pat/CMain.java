package ca.uqac.pat;

import javax.swing.*;


public class CMain {
	public static void main(String[] args) {
		int Largeur = 65;
		int Hauteur = 65;
		
		ImageIcon Im = new ImageIcon ("Black.gif");		
		CEcranGUI Ecran = new CEcranGUI (Hauteur, Largeur, Im);
		
		/*CLightCycle ForceOrange= new CLightCycle(Ecran, 12, 30, 10, 1, "Orange");
		CLightCycle ForcePurple= new CLightCycle(Ecran, 12, 40, 10, 1, "Purple");
		CLightCycle ForceGreen= new CLightCycle(Ecran, 12, 50, 10, 1, "Green");*/

	}	
}
