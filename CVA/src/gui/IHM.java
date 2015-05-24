package gui;


import gui.graphui.GSGraphicGraph;
import gui.graphui.GraphicGraph;
import helper.FileHelper;
import io.GSGraphIO;
import io.Loader;
import io.LoadingTypeException;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
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
import javax.swing.ToolTipManager;

import af.ArgumentationFramework;
import af.Argument;
import algo.AbstractAlgorithm;
import algo.Algorithm;


	public class IHM extends JFrame{

		private JPanel mainWindow;
		private  ArgumentationFramework mygraph;
		private GraphicGraph igg = null;
		private ArgumentEditorComponent aeg = null;
		private LeftComponent left;
		public IHM(){
			
			ToolTipManager.sharedInstance().setInitialDelay(500);
	        ToolTipManager.sharedInstance().setDismissDelay(999999999);
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
			this.setMinimumSize(new Dimension(800, 600));

			
			mainWindow = new JPanel(new BorderLayout()); 

			
			left = new LeftComponent(this);
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
						
							mygraph = Loader.load(dialogue.getSelectedFile().getAbsolutePath());
							igg = new GSGraphicGraph(mygraph);

							left.switchGraph(mygraph);
							
							if(((BorderLayout)mainWindow.getLayout()).getLayoutComponent(BorderLayout.CENTER)!=null){
								mainWindow.remove(((BorderLayout)mainWindow.getLayout()).getLayoutComponent(BorderLayout.CENTER));
							}
							JPanel gPanel = new JPanel(new GridLayout(0,1));
							gPanel.add(igg.getGraphicGraphComponent());
						//	aeg = new ArgumentEditorComponent(igg,mygraph);
						//	mainWindow.add(aeg, BorderLayout.EAST);
							mainWindow.add(gPanel, BorderLayout.CENTER);
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
			
			JMenuItem ajouterArgument = new JMenuItem("Ajout d'un Argument");
			ajouterArgument.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e){
					if(!left.isGraph()){
						JOptionPane.showMessageDialog(null, "Aucun graphe n'a été chargé", "missing graph", JOptionPane.ERROR_MESSAGE);
					}else{
						String argumentId = JOptionPane.showInputDialog("Entrez un id pour l'argument");
						String argumentDesc = JOptionPane.showInputDialog("Entrez une description pour l'argument");
						Argument toAddArgument = mygraph.addArgument(argumentId, argumentDesc);
						// Je dois reconnaitre ne pas savoir comment ajouter l'argument au graphe
						// Il faut faire une autre fenetre pour demander une utilit�� et quels argument il attaque ou d��fends ? avant de pouvoir l'ajouter 
						// ou on peut le faire apres ? 
						// Et oui je vais plutot faire un pop up ou on va demander toutes les informations necessaires plutot
						// que d'avoir une fenetre qui s'ouvre pour chaque informations
					}
				}
			});
			
			menuVisualisation.add(ajouterArgument);
			
			
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
						
					}
				}
			});
			menuRun.add(runItem);
			
			JMenuItem testsItem = new JMenuItem("multiples tests",KeyEvent.VK_T);
			testsItem.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e){
					new FrameTests(null, "Réaliser une batterie de tests", true);
				}
			});
			menuRun.add(testsItem);
			
			this.add(mainWindow);
			this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

			this.setVisible(true);
		}
		public void refresh() {
			igg.refresh();
			left.MajInterface();
		}
}
