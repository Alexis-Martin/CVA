package gui.graphui.listener;

import gui.graphui.GSGraphicGraph;

import java.awt.event.MouseEvent;

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
	private static int id = 0;
	public GSGraphicGraphMouseListener(){
		
	}
	public GSGraphicGraphMouseListener(GSGraphicGraph gsGraphicGraph) {
		this.gs = gsGraphicGraph;
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

		View view = (View) arg0.getComponent();		
		int x = arg0.getX();
		int y = arg0.getY(); 
		GraphicElement graphicElement = view.findNodeOrSpriteAt(x, y);
		if(graphicElement!=null){
			view.freezeElement(graphicElement, true);
		}
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub

		int x = arg0.getX();
		int y = arg0.getY(); 
		View view = (View) arg0.getComponent();
		GraphicElement graphicElement = view.findNodeOrSpriteAt(x, y);
		if(graphicElement!=null){

			Node node_selected = this.gs.getGraphstreamGraph().getNode(graphicElement.getId());
			if(!this.s_node.equals("")){

				Node old_node_select = this.gs.getGraphstreamGraph().getNode(s_node);
				old_node_select.removeAttribute("ui.style");
				old_node_select.addAttribute("ui.class", "default");
			}
			Node node_select = this.gs.getGraphstreamGraph().getNode(node_selected.getId());
			s_node = node_selected.getId();
			s_style = node_select.getAttribute("ui.class");
			node_select.addAttribute("ui.class","select");
			
		}
		else{

			if(!this.s_node.equals("")){

				Node old_node_select = this.gs.getGraphstreamGraph().getNode(s_node);
				old_node_select.removeAttribute("ui.style");
				old_node_select.addAttribute("ui.class", "default");
			}
			id++;
			
			
			
		}	
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

}
