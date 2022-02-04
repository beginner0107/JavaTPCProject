import java.io.InputStream;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

public class Project01_C {
	public static void main(String[] args) {
		String src="info.json";
		// IO->Stream(스트림)
		InputStream is=Project01_C.class.getResourceAsStream(src);
		// 현재 이 클래스가 만들어 진 곳에서 얻어와라. IO를. Resource(파일)과 연결해서 연결된 객체를 가져와라
		if(is==null) {
			throw new NullPointerException("Cannot find resource file");
		}
		JSONTokener tokener=new JSONTokener(is);
		// 문자열이 JSON형태로
		JSONObject object=new JSONObject(tokener);
		JSONArray students=object.getJSONArray("students");
		for(int i=0;i<students.length();i++) {
			JSONObject student=(JSONObject)students.getJSONObject(i);
			System.out.print(student.get("name")+"\t");
			System.out.print(student.get("address")+"\t");
			System.out.println(student.get("phone"));
		}
	}
}
