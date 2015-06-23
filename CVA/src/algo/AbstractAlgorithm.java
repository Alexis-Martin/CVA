package algo;

import helper.FileHelper;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import utils.AlgoFinder;
import af.ArgumentationFramework;
import af.Argument;

public abstract class AbstractAlgorithm implements Algorithm {
	private String name;
	private ArgumentationFramework graph;
	private HashMap<String, Parameter> params;
	private List<HashMap<String, Double>> steps;
	protected boolean stepByStep;
	
	public AbstractAlgorithm(String name){
		this.name = name;
		params = new HashMap<String, Parameter>();
		steps = new ArrayList<HashMap<String, Double>>();
		this.graph = null;
	}
	
	@Override
	public abstract void init();

	@Override
	public abstract void run();

	@Override
	public final void execute(boolean stepByStep){
		this.stepByStep = stepByStep;
		init();
		run();
		end();
	}
	
	@Override
	public final void execute(){
		this.execute(false);
	}
	
	public abstract void end();
	@Override
	public String getName(){
		return name;
	}
	
	@Override
	public ArgumentationFramework getGraph(){
		return graph;
	}
	
	@Override
	public void setGraph(ArgumentationFramework graph){
		this.graph = graph;
	}

	
	@Override
	public HashMap<String, Parameter> getParams(){
		return params;
	}
	
	@Override
	public void addParam(Parameter param){
		this.params.put(param.getName(), param);
	}
	
	@Override
	public void addParam(String name, Object value, String description){
		if(params.containsKey(name)){
			setParam(name, value);
			return;
		}
		addParam(new Parameter(name, value, description));
		
	}
	
	@Override
	public boolean setParam(String name, Object value){
		if(params.containsKey(name)){
			params.get(name).setValue(value);
			return true;
		}
		return false;
	}
	
	@Override
	public Parameter getParam(String name){
		return this.params.get(name);
	}
	
	@Override
	public String getRes(){
		if(this.getGraph() == null){
			return "";
		}
		
		List<Argument> list = this.getGraph().getUtilities();
		String res = "";
		if(list.size() != 0)
			res+=  "(" + list.get(0).getId() +", " + list.get(0).getUtility() + ")";
		for(int i = 1; i < list.size(); i++){
			if(list.get(i).getUtility() < list.get(i-1).getUtility())
				res+= " > ";
			else
				res+= " = ";
			
			res+="(" + list.get(i).getId() +", " + list.get(i).getUtility() + ")";
		}
		return res;
	}
	
	@SuppressWarnings("unchecked")
	public static List<Algorithm> getAlgos(){
		List<Algorithm> algos = new ArrayList<Algorithm>();
		List<Class<Algorithm>> classes = AlgoFinder.findAlgo();
		for(Class<Algorithm> alg : classes){
			Algorithm algo;
			try {
				algo = alg.newInstance();
				algos.add(algo);
			} catch (InstantiationException | IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		/*
		File dir = FileHelper.getResource("src/algo/implem");
		List<Algorithm> algos = new ArrayList<Algorithm>();
		for (final File entryFile : dir.listFiles()){
			String classFile = entryFile.getName();
			if(classFile.contains(".java")){
				String className = classFile.split("\\.")[0];
				try {
					Class<Algorithm> algo = (Class<Algorithm>) Class.forName("algo.implem."+className);
					Algorithm alg = algo.newInstance();
					algos.add(alg);
				} catch (InstantiationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}	
		*/
		return algos;
	}
	
	@Override
	public double getDefaultInitUtility(){
		return 1;
	}
	
	@Override
	public int getNbIteration(){
		return 0;
	}
	
	@Override
	public int getCurrentIteration() {
		return 0;
	}
	
	protected void clearSteps(){
		steps.clear();
	}
	
	protected double getU(int step, String a){
		return steps.get(step).get(a);
	}
	
	protected double getLastU(String a){
		return this.getU(this.steps.size()-1, a);
	}
	
	protected void addStep(HashMap<String, Double> s){
		if(!stepByStep && steps.size() > 0){
			steps.remove(this.steps.size() -1);
		}
		this.steps.add(s);
	}
}
