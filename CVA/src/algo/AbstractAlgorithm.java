package algo;

import CVAGraph.AGraph;

abstract class AbstractAlgorithm implements Algorithm {
	private String name;
	private AGraph graph;
	
	private AbstractAlgorithm(){}
	
	public AbstractAlgorithm(AGraph graph, String name){
		this.name = name;
		this.graph = graph;
		
	}
	@Override
	public abstract void init();

	@Override
	public abstract void run();

	@Override
	public String getName(){
		return name;
	}
	
	public AGraph getGraph(){
		return graph;
	}

}
