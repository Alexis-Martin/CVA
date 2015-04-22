package algo;

import CVAGraph.AGraph;
import CVAGraph.Argument;

public class Categoriser extends AbstractAlgorithm {	
	
	private final String epsilon = "epsilon";
	
	public Categoriser(AGraph graph, String name){
		this(graph, name, 0);	
	} 
	
	public Categoriser(AGraph graph, String name, double epsilon){
		super(graph, name);
		addParam(new Parameter("epsilon", epsilon));
	}
	
	@Override
	public void init() {
		Parameter eps = getParam(epsilon);
		if(eps.getValue() == 0 || eps.getValue() > 1)
			eps.setValue(0.0001); 
		
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
		double eps = getParam(epsilon).getValue();
		boolean finish = true;
		AGraph graph = super.getGraph();
		for(Argument a : graph.getArguments()){
			double att = 1;
			 
			for(Argument arg : a.getAttackers()){
			
				att += arg.getUtility();
			}
			
			double utility = 1 / att;
				
			if(a.getUtility() > utility + eps || a.getUtility() < utility - eps){
				finish = false;
			}
			a.setUtility(utility);
		}
		
		if(!finish)
			algo();
	}
	
	
}
