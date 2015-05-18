package gui;

import java.util.Collection;

import gui.graphui.IGraphicGraph;
import gui.graphui.listener.SelectorListener;

import javax.swing.JPanel;

public class ArgumentEditorComponent extends JPanel implements SelectorListener {
	public ArgumentEditorComponent(IGraphicGraph igg) {
		igg.addSelectorListener(this);
	}

	@Override
	public void selected(Collection<String> str) {
		System.out.println("Element selected");
		
	}
	

}
