package check;

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
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;

import org.json.JSONArray;
import org.json.JSONObject;

public class NewsSearch {
	private static ByteArrayOutputStream outStream = new ByteArrayOutputStream();
	private static PrintStream printStream = new PrintStream(outStream);
	private static String printStackTraceStr = null;
	
	
	public static JSONObject newsSearch(String text) {
		String clientId = "1gT6ptILkTkiIXDqGrGi";
		String clientSecret = "7d6iOETonj";
		HttpURLConnection con = null;
		BufferedReader br = null;
		JSONObject result = null;
		
		try {
			text = URLEncoder.encode(text, "UTF-8");
			String apiURL = "https://openapi.naver.com/v1/search/news.json?query="+text+"&display=10&start=1&sort=sim"; // json 결과

			URL url = new URL(apiURL);
			con = (HttpURLConnection) url.openConnection();

			con.setRequestMethod("GET");
			con.setRequestProperty("X-Naver-Client-Id", clientId);
			con.setRequestProperty("X-Naver-Client-Secret", clientSecret);

			int responseCode = con.getResponseCode();
			if (responseCode == HttpURLConnection.HTTP_OK) { // 정상 호출
				br = new BufferedReader(new InputStreamReader(con.getInputStream(),"UTF-8"));
			} else { // 에러 발생
				br = new BufferedReader(new InputStreamReader(con.getInputStream(),"UTF-8"));
			}

			String str = new String();

			while (true) {
				String s = br.readLine();
				if (s == null)
					break;
				str += s;
			}
			
			JSONObject obj = new JSONObject(str);
			result = obj;

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace(printStream);
		} catch (MalformedURLException e) {
			e.printStackTrace(printStream);
		} catch (ProtocolException e) {
			e.printStackTrace(printStream);
		} catch (IOException e) {
			e.printStackTrace(printStream);
		} finally {
			printStackTraceStr = outStream.toString();
			errorWriter(printStackTraceStr);
			System.out.println(printStackTraceStr);
			
		}

		return result;

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
			} catch(IOException e){
				e.printStackTrace();
			}
		}
	}
	
	public static void fileWriter(JSONObject obj) {
		File file = new File("news_search.txt");
		FileWriter fw = null;
		PrintWriter pw = null;
		
		try {
			fw = new FileWriter(file);
			pw = new PrintWriter(fw);
			
			JSONArray array = obj.getJSONArray("items");
			String pubDate = "";
			String title = "";
			String description = "";
			String link = "";
			
			for(int i=0;i<array.length();i++) {
				JSONObject content = array.getJSONObject(i);
				title = content.getString("title");
				description = content.getString("description");
				pubDate = content.getString("pubDate");
				link = content.getString("link");
				
				pw.println("작성일 : "+pubDate);
				pw.println("제목 : "+title);
				pw.println("내용 : "+description);
				pw.println("링크 : "+link);
				pw.println();
				pw.flush();
				
			}
			
		} catch (IOException e) {
			e.printStackTrace(printStream);
		} finally {
			printStackTraceStr = outStream.toString();
			errorWriter(printStackTraceStr);
		}
		
	}

	public static void main(String[] args) throws IOException {
		BufferedReader scan = new BufferedReader(new InputStreamReader(System.in));
		
		String text = scan.readLine();
		JSONObject result = newsSearch(text);
		System.out.println(result);
		fileWriter(result);
		
	}

}