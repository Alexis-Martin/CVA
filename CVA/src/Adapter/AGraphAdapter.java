package Adapter;

import java.util.Collection;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.MultiGraph;

import CVAGraph.AGraph;
import CVAGraph.Argument;
import CVAGraph.GSAGraph;
import CVAGraph.GSArgument;

public class AGraphAdapter {
	public static AGraph graphstreamToAGraph(Graph g){
		GSAGraph agraph = new GSAGraph(g.getId());

		
		//BUILDING NODES
		Collection<Node> nodes = g.getNodeSet();
		for(Node node :  nodes){
		
			GSArgument arg = agraph.addArgument(node.getId(), (String) node.getAttribute("description"));
			Double utility = node.getAttribute("utility");
			if( utility!=null){
				arg.setUtility(utility);
			}

		}
		//BUILDING EDGE
		Collection<Edge> edges = g.getEdgeSet();
		for(Edge edge :  edges){
			String role = edge.getAttribute("role");
			edge.getSourceNode();
			String source = edge.getSourceNode().getId();
			String target = edge.getTargetNode().getId();
			if(role.equals("attack")){
				agraph.addAttack(source, target);
			}
			else if(role.equals("defend")){
				agraph.addDefense(source, target);
			}

		}
		return agraph;
	}	
	public static Graph agraphToGraphstream(AGraph g, String name){
		MultiGraph gstream = new MultiGraph(name);
		Collection<Argument> args = g.getArguments();
		for(Argument arg : args){
			Node node = gstream.addNode(arg.getId());
			node.addAttribute("utility", arg.getUtility());
			
		}
		//NOT finish
		
		//BUILDING NODES
		return gstream;
	}
}
