package Main;

import in_out.CVAGraphIO;

import java.awt.BorderLayout;
import java.awt.Component;
import java.io.IOException;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import algo.Algorithm;
import algo.Categoriser;
import algo.SocialAbstractArgumentation;

import CVAGraph.AGraph;
import CVAGraph.Argument;
import CVAGraph.GSAGraph;
import CVAGraph.CVAGraphViewer;
import CVAGraph.GSArgument;
import IHM.IHM;



public class Main {

	public static void main(String argv[]){
		/*JFrame fenetre;
	    
	fenetre = new JFrame();
		fenetre.setVisible(true);
		fenetre.setSize(600, 400);
		fenetre.setLayout(new BorderLayout());
		JPanel jpg = new JPanel();
		jpg.setSize(400, fenetre.getSize().height);
		jpg.add(new JLabel("Test"));
		fenetre.getContentPane().add(jpg,BorderLayout.WEST);
*/
		AGraph mygraph =  new GSAGraph("Test");

		Argument a = mygraph.addArgument("a", "a");
		Argument b = mygraph.addArgument("b", "b");
		Argument c = mygraph.addArgument("c", "c");
		Argument d = mygraph.addArgument("d", "d");
		Argument e = mygraph.addArgument("e", "e");
		
		mygraph.addAttack(a, b);
		mygraph.addAttack(b, a);
		mygraph.addAttack(c, b);
		mygraph.addAttack(d, c);
		mygraph.addAttack(e, c);
		mygraph.addAttack(e, d);
		mygraph.addAttack(d, e);
		
		Algorithm categoriser = new SocialAbstractArgumentation(mygraph, "Categoriser");
		categoriser.execute();
		List<Argument> list = mygraph.getUtilities();
		if(list.size() != 0)
			System.out.print("(" + list.get(0).getId() +", " + list.get(0).getUtility() + ")");
		for(int i = 1; i < list.size(); i++){
			if(list.get(i).getUtility() < list.get(i-1).getUtility())
				System.out.print(" > ");
			else
				System.out.print(" = ");
			
			System.out.print("(" + list.get(i).getId() +", " + list.get(i).getUtility() + ")");
		}
		IHM ihm = new IHM();	
		
		/*
	try {
			CVAGraphIO.write("Test/GrapheNumero1", mygraph);
		} catch (IOException e) {

			e.printStackTrace();
		}
*/
	//	CVAGraphViewer cvagv = new CVAGraphViewer(mygraph.getGraph()); 
	  //  fenetre.getContentPane().add((Component) cvagv.getView(),BorderLayout.CENTER);
	    
	}
}