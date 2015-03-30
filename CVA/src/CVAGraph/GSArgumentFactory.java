package CVAGraph;

import org.graphstream.graph.Graph;
import org.graphstream.graph.NodeFactory;
import org.graphstream.graph.implementations.AbstractGraph;

public class GSArgumentFactory implements NodeFactory<GSArgument> {

	public GSArgumentFactory() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public GSArgument newInstance(String id, Graph graph) {
		// TODO Auto-generated method stub
		return new GSArgument((AbstractGraph)graph, id);
	}
	

}
