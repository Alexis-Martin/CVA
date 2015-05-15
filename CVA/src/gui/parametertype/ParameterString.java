package gui.parametertype;

import java.awt.Dimension;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JComponent;
import javax.swing.JTextField;

import algo.Parameter;

public class ParameterString extends ParameterType {

	public ParameterString(Parameter param) {
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
				setValue(((JTextField)arg0.getComponent()).getText());
			}
			
			@Override
			public void focusGained(FocusEvent arg0) {
				((JTextField)arg0.getComponent()).selectAll();
			}
		});
		return f;
	}

}
