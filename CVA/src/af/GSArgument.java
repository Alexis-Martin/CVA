package af;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;

import org.graphstream.graph.implementations.AbstractGraph;
import org.graphstream.graph.implementations.SingleNode;

public class GSArgument extends SingleNode implements Argument, Comparable<Argument>{

	public GSArgument(AbstractGraph graph, String id) {
		super(graph, id);
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
		Collection<GSRelation> edges = (Collection)this.getEnteringEdgeSet();
		Collection<Argument> ret = new HashSet<Argument>();
		for(GSRelation edge : edges){
			if(edge.isAttack())
				ret.add((Argument) edge.getSourceNode());
		}
		return ret;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Collection<Argument> getDefenders() {
		Collection<GSRelation> edges = (Collection)this.getEnteringEdgeSet();
		Collection<Argument> ret = new HashSet<Argument>();
		for(GSRelation edge : edges){
			if(edge.isDefend())
				ret.add((Argument) edge.getSourceNode());
		}
		return ret;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Collection<Relation> getEdge() {
		Collection<GSRelation> x = this.getEdgeSet();
		return (Collection)x;
	}

	@Override
	public String getDescription() {
		return (String) this.getAttribute("description");
	}

	@Override
	public void setDescription(String descr) {
		this.setAttribute("description", descr);
	}

	@Override
	public double getWeight() {
		return this.getAttribute("weight");
	}

	@Override
	public void setWeight(double value) {
		this.addAttribute("weight", value);
	}

	@Override
	public boolean hasWeight() {
		return this.getAttribute("weight") != null;
	}

	@Override
	public Relation getAttack(String id) {
		return this.getEdgeToward(id);
	}

	@Override
	public void setAttr(String key, Object value) {
		this.setAttribute(key, value);
	}

	@Override
	public Object getAttr(String key) {
		return this.getAttribute(key);
	}

	@Override
	public void removeAttr(String key) {
		this.removeAttribute(key);
	}

	@Override
	public int compareTo(Argument o) {
		return Double.compare(this.getUtility(), o.getUtility());
	}
	

}
