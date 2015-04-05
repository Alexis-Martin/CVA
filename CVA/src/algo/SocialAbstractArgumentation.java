package algo;

import java.util.ArrayList;
import java.util.Collection;

import CVAGraph.AGraph;
import CVAGraph.Argument;

public class SocialAbstractArgumentation extends AbstractAlgorithm {
	double epsilon;
	double param;
	int currentArg, totalArg;

	public SocialAbstractArgumentation(AGraph graph, String name){
		this(graph, name, 0, 0.0001);	
	}
	public SocialAbstractArgumentation(AGraph graph, String name, double epsilon, double param) {
		super(graph, name);
		this.epsilon = epsilon;
		this.param = param;
	}
	@Override
	public void init() {
		
		if(epsilon == 0 || epsilon > 1)
			epsilon = 0.0001; 
		
		AGraph graph = super.getGraph();
		for(Argument a : graph.getArguments()){
			a.setUtility(0);
		}
	}

	@Override
	public void run() {
		this.algo();

	}
	
	//In Case Of acyclic graph we have to obtain the rigth order of th graph
	
	public void algo(){
		boolean finish = true;
		AGraph graph = super.getGraph();
		for(Argument a : graph.getArguments()){
			double attP = 0;
			double attM = 1;
			for(Argument arg : a.getAttackers()){
			
				attP += arg.getUtility();
				attM *= arg.getUtility();
			}
			
			double utility = (1/(1+this.param))*(1-(attP-attM));
				
			if(a.getUtility() > utility + epsilon || a.getUtility() < utility - epsilon){
				finish = false;
			}
			a.setUtility(utility);
		}
		
		if(!finish)
			algo();
	}
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return this.getName();
	}

	public AGraph getStep(int i){
		return this.getGraph();
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
