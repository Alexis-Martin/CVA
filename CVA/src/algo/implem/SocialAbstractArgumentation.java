package algo.implem;

import graph.AGraph;
import algo.Parameter;
import graph.Argument;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import algo.AbstractAlgorithm;
import algo.Parameter;

public class SocialAbstractArgumentation extends AbstractAlgorithm {
	double epsilon ;
	double xhi ;
	int currentArg, totalArg;
	
	public SocialAbstractArgumentation(){
		super("Social Abstract Argumentation ISS");

		addParam("epsilon", 0.001, "Précision de calcul de l'algorithme");
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
			HashSet<Argument> argument_computed = new HashSet<Argument>();
			
			for(Argument a : graph.getArguments()){
				double result = 0.0;

				ArrayList<Argument>  attackers = new ArrayList<Argument>();

				attackers.addAll(a.getAttackers());
				
				if(!attackers.isEmpty()) result = 1.;
				for(int i=0; i<attackers.size();i++){
					if(argument_computed.contains(attackers.get(i))){
						result *= (1 - s.get(attackers.get(i).getId()));
					}
					else{
						result *= (1 - super.getLastU(attackers.get(i).getId()));
					}
					
				}

				double utility = (1.0/(1.0+this.xhi))*result;

				if(super.getLastU(a.getId()) > utility + epsilon || super.getLastU(a.getId()) < utility - epsilon){
					finish = false;
				}
				argument_computed.add(a);
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

	@Override
	public int getCurrentIteration() {
		return 0;
	}
	
}
