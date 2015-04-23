package algo;

import java.util.HashMap;

import CVAGraph.AGraph;

abstract class AbstractAlgorithm implements Algorithm {
	private String name;
	private AGraph graph;
	private HashMap<String, Parameter> params;
	

	private AbstractAlgorithm(){}
	
	public AbstractAlgorithm(AGraph graph, String name){
		this();
		this.graph = graph;
		this.name = name;
		params = new HashMap<String, Parameter>();
		
	}
	@Override
	public abstract void init();

	@Override
	public abstract void run();

	public final void execute(){
		init();
		run();
		end();
	}
	
	
	public abstract void end();
	@Override
	public String getName(){
		return name;
	}
	
	public AGraph getGraph(){
		return graph;
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
	public Parameter getParam(String name){
		return this.params.get(name);
	}
}
