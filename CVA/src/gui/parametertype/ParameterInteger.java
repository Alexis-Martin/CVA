package gui.parametertype;

import java.awt.Dimension;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JComponent;
import javax.swing.JTextField;

import algo.Parameter;

public class ParameterInteger extends ParameterType {

	public ParameterInteger(Parameter param) {
		super(param);
		// TODO Auto-generated constructor stub
	}

	@Override
	public JComponent initComponent() {
		JTextField f = new JTextField("" + getValue());
		f.setPreferredSize(new Dimension(70, 27));

		f.addFocusListener(new FocusListener() {
			
			@Override
			public void focusLost(FocusEvent arg0) {
				try{
					int d = Integer.parseInt(((JTextField)arg0.getComponent()).getText());
					setValue(d);
				}
				catch(NumberFormatException e){
					((JTextField)arg0.getComponent()).setText("" + (int)getValue());
				}
			}
			
			@Override
			public void focusGained(FocusEvent arg0) {
				((JTextField)arg0.getComponent()).selectAll();
			}
		});
		return f;
	}
	
	public void setValue(int d){
		super.setValue(d);
		((JTextField)getJComponent()).setText("" + d);
		
	}

}
