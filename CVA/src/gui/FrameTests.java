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
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;
import java.util.Map.Entry;

import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
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
import javax.swing.ListCellRenderer;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import algo.AbstractAlgorithm;
import algo.Algorithm;
import algo.Parameter;

public class FrameTests extends JDialog implements ActionListener{

	private File graph_dir;
	private List<Algorithm> algos;
	private JButton run;
	private JTextField dir_graph;
	private JPanel detail_algo_panel;
	
	public FrameTests(Dialog owner, String title, boolean modal){
		super(owner, title, modal);
		this.setSize(900, 300);
		
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
		
		JList<Algorithm> j_algos = getJListAlgos(new Vector<Algorithm>(AbstractAlgorithm.getAlgos()));
		j_algos.addListSelectionListener(new ListSelectionListener() {
			@SuppressWarnings("unchecked")
			@Override
			public void valueChanged(ListSelectionEvent e) {
				detailAlgo(((JList<Algorithm>)e.getSource()).getSelectedValue());
				
			}
		});
		
		algos_panel.add(new JScrollPane(j_algos), BorderLayout.WEST);
		
		detail_algo_panel = new JPanel(new BorderLayout());
		algos_panel.add(detail_algo_panel, BorderLayout.CENTER);
		
		JList<Algorithm> j_result = getJListAlgos(new Vector<Algorithm>());
		algos_panel.add(new JScrollPane(j_result), BorderLayout.EAST);
		
		
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

	
	public void detailAlgo(Algorithm alg){
		try{
			detail_algo_panel.removeAll();
		}
		catch(NullPointerException e ){}
		HashMap<String,Parameter> params = alg.getParams();
		JPanel parameters = new JPanel(new GridLayout(2, 0, 10, 5));
		int param = 0;
		for (Entry<String, Parameter> entry : params.entrySet()) {
			JPanel parameter = new JPanel();
		    String list = entry.getKey();
		    
		    JLabel tmpLabel = new JLabel(list + " de"); 
		    
		    JTextField tmpField = new JTextField(entry.getValue().printVal()); 
		    tmpField.setPreferredSize(new Dimension(70, 27));

		    JLabel tmpLabel2 = new JLabel("à");

		    JTextField tmpField2 = new JTextField(entry.getValue().printVal()); 
		    tmpField2.setPreferredSize(new Dimension(70, 27));

		    JLabel tmpLabel3 = new JLabel("pas");

		    JTextField tmpField3 = new JTextField("0.0001"); 
		    tmpField3.setPreferredSize(new Dimension(70, 27));
		    
		    parameter.add(tmpLabel);
			parameter.add(tmpField);
			parameter.add(tmpLabel2);
			parameter.add(tmpField2);
			parameter.add(tmpLabel3);
			parameter.add(tmpField3);
			
			parameters.add(parameter);
			param++;

		}
		detail_algo_panel.add(parameters, BorderLayout.CENTER);
		
		JPanel button_panel = new JPanel();
		JButton add = new JButton("Ajouter");
		button_panel.add(add);
		detail_algo_panel.add(button_panel, BorderLayout.SOUTH);
		detail_algo_panel.setPreferredSize(new Dimension(param * 50 + 30, param * 80 + 30));
		detail_algo_panel.validate();
		this.validate();
	}
	
	
	public JList<Algorithm> getJListAlgos(Vector<Algorithm> algos){
		JList<Algorithm> j_algos = new JList<Algorithm>(algos);
		j_algos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		j_algos.setCellRenderer(new ListCellRenderer<Algorithm>() {
			
			protected DefaultListCellRenderer defaultRenderer = new DefaultListCellRenderer();
			@Override
			public Component getListCellRendererComponent(JList<? extends Algorithm> list, Algorithm value,
														int index, boolean isSelected, boolean cellHasFocus) {
				
				JLabel renderer = (JLabel) defaultRenderer.getListCellRendererComponent(list, value, index,
				        				isSelected, cellHasFocus);
				renderer.setText(value.getName());
				return renderer;
			}
			
		});
		
		return j_algos;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == run){
			System.out.println(dir_graph.getText());
			this.dispose();
		}
	}
	
}
