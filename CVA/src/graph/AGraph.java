package graph;

import java.util.Collection;
import java.util.List;

public interface AGraph {

	public Argument addArgument();
	public Argument addArgument(String description);	
	public Argument addArgument(String id, String description);
	
	public AEdge addAttack(String argumentId1, String argumentId2);
	public AEdge addAttack(Argument argument1, Argument argument2);
	
	public AEdge addDefense(String argumentId1, String argumentId2);
	public AEdge addDefense(Argument argument1, Argument argument2);
	public Collection<Argument> getArguments();
	public List<Argument> getUtilities();
	
	public void removeArgument(String id);
	public void removeArgument(Argument arg);
	
	public Argument getArgument(String argument);


}
