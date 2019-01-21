package config;

import java.util.List;

import product.Product;
import product.ProductDAO;

public class DBThread extends Thread {
	String name;
	int index=0; //쓰레드가 차례로 접근할  List 의 index
	int endPoint;//마지막 레코드 index
	boolean flag=true;
	ConfigMain configMain;
	ProductDAO productDAO=new ProductDAO();
	List<Product> productList;
	
	public DBThread(ConfigMain configMain ,String name, int index, int endPoint, List productList) {
		super(name);
		this.name=name;
		this.configMain=configMain;
		this.index=index;
		this.endPoint=endPoint;
		this.productList=productList;
	}
	
	public void run() {
		while (flag) {
			try {
				Thread.sleep(700);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			// 가져온 후 리스트를 대상으로, 괄호없애고 다시 update
			if(index>=endPoint)break;
			if(index>=productList.size())break; //총 레코드수를 못 넘게 처리!
			Product product = productList.get(index++);
			//System.out.println(product.getProduct_name() + "," + product.getBarcode());

			int firstIndex = product.getProduct_name().indexOf("(");

			if (firstIndex != -1) {// 괄호가 있을때만
				String productName = product.getProduct_name().substring(0, firstIndex);
				configMain.area.append(name+": "+productName + "\n");

				// 데이터베이스 갱신 ( 단 """ 를 살려야 한다 )
				product.setProduct_name("\"\"\"" + productName + "\"\"\"");
				//System.out.println("productDAO : "+productDAO);
				int result = productDAO.update(product);
				// System.out.println(product.getProduct_name()+","+product.getBarcode().length());
				if (result == 0) {
					configMain.area.append(name+": "+product.getProduct_name() + ", " + product.getBarcode() + "수정실패 \n");
				} else {
					configMain.area.append(name+": "+product.getProduct_name() + ", " + product.getBarcode() + "수정성공 \n");
				}
			} else {
				configMain.area.append(name+": "+product.getProduct_name() + ", " + product.getBarcode() + "해당 없음 \n");
			}
			configMain.bar.setValue(configMain.bar.getMaximum());
		}
	}

}
