package algo;

import CVAGraph.AGraph;
import CVAGraph.Argument;

public class Categoriser extends AbstractAlgorithm {

	double epsilon;
	
	public Categoriser(AGraph graph, String name){
		this(graph, name, 0);	
	} 
	
	public Categoriser(AGraph graph, String name, double epsilon){
		super(graph, name);
		this.epsilon = epsilon; 
	}
	
	@Override
	public void init() {
		if(epsilon == 0 || epsilon > 1)
			epsilon = 0.0001; 
		
		AGraph graph = super.getGraph();
		for(Argument a : graph.getArguments()){
			a.setUtility(1);
		}
	}

	@Override
	public void run() {
		algo();
		
	}
	
	public void end(){
		AGraph graph = super.getGraph();
		
		String chiffre = "" + epsilon;
		int produit = (int) Math.pow(10, chiffre.split("\\.")[1].length());
		for(Argument a : graph.getArguments()){
			a.setUtility((double)((int)(a.getUtility()*produit))/produit);
		}
		
	}
	
	private void algo(){
		boolean finish = true;
		AGraph graph = super.getGraph();
		for(Argument a : graph.getArguments()){
			double att = 1;
			 
			for(Argument arg : a.getAttackers()){
			
				att += arg.getUtility();
			}
			
			double utility = 1 / att;
				
			if(a.getUtility() > utility + epsilon || a.getUtility() < utility - epsilon){
				finish = false;
			}
			a.setUtility(utility);
		}
		
		if(!finish)
			algo();
	}
	
	
}
