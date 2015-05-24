package graph;

import org.graphstream.graph.implementations.AbstractEdge;
import org.graphstream.graph.implementations.AbstractNode;

public class GSAEdge extends AbstractEdge implements AEdge {

	public GSAEdge(String id, AbstractNode source, AbstractNode target, boolean directed) {
		super(id, source, target, directed);
	}

	@Override
	public void setRole(String role) {
		this.addAttribute("role", role);

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

	@Override
	public Argument getSource() {
		return this.getSourceNode();
	}

	@Override
	public Argument getTarget() {
		return this.getTargetNode();
	}
	
	@Override
	public String getId(){
		return super.getId();
	}

	@Override
	public void setAttr(String attribute, Object values) {
		this.setAttribute(attribute, values);
	}

	@Override
	public Object getAttr(String attribute) {
		return this.getAttribute(attribute);
	}

	@Override
	public void removeAttr(String attribute) {
		this.removeAttribute(attribute);
	}
}
