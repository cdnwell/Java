package APIs;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import org.json.JSONArray;
import org.json.JSONObject;

public class BlogSearch {

	public static JSONObject searchBlog(String text) {
		String clientId = "1gT6ptILkTkiIXDqGrGi"; // 애플리케이션 클라이언트 아이디값"
		String clientSecret = "7d6iOETonj"; // 애플리케이션 클라이언트 시크릿값"
		HttpURLConnection con = null;
		BufferedReader br = null;
		JSONObject obj = null;
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		PrintStream printStream = new PrintStream(outStream);
		
		try {
			text = URLEncoder.encode(text, "UTF-8");
			
			String apiURL = "https://openapi.naver.com/v1/search/blog?query=" + text; // json 결과
			
			URL url = new URL(apiURL);
			con = (HttpURLConnection)url.openConnection();
			
			con.setRequestMethod("GET");
			con.setRequestProperty("X-Naver-Client-Id", clientId);
			con.setRequestProperty("X-Naver-Client-Secret", clientSecret);
			
			int responseCode = con.getResponseCode();
			
			if (responseCode == HttpURLConnection.HTTP_OK) { // 정상 호출
				br = new BufferedReader(new InputStreamReader(con.getInputStream()));
			} else { // 에러 발생
				br = new BufferedReader(new InputStreamReader(con.getInputStream()));
			}
			
			String str = new String();
			String content = new String();
			while((str = br.readLine()) != null) {
				content += str;
			}
			
			System.out.println(content);
			
			obj = new JSONObject(content);
			
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace(printStream);
		} catch (IOException e) {
			e.printStackTrace(printStream);
		} finally {
			String errorStr = outStream.toString();
			errorWriter(errorStr);
		}
	
		return obj;

	}

	public static void textWriter(JSONObject obj) {
		File file = new File("blog_search.txt");
		FileWriter fw = null;
		PrintWriter pw = null;
		
		
		try {
			fw = new FileWriter(file);
			pw = new PrintWriter(fw);
			
			JSONArray tagList = obj.getJSONArray("items");
			for(int i=0;i<tagList.length();i++) {
				JSONObject tag = tagList.getJSONObject(i);
				
				String postdate = tag.getString("postdate");
				String title = tag.getString("title");
				String bloggername = tag.getString("bloggername");
				String description = tag.getString("description");
				String link = tag.getString("link");
				
				pw.println("포스트 날짜 : "+postdate);
				pw.println("제목 : "+title);
				pw.println("블로거이름 : "+bloggername);
				pw.println("내용 : "+description);
				pw.println("링크 : "+link);
				pw.println();
				pw.flush();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if(pw != null) pw.close();
				if(fw != null) fw.close();
			}catch(IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void errorWriter(String error) {
		File file = new File("error_msg.txt");
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
				if(pw != null) pw.close();
				if(fw != null) fw.close();
			}catch(IOException e) {
				e.printStackTrace();
			}
		}
		
		
	}
	
	public static void main(String[] args) {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		try {
			JSONObject obj = searchBlog(br.readLine());
			textWriter(obj);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}