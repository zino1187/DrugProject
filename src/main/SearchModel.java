package main;

import java.util.Vector;

import javax.swing.table.AbstractTableModel;

public class SearchModel extends AbstractTableModel{

	String[] columnName;
	Vector list;
	
	public SearchModel() {

		columnName= new String[5];
		
		columnName[0]="ǰ���";
		columnName[1]="����ȸ��";
		columnName[2]="����/�Ϲ�";
		columnName[3]="�������";
		columnName[4]="�ܰ�";
				
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
