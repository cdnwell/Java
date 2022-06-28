package read;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class FileReadJson {

	public static void main(String[] args) {
		
		File file = new File("data.json");
		// 2. 스트림 생성 - FileReader, BufferedReader
		// 스트림은 항상 노드 스트림 부터 만들어 줘야 한다.
		FileReader fr = null;
		BufferedReader br = null;
		String jsonBox = new String();
		
		try {
			fr = new FileReader(file);
			br = new BufferedReader(fr);
			// 스트림 생성 끝
			// 3. 파일 내용 읽기
			
			while(true) {
				String s = br.readLine();
				if( s == null)
					break;
				
				jsonBox = jsonBox + s + " ";
				//줄바꿈 넣지 말기
//				System.out.println(s);
			}
			System.out.println(jsonBox);
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
