package CVAGraph;

import java.io.IOException;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.MultiGraph;
import org.graphstream.stream.file.FileSinkDGS;
import org.graphstream.stream.file.FileSinkDOT;
import org.graphstream.stream.file.FileSource;
import org.graphstream.stream.file.FileSourceFactory;

public class CVAGraph{
	int nodeId = 0;
	int edgeId = 0;
	MultiGraph cvaGraph;
	public CVAGraph(String id) {
		this.cvaGraph =  new MultiGraph(id);
		this.cvaGraph.addAttribute("ui.stylesheet", "url(style/default.css)");
		this.cvaGraph.addAttribute("ui.quality");
		this.cvaGraph.addAttribute("ui.antialias");
		
	}
	public CVAGraph(CVAGraph g){
		
		this.cvaGraph = g.cvaGraph;
		
	}
	public CVAGraph(String id, String path)throws IOException{
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
	public String addArgument(String description){
		
		Node node = this.addArgumentById(""+nodeId);
		nodeId++;
		
		if(description!=null)node.addAttribute("description", description);
		
		
		return node.getId();
	}
	private Node addArgumentById(String id){
		
		return cvaGraph.addNode(id);
		
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

	
}