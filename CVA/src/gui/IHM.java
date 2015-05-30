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
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.ToolTipManager;
import javax.swing.filechooser.FileNameExtensionFilter;

import af.ArgumentationFramework;
import af.Argument;
import af.GSArgumentationFramework;
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
			
			JPanel voidPanelGraph = new JPanel(new GridLayout(0,1));
			JLabel voidLabelGraph = new JLabel("Aucun réseau chargé");
			voidPanelGraph.add(voidLabelGraph);
			mainWindow.add(voidPanelGraph, BorderLayout.CENTER);
			
			//menu
			JMenuBar menuBar = new JMenuBar();
			this.setJMenuBar(menuBar);
			
			JMenu menuMenu = new JMenu("AF");
			menuBar.add(menuMenu);
			JMenuItem g3 = new JMenuItem("Nouveau",KeyEvent.VK_A);
			g3.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e)
		    	{
		    	String graphe_name = JOptionPane.showInputDialog(null, "Choisissez le nom du graphe");
				System.out.println(graphe_name);
				if(graphe_name == null) return;
		    	if(graphe_name == "")
					mygraph = new GSArgumentationFramework("default_name");
				mygraph = new GSArgumentationFramework(graphe_name);
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
			});
			JMenuItem g1 = new JMenuItem("Charger",KeyEvent.VK_C);
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
							
							System.out.println("Chargement du graphe "+dialogue.getSelectedFile().getAbsolutePath());
							
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
			JMenuItem g2 = new JMenuItem("Sauvegarder",KeyEvent.VK_A);
			g2.addActionListener(new ActionListener() {
			  public void actionPerformed(ActionEvent e)
	            {
				  if(mygraph == null)
					  return;
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
						dialogue.setFileFilter(new FileNameExtensionFilter("DGS file", "dgs", "DGS"));

						int choice = dialogue.showSaveDialog(null);
						//si on a choisi un fichier
						if(choice == JFileChooser.APPROVE_OPTION){
							if(dialogue.getSelectedFile() == null) return;
							FileWriter fw = new FileWriter ("config/pathgraph.conf");
							BufferedWriter bw = new BufferedWriter (fw);
							PrintWriter fichierSortie = new PrintWriter (bw); 
							fichierSortie.println (dialogue.getSelectedFile().getParentFile().getAbsolutePath()); 
							fichierSortie.close();
							String[] split = dialogue.getSelectedFile().getAbsolutePath().split("\\.");
							String ext = split[split.length-1];
							if(!ext.equals("dgs")&&!ext.equals("DGS")){
								JOptionPane.showMessageDialog(null, "Vous ne pouvez sauvegarder qu'avec l'extenssion dgs");
								return;
							}
							
							System.out.println("Sauvegarde du graphe à "+dialogue.getSelectedFile().getAbsolutePath());
							
							Loader.write(dialogue.getSelectedFile().getAbsolutePath(),mygraph);

						}
						
					} catch (IOException e1) {
						e1.printStackTrace();
					} catch (LoadingTypeException e1) {
						e1.printStackTrace();
					}
					
	            }
			});
			menuMenu.add(g3); 
			menuMenu.add(g1); 
			menuMenu.add(g2); 
		
			JMenu menuAlgorithmes = new JMenu("Algorithme");
			menuBar.add(menuAlgorithmes);
			List<Algorithm> algos = AbstractAlgorithm.getAlgos();
		
			for(final Algorithm algo : algos){
				JMenuItem algoItem = new JMenuItem(algo.getName(),KeyEvent.VK_T);
				algoItem.addActionListener(
			            new ActionListener(){
			                public void actionPerformed(ActionEvent e)
			                {
			                	System.out.println("Chargmenet de l'algo" + algo.getName());
		                		left.switchAlgo(algo);
			                }});
				menuAlgorithmes.add(algoItem);
			}
			
			JMenu menuRun = new JMenu("Exécution");
			menuBar.add(menuRun);
			JMenuItem runItem = new JMenuItem("Lancer",KeyEvent.VK_F1);
			runItem.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e){
					if(!left.isGraph()){
						JOptionPane.showMessageDialog(null, "Aucun graphe n'a été chargé", "missing graph", JOptionPane.ERROR_MESSAGE);
					}else if(!left.isAlgo()){
						JOptionPane.showMessageDialog(null, "Aucun algorithme n'a été chargé", "missing algorithm", JOptionPane.ERROR_MESSAGE);
					}else{
						System.out.println("Lancement de l'algorithme "+ left.getAlgo().getName());
						left.run();
						
					}
				}
			});
			menuRun.add(runItem);
			
			JMenuItem testsItem = new JMenuItem("Batterie de tests",KeyEvent.VK_T);
			testsItem.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e){
					new FrameTests(null, "Réaliser une batterie de tests", true);
				}
			});
			menuRun.add(testsItem);
			
			JMenu menuVisualisation = new JMenu("Edition");
			menuBar.add(menuVisualisation);
			
			JMenuItem edition = new JMenuItem("Mode Edition");
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
			
			this.add(mainWindow);
			this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

			this.setVisible(true);
		}
		public void refresh() {
			igg.refresh();
			left.MajInterface();
		}
}
