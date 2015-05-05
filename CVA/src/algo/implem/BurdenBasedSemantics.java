package algo.implem;

import java.util.HashMap;

import CVAGraph.Argument;
import algo.AbstractAlgorithm;
import algo.utils.Threshold;

public class BurdenBasedSemantics extends AbstractAlgorithm {
	private double t;
	
	
	public BurdenBasedSemantics(){
		super("Burden Based Semantics");
	}

	@Override
	public void init() {
		HashMap<String, Double> s = new HashMap<String, Double>();
		
		t = Threshold.nbNodes(super.getGraph());
		
		super.clearSteps();
		for(Argument a : super.getGraph().getArguments()){
			s.put(a.getId(), 1.0);
		}
		super.addStep(s);
	}

	@Override
	public void run() {
		for(int i=0; i<t; i++){
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
		for(Argument a : super.getGraph().getArguments()){
			a.setUtility((-1)*super.getLastU(a.getId()));
		}
	}
}
