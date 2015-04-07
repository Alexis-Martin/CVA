package IHMGraph;

import java.awt.Component;

import CVAGraph.AGraph;

public interface IGraphicGraph {
	
	public Component getGraphicGraphComponent();
	public AGraph getAGraph();
	public void refresh();
	public void replace();
	
	public void setAutomaticTopology(boolean automatic);
}
