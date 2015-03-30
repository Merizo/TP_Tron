package ca.uqac.pat;


import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


public class CFrameEcran extends JFrame{
	public CFrameEcran(JComponent centre) {
		super ("Power ANSI REPLACER");

		setContentPane(centre);
		
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
