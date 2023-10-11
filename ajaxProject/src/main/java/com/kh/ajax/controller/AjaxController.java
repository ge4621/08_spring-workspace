package com.kh.ajax.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.kh.ajax.model.vo.Member;

@Controller
public class AjaxController {

	/*
	 * 1. HttpServletResponse 객체로 응답데이터 응답하기(기존의 jsp, servlet 때 했던 Stream 이용한 방식)
	@RequestMapping("ajax1.do")
	public void ajaxMethod1(String name, int age,HttpServletResponse response) throws IOException {
		//(String name, int age)여기서 name, age는 jsp에서 넘긴 키값(ajax스크립트에서 data에 있는 키값)
		System.out.println(name);
		System.out.println(age);
		
		//요청 처리를 위해 서비스 호출
		
		//요청 처리가 다 됐다는 가정하에 요청한 그 페이지에 응답할 데이터가 있을 경우
		String responseDate="응답문자열 : " + name + "은(는)" + age + "살 입니다.";
		
		response.setContentType("text/html; charset=UTF-8");
		response.getWriter().print(responseDate);
	} 
	*/
	
	/*
	 * 2. 응답할 데이터로 문자열로 리턴
	 * 	=> HttpServletResponse 를 안쓸수 있음
	 * 		단, 문자열 리턴하면 원래는 포워딩 방식이였음 => 응답뷰로 인식해서 해당 뷰 페이지를 찾고 있음
	 * 		따라서 내가 리턴하는 문자열이 응답뷰가 아니라 응답 데이터야 라는걸 선언해야 한다.
	 * 		어노테이션 @ResponseBody를 붙여야 한다.
	 * 
	 * 그냥 하면 글자가 깨짐 이거 해결하기 위해 필터 사용 하는거 아님
	 * 필터는 jsp에서 controller으로 넘길때 안깨지게 하는것
	 * 이건 controller에서 jsp로 넘길때 글자가 깨지고 있는 경우
	 * 따라서 필터 사용해도 글자가 깨짐 => 해결 법 : produces사용
	 * (속성값 추가 하기 위해서는 매핑값 앞에 value를 붙여준다.)
	 */
	/*
	@ResponseBody
	@RequestMapping(value="ajax1.do",produces = "text/html;charset=UTF-8")
	public String ajaxMethod1(String name, int age) {
		String responseDate="응답문자열 : " + name + "은(는)" + age + "살 입니다.";
		return responseDate;
		
	}
	*/
	
	/*
	//다수의 응답데이터가 있을 경우???
	@RequestMapping("ajax1.do")
	public void ajaxMethod1(String name, int age, HttpServletResponse response) throws IOException {
	//요청 처리가 다 됐다는 가정하에 데이터 응답
		
		
		//response.setContentType("text/html;charset=UTF-8");
		//response.getWriter().print(name);
		//response.getWriter().print(age);
		//연이어서 출력하는 데이터가 하나의 문자열로 연이어져 있음(값은 깨지지 않고 잘 넘어감)
		
		
		//JSON(JavaScript Object Notation) 형태로 담아서 응답
		//JSONArray => [값,값,값,......] => 자바에서의 ArrayList와 유사 (list에 추가할 때는 add)
		//JSONObject => {키:값, 키:값}  => 객체(객체여서 인덱스 개념이 없어 순서가 없다. 그렇기 때문에 {키:값}을 사용 => 자바에서의 HashMap과 유사 (map에 추가 할때는 put)
		
		//첫번째 방법 JSONArray로 담아서 응답
		
		//JSONArray jArr = new JSONArray(); //[] =>현재는 빈 배열형(=처음 만들었을 때의 형태)
		//jArr.add(name); //[] -> ["차은우"] => 이름을 추가했기 때문에 입력값 이름이 들어간다.
		//jArr.add(age); // ["차은우"] -> ["차은우",20] => 이름이 들어가 있는 배열에 나이를 입력했기 때문에 ["차은우"] 뒤에 나이 값이 추가 되어 ["차은우", 20] 이 된다. => 인덱스를 이용해서 하나씩 뽑을 수 있다.
		
		
		//두번째 방법. JSONObject로 담아서 응답
		//JSONObject jObj = new JSONObject();  //=> {} =>처음 만들었을 때의 형태 , HashMap는 순서가 없다.(주의 해야 할 점)
		//jObj.put("name", name); //앞이 키값, 뒤는 앞에서 받아온 벨류 값 {name:'차은우'} => 앞에서 차은우를 입력하면 담겨지는 형태
		//jObj.put("age", age); //{name:'차은우', age:10} => String형태로 담길때는 ''사용,int 형으로 담길때는 그냥 담김 => {name:'차은우'}가 담겨 있는 곳에 나이를 입력 받았기 때문에 {name:'차은우', int:10}으로 됨
								//Object자료형이기 때문에 순서가 없다.(입력한 순서가 유기자 되지 않는다. 순서를 사용 하고 싶다면 JSONArray를 사용해야 한다.)
		
		response.setContentType("application/json; charset=UTF-8");
		response.getWriter().print(jObj);
	
	}
	 */
	/*(ajax1.do가 value값이고 괄호안의 값이 1개일 경우 ajax1.do으로만 사용 가능 )*/
	/*
	@ResponseBody
	@RequestMapping(value="ajax1.do", produces = "application/json; charset=UTF-8")
	public String ajaxMethod1(String name, int age) {
		
		JSONObject jObj = new JSONObject(); //{} => 처음 형태
		jObj.put("name", name); //{} => {name:'차은우'} 변경
		jObj.put("age", age); //{name:'차은우'} => {name:'차은우',age:10} 변경
		
		return jObj.toJSONString(); //return jObj만 쓸 경우 자료형이 맞지 않음 따라서 String로 바꿔줘야 됨 => .toJSONString()사용 => "{name:'차은우',age:10}" 형태(String)로 넘어감, 반환될때는 String 받을 때는 {} 형태
	
	}
	
	@ResponseBody
	@RequestMapping(value="ajax2.do",produces = "application/json; charset=UTF-8")
	public String ajaxMethod2(int num) {
		
		//Member m = mService.selectMember(num); //일반적인 방법 but 지금 아무것도 없기 때문에 member안에 값이 담겨 있다고 가정
		Member m = new Member("user01","pass01","차은우",20,"01011112222"); //더미(원래는 위의 코드 사용 해야 한는데...데이터가 없으니 가정해서 진행)
		
		//JSON형태로 만들어서 응답해야지 반환가능 => toJSONString() 사용해서 반환
		JSONObject jObj = new JSONObject();  //{} => 초기 형태
		jObj.put("userId", m.getUserId());	//{} => {userId:"user01"}
		jObj.put("userName", m.getUserName());	//{userId:"user01"} => {userId:"user01",userName:"차은우"}
		jObj.put("age", m.getAge()); //반환형 int 	//{userId:"user01",userName:"차은우"} => {userId:"user01",userName:"차은우",age:10}
		jObj.put("phone", m.getPhone());//{userId:"user01",userName:"차은우",age:10} => {userId:"user01",userName:"차은우",age:10,phone:"01011111111"}  JSONObject는 순서가 보장되지 않는다. => 입력한 순서대로 값이 안나올 수 있다.
		
		return jObj.toJSONString();  //그냥 m을 하면 반환형도 안맞아서 안된다.
		
		
	}
	*/
	@ResponseBody
	@RequestMapping(value="ajax2.do",produces = "application/json; charset=UTF-8")
	public String ajaxMethod2(int num) {
		
		//Member m = mService.selectMember(num); //일반적인 방법 but 지금 아무것도 없기 때문에 member안에 값이 담겨 있다고 가정
		Member m = new Member("user01","pass01","차은우",20,"01011112222"); //더미(원래는 위의 코드 사용 해야 한는데...데이터가 없으니 가정해서 진행)
		
		return new Gson().toJson(m); //gson사용 하면 간단해짐 {userId:'user01', userPwd:'pass01',......}
	}
	
	@ResponseBody
	@RequestMapping(value="ajax3.do",produces = "application/json; charset=UTF-8")
	public String ajaxMethod3() {
		
		//ArrayList<Member> list = mService.selectList(); =>원래 코드
		ArrayList<Member> list = new ArrayList<Member>(); //[] =>처음 리스트 형태 
		list.add(new Member("user01", "pass01", "차은우", 20, "01011111111")); //[{차은우 관련 객체}]
		list.add(new Member("user02", "pass02", "주지훈", 30, "01011222222")); //[{차은우 관련 객체},{주지훈 관련 객체}]
		list.add(new Member("user03", "pass03", "정우성", 40, "01033322222")); //[{차은우 관련 객체},{주지훈 관련 객체},{정우성 관련 객체}]
		
		return new Gson().toJson(list);
	}
	
}
