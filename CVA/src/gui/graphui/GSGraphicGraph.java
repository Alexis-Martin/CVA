package gui.graphui;

import graph.AEdge;
import graph.AGraph;
import graph.Argument;
import graph.adapter.AGraphAdapter;
import gui.graphui.listener.GSGraphicGraphKeyListener;
import gui.graphui.listener.GSGraphicGraphMouseListener;
import gui.graphui.listener.SelectorListener;

import java.awt.Component;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.ui.geom.Point3;
import org.graphstream.ui.graphicGraph.GraphicEdge;
import org.graphstream.ui.graphicGraph.GraphicNode;
import org.graphstream.ui.swingViewer.ViewPanel;
import org.graphstream.ui.view.Camera;
import org.graphstream.ui.view.View;
import org.graphstream.ui.view.Viewer;
import org.graphstream.ui.view.ViewerListener;
import org.graphstream.ui.view.ViewerPipe;

import utils.Couple;
import utils.MemoryStack;

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
	private GSGraphicGraphKeyListener GSGGKL;
	public HashMap<String,Couple> positions;
	private int created_c_id = 0;
	//REMEMBER
	MemoryStack<HashSet<Argument>> removed_nodes ;
	MemoryStack<HashSet<AEdge>> removed_edges;
	MemoryStack<HashSet<Argument>> added_nodes;
	MemoryStack<HashSet<AEdge>> added_edges;	
	int current_step = -1;
	private SelectorListener sl;
	
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
    	removed_nodes = new MemoryStack<HashSet<Argument>>(100);
    	removed_edges = new MemoryStack<HashSet<AEdge>>(100);
    	added_nodes = new MemoryStack<HashSet<Argument>>(100);
    	added_edges = new MemoryStack<HashSet<AEdge>>(100);
    	positions = new HashMap<String,Couple>();

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
    public void addSelectorListener(SelectorListener sl){
    	if(GSGGML == null){
    		System.err.println("Viewer is not started, call getGraphicGraphComponent() first");
    		return;
    	}
    	else{
    		GSGGML.setSelectorListener(sl);
    	}
    	this.sl = sl;
    	
    }
	@Override
	public Component getGraphicGraphComponent() {
		this.view = viewer.addDefaultView(false);
		
		
		this.GSGGML = new GSGraphicGraphMouseListener(this);
		GSGGML.init(this.viewer.getGraphicGraph(), this.view);
		
		this.GSGGKL = new GSGraphicGraphKeyListener(this);
		GSGGML.init(this.viewer.getGraphicGraph(), this.view);
		view.addMouseListener(GSGGML);
		view.addKeyListener(GSGGKL);
		
		GSGGML.addKeyListener(GSGGKL);
		GSGGML.setSelectorListener(sl);
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
	

	public void updateStyle(){
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


	public void removeSelectedElement() {
		HashSet<String> x = this.GSGGML.getNodeSelected();
		this.GSGGML.removeSelectedNodes();
	//	HashSet<Argument> rm_n = new HashSet<Argument>();
	//	HashSet<AEdge> rm_e = new HashSet<AEdge>();	
		this.saveRemoved(x);		
		for(String arg_name : x){
			Argument arg = this.graph.getArgument(arg_name);
	//		rm_n.add(arg);
	//		rm_e.addAll(arg.getEdge());
			this.graphstream.removeNode(arg_name);
			this.graph.removeArgument(arg_name);
			
		}
		this.current_step++;
	}
	public void setSelectedAttackers(HashSet<String> nodes_att){
		
		HashSet<String> x = this.GSGGML.getNodeSelected();
		HashSet<AEdge> edges = new HashSet<AEdge>();
		//this.GSGGML.removeSelectedNodes();

		for(String s_node_attacked : nodes_att ){
			Argument node_attacked = this.graph.getArgument(s_node_attacked);
			for(String arg_name : x){
				Argument arg = this.graph.getArgument(arg_name);
				if(arg.getAttack(node_attacked.getId()) == null){
					AEdge edge = this.graph.addAttack(arg.getId(), node_attacked.getId());	
					Edge graphstream_edge = this.graphstream.addEdge(edge.getId(),arg.getId(),node_attacked.getId(), true);
					graphstream_edge.addAttribute("ui.class", "attack");
					graphstream_edge.addAttribute("role", "attack");
					edges.add(edge);
				}
				
			}
		}
		this.added_edges.push(edges);
		this.added_nodes.push(new HashSet<Argument>());
		this.removed_edges.push(new HashSet<AEdge>());
		this.removed_nodes.push(new HashSet<Argument>());
		
	}
	public void previous_step(){
		if(!this.removed_edges.has_previous())
			return;
		this.GSGGML.removeSelectedNodes();
		this.setOldStep(current_step);
		this.current_step --;
	}
	public void next_step(){
		if(!this.removed_edges.has_next())
			return;
		this.GSGGML.removeSelectedNodes();
		this.setNewStep(current_step+1);
		this.current_step ++;		
	}
	private void saveRemoved(Collection<String> args){
	
	  	HashSet<Argument> rm_n = new HashSet<Argument>();
		HashSet<AEdge> rm_e = new HashSet<AEdge>();
		for(String arg : args){
			GraphicNode gn = viewer.getGraphicGraph().getNode(arg);
			
			System.out.println("X = "+gn.getX()+" Y="+gn.getY());
			Couple coor = new Couple(gn.getX(),gn.getY());
			this.positions.put(arg,coor);
			rm_n.add(this.graph.getArgument(arg));
			rm_e.addAll(this.graph.getArgument(arg).getEdge());
		}
		this.removed_edges.push(rm_e);
		this.removed_nodes.push(rm_n);
		this.added_edges.push(new HashSet<AEdge>());
		this.added_nodes.push(new HashSet<Argument>());
	}
	public void newNode(int x, int y){
		Argument arg = this.graph.addArgument();
		HashSet<Argument> args = new HashSet<Argument>();
		args.add(arg);

		Node node = this.graphstream.addNode(arg.getId());
		node.addAttribute("utility", arg.getUtility());
		Camera camera = this.view.getCamera();
		Point3 point = camera.transformPxToGu(x, y); 
		Couple coor = new Couple(point.x,point.y);
		this.positions.put(arg.getId(),coor);
		node.setAttribute("x", this.positions.get(arg.getId()).getL());
		node.setAttribute("y", this.positions.get(arg.getId()).getR());
		this.added_nodes.push(args);
		this.added_edges.push(new HashSet<AEdge>());
		this.removed_edges.push(new HashSet<AEdge>());
		this.removed_nodes.push(new HashSet<Argument>());
		this.current_step++;
		
	}
	
	private void setOldStep(int i){
	  	HashSet<Argument> rm_n = this.removed_nodes.get_previous();
		HashSet<AEdge> rm_e = this.removed_edges.get_previous();
		HashSet<Argument> add_n = this.added_nodes.get_previous();
		HashSet<AEdge> add_e = this.added_edges.get_previous();
		this.addNodes(rm_n);
		this.addEdges(rm_e);
		this.removeEdges(add_e);
		this.removeNodes(add_n);

		
	}
	private void setNewStep(int i){
	  	HashSet<Argument> rm_n = this.removed_nodes.get_next();
		HashSet<AEdge> rm_e = this.removed_edges.get_next();
		HashSet<Argument> add_n = this.added_nodes.get_next();
		HashSet<AEdge> add_e = this.added_edges.get_next();
		this.removeEdges(rm_e);
		this.removeNodes(rm_n);
		this.addNodes(add_n);
		this.addEdges(add_e);
	}
	private void removeNodes(Collection<Argument> args){
		for(Argument arg :args){
			this.graph.removeArgument(arg.getId());
			this.graphstream.removeNode(arg.getId());
		}
	}
	private void removeEdges(Collection<AEdge> edges){
		for(AEdge edge : edges){
			this.graph.removeAEdge(edge.getId());
			this.graphstream.removeEdge(edge.getId());
		}
	}
	private void addNodes(Collection<Argument> args){
		for(Argument arg :args){
			Node node = this.graphstream.addNode(arg.getId());
			node.addAttribute("utility", arg.getUtility());
			this.graph.addArgument(arg.getId(), arg.getDescription());
			node.setAttribute("x", this.positions.get(arg.getId()).getL());
			node.setAttribute("y", this.positions.get(arg.getId()).getR());
			System.out.println(this.positions.get(arg.getId()).getR());
		}
	}
	private void addEdges(Collection<AEdge> edges){
		for(AEdge edge : edges){
			String role = edge.getRole();
			if(role.equals("attack")){
				graph.addAttack(edge.getId(),edge.getSource().getId(), edge.getTarget().getId());
				Edge edge_gs = graphstream.addEdge(edge.getId(),edge.getSource().getId(), edge.getTarget().getId(),true);
				edge_gs.setAttribute("ui.class", "attack");
				edge_gs.setAttribute("role", "attack");	
				edge_gs.setAttribute("ui.label", edge.getId());
			}
			else if(role.equals("defend")){
				Edge edge_gs = graphstream.addEdge(edge.getId(),edge.getSource().getId(), edge.getTarget().getId(),true);
				edge_gs.setAttribute("ui.class", "defend");
				edge_gs.setAttribute("role", "defend");
			}
			
		}
	}
}