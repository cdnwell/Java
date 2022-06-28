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

public class XMLFileReaderQuest1_mod {

	public static void main(String[] args) {
		FileInputStream fis;
		try {
			fis = new FileInputStream("app_card.xml");
			DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = builderFactory.newDocumentBuilder();
			Document document = builder.parse(fis);
			document.getDocumentElement().normalize();

			NodeList tagList = document.getElementsByTagName("record");

			for (int i = 0; i < tagList.getLength(); i++) {

				NodeList childNodes = tagList.item(i).getChildNodes();

				System.out.println(tagList.item(i).getNodeName());
				for (int j = 0; j < childNodes.getLength(); j++) {
					System.out.println(
							"\t" + childNodes.item(j).getNodeName() + " - " 
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
