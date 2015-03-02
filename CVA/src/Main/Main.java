package Main;

import in_out.CVAGraphIO;

import java.awt.BorderLayout;
import java.awt.Component;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import CVAGraph.CVAGraph;
import CVAGraph.CVAGraphViewer;



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
	    
	    
	    
		CVAGraph mygraph =  new CVAGraph("Test");

		String a = mygraph.addArgument(null);
		String b = mygraph.addArgument(null);
		String c = mygraph.addArgument(null);
		
		mygraph.addAttack(a, b);
		mygraph.addAttack(b, c);
		mygraph.addDefense(c, a);
	
		try {
			CVAGraphIO.write("Test/GrapheNumero1", mygraph);
		} catch (IOException e) {

			e.printStackTrace();
		}
		CVAGraphViewer cvagv = new CVAGraphViewer(mygraph.getGraph()); 
	    fenetre.getContentPane().add((Component) cvagv.getView(),BorderLayout.CENTER);
	    
	}
}