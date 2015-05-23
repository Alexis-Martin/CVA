package algo.implem;

import graph.AGraph;
import graph.Argument;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;

import algo.AbstractAlgorithm;
import algo.Parameter;
import algo.utils.BreathFirst;
import algo.utils.Threshold;

public class Propagation extends AbstractAlgorithm{
	double epsilon ;
	double xhi ;
	int currentArg, totalArg;
	private int t;
	private Collection<Argument> args;
	private HashSet<Argument> args_ranked;
	private Double delta ;
	private int currentIt;

	
	public Propagation(){
		super("Propagation");

		addParam("epsilon", 0.0, "aucune idée");
		addParam("threshold", 50, "Nombre de tour effectué");
		addParam("delta", 1., "aucune idée");
	}
	
	@Override
	public void init() {
		this.t = (int) getParam("threshold").getValue();
		if(this.t == -1.)
			this.t = Threshold.nbNodes(super.getGraph());
		this.epsilon = (double) getParam("epsilon").getValue();
		this.delta = (double) getParam("delta").getValue();
		HashMap<String, Double> s = new HashMap<String, Double>();
		super.clearSteps();
		for(Argument a : super.getGraph().getArguments()){
			s.put(a.getId(), a.getWeight());
		}
		super.addStep(s);
	}
	
	private ArrayList<Collection<Argument>> separe(ArrayList<Collection<Argument>> sorted_tab, HashMap<String,Double> scores){
		ArrayList<Collection<Argument>> new_sorted_tab = new ArrayList<Collection<Argument>>();
		for (int i = 0 ; i< sorted_tab.size() ; i++){
			//Sort
			HashMap<Double, Collection<Argument>> to_sort = new HashMap<Double,Collection<Argument>>();
			
			for(Argument arg: sorted_tab.get(i)){
				Double score = scores.get(arg.getId());
				if(score!=null){
					if(!to_sort.containsKey(score)){
						to_sort.put(score, new HashSet<Argument>());
					}
					//System.out.println(scores);
					Collection<Argument> y = to_sort.get(score);
					y.add(arg);
				}
				
			}
			SortedSet<Double> keys = new TreeSet<Double>(to_sort.keySet());
			Iterator<Double> it = keys.iterator();
			while(it.hasNext()){
				Double c_v = it.next();
				new_sorted_tab.add(to_sort.get(c_v));
				if(to_sort.get(c_v).size() == 1) this.args_ranked.addAll(to_sort.get(c_v));
			}
		}
		//System.out.println(new_sorted_tab);
		return new_sorted_tab;
	}
	public HashMap<String,Double> current_ranking(ArrayList<Collection<Argument>> sorted_tab){
		HashMap<String, Double> current_sol = new HashMap<String,Double>();
		for(int i = 0 ; i< sorted_tab.size() ; i++ ){
			for(Argument arg : sorted_tab.get(i)){
				current_sol.put(arg.getId(), (double) (sorted_tab.size()-i));
			}
		}
		return current_sol;
	}
	@Override
	public void run() {
		//In this algorithm we put value from N to 1

		HashMap<Argument,HashSet<Argument>> attackers_previous = null;
		HashMap<Argument,HashSet<Argument>> attackers_current ;
		HashMap<Argument,Double> vals = new HashMap<Argument,Double>();
		HashMap<Argument,Double> v_arg = new HashMap<Argument,Double>();
		
		this.args = super.getGraph().getArguments();
		//initialisation
		//Attacker init
		for(Argument arg : args){
			HashSet<Argument> a = new HashSet<Argument>();
			a.add(arg);
			HashSet<Argument> attacker_a_c = BreathFirst.listBreath(a, 1);
			double a_score = 0.;
			if(attacker_a_c.size() == 0)
				a_score = 1;
			else
				a_score = this.epsilon;
			vals.put(arg, 0.);
			v_arg.put(arg, a_score);
		}

		int nb_nodes = args.size();
		this.args_ranked = new HashSet<Argument>();
		ArrayList<Collection<Argument>> sorted_tab = new ArrayList<Collection<Argument>>();
		HashSet<Argument> start_arg = new HashSet<Argument>();
		start_arg.addAll(this.args);
		sorted_tab.add(start_arg);
		

		for(int i=0; i<(t)&&args_ranked.size() != nb_nodes; i++){
			currentIt = i;
			HashMap<String, Double> s = new HashMap<String, Double>();
			
			
			attackers_current = new HashMap<Argument,HashSet<Argument>>();
	
			

			for(Argument a : args){
				//Start -> defend
				if( i == 0){
					HashSet<Argument> args = new HashSet<Argument>();
					args.add(a);
					HashSet<Argument> defender_a_c = new HashSet<Argument>();
					defender_a_c.add(a);	
					vals.put(a,this.delta * v_arg.get(a)); 
					
					attackers_current.put(a, defender_a_c);	
					s.put(a.getId(), - this.delta * v_arg.get(a));
				}
				//Impair attack
				else if( i%2 == 1 ){
					
					HashSet<Argument> args = attackers_previous.get(a);
					HashSet<Argument> attacker_a_c = BreathFirst.listBreath(args, 1);
					double a_score = vals.get(a);	
					for(Argument att : attacker_a_c){
						a_score -= this.delta * v_arg.get(att);
					}
					attackers_current.put(a, attacker_a_c);
					vals.put(a, a_score);
					s.put(a.getId(), - a_score);
					
					
				}
				//Pair defend
				else{
					HashSet<Argument> args = attackers_previous.get(a);
					HashSet<Argument> attacker_a_c = BreathFirst.listBreath(args, 1);
					double a_score = vals.get(a);	
					for(Argument att : attacker_a_c){
						a_score += this.delta * v_arg.get(att);
					}
					attackers_current.put(a, attacker_a_c);
					vals.put(a, a_score);
					s.put(a.getId(),- a_score);
				}
				
			}
			
			

			sorted_tab = separe(sorted_tab, s);

			attackers_previous = attackers_current;
			this.addStep(this.current_ranking(sorted_tab));
			
		}
	}
	@Override
	public void end() {
		//TODO:preferences a la fin dependent des iterations d'avant (voir papier)
		for(Argument a : super.getGraph().getArguments()){
			a.setUtility((1)*super.getLastU(a.getId()));
		}
	}
	@Override
	public void setGraph(AGraph g){
		super.setGraph(g);
		this.t = Threshold.nbNodes(g);
		this.setParam("threshold", t);
	}
	@Override
	public double getDefaultInitUtility(){
		return 0;
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
