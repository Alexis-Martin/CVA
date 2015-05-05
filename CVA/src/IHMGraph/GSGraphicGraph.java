package IHMGraph;

import java.awt.Component;
import java.awt.LayoutManager;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.swing.JPanel;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.ui.view.View;
import org.graphstream.ui.view.Viewer;
import org.graphstream.ui.view.ViewerListener;
import org.graphstream.ui.view.ViewerPipe;

import Adapter.AGraphAdapter;
import CVAGraph.AGraph;
import CVAGraph.Argument;

public class GSGraphicGraph extends Thread implements IGraphicGraph, ViewerListener {
    protected boolean loop = true;
	private AGraph graph;
	private Graph graphstream;
	public Viewer viewer;
	private int minimumNodeSize;
	private int maximumNodeSize;
	private ViewerPipe fromViewer;
    public GSGraphicGraph(AGraph graph) {

    	this.graph = graph;
    	this.graphstream = AGraphAdapter.agraphToGraphstream(graph,"blabla");
        this.viewer = new Viewer(this.graphstream, Viewer.ThreadingModel.GRAPH_IN_ANOTHER_THREAD);
        this.viewer.enableAutoLayout();
        this.viewer.setCloseFramePolicy(Viewer.CloseFramePolicy.HIDE_ONLY);
        this.setDefaultCSS("style/default.css");

        this.setMinimumNodeSize(10);
        this.setMaximumNodeSize(30);
        this.updateStyle();
        this.fromViewer = viewer.newViewerPipe();
        fromViewer.addViewerListener(this);
        fromViewer.addSink(this.graphstream);


    }
 
    public void viewClosed(String id) {
        loop = false;
    }
    public void run() {
    	while(true){
    			fromViewer.pump();
    	}

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
		this.updateStyle();

		
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
		System.out.println("maj graph nodes"+utilities);
		double min=0;
		double max=0;
		for(int i=0;i<utilities.size();i++){
			this.graphstream.getNode(utilities.get(i).getId()).setAttribute("utility",utilities.get(i).getUtility() );
			if(i==0){
				min=utilities.get(0).getUtility();
				max=utilities.get(0).getUtility();
			}
			
			if(utilities.get(i).getUtility()<min)
				min = utilities.get(i).getUtility();
			else if(utilities.get(i).getUtility()>max)
				max = utilities.get(i).getUtility();
					
		}
		System.out.println(min);
		System.out.println(max);		
		
		//we determine an affine function for the size of the node aX+b 
		double a = 0;
		double b = (this.maximumNodeSize+this.minimumNodeSize)/2;
		if(min!=max){
			a = ((double)this.minimumNodeSize-(double)this.maximumNodeSize)/(min-max);
			b = ((double)this.minimumNodeSize)-a*min; 
		}

		System.out.println("fdssdfq"+(double)this.minimumNodeSize);
		Collection<Node> nodes = this.graphstream.getNodeSet();
		for(Node node: nodes){
			Double sizeNode = new Double( a*(double)node.getAttribute("utility")+b);
			node.addAttribute("ui.style","size:"+sizeNode.intValue()+";" );
			System.out.println(sizeNode );

			node.addAttribute("ui.label", (double)node.getAttribute("utility"));
			
		}
		Collection<Edge> edges = this.graphstream.getEdgeSet();
		for(Edge edge : edges){
			if(((String)edge.getAttribute("role")).equals("attack")){
				edge.setAttribute("ui.class", "attack");
			}
			else if(((String)edge.getAttribute("role")).equals("defend")){
				edge.setAttribute("ui.class", "defend");
			}
		}

		
		
	}


	public void buttonPushed(String arg0) {
		// TODO Auto-generated method stub
		System.out.println(arg0);
	}


	public void buttonReleased(String arg0) {
		// TODO Auto-generated method stub
		
	}
}