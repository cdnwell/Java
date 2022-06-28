package blogsearch;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONObject;


public class BlogSearchEx1 {


    public static void main(String[] args) {
        String clientId = "1gT6ptILkTkiIXDqGrGi"; //애플리케이션 클라이언트 아이디값"
        String clientSecret = "7d6iOETonj"; //애플리케이션 클라이언트 시크릿값"
        Scanner sc = new Scanner(System.in);

        String text = null;
        String search = sc.nextLine();
        try {
            text = URLEncoder.encode(search, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("검색어 인코딩 실패",e);
        }


        String apiURL = "https://openapi.naver.com/v1/search/blog?query=" + text;    // json 결과
        //String apiURL = "https://openapi.naver.com/v1/search/blog.xml?query="+ text; // xml 결과


        Map<String, String> requestHeaders = new HashMap<>();
        requestHeaders.put("X-Naver-Client-Id", clientId);
        requestHeaders.put("X-Naver-Client-Secret", clientSecret);
        String responseBody = get(apiURL,requestHeaders);

//        System.out.println(responseBody);
        
        JSONObject json = new JSONObject(responseBody);
        
        fileWriter(responseBody,search);
        
    }

    public static void fileWriter(String content,String fileName) {
    	File file = new File(fileName+".html");
		FileWriter fw = null;
		PrintWriter pw = null;
		try {
			fw = new FileWriter(file);
			pw = new PrintWriter(fw);
//			pw.println("Hello World");
			
			JSONArray json = new JSONObject(content).getJSONArray("items");
			ArrayList<String> list = new ArrayList<>();
			
			for(int i = 0; i< json.length();i++) {
				JSONObject obj = json.getJSONObject(i);
				
				String strSum = new String();
				
				strSum += obj.getString("bloggername") + "\t"; 
				strSum += obj.getString("title") + "\t";
				strSum += obj.getString("postdate") + "\t";
				strSum += obj.getString("bloggerlink");
				
				list.add(strSum);
				
			}
			
			String result = writeHtml(list, fileName);
			
			pw.println(result);
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

    public static String writeHtml(ArrayList<String> list,String fileName) {
    	byte[] encode;
    	String tag = "";
    	
    	try {
			encode = Files.readAllBytes(Paths.get("java_template.html"));
			tag = new String(encode,"UTF-8");
			String result = "";
			
			result += "<tr><th>블로거이름</th><th>제목</th><th>작성일</th><th>링크</th></tr>";
	    	for(String str : list) {
	    		String cell[] = str.split("\t");
	    		result += "<tr>";
	    		for(int i =0;i<cell.length-1;i++) {
	    			result += "<td>"+cell[i]+"</td>";
	    		}
	    		result += "<td><a href='"+cell[cell.length-1]+"'>게시글 링크</a></td></tr>";
	    	}
	    	
	    	tag = tag.replace("{result}", result);
			
	    	return tag;
	    	
		} catch (IOException e) {
			e.printStackTrace();
		}
    	
    	return tag;
    	
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