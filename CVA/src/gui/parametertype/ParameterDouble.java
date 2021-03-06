package gui.parametertype;

import java.awt.Dimension;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JComponent;
import javax.swing.JTextField;

import algo.Parameter;

public class ParameterDouble extends ParameterType {

	public ParameterDouble(Parameter param) {
		super(param);
	}

	@Override
	public JComponent initComponent() {
		JTextField f = new JTextField("" + getValue());
		f.setPreferredSize(new Dimension(70, 27));
		f.addFocusListener(new FocusListener() {
			
			@Override
			public void focusLost(FocusEvent arg0) {
				try{
					double d = Double.parseDouble(((JTextField)arg0.getComponent()).getText());
					setValue(d);
				}
				catch(NumberFormatException e){
					((JTextField)arg0.getComponent()).setText("" + (double)getValue());
				}
			}
			
			@Override
			public void focusGained(FocusEvent arg0) {
				((JTextField)arg0.getComponent()).selectAll();
			}
		});
		return f;
	}

	
	public void setValue(double d){
		super.setValue(d);
		((JTextField)getJComponent()).setText("" + d);
		
	}
	
	

	
	
}
