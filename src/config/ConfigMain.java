package config;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import main.AppMain;
import product.Product;
import product.ProductDAO;

public class ConfigMain extends JPanel{
	AppMain appMain;
	JPanel p_north;
	JButton bt_manipulation;
	JTextArea area;
	JScrollPane scroll;
	Thread[] threads;
	ProductDAO productDAO;
	List<Product> productList;
	
	int totalRecord=0;
	int pageSize=20000;
	int totalPage=0;
	int curPos; //각 페이지당 커서 위치
	int num; //각 페이지당 시작 번호
	JScrollBar bar;
	
	public ConfigMain(AppMain appMain) {
		this.appMain=appMain;
		setLayout(new BorderLayout());
		p_north=new JPanel();
		bt_manipulation=new JButton("DB 정리하기");
		area = new JTextArea();
		scroll  =new  JScrollPane(area);
		bar=scroll.getVerticalScrollBar();
		
		
		p_north.add(bt_manipulation);
		add(scroll);
		add(p_north, BorderLayout.NORTH);
		
		bt_manipulation.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//manipulate();
				//startThread();
			}
		});
	}	
	
	public void manipulate() {
		productDAO = new ProductDAO();
		productList=productDAO.selectAll();		
	}
	public void startThread() {
		totalRecord=productList.size();
		totalPage=(int)Math.ceil((float)totalRecord/pageSize);
		
		threads=new Thread[totalPage];
		
		for(int i=0;i<totalPage;i++) {
			curPos=i*(pageSize);
			int endPoint=curPos+pageSize;
			System.out.println(i+"번째 쓰레드의 시작 인덱스는 "+curPos+", endPoint "+endPoint);
			threads[i] = new DBThread(ConfigMain.this, "Thread"+i ,curPos, endPoint,productList);
			threads[i].start();
		}						
	}
}

