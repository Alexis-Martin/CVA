package gui;

import java.awt.Color;
import java.awt.Component;
import java.util.ArrayList;
import java.util.List;

import graph.Argument;

import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.table.AbstractTableModel;

public class ResultTableModel extends AbstractTableModel{

	List<Argument> data;
	List<String> title;

	public ResultTableModel(List<Argument> data) {
		this();
		this.data = data;
		
	}
	
	public ResultTableModel() {
		this.title = new ArrayList<String>();
		title.add("id");
		title.add("poids");
		title.add("utilité");
		this.data = new ArrayList<Argument>();
	}
	
	@Override
	public int getColumnCount() {
		return title.size();
	}

	@Override
	public int getRowCount() {
		return data.size();
	}

	@Override
	public Object getValueAt(int row, int col) {
		if(title.get(col).equals("id"))
			return data.get(row).getId();
		if(title.get(col).equals("poids"))
			return data.get(row).getWeight();
		if(title.get(col).equals("utilité"))
			return data.get(row).getUtility();
		return null;
	}

	@Override
	public String getColumnName(int col) {
		return title.get(col);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Class getColumnClass(int col){
		if(title.get(col).equals("id"))
			return data.get(0).getId().getClass();
		if(title.get(col).equals("poids"))
			return ((Double)data.get(0).getWeight()).getClass();
		if(title.get(col).equals("utilité"))
			return ((Double)data.get(0).getUtility()).getClass();
		return Object.class;
	}
	
	@Override
	public boolean isCellEditable(int row, int col){
		return (title.get(col).equals("poids"))?true:false; 
	}
	
	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex){
		data.get(rowIndex).setWeight((double)aValue);
	}
	
	public void setUtility(Object aValue, String id_arg){
		for(Argument arg : data){
			if(arg.getId().equals(id_arg)){
				arg.setUtility((double)aValue);
			}
		}
	}
	
	public void setUtilities(List<Argument> list){
		this.data = list;
		fireTableDataChanged();
	}
	
	
	
	
}
