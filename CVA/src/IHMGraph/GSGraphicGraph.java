package IHMGraph;

import java.awt.Component;
import java.awt.LayoutManager;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.swing.JPanel;

import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.ui.view.View;
import org.graphstream.ui.view.Viewer;
import org.graphstream.ui.view.ViewerPipe;

import Adapter.AGraphAdapter;
import CVAGraph.AGraph;
import CVAGraph.Argument;

public class GSGraphicGraph  implements IGraphicGraph{
    protected boolean loop = true;
	private AGraph graph;
	private Graph graphstream;
	public Viewer viewer;
	private int minimumNodeSize;
	private int maximumNodeSize;
    public GSGraphicGraph(AGraph graph) {

    	this.graph = graph;
    	this.graphstream = AGraphAdapter.agraphToGraphstream(graph,"blabla");
        this.viewer = new Viewer(this.graphstream, Viewer.ThreadingModel.GRAPH_IN_ANOTHER_THREAD);
        this.viewer.enableAutoLayout();
        this.viewer.setCloseFramePolicy(Viewer.CloseFramePolicy.HIDE_ONLY);
        this.setDefaultCSS("style/default.css");
        
        this.setMinimumNodeSize(5);
        this.setMaximumNodeSize(30);
        this.updateStyle();

    }
 
    public void viewClosed(String id) {
        loop = false;
    }

	@Override
	public Component getGraphicGraphComponent() {
		View view = viewer.addDefaultView(false);
		return (Component)view;
	}

	@Override
	public AGraph getAGraph() {
		return this.graph;
	}

	
	@Override
	public void refresh() {
		

		
	}

	@Override
	public void replace() {

		
	}
	public void setDefaultCSS(String path){
		this.graphstream.addAttribute("ui.stylesheet","url("+path+")");
	}
	/**
	 * @param define if the viewer use an automatic placement for the nodes 
	 */
	public void setAutomaticTopology(boolean automatic) {
		if(automatic)
			this.viewer.enableAutoLayout();
		else
			this.viewer.disableAutoLayout();
	}
	
	/***
	 * 
	 * @param px minimum node size
	 * Permit to choose a minimum node size in the graph viewer
	 */
	public void setMinimumNodeSize(int px){
		this.minimumNodeSize = px;
	}
	/***
	 * 
	 * @param px maximum node size
	 * Permit to choose a maximum node size in the graph viewer
	 */
	public void setMaximumNodeSize(int px){
		this.maximumNodeSize = px;		
	}
	
	
	
	private void updateStyle(){
		List<Argument> utilities = this.graph.getUtilities();
		
		double min=0;
		double max=0;
		for(int i=0;i<utilities.size();i++){

			if(i==0){
				min=utilities.get(0).getUtility();
				max=utilities.get(0).getUtility();
			}
			
			if(utilities.get(i).getUtility()<min)
				min = utilities.get(i).getUtility();
			else if(utilities.get(i).getUtility()>max)
				max = utilities.get(i).getUtility();
					
		}
		
		//we determine an affine function for the size of the node aX+b 
		double a = ((double)this.minimumNodeSize-(double)this.maximumNodeSize)/(min-max);
		double b = ((double)this.minimumNodeSize)-a*min; 
		Collection<Node> nodes = this.graphstream.getNodeSet();
		for(Node node: nodes){
			node.addAttribute("ui.style","size:"+((int)(a*(double)node.getAttribute("utility")+b))+";" );
			System.out.println(((int)(a*(double)node.getAttribute("utility")+b)));
			node.addAttribute("ui.style", "text-color:white;");
			node.addAttribute("ui.style", "text-background-mode:plain;");
			node.addAttribute("ui.style", "text-background-color:grey;");
			node.addAttribute("ui.style", "stroke-mode: plain;");
			node.addAttribute("ui.style", "stroke-color: black;");
			node.addAttribute("ui.style", "stroke-width: 5;");
			node.addAttribute("ui.label", (double)node.getAttribute("utility"));
			
		}
		
	}
}
