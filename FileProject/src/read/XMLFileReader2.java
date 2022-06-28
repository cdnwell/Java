package read;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class XMLFileReader2 {

	public static void main(String[] args) {
		//dataset.xml 파일을 읽어서 모든 내용을 출력
		FileInputStream fis;
		try {
			fis = new FileInputStream("dataset.xml");
			DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = builderFactory.newDocumentBuilder();
			Document document = builder.parse(fis);
			document.getDocumentElement().normalize();
			
			NodeList tagList =  document.getElementsByTagName("record");
			// 둘 다 상관없다.
//			NodeList tagList =  document.getElementsByTagName("dataset");
			
//			for(int i =0;i<tagList.getLength();i++) {
//				NodeList childList = tagList.item(i).getChildNodes();
//				System.out.println(i);
//				for(int j =0;j<childList.getLength();j++){
//					System.out.println(childList.item(j).getNodeName()
//							+"," + childList.item(j).getTextContent());
//				}
//			}
			
			for(int i =0;i<tagList.getLength();i++) {
				System.out.println(tagList.item(i).getNodeName());
				NodeList childNodes = tagList.item(i).getChildNodes();
				for(int j = 0; j<childNodes.getLength();j++) {
					System.out.println("\t"+childNodes.item(j).getNodeName()+" - "
				+ childNodes.item(j).getTextContent());
					
				}
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	
		
	}

}
