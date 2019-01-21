package store;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import main.AppMain;


public class StoreMain extends JPanel{
	AppMain appMain;
	
	JPanel p_inputWrapper;
	JPanel p_inputContainer;
	JPanel p_center;
	JPanel p_tableContainer;
	JLabel la_name; 
	JLabel la_phone; 
	JLabel la_loc; 
 
	JTextField t_name; 
	JTextField t_phone; 
	JTextField t_loc; 


	DataModel model;
	JTable table;
	JScrollPane scroll;
	

	JButton bt_regist;
	
	StoreDAO storeDAO=new StoreDAO();

	public StoreMain(AppMain appMain) {
		this.appMain=appMain;
		
		p_inputContainer=new JPanel();
		p_center=new JPanel();
		p_tableContainer=new JPanel();
		

		p_center.setLayout(new BorderLayout());
		p_tableContainer.setLayout(new BorderLayout());
		
		la_name = new JLabel("약국명");
		la_phone = new JLabel("연락처");
		la_loc = new JLabel("주소");
		
		t_name = new JTextField();
		t_phone = new JTextField();
		t_loc = new JTextField();
		
		model = new DataModel(this);
		
		table = new JTable(model);
		scroll =new JScrollPane(table);
		
		bt_regist=new JButton("등록");

		Dimension labelWidth = new Dimension(100, 25);
		Dimension textWidth = new Dimension(200, 40);
		Dimension inputWidth = new Dimension(350, 40); 
		Dimension tableWidth = new Dimension(1000, 40);
		

		la_name.setPreferredSize(labelWidth);
		la_phone.setPreferredSize(labelWidth);
		la_loc.setPreferredSize(labelWidth);
		
		t_name.setPreferredSize(textWidth);
		t_phone.setPreferredSize(textWidth);
		t_loc.setPreferredSize(textWidth);
		
		table.setFont(new Font("돋움",Font.BOLD,12));
		table.setRowHeight(25);
		
		p_inputContainer.setPreferredSize(inputWidth);
		
		p_tableContainer.setPreferredSize(tableWidth);
		p_tableContainer.setBackground(Color.YELLOW);
		
		p_inputContainer.add(la_name);
		p_inputContainer.add(t_name);
		p_inputContainer.add(la_phone);
		p_inputContainer.add(t_phone);
		p_inputContainer.add(la_loc);
		p_inputContainer.add(t_loc);
		p_inputContainer.add(bt_regist);
		
		p_tableContainer.add(scroll);
		
 
		p_center.add(p_inputContainer, BorderLayout.WEST);
		p_center.add(p_tableContainer);		
		
		this.setLayout(new BorderLayout());
		this.add(p_center);
		
		bt_regist.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				regist();
			}
		});
		
		
		
	}
	
	public void regist() {
		Store store = new Store();

		store.setName(t_name.getText());
		store.setPhone(t_phone.getText());
		store.setAddr(t_loc.getText());
		
		int result=storeDAO.regist(store);
		if(result >0) {
			getList();
		}
	}

	public void getList() {
		List<Store> list=storeDAO.selectAll();
		
		model.list=new Vector();
		
		for(int i=0;i<list.size();i++) {
			Store store=list.get(i);
			String[] data = new String[4];
			
			data[0]=Integer.toString(store.getStore_id());
			data[1]=store.getName();
			data[2]=store.getPhone();
			data[3]=store.getAddr();
			
			model.list.add(data);
		}
		model.fireTableDataChanged();
		
	}

}