import java.io.DataOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class BlogSearch_xml {
	public static void writeHTML(HashMap<String,ArrayList<HashMap<String,String>>> list,String fileName) {
		byte[] encode;
		try {
			encode = Files.readAllBytes(Paths.get("template.html"));
			String tag = new String(encode,"UTF-8");
			System.out.println(tag);
			String table = "<table>";
			table += "<tr><th>블로그명</th><th>작성일</th><th>글제목</th><th>링크</th></tr>";
//			for(HashMap<String,String> map : list1) {
//				table += "<tr>";
//				
//				table += "<td>"+ map.get("bloggername") +"</td>";
//				table += "<td>"+ map.get("postdate") +"</td>";
//				table += "<td>"+ map.get("title") +"</td>";
//				table += "<td><a href='"+map.get("link")+"'>해당 페이지 이동</a></td></tr>";
//				
//			}
//			table += "<tr><th>기사명</th><th>작성일</th><th>글설명</th><th>링크</th></tr>";
//			for(HashMap<String,String> map : list1) {
//				table += "<tr>";
//				
//				table += "<td>"+ map.get("title") +"</td>";
//				table += "<td>"+ map.get("pubDate") +"</td>";
//				table += "<td>"+ map.get("description") +"</td>";
//				table += "<td><a href='"+map.get("link")+"'>해당 페이지 이동</a></td></tr>";
//				
//			}
			table += "</table>";
			tag = tag.replace("{result}", table);
			System.out.println(tag);
			
			FileWriter fw = new FileWriter(fileName+".html");
			fw.write(tag);
			fw.flush();
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static ArrayList<HashMap<String, String>> blogSearch(String text) {
		String clientId = "_1rIIr0u6hwdD4VpqYnd";
        String clientSecret = "k5ERzutCdQ";
        String apiURL = "https://openapi.naver.com/v1/search/blog.xml";
        DataOutputStream dos = null;
        HttpURLConnection con = null;
        ArrayList<HashMap<String, String>> result = new ArrayList<HashMap<String, String>>();
        try {
			text = URLEncoder.encode(text,"UTF-8");
			
			String postParams = "?query=" + text;
			URL url = new URL(apiURL + postParams);
			con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");
			con.setRequestProperty("X-Naver-Client-Id", clientId);
			con.setRequestProperty("X-Naver-Client-Secret", clientSecret);
			
			con.setDoOutput(true);
			
			DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = builderFactory.newDocumentBuilder();
			Document document = null;
			
			int responseCode = con.getResponseCode();
			if (responseCode == HttpURLConnection.HTTP_OK) {
				document = builder.parse(con.getInputStream());
			} else {  
				document = builder.parse(con.getErrorStream());
			}

			document.getDocumentElement().normalize();
			
			NodeList nodeList = document.getElementsByTagName("item");
			for(int i=0;i<nodeList.getLength();i++) {
//				System.out.println(nodeList.item(i).getNodeName());
				NodeList childNodes = nodeList.item(i).getChildNodes();
				HashMap<String, String> map = new HashMap<String, String>();
				for(int j=0;j<childNodes.getLength();j++) {
//					System.out.println("\t" + childNodes.item(j).getNodeName() + " - " + childNodes.item(j).getTextContent());
					map.put(childNodes.item(j).getNodeName(), childNodes.item(j).getTextContent());
				}
				result.add(map);
			}
			
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("인코딩 실패", e);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		}
        return result;
	}
	
	public static ArrayList<HashMap<String, String>> newsSearch(String text){
		String clientId = "_1rIIr0u6hwdD4VpqYnd";
        String clientSecret = "k5ERzutCdQ";
        String apiURL = "https://openapi.naver.com/v1/search/news.xml";
        DataOutputStream dos = null;
        HttpURLConnection con = null;
        ArrayList<HashMap<String, String>> result = new ArrayList<HashMap<String, String>>();
        try {
			text = URLEncoder.encode(text,"UTF-8");
			
			String postParams = "?query=" + text;
			URL url = new URL(apiURL + postParams);
			con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");
			con.setRequestProperty("X-Naver-Client-Id", clientId);
			con.setRequestProperty("X-Naver-Client-Secret", clientSecret);
			
			con.setDoOutput(true);
			
			DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = builderFactory.newDocumentBuilder();
			Document document = null;
			
			int responseCode = con.getResponseCode();
			if (responseCode == HttpURLConnection.HTTP_OK) {
				document = builder.parse(con.getInputStream());
			} else {  
				document = builder.parse(con.getErrorStream());
			}

			document.getDocumentElement().normalize();
			
			System.out.println(document);
			
			NodeList nodeList = document.getElementsByTagName("item");
			for(int i=0;i<nodeList.getLength();i++) {
				System.out.println(nodeList.item(i).getNodeName());
				NodeList childNodes = nodeList.item(i).getChildNodes();
				HashMap<String, String> map = new HashMap<String, String>();
				for(int j=0;j<childNodes.getLength();j++) {
					System.out.println("\t" + childNodes.item(j).getNodeName() + " - " + childNodes.item(j).getTextContent());
					map.put(childNodes.item(j).getNodeName(), childNodes.item(j).getTextContent());
				}
				result.add(map);
			}
			
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("인코딩 실패", e);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		}
        return result;
	}
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.print("검색어 입력 : ");
		String text = sc.nextLine();
        ArrayList<HashMap<String,String>> list1 = blogSearch(text);
        ArrayList<HashMap<String,String>> list2 = blogSearch(text);
        HashMap<String,ArrayList<HashMap<String,String>>> map = new HashMap<>();
        map.put("blog", list1);
        map.put("news", list2);
        writeHTML(map, text);
    }

  
}