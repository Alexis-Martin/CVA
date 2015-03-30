package Main;

import in_out.CVAGraphIO;

import java.awt.BorderLayout;
import java.awt.Component;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import CVAGraph.GSAGraph;
import CVAGraph.CVAGraphViewer;
import CVAGraph.GSArgument;



public class Main {

	public static void main(String argv[]){
		JFrame fenetre = new JFrame();
	    fenetre.setVisible(true);
	    fenetre.setSize(600, 400);
	    fenetre.setLayout(new BorderLayout());
	    JPanel jpg = new JPanel();
	    jpg.setSize(400, fenetre.getSize().height);
	    jpg.add(new JLabel("Test"));
	    fenetre.getContentPane().add(jpg,BorderLayout.WEST);
	    
	    
	    
		GSAGraph mygraph =  new GSAGraph("Test");

		GSArgument a = mygraph.addArgument(null);
		GSArgument b = mygraph.addArgument(null);
		GSArgument c = mygraph.addArgument(null);
		
		mygraph.addAttack(a.getId(), b.getId());
		mygraph.addAttack(b.getId(), c.getId());
		mygraph.addDefense(c.getId(), a.getId());
	

		/*
	try {
			CVAGraphIO.write("Test/GrapheNumero1", mygraph);
		} catch (IOException e) {

			e.printStackTrace();
		}
*/
		CVAGraphViewer cvagv = new CVAGraphViewer(mygraph.getGraph()); 
	    fenetre.getContentPane().add((Component) cvagv.getView(),BorderLayout.CENTER);
	    
	}
}