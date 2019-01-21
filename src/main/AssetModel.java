package main;

import javax.swing.table.AbstractTableModel;

public class AssetModel extends AbstractTableModel{
	AppMain appMain;
	String[] columnName;
	String[][] data;
	
	public AssetModel(AppMain appMain) {
		this.appMain=appMain;
		
		columnName= new String[12];
		
		columnName[0]="구분코드";
		columnName[1]="품목명";
		columnName[2]="제약회사";
		columnName[3]="약품규격";
		columnName[4]="총수량";
		columnName[5]="제형구분";
		columnName[6]="포장형태";
		columnName[7]="전문/일반";
		columnName[8]="바코드";
		columnName[9]="단가";
		columnName[10]="재고";
		columnName[11]="비고";
				
		data = new String[0][columnName.length];
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
		return data.length;
	}

	@Override
	public Object getValueAt(int row , int col) {
		return data[row][col];
	}
	@Override
	public boolean isCellEditable(int row, int col) {
		boolean edit=false;
		if(col==9 || col==10 || col==11) {
			edit=true;
		}
		return edit;
	}
	@Override
	public void setValueAt(Object obj, int row, int col) {
		data[row][col]=(String)obj;
		appMain.update();
	}
	
}

