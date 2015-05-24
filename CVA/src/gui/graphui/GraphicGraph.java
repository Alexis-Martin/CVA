package gui.graphui;

import gui.graphui.listener.SelectorListener;

import java.awt.Component;

import af.ArgumentationFramework;

public interface GraphicGraph {
	
	public Component getGraphicGraphComponent();
	public ArgumentationFramework getAGraph();
	public void refresh();
	public void replace();
	public void addSelectorListener(SelectorListener sl);
	public void setAutomaticTopology(boolean automatic);
}
