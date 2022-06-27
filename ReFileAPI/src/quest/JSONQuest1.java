package quest;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONObject;

public class JSONQuest1 {
	/*
	 * 	[중간실습]
	 * 
	 *	data.json 파일에서 성별이 남자이고
	 *	id 값이 3의 배수인 사람만 조회
	 *
	 *	JSONArray
	 *	JSONObject
	 *	getJSONObject(메서드)
	 *
	 */
	public static void main(String[] args) {
		File file = new File("data.json");
		FileReader fr = null;
		BufferedReader br = null;
		String json = new String();
		
		try {
			fr = new FileReader(file);
			br = new BufferedReader(fr);
			
			while(true) {
				String s = br.readLine();
				if(s == null) break;
				json += s + " ";
			}
			
			JSONArray array = new JSONArray(json);
			for(int i = 0; i< array.length();i++) {
				JSONObject obj = array.getJSONObject(i);
				
				if(obj.getString("gender").equals("Male") && obj.getInt("id") % 3 == 0) {
					System.out.println(obj.get("id")+" "+obj.get("first_name")+" "+obj.get("last_name")+" "+obj.get("email")+" "
							+obj.get("gender")+" "+obj.get("ip_address"));
				}
				
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if(br != null) br.close();
				if(fr != null) fr.close();
			}catch(IOException e) {
				e.printStackTrace();
			}
		}
		
	}

}
