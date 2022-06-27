package htmlconvert;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONObject;

public class ConvertHtml {

	public static ArrayList<String> blogSearch(String text) {
		String clientId = "1gT6ptILkTkiIXDqGrGi";
		String clientSecret = "7d6iOETonj";
		String apiURL = "https://openapi.naver.com/v1/search/blog";
		DataOutputStream dos = null;
		BufferedReader br = null;
		HttpURLConnection con = null;
		ArrayList<String> result = new ArrayList<>();
		try {
			text = URLEncoder.encode(text, "UTF-8");

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

			while (true) {
				String str = br.readLine();
				if (str == null)
					break;
				msg += str;
			}

//			System.out.println(msg);

//			[풀이]

			JSONObject json = new JSONObject(msg);
			JSONArray arr = json.getJSONArray("items");
			for (int i = 0; i < arr.length(); i++) {
				JSONObject obj = arr.getJSONObject(i);
				result.add(obj.getString("bloggername") + "\t" + obj.getString("postdate") + "\t"
						+ obj.getString("title") + "\t" + obj.getString("link") + "\t");
			}

		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("인코딩 실패", e);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}// method 끝

	public static void fileWriter(ArrayList<String> list) {
		File file = new File("검색어.html");
		FileWriter fw = null;
		PrintWriter pw = null;
		try {
			fw = new FileWriter(file);
			pw = new PrintWriter(fw);
			pw.println("<!DOCTYPE html>" + "<html lang=\"ko\">");
			pw.println("<body><table>");

			// ----------------------------------------------//

			for (String str : list) {
				//1.블로거 이름
				//2.포스트 날짜
				//3.제목
				//4.링크
				String bloggername = str.split("\t")[0];
				String postdate = str.split("\t")[1];
				String title = str.split("\t")[2];
				String link = str.split("\t")[3];
				
				pw.println("<tr>");
				pw.println("<td>"+bloggername+"</td>");
				pw.println("<td>"+postdate+"</td>");
				pw.println("<td>"+title+"</td>");
				pw.println("<td><a href=\""+link+"\">"+link+"</a></td>");
				pw.println("</tr>");
				
			}
			

//			JSONObject json = new JSONObject(responseBody);
//
//			JSONArray array = json.getJSONArray("items");
//			for (int i = 0; i < array.length(); i++) {
//				JSONObject obj = array.getJSONObject(i);
//
//				String bloggername = obj.getString("bloggername");
//				int postdate = obj.getInt("postdate");
//				String title = obj.getString("title");
//				String bloggerlink = obj.getString("bloggerlink");
//
//				System.out.println("블로거 이름 : " + bloggername);
//				System.out.println("포스트 날짜 : " + postdate);
//				System.out.println("글 제목 : " + title);
//				System.out.println("블로그 링크 : " + bloggerlink);
//				System.out.println();
//			}

			// ----------------------------------------------//
			
			
			pw.println("</table></body></html>");

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (pw != null)
					pw.close();
				if (fw != null)
					fw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);
		String text = sc.nextLine();
		ArrayList<String> result = blogSearch(text);
		fileWriter(result);
		System.out.println("파일 입력이 되었습니다.");

	}

}
