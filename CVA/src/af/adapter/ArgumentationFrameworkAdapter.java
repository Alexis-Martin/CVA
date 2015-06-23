package af.adapter;

import java.util.Collection;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.MultiGraph;

import af.Relation;
import af.ArgumentationFramework;
import af.Argument;
import af.GSArgumentationFramework;
import af.GSArgument;

public class ArgumentationFrameworkAdapter {
	static int id = 0;
	/**
	 * 
	 * @param g A graphstream Graph Object
	 * @return ArgumentationFramwork corresponding to g
	 */
	public static ArgumentationFramework graphstreamToAGraph(Graph g){
		GSArgumentationFramework agraph = new GSArgumentationFramework(g.getId()+"_"+id);
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
	/**
	 * 
	 * @param g ArgumentationFramework object to transform into graphstream Object 
	 * @param name name of the Graph
	 * @return graphstream Multigraph
	 */
	public static Graph agraphToGraphstream(ArgumentationFramework g, String name){
		MultiGraph gstream = new MultiGraph(name+"_"+id);
		id++;
		gstream.setStrict(false);
		for(Argument arg : g.getArguments()){
			Node node = gstream.addNode(arg.getId());
			node.addAttribute("utility", arg.getUtility());
		}
		for(Relation e : g.getRelations()){
			Edge edge = gstream.addEdge(e.getSource().getId()+"_to_"+e.getTarget().getId(),e.getSource().getId(), e.getTarget().getId(), true);
			edge.addAttribute("role", e.getRole());
		}
		return gstream;
	}
}
