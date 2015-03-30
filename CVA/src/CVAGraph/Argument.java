package CVAGraph;

import java.util.Collection;
import java.util.Iterator;


public interface Argument {
	public void setUtility(double utility);
	
	public double getUtility();
	public Iterator<Argument> getNeighbor();

}
