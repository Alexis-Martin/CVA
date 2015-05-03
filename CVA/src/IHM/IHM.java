package IHM;

import in_out.CVAGraphIO;
import in_out.LoadingTypeException;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.List;

import javax.swing.* ; 

import algo.AbstractAlgorithm;
import algo.Algorithm;
import algo.implem.Categoriser;
import CVAGraph.AGraph;
import CVAGraph.Argument;
import IHMGraph.GSGraphicGraph;
import IHMGraph.IGraphicGraph;


	public class IHM {

		private JSplitPane mainWindow;
		private  AGraph mygraph;
		private IGraphicGraph igg;
		public IHM()
		{
			final JSplitPane mainWindow = new JSplitPane(); 
			mainWindow.setRightComponent(null);
			JFrame frame = new JFrame("Calcul de valeur d'arguments");
			frame.setSize(900, 600);
			
			JMenuBar menuBar = new JMenuBar();
			frame.setJMenuBar(menuBar);
			
			JMenu menuMenu = new JMenu("Menu");
			menuBar.add(menuMenu);
			JMenuItem load = new JMenuItem("Charger Graphe",KeyEvent.VK_C);
			menuMenu.add(load); 
			igg =null ;
			load.addActionListener(
					new ActionListener() {
						  public void actionPerformed(ActionEvent e)
			                {
							  mygraph = null;
								try {
									mygraph = CVAGraphIO.read("savefile/graph_exemple.dgs");
								} catch (IOException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								} catch (LoadingTypeException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
								igg =  new GSGraphicGraph(mygraph);
								mainWindow.setRightComponent((Component) igg.getGraphicGraphComponent());
			                }
					}
					); 
			
			
			final LeftComponent left = new LeftComponent();
			mainWindow.setLeftComponent(left);
			
			JMenu menuAlgorithmes = new JMenu("Algorithmes");
			menuBar.add(menuAlgorithmes);
			List<Algorithm> algos = AbstractAlgorithm.getAlgos();
			for(final Algorithm algo : algos){
				JMenuItem algoItem = new JMenuItem(algo.getName(),KeyEvent.VK_T);
				algoItem.addActionListener(
			            new ActionListener(){
			                public void actionPerformed(ActionEvent e)
			                {
			                	if (mygraph != null)
			                	{
			                		left.switchAlgo(algo);
			                		System.out.print("Algo "+algo.getName()+" loaded");
			                		left.switchGraph(mygraph);
			                	}
			                }});
				menuAlgorithmes.add(algoItem);
			}
			
			JMenu menuVisualisation = new JMenu("Visualisation");
			menuBar.add(menuVisualisation);
			
			JPanel ongletPanel = new JPanel() ; 
			final OngletCreater onglets = new OngletCreater();
			onglets.setPreferredSize(new Dimension(300,300));
			ongletPanel.add(onglets); 
			Box leftBox = Box.createVerticalBox();
			leftBox.add(ongletPanel);
			JButton run = new JButton("Run");
			leftBox.add(run);
			run.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e){
					left.run();
					igg.refresh();
					
					
					List<Argument> list = mygraph.getUtilities();
					if(list.size() != 0)
						System.out.print("(" + list.get(0).getId() +", " + list.get(0).getUtility() + ")");
					for(int i = 1; i < list.size(); i++){
						if(list.get(i).getUtility() < list.get(i-1).getUtility())
							System.out.print(" > ");
						else
							System.out.print(" = ");
						
						System.out.print("(" + list.get(i).getId() +", " + list.get(i).getUtility() + ")");
					}
				}
			});
			
			frame.add(mainWindow);
			this.mainWindow = mainWindow; 
			frame.setVisible(true);
		}
		
		public void addGraphVisu(Component component){
			this.mainWindow.setRightComponent(component);
		}

}
