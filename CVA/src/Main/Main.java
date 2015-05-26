package Main;

import gui.IHM;




public class Main {

	public static void main(String argv[]){
		System.setProperty("org.graphstream.ui.renderer",
			    "org.graphstream.ui.j2dviewer.J2DGraphRenderer");
		
		IHM ihm = new IHM();
	}
}