package graph;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.MultiGraph;

public class GSAGraph implements AGraph{
	int nodeId = 0;
	int edgeId = 0;
	MultiGraph cvaGraph;
	public GSAGraph(String id) {
		this.cvaGraph =  new MultiGraph(id);
		cvaGraph.setNodeFactory(new GSArgumentFactory());
		cvaGraph.setEdgeFactory(new GSAEdgeFactory());
		
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

	
	public GSArgument addArgument(){
		nodeId++;
		return addArgument(nodeId+"");
	}
	
	public GSArgument addArgument(String id){
		return addArgument(id, null);
	}
	
	public GSArgument addArgument(String id, String description){
		if(cvaGraph.getNode(id) != null){
			return null;
		}
		
		GSArgument node = cvaGraph.addNode(id);
		node.setUtility(0.0);
		
		if(description!=null)
			node.addAttribute("description", description);

		return node;
	}
	
	
	
	
	public GSAEdge addAttack(String nodeIdA, String nodeIdB){
		GSAEdge edge = this.cvaGraph.addEdge(""+this.edgeId, nodeIdA, nodeIdB, true);
		this.edgeId++;
		edge.setAttribute("ui.class", "attack");
		edge.setAttribute("role", "attack");
		return edge;
	}
	public GSAEdge addDefense(String nodeIdA, String nodeIdB){
		GSAEdge edge = this.cvaGraph.addEdge(""+this.edgeId, nodeIdA, nodeIdB, true);
		this.edgeId++;
		edge.setAttribute("ui.class", "defend");
		edge.setAttribute("role", "defend");
		return edge;
	}
	
	@Override
	public GSAEdge addAttack(Argument argument1, Argument argument2) {

		return addAttack(argument1.getId(), argument2.getId());
	}
	@Override
	public GSAEdge addDefense(Argument argument1, Argument argument2) {
		
		return addDefense(argument1.getId(), argument2.getId());
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
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Collection<AEdge> getRelations() {
		return (Collection) cvaGraph.getEdgeSet();
	}

	@Override
	public List<Argument> getUtilities() {
		Iterator<GSArgument> it = cvaGraph.getNodeIterator();
		List<Argument> a = new ArrayList<Argument>();
		while(it.hasNext()){
			Argument arg = it.next();
			int position = 0;
			for(int i = 0; i < a.size(); i++){
				if(a.get(i).getUtility() >= arg.getUtility()){
					position = i+1;
				}
				else
					break;
			}
			a.add(position, arg);
		}
		return a;
	}
	@Override
	public void removeArgument(String id) {
		this.cvaGraph.removeNode(id);
		
	}
	@Override
	public void removeArgument(Argument arg) {
		this.cvaGraph.removeNode((Node)arg);
		
	}
	@Override
	public Argument getArgument(String argument) {
		return this.cvaGraph.getNode(argument);
	}
	@Override
	public AEdge addAttack(String id, String argumentId1, String argumentId2) {
		GSAEdge edge = this.cvaGraph.addEdge(id , argumentId1, argumentId2, true);
		edge.setAttribute("ui.class", "attack");
		edge.setAttribute("role", "attack");
		this.edgeId++;
		return edge;
	}
	@Override
	public AEdge addDefense(String id, String argumentId1, String argumentId2) {
		GSAEdge edge = this.cvaGraph.addEdge(id , argumentId1, argumentId2, true);
		edge.setAttribute("ui.class", "defend");
		edge.setAttribute("role", "defend");
		this.edgeId++;
		return edge;
	}
	@Override
	public void removeAEdge(String id) {
		this.cvaGraph.removeEdge(id);
		
	}
	@Override
	public Collection<Argument> getArgumentsWithoutWeight() {
		Iterator<Argument> it = getArguments().iterator();
		Collection<Argument> args = new HashSet<Argument>();
		while(it.hasNext()){
			Argument arg = it.next();
			if(!arg.hasWeight())
				args.add(arg);
		}
		return args;
	}
	

	
}