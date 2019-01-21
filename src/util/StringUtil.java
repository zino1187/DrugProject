package util;
public class StringUtil{
	
	public static String getProjectPath(String path) {
		int last=path.lastIndexOf("\\")-4;
		String result=path.substring(0 , last);
		//System.out.println(result);
		return result;
	}
	
/*	
	public static void main(String[] args) {
		String result=StringUtil.getProjectPath("E:\\workspace\\spring_workspace\\DrugProject\\src\\main");
	}
*/	
}
