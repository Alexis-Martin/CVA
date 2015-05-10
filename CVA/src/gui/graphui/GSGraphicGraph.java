package gui.graphui;

import graph.AGraph;
import graph.Argument;
import graph.adapter.AGraphAdapter;
import gui.graphui.listener.GSGraphicGraphMouseListener;

import java.awt.Component;
import java.util.Collection;
import java.util.List;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.ui.swingViewer.ViewPanel;
import org.graphstream.ui.view.View;
import org.graphstream.ui.view.Viewer;
import org.graphstream.ui.view.ViewerListener;
import org.graphstream.ui.view.ViewerPipe;

public class GSGraphicGraph extends Thread implements IGraphicGraph, ViewerListener {
    protected boolean loop = true;
	private AGraph graph;
	private Graph graphstream;
	public Viewer viewer;
	private int minimumNodeSize;
	private int maximumNodeSize;
	private ViewerPipe fromViewer;
	private static int id = 0;
	private String s_node = "";
	private String s_style = "";
	private String f_node = "";
	private ViewPanel view;
	private GSGraphicGraphMouseListener GSGGML;
	private boolean edit = false;
    public GSGraphicGraph(AGraph graph) {

    	this.graph = graph;
    	this.graphstream = AGraphAdapter.agraphToGraphstream(graph,"graph_adapter_"+id);
    	id++;
        this.viewer =  new Viewer(this.graphstream, Viewer.ThreadingModel.GRAPH_IN_ANOTHER_THREAD);
        this.viewer.enableAutoLayout();
        this.viewer.setCloseFramePolicy(Viewer.CloseFramePolicy.HIDE_ONLY);
        this.setDefaultCSS("style/default.css");

        this.setMinimumNodeSize(10);
        this.setMaximumNodeSize(30);
        System.out.println("constructeur");
        this.updateStyle();
     //   this.fromViewer = viewer.newViewerPipe();
     //   fromViewer.addViewerListener(this);
      //  fromViewer.addSink(this.graphstream);


    }

 
    public void viewClosed(String id) {
        loop = false;
    }
    public void run() {
   /* 	while(true){
    			try {
    				fromViewer.blockingPump();
    			
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				};
    	}*/
    	return;

    }
	@Override
	public Component getGraphicGraphComponent() {
		this.view = viewer.addDefaultView(false);
		this.GSGGML = new GSGraphicGraphMouseListener(this);
		GSGGML.init(this.viewer.getGraphicGraph(), this.view);
		view.addMouseListener(GSGGML);
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
	/**
	 * define if the user can move nodes
	 * @param positionChange 
	 */
	public void setPositionChange(boolean positionChange){

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
		
		//we determine an affine function for the size of the node aX+b 
		double a = 0;
		double b = (this.maximumNodeSize+this.minimumNodeSize)/2;
		if(min!=max){
			a = ((double)this.minimumNodeSize-(double)this.maximumNodeSize)/(min-max);
			b = ((double)this.minimumNodeSize)-a*min; 
		}

		Collection<Node> nodes = this.graphstream.getNodeSet();
		for(Node node: nodes){
			Double sizeNode = new Double( a*(double)node.getAttribute("utility")+b);
			node.addAttribute("ui.style","size:"+sizeNode.intValue()+";" );
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
		System.out.println("button pushed "+arg0);
	}


	public void buttonReleased(String arg0) {
		if(!s_node.equals("")){
			System.out.println("ICI "+s_node+" "+s_style);
			Node old_node_select = this.graphstream.getNode(s_node);
			old_node_select.removeAttribute("ui.style");
			old_node_select.addAttribute("ui.class", "default");
		}
		Node node_select = this.graphstream.getNode(arg0);
		s_node = arg0;
		s_style = node_select.getAttribute("ui.class");
		node_select.addAttribute("ui.class","select");
		System.out.println("button released "+arg0);
		
	}
	public void switchEditMode(){
		this.edit = !this.edit;
		if(this.edit){
			this.GSGGML.setActive(true);
			this.setAutomaticTopology(false);
		}
		else{
			this.GSGGML.setActive(false);
			this.setAutomaticTopology(true);
		}
	}
	public Graph getGraphstreamGraph(){
		return this.graphstream;
	}
}