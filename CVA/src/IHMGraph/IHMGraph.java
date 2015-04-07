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

public class IHMGraph {
    protected boolean loop = true;
	private AGraph graph;
	private Graph graphstream;
	public Viewer viewer;
    public IHMGraph(AGraph graph) {

    	this.graph = graph;
    	this.graphstream = AGraphAdapter.agraphToGraphstream(graph,"blabla");
        this.viewer = new Viewer(this.graphstream, Viewer.ThreadingModel.GRAPH_IN_ANOTHER_THREAD);
        this.viewer.enableAutoLayout();

        viewer.setCloseFramePolicy(Viewer.CloseFramePolicy.HIDE_ONLY);

        ViewerPipe fromViewer = viewer.newViewerPipe();
        fromViewer.addSink(this.graphstream);

        fromViewer.pump();

    }
 
    public void viewClosed(String id) {
        loop = false;
    }
    public View getView(){
    	View view = viewer.addDefaultView(false);
    	return view;
    }
    
    public void buttonPushed(String id) {
        System.out.println("Button pushed on node "+id);
    }
 
    public void buttonReleased(String id) {
        System.out.println("Button released on node "+id);
    }
}
