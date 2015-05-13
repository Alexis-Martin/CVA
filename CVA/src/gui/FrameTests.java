package gui;

import graph.Argument;
import graph.adapter.AGraphAdapter;
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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import algo.AbstractAlgorithm;
import algo.Algorithm;
import algo.Parameter;

public class FrameTests extends JDialog implements ActionListener{

	private Vector<MultipleTests> algos;
	private JButton run, add;
	private JTextField dir_graph;
	private JPanel detail_algo_panel;
	private JList<Algorithm> j_algos;
	private JList<MultipleTests> j_result;
	private JTextField dir_result;
	
	public FrameTests(Dialog owner, String title, boolean modal){
		super(owner, title, modal);
		this.setSize(900, 300);
		
		algos = new Vector<MultipleTests>();
		
		JPanel frame_panel = new JPanel(new BorderLayout());
		
		JPanel top_panel = new JPanel();
		JLabel name = new JLabel("Batterie de tests");
		name.setFont(new Font(name.getFont().getName(), Font.BOLD, 16));
		
		top_panel.add(name);
		frame_panel.add(top_panel, BorderLayout.NORTH);
		
		JPanel center_panel = new JPanel(new BorderLayout());
		
		JPanel dir_panel = new JPanel(new GridLayout(0, 1));
		JPanel dir_graph_panel = new JPanel();
		dir_graph_panel.setBorder(BorderFactory.createTitledBorder("Dossier de graphes"));
		
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
		
		dir_panel.add(dir_graph_panel);
		
		JPanel dir_result_panel = new JPanel();
		dir_result_panel.setBorder(BorderFactory.createTitledBorder("Enregistrement des résultats"));
		
		dir_result = new JTextField();
		dir_result.setPreferredSize(new Dimension(400, 27));
		dir_result_panel.add(dir_result);
		
		JButton select_result_graph = new JButton(new ImageIcon("style/open dossier.png"));
		select_result_graph.setToolTipText("selectionner un dossier");
		select_result_graph.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser dialogue = new JFileChooser();
				
				dialogue.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				dialogue.setMultiSelectionEnabled(false);
				int choice = dialogue.showOpenDialog(null);
				
				//si on a choisi un fichier
				if(choice == JFileChooser.APPROVE_OPTION){
					dir_result.setText(dialogue.getSelectedFile().getAbsolutePath());
				}
			}
		});
		
		dir_result_panel.add(select_result_graph);
		
		dir_panel.add(dir_result_panel);
		
		center_panel.add(dir_panel, BorderLayout.NORTH);
		
		
		JPanel algos_panel = new JPanel(new BorderLayout());
		
		j_algos = getJListAlgos(new Vector<Algorithm>(AbstractAlgorithm.getAlgos()));
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
		
		j_result = getJListResult();
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
		    tmpField.setName("de");

		    JLabel tmpLabel2 = new JLabel("à");

		    JTextField tmpField2 = new JTextField(entry.getValue().printVal()); 
		    tmpField2.setPreferredSize(new Dimension(70, 27));
		    tmpField2.setName("a");

		    JLabel tmpLabel3 = new JLabel("pas");

		    JTextField tmpField3 = new JTextField("0.0001"); 
		    tmpField3.setPreferredSize(new Dimension(70, 27));
		    tmpField3.setName("pas");

		    
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
		add = new JButton("Ajouter");
		add.addActionListener(this);
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

	public JList<MultipleTests> getJListResult(){
		JList<MultipleTests> j_algos = new JList<MultipleTests>(new DefaultListModel<MultipleTests>());
		j_algos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		j_algos.setCellRenderer(new ListCellRenderer<MultipleTests>() {
			
			protected DefaultListCellRenderer defaultRenderer = new DefaultListCellRenderer();
			@Override
			public Component getListCellRendererComponent(JList<? extends MultipleTests> list, MultipleTests value,
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
			if(dir_graph.getText().equals("") || dir_result.getText().equals("") || algos.isEmpty()){
				JOptionPane.showMessageDialog(null, "Tous les champs doivent être complétés", "Erreur", JOptionPane.ERROR_MESSAGE);
				return;
			}
			File dgraph = new File(dir_graph.getText());
			if(!dgraph.isDirectory()){
				JOptionPane.showMessageDialog(null, "Le dossier source n'existe pas.", "Erreur", JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			File dsource =  new File(dir_result.getText());
			
			if(!dsource.exists()){
				dsource.mkdirs();
			}
			
			File[] list_graph = dgraph.listFiles();
			File graph_result = null;
			try {
				int i = 1;
				do{
					graph_result = new File(dsource.getAbsolutePath() + File.separator + i + "_tests.csv");
					i++;
				}while(!graph_result.createNewFile());
				
			} catch (IOException e1) {
				e1.printStackTrace();
				return;
			}
			//pour tous les graphes
			for(int i = 0; i < list_graph.length; i++){
				if(!list_graph[i].getName().split("\\.")[list_graph[i].getName().split("\\.").length - 1].equals("dgs"))
					continue;
				FileHelper.writeLine(graph_result, "Graphe " + list_graph[i].getName(), true);
				FileHelper.writeLine(graph_result, "", true);
				
				//pour tous les algos
				for(int j = 0; j < algos.size(); j++){
					MultipleTests m_test = algos.get(j);
					FileHelper.writeLine(graph_result, "Algo " + m_test.getName(), true);
					FileHelper.writeLine(graph_result, "", true);
					
					Algorithm algo = m_test.getAlgo();
					try {
						algo.setGraph(CVAGraphIO.read(list_graph[i].getAbsolutePath()));
					} catch (IOException | LoadingTypeException e1) {
						e1.printStackTrace();
						return;
					}
					String title = "";
					for(String t : m_test.getTitles()){
						title += t + ",";
					}
					for(Argument n : algo.getGraph().getArguments()){
						title += n.getId() + ",";
					}

					FileHelper.writeLine(graph_result, title, true);

					System.out.println(m_test.size());
					for(int k = 0; k < m_test.size(); k++){
						List<Parameter> params = m_test.getParameters(k);
						String line = "";

						for(int l = 0; l < params.size(); l++){
							algo.addParam(params.get(l));
							line += params.get(l).printVal() + ",";
						}

						algo.execute();
						for(Argument n : algo.getGraph().getArguments()){
							line += n.getUtility() + ",";
						}
						List<Argument> args = algo.getGraph().getUtilities();
						line += args.get(0).getId(); 
						for(int l = 1; l < args.size(); l++){
							if(args.get(l).getUtility() == args.get(l-1).getUtility() )
								line += " = " + args.get(l).getId();
							else
								line += " > " + args.get(l).getId();

						}
						FileHelper.writeLine(graph_result, line, true);						
					}
					FileHelper.writeLine(graph_result, "", true);

					
				}
			}
			this.dispose();
		}
		
		if(e.getSource() == add){
			Algorithm algo = j_algos.getSelectedValue();
			JPanel parameters = (JPanel)((BorderLayout)detail_algo_panel.getLayout()).getLayoutComponent(BorderLayout.CENTER);
			
			MultipleTests list_param = new MultipleTests(algo);
			
			for(int i = 0; i < parameters.getComponentCount(); i++){
				Component[] parameter = ((JPanel)parameters.getComponent(i)).getComponents();
				String name_param = "";
				Double de = null;
				Double a = null;
				Double pas = null;
				for(int j = 0; j < parameter.length; j++){

					if(parameter[j] instanceof JLabel && 
							!((JLabel)parameter[j]).getText().equals("de") && 
							!((JLabel)parameter[j]).getText().equals("à") && 
							!((JLabel)parameter[j]).getText().equals("pas"))
					{	
						name_param = ((JLabel)parameter[j]).getText();
					}
					if(!(parameter[j] instanceof JTextField))
						continue;
					
					
					System.out.println(((JTextField)parameter[j]).getName());
					if(((JTextField)parameter[j]).getName().equals("de")){
						de = Double.parseDouble(((JTextField)parameter[j]).getText());
					}
					else if(((JTextField)parameter[j]).getName().equals("a")){
						a = Double.parseDouble(((JTextField)parameter[j]).getText());
					}
					
					else if(((JTextField)parameter[j]).getName().equals("pas")){
						pas = Double.parseDouble(((JTextField)parameter[j]).getText());
					}
				}
				System.out.println(a + " " + de + " " + pas);
				if(a < de || pas <= 0){
					JOptionPane.showMessageDialog(null, "impossible d'effectuer ces tests, vérifiez vos paramètres", "Erreur paramètres", JOptionPane.ERROR_MESSAGE);
					return;
				}
				Parameter p = new Parameter(name_param, de);
				list_param.put(name_param, p);
				System.out.println("put de " + name_param);
				for(double k = de+pas; k < a; k += pas){
					list_param.put(name_param, new Parameter(name_param, k));
					
				}
			}

		
			algos.add(list_param);
			((DefaultListModel<MultipleTests>)j_result.getModel()).addElement(list_param);
		}
	}
	
}
