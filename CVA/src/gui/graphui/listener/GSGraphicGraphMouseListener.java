package gui.graphui.listener;

import graph.Argument;
import gui.graphui.GSGraphicGraph;

import java.awt.GraphicsEnvironment;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Collection;
import java.util.HashSet;

import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.ui.graphicGraph.GraphicElement;
import org.graphstream.ui.graphicGraph.GraphicGraph;
import org.graphstream.ui.view.View;
import org.graphstream.ui.view.util.DefaultMouseManager;
import org.graphstream.ui.view.util.MouseManager;

public class GSGraphicGraphMouseListener implements MouseManager{
	private GSGraphicGraph gs;
	private String s_node="";
	private String s_style="";
	private boolean active;
	private static int id = 0;
	private HashSet<String> nodesSelected;
	private boolean is_pressed = false;
	private int s_x, s_y;
	private GSGraphicGraphKeyListener  kl= null;
	public GSGraphicGraphMouseListener(){
		
	}
	public GSGraphicGraphMouseListener(GSGraphicGraph gsGraphicGraph) {
		this.gs = gsGraphicGraph;
		this.nodesSelected = new HashSet<String>();
	}
	public void addKeyListener(GSGraphicGraphKeyListener kl){
		this.kl = kl;
	}
	@Override
	public void mouseClicked(MouseEvent arg0) {

	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		if(!active)
			return;
		View view = (View) arg0.getComponent();		
		this.s_x = arg0.getX();
		this.s_y = arg0.getY(); 
		
		this.is_pressed = true;
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		if(!active)
			return;
		int x = arg0.getX();
		int y = arg0.getY(); 
		View view = (View) arg0.getComponent();
		
		System.out.println(s_x+","+s_y+","+x+","+y);
		if(x<s_x){
			int mem = x ;
			x = s_x;
			s_x = mem;
		}
		if(y<s_y){
			int mem = y ;
			y = s_y;
			s_y = mem;
		}
		Collection<GraphicElement> graphicElements = view.allNodesOrSpritesIn(s_x, s_y, x, y);

		System.out.println(graphicElements);
		if(kl.getKeyPressed() == KeyEvent.VK_CONTROL){
			this.disUnion(graphicElements);
		}
		else if(kl.getKeyPressed() == KeyEvent.VK_A){
			this.gs.setSelectedAttackers(this.getNodesSet(graphicElements));
		}
		else{
			this.removeSelectedNodes();
			this.addSelectedNodes(graphicElements);
		}

		this.routine_release();
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void init(GraphicGraph arg0, View arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void release() {
		// TODO Auto-generated method stub
		
	}
	public void setActive(boolean b) {
		this.removeSelectedNodes();
		this.active = b;
		
	}
	private void addSelectedNodes(Collection<GraphicElement> ges){
		for(GraphicElement ge : ges){
			if(this.gs.getGraphstreamGraph().getNode(ge.getId())!=null){
				this.addNodeSelected(ge.getId());
			}
		}
	}
	private void removeSelectedNodes(Collection<GraphicElement> ges){
		for(GraphicElement ge : ges){
			if(this.gs.getGraphstreamGraph().getNode(ge.getId())!=null){
				this.removeNodeSelected(ge.getId());
			}
		}
		
	}
	private void disUnion(Collection<GraphicElement> ges){
		for(GraphicElement ge : ges){
			if(this.gs.getGraphstreamGraph().getNode(ge.getId())!=null){
				if(this.nodesSelected.contains(ge.getId())){
					this.removeNodeSelected(ge.getId());
				}
				else{
					this.addNodeSelected(ge.getId());
				}
			}
		}	
	}
	private void routine_release(){
		this.is_pressed = false;
	}
	public void removeSelectedNodes(){
		Graph graph = this.gs.getGraphstreamGraph();
		for(String s_node  : nodesSelected){
			graph.getNode(s_node).setAttribute("ui.class", "default");
		}
		this.nodesSelected = new HashSet<String>();
	}
	private void removeNodeSelected(String node){
		Graph graph = this.gs.getGraphstreamGraph();
		graph.getNode(node).setAttribute("ui.class", "default");
		this.nodesSelected.remove(node);
	}
	private void addNodeSelected(String node){
		Graph graph = this.gs.getGraphstreamGraph();
		graph.getNode(node).addAttribute("ui.class", "select");
		this.nodesSelected.add(node);
	}
	public HashSet<String> getNodeSelected(){
		return this.nodesSelected;
	}

	private HashSet<String> getNodesSet(Collection<GraphicElement> ges){
		HashSet<String> node_set = new HashSet<String>();
		for(GraphicElement ge : ges){
			if(this.gs.getGraphstreamGraph().getNode(ge.getId())!=null){
				node_set.add(ge.getId());
			}
		}	
		return node_set;
	}
	
}
