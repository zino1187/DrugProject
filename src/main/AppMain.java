package main;

import java.awt.BorderLayout;
import java.awt.Choice;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import api.PriceManager;
import config.ConfigMain;
import db.DBManager;
import product.Asset;
import product.AssetDAO;
import product.Product;
import product.ProductDAO;
import store.Store;
import store.StoreDAO;
import store.StoreMain;

public class AppMain extends JFrame{
	String Log=this.getClass().getName();
	JPanel[] pages=new JPanel[3];
	JPanel p_cardContainer;
	JPanel p_center; 
	JPanel p_inputContainer;
	JPanel p_info; 
	JPanel p_tableContainer;
	JPanel p_searchContainer; //검색영역
	
	
	
	int currentPage=0;
	StoreMain storeMain;
	ConfigMain configMain;
	

	JLabel la_store,la_barcode ,la_name ,la_company ,la_expert ,la_packunit ,la_price,la_type; 
	JTextField t_barcode,t_name,t_company,t_expert,t_packunit,t_price, t_type; 
	Choice t_store;
	 
	JTextField t_search;//테이블 검색
	JLabel la_total;//테이블 통계 
	
	AssetModel model;
	JTable table;
	JScrollPane scroll;
	
	JButton bt_del;//직접 검색
	JButton bt_regist;
	JMenuBar bar;
	JMenu m_home,m_store,m_config;
	
	DBManager dbManager;
	StoreDAO storeDAO;
	ProductDAO productDAO;
	AssetDAO assetDAO;
	List<Store> storeList;
	
	String apiKey="4ZZCzYxW3eNddY278CkGqPVkZi0xzWDAFZ%2FVKD6hrcMYO00C3eXIPhgau9tOFN0j8tjd%2B5W3bWoHYXCGUdPLlw%3D%3D";
	PriceManager priceManager;
	Thread barcodeThread;
	
	public AppMain() {
		super("약국관리 프로그램 Ver 1.0");
		p_cardContainer=new JPanel();
		p_center=new JPanel();
		storeMain=new StoreMain(this);
		configMain=new ConfigMain(this);
		p_inputContainer=new JPanel();
		p_info = new JPanel();
		p_tableContainer=new JPanel();
		p_searchContainer=new JPanel();
		
		
		la_store = new JLabel("약국선택");
		la_barcode = new JLabel("바코드 검색");
		la_name = new JLabel("약품명");
		la_company = new JLabel("제조사");
		la_expert = new JLabel("전문/일반");
		la_packunit = new JLabel("포장형태");
		la_type = new JLabel("제형구분");
		la_price = new JLabel("가격");
		
		
		t_store = new Choice();
		t_barcode = new JTextField();
		t_name = new JTextField();
		t_company = new JTextField();
		t_expert = new JTextField();
		t_packunit = new JTextField();
		t_type = new JTextField();
		t_price = new JTextField();
		
		t_search=new JTextField(40);
		la_total=new JLabel("여기에 통계결과");
		
		model = new AssetModel(this); 
		table = new JTable(model);
		scroll =new JScrollPane(table);
		
		
		bt_regist=new JButton("직접등록");
		bt_del=new JButton("선택삭제");
		
		bar = new JMenuBar();
		this.setJMenuBar(bar);
		
		m_home=new JMenu("HOME");
		m_store=new JMenu("약국관리");
		m_config=new JMenu("환경설정");

		
		dbManager = new DBManager();
		storeDAO = new StoreDAO();
		productDAO = new ProductDAO();
		assetDAO = new AssetDAO();
		
		priceManager = new PriceManager();
		
		bar.setPreferredSize(new Dimension(1800,45));
		bar.setBackground(Color.DARK_GRAY);
		m_home.setPreferredSize(new Dimension(120,45));
		
		m_store.setPreferredSize(new Dimension(120,45));
		m_config.setPreferredSize(new Dimension(120,45));

		m_home.setFont(new Font("돋움",Font.BOLD,15));
		m_store.setFont(new Font("돋움",Font.BOLD,15));
		m_config.setFont(new Font("돋움",Font.BOLD,15));
		
		m_home.setForeground(Color.WHITE);
		m_config.setForeground(Color.WHITE);
		m_store.setForeground(Color.WHITE);
		
		bar.add(m_home);
		bar.add(m_store);
		bar.add(m_config);
		
		Dimension labelWidth = new Dimension(100, 40);
		Dimension textWidth = new Dimension(200, 40);
		Dimension inputWidth = new Dimension(350, 40); 
		Dimension tableWidth = new Dimension(1150, 40);
		
		la_store.setPreferredSize(labelWidth);
		la_barcode.setPreferredSize(labelWidth);
		la_name.setPreferredSize(labelWidth);
		la_company.setPreferredSize(labelWidth);
		la_expert.setPreferredSize(labelWidth);
		la_packunit.setPreferredSize(labelWidth);
		la_type.setPreferredSize(labelWidth);
		la_price.setPreferredSize(labelWidth);
		
		t_store.setPreferredSize( new Dimension(200, 22));
		t_barcode.setPreferredSize(textWidth);
		t_name.setPreferredSize(textWidth);
		t_company.setPreferredSize(textWidth);
		t_expert.setPreferredSize(textWidth);
		t_packunit.setPreferredSize(textWidth);
		t_type.setPreferredSize(textWidth);
		t_price.setPreferredSize(textWidth);
		
		t_search.setFont(new Font("굴림",Font.BOLD,15));
		t_search.setPreferredSize(new Dimension(1000, 30));
		la_total.setPreferredSize(new Dimension(1800, 150));
		la_total.setFont(new Font("굴림",Font.BOLD,25));
		
		p_info.setPreferredSize(new Dimension(350, 400));
		p_info.setBorder(BorderFactory.createTitledBorder("등록현황"));
		p_inputContainer.setPreferredSize(inputWidth);
		p_tableContainer.setPreferredSize(tableWidth);
		
		t_store.setFont(new Font("굴림",Font.BOLD,15));
		t_barcode.setBackground(Color.YELLOW);
		table.setFont(new Font("돋움",Font.BOLD,15));
		table.setRowHeight(30);
		
		
		p_cardContainer.setLayout(new BorderLayout());
		p_center.setLayout(new BorderLayout());
		p_tableContainer.setLayout(new BorderLayout());
		
		p_inputContainer.add(la_store);
		p_inputContainer.add(t_store);
		p_inputContainer.add(la_barcode);
		p_inputContainer.add(t_barcode);
		p_inputContainer.add(la_name);
		p_inputContainer.add(t_name);
		p_inputContainer.add(la_company);
		p_inputContainer.add(t_company);
		p_inputContainer.add(la_expert);
		p_inputContainer.add(t_expert);
		p_inputContainer.add(la_packunit);
		p_inputContainer.add(t_packunit);
		p_inputContainer.add(la_type);
		p_inputContainer.add(t_type);
		p_inputContainer.add(la_price);
		p_inputContainer.add(t_price);
		
		p_inputContainer.add(bt_regist);
		p_inputContainer.add(bt_del);
		
		p_searchContainer.add(t_search);
		
		p_tableContainer.add(p_searchContainer, BorderLayout.NORTH);
		p_tableContainer.add(scroll);
		p_tableContainer.add(la_total, BorderLayout.SOUTH);
		la_total.setBackground(Color.YELLOW);
		
		p_inputContainer.add(p_info);
		p_center.add(p_inputContainer, BorderLayout.WEST);
		p_center.add(p_tableContainer);
		
		p_cardContainer.add(p_center);
		
		pages[0]=p_center;
		pages[1]=storeMain;
		pages[2]=configMain;

		add(p_cardContainer);

		setVisible(true);
		setSize(1800,900);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		m_home.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				getStoreList();
				showHide(0);
			}
		});
		m_store.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				showHide(1);
				storeMain.getList();
			}
		});
		m_config.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				showHide(2);
			}
		});
	
		bt_regist.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//regist();
				reset();
			}
		});
		bt_del.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				delete();
			}
		});
		
		t_store.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				t_barcode.setText("");	
				t_barcode.requestFocus();
			}
		});
		
		t_barcode.getDocument().addDocumentListener(new DocumentListener() {
			public void removeUpdate(DocumentEvent e) {
				System.out.println("removeUpdate");
			}
			public void insertUpdate(DocumentEvent e) {
				//System.out.println("insertUpdate");
				barcodeThread = new Thread() {
					public void run() {
						if(t_barcode.getText().length()==13){
							searchUsingBarcode();	
						}
					}
				};
				barcodeThread.start();
			}
			public void changedUpdate(DocumentEvent e) {
				System.out.println("changedUpdate");
			}
		});;
		
		t_store.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if(t_store.getSelectedIndex()>0) {
					getAssetList();
				}
			}
		});
		/*		
		table.getModel().addTableModelListener(new TableModelListener() {
			public void tableChanged(TableModelEvent e) {
				if(e.getType()==TableModelEvent.UPDATE) {
					System.out.println("수정했어?");
				}
			}
		});
		 */
		t_search.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				int key=e.getKeyCode();
				if(key == KeyEvent.VK_ENTER) {
					getSearchList();
				}
			}
		});
		runServer();
		getStoreList();
	}
	
	public void runServer() {
		if(dbManager.runServer()){
			System.out.println(Log + "Database Server is running!!");
		}else {
			System.out.println(Log + "Database Server fail...");
		}	
	}
	
	public void showResultDialog() {
		SearchFrame sf = new SearchFrame(this, "검색결과");
	}
	
	public void getStoreList() {
		t_store.removeAll();
		
		t_store.add("약국을 선택해 주세요");

		storeList=storeDAO.selectAll();
		for(int i=0;i<storeList.size();i++) {
			Store store=storeList.get(i);
			t_store.add(store.getName());
			System.out.println(store.getName());
		}
	}

	public void showHide(int showIndex) {
		p_cardContainer.remove(pages[currentPage]);
		p_cardContainer.add(pages[showIndex]);
		p_cardContainer.revalidate();
		p_cardContainer.repaint();
		
		currentPage=showIndex; 
	}

	public void searchUsingBarcode() {
		
		if(t_store.getSelectedIndex() ==0) {
			JOptionPane.showMessageDialog(this, "약국을 선택하세요");
			t_barcode.setText("");
			t_name.setText("");
			return;
		}
		/*
		int len=t_barcode.getText().length();
		
		if(len>=13) {
			System.out.println("바코드 읽음");
			getProductInfoByBarcode();
		}
		*/
		getProductInfoByBarcode();
	}
	
	public void reset() {
		t_barcode.setText("");
		t_name.setText("");
		t_company.setText("");
		t_expert.setText("");
		t_packunit.setText("");
		t_type.setText("");
		t_price.setText("");
		
		t_barcode.requestFocusInWindow();
	}

	/*----------------------------------------------------
	asset 에 등록
	----------------------------------------------------*/
	public void regist(Asset asset) {
		Store store=storeList.get(t_store.getSelectedIndex()-1);
		asset.setStore(store); //asset 에 스토어 등록
		asset.setPrice(Integer.parseInt(t_price.getText()));
		
		//이미 존재하는 상품인지 검증하기 위한 절차
		Product product=productDAO.selectByBarcode(t_barcode.getText());
		asset.getProduct().setProduct_id(product.getProduct_id());
		List<Asset> list=assetDAO.checkExist(asset);
		System.out.println(asset.getProduct().getProduct_id()+","+asset.getStore().getStore_id()+"중복조회 결과"+list.size());
		
		if(list.size()>0) {
			Asset obj=list.get(0);
			int result=assetDAO.updateStock(obj.getAsset_id());
			if(result >0) {
				JOptionPane.showMessageDialog(this, "이미 등록된 상품이므로 기존 재고량은 1증가되었습니다");
			}
		}else {
			int result=assetDAO.insert(asset);
			System.out.println("asset에 등록 성공");
		}
		getAssetList(); //목록 갱신
		reset();
	}
		
	/*----------------------------------------------------
	바코드로 약품명 가져오기
	----------------------------------------------------*/
	public void getProductInfoByBarcode() {
		Product product=productDAO.selectByBarcode(t_barcode.getText());
		
		if(product==null) {//DB에 없을 경우
			p_info.setBackground(Color.ORANGE);
			
			reset();
			
		}else{//DB에 있을 경우
			p_info.setBackground(Color.GREEN);
			
			System.out.println(Log+": DB: "+product.getProduct_name());
			System.out.println(Log+": DB: "+product.getCompany());
			System.out.println(Log+": DB: "+product.getProfessional());
			System.out.println(Log+": DB: "+product.getPack());
			System.out.println(Log+": DB: "+product.getType());
			
			t_name.setText(product.getProduct_name()); //약품명
			t_company.setText(product.getCompany());//제조사
			t_expert.setText(product.getProfessional());//전문/일반
			t_packunit.setText(product.getPack());//포장형태
			t_type.setText(product.getType());//제형구분
			
			//공공데이터 포털 가격 검색
			String[] result=getPrice(product.getProduct_name());
			int totalCount=Integer.parseInt(result[0]);
			
			
			Asset asset = new Asset();
			
			if(totalCount== 0) {//가격 조회 실패
				t_price.setText("0");
				asset.setMemo("가격없음");
			}else if(totalCount==1){ //가격이 한건 조회되면
				t_price.setText(result[1]);
				asset.setMemo("조회성공");
			}else if(totalCount>1) {//가격이 여러건 조회되면
				//선택 팝업 띄우기
				t_price.setText("0");
				asset.setMemo("2건이상");
			}
			
			if(result[1]==null) {
				t_price.setBackground(Color.ORANGE);
			}else {
				t_price.setBackground(Color.WHITE);
			}
			
			//asset에 등록
			asset.setProduct(product);
			regist(asset);
		}
	}
	
	/*----------------------------------------------------
	이름로 약품명 가져오기
	----------------------------------------------------*/
	public void getProductInfoByName() {
		List<Product> productList=productDAO.selectByName(t_name.getText());
		
		if(productList.size()==0) {//DB에 없을 경우
			p_info.setBackground(Color.ORANGE);
			
			reset();
			
		}else if(productList.size()==1){//DB에 있을 경우
			Product product = productList.get(0);
			p_info.setBackground(Color.GREEN);
			
			System.out.println(Log+": DB: "+product.getProduct_name());
			System.out.println(Log+": DB: "+product.getCompany());
			System.out.println(Log+": DB: "+product.getProfessional());
			System.out.println(Log+": DB: "+product.getPack());
			System.out.println(Log+": DB: "+product.getType());
			
			t_name.setText(product.getProduct_name()); //약품명
			t_company.setText(product.getCompany());//제조사
			t_expert.setText(product.getProfessional());//전문/일반
			t_packunit.setText(product.getPack());//포장형태
			t_type.setText(product.getType());//제형구분
			
			//getPriceByName(product.getProduct_name());
			
		}else if(productList.size()>1) {
			new SearchFrame(this, t_name.getText()+"로 조회한 결과 "+productList.size()+" 건이 검색되었습니다");
		}
	}
	
	
	
	
	/*----------------------------------------------------
	공공데이터 포털의 가격 검색
	----------------------------------------------------*/
	public String[] getPrice(String productName) {
		String[] result=null;
		try {
			
			result=priceManager.getProductInfo(apiKey, productName);
			System.out.println("총건수 "+result[0]);
			System.out.println("가격은 "+result[1]);
			
			//가격이  null 인 경우, 약품명에서 (괄호)를 뺀다
			if(result[1]==null) {
				result[1]=findWithoutBrace(productName);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/*----------------------------------------------------
	괄호를 제거한 재검색
	----------------------------------------------------*/
	public String findWithoutBrace(String product_name) {
		String price=null;
		int beginIndex = product_name.indexOf("(");
		System.out.println("beginIndex "+beginIndex);
		
		if(beginIndex > -1) {
			String name = product_name.substring(0, beginIndex);
			JOptionPane.showMessageDialog(this, name+" 으로 재검색 합니다");
			//getPriceByName(name);
		}
		return price;
	}

	/*----------------------------------------------------
	약국의 asset 목록 
	----------------------------------------------------*/
	public void getAssetList() {
		Store store=storeList.get(t_store.getSelectedIndex()-1);
		List<Asset> assetList=assetDAO.selectAll(store.getStore_id());
		String[][] data=new String[assetList.size()][model.columnName.length];
		
		for(int i=0;i<assetList.size();i++) {
			Asset asset=assetList.get(i);
			data[i][0]=Integer.toString(asset.getAsset_id());
			data[i][1]=asset.getProduct().getProduct_name();
			data[i][2]=asset.getProduct().getCompany();
			data[i][3]=asset.getProduct().getSpec();
			data[i][4]=asset.getProduct().getAmount();
			data[i][5]=asset.getProduct().getType();
			data[i][6]=asset.getProduct().getPack();
			data[i][7]=asset.getProduct().getProfessional();
			data[i][8]=asset.getProduct().getBarcode();
			data[i][9]=Integer.toString(asset.getPrice());
			data[i][10]=Integer.toString(asset.getStock());
			data[i][11]=asset.getMemo();
		}
		model.data=data;
		table.updateUI();
		
		showTotal();
	}
	
	/*----------------------------------------------------
	통계 합산 및 출력 
	----------------------------------------------------*/
	public void showTotal() {
		StringBuffer sb = new StringBuffer();
		sb.append("등록건수 : "+model.data.length+"개, ");
		
		int sum=0;
		for(int i=0;i<model.data.length;i++) {
			int price=Integer.parseInt(model.data[i][9]); //단가
			int stock=Integer.parseInt(model.data[i][10]); //재고량
			sum=sum+(price * stock);
		}
		sb.append("자산 합계 : "+sum+" 원");
		
		la_total.setText(sb.toString());
		la_total.updateUI();
	}
	
	/*----------------------------------------------------
	선택 삭제 
	----------------------------------------------------*/
	public void delete() {
		int row=table.getSelectedRow();
		
		int asset_id=Integer.parseInt(((String)table.getValueAt(row, 0)));
		String name=(String)table.getValueAt(row, 1);
		
		if(JOptionPane.showConfirmDialog(this, name+" 약품을 삭제하시겠습니까?")==JOptionPane.OK_OPTION) {
			int result=assetDAO.delete(asset_id);
			if(result>0) {
				getAssetList();
			}else {
				JOptionPane.showMessageDialog(this, "삭제 실패\n관리자에게 문의하세요");
			}
		}
	}
	/*----------------------------------------------------
	데이터 수정 
	----------------------------------------------------*/
	public void update() {
		int row=table.getSelectedRow();
		//System.out.println("지금 수정하고 있는 row는 "+row);
		int asset_id=Integer.parseInt((String)table.getValueAt(row, 0));
		int price=Integer.parseInt((String)table.getValueAt(row, 9));
		int stock=Integer.parseInt((String)table.getValueAt(row, 10));
		String memo=(String)table.getValueAt(row, 11);
		
		Asset asset = new Asset();
		asset.setAsset_id(asset_id);
		asset.setPrice(price);
		asset.setStock(stock);
		asset.setMemo(memo);
		
		int result=assetDAO.update(asset);
		if(result >0) {
			if(t_search.getText().length()>0) {
				getSearchList();
			}else {
				getAssetList();
			}
		}else {
			JOptionPane.showMessageDialog(this, "수정 실패\n관리자에게 문의하세요");
		}
	}
	
	/*----------------------------------------------------
	검색 
	----------------------------------------------------*/
	public void getSearchList() {
		String input_name=t_search.getText();
		
		//검색어가 있다면...
		if(input_name.length()>0) {
			ArrayList list=new ArrayList();
			
			for(int i=0;i<model.data.length;i++) {
				String product_name=model.data[i][1]; //상품명
				if(product_name.contains(input_name)) {
					list.add(model.data[i]);
				}			
			}
			
			System.out.println("매칭 결과 수는 "+list.size());
			
			String[][] data=new String[list.size()][model.columnName.length];
			
			for(int i=0;i<list.size();i++) {
				data[i]=(String[])list.get(i);
			}
			model.data=data;
			table.updateUI();
		}else {//검색어가 없다면..
			getAssetList();
		}
	}

	public static void main(String[] args) {
		new AppMain();
	}
}
