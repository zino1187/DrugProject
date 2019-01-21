package product;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import db.DBManager;


public class ProductDAO {
	DBManager dbManager=new DBManager();

	public Product selectByBarcode(String barcode) {
		SqlSession sqlSession=dbManager.getSqlSession();
		Product product=sqlSession.selectOne("Product.selectByBarcode", barcode);
		sqlSession.close();
		return product;
	}
	
	public List selectByName(String name) {
		SqlSession sqlSession=dbManager.getSqlSession();
		List productList=sqlSession.selectList("Product.selectByName", name);
		sqlSession.close();
		return productList;
	}
	
	//모든 데이터 가져오기
	public List selectAll() {
		SqlSession sqlSession=dbManager.getSqlSession();
		List productList=sqlSession.selectList("Product.selectAll");
		sqlSession.close();
		return productList;
	}
	
	//이름수정하기
	public int update(Product product) {
		SqlSession sqlSession=dbManager.getSqlSession();
		int result=sqlSession.update("Product.update", product);
		sqlSession.commit();
		sqlSession.close();
		return result;
	}
	
}
