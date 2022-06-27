import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import org.json.JSONObject;

public class PaPagoEx1 {
	public static String papagoTranslate(String target, String text) {
		String clientId = "1gT6ptILkTkiIXDqGrGi";
		String clientSecret = "7d6iOETonj";
		String apiURL = "https://openapi.naver.com/v1/papago/n2mt";
		DataOutputStream dos = null;
		BufferedReader br = null;
		HttpURLConnection con = null;
		String result = null;
		try {
			text = URLEncoder.encode(text, "UTF-8");

			URL url = new URL(apiURL);
			con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("POST");
			con.setRequestProperty("X-Naver-Client-Id", clientId);
			con.setRequestProperty("X-Naver-Client-Secret", clientSecret);
			String postParams = "source=ko&target=" + target + "&text=" + text;

			con.setDoOutput(true);
			dos = new DataOutputStream(con.getOutputStream());
			dos.writeBytes(postParams);
			dos.flush();
			dos.close();

			int responseCode = con.getResponseCode();
			if (responseCode == HttpURLConnection.HTTP_OK) {
				br = new BufferedReader(new InputStreamReader(con.getInputStream()));
			} else {
				br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
			}
			
			String msg = new String();

			while (true) {
				String str = br.readLine();
				if (str == null)
					break;
				msg += str;
			}

//			JSONObject obj = new JSONObject(msg);
//			JSONObject obj2 = obj.getJSONObject("message");
//			JSONObject obj3 = obj2.getJSONObject("result");
//			String contents = obj3.getString("translatedText");
//			
//			System.out.println(contents);
			
//			[풀이]
			JSONObject json = new JSONObject(msg);
			result = json.getJSONObject("message").getJSONObject("result").getString("translatedText");
			
			
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("인코딩 실패", e);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

	public static void main(String[] args) {

		String result = papagoTranslate("en", "지금은 장마기간 입니다.");
		System.out.println(result);

	}

}
