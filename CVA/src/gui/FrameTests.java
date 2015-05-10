package gui;

import gui.graphui.GSGraphicGraph;
import helper.FileHelper;
import io.CVAGraphIO;
import io.LoadingTypeException;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import algo.AbstractAlgorithm;
import algo.Algorithm;

public class FrameTests extends JDialog implements ActionListener{

	private File graph_dir;
	private List<Algorithm> algos;
	private JButton run;
	private JTextField dir_graph;
	
	public FrameTests(Dialog owner, String title, boolean modal){
		super(owner, title, modal);
		this.setSize(700, 500);
		
		graph_dir = null;
		algos = new ArrayList<Algorithm>();
		
		JPanel frame_panel = new JPanel(new BorderLayout());
		
		JPanel top_panel = new JPanel();
		JLabel name = new JLabel("Batterie de tests");
		name.setFont(new Font(name.getFont().getName(), Font.BOLD, 16));
		
		top_panel.add(name);
		frame_panel.add(top_panel, BorderLayout.NORTH);
		
		JPanel center_panel = new JPanel(new BorderLayout());
		
		JPanel dir_graph_panel = new JPanel();
		dir_graph_panel.setBorder(BorderFactory.createTitledBorder("Sélection du dossier de graphes"));
		
		dir_graph = new JTextField();
		dir_graph.setPreferredSize(new Dimension(400, 27));
		dir_graph_panel.add(dir_graph);
		
		JButton select_dir_graph = new JButton(new ImageIcon("style/open dossier.png"));
		select_dir_graph.setToolTipText("selectionner un dossier");
		select_dir_graph.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser dialogue = new JFileChooser();
				
				dialogue.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				dialogue.setMultiSelectionEnabled(false);
				int choice = dialogue.showOpenDialog(null);
				
				//si on a choisi un fichier
				if(choice == JFileChooser.APPROVE_OPTION){
					dir_graph.setText(dialogue.getSelectedFile().getAbsolutePath());
				}
			}
		});
		
		dir_graph_panel.add(select_dir_graph);
		
		center_panel.add(dir_graph_panel, BorderLayout.NORTH);
		
		
		JPanel algos_panel = new JPanel(new BorderLayout());
		
		List<Algorithm> v = AbstractAlgorithm.getAlgos();
		final DefaultListModel<String> model = new DefaultListModel<String>();
	    for (int i = 0, n = v.size(); i < n; i++) {
	        model.addElement(v.get(i).getName());
	      }
		JList<Algorithm> j_algos = new JList<Algorithm>(v.toArray(new Algorithm[0]));
		
		algos_panel.add(new JScrollPane(j_algos), BorderLayout.WEST);
		center_panel.add(algos_panel, BorderLayout.CENTER);
		
		
		frame_panel.add(center_panel, BorderLayout.CENTER);
		
		
		JPanel run_panel = new JPanel();
		run = new JButton("Run");
		run.addActionListener(this);

		run_panel.add(run);
		frame_panel.add(run_panel, BorderLayout.SOUTH);
		
		this.add(frame_panel);
		this.setVisible(true);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == run){
			System.out.println(dir_graph.getText());
			this.dispose();
		}
	}
}
