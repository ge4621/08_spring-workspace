package com.kh.ajax.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
	@ResponseBody
	@RequestMapping(value="ajax1.do",produces = "text/html;charset=UTF-8")
	public String ajaxMethod1(String name, int age) {
		String responseDate="응답문자열 : " + name + "은(는)" + age + "살 입니다.";
		return responseDate;
		
	}
	
}
