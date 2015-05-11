package gui;


import graph.AGraph;
import gui.graphui.GSGraphicGraph;
import gui.graphui.IGraphicGraph;
import helper.FileHelper;
import io.CVAGraphIO;
import io.LoadingTypeException;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.List;

import javax.swing.DefaultListModel;
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
			//verification du folder config et du fichier pathgraph
			File directory = new File("config");
			
			if(!directory.exists() || !directory.isDirectory()){
				directory.mkdir();
			}
			
			File pathGraph = new File(directory, "pathgraph.conf");
			if(!pathGraph.exists()){
				try {
					pathGraph.createNewFile();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
			
			
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
						String dir = null;
						File directory = null;
						List<String> temp = FileHelper.readFile("config/pathgraph.conf");
						if(temp != null && temp.size() >= 1){
							dir = temp.get(0);
							directory = new File(dir);

						}
						if(directory != null && directory.exists() && directory.isDirectory())	
							dialogue.setCurrentDirectory(directory);
						
						dialogue.setFileSelectionMode(JFileChooser.FILES_ONLY);
						dialogue.setMultiSelectionEnabled(false);
						int choice = dialogue.showOpenDialog(null);
						
						//si on a choisi un fichier
						if(choice == JFileChooser.APPROVE_OPTION){
							FileWriter fw = new FileWriter ("config/pathgraph.conf");
							BufferedWriter bw = new BufferedWriter (fw);
							PrintWriter fichierSortie = new PrintWriter (bw); 
							fichierSortie.println (dialogue.getSelectedFile().getParentFile().getAbsolutePath()); 
							fichierSortie.close();
						
							mygraph = CVAGraphIO.read(dialogue.getSelectedFile().getAbsolutePath());
							igg = new GSGraphicGraph(mygraph);
							left.switchGraph(mygraph);
							if(((BorderLayout)mainWindow.getLayout()).getLayoutComponent(BorderLayout.CENTER)!=null){
								mainWindow.remove(((BorderLayout)mainWindow.getLayout()).getLayoutComponent(BorderLayout.CENTER));
							}	
							mainWindow.add((Component) igg.getGraphicGraphComponent(), BorderLayout.CENTER);
							mainWindow.validate();
						}
						
					} catch (IOException e1) {
						e1.printStackTrace();
					} catch (LoadingTypeException e1) {
						e1.printStackTrace();
					}
					
	            }
			});
			menuMenu.add(g1); 
		
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
			
			JMenuItem edition = new JMenuItem("Editer");
			edition.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e){
					if(!left.isGraph()){
						JOptionPane.showMessageDialog(null, "Aucun graphe n'a été chargé", "missing graph", JOptionPane.ERROR_MESSAGE);
					}else{

						((GSGraphicGraph)igg).switchEditMode();
					}
				}
			});
			
			menuVisualisation.add(edition);
			
			
			JMenu menuRun = new JMenu("Execute");
			menuBar.add(menuRun);
			JMenuItem runItem = new JMenuItem("Run",KeyEvent.VK_F1);
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
			
			JMenuItem testsItem = new JMenuItem("multiples tests",KeyEvent.VK_T);
			testsItem.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e){
					FrameTests tests = new FrameTests(null, "Réaliser une batterie de tests", true);
				}
			});
			menuRun.add(testsItem);
			
			this.add(mainWindow);
			this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

			this.setVisible(true);
		}
}
