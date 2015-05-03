package algo;

public class Parameter {
	String name;
	Object value;
	String description;
	
	public Parameter(String name, Object value){
		this.name = name;
		this.value = value;
	}
	
	public Object getValue(){
		return value;
	}
	
	public void setValue(Object v){
		this.value = v;
	}
	
	public String getName(){
		return name;
	}
	
	public String printVal(){
		return ""+value;
	}
}
