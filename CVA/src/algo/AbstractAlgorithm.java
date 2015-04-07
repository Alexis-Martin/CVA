package algo;

import CVAGraph.AGraph;

abstract class AbstractAlgorithm implements Algorithm {
	private String name;
	private AGraph graph;
	
	@SuppressWarnings("unused")
	private AbstractAlgorithm(){}
	
	public AbstractAlgorithm(AGraph graph, String name){
		this.graph = graph;
		this.name = name;
		
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

}
