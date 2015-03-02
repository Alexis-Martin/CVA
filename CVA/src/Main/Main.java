package Main;

import java.io.IOException;

import CVAGraph.CVAGraph;
import CVAGraph.CVAGraphViewer;

public class Main {

	public static void main(String argv[]){
		CVAGraph mygraph =  new CVAGraph("Test");

		String a = mygraph.addArgument(null);
		String b = mygraph.addArgument(null);
		String c = mygraph.addArgument(null);
		
		mygraph.addAttack(a, b);
		mygraph.addAttack(b, c);
		mygraph.addDefense(c, a);
	
		mygraph.save("graph1.gst");
		CVAGraphViewer cvagv = new CVAGraphViewer(mygraph.getGraph()); 

	}
}