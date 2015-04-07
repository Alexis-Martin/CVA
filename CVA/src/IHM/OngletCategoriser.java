package IHM;
import java.awt.Dimension;

import javax.swing.*;

public class OngletCategoriser extends Onglet {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public OngletCategoriser()
	{

		
		Box layoutPrincipal = Box.createVerticalBox();
		JLabel prop = new JLabel ("Propriétés");
		prop.setPreferredSize(new Dimension(100,10));
		
		////////// PROPERTIES BOX ///////////
		Box propertiesBox = Box.createHorizontalBox();
		JLabel E = new JLabel("E");
		E.setPreferredSize(new Dimension(100,20));
		propertiesBox.add(E); 
		/*JTextField eText = new JTextField(); 
		propertiesBox.add(eText);  */ 
		
		JPanel Informations = new JPanel() ;
		JLabel infos = new JLabel("Informations sur l'algorithme"); 
		Informations.add(infos); 
		Informations.setPreferredSize(new Dimension(200,70));
		
		JButton run = new JButton("Run"); 
		
		layoutPrincipal.add(prop); 
		layoutPrincipal.add(propertiesBox); 
		layoutPrincipal.add(Informations); 
		layoutPrincipal.add(run); 
		
		this.add(layoutPrincipal);
	}

}
