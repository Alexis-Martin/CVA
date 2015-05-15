package algo.implem;

import graph.AGraph;
import algo.Parameter;
import graph.Argument;

import java.util.ArrayList;
import java.util.HashMap;

import algo.AbstractAlgorithm;
import algo.Parameter;

public class SocialAbstractArgumentation extends AbstractAlgorithm {
	double epsilon ;
	double xhi ;
	int currentArg, totalArg;
	
	public SocialAbstractArgumentation(){
		super("Social Abstract Argumentation");

		addParam("epsilon", 0.0001, "Précision de calcul de l'algorithme");
		addParam("xhi", 0.1, "aucune idée");
	}
	
	@Override
	public void init() {
		HashMap<String, Double> s = new HashMap<String, Double>();

		
		super.clearSteps();
		for(Argument a : super.getGraph().getArguments()){
			s.put(a.getId(), a.getWeight());
		}
		super.addStep(s);
		this.epsilon = (double) getParam("epsilon").getValue();
		this.xhi = (double) getParam("xhi").getValue();
	}

	@Override
	public void run() {
		this.algo();
	}
	
	//In Case Of acyclic graph we have to obtain the rigth order of th graph
	
	private void algo(){
		

		boolean finish = false;
		AGraph graph = super.getGraph();

		while(!finish){	

			finish = true;
			
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
		}


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
	
	@Override
	public double getDefaultInitUtility(){
		return 0;
	}

}
