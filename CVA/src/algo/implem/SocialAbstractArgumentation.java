package algo.implem;

import graph.AGraph;
import graph.Argument;

import java.util.ArrayList;
import java.util.HashMap;

import algo.AbstractAlgorithm;

public class SocialAbstractArgumentation extends AbstractAlgorithm {
	double epsilon = 0.0;
	double xhi = 0.1;
	int currentArg, totalArg;
	
	public SocialAbstractArgumentation(){
		super("Social Abstract Argumentation");
	}
	
	@Override
	public void init() {
		HashMap<String, Double> s = new HashMap<String, Double>();
		
		if(epsilon == 0.0 || epsilon > 1)
			epsilon = 0.0001; 
		
		super.clearSteps();
		for(Argument a : super.getGraph().getArguments()){
			s.put(a.getId(), 0.0);
		}
		super.addStep(s);
	}

	@Override
	public void run() {
		this.algo();
	}
	
	//In Case Of acyclic graph we have to obtain the rigth order of th graph
	
	private void algo(){
		boolean finish = true;
		AGraph graph = super.getGraph();
		HashMap<String, Double> s = new HashMap<String, Double>();
		
		for(Argument a : graph.getArguments()){
			double result = 1.0;
			ArrayList<Argument>  args = new ArrayList<Argument>();
			
			args.addAll(a.getAttackers());
			if(!args.isEmpty())
				result = super.getLastU(args.get(0).getId());
			for(int i=1; i<args.size();i++){
				result += super.getLastU(args.get(i).getId()) - result*super.getLastU(args.get(i).getId());
			}
			double utility = (1.0/(1.0+this.xhi))*(1-result);
				
			if(super.getLastU(a.getId()) > utility + epsilon || super.getLastU(a.getId()) < utility - epsilon){
				finish = false;
			}
			s.put(a.getId(), utility);
		}
		
		super.addStep(s);
		
		if(!finish)
			algo();
	}

	@Override
	public void end() {
		AGraph graph = super.getGraph();
		
		for(Argument a : graph.getArguments()){
			a.setUtility(super.getLastU(a.getId()));
		}
		
		String chiffre = "" + epsilon;
		int produit = (int) Math.pow(10, chiffre.split("\\.")[1].length());
		for(Argument a : graph.getArguments()){
			a.setUtility((double)((int)(a.getUtility()*produit))/produit);
		}
	}

}
