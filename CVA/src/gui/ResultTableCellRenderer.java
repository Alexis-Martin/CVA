package gui;

import java.awt.Color;
import java.awt.Component;

import javax.swing.BorderFactory;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;

public class ResultTableCellRenderer extends DefaultTableCellRenderer {


	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
	    
		System.out.println("row " + row + " column " + column);
		Color c = (row % 2 == 0)? Color.WHITE: new Color(226, 226, 226);
			
		this.setText(value.toString());
		this.setBackground(hasFocus? new Color(147, 193, 217) : c);
		this.setBorder(isSelected? BorderFactory.createLineBorder(new Color(147, 193, 217)) : BorderFactory.createEmptyBorder());
		this.setSize((int) this.getSize().getWidth(), 30);
		
		return this;
	  }
}
