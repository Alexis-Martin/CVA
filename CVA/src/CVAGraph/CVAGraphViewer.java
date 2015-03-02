package CVAGraph;

import org.graphstream.graph.Graph;
import org.graphstream.ui.view.View;
import org.graphstream.ui.view.Viewer;
import org.graphstream.ui.view.ViewerListener;
import org.graphstream.ui.view.ViewerPipe;


public class CVAGraphViewer implements ViewerListener {
    protected boolean loop = true;
	private Graph graph;
	public Viewer viewer;
    public CVAGraphViewer(Graph graph) {

    	this.graph = graph;
        this.viewer = new Viewer(graph, Viewer.ThreadingModel.GRAPH_IN_ANOTHER_THREAD);
        this.viewer.enableAutoLayout();

        viewer.setCloseFramePolicy(Viewer.CloseFramePolicy.HIDE_ONLY);

        ViewerPipe fromViewer = viewer.newViewerPipe();
        fromViewer.addViewerListener(this);
        fromViewer.addSink(graph);

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