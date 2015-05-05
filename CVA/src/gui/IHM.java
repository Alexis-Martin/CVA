package gui;

import graph.AGraph;
import gui.graphui.GSGraphicGraph;
import gui.graphui.IGraphicGraph;
import io.CVAGraphIO;
import io.LoadingTypeException;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.List;

import javax.swing.* ; 

import algo.AbstractAlgorithm;
import algo.Algorithm;


	public class IHM {

		private JSplitPane mainWindow;
		private  AGraph mygraph;
		private IGraphicGraph igg = null;
		public IHM()
		{
			final JSplitPane mainWindow = new JSplitPane(); 
			mainWindow.setRightComponent(null);
			JFrame frame = new JFrame("Calcul de valeur d'arguments");
			frame.setSize(900, 600);
			
			final LeftComponent left = new LeftComponent();
			mainWindow.setLeftComponent(left);
			
			JMenuBar menuBar = new JMenuBar();
			frame.setJMenuBar(menuBar);
			
			JMenu menuMenu = new JMenu("Menu");
			menuBar.add(menuMenu);
			JMenuItem g1 = new JMenuItem("Charger Graphe 1",KeyEvent.VK_C);
			menuMenu.add(g1); 
			g1.addActionListener(new ActionListener() {
			  public void actionPerformed(ActionEvent e)
	            {
				  mygraph = null;
					try {
						mygraph = CVAGraphIO.read("savefile/graph_exemple_1.dgs");
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (LoadingTypeException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					igg = new GSGraphicGraph(mygraph);
					left.switchGraph(mygraph);
					mainWindow.setRightComponent((Component) igg.getGraphicGraphComponent());
	            }
			});
			
			JMenuItem g2 = new JMenuItem("Charger Graphe 2",KeyEvent.VK_C);
			menuMenu.add(g2); 
			g2.addActionListener(new ActionListener() {
			  public void actionPerformed(ActionEvent e)
	            {
				  mygraph = null;
					try {
						mygraph = CVAGraphIO.read("savefile/graph_exemple_2.dgs");
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (LoadingTypeException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					igg = new GSGraphicGraph(mygraph);
					left.switchGraph(mygraph);
					mainWindow.setRightComponent((Component) igg.getGraphicGraphComponent());
	            }
			}); 
			
			JMenu menuAlgorithmes = new JMenu("Algorithmes");
			menuBar.add(menuAlgorithmes);
			List<Algorithm> algos = AbstractAlgorithm.getAlgos();
			for(final Algorithm algo : algos){
				JMenuItem algoItem = new JMenuItem(algo.getName(),KeyEvent.VK_T);
				algoItem.addActionListener(
			            new ActionListener(){
			                public void actionPerformed(ActionEvent e)
			                {
		                		left.switchAlgo(algo);
		                		System.out.println("Algo "+algo.getName()+" loaded");
			                }});
				menuAlgorithmes.add(algoItem);
			}
			
			JMenu menuVisualisation = new JMenu("Visualisation");
			menuBar.add(menuVisualisation);
			
			JMenu menuRun = new JMenu("Execute");
			menuBar.add(menuRun);
			JMenuItem runItem = new JMenuItem("Run",KeyEvent.VK_T);
			runItem.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e){
					if(!left.isGraph()){
						System.out.println("No graph");
					}else if(!left.isAlgo()){
						System.out.println("No algo");
					}else{
						left.run();
						igg.refresh();
					}
				}
			});
			menuRun.add(runItem);
			
			/*
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
					if(!left.isGraph()){
						System.out.println("No graph");
					}else if(!left.isAlgo()){
						System.out.println("No algo");
					}else{
						left.run();
						igg.refresh();
					}
				}
			});
			*/
			
			frame.add(mainWindow);
			this.mainWindow = mainWindow; 
			frame.setVisible(true);
		}
		
		public void addGraphVisu(Component component){
			this.mainWindow.setRightComponent(component);
		}

}
