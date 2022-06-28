package read;

import java.io.FileInputStream;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class XMLFileReader {

	public static void main(String[] args) {
//		byte[] encode;
		try {
			// 보통 encode해서 쓰진 않는다.
//			encode = Files.readAllBytes(Paths.get("app.xml"));
//			String r = new String(encode,"UTF-8");
			FileInputStream fis = new FileInputStream("app.xml");
			
			DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = builderFactory.newDocumentBuilder();
			Document document = builder.parse(fis);
//			Document document = builder.parse(new InputSource(r));
			document.getDocumentElement().normalize();
			// --- 준비 과정 --- //
			
			//자바스크립트 태그 읽어오는 것과 같다.
			NodeList tagList =  document.getElementsByTagName("record");
			System.out.println(tagList.getLength());
			System.out.println(tagList.item(0).getChildNodes().getLength());		// item : 일반 list에서 썼던 get과 같다.
			//getchildnodes : 자식 노드를 검색
			NodeList childList = tagList.item(0).getChildNodes();
			for(int i =0;i<childList.getLength();i++){
				System.out.println(childList.item(i).getNodeName()
						+"," + childList.item(i).getTextContent());
				// 잡음이 생겨서 걸러 줘야 함
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		}
		
	}

}
