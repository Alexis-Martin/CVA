package algo;

import java.util.HashMap;

import CVAGraph.AGraph;

public interface Algorithm {
	public void init();
	public void run();
	public String getName();
	public void execute(AGraph g);
	public void end();
	public HashMap<String, Parameter> getParams();
	public void addParam(Parameter param);
	public Parameter getParam(String name);
	
} 
