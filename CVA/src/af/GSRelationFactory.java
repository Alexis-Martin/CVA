package af;

import org.graphstream.graph.EdgeFactory;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.AbstractNode;

public class GSRelationFactory implements EdgeFactory<GSRelation> {

	public GSRelationFactory() {
	}

	@Override
	public GSRelation newInstance(String idEdge, Node src, Node dst, boolean directed) {
		return new GSRelation(idEdge, (AbstractNode)src, (AbstractNode)dst, directed);
	}

}
