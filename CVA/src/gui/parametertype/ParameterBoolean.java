package gui.parametertype;

import java.awt.Dimension;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class ParameterBoolean extends ParameterType{

	JCheckBox box; 
	

	public ParameterBoolean(Object value) {
		name = new JLabel();
		textField = new JTextField();
		textField.setPreferredSize(new Dimension(100, 27));
		box = new JCheckBox();
		this.add(name);
		this.add(box);
	}
	
	public ParameterBoolean(String string_name, String text,Object value) 
	{
		this(value);
		setLabel(string_name);
	}
	
	public ParameterBoolean(String string_name, boolean text,Object value) 
	{
		this(value);
		setLabel(string_name);
		box.setSelected(text);
		 
	}

}
