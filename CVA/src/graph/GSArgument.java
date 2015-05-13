package graph;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;

import org.graphstream.graph.implementations.AbstractGraph;
import org.graphstream.graph.implementations.SingleNode;

public class GSArgument extends SingleNode implements Argument{

	public GSArgument(AbstractGraph graph, String id) {
		super(graph, id);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void setUtility(double utility) {
		this.addAttribute("utility", utility);	
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Iterator<Argument> getNeighbor() {
		return (Iterator)super.getNeighborNodeIterator();
	}

	@Override
	public double getUtility() {
		return (Double)this.getAttribute("utility");
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Collection<Argument> getAttackers() {
		Collection<GSAEdge> edges = (Collection)this.getEnteringEdgeSet();
		Collection<Argument> ret = new HashSet<Argument>();
		for(GSAEdge edge : edges){
			if(edge.isAttack())
				ret.add((Argument) edge.getSourceNode());
		}
		return ret;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Collection<Argument> getDefenders() {
		Collection<GSAEdge> edges = (Collection)this.getEnteringEdgeSet();
		Collection<Argument> ret = new HashSet<Argument>();
		for(GSAEdge edge : edges){
			if(edge.isDefend())
				ret.add((Argument) edge.getSourceNode());
		}
		return ret;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Collection<AEdge> getEdge() {
		Collection<GSAEdge> x = this.getEdgeSet();
		return (Collection)x;
	}

	@Override
	public String getDescription() {
		return "";
	}
	

}
