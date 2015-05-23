package algo.implem;

import graph.AGraph;
import graph.Argument;

import java.util.HashMap;

import algo.AbstractAlgorithm;
import algo.Parameter;
import algo.utils.Threshold;

public class BurdenBasedSemantics extends AbstractAlgorithm {
	private int t;
	private int currentIt;
	private double eps;
	
	public BurdenBasedSemantics(){
		super("Burden Based Semantics");
		this.addParam("threshold", -1, "Nombre de tour effectué");
		this.addParam("epsilon", 0.0001, "Précision de calcul de l'algorithme");
	}

	@Override
	public void init() {
		HashMap<String, Double> s = new HashMap<String, Double>();
		this.t = (int)getParam("threshold").getValue();
		if(this.t == -1.)
			this.t = Threshold.nbNodesSquare(super.getGraph());
		this.getParam("threshold").setValue(t);
		
		this.eps = (double) getParam("epsilon").getValue();
		
		if( eps == 0 || eps > 1){
			this.eps = 0.0001;
			this.getParam("epsilon").setValue(eps);
		}
		
		super.clearSteps();
		for(Argument a : super.getGraph().getArguments()){
			s.put(a.getId(), a.getWeight());
		}
		super.addStep(s);
	}

	@Override
	public void run() {
		boolean finish = false;
		for(int i=0; i<t && !finish; i++){
			finish = true;
			currentIt = i;
			HashMap<String, Double> s = new HashMap<String, Double>();
			
			for(Argument a : super.getGraph().getArguments()){
				double utility = 1;
				 
				for(Argument arg : a.getAttackers()){
					utility += 1/super.getLastU(arg.getId());
				}
				
				s.put(a.getId(), utility);
				if(super.getLastU(a.getId()) > utility + eps || super.getLastU(a.getId()) < utility - eps){
					finish = false;
				}
			}
			
			this.addStep(s);
		}
	}

	@Override
	public void end() {
		//TODO:preferences a la fin dependent des iterations d'avant (voir papier)
		for(Argument a : super.getGraph().getArguments()){
			a.setUtility((-1)*super.getLastU(a.getId()));
		}
	}
	
	@Override
	public void setGraph(AGraph g){
		super.setGraph(g);
		this.t = Threshold.nbNodesSquare(g);
		this.setParam("threshold", t);
	}

	@Override
	public int getCurrentIteration() {
		return currentIt;
	}
	
	@Override
	public int getNbIteration(){
		return t;
	}
}
