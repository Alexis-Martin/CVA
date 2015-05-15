package gui;

import graph.AGraph;
import graph.Argument;
import graph.adapter.AGraphAdapter;
import gui.graphui.GSGraphicGraph;
import helper.CsvFileWriter;
import helper.FileHelper;
import io.CVAGraphIO;
import io.Loader;
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
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
	private JPanel detail_algo_panel, parameters;
	private JList<Algorithm> j_algos;
	private JList<MultipleTests> j_result;
	private JTextField dir_result;
	
	public FrameTests(Dialog owner, String title, boolean modal){
		super(owner, title, modal);
		this.setSize(1000, 400);
		this.setMinimumSize(new Dimension(1000, 400));
		
		algos = new Vector<MultipleTests>();
		
		JPanel frame_panel = new JPanel(new BorderLayout());
		
		JPanel top_panel = new JPanel();
		JLabel name = new JLabel("Batterie de tests");
		name.setFont(new Font(name.getFont().getName(), Font.BOLD, 16));
		
		top_panel.add(name);
		frame_panel.add(top_panel, BorderLayout.NORTH);
		
		JPanel center_panel = new JPanel(new BorderLayout());
		
		JPanel dir_panel = new JPanel(new GridLayout(1, 2));
		JPanel dir_graph_panel = new JPanel();
		dir_graph_panel.setBorder(BorderFactory.createTitledBorder("Dossier de graphes"));
		
		dir_graph = new JTextField();
		dir_graph.setPreferredSize(new Dimension(300, 27));
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
		dir_result.setPreferredSize(new Dimension(300, 27));
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
		parameters = new JPanel(new GridLayout(0, 7));
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
			parameters.removeAll();
		}
		catch(NullPointerException e ){}
		HashMap<String,Parameter> params = alg.getParams();

		for (Entry<String, Parameter> entry : params.entrySet()) {
		    String list = entry.getKey();
		    
		    JPanel panel_list = new JPanel();
		    JLabel label_list = new JLabel(list);
		    panel_list.add(label_list);
		    
		    JPanel tmppanel = new JPanel();
		    JLabel tmpLabel = new JLabel("de"); 
		    tmppanel.add(tmpLabel);
		    
		    JPanel tmppanelf = new JPanel();
		    JTextField tmpField = new JTextField(entry.getValue().printVal()); 
		    tmpField.setPreferredSize(new Dimension(70, 27));
		    tmpField.setName("de");
		    tmppanelf.add(tmpField);
		    
		    
		    JPanel tmppanel2 = new JPanel();
		    JLabel tmpLabel2 = new JLabel("à");
		    tmppanel2.add(tmpLabel2);
		    
		    JPanel tmppanelf2 = new JPanel();
		    JTextField tmpField2 = new JTextField(entry.getValue().printVal()); 
		    tmpField2.setPreferredSize(new Dimension(70, 27));
		    tmpField2.setName("a");
		    tmppanelf2.add(tmpField2);
		    
		    JPanel tmppanel3 = new JPanel();
		    JLabel tmpLabel3 = new JLabel("pas");
		    tmppanel3.add(tmpLabel3);
		    
		    
		    JPanel tmppanelf3= new JPanel();
		    JTextField tmpField3 = new JTextField("0.0001"); 
		    tmpField3.setPreferredSize(new Dimension(70, 27));
		    tmpField3.setName("pas");
		    tmppanelf3.add(tmpField3);
		    
		    
		    parameters.add(panel_list);
		    parameters.add(tmppanel);
			parameters.add(tmppanelf);
			parameters.add(tmppanel2);
			parameters.add(tmppanelf2);
			parameters.add(tmppanel3);
			parameters.add(tmppanelf3);
			

		}
		parameters.setPreferredSize(new Dimension(400, 100));
		detail_algo_panel.add(new JScrollPane(parameters), BorderLayout.CENTER);
		
		JPanel button_panel = new JPanel();
		add = new JButton("Ajouter");
		add.addActionListener(this);
		button_panel.add(add);
		detail_algo_panel.add(button_panel, BorderLayout.SOUTH);
		detail_algo_panel.validate();
		this.validate();
	}
	
	
	public JList<Algorithm> getJListAlgos(Vector<Algorithm> algos){
		JList<Algorithm> j_algos = new JList<Algorithm>(algos);
		j_algos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		j_algos.setPreferredSize(new Dimension(250, 10));
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
		j_algos.setPreferredSize(new Dimension(250, 10));
		
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
			Calendar calendar = Calendar.getInstance();
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-YY");
			File[] list_graph = dgraph.listFiles();
			File graph_result = null;
			CsvFileWriter csvWriter = null;
			
			//pour tous les algos
			for(int j = 0; j < algos.size(); j++){
				List<Map<String, String>> toWrite = new ArrayList<Map<String, String>>();
				MultipleTests m_test = algos.get(j);
				String[] titles = new String[6+m_test.getTitles().size()];
				Algorithm algo = m_test.getAlgo();
				
				titles[0] = "Graphe";
				titles[1] = "Nombre d'arguments";
				titles[2] = "Nombre de relations";
				int ti = 3;
				for(String t : m_test.getTitles()){
					titles[ti] = t;
					ti++;
				}
				titles[ti] = "Temps (ms)";
				titles[ti+1] = "Temps (h:m:s.ms)";
				titles[ti+2] = "Résultats";
				
				try {
					int i = 1;
					do{
						graph_result = new File(dsource.getAbsolutePath() + File.separator + i 
								+ "_" + sdf.format(calendar.getTime()) +"_" +m_test.getName() + ".csv");
						i++;
					}while(!graph_result.createNewFile());
					
				} catch (IOException e1) {
					e1.printStackTrace();
					return;
				}
				csvWriter = new CsvFileWriter(graph_result);
							
			//pour tous les graphes
			for(int i = 0; i < list_graph.length; i++){
				//if(!list_graph[i].getName().split("\\.")[list_graph[i].getName().split("\\.").length - 1].equals("dgs"))
				//	continue;
				AGraph g = null;
				try {
					g = Loader.load(list_graph[i].getAbsolutePath());
				} catch (IOException e2) {
					e2.printStackTrace();
				} catch (LoadingTypeException e2) {
					e2.printStackTrace();
				}
				if(g == null)
					continue;
				
				algo.setGraph(g);
				

					for(int k = 0; k < m_test.size(); k++){
						List<Parameter> params = m_test.getParameters(k);
						Map<String, String> resData = new HashMap<String, String>();
						List<Argument> args = algo.getGraph().getUtilities();
						
						resData.put("Graphe", list_graph[i].getName());
						resData.put("Nombre d'arguments", args.size()+"");
						resData.put("Nombre de relations", algo.getGraph().getRelations().size() +"");
						for(int l = 0; l < params.size(); l++){
							algo.getParam(params.get(l).getName()).setValue(params.get(l).getValue());
							resData.put(params.get(l).getName(), params.get(l).printVal());
						}

						long start = System.currentTimeMillis();
						algo.execute();
						long stop = System.currentTimeMillis();
						
						resData.put("Temps (ms)", (stop - start) +"");
						resData.put("Temps (h:m:s.ms)", convertTime(stop - start));
						String res = args.get(0).getId(); 
						for(int l = 1; l < Math.min(args.size(), 10); l++){
							if(args.get(l).getUtility() == args.get(l-1).getUtility() )
								res += " = " + args.get(l).getId();
							else
								res += " > " + args.get(l).getId();

						}
						resData.put("Résultats", res);	
						toWrite.add(resData);
					}
				}
				try {
					csvWriter.write(toWrite, titles);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			this.dispose();
		}
		
		if(e.getSource() == add){
			Algorithm algo = j_algos.getSelectedValue();
			
			MultipleTests list_param = new MultipleTests(algo);
			
			for(int i = 0; i < parameters.getComponentCount()/7; i++){
				String name_param = "";
				Number de = null;
				Number a = null;
				Number pas = null;
				for(int j = 0; j < 7; j++){
					Component parameter = ((JPanel)parameters.getComponent(i*7 + j)).getComponent(0);
					if(parameter instanceof JLabel && 
							!((JLabel)parameter).getText().equals("de") && 
							!((JLabel)parameter).getText().equals("à") && 
							!((JLabel)parameter).getText().equals("pas"))
					{	
						name_param = ((JLabel)parameter).getText();
					}
					if(!(parameter instanceof JTextField))
						continue;
					
					
					System.out.println(((JTextField)parameter).getName());
					if(((JTextField)parameter).getName().equals("de")){
						de = Double.parseDouble(((JTextField)parameter).getText());
					}
					else if(((JTextField)parameter).getName().equals("a")){
						a = Double.parseDouble(((JTextField)parameter).getText());
					}
					
					else if(((JTextField)parameter).getName().equals("pas")){
						pas = Double.parseDouble(((JTextField)parameter).getText());
					}
				}
				
				System.out.println(a + " " + de + " " + pas);
				
				if(a.doubleValue() < de.doubleValue() || pas.doubleValue() <= 0){
					JOptionPane.showMessageDialog(null, "impossible d'effectuer ces tests, vérifiez vos paramètres", "Erreur paramètres", JOptionPane.ERROR_MESSAGE);
					return;
				}
				if(algo.getParam(name_param).getValue() instanceof Double){
					Parameter p = new Parameter(name_param, de.doubleValue());
					list_param.put(name_param, p);
					for(double k = de.doubleValue()+pas.doubleValue(); k < a.doubleValue(); k += pas.doubleValue()){
						list_param.put(name_param, new Parameter(name_param, k));
					}
				}
				if(algo.getParam(name_param).getValue() instanceof Integer){
					System.out.println("integer " + name_param);
					Parameter p = new Parameter(name_param, de.intValue());
					list_param.put(name_param, p);
					for(int k = de.intValue()+pas.intValue(); k < a.intValue(); k += pas.intValue()){
						list_param.put(name_param, new Parameter(name_param, k));
					}
				}
			}

		
			algos.add(list_param);
			((DefaultListModel<MultipleTests>)j_result.getModel()).addElement(list_param);
		}
	}
	
	public String convertTime(long ms){
		long millisecondes=ms%1000; 
		ms=ms/1000; 
		long secondes=ms%60; 
		 ms=ms/60; 
		long minutes=ms%60; 
		 ms=ms/60; 
		long heures=ms;
 
		return heures+":"+minutes+":"+secondes+"."+millisecondes;
	}
	
}
