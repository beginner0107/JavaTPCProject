import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import kr.inflearn.BookDTO;

public class Project01_A {
	public static void main(String[] args) {
		// Object(BookDTO)-->JSON(String)
		BookDTO dto=new BookDTO("자바",12000,"에이콘",170);
		Gson g=new Gson();
		String json=g.toJson(dto);
		System.out.println(json);
		
		BookDTO dto1=g.fromJson(json, BookDTO.class);
		System.out.println(dto1);
		
		// Object(List<BookDTO>) ->JSON(String) : [{  },{  }]
		List<BookDTO>lst=new ArrayList<BookDTO>();
		lst.add(new BookDTO("자바1",21000,"에이콘1",570));
		lst.add(new BookDTO("자바2",31000,"에이콘2",670));
		lst.add(new BookDTO("자바3",41000,"에이콘3",770));
		
		String lstJson=g.toJson(lst);
		System.out.println(lstJson);
		// JSON(String) -> Object(List<BookDTO>)
		List<BookDTO>lst1=g.fromJson(lstJson, new TypeToken<List<BookDTO>>() {}.getType());
		for(BookDTO vo:lst1) {
			System.out.println(vo);
		}
	}
}
