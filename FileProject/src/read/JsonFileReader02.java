package read;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONObject;

public class JsonFileReader02 {

	public static void main(String[] args) {
		File file = new File("data.json");
		FileReader fr = null;
		BufferedReader br = null;
		
		try {
			fr = new FileReader(file);
			br = new BufferedReader(fr);
			String result = new String();
			while(true) {
				String s = br.readLine();
				if(s==null) break;
				result += s;
			}
//			System.out.println(result);
			//JSON으로 변환해서 출력
			//JSONobject 중괄호 열고 닫고, json객체 하나 읽기
			//대괄호는 Array 전체 데이터, JSONarray안에 json 객체 여러개
			//JSON으로 바꿔서 출력
			
			JSONArray arr = new JSONArray(result);
			//1000개 나옴 데이터
//			System.out.println(arr.length());
			
			//한번에 다 읽어온 후 데이터 가공
			
			for(int i=0; i<arr.length();i++) {
				//System.out.println(arr.get(i));
				//JSON객체 한건을 가져와서 파싱함
				JSONObject obj = arr.getJSONObject(i);
				int num = (int)obj.get("id");
				String gender = (String)obj.get("gender");
				if(gender.equals("Male") && num % 3 == 0)
					System.out.println(obj.get("id")+ " " + obj.get("first_name")+ " "+ obj.get("last_name")+ " "
				 + obj.get("email")+ " " + obj.get("gender")+ " " + obj.get("ip_address"));
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if(fr != null) fr.close();
				if(br != null) br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}

}
