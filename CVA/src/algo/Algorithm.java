package algo;

import graph.AGraph;

import java.util.HashMap;

public interface Algorithm {
	
	public void init();
	public void run();
	public String getName();
	public void execute();
	public void execute(boolean stepByStep);
	public void end();
	public HashMap<String, Parameter> getParams();
	public void addParam(Parameter param);
	public void addParam(String name, Object value, String description);
	public boolean setParam(String name, Object value);
	public Parameter getParam(String name);
	public String getRes();
	public AGraph getGraph();
	public void setGraph(AGraph graph);
	public double getDefaultInitUtility();
} 
