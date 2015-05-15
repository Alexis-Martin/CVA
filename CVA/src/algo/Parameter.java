package algo;

public class Parameter {
	private String name;
	private Object value;
	private String description;
	
	public Parameter(String name, Object value, String description){
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
