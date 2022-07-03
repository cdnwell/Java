import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONObject;

public class BlogSearch_html_mod {
	
	public static void writeHTML(ArrayList<String> list,String fileName) {
		
		byte[] encode;
		try {
			encode = Files.readAllBytes(Paths.get("template.html"));
			String tag = new String(encode,"UTF-8");
			String table = "<table><tr><th>블로그명</th><th>작성일</th><th>글제목</th><th>링크</th></tr>";
			for(String row : list) {
				String cell[] = row.split("\t");
				table += "<tr>";
				
				for(int i=0;i<cell.length-1;i++) {
					table += "<td>" + cell[i] + "</td>";
				}
				table += "<td><a href='"+cell[cell.length-1]+"'>해당 페이지로 이동</a></td>";
				table += "</tr>";
			}
			table += "</table>";
			tag = tag.replace("{result}", table);
			
			FileWriter fw = new FileWriter(fileName+".html");
			fw.write(tag);
			fw.flush();
			fw.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public static ArrayList<String> blogSearch(String text) {
		String clientId = "1gT6ptILkTkiIXDqGrGi";
        String clientSecret = "7d6iOETonj";
        String apiURL = "https://openapi.naver.com/v1/search/blog";
        DataOutputStream dos = null;
        BufferedReader br = null;
        HttpURLConnection con = null;
        ArrayList<String> result = new ArrayList<String>();
        try {
			text = URLEncoder.encode(text,"UTF-8");
			
			String postParams = "?query=" + text;
			URL url = new URL(apiURL + postParams);
			con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");
			con.setRequestProperty("X-Naver-Client-Id", clientId);
			con.setRequestProperty("X-Naver-Client-Secret", clientSecret);
			
			con.setDoOutput(true);
			
			int responseCode = con.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
            	 br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            } else {  
            	br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
            }
			String msg = new String();
			
			while(true) {
				String str = br.readLine();
				if(str==null) break;
				msg += str;
			}
			
			JSONObject json = new JSONObject(msg);
			JSONArray arr = json.getJSONArray("items");
			for(int i=0;i<arr.length();i++) {
				JSONObject obj = arr.getJSONObject(i);
				result.add(obj.getString("bloggername") + "\t" + obj.getString("postdate")+ "\t" + obj.getString("title")+ "\t" + obj.getString("link"));
			}
			
			throw new UnsupportedEncodingException();
			
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			String str = e.getMessage();
			fileWriter(str);
			throw new RuntimeException("인코딩 실패", e);
		} catch (MalformedURLException e) {
			ByteArrayOutputStream outStream = new ByteArrayOutputStream();
			PrintStream printStream = new PrintStream(outStream);
			e.printStackTrace(printStream);
			String printStackTraceStr = outStream.toString();
			System.out.println(printStackTraceStr);
			fileWriter(printStackTraceStr);
		} catch (IOException e) {
			ByteArrayOutputStream outStream = new ByteArrayOutputStream();
			PrintStream printStream = new PrintStream(outStream);
			e.printStackTrace(printStream);
			String printStackTraceStr = outStream.toString();
			System.out.println(printStackTraceStr);
			fileWriter(printStackTraceStr);
		} 
        
        return result;
        
	}

	public static void fileWriter(String error) {
		File file = new File("error1.txt");
		FileWriter fw = null;
		PrintWriter pw = null;
		
		try {
			fw = new FileWriter(file);
			pw = new PrintWriter(fw);
			
			pw.println(error);
			pw.flush();
			
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if(fw != null) fw.close();
				if(pw != null) pw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		String text = sc.nextLine();
//        ArrayList<String> list = blogSearch(text);
		blogSearch(text);
//        for(String str : list) {
//        	System.out.println(str);
//        }
//        writeHTML(list,text);
    }

}