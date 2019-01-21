package main;

import java.util.Vector;

import javax.swing.table.AbstractTableModel;

public class SearchModel extends AbstractTableModel{

	String[] columnName;
	Vector list;
	
	public SearchModel() {

		columnName= new String[5];
		
		columnName[0]="품목명";
		columnName[1]="제약회사";
		columnName[2]="전문/일반";
		columnName[3]="포장단위";
		columnName[4]="단가";
				
		list = new Vector();
	}
	
	@Override
	public int getColumnCount() {
		return columnName.length;
	}
	
	@Override
	public String getColumnName(int col) {
		return columnName[col];
	}
	
	@Override
	public int getRowCount() {
		return list.size();
	}

	@Override
	public Object getValueAt(int row , int col) {
		String[] array=(String[])list.elementAt(row);
		return array[col];
	}
	

}
