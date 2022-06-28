package quest;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.json.JSONArray;
import org.json.JSONObject;

public class JSONQuest2 {
	/*
	 * 	[중간실습]
	 *	city.list.json에 있는 내용 중 
	 * 	한국 도시만 꺼내서 출력
	 * 
	 *  JSONArray
	 *  JSONObject
	 *  getJSONObject(메서드)
	 *  
	 *  id, name, state, country
	 *  coord : lon, lat
	 *  
	 */
	public static void main(String[] args) {
		try {
			byte[] encode = Files.readAllBytes(Paths.get("city.list.json"));
			String r = new String(encode,"UTF-8");
			
			JSONArray array = new JSONArray(r);
			for(int i =0 ;i<array.length();i++) {
				JSONObject obj = array.getJSONObject(i);
				
				if(obj.getString("country").equals("KR")) {
					System.out.print(obj.get("id")+" "+obj.get("name")+" "+obj.get("state")+" "+obj.get("country")+" ");
					JSONObject corObj = obj.getJSONObject("coord");
					System.out.println(corObj.get("lon")+" "+corObj.get("lat"));
				}
				
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
