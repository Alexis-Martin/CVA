package gui;

import javax.swing.*;


public class OngletCreater extends JTabbedPane {
	private static final long serialVersionUID = -680418971863629867L;


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
