package graph;

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
	public String getRole() {
		return (String)this.getAttribute("role");
	}

	@Override
	public boolean isAttack() {
		if(getRole().equals("attack"))
			return true;
		return false;
	}

	@Override
	public boolean isDefend() {
		if(getRole().equals("defend"))
			return true;
		return false;
	}

}
