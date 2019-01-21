package product;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import db.DBManager;


public class AssetDAO {
	DBManager dbManager=new DBManager();
	
	
	public int insert(Asset asset) {
		SqlSession sqlSession=dbManager.getSqlSession();
		int result=sqlSession.insert("Asset.insert", asset);
		sqlSession.commit();
		sqlSession.close();
		return result;
	}
	
	public List selectAll(int store_id) {
		SqlSession sqlSession=dbManager.getSqlSession();
		List assetList=sqlSession.selectList("Asset.selectAll", store_id);
		sqlSession.close();
		return assetList;
	}
	
	public List checkExist(Asset asset) {
		SqlSession sqlSession=dbManager.getSqlSession();
		List assetList=sqlSession.selectList("Asset.checkExist", asset);
		sqlSession.close();
		return assetList;
	}
	
	public int updateStock(int asset_id) {
		SqlSession sqlSession=dbManager.getSqlSession();
		int result=sqlSession.insert("Asset.updateStock", asset_id);
		sqlSession.commit();
		sqlSession.close();
		return result;
	}
	
	public int delete(int asset_id) {
		SqlSession sqlSession=dbManager.getSqlSession();
		int result=sqlSession.delete("Asset.deleteById", asset_id);
		sqlSession.commit();
		sqlSession.close();
		return result;
	}
	
	public int update(Asset asset) {
		SqlSession sqlSession=dbManager.getSqlSession();
		int result=sqlSession.update("Asset.updateById", asset);
		sqlSession.commit();
		sqlSession.close();
		return result;
	}
	
}
