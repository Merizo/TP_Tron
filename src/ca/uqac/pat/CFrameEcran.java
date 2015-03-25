package ca.uqac.pat;


import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


public class CFrameEcran extends JFrame{
	private static final long serialVersionUID = 1L;
	private JComponent Centre;
	
	public CFrameEcran(JComponent centre, int Lar, int Hau) {
		super ("Power ANSI REPLACER");
		Centre 	= centre;
		
		setContentPane(Centre);
		
		pack();
		setVisible (true);
		
		addWindowListener(new WindowAdapter(){
			@Override
			public void windowClosing(WindowEvent e){
				System.out.println("Fin");
				System.exit(1);
			}});
	}
}
