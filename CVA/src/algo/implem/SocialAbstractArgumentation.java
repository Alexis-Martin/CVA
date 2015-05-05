package algo.implem;

import java.util.ArrayList;
import java.util.HashMap;

import algo.AbstractAlgorithm;
import CVAGraph.AGraph;
import CVAGraph.Argument;

public class SocialAbstractArgumentation extends AbstractAlgorithm {
	double epsilon = 0.0;
	double xhi = 0.1;
	int currentArg, totalArg;
	
	public SocialAbstractArgumentation(){
		super("Social Abstract Argumentation");
	}
	
	@Override
	public void init() {
		
		if(epsilon == 0.0 || epsilon > 1)
			epsilon = 0.0001; 
		
		AGraph graph = super.getGraph();
		for(Argument a : graph.getArguments()){
			a.setUtility(0.0);
		}
	}

	@Override
	public void run() {
		this.algo();

	}
	
	//In Case Of acyclic graph we have to obtain the rigth order of th graph
	
	private void algo(){
		boolean finish = true;
		AGraph graph = super.getGraph();

		for(Argument a : graph.getArguments()){
			double result = 1.0;
			ArrayList<Argument>  args = new ArrayList<Argument>();
			
			args.addAll(a.getAttackers());
			if(!args.isEmpty())
				result = args.get(0).getUtility();
			for(int i=1; i<args.size();i++){
				result = result+args.get(i).getUtility()-result*args.get(i).getUtility();
			}
			double utility = (1.0/(1.0+this.xhi))*(1-result);
				
			if(a.getUtility() > utility + epsilon || a.getUtility() < utility - epsilon){
				finish = false;
			}
			a.setUtility(utility);
		}
		if(!finish)
			algo();
	}

	@Override
	public void end() {
		AGraph graph = super.getGraph();
		
		String chiffre = "" + epsilon;
		int produit = (int) Math.pow(10, chiffre.split("\\.")[1].length());
		for(Argument a : graph.getArguments()){
			a.setUtility((double)((int)(a.getUtility()*produit))/produit);
		}

	}

}
