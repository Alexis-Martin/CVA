package algo.implem;

import af.Argument;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Random;

import algo.AbstractAlgorithm;

public class RandomWalkSemantic extends AbstractAlgorithm {

	private int nbPath = 50;
	private double p = 0.3;
	private Random rand ;
	private int iteration = 0;
	
	public RandomWalkSemantic() {
		super("Random Walk Based Semantic");
		addParam("p",p, "Probabilité d'arret");
		addParam("nbPath",nbPath, "Nombre de chemin");
		rand = new Random();
		rand.setSeed(System.nanoTime());
	}

	@Override
	public void init() {
		this.p = (double) this.getParam("p").getValue();
		this.nbPath = (int) this.getParam("nbPath").getValue();
	
		HashMap<String, Double> s = new HashMap<String, Double>();

		super.clearSteps();
		for(Argument a : super.getGraph().getArguments()){
			s.put(a.getId(), a.getWeight());
		}
		super.addStep(s);
	}

	@Override
	public void run() {
		this.compute();
	}
	public void compute(){
		Collection<Argument> arguments = this.getGraph().getArguments();
		HashMap<String, Double> s = new HashMap<String, Double>();
		int k = 0;
		for(Argument argument : arguments){
			Argument current_arg = argument;
			int accepted = 0;

			for(int i = 0 ; i<this.nbPath; i++){
				current_arg = argument;
				this.iteration = k * this.nbPath + i;
				
				double random_double = rand.nextDouble();
				boolean acceptation = true;
				while(random_double > this.p && !current_arg.getAttackers().isEmpty()){
					ArrayList<Argument> attackers = new ArrayList<Argument>();
					attackers.addAll(current_arg.getAttackers());
		
					acceptation = ! acceptation;
					
					random_double = rand.nextDouble();
					current_arg = attackers.get(rand.nextInt(attackers.size()));
				}
				if(acceptation) accepted++;				
				
			}

			k++;
			s.put(argument.getId(), ((double)accepted)/((double)this.nbPath));
		}
		this.addStep(s);
	}

	@Override
	public void end() {
		//TODO:preferences a la fin dependent des iterations d'avant (voir papier)
		for(Argument a : super.getGraph().getArguments()){
			a.setUtility((1)*super.getLastU(a.getId()));
		}
	}
	@Override
	public int getCurrentIteration() {
		// TODO Auto-generated method stub
		return this.iteration;
	}
	public int getNbIteration(){
		return this.nbPath*this.getGraph().getArguments().size();
	}
}
