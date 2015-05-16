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
	
	public BurdenBasedSemantics(){
		super("Burden Based Semantics");
		this.addParam("threshold", -1, "Nombre de tour effectué");
	}

	@Override
	public void init() {
		HashMap<String, Double> s = new HashMap<String, Double>();
		this.t = (int)getParam("threshold").getValue();
		if(this.t == -1.)
			this.t = Threshold.nbNodes(super.getGraph());
		this.getParam("threshold").setValue(t);
		
		super.clearSteps();
		for(Argument a : super.getGraph().getArguments()){
			s.put(a.getId(), a.getWeight());
		}
		super.addStep(s);
	}

	@Override
	public void run() {
		for(int i=0; i<t; i++){
			currentIt = i;
			HashMap<String, Double> s = new HashMap<String, Double>();
			
			for(Argument a : super.getGraph().getArguments()){
				double utility = 1;
				 
				for(Argument arg : a.getAttackers()){
					utility += 1/super.getLastU(arg.getId());
				}
				
				s.put(a.getId(), utility);
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
		this.t = g.getArguments().size();
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
