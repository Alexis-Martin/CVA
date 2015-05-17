package gui;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JProgressBar;

public class WaitExecution extends JDialog {

	JProgressBar progress;
	public WaitExecution(JFrame owner) {
		super(owner, "Patientez s'il vous plaît", true);
		this.progress = new JProgressBar();
		progress.setIndeterminate(true);
		this.getContentPane().add(progress);
		this.setSize(400, 120);
		this.setDefaultCloseOperation(this.DO_NOTHING_ON_CLOSE);
		//this.setVisible(true);
		
		
	}
	
	
	
	public void setProgress(int progress){
		this.progress.setValue(progress);
	}
	
	public void setLimit(int limit){
		this.progress.setMaximum(limit);
		this.progress.setIndeterminate(false);
	}
	
}
