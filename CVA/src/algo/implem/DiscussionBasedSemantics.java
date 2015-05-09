package algo.implem;

import graph.Argument;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;

import algo.AbstractAlgorithm;
import algo.utils.BreathFirst;
import algo.utils.Threshold;

public class DiscussionBasedSemantics extends AbstractAlgorithm {
	private double t;
	private Collection<Argument> args;
	private HashSet<Argument> args_ranked;
	
	
	public DiscussionBasedSemantics(){
		super("Discussion Based Semantics");
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
				if(c_v == 0.||to_sort.get(c_v).size() == 1) this.args_ranked.addAll(to_sort.get(c_v));
			}
		}
		//System.out.println(new_sorted_tab);
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
		for(int i=0; i<(t*t)&&args_ranked.size() != nb_nodes; i++){

			HashMap<String, Double> s = new HashMap<String, Double>();
			attackers_current = new HashMap<Argument,HashSet<Argument>>();


			for(Argument a : args){
				//Start
				if( i == 0){
					HashSet<Argument> args = new HashSet<Argument>();
					args.add(a);
					HashSet<Argument> attacker_a_c = BreathFirst.listBreath(args, 1);
					double a_score = attacker_a_c.size();
					//System.out.println("Score ici"+a_score);
					s.put(a.getId(), a_score);
					attackers_current.put(a, attacker_a_c);
				}
				//Impair
				else if( i%2 == 1 ){
					HashSet<Argument> args = attackers_previous.get(a);
					HashSet<Argument> attacker_a_c = BreathFirst.listBreath(args, 1);
					double a_score = - attacker_a_c.size();
					s.put(a.getId(), a_score);	
					attackers_current.put(a, attacker_a_c);
				}
				//Pair
				else{
					HashSet<Argument> args = attackers_previous.get(a);
					HashSet<Argument> attacker_a_c = BreathFirst.listBreath(args, 1);
					double a_score = attacker_a_c.size();
					s.put(a.getId(), a_score);
					//System.out.println("Score ici"+a_score);
					attackers_current.put(a, attacker_a_c);
				}
				
			}
			
			attackers_previous = attackers_current;
			sorted_tab = separe(sorted_tab, s);
			
			//System.out.println(s);
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
}