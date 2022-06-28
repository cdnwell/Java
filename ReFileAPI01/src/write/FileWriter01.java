package write;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

public class FileWriter01 {

	public static void main(String[] args) {
		File file = new File("write.txt");
		FileReader fr = null;
		BufferedReader br = null;

		try {
			fr = new FileReader(file);
			br = new BufferedReader(fr);
			String tmp = new String();

			while (true) {
				String s = br.readLine();
				if (s == null)
					break;
				tmp += s;
			}

			StringReader sr = new StringReader(tmp);
			StringWriter sw = new StringWriter();
			int ch;

			while ((ch = sr.read()) != -1) {
				sw.write(ch);
			}
			System.out.println(sw.toString());

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
