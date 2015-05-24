package af;

import java.util.Collection;
import java.util.List;

public interface ArgumentationFramework {

	public Argument addArgument();
	public Argument addArgument(String description);	
	public Argument addArgument(String id, String description);
	
	public Relation addAttack(String argumentId1, String argumentId2);
	public Relation addAttack(Argument argument1, Argument argument2);
	public Relation addAttack(String id, String argumentId1, String argumentId2);
	
	public Relation addDefense(String argumentId1, String argumentId2);
	public Relation addDefense(Argument argument1, Argument argument2);
	public Relation addDefense(String id, String argumentId1, String argumentId2);
	public Collection<Argument> getArguments();
	public Collection<Relation> getRelations();
	public List<Argument> getUtilities();
	
	public void removeArgument(String id);
	public void removeArgument(Argument arg);
	public void removeAEdge(String id);
	public Argument getArgument(String argument);

	public Collection<Argument> getArgumentsWithoutWeight();
	
	public void setAttr(String attribute, Object values);
	public Object getAttr(String attribute);
	public void removeAttr(String attribute);

}
