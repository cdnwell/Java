package fileconvert;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

public class Weather {
    public static void main(String[] args) throws IOException {
        StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getUltraSrtNcst"); /*URL*/
        urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=" +"vXrz1BmPoWyqzRJsxFpp0r557G5Yuoa6rUhVSXT%2FxeCyTRXhh%2FvBU3fj7%2FVNbXtxOZ21OLbJOIlIH2aSy1bJSA%3D%3D"); /*Service Key*/
        urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지번호*/
        urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("1000", "UTF-8")); /*한 페이지 결과 수*/
        urlBuilder.append("&" + URLEncoder.encode("dataType","UTF-8") + "=" + URLEncoder.encode("JSON", "UTF-8")); /*요청자료형식(XML/JSON) Default: XML*/
        urlBuilder.append("&" + URLEncoder.encode("base_date","UTF-8") + "=" + URLEncoder.encode("20220629", "UTF-8")); /*‘21년 6월 28일 발표*/
        urlBuilder.append("&" + URLEncoder.encode("base_time","UTF-8") + "=" + URLEncoder.encode("1400", "UTF-8")); /*06시 발표(정시단위) */
        urlBuilder.append("&" + URLEncoder.encode("nx","UTF-8") + "=" + URLEncoder.encode("55", "UTF-8")); /*예보지점의 X 좌표값*/
        urlBuilder.append("&" + URLEncoder.encode("ny","UTF-8") + "=" + URLEncoder.encode("127", "UTF-8")); /*예보지점의 Y 좌표값*/
        URL url = new URL(urlBuilder.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");
//        System.out.println("Response code: " + conn.getResponseCode());
        BufferedReader rd;
        if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }
        rd.close();
        conn.disconnect();
        String content = sb.toString();
//        System.out.println(sb.toString());
        
        JSONObject obj = new JSONObject(content);
        JSONArray array = obj.getJSONObject("response").getJSONObject("body").getJSONObject("items").getJSONArray("item");
        
        // 1시간 강수량 RN1
        // 습도 REH
        // 하늘상태 SKY
        // 기온 T1H
        // 풍속 WSD : m/s
        ArrayList<String> list = new ArrayList<>(); 
        
        for(int i =0;i<array.length();i++) {
        	JSONObject in = array.getJSONObject(i);
        	String item = in.getString("category");
        	
        	switch(item) {
        	case "RN1":
        		System.out.println("1시간 강수량 : " + in.getString("obsrValue"));
        		list.add("1시간 강수량 : " + in.getString("obsrValue"));
        		break;
        	case "REH":
        		System.out.println("습도 : " + in.getString("obsrValue"));
        		list.add("습도 : " + in.getString("obsrValue"));
        		break;
        	case "T1H":
        		System.out.println("기온 : " + in.getString("obsrValue") + "℃");
        		list.add("기온 : " + in.getString("obsrValue") + "℃");
        		break;
        	case "WSD":
        		System.out.println("풍속 : " + in.getString("obsrValue") + "m/s");
        		list.add("풍속 : " + in.getString("obsrValue") + "m/s");
        		break;
        	}
        	
        }
        
        /*
    	 *  1. File 객체 생성, 경로는 상대경로, 만들 텍스트의 이름 매개변수에 입력(String)
    	 *  2. FileWriter, PrintWriter
    	 *  3. pw.println(str)
    	 *  4. try catch finally 마무리 
    	 */
        
        File file = new File("write02.txt");
        FileWriter fw = new FileWriter(file);
        PrintWriter pw = new PrintWriter(fw);
        
        for(String str : list) {
        	fw.write(str+"\n");
        	fw.flush();
        }
        
    }
}