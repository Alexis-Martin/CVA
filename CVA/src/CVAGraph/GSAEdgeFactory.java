package CVAGraph;

import org.graphstream.graph.EdgeFactory;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.AbstractNode;

public class GSAEdgeFactory implements EdgeFactory<GSAEdge> {

	public GSAEdgeFactory() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public GSAEdge newInstance(String idEdge, Node src, Node dst, boolean directed) {
		// TODO Auto-generated method stub
		return new GSAEdge(idEdge, (AbstractNode)src, (AbstractNode)dst, directed);
	}

}
