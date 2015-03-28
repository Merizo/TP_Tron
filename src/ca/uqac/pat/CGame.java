package ca.uqac.pat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.plaf.SliderUI;

public class CGame {
	
	boolean isRunning;
	private CEcranGUI Ecran;
	public static List<Point> usedCoord;
	
	public CGame() {
		this.isRunning = true;
		
		//Initialisation de la liste synchronized pour gérer les accès multiples
		this.usedCoord = Collections.synchronizedList(new ArrayList<Point>());
	}
	
	public void play(){
		int Largeur = 65;
		int Hauteur = 65;
		ImageIcon Im = new ImageIcon ("Black.gif");		
		Ecran = new CEcranGUI (Hauteur, Largeur, Im);
		while(isRunning){	
			
			//SI l'utilisateur veut rejouer
			if(this.Ecran.getGameOver() == true){
				this.Ecran.Fen.setVisible(false);
				Ecran = new CEcranGUI (Hauteur, Largeur, Im);
			}
		}
	}
}
