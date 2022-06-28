package read;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class FileReadMain {

	public static void main(String[] args) {
		// 1. 파일오픈 - write.txt
		File file = new File("write.txt");
		// 2. 스트림 생성 - FileReader, BufferedReader
		// 스트림은 항상 노드 스트림 부터 만들어 줘야 한다.
		FileReader fr = null;
		BufferedReader br = null;
	
		try {
			fr = new FileReader(file);
			br = new BufferedReader(fr);
			// 스트림 생성 끝
			// 3. 파일 내용 읽기
			while(true) {
				String s = br.readLine();
				if( s == null)
					break;
				System.out.println(s);
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				// 4. 스트림 닫기
				// json, xml 풀어서 자료에 접근하는 것 문제에 나옴
				if(br != null) br.close();
				if(fr != null) fr.close();
			}catch(IOException e) {
				e.printStackTrace();
			}
		}
		
		
	}

}
