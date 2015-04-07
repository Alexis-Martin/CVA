package algo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import CVAGraph.AGraph;
import CVAGraph.Argument;

public class SocialAbstractArgumentation extends AbstractAlgorithm {
	double epsilon;
	double param;
	int currentArg, totalArg;
	ArrayList<HashMap<Argument,Double>> steps;
	
	public SocialAbstractArgumentation(AGraph graph, String name){
		this(graph, name, 0, 0.1);	
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
		steps = new ArrayList<HashMap<Argument,Double>>();
	}

	@Override
	public void run() {
		this.algo();

	}
	
	//In Case Of acyclic graph we have to obtain the rigth order of th graph
	
	private void algo(){
		boolean finish = true;
		AGraph graph = super.getGraph();
		HashMap<Argument,Double> utilities = new HashMap<Argument,Double>();
		for(Argument a : graph.getArguments()){

			double result = 1;
			for(Argument argA : a.getAttackers()){
				if(a.getAttackers().size() == 1)
					result = 1-argA.getUtility();
				else
					for(Argument argB : a.getAttackers())
						if(!argA.equals(argB))
							result *= 1- (argA.getUtility()+argB.getUtility() - argA.getUtility()*argB.getUtility());
				
			}
			
			
			double utility = (1/(1+this.param))*result;
				
			if(a.getUtility() > utility + epsilon || a.getUtility() < utility - epsilon){
				finish = false;
			}
			utilities.put(a, utility);
		}
		//UPDATE GRAPH
		for(Argument arg :utilities.keySet()){
			arg.setUtility(utilities.get(arg));
		}
		this.steps.add(utilities);
		if(!finish)
			algo();
	}


	public void getStep(int i){
		HashMap<Argument, Double> col = this.steps.get(i);
		for(Argument arg : col.keySet()){
			arg.setUtility(col.get(arg));
		}
	}

	public int getStepNumber(){
		return this.steps.size();
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
