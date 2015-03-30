package CVAGraph;

import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.MultiGraph;
import org.graphstream.stream.file.FileSinkDGS;
import org.graphstream.stream.file.FileSinkDOT;
import org.graphstream.stream.file.FileSource;
import org.graphstream.stream.file.FileSourceFactory;

public class GSAGraph implements AGraph{
	int nodeId = 0;
	int edgeId = 0;
	MultiGraph cvaGraph;
	public GSAGraph(String id) {
		this.cvaGraph =  new MultiGraph(id);
		cvaGraph.setNodeFactory(new GSArgumentFactory());
		this.cvaGraph.addAttribute("ui.stylesheet", "url(style/default.css)");
		this.cvaGraph.addAttribute("ui.quality");
		this.cvaGraph.addAttribute("ui.antialias");
		
	}
	public GSAGraph(GSAGraph g){
		
		this.cvaGraph = g.cvaGraph;
		
		
	}
	public GSAGraph(String id, String path)throws IOException{
		this.cvaGraph = new MultiGraph(id);
		this.cvaGraph.addAttribute("ui.stylesheet", "url(style/default.css)");
		this.cvaGraph.addAttribute("ui.quality");
		this.cvaGraph.addAttribute("ui.antialias");
		
	}
	
	/**
	 * 
	 * @param description The Argument description, if null no description is set
	 *
	 * @return The Id of the node
	 */
	public GSArgument addArgument(){
		return addArgument(null);
	}
	
	public GSArgument addArgument(String description){
		
		GSArgument node = cvaGraph.addNode(""+nodeId);
		nodeId++;
		
		if(description!=null)
			node.addAttribute("description", description);
		
		
		return node;
	}
	
	
	
	
	
	public String addAttack(String nodeIdA, String nodeIdB){
		Edge edge = this.cvaGraph.addEdge(""+this.edgeId, nodeIdA, nodeIdB, true);
		this.edgeId++;
		edge.setAttribute("ui.class", "attack");
		edge.setAttribute("role", "attack");
		return edge.getId();
	}
	public String addDefense(String nodeIdA, String nodeIdB){
		Edge edge = this.cvaGraph.addEdge(""+this.edgeId, nodeIdA, nodeIdB, true);
		this.edgeId++;
		edge.setAttribute("ui.class", "defend");
		edge.setAttribute("role", "defend");
		return edge.getId();
	}
	/**
	 * Tool visualization of the graph into a separate window
	 **/
	public void quickVisualisation(){
		cvaGraph.display();
	}
	
	public Graph getGraph(){
		return this.cvaGraph;
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Collection<Argument> getArguments() {
		return (Collection) cvaGraph.getNodeSet();
	}
	@Override
	public void setArgumentAttribute(String idArgument, String nameAttribute,
			Object valueAttribute) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setDefenseAttribute(String idArgument, String nameAttribute,
			Object valueAttribute) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setAttackAttribute(String idArgument, String nameAttribute,
			Object valueAttribute) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public Object getArgumentAttribute(String idArgument, String nameAttribute) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Object getDefenseAttribute(String idArgument, String nameAttribute) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Object getAttackAttribute(String idArgument, String nameAttribute) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Object getUtility(String idArgument, double utility) {
		// TODO Auto-generated method stub
		return null;
	}

	
}