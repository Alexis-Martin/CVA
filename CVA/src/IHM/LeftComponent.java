package IHM;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import CVAGraph.AGraph;
import CVAGraph.Argument;
import IHMGraph.GSGraphicGraph;
import IHMGraph.IGraphicGraph;
import algo.Algorithm;
import algo.Categoriser;
import algo.Parameter;

public class LeftComponent extends JPanel {
	
	ArrayList<String> results ;
	Algorithm algo ;
	IGraphicGraph igg;
	AGraph mygraph ; 
	JPanel parametersArea ;
	JTextArea resultArea ; 
	JButton run ;
	
	
	public LeftComponent()
	{
		this.algo = null ; 
		this.results = new ArrayList<String>(); 
		this.parametersArea = new JPanel(); 
		this.resultArea = new JTextArea() ; 
		this.setLayout(new BoxLayout(this,BoxLayout.PAGE_AXIS));
		Box layoutPrincipal = Box.createVerticalBox();

		
		
		
		////////// PROPERTIES BOX ///////////
		Box propertiesBox = Box.createVerticalBox();
		JLabel prop = new JLabel ("Propriétés");
		propertiesBox.add(prop);
		parametersArea.add(new JLabel("No Parameters"));
		propertiesBox.add(parametersArea); 
		/*E.setPreferredSize(new Dimension(100,20));
		propertiesBox.add(E); 
		JTextField eText = new JTextField(); 
		propertiesBox.add(eText);  */ 
		
		/////// RESULTS BOX //////
		Box resultBox = Box.createVerticalBox(); 
		JLabel resu = new JLabel ("Resultats") ;
		resultBox.add(resu);
		resultBox.add(resultArea);
		
		/*
		JPanel Informations = new JPanel() ;
		JLabel infos = new JLabel("Informations sur l'algorithme"); 
		Informations.add(infos); 
		Informations.setPreferredSize(new Dimension(200,70));
			layoutPrincipal.add(prop); 
		*/
	
		layoutPrincipal.add(propertiesBox); 
		layoutPrincipal.add(resultBox); 
		
		this.add(layoutPrincipal);
		
		
		run = new JButton("Run");
		layoutPrincipal.add(run);
		run.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				run();
				}
			}
		);
		
	}
	
	// La fonction qui permet de changer l'algorithme courant mais aussi d'en ajouter un au départ
	public void switchAlgo (Algorithm al)
	{
		this.algo = al ; 
	}
	
	public void switchGraph (AGraph gr)
	{
		this.mygraph = gr ; 
		igg =  new GSGraphicGraph(mygraph);
	}
	
	public boolean canRun ()
	{
		return this.algo!=null && this.mygraph!=null ; 
	}
	
	public void run ()
	{
		if (canRun())
		{
			algo.execute();
			igg.refresh();
			List<Argument> list = mygraph.getUtilities();
			if(list.size() != 0)
				System.out.print("(" + list.get(0).getId() +", " + list.get(0).getUtility() + ")");
			for(int i = 1; i < list.size(); i++){
				if(list.get(i).getUtility() < list.get(i-1).getUtility())
					System.out.print(" > ");
				else
					System.out.print(" = ");
				
				System.out.print("(" + list.get(i).getId() +", " + list.get(i).getUtility() + ")");
		}}
		else
		{
			System.out.print("can't run");
			results.add("Can't run");
			if (this.algo ==null)
				results.add("There is no loaded algorithm");
			System.out.print("There is no loaded algorithm");
			if (this.mygraph==null)
				results.add("There is no loaded graph");
			System.out.print("There is no loaded graph");
		}
		MajInterface() ;
	}
	
	// Une fonction qui remet les composants du LeftComponent à jour
	// pour qu'ils correspondent à l'algo courant 
	// Elle combine la fonction qui mets les résultats à jour avec celle qui mets les paramètres à jour
	public void MajInterface ()
	{
		MajParametres();
		
	}
	
	public void MajParametres () 
	{
		//JPanel newArea = new JPanel() ;
		HashMap<String,Parameter> params = algo.getParams() ; 
		int i ; 
		ArrayList<Box> boxList = new ArrayList<Box>() ;
		ArrayList<JLabel> labelList = new ArrayList<JLabel>() ;
		ArrayList<JTextField> fieldList = new ArrayList<JTextField>() ;
		Set<String> keys = params.keySet();
		parametersArea.removeAll();
		
		for (Entry<String, Parameter> entry : params.entrySet()) {
		    String list = entry.getKey();
		    JLabel tmpLabel = new JLabel(list); 
		    JTextField tmpField = new JTextField(Double.toString(entry.getValue().getValue())); 
			Box tmpBox = Box.createHorizontalBox(); 
			tmpBox.add(tmpLabel);
			tmpBox.add(tmpField);
			//newArea.add(tmpBox) ;
			parametersArea.add(tmpBox) ;

		}
		parametersArea.revalidate();
		
	/*	for (i=0;i<params.size();i++)
		{
			JLabel tmpLabel = new JLabel(keyList.get(i)); 
			JTextField tmpField = new JTextField(Double.toString(params.get(keyList.get(i)).getValue()));
			Box tmpBox = Box.createHorizontalBox(); 
			tmpBox.add(tmpLabel);
			tmpBox.add(tmpField);
			parametersArea.add(tmpBox);
		}*/
		
	}
	
	public void MajResultats () 
	{
		
	}
	
	

}
