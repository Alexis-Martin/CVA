package gui.parametertype;

public class ParameterDouble extends ParameterType {

	public ParameterDouble(Object value) {
		super(value);
	}
	
	public ParameterDouble(String string_name, String text,Object value) 
	{
		this(value);
		setLabel(string_name);
		setField(text); 
	}

	
	
}
