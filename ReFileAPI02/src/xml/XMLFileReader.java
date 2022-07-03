package xml;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class XMLFileReader {
	/*
	 *  1. FileInputStream으로 xml파일을 생성자에 매개변수로 넣고 인스턴스 생성.
	 *  2. DocumentBuilderFactory 변수 생성, newInstance()로 인스턴스 받아옴
	 *  3. builder도 builderFactory에서 newDocumentBuilder로 인스턴스 받아옴
	 *  4. document 변수는 fis를 parse, parse메서드는 builder에 있음
	 *  
	 *  5. NodeList 인터페이스 document 객체에서 getElementsByTagName 메서드 이용해서 string 매개변수로 받아오기
	 *  매개변수는 xml의 태그를 의미한다.
	 *  6. NodeList 사용 메서드 정리
	 *    - item()
	 *    - getChildNodes()
	 *    - getLength()
	 *    - getNodeName()
	 *    - getTextContent()
	 */
	public static void main(String[] args) {
		FileInputStream fis = null;
		try {
			fis = new FileInputStream("app_card.xml");
			DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
			Document document = documentBuilder.parse(fis);
			
			NodeList tagList = document.getElementsByTagName("record");
			
			for(int i =0;i<tagList.getLength();i++) {
				NodeList childList = tagList.item(i).getChildNodes();
				int count = 0;
				for(int j=0;j<childList.getLength();j++) {
					if(childList.item(j).getNodeName().equals("price")) {
						int price = Integer.parseInt(childList.item(j).getTextContent());
						if( price>=2000 && price <4000) {
							count++;
						}
					}
					
					if(childList.item(j).getNodeName().equals("card_type")) {
						if(childList.item(j).getTextContent().equals("americanexpress")) {
							count++;
						}
					}
					
				}
				
				if(count == 2) {
					for(int j=0;j<childList.getLength();j++) {
						System.out.println(childList.item(j).getNodeName()+ " - "
								+ childList.item(j).getTextContent());
					}
					System.out.println();
				}
				
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
