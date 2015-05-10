package graph.adapter;

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
		MultiGraph gstream = new MultiGraph(name+"_"+id);
		id++;
		gstream.setStrict(false);
		Collection<Argument> args = g.getArguments();
		for(Argument arg : args){
			Node node = gstream.addNode(arg.getId());
			node.addAttribute("utility", arg.getUtility());

		}
		for(Argument arg :args){
			Collection<Argument> attackers = arg.getAttackers();
			Collection<Argument> defenders = arg.getDefenders();
			for(Argument argAttackers : attackers){
				Edge edge = gstream.addEdge(argAttackers.getId()+"_to_"+arg.getId(),argAttackers.getId(), arg.getId(), true );
				edge.addAttribute("role", "attack");
				
			}
			for(Argument argDefenders : defenders){
				Edge edge = gstream.addEdge(argDefenders.getId()+"_to_"+arg.getId(),argDefenders.getId(), arg.getId(), true );
				edge.addAttribute("role", "defend");
				
			}
		}
		//NOT finish
		
		//BUILDING NODES
		return gstream;
	}
	public static Graph agraphToGraphstream(AGraph g, String name,boolean saveResult){
		MultiGraph gstream = new MultiGraph(name+"_"+id);
		gstream.setStrict(false);
		id++;
		Collection<Argument> args = g.getArguments();
		for(Argument arg : args){
			Node node = gstream.addNode(arg.getId());
			if(saveResult)
				node.addAttribute("utility", arg.getUtility());
			else
				node.addAttribute("utility", 0.0);

		}
		for(Argument arg :args){
			Collection<Argument> attackers = arg.getAttackers();
			Collection<Argument> defenders = arg.getDefenders();
			for(Argument argAttackers : attackers){
				Edge edge = gstream.addEdge(argAttackers.getId()+"_to_"+arg.getId(),argAttackers.getId(), arg.getId(), true );
				edge.addAttribute("role", "attack");
				
			}
			for(Argument argDefenders : defenders){
				Edge edge = gstream.addEdge(argDefenders.getId()+"_to_"+arg.getId(),argDefenders.getId(), arg.getId(), true );
				edge.addAttribute("role", "defend");
				
			}
		}
		//NOT finish
		
		//BUILDING NODES
		return gstream;
	}

}