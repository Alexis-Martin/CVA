package CVAGraph;

import org.graphstream.graph.implementations.AbstractEdge;
import org.graphstream.graph.implementations.AbstractNode;

public class GSAEdge extends AbstractEdge implements AEdge {

	public GSAEdge(String id, AbstractNode source, AbstractNode target, boolean directed) {
		super(id, source, target, directed);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void setRole(String role) {
		this.addAttribute("role", role);
		// TODO Auto-generated method stub

	}

	@Override
	public String getRole(String role) {
		return (String)this.getAttribute(role);
	}

}
