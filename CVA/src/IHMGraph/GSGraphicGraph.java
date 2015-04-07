package IHMGraph;

import java.awt.Component;
import java.awt.LayoutManager;

import javax.swing.JPanel;

import org.graphstream.graph.Graph;
import org.graphstream.ui.view.View;
import org.graphstream.ui.view.Viewer;
import org.graphstream.ui.view.ViewerPipe;

import Adapter.AGraphAdapter;
import CVAGraph.AGraph;

public class GSGraphicGraph  implements IGraphicGraph{
    protected boolean loop = true;
	private AGraph graph;
	private Graph graphstream;
	public Viewer viewer;
    public GSGraphicGraph(AGraph graph) {

    	this.graph = graph;
    	this.graphstream = AGraphAdapter.agraphToGraphstream(graph,"blabla");
        this.viewer = new Viewer(this.graphstream, Viewer.ThreadingModel.GRAPH_IN_ANOTHER_THREAD);
        this.viewer.enableAutoLayout();
        this.viewer.setCloseFramePolicy(Viewer.CloseFramePolicy.HIDE_ONLY);
        this.setDefaultCSS("style/default.css");
        

    }
 
    public void viewClosed(String id) {
        loop = false;
    }

	@Override
	public Component getGraphicGraphComponent() {
		View view = viewer.addDefaultView(false);
		return (Component)view;
	}

	@Override
	public AGraph getAGraph() {
		return this.graph;
	}

	
	@Override
	public void refresh() {
		

		
	}

	@Override
	public void replace() {

		
	}
	public void setDefaultCSS(String path){
		this.graphstream.addAttribute("ui.stylesheet","url("+path+")");
	}
	@Override
	public void setAutomaticTopology(boolean automatic) {
		if(automatic)
			this.viewer.enableAutoLayout();
		else
			this.viewer.disableAutoLayout();
	}
	
	private void updateStyle(){

	}
}
