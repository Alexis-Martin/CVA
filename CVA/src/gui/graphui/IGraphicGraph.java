package gui.graphui;

import graph.AGraph;
import gui.graphui.listener.SelectorListener;

import java.awt.Component;

public interface IGraphicGraph {
	
	public Component getGraphicGraphComponent();
	public AGraph getAGraph();
	public void refresh();
	public void replace();
	public void addSelectorListener(SelectorListener sl);
	public void setAutomaticTopology(boolean automatic);
}
