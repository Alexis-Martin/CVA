package gui;

import graph.AGraph;
import graph.Argument;
import gui.parametertype.ParameterBoolean;
import gui.parametertype.ParameterDouble;
import gui.parametertype.ParameterInteger;
import gui.parametertype.ParameterString;
import gui.parametertype.ParameterType;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

import algo.Algorithm;
import algo.Parameter;

public class LeftComponent extends JPanel {
	private static final long serialVersionUID = 7302042201274878731L;
	private Algorithm algo ;
	private AGraph mygraph; 
	private JLabel algoName ;
	private JPanel parametersArea ;
	private List<ParameterType> parameters_type;
	private JPanel resultArea ; 
	private JTable detail;
	private JFrame frame;
	private WaitExecution wait;
	
	
	public LeftComponent(JFrame frame)
	{	
		this.frame = frame;
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
		
		parameters_type = new ArrayList<ParameterType>();

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
			if(JOptionPane.showConfirmDialog(frame, "Voulez-vous changer les poids pour remettre les valeurs par défaut?", 
											"Remise à zeros des poids", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null) 
											== JOptionPane.YES_OPTION)
			{
				setDefaultWeight();	
			}
			
		}
		MajInterface();
	}
	
	public void switchGraph (AGraph gr)
	{
		this.mygraph = gr;
		if(this.algo != null){
			this.algo.setGraph(mygraph);
			setDefaultWeight();
		}
		MajInterface();
	}
	
	private void setDefaultWeight(){
		for(Argument arg : mygraph.getArguments()){
			arg.setWeight(algo.getDefaultInitUtility());
		}
	}
	
	public boolean canRun ()
	{
		return this.algo!=null && this.mygraph!=null ; 
	}
	

	public void run ()
	{
		if (canRun())
		{
		    
			
			wait = new WaitExecution(frame);
			
			Thread t = new Thread(new Runnable() {
				public void run() {
					
					
					
			        SwingUtilities.invokeLater(new Runnable() {
						 
						@Override
						public void run() {
							if(algo.getNbIteration() != 0){
								wait.setLimit(algo.getNbIteration());
							}
							wait.setVisible(true); // on affiche le dialogue (comme il est modale, ça bloque le thread courant)
						}
					});
			        
					while(!wait.isVisible()){
						try {
							Thread.sleep(200);
						} catch (InterruptedException e) { 
						}
					}
					
					Thread t = new Thread(new Runnable() {
						
						@Override
						public void run() {
							algo.execute();
						}
					});
					t.start();
					
					while(t.isAlive()){
						 try {
							SwingUtilities.invokeAndWait(new Runnable() {
								 
									@Override
									public void run() {
										if(algo.getNbIteration() != 0){
											
											wait.setProgress(algo.getCurrentIteration());
										}
									}
							 });
						} catch (InvocationTargetException
								| InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					System.out.println("Le calcul est terminé, on refresh");
					SwingUtilities.invokeLater(new Runnable() {
						 
						@Override
						public void run() {
							MajInterface();
							System.out.println("fin du refresh");
							wait.dispose();
						}
					});					
					
				}
			});

			t.start();
			
			
						
		}
		else
		{
			if (this.algo ==null)
				JOptionPane.showMessageDialog(frame, "Aucun algorithme n'a été chargé", "missing algorithm", JOptionPane.ERROR_MESSAGE);

			if (this.mygraph==null)
				JOptionPane.showMessageDialog(frame, "Aucun graphe n'a été chargé", "missing graph", JOptionPane.ERROR_MESSAGE);
		}
		
	}
	
	// Une fonction qui remet les composants du LeftComponent � jour
	// pour qu'ils correspondent � l'algo courant 
	// Elle combine la fonction qui mets les r�sultats � jour avec celle qui mets les param�tres � jour
	public void MajInterface ()
	{
		MajName(); 
		System.out.println("maj param");
		MajParametres();
		System.out.println("majres");
		MajResultats();
		System.out.println("fin maj");
	}
	
	private void MajName() 
	{
		if(this.algo != null)
			algoName.setText(algo.getName());
	}
	

	public void MajParametres () {
		

		
		if(algo == null) return;
		
		Dimension parentDim = this.getSize(); 
		parametersArea.setPreferredSize(new Dimension(parentDim.width,parentDim.height/4));
		parametersArea.setVisible(true);
		try{
			parametersArea.remove(((BorderLayout)parametersArea.getLayout()).getLayoutComponent(BorderLayout.CENTER));
			parameters_type.clear();
		}
		catch(NullPointerException e ){}
		
		
		HashMap<String,Parameter> params = algo.getParams();
		JPanel parameters = new JPanel(new GridLayout(0, 1));
		
		for (Entry<String, Parameter> entry : params.entrySet()) {
			
			ParameterType param = null;
			
			if(entry.getValue().getValue() instanceof Double){
				param = new ParameterDouble(entry.getValue());
			}
			else if(entry.getValue().getValue() instanceof Integer){
				System.out.println(entry.getValue().getName());
				param = new ParameterInteger(entry.getValue());
			}
			else if(entry.getValue().getValue() instanceof Boolean){
				param = new ParameterBoolean(entry.getValue());
			}
			else{
				param = new ParameterString(entry.getValue());
			}
			
			parameters.add(param);
			parameters_type.add(param);

		}
		JScrollPane scroll_params = new JScrollPane(parameters);
		scroll_params.setBorder(null);
		parametersArea.add(scroll_params, BorderLayout.CENTER);
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
			resultArea.remove(((BorderLayout)resultArea.getLayout()).getLayoutComponent(BorderLayout.SOUTH));

		}
		catch(NullPointerException e){}
		
		List<Argument> args = mygraph.getUtilities();
		
		System.out.println("here 305");
		((ResultTableModel)detail.getModel()).setUtilities(args);
		System.out.println("here 307");
		JTextArea text_result = new JTextArea(2, 20); 
		for(int i = 0; i < args.size(); i++){
			System.out.println(i);
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
		System.out.println("here 323");
		JScrollPane scroll_result = new JScrollPane(text_result);
		scroll_result.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5), scroll_result.getBorder()));
		resultArea.add(scroll_result, BorderLayout.SOUTH);
		System.out.println("here327");
		resultArea.validate();
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
