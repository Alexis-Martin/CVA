package graph;

public interface AEdge {
	public final static String ROLE_ATTACK = "attack";
	public final static String ROLE_DEFEND = "defend";
	
	public void setRole(String role);
	public String getRole();
	public boolean isAttack();
	public boolean isDefend();
	public Argument getSource();
	public Argument getTarget();
	public String getId();
	
	public void setAttr(String attribute, Object values);
	public Object getAttr(String attribute);
	public void removeAttr(String attribute);
}
