package IHM;

import in_out.CVAGraphIO;
import in_out.LoadingTypeException;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.IOException;

import javax.swing.* ; 

import CVAGraph.AGraph;
import IHMGraph.IHMGraph;

	public class IHM {

		private JSplitPane mainWindow;

		public IHM()
		{
			final JSplitPane mainWindow = new JSplitPane(); 
			mainWindow.setRightComponent(null);
			JFrame frame = new JFrame("interface");
			frame.setSize(900, 600);
			frame.setVisible(true);
			
			JMenuBar menuBar = new JMenuBar();
			frame.setJMenuBar(menuBar);
			
			JMenu menuMenu = new JMenu("Menu");
			menuBar.add(menuMenu);
			JMenuItem load = new JMenuItem("Charger Graphe",KeyEvent.VK_C);
			menuMenu.add(load); 
			load.addActionListener(
					new ActionListener() {
						  public void actionPerformed(ActionEvent e)
			                {
							  AGraph mygraph = null;
								try {
									mygraph = CVAGraphIO.read("savefile/graph_exemple.dgs");
								} catch (IOException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								} catch (LoadingTypeException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
								IHMGraph ihmgraph =  new IHMGraph(mygraph);
								mainWindow.setRightComponent((Component) ihmgraph.getView());
			                }
					}
					); 
			
			
			final OngletCategoriser onglet1 = new OngletCategoriser(); 
			
			JMenu menuAlgorithmes = new JMenu("Algorithmes");
			menuBar.add(menuAlgorithmes);
			JMenuItem categoriser = new JMenuItem("Categoriser",KeyEvent.VK_T);
			menuAlgorithmes.add(categoriser);
			
			JMenu menuVisualisation = new JMenu("Visualisation");
			menuBar.add(menuVisualisation);
			
			
			JPanel ongletPanel = new JPanel() ; 
			final OngletCreater onglets = new OngletCreater();
			onglets.setPreferredSize(new Dimension(300,300));
			ongletPanel.add(onglets); 

			
			

			mainWindow.setLeftComponent(ongletPanel);

			
			categoriser.addActionListener(
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
