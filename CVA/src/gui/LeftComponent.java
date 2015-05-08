package gui;

import graph.AGraph;
import graph.Argument;

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

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import algo.Algorithm;
import algo.Parameter;

public class LeftComponent extends JPanel {
	private static final long serialVersionUID = 7302042201274878731L;
	private Algorithm algo ;
	private AGraph mygraph = null; 
	private JPanel parametersArea ;
	private JPanel resultArea ; 
	//private JButton run ;
	
	
	public LeftComponent()
	{
		this.algo = null ; 
		this.setLayout(new BorderLayout());
		
		//parameters
		this.parametersArea = new JPanel(new BorderLayout()); 
		JLabel prop = new JLabel ("Paramètres");
		prop.setFont(new Font(this.getFont().getName(), Font.BOLD, 16));
		prop.setHorizontalAlignment(JLabel.CENTER);
		parametersArea.add(prop, BorderLayout.NORTH);
		this.parametersArea.setVisible(false);
		this.add(parametersArea, BorderLayout.NORTH);
		
		//resultats
		this.resultArea = new JPanel(new BorderLayout());
		JLabel result = new JLabel("Résultats");
		result.setFont(new Font(this.getFont().getName(), Font.BOLD, 16));
		result.setHorizontalAlignment(JLabel.CENTER);
		resultArea.add(result, BorderLayout.NORTH);
		this.resultArea.setVisible(false);
		this.add(resultArea, BorderLayout.CENTER);
		
	}
	
	// La fonction qui permet de changer l'algorithme courant mais aussi d'en ajouter un au d�part
	public void switchAlgo (Algorithm al)
	{
		this.algo = al;
		MajInterface();
	}
	
	public void switchGraph (AGraph gr)
	{
		this.mygraph = gr;
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
			HashMap<String,Parameter> params = algo.getParams(); 
			//TODO: setParams
			algo.execute(this.mygraph);
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
		MajParametres();
		MajResultats();
	}
	
	public void MajParametres () {
		if(algo == null) return;
		
		parametersArea.setVisible(true);
		try{
			parametersArea.remove(((BorderLayout)parametersArea.getLayout()).getLayoutComponent(BorderLayout.CENTER));
		}
		catch(NullPointerException e ){}
		HashMap<String,Parameter> params = algo.getParams();
		JPanel parameters = new JPanel(new GridLayout(2, 0, 10, 5));
		int param = 0;
		for (Entry<String, Parameter> entry : params.entrySet()) {
			JPanel parameter = new JPanel();
		    String list = entry.getKey();
		    JLabel tmpLabel = new JLabel(list); 
		    JTextField tmpField = new JTextField(entry.getValue().printVal()); 
		    tmpField.setPreferredSize(new Dimension(100, 27));

		    parameter.add(tmpLabel);
			parameter.add(tmpField);
			//newArea.add(tmpBox) ;
			parameters.add(parameter);
			param++;

		}
		parametersArea.add(parameters, BorderLayout.CENTER);
		parametersArea.setPreferredSize(new Dimension(param * 50 + 30, param * 80 + 30));
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
			resultArea.remove(((BorderLayout)resultArea.getLayout()).getLayoutComponent(BorderLayout.CENTER));
		}
		catch(NullPointerException e){}
		
		List<Argument> args = mygraph.getUtilities();
		JPanel detail = new JPanel();
		detail.setLayout(new BoxLayout(detail, BoxLayout.Y_AXIS));
		
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
		
		JScrollPane scroll_detail = new JScrollPane(detail);
		resultArea.add(scroll_detail, BorderLayout.CENTER);
		
		
		JPanel result = new JPanel();
		JTextField text_result = new JTextField(); 
		
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
		result.add(text_result);
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
	
	public Dimension getPreferredSize(){
		Dimension parent_dim = this.getParent().getSize();
		Dimension my_dim = new Dimension((int)parent_dim.getWidth()/3, (int) parent_dim.getHeight());
		return my_dim;
	}

}
