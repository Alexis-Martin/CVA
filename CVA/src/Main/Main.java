package Main;

import in_out.CVAGraphIO;
import in_out.LoadingTypeException;

import java.awt.BorderLayout;
import java.awt.Component;
import java.io.IOException;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import algo.Algorithm;
import algo.Categoriser;
import algo.SocialAbstractArgumentation;

import CVAGraph.AGraph;
import CVAGraph.Argument;
import CVAGraph.GSAGraph;
import CVAGraph.CVAGraphViewer;
import CVAGraph.GSArgument;
import IHM.IHM;
import IHMGraph.IHMGraph;



public class Main {

	public static void main(String argv[]){
		
		 try {
	            // Set cross-platform Java L&F (also called "Metal")
	        UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
	    } 
	    catch (UnsupportedLookAndFeelException e) {
	       // handle exception
	    }
	    catch (ClassNotFoundException e) {
	       // handle exception
	    }
	    catch (InstantiationException e) {
	       // handle exception
	    }
	    catch (IllegalAccessException e) {
	       // handle exception
	    }
		 
		AGraph mygraph = null;
		try {
			mygraph = CVAGraphIO.read("savefile/graph_exemple.dgs");
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (LoadingTypeException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
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
		IHMGraph ihmgraph =  new IHMGraph(mygraph);
		IHM ihm = new IHM();	
		ihm.addGraphVisu((Component) ihmgraph.getView());


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