package af;

import java.util.Collection;
import java.util.Iterator;


public interface Argument {
	
	/**
	 *  Permit to get the Id of the argument
	 * @return id of the argument
	 */
	public String getId();
	/**
	 *  Permit to set the utility of the argument
	 * @param utility
	 */
	public void setUtility(double utility);
	
	public double getUtility();
	public Iterator<Argument> getNeighbor();
	
	public Collection<Argument> getAttackers();
	public Collection<Argument> getDefenders();

	public Collection<Relation> getEdge();
	public Relation getAttack(String id);
	public String getDescription();
	public void setDescription(String descr);

	public double getWeight();
	public void setWeight(double value);
	public boolean hasWeight();
	
	public void setAttr(String key, Object value);
	public Object getAttr(String key);
	public void removeAttr(String key);
}
