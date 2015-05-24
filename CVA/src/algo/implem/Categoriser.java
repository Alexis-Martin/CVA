package algo.implem;

import java.util.HashMap;

import af.ArgumentationFramework;
import af.Argument;
import algo.AbstractAlgorithm;
import algo.Parameter;

public class Categoriser extends AbstractAlgorithm {	
	
	private final String epsilon = "epsilon";
	private double eps;
	
	public Categoriser(){
		super("Categoriser");
		addParam(epsilon, 0.0001, "Précision de calcul de l'algorithme");
	}
	
	@Override
	public void init() {
		Parameter eps = getParam(epsilon);
		HashMap<String, Double> s = new HashMap<String, Double>();
		
		if((double) eps.getValue() == 0 || (double) eps.getValue() > 1)
			eps.setValue(0.0001); 
		
		this.eps = (double) eps.getValue();
		
		super.clearSteps();
		for(Argument a : super.getGraph().getArguments()){
			s.put(a.getId(), a.getWeight());
		}
		super.addStep(s);
	}

	@Override
	public void run() {
		System.out.println("epsilon : "+eps);
		algo();
	}
	
	@Override
	public void end(){
		ArgumentationFramework graph = super.getGraph();
		
		for(Argument a : graph.getArguments()){
			a.setUtility(super.getLastU(a.getId()));
		}
		
		String chiffre = "" + eps;
		int produit = (int) Math.pow(10, chiffre.split("\\.")[1].length());
		for(Argument a : graph.getArguments()){
			a.setUtility((double)((int)(a.getUtility()*produit))/produit);
		}
		
	}
	
	private void algo(){
		boolean finish = true;
		HashMap<String, Double> s = new HashMap<String, Double>();
		
		for(Argument a : super.getGraph().getArguments()){
			double att = 1;
			 
			for(Argument arg : a.getAttackers()){
				att += super.getLastU(arg.getId());
			}
			
			double utility = 1 / att;
				
			if(super.getLastU(a.getId()) > utility + eps || super.getLastU(a.getId()) < utility - eps){
				finish = false;
			}
			s.put(a.getId(), utility);
		}
		
		this.addStep(s);
		
		if(!finish)
			algo();
	}
	
	@Override
	public int getCurrentIteration() {
		return 0;
	}

	
}
