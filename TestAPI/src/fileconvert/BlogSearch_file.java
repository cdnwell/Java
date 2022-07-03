package fileconvert;
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
import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONObject;

public class BlogSearch_file {
	
	public static JSONObject blogSearch(String text) {
		String clientId = "1gT6ptILkTkiIXDqGrGi";
        String clientSecret = "7d6iOETonj";
        String apiURL = "https://openapi.naver.com/v1/search/blog";
        DataOutputStream dos = null;
        BufferedReader br = null;
        HttpURLConnection con = null;
        JSONObject result = null;
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		PrintStream printStream = new PrintStream(outStream);
		String printStackTraceStr = null;
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
			
			System.out.println(msg);
			JSONObject json = new JSONObject(msg);
			result = json;
			
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace(printStream);
			throw new RuntimeException("인코딩 실패", e);
		} catch (MalformedURLException e) {
			e.printStackTrace(printStream);
		} catch (IOException e) {
			e.printStackTrace(printStream);
		} finally {
			printStackTraceStr = outStream.toString();
			System.out.println(printStackTraceStr);
			errorWriter(printStackTraceStr);
		}
        
        return result;
        
	}

	public static void errorWriter(String error) {
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
	
	public static void fileWriter(JSONObject obj) {
		File file = new File("blog_search.txt");
		FileWriter fw = null;
		PrintWriter pw = null;
		
		try {
			fw = new FileWriter(file);
			pw = new PrintWriter(fw);
			
			JSONArray array = obj.getJSONArray("items");
			for(int i=0;i<array.length();i++) {
				JSONObject content = array.getJSONObject(i);
				
				pw.println("제목 : " + content.getString("title"));
				pw.println("블로거명 : " + content.getString("bloggername"));
				pw.println("내용요약 : " + content.getString("description"));
				pw.println("링크 : " + content.getString("link")+"\n");
				pw.flush();
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if(pw != null) pw.close();
				if(fw != null) fw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		String text = sc.nextLine();
		JSONObject json = blogSearch(text);
		fileWriter(json);
    }

}