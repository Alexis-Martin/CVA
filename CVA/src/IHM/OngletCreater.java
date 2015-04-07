package IHM;

import javax.swing.*;


public class OngletCreater extends JTabbedPane {
	
	public OngletCreater()
	{
	/*	testOnglet onglet1 = new testOnglet(); 
		testOnglet onglet2 = new testOnglet();
		
		this.addTab("Onglet", onglet1);
		this.addTab("Onglet2", onglet2); */ 
	}

	
	public void addOnglet(Onglet toAdd)
	{
		this.addTab("Onglet", toAdd);
	}
}
