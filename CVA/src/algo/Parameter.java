package algo;

public class Parameter {
	String name;
	double value;
	String description;
	
	public Parameter(String name, double value){
		this.name = name;
		this.value = value;
	}
	
	public double getValue(){
		return value;
	}
	
	public void setValue(double v){
		this.value = v;
	}
	
	public String getName(){
		return name;
	}
}
