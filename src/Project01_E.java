import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

public class Project01_E {
	// 지도 이미지 생성 메서드
	public static void map_service(String point_x, String point_y, String address){
		String URL_STATICMAP="https://naveropenapi.apigw.ntruss.com/map-static/v2/raster?";
		try {
			String pos=URLEncoder.encode(point_x+" "+point_y,"UTF-8");
			String url=URL_STATICMAP;
			url+="center="+point_x+","+point_y;
			url+="&level=16&w=700&h=500";
			url+="&markers=type:t|size:mid|pos:"+pos+"|label:"+URLEncoder.encode(address,"UTF-8");
			URL u=new URL(url);
			HttpURLConnection con=(HttpURLConnection)u.openConnection();
			con.setRequestProperty("X-NCP-APIGW-API-KEY-ID", "발급받은 아이디");
			con.setRequestProperty("X-NCP-APIGW-API-KEY", "발급받은 키값");
			int responseCode=con.getResponseCode();
			BufferedReader br;
			if(responseCode==200) { // 정상 호출
				InputStream is=con.getInputStream();
				int read=0;
				// 랜덤한 이름으로 파일 생성
				byte[] bytes=new byte[1024];
				String tempname=Long.valueOf(new Date().getTime()).toString();
				File f=new File(tempname+".jpg");
				f.createNewFile();
				OutputStream outputStream=new FileOutputStream(f);
				while((read=is.read(bytes))!=-1) {
					outputStream.write(bytes,0,read);
				}
				outputStream.close();
				is.close();
			}else {
				br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
				String inputLine;
				StringBuffer response=new StringBuffer();
				while((inputLine=br.readLine())!=null) {
					response.append(inputLine);
				}
				br.close();
				System.out.println(response.toString());
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}

	public static void main(String[] args) {
//		String apiURP="https://naveropenapi.apigw.ntruss.com/map-geocode/v2/geocode?query=";
		String client_id = "jvlj60gqjn";
		String client_secret = "1ZgS8OIyDBoq8juYmHSRwsDvcqRQsvNn3sucFYBq";
		BufferedReader io = new BufferedReader(new InputStreamReader(System.in));
		// System.in은 byte Stream, BufferedReader 문자열 스트림
		try {
			System.out.print("주소를 입력하세요 : ");
			String address = io.readLine();
			String addr = URLEncoder.encode(address, "UTF-8");
			String reqUrl = "https://naveropenapi.apigw.ntruss.com/map-geocode/v2/geocode?query=" + addr;

			URL url = new URL(reqUrl);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");
			con.setRequestProperty("X-NCP-APIGW-API-KEY-ID", client_id);
			con.setRequestProperty("X-NCP-APIGW-API-KEY", client_secret);
			BufferedReader br;
			int responseCode = con.getResponseCode();
			if (responseCode == 200) {
				br = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
			} else {
				br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
			}
			String line;
			StringBuffer response = new StringBuffer();
			// 추가된 부분
			String x = "";
			String y = "";
			String z = "";
			while ((line = br.readLine()) != null) {
				response.append(line);
			}
			JSONTokener tokener = new JSONTokener(response.toString());
			JSONObject object = new JSONObject(tokener);
			System.out.println(object.toString(2));

			JSONArray arr = object.getJSONArray("addresses");
			for (int i = 0; i < arr.length(); i++) {
				JSONObject temp = (JSONObject) arr.get(i);
				System.out.print("address : " + temp.get("roadAddress") + " ");
				System.out.print("jibunAddress : " + temp.get("jibunAddress") + " ");
				System.out.print("경도 : " + temp.get("x") + " ");
				System.out.println("위도 : " + temp.get("y"));
				// 추가된 부분
				x = (String) temp.get("x");
				y = (String) temp.get("y");
				z = (String) temp.get("roadAddress");
			}
			// 추가된 부분
			map_service(x, y, z);
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
