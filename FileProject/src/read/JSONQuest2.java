package read;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

import org.json.JSONArray;
import org.json.JSONObject;

public class JSONQuest2 {

	public static void main(String[] args) {
		
		try {
			// 대용량 파일 읽어 들이기
			byte[] encode = Files.readAllBytes(Paths.get("city.list.json"));
			
			String r = new String(encode,"UTF-8");
			
			//상황에 따라서 JSONObject로도 시작 가능
			// 중괄호로 시작하면 오브젝트 대괄호면 배열
			JSONArray array = new JSONArray(r);
			for(int i =0;i<array.length();i++) {
				JSONObject obj = array.getJSONObject(i);
//				System.out.println(Arrays.toString(JSONObject.getNames(obj)));
				if(obj.getString("country").equals("KR")) {
					JSONObject coord = obj.getJSONObject("coord");
					System.out.println(obj.get("id")+" "+obj.get("name")+" "+obj.get("state")+" "
					+obj.get("country")+" ("+coord.get("lon")+","+coord.get("lat")+")");
				}
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
