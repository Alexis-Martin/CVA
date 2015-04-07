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
	
			ArrayList<Argument>  args = new ArrayList<Argument>();
			args.addAll(a.getAttackers());
			for(int i=0; i<args.size();i++){
				if(args.size() == 1)
					result = 1-args.get(0).getUtility();
				else
					for(int j = i+1;j<args.size();j++)
						result *= 1- (args.get(i).getUtility()+args.get(j).getUtility() - args.get(i).getUtility()*args.get(j).getUtility());
				
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