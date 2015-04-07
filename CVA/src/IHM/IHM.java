package IHM;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.* ; 

import IHMGraph.IHMGraph;

	public class IHM {

		private JSplitPane mainWindow;

		public IHM()
		{
			//mouhahaha
			JFrame frame = new JFrame("interface");
			frame.setSize(900, 600);
			frame.setVisible(true);
			
			JMenuBar menuBar = new JMenuBar();
			frame.setJMenuBar(menuBar);
			
			JMenu menuMenu = new JMenu("Menu");
			menuBar.add(menuMenu);
			JMenuItem fonction1 = new JMenuItem("Fonction1",KeyEvent.VK_T);
			menuMenu.add(fonction1);
			final OngletCategoriser onglet1 = new OngletCategoriser(); 
			
			JMenu menuAlgorithmes = new JMenu("Algorithmes");
			menuBar.add(menuAlgorithmes);
			
			JMenu menuVisualisation = new JMenu("Visualisation");
			menuBar.add(menuVisualisation);
			
			
			JPanel ongletPanel = new JPanel() ; 
			final OngletCreater onglets = new OngletCreater();
			onglets.setPreferredSize(new Dimension(300,300));
			ongletPanel.add(onglets); 

			
			JSplitPane mainWindow = new JSplitPane(); 

			mainWindow.setLeftComponent(ongletPanel);

			
			fonction1.addActionListener(
		            new ActionListener(){
		                public void actionPerformed(ActionEvent e)
		                {
		                 onglets.addOnglet(onglet1);
		                }});
			
			
			
			
			frame.add(mainWindow);
			this.mainWindow = mainWindow; 

		}
		
		public void addGraphVisu(Component component){
			this.mainWindow.setRightComponent(component);
		}

}
