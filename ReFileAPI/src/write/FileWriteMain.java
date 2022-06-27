package write;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class FileWriteMain {

	public static void main(String[] args) {
		File file = new File("write.txt");
		FileWriter fw = null;
		PrintWriter pw = null;
		
		try {
			fw = new FileWriter(file);
			pw = new PrintWriter(fw);
			
			pw.println("장마가 계속 되겠습니다.");
			pw.println("[날씨 예보]");
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

}
