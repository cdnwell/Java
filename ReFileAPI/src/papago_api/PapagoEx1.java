package papago_api;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

// 네이버 기계번역 (Papago SMT) API 예제
public class PapagoEx1 {
	public static String papagoTranslate(String target, String text) {
		String result = new String();
		String clientId = "1gT6ptILkTkiIXDqGrGi";	//애플리케이션 클라이언트 아이디값";
        String clientSecret = "7d6iOETonj";			//애플리케이션 클라이언트 시크릿값";
        String apiURL = "https://openapi.naver.com/v1/papago/n2mt";
        HttpURLConnection con = null;
        
//        try {
//            text = URLEncoder.encode("안녕하세요. 오늘 기분은 어떻습니까?", "UTF-8");
//        } catch (UnsupportedEncodingException e) {
//            throw new RuntimeException("인코딩 실패", e);
//        }
        
        Map<String, String> requestHeaders = new HashMap<>();
        requestHeaders.put("X-Naver-Client-Id", clientId);
        requestHeaders.put("X-Naver-Client-Secret", clientSecret);
        
        try {
            URL url = new URL(apiURL);
            
            con = (HttpURLConnection)url.openConnection();
            String postParams = "source=ko&target=" + target + "&text=" + text; //원본언어: 한국어 (ko) -> 목적언어: 영어 (en)
            try {
                con.setRequestMethod("POST");
                for(Map.Entry<String, String> header :requestHeaders.entrySet()) {
                    con.setRequestProperty(header.getKey(), header.getValue());
                }

                con.setDoOutput(true);
                try (DataOutputStream wr = new DataOutputStream(con.getOutputStream())) {
                    wr.write(postParams.getBytes());
                    wr.flush();
                }

                int responseCode = con.getResponseCode();
                
                if (responseCode == HttpURLConnection.HTTP_OK) { // 정상 응답
                //
	                InputStreamReader streamReader = new InputStreamReader(con.getInputStream());
	
	                try (BufferedReader lineReader = new BufferedReader(streamReader)) {
	                    StringBuilder responseBody = new StringBuilder();
	
	                    String line;
	                    while ((line = lineReader.readLine()) != null) {
	                        responseBody.append(line);
	                    }
	
	                    result += responseBody.toString();
	                    return result;
	                    
	                } catch (IOException e) {
	                    throw new RuntimeException("API 응답을 읽는데 실패했습니다.", e);
	                }
                //
                } else {
	            	//
	                InputStreamReader streamReader = new InputStreamReader(con.getInputStream());
	
	                try (BufferedReader lineReader = new BufferedReader(streamReader)) {
	                    StringBuilder responseBody = new StringBuilder();
	
	                    String line;
	                    while ((line = lineReader.readLine()) != null) {
	                        responseBody.append(line);
	                    }
	                    
	                    result += responseBody.toString();
	                    return result;
	                    
	                } catch (IOException e) {
	                    throw new RuntimeException("API 응답을 읽는데 실패했습니다.", e);
	                }
	                //
                }
                
            } catch (IOException e) {
                throw new RuntimeException("API 요청과 응답 실패", e);
            } finally {
                con.disconnect();
            }
            
        } catch (MalformedURLException e) {
            throw new RuntimeException("API URL이 잘못되었습니다. : " + apiURL, e);
        } catch (IOException e) {
            throw new RuntimeException("연결이 실패했습니다. : " + apiURL, e);
        }
        
	}
	
    public static void main(String[] args) {
    	Scanner sc = new Scanner(System.in);
    	String text = sc.nextLine();
    	
        String responseBody = papagoTranslate("en",text);

        System.out.println(responseBody);
    }

}
