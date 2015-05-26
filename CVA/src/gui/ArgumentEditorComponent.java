package gui;

import java.awt.BorderLayout;
import java.util.Collection;

import gui.graphui.GraphicGraph;
import gui.graphui.listener.SelectorListener;

import javax.swing.BorderFactory;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import af.ArgumentationFramework;

public class ArgumentEditorComponent extends JPanel implements SelectorListener {
	private ArgumentationFramework graph;

	public ArgumentEditorComponent(GraphicGraph igg, ArgumentationFramework graph) {
		super();
		igg.addSelectorListener(this);
		this.graph = graph;
	}

	@Override
	public void selected(Collection<String> str) {
		this.removeAll();
		if(str.size() == 1){
			String node_name = (String) str.toArray()[0];
			JPanel argsArea = new JPanel(new BorderLayout());
			JTextArea nameArea = new JTextArea();
			nameArea.setColumns(15);
			argsArea.setBorder(BorderFactory.createTitledBorder("Argument : "+node_name));
			
			this.graph.getArgument(node_name).getUtility();
			argsArea.setSize(200, 200);
			
			this.setSize(200, 400);
			this.add(nameArea);
			this.add(argsArea);
			this.setVisible(true);
			argsArea.setVisible(true);
			nameArea.setVisible(true);

			this.repaint();
		}
		else{
			this.setVisible(false);
		}
		
	}
	

}
