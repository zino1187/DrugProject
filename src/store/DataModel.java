package store;

import java.util.Vector;

import javax.swing.table.AbstractTableModel;

public class DataModel extends AbstractTableModel{
	StoreMain storeMain;
	String[] columnName;
	
	Vector list;

	
	public DataModel(StoreMain storeMain) {
		this.storeMain=storeMain;
		columnName= new String[4];
		
		columnName[0]="고유번호";
		columnName[1]=storeMain.la_name.getText();
		columnName[2]=storeMain.la_phone.getText();
		columnName[3]=storeMain.la_loc.getText();
				
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
	@Override
	public boolean isCellEditable(int row, int col) {
		boolean edit=true;
		if(col==0) {
			edit=false;
		}
		return edit;
	}
	@Override
	public void setValueAt(Object obj, int row, int col) {
		String[] array=(String[])list.elementAt(row);
		array[col]=(String)obj;

		System.out.println(obj);
	}

}
