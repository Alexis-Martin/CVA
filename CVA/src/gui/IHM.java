package gui;

import graph.AGraph;
import gui.graphui.GSGraphicGraph;
import gui.graphui.IGraphicGraph;
import io.CVAGraphIO;
import io.LoadingTypeException;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import algo.AbstractAlgorithm;
import algo.Algorithm;


	public class IHM extends JFrame{

		private JPanel mainWindow;
		private  AGraph mygraph;
		private IGraphicGraph igg = null;
		
		public IHM(){

			//création de la frame
			this.setTitle("Calcul de valeurs d'arguments");
			this.setSize(900, 600);

			
			mainWindow = new JPanel(new BorderLayout()); 

			
			final LeftComponent left = new LeftComponent();
			mainWindow.add(left, BorderLayout.WEST);
			
			//menu
			JMenuBar menuBar = new JMenuBar();
			this.setJMenuBar(menuBar);
			
			JMenu menuMenu = new JMenu("Menu");
			menuBar.add(menuMenu);
			
			JMenuItem g1 = new JMenuItem("Charger Graphe",KeyEvent.VK_C);
			g1.addActionListener(new ActionListener() {
			  public void actionPerformed(ActionEvent e)
	            {
				  mygraph = null;
					try {
						JFileChooser dialogue = new JFileChooser();
						dialogue.showOpenDialog(null);
						System.out.println("file " + dialogue.getSelectedFile().getAbsolutePath());
						mygraph = CVAGraphIO.read(dialogue.getSelectedFile().getAbsolutePath());
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (LoadingTypeException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					igg = new GSGraphicGraph(mygraph);
					((GSGraphicGraph)igg).start();
					left.switchGraph(mygraph);
					mainWindow.add((Component) igg.getGraphicGraphComponent(), BorderLayout.CENTER);
					mainWindow.validate();
	            }
			});
			menuMenu.add(g1); 
			/*
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
			*/
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
			JMenuItem edition = new JMenuItem("Editer",KeyEvent.VK_T);
			edition.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e){
					System.out.println("LA");
				//	if(!left.isGraph()){
				//		JOptionPane.showMessageDialog(null, "Aucun graphe n'a été chargé", "missing graph", JOptionPane.ERROR_MESSAGE);
				//	}else{
						left.run();
						igg.setAutomaticTopology(false);
						((GSGraphicGraph)igg).setPositionChange(false);
				//	}
				}
			});		
			menuVisualisation.add(edition);
			
			JMenu menuRun = new JMenu("Execute");
			menuBar.add(menuRun);
			JMenuItem runItem = new JMenuItem("Run",KeyEvent.VK_T);
			runItem.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e){
					if(!left.isGraph()){
						JOptionPane.showMessageDialog(null, "Aucun graphe n'a été chargé", "missing graph", JOptionPane.ERROR_MESSAGE);
					}else if(!left.isAlgo()){
						JOptionPane.showMessageDialog(null, "Aucun algorithme n'a été chargé", "missing algorithm", JOptionPane.ERROR_MESSAGE);
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
			
			this.add(mainWindow);
			this.setVisible(true);
		}
}
