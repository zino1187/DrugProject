package api;

import java.io.StringReader;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class XMLSAXParser extends DefaultHandler {

	// SAXParserFactory
	private SAXParserFactory parserFactory;

	// SAXParser
	private SAXParser parser;

	// 시작 태그명
	private String startTagName;

	// 끝 태그명
	private String endTagName;

	// String buffer
	private StringBuffer buffer = new StringBuffer();

	//  태그명 
	private String mxCprc;
	private String totalCount;

	// 생성자
	public XMLSAXParser() {
		super();
		try {
			parserFactory = SAXParserFactory.newInstance();
			parser = parserFactory.newSAXParser();
		} catch (Exception e) {
			System.out.println("Exception >> " + e.toString());
		}
	}

	// 문서의 시작
	public void startDocument() {
		// System.out.println("start document!!");
	}

	// 문서의 종료
	public void endDocument() {
		// System.out.println("end document!!");
	}

	// 시작 태그 인식했을 때 처리
	public void startElement(String url, String name, String elementName, Attributes attrs) throws SAXException {
		startTagName = elementName;
		// reset
		buffer.setLength(0);
		System.out.print("<"+startTagName+">");
	}

	// 시작태그와 끝태그 사이의 내용을 인식 했을 때 처리
	public void characters(char[] str, int start, int len) throws SAXException {
		buffer.append(str, start, len);

		// 태그명 중 'mxCprc'만 추출해서 변수에 담는다.
		if (this.startTagName.equals("mxCprc")) {
			this.mxCprc = buffer.toString().trim();
			System.out.print(this.mxCprc);
		}
		// 태그명 중 'totalCount'만 추출해서 변수에 담는다.
		if (this.startTagName.equals("totalCount")) {
			this.totalCount = buffer.toString().trim();
			System.out.print(this.totalCount);
		}
		
	}

	// 끝태그를 인식 했을 때 처리
	public void endElement(String url, String localName, String name) {
		endTagName = name;
		System.out.println("</"+endTagName+">");
	}

	// parse
	public void parse(String xml) {
		System.out.println("넘겨받은 xml "+xml);
		try {
			parser.parse(new InputSource(new StringReader(xml)) , this);
		} catch (Exception e) {
			System.out.println("XMLSAXParser Exception " + e.toString());
		}
	}

	// 추출 값 가져오기
	public String getMxCprc() {
		return mxCprc;
	}

	public String getTotalCount() {
		return totalCount;
	}
	
	/*	
	public static void main(String[] args) {
		XMLSAXParser parser = new XMLSAXParser("D:\\test.xml");

		try {
			parser.parse();

			System.out.println("x = " + parser.getX());
			System.out.println("y = " + parser.getY());
		} catch (Exception e) {
			System.out.println("parser.parse() Exception >> " + e.toString());
		}
	}
	 */
}
