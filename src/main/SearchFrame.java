package main;

import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class SearchFrame extends JFrame{
	AppMain appMain;
	JPanel p_north;
	JPanel p_center;
	
	JLabel la_message;
	JTable table;
	JScrollPane scroll;
	SearchModel model;
	
	public SearchFrame(AppMain appMain, String title) {
		super(title);
		this.appMain=appMain;
		
		p_north = new JPanel();
		p_center = new JPanel();
		la_message = new JLabel(title);
		table = new JTable(model=new SearchModel());
		scroll = new JScrollPane(table);
		
		la_message.setFont(new Font("돋움",Font.BOLD, 20));
		
		p_center.setLayout(new BorderLayout());
		
		p_north.add(la_message);
		p_center.add(scroll);
		
		add(p_north, BorderLayout.NORTH);
		add(p_center);
		
		this.setVisible(true);
		this.setSize(900, 350);
		this.setLocationRelativeTo(appMain);		
	}
}
