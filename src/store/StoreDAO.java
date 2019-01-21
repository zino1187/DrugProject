package store;
import java.util.List;
import org.apache.ibatis.session.SqlSession;

import db.DBManager;

public class StoreDAO {
	DBManager dbManager=new DBManager();
	
	public int regist(Store store) {
		int result=0;
		SqlSession sqlSession=dbManager.getSqlSession();
		result=sqlSession.insert("Store.insert", store);
		sqlSession.commit();
		sqlSession.close();
		return result;
	}
	
	public List selectAll() {
		List list=null;
		SqlSession sqlSession=dbManager.getSqlSession();
		list=sqlSession.selectList("Store.selectAll");
		sqlSession.close();
		return list;
	}
}



