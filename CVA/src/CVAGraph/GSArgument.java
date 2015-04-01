package CVAGraph;

import java.util.Collection;
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

	@Override
	public Collection<Argument> getAttackers() {
		//for(Edge)
		super.getEnteringEdgeSet();
		// TODO Auto-generated method stub
		return null;
	}
	

}
