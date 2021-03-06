package write;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class FileWriteMain {
	/*
	 *  1. File 객체 생성, 경로는 상대경로, 만들 텍스트의 이름 매개변수에 입력(String)
	 *  2. FileWriter, PrintWriter
	 *  3. pw.println(str)
	 *  4. try catch finally 마무리 
	 */
	public static void main(String[] args) {
		// 1. 파일 열기
		File file = new File("write.txt");
		// 2. 스트림 생성, 입력/출력 구분
		FileWriter fw = null;
		//멀티바이트, 문자열 전송, 한글이나 텍스트 전송에 유리하다.
		//FileOutputStream fos;		//Stream으로 끝나며 바이트 단위, 한글 쓸 때 깨질 수 있음
									//바이너리나 이진파일 주로 사용, 정수와 같은 이진파일 전송에 유리
		PrintWriter pw = null;
		try {
			//안에 선언하면 지역변수라 바로 없어짐, 밖에 선언해야 닫아줄 수 있다.
			fw = new FileWriter(file);
			pw = new PrintWriter(fw);
			// 3. 입출력 수행
			pw.println("Hello World");
			pw.println("안녕하세요");
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// 4. 스트림 닫아주기
			try {
				if(pw != null) pw.close();
				if(fw != null) fw.close();
			}catch(IOException e) {
				e.printStackTrace();
			}
		}
		
	}

}
