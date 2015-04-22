package algo;

import java.util.HashMap;

public interface Algorithm {
	public void init();
	public void run();
	public String getName();
	public void execute();
	public void end();
	public HashMap<String, Parameter> getParams();
	public void addParam(Parameter param);
	public Parameter getParam(String name);
	
} 
