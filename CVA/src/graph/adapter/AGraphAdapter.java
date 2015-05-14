package graph.adapter;

import graph.AEdge;
import graph.AGraph;
import graph.Argument;
import graph.GSAGraph;
import graph.GSArgument;

import java.util.Collection;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.MultiGraph;

public class AGraphAdapter {
	static int id = 0;
	public static AGraph graphstreamToAGraph(Graph g){
		GSAGraph agraph = new GSAGraph(g.getId()+"_"+id);
		id++;
		
		//BUILDING NODES
		Collection<Node> nodes = g.getNodeSet();
		for(Node node :  nodes){
			GSArgument arg = agraph.addArgument(node.getId());
			Double utility = (Double) node.getAttribute("utility");
			String descr = (String) node.getAttribute("description");
			if( utility!=null){
				arg.setUtility(utility);
			}
			if(utility != null){
				arg.setDescription(descr);
			}
		}
		
		//BUILDING EDGE
		Collection<Edge> edges = g.getEdgeSet();
		for(Edge edge :  edges){
			String source = edge.getSourceNode().getId();
			String target = edge.getTargetNode().getId();
			String role = (String) edge.getAttribute("role");
			if(role == null || role.equals("attack")){
				agraph.addAttack(source, target);
			}else if(role.equals("defend")){
				agraph.addDefense(source, target);
			}else{
				agraph.addAttack(source, target); //Au cas ou..
			}
		}
		return agraph;
	}	
	public static Graph agraphToGraphstream(AGraph g, String name){
		MultiGraph gstream = new MultiGraph(name+"_"+id);
		id++;
		gstream.setStrict(false);
		for(Argument arg : g.getArguments()){
			Node node = gstream.addNode(arg.getId());
			node.addAttribute("utility", arg.getUtility());
		}
		for(AEdge e : g.getRelations()){
			Edge edge = gstream.addEdge(e.getSource().getId()+"_to_"+e.getTarget().getId(),e.getSource().getId(), e.getTarget().getId(), true);
			edge.addAttribute("role", e.getRole());
		}
		return gstream;
	}
}
