package algo.implem;

import algo.AbstractAlgorithm;
import algo.Parameter;
import CVAGraph.AGraph;
import CVAGraph.Argument;

public class Categoriser extends AbstractAlgorithm {	
	
	private final String epsilon = "epsilon";
	
	public Categoriser(){
		super("Categoriser");
		addParam(new Parameter(epsilon, 0.0001));
	}
	
	@Override
	public void init() {
		Parameter eps = getParam(epsilon);
		if((double) eps.getValue() == 0 || (double) eps.getValue() > 1)
			eps.setValue(0.0001); 
		
		AGraph graph = super.getGraph();
		for(Argument a : graph.getArguments()){
			a.setUtility(1);
		}
	}

	@Override
	public void run() {
		System.out.println("epsilon : "+this.getParam(epsilon).printVal());
		algo();
		
	}
	
	@Override
	public void end(){
		AGraph graph = super.getGraph();
		Parameter eps = getParam(epsilon);
		
		String chiffre = "" + eps;
		int produit = (int) Math.pow(10, chiffre.split("\\.")[1].length());
		for(Argument a : graph.getArguments()){
			a.setUtility((double)((int)(a.getUtility()*produit))/produit);
		}
		
	}
	
	private void algo(){
		double eps = (double) getParam(epsilon).getValue();
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
