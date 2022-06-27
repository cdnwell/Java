package blogsearch;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

public class BlogSearch {

	  public static void main(String[] args) {
	        String clientId = "1gT6ptILkTkiIXDqGrGi"; //애플리케이션 클라이언트 아이디값"
	        String clientSecret = "7d6iOETonj"; //애플리케이션 클라이언트 시크릿값"


	        String text = null;
	        try {
	            text = URLEncoder.encode("그린팩토리", "UTF-8");
	        } catch (UnsupportedEncodingException e) {
	            throw new RuntimeException("검색어 인코딩 실패",e);
	        }


	        String apiURL = "https://openapi.naver.com/v1/search/blog?query=" + text;    // json 결과
	        //String apiURL = "https://openapi.naver.com/v1/search/blog.xml?query="+ text; // xml 결과


	        Map<String, String> requestHeaders = new HashMap<>();
	        requestHeaders.put("X-Naver-Client-Id", clientId);
	        requestHeaders.put("X-Naver-Client-Secret", clientSecret);
	        String responseBody = get(apiURL,requestHeaders);

//	        System.out.println(responseBody);
	        
	        JSONObject json = new JSONObject(responseBody);
	        
	        JSONArray array = json.getJSONArray("items");
	        for(int i =0 ;i <array.length();i++) {
	        	JSONObject obj = array.getJSONObject(i);
	        	
	        	String bloggername = obj.getString("bloggername");
	        	int postdate = obj.getInt("postdate");
	        	String title = obj.getString("title");
	        	String bloggerlink = obj.getString("bloggerlink");
	        	
	        	System.out.println("블로거 이름 : " + bloggername);
	        	System.out.println("포스트 날짜 : " + postdate);
	        	System.out.println("글 제목 : " + title);
	        	System.out.println("블로그 링크 : " + bloggerlink);
	        	System.out.println();
	        }
	        
	        
	    }


	    private static String get(String apiUrl, Map<String, String> requestHeaders){
	        HttpURLConnection con = connect(apiUrl);
	        try {
	            con.setRequestMethod("GET");
	            for(Map.Entry<String, String> header :requestHeaders.entrySet()) {
	                con.setRequestProperty(header.getKey(), header.getValue());
	            }


	            int responseCode = con.getResponseCode();
	            if (responseCode == HttpURLConnection.HTTP_OK) { // 정상 호출
	                return readBody(con.getInputStream());
	            } else { // 에러 발생
	                return readBody(con.getErrorStream());
	            }
	        } catch (IOException e) {
	            throw new RuntimeException("API 요청과 응답 실패", e);
	        } finally {
	            con.disconnect();
	        }
	    }


	    private static HttpURLConnection connect(String apiUrl){
	        try {
	            URL url = new URL(apiUrl);
	            return (HttpURLConnection)url.openConnection();
	        } catch (MalformedURLException e) {
	            throw new RuntimeException("API URL이 잘못되었습니다. : " + apiUrl, e);
	        } catch (IOException e) {
	            throw new RuntimeException("연결이 실패했습니다. : " + apiUrl, e);
	        }
	    }


	    private static String readBody(InputStream body){
	        InputStreamReader streamReader = new InputStreamReader(body);


	        try (BufferedReader lineReader = new BufferedReader(streamReader)) {
	            StringBuilder responseBody = new StringBuilder();


	            String line;
	            while ((line = lineReader.readLine()) != null) {
	                responseBody.append(line);
	            }


	            return responseBody.toString();
	        } catch (IOException e) {
	            throw new RuntimeException("API 응답을 읽는데 실패했습니다.", e);
	        }
	    }

}
