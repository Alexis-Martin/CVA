package graph;

import java.util.Collection;
import java.util.Iterator;


public interface Argument {
	
	public String getId();
	
	public void setUtility(double utility);
	
	public double getUtility();
	public Iterator<Argument> getNeighbor();
	
	public Collection<Argument> getAttackers();
	public Collection<Argument> getDefenders();

	public Collection<AEdge> getEdge();
	public AEdge getAttack(String id);
	public String getDescription();
	public void setDescription(String descr);

	public double getWeight();
	public void setWeight(double value);
	public boolean hasWeight();
	
	public void setAttr(String key, Object value);
	public Object getAttr(String key);
	public void removeAttr(String key);
}
