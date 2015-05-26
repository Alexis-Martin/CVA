package algo.implem;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;

import af.ArgumentationFramework;
import af.Argument;
import algo.AbstractAlgorithm;
import algo.Parameter;
import algo.utils.BreathFirst;
import algo.utils.Threshold;

public class DiscussionBasedSemantics extends AbstractAlgorithm {
	private int t;
	private Collection<Argument> args;
	private HashSet<Argument> args_ranked;
	private int currentIt;
	private boolean done = false;
	private static int nbt = 0;
	public DiscussionBasedSemantics(){
		super("Discussion Based Semantics");
		addParam("threshold",-1, "Nombre de tour effectué");
		this.getParam("threshold").setValue(-1);
	}

	@Override
	public void init() {
		this.t = (int) getParam("threshold").getValue();
		if(this.t == -1&&super.getGraph()!=null){
			this.t = Threshold.nbNodes(super.getGraph());
		}
		this.getParam("threshold").setValue(t);
		
		HashMap<String, Double> s = new HashMap<String, Double>();

		
		super.clearSteps();
		for(Argument a : super.getGraph().getArguments()){
			s.put(a.getId(), a.getWeight());
		}
		super.addStep(s);
		this.currentIt =0;
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
					Collection<Argument> y = to_sort.get(score);
					y.add(arg);
				}
				
			}
			SortedSet<Double> keys = new TreeSet<Double>(to_sort.keySet());
			Iterator<Double> it = keys.iterator();
			while(it.hasNext()){
				Double c_v = it.next();
				new_sorted_tab.add(to_sort.get(c_v));
				if(c_v == 0.||to_sort.get(c_v).size() == 1) this.args_ranked.addAll(to_sort.get(c_v));
			}
		}
		return new_sorted_tab;
	}

	@Override
	public void run() {
		//In this algorithm we put value from N to 1
		HashMap<Argument,HashSet<Argument>> attackers_previous = null;
		HashMap<Argument,HashSet<Argument>> attackers_current ;
		this.args= super.getGraph().getArguments();
		int nb_nodes = args.size();
		this.args_ranked = new HashSet<Argument>();
		ArrayList<Collection<Argument>> sorted_tab = new ArrayList<Collection<Argument>>();
		HashSet<Argument> start_arg = new HashSet<Argument>();
		start_arg.addAll(this.args);
		sorted_tab.add(start_arg);
		HashMap<Argument,HashSet<Argument>>  attackers_of_an_arg = new HashMap<Argument,HashSet<Argument>>();

		
		for(int i=0; i<(t)&&args_ranked.size() != nb_nodes; i++){
			currentIt = i;
			HashMap<String, Double> s = new HashMap<String, Double>();
			attackers_current = new HashMap<Argument,HashSet<Argument>>();


			for(Argument a : args){
				//Start
				if( i == 0){
					HashSet<Argument> args = new HashSet<Argument>();
					args.add(a);
					HashSet<Argument> attacker_a_c = BreathFirst.listBreath(args, 1);
					attackers_of_an_arg.put(a, attacker_a_c);
					double a_score = attacker_a_c.size();
					s.put(a.getId(), a_score);
					attackers_current.put(a, attacker_a_c);
				}
				//Impair
				else if( i%2 == 1 ){
					HashSet<Argument> args = attackers_previous.get(a);
					HashSet<Argument> attacker_a_c = new HashSet<Argument>();
					for(Argument arg_a: args){
						attacker_a_c.addAll(attackers_of_an_arg.get(arg_a));
					}
							
						//	BreathFirst.listBreath(args, 1);
					double a_score = - attacker_a_c.size();
					s.put(a.getId(), a_score);	
					attackers_current.put(a, attacker_a_c);
				}
				//Pair
				else{
					HashSet<Argument> args = attackers_previous.get(a);
					HashSet<Argument> attacker_a_c = new HashSet<Argument>();
					for(Argument arg_a: args){
						attacker_a_c.addAll(attackers_of_an_arg.get(arg_a));
					}
					double a_score = attacker_a_c.size();
					s.put(a.getId(), a_score);
					attackers_current.put(a, attacker_a_c);
				}
				
			}
			
			attackers_previous = attackers_current;
			sorted_tab = separe(sorted_tab, s);
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
	public void setGraph(ArgumentationFramework g){
		super.setGraph(g);
		if((int)this.getParam("threshold").getValue() <= 0){
			this.t = Threshold.nbNodes(g);
			this.getParam("threshold").setValue(t);
		}
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
