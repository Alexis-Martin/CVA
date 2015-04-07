package IHM;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.KeyEvent;

import javax.swing.* ; 

	public class IHM {

		public IHM()
		{
		
			JFrame frame = new JFrame("interface");
			frame.setSize(900, 600);
			frame.setVisible(true);
			
			JMenuBar menuBar = new JMenuBar();
			frame.setJMenuBar(menuBar);
			
			JMenu menuMenu = new JMenu("Menu");
			menuBar.add(menuMenu);
			JMenuItem fonction1 = new JMenuItem("Fonction1",KeyEvent.VK_T);
			menuMenu.add(fonction1);
			testOnglet onglet1 = new testOnglet(); 
			
			JMenu menuAlgorithmes = new JMenu("Algorithmes");
			menuBar.add(menuAlgorithmes);
			
			JMenu menuVisualisation = new JMenu("Visualisation");
			menuBar.add(menuVisualisation);
			
			Box layoutPrincipal = Box.createHorizontalBox();
			
			
			//testOnglet onglet = new testOnglet(); 
			JPanel ongletPanel = new JPanel() ; 
			OngletCreater onglets = new OngletCreater();
			onglets.setPreferredSize(new Dimension(300,300));
			ongletPanel.add(onglets); 
			layoutPrincipal.add(ongletPanel);
			//testOnglet onglet2 = new testOnglet(); 
			//layoutPrincipal.add(onglet2);
			layoutPrincipal.add(Box.createHorizontalGlue());
			
			
			
			frame.add(layoutPrincipal);
		
		}
}
