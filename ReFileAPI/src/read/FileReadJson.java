package read;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class FileReadJson {

	public static void main(String[] args) {
		File file = new File("data.json");
		FileReader fr = null;
		BufferedReader br = null;
		String str = new String();
		
		try {
			fr= new FileReader(file);
			br = new BufferedReader(fr);
			
			while(true) {
				String s = br.readLine();
				if(s == null) break;
				str += s + " ";
			}
			System.out.println(str);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if(br != null) br.close();
				if(fr != null) fr.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		
	}

}
