package gui.parametertype;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTextField;

import algo.Parameter;

public class ParameterBoolean extends ParameterType{

	public ParameterBoolean(Parameter param, boolean value) {
		super(param);
		this.setValue(value);
	}
	public ParameterBoolean(Parameter param) {
		this(param, false);
	}
	@Override
	public JComponent initComponent() {
		JCheckBox c = new JCheckBox();
		c.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				setValue(((JCheckBox)arg0.getSource()).isSelected());
				
			}
		});
		return null;
	}
	
	public void setValue(boolean b){
		super.setValue(b);
		((JCheckBox)this.getJComponent()).setSelected(b);

	}

	

}
