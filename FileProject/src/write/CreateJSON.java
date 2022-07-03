package write;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

public class CreateJSON {
	/*
	 *  1. EmployeeVO 객체 3개 만들기
	 *  2. JSONObject 객체 3개 만들어서 각각 순서에 맞게 넣어주기
	 *  3. print
	 * 
	 *  4. EmployeeVO list 만들기
	 *  5. list에 만들어둔 객체 3개 추가하기 (ArrayList)
	 *  6. JSONArray 객체 하나 만들기, 인스턴스 만들 때 list 매개변수로 보내주기
	 *  6-1.print.
	 *  
	 *  7. JSONObject 객체 하나 만들기
	 *  8. put으로 값 넣어주기, (resultCode, 200) 한 묶음
	 *  9. (items, array - JSON 배열) 한 묶음
	 *  10. 값 두개 넣어주기
	 *  11. print.
	 *  
	 */
	public static void main(String[] args) {
		//Employee 객체 3개 만들기
		EmployeeVO vo1 = new EmployeeVO("001", "홍길동", "과장", "토목부", 1200);
		EmployeeVO vo2 = new EmployeeVO("101", "김영동", "차장", "공학부", 3500);
		EmployeeVO vo3 = new EmployeeVO("203", "이홍길", "팀장", "회계부", 6200);
		
		JSONObject json1 = new JSONObject(vo1);
		JSONObject json2 = new JSONObject(vo2);
		JSONObject json3 = new JSONObject(vo3);
		System.out.println(json1);
		System.out.println(json2);
		System.out.println(json3);
		
		ArrayList<EmployeeVO> list = new ArrayList<EmployeeVO>();
		list.add(vo1);
		list.add(vo2);
		list.add(vo3);
		
		JSONArray array = new JSONArray(list);
		System.out.println(array);
		JSONObject json = new JSONObject();
		json.put("resultCode", "200");
		json.put("items", array);
		System.out.println(json);
		
		//가능한 이유 -> set get 데이터가 있기 때문이다. 아니면 데이터 못뽑음. private이기 때문에
	}

}
