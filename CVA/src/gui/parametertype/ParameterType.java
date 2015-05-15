package gui.parametertype;

import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.*;

import algo.Parameter;

public abstract class ParameterType extends JPanel{
	
	protected JLabel name;
	protected JComponent component_value ;
	
	private Parameter param;
	
	public ParameterType(Parameter param){
		this.param = param;
		name = new JLabel(param.getName());
		name.setPreferredSize(new Dimension(70, 27));
		component_value = initComponent();

		setToolTip(param.getDescription());		
		
		this.setLayout(new GridLayout());
		JPanel panel_name = new JPanel();
		panel_name.add(name);
		this.add(panel_name);
		JPanel panel_comp = new JPanel();
		panel_comp.add(component_value);
		this.add(panel_comp);
	}
	
	public abstract JComponent initComponent();
	
	public void setLabel(String text)
	{
		name.setText(text);
	}
	
	public JLabel getLabel()
	{
		return name ;
	}
	
	public Object getValue(){
		return param.getValue();
	}
	
	public void setValue(Object value)
	{
		param.setValue(value);
	}
	
	public void setToolTip(String description)
	{
		name.setToolTipText(description);
	}
	
	public void setJComponent(JComponent component){
		component_value = component;
	}
	
	public JComponent getJComponent(){
		return component_value;
	}
}
