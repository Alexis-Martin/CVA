package gui.parametertype;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.*;

public abstract class ParameterType extends JPanel{
	
	protected JLabel name;
	protected JTextField textField ;
	protected Object value; 

	public ParameterType()
	{
	}
	
	public ParameterType(Object value) 
	{
		name = new JLabel();
		textField = new JTextField();
		textField.setPreferredSize(new Dimension(100, 27));
		this.add(name);
		this.add(textField);
		setValue(value);
	}
	
	public ParameterType(String string_name, String text,Object value) 
	{
		this(value);
		setLabel(string_name);
		setField(text); 
	}
	
	public void setLabel(String text)
	{
		name.setText(text);
	}
	
	public void setField(String t)
	{
		textField.setText(t);
	}
	
	public void setValue(Object value)
	{
		this.value = value ;
	}
	
	public JLabel getLabel()
	{
		return name ;
	}
	
	public JTextField getField()
	{
		return textField ;
	}
	
	public Object getValue()
	{
		return value;
	}
	
	public void setToolTip(String description)
	{
		name.setToolTipText(description);
	}
	
}
