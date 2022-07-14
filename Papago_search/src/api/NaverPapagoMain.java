package api;

import java.util.Scanner;

import org.json.JSONObject;

public class NaverPapagoMain {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		String text = sc.nextLine();
		NaverPapagoRun.getInstance().searchPapago(text);
		
	}

}
