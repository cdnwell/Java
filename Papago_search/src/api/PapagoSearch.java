package api;

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
import java.net.URL;
import java.net.URLEncoder;

import org.json.JSONObject;

public class PapagoSearch {

	public static JSONObject searchPapago(String text) {
		String clientId = "1gT6ptILkTkiIXDqGrGi";//애플리케이션 클라이언트 아이디값";
        String clientSecret = "7d6iOETonj";//애플리케이션 클라이언트 시크릿값";
        String apiURL = "https://openapi.naver.com/v1/papago/n2mt";
        HttpURLConnection con = null;
        BufferedReader br = null;
        JSONObject obj = null;
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outStream);
        boolean errorFlag = false;
        
        try {
        	
            text = URLEncoder.encode(text, "UTF-8");
            
            URL url = new URL(apiURL);
            con = (HttpURLConnection)url.openConnection();
            
            String postParams = "source=ko&target=en&text=" + text; //원본언어: 한국어 (ko) -> 목적언어: 영어 (en)
        	con.setRequestMethod("POST");
            
            con.setRequestProperty("X-Naver-Client-Id" , clientId);
            con.setRequestProperty("X-Naver-Client-Secret" , clientSecret);

            con.setDoOutput(true);
            try (DataOutputStream wr = new DataOutputStream(con.getOutputStream())) {
                wr.write(postParams.getBytes());
                wr.flush();
            }

            int responseCode = con.getResponseCode();
            
            if (responseCode == HttpURLConnection.HTTP_OK) { // 정상 응답
            	br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            } else {  // 에러 응답
            	br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            	errorFlag = true;
            }
        
            String msg = new String();
            while(true) {
            	String str = br.readLine();
            	if(str == null) break;
            	msg += str;
            }
            
            obj = new JSONObject(msg);
//            System.out.println(msg);
            
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace(printStream);
        } catch (IOException e) {
			e.printStackTrace(printStream);
		} finally {
			String printStackTraceStr = outStream.toString();
			errorWriter(printStackTraceStr);
		}

        return obj;
        
	}
	
	public static void textWriter(JSONObject obj) {
		File file = new File("papago_search.txt");
		FileWriter fw = null;
		PrintWriter pw = null;
		
		try {
			fw = new FileWriter(file);
			pw = new PrintWriter(fw);
			
			String result = obj.getJSONObject("message").getJSONObject("result").getString("translatedText");
			
			System.out.println(result);
			pw.println(result);
			pw.flush();
			
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
	
	public static void errorWriter(String error) {
		File file = new File("error_msg.txt");
		FileWriter fw = null;
		PrintWriter pw = null;
		
		try {
			fw = new FileWriter(file);
			pw = new PrintWriter(fw);
			
			pw.println(error);
			
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
	
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        JSONObject obj = searchPapago(br.readLine());
        textWriter(obj);
        
    }

}