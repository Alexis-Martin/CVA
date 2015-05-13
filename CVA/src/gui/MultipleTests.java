package gui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import algo.Algorithm;
import algo.Parameter;

public class MultipleTests {
	private Algorithm algo;
	private HashMap<String, List<Parameter>> algo_params;
	
	
	public MultipleTests(Algorithm algo){
		this.algo = algo;
		algo_params = new HashMap<String, List<Parameter>>();
	}
	
	public void put(String name_param, Parameter param){ 
		if(!algo_params.containsKey(name_param))
			algo_params.put(name_param, new ArrayList<Parameter>());
		algo_params.get(name_param).add(param);
	}
	
	public void set(String name_param, List<Parameter> params){
		algo_params.put(name_param, params);
	}

	public String getName() {
		return algo.getName();
	}
	
	public List<Parameter> getParameters(int i){
		Set<String> keys = algo_params.keySet();
		List<Parameter> retour = new ArrayList<Parameter>();

		for(String key : keys){
			List<Parameter> param_key = algo_params.get(key);
			
			retour.add(param_key.get(i % param_key.size()));
			i = i / param_key.size();
			
		}
		
		return retour;
	}
	
	public int size(){
		int size = 1;
		for(String key : algo_params.keySet()){
			size *= algo_params.get(key).size();
		}
		return size;
	}

	public Algorithm getAlgo() {
		return algo;
	}

	public Set<String> getTitles() {
		return algo_params.keySet();
	}
	
}
