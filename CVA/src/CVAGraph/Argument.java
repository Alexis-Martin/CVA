package CVAGraph;

import java.util.Collection;
import java.util.Iterator;


public interface Argument {
	
	public String getId();
	
	public void setUtility(double utility);
	
	public double getUtility();
	public Iterator<Argument> getNeighbor();
	
	public Collection<Argument> getAttackers();
	public Collection<Argument> getDefenders();

}
