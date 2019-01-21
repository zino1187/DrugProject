package db;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import util.StringUtil;

public class DBManager {	
	String resource = "db/mybatis-config.xml";
	InputStream inputStream;
	SqlSessionFactory sqlSessionFactory;

	public DBManager() {
		try {
			inputStream = Resources.getResourceAsStream(resource);
			sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public boolean runServer() {
		String projectRoot=System.getProperty("user.dir");
		String command=projectRoot+"/mariadb/bin/mysqld.exe";
		
		try {
			Process process=Runtime.getRuntime().exec(command);
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public SqlSession getSqlSession() {
		SqlSession sqlSession=null;
		sqlSession=sqlSessionFactory.openSession();
		return sqlSession;
	}
	
	/*	
	public static void main(String[] args) {
		DBManager manager=new DBManager();
		manager.runServer();
		StoreDAO dao = new StoreDAO();
		List list=(List)dao.selectAll();
		Store store=(Store)list.get(0);
		System.out.println(store.getName());
	}
 	*/		
}

