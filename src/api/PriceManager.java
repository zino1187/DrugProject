package api;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.io.BufferedReader;
import java.io.IOException;

public class PriceManager {
	String Log = this.getClass().getName();
	
	
	public String[] getProductInfo(String apiKey, String product_name) throws IOException {
		StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/B551182/dgamtCrtrInfoService/getDgamtList"); /* URL */
		urlBuilder.append("?" + URLEncoder.encode("ServiceKey", "UTF-8") + "="+apiKey); /* Service Key */
		urlBuilder.append(
				"&" + URLEncoder.encode("itmNm", "UTF-8") + "=" + URLEncoder.encode(product_name, "UTF-8")); /* 파라미터설명 */
		
		//System.out.println(urlBuilder.toString());
		
		URL url = new URL(urlBuilder.toString());
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Content-type", "application/json");
		System.out.println("Response code: " + conn.getResponseCode());
		BufferedReader rd;
		if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
			rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		} else {
			rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
		}
		StringBuilder sb = new StringBuilder();
		String line;
		while ((line = rd.readLine()) != null) {
			sb.append(line);
		}
		rd.close();
		conn.disconnect();
		
		XMLSAXParser xmlsaxParser=new XMLSAXParser();
		xmlsaxParser.parse(sb.toString());
		String price=xmlsaxParser.getMxCprc();
		String totalCount=xmlsaxParser.getTotalCount();
		//System.out.println("가격은 "+price);
		
		return new String[] {totalCount,price};
	}
}