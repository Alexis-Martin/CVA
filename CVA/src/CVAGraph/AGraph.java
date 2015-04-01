package CVAGraph;

import java.util.Collection;
import java.util.List;


import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;

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

	
	/**
	 * accesseurs pour les différents attributs
	 * @param idArgument
	 * @param nameAttribute
	 * @param valueAttribute
	 */
	public void setArgumentAttribute(String idArgument, String nameAttribute, Object valueAttribute);

	public void setDefenseAttribute(String idArgument, String nameAttribute, Object valueAttribute);

	public void setAttackAttribute(String idArgument, String nameAttribute, Object valueAttribute);
	
	public Object getArgumentAttribute(String idArgument, String nameAttribute);

	public Object getDefenseAttribute(String idArgument, String nameAttribute);

	public Object getAttackAttribute(String idArgument, String nameAttribute);
	
	public Object getUtility(String idArgument, double utility);
	
	public void quickVisualisation();
	

}
