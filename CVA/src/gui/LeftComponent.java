package gui;

import graph.AGraph;
import graph.Argument;
import gui.parametertype.ParameterBoolean;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import algo.Algorithm;
import algo.Parameter;

public class LeftComponent extends JPanel {
	private static final long serialVersionUID = 7302042201274878731L;
	private Algorithm algo ;
	private AGraph mygraph; 
	private JLabel algoName ;
	private JPanel parametersArea ;
	private JPanel resultArea ; 
	private HashMap<JLabel,JTextField> labelToField;
	//private JButton run ;
	private JTable detail;
	
	
	public LeftComponent()
	{
		this.algo = null ;
		this.mygraph = null;
		this.setLayout(new BorderLayout());
		

		
		JPanel nameAndParameters = new JPanel(new BorderLayout()); 
		//name 
		algoName = new JLabel ("");
		algoName.setFont(new Font(this.getFont().getName(), Font.BOLD, 18));
		algoName.setHorizontalAlignment(JLabel.CENTER);
		nameAndParameters.add(algoName,BorderLayout.NORTH);		
		
		//parameters

		this.parametersArea = new JPanel(new BorderLayout()); 
		this.parametersArea.setBorder(BorderFactory.createTitledBorder("paramètres"));
		this.parametersArea.setVisible(false);
		nameAndParameters.add(parametersArea, BorderLayout.CENTER);
		
		this.add(nameAndParameters,BorderLayout.NORTH);
		
		//resultats
		this.resultArea = new JPanel(new BorderLayout());
		resultArea.setBorder(BorderFactory.createTitledBorder("Résultats"));
		this.resultArea.setVisible(false);
		
		detail = new JTable(new ResultTableModel());
		detail.setRowHeight(27);
		detail.setShowGrid(false);
		detail.setDefaultRenderer(Double.class, new ResultTableCellRenderer());
		detail.getColumn("id").setCellRenderer(new ResultTableCellRenderer());
		
		JScrollPane scroll_detail = new JScrollPane(detail);
		resultArea.add(scroll_detail, BorderLayout.CENTER);
		
		
		this.add(resultArea, BorderLayout.CENTER);
		
	}
	
	// La fonction qui permet de changer l'algorithme courant mais aussi d'en ajouter un au d�part
	public void switchAlgo (Algorithm al)
	{
		this.algo = al;
		if(mygraph != null){
			this.algo.setGraph(mygraph);
		}
		MajInterface();
	}
	
	public void switchGraph (AGraph gr)
	{
		this.mygraph = gr;
		if(this.algo != null){
			this.algo.setGraph(mygraph);
		}
		MajInterface();
	}
	
	public boolean canRun ()
	{
		return this.algo!=null && this.mygraph!=null ; 
	}
	
	@SuppressWarnings("unused")
	public void run ()
	{
		if (canRun())
		{
			MajParametersValues();
			HashMap<String,Parameter> params = algo.getParams(); 
			algo.execute();
		}
		else
		{
			if (this.algo ==null)
				JOptionPane.showMessageDialog(null, "Aucun algorithme n'a été chargé", "missing algorithm", JOptionPane.ERROR_MESSAGE);

			if (this.mygraph==null)
				JOptionPane.showMessageDialog(null, "Aucun graphe n'a été chargé", "missing graph", JOptionPane.ERROR_MESSAGE);
		}
		MajInterface();
	}
	
	// Une fonction qui remet les composants du LeftComponent � jour
	// pour qu'ils correspondent � l'algo courant 
	// Elle combine la fonction qui mets les r�sultats � jour avec celle qui mets les param�tres � jour
	public void MajInterface ()
	{
		MajName(); 
		MajParametres();
		MajResultats();
	}
	
	private void MajName() 
	{
		if(this.algo != null)
			algoName.setText(algo.getName());
	}
	
	private void MajParametersValues(){
		for(JLabel label : labelToField.keySet()){
			try{
				Parameter currentParameter = algo.getParam(label.getText());
				currentParameter.setValue(Double.parseDouble(labelToField.get(label).getText()));
				System.out.println( label.getText()+" = "+Double.parseDouble(labelToField.get(label).getText()));
			}
			catch(NumberFormatException e){
				labelToField.get(label).setText(algo.getParam(label.getText()).printVal());
			}
		}
	}
	public void MajParametres () {
		

		
		if(algo == null) return;
		
		labelToField = new HashMap<JLabel, JTextField>();
		Dimension parentDim = this.getSize(); 
		parametersArea.setPreferredSize(new Dimension(parentDim.width,parentDim.height/4));
		parametersArea.setVisible(true);
		try{
			parametersArea.remove(((BorderLayout)parametersArea.getLayout()).getLayoutComponent(BorderLayout.CENTER));
		}
		catch(NullPointerException e ){}
		HashMap<String,Parameter> params = algo.getParams();
		JPanel parameters = new JPanel(new GridLayout(0, 1));
		int param = 0;
		for (Entry<String, Parameter> entry : params.entrySet()) {
			
			JPanel parameter = new JPanel();
		    String list = entry.getKey();
		    JLabel tmpLabel = new JLabel(list); 
		    JTextField tmpField = new JTextField(entry.getValue().printVal()); 
		    tmpField.setPreferredSize(new Dimension(100, 27));
		    labelToField.put(tmpLabel, tmpField);
		    parameter.add(tmpLabel);
			parameter.add(tmpField);
			//newArea.add(tmpBox) ;
			parameters.add(parameter);
			param++;

		}
		JScrollPane scroll_params = new JScrollPane(parameters);
		scroll_params.setBorder(null);
		parametersArea.add(scroll_params, BorderLayout.CENTER);
		//parametersArea.setPreferredSize(new Dimension(param * 40 + 30, param * 40 + 50));
		parametersArea.validate();
		this.validate();
	}
	
	public void MajResultats () 
	{
		if(mygraph == null || algo == null){
			return;
		}
		resultArea.setVisible(true);
		try{
			//resultArea.remove(((BorderLayout)resultArea.getLayout()).getLayoutComponent(BorderLayout.CENTER));
			resultArea.remove(((BorderLayout)resultArea.getLayout()).getLayoutComponent(BorderLayout.SOUTH));

		}
		catch(NullPointerException e){}
		
		List<Argument> args = mygraph.getUtilities();
		
		
		((ResultTableModel)detail.getModel()).setUtilities(args);
		
		/*JPanel detail = new JPanel();
		 * detail.setLayout(new BoxLayout(detail, BoxLayout.Y_AXIS));
		
		for(int i = 0; i < args.size(); i++){
			JPanel arg = new JPanel();
			arg.setPreferredSize(new Dimension(300, 40));
			JTextField node_name = new JTextField(args.get(i).getId());
			node_name.setEditable(false);
			node_name.setBackground(new Color(255, 255, 255));
			node_name.setPreferredSize(new Dimension(120, 27));
			arg.add(node_name);

			JTextField node_utility = new JTextField("" + args.get(i).getUtility());
			node_utility.setEditable(false);
			node_utility.setBackground(new Color(255, 255, 255));
			node_utility.setPreferredSize(new Dimension(120, 27));
			arg.add(node_utility);
			
			detail.add(arg);
		}
		*/
		
		
		
		JPanel result = new JPanel();
		JTextArea text_result = new JTextArea(2, 20); 
		for(int i = 0; i < args.size(); i++){
			if(i == 0){
				text_result.setText(args.get(i).getId());
				continue;
			}
			
			if(args.get(i).getUtility() == args.get(i-1).getUtility() )
				text_result.setText(text_result.getText() + " = " + args.get(i).getId());
			else
				text_result.setText(text_result.getText() + " > " + args.get(i).getId());

		}
		text_result.setEditable(false);
		text_result.setBackground(new Color(255, 255, 255));

		result.add(new JScrollPane(text_result));
		resultArea.add(result, BorderLayout.SOUTH);
		
		resultArea.validate();
	}
	
	@SuppressWarnings("unused")
	private HashMap<String,Parameter> getParams(){
		HashMap<String,Parameter> params = new HashMap<String,Parameter>();
		
		return params;
	}
	
	public boolean isGraph(){
		return mygraph == null ? false : true;
	}
	
	public boolean isAlgo(){
		return algo == null ? false : true;
	}
	
	@Override
	public Dimension getPreferredSize(){
		Dimension parent_dim = this.getParent().getSize();
		Dimension my_dim = new Dimension((int)parent_dim.getWidth()/3, (int) parent_dim.getHeight());
		return my_dim;
	}

}
