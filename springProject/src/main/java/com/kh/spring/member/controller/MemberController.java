package com.kh.spring.member.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.kh.spring.member.model.service.MemberServiceImpl;
import com.kh.spring.member.model.vo.Member;

@Controller //Controller 타입의 어노테이션을 붙여주면 빈스캐닝 통해 자동으로 빈 등록
public class MemberController {
	
	@Autowired //DI(Dependency Injection)특징 => 의존성 주입 @Autowired => 중요!!안쓰면 null 오류가 뜸
	private MemberServiceImpl mService;
	
	@Autowired//controller가 필요할 때 가져다 쓰기 위해 Autowired를 선언하는 거임
	private BCryptPasswordEncoder bcryptPasswordEncoder;
	

	/*
	@RequestMapping(value="login.me")  //RequestMapping타입의 어노테이션을 붙여줌으로 써 HandlerMapping 등록
	public void loginMember() {
		
	} //로그인
	
	@RequestMapping(value="inser.me")
	public void insertMember() {
		
	}//회원가입
	
	public void updateMember() {
		
	}//정보변경
	*/
	
	/*
	 * *파라비터(요청 시 전달 값)를 받는 방법(요청시 전달되는 값들 처리 방법
	 * 
	 * 1. HttpServletRequest를 이용해서 전달 받기(기존의 jsp/servlet 방식)
	 * 	  해당 메소드의 매개변수로 HttpServletRequest를 작성 해두변
	 * 	  스프링컨테이너가 해당 메소드 호출 시(실행 시)자동으로 해당 객체를 생성해서 인자로 주입해줌
	 */
	/*
	@RequestMapping("login.me")
	public String loginMember(HttpServletRequest request) {
		String userId = request.getParameter("id");
		String userPwd= request.getParameter("pwd");
		
		System.out.println("ID:" + userId);
		System.out.println("PWD: " + userPwd);
		return "main";
	}
	*/
	
	/*
	 * 2. @RequestParam 어노테이션을 이용하는 방법(공부)***
	 * 	  request.getParameter("키") : 벨류의 역할을 대신해주는 어노테이션
	 * 
	 *  defaultValue => 값을 미입력하면 설정된 값으로 나온다.
	 */
	/*
	@RequestMapping("login.me")
	public String loginMember(@RequestParam(value="id",defaultValue="aaa") String userId,
							  @RequestParam(value="pwd") String userPwd) {
		
		System.out.println("ID:" + userId);
		System.out.println("PWD: " + userPwd);
		return "main";
	}
	*/
	
	/*
	 * 3.@RequestParam 어노테이션을 생략하는 방법
	 * **단, 매개변수명과 name값(요청시 전달값의 키값)과 동일하게 세팅해둬야 자동으로 값이 주입됨
	 */
	/*
	@RequestMapping("login.me")
	public String loginMember(String id, String pwd) {
		
		System.out.println("ID: " + id);
		System.out.println("PWD: " + pwd);
		
		Member m = new Member();
		m.setUserId(id);
		m.setUserPwd(pwd);
		
		//Service쪽 메소드에 m을 전달하며 조회
		
		return "main";
	}
	*/
	
	/*
	 * 4. 커맨드 객체 방식(공부)****
	 * 	 해당 메소드 매개변수로
	 * 	요청시 전달값을 담고자 하는 vo클래스 타입을 셋팅 후
	 * 	요청시 전달값의 키값(name값)을 vo 클래스에 담고자 하는 필드명으로 작성
	 * 
	 *  스프링컨테이너가 해당 객체를 기본생성자로 생성 후 setter 메소드 찾아서
	 *  요청시 전달값을 해당 필드에 담아주는 내부적인 원리
	 *  
	 *  **반드시 name속성값(키값)과 담고자 하는 필드명 동일해야 한다.
	 * 
	 * 단,매개변수에 사용되는 필드 이름과 value값이 일치해야 한다. (저 Member m 에 있는 필드명과 jsp의 value값이 일치)
	 */
	
	/*
	 * 요청 처리 후 응답페이지로 포워딩 또는 url 재요청, 응답데이터 담는 방법
	 * 
	 * 1. 스프링에서 제공하는 Model객체를 사용하는 방법
	 * 	  포워딩할 뷰로 전달하고자 하는 데이터를 맵 형식(key-value)으로 담을 수 있는 영역
	 * 	  Model 객체는 requestScope이다.
	 * 	  단, setAttribute가 아닌 addAttribute메소드 이용
	 */
	/*
	@RequestMapping("login.me")
	public String loginMember(Member m, Model model, HttpSession session) {
		
		Member loginMember = mService.loginMember(m);
		
		if(loginMember == null) { //로그인 실패 => 에러메시지(/WEB-INF/views/common/errorPage.jsp) requestScope에 담아서 에러페이지 포워딩
			model.addAttribute("errorMsg","로그인 실패");
			return "common/errorPage"; // /WEB-INF/views/ ???? .jsp
		}else {//로그인 성공 => loginMember를 sessionScope에 담고 메인 페이지 url재요청
			session.setAttribute("loginMember", loginMember);
			return "redirect:/";
		}
	}
	
	*/
	
	/*
	 * 2. 스프링에서 제공하는 ModelAndView 객체를 이용하는 방법
	 * 
	 * 	Model은 데이터를 key-value 세트로 담을 수 있는 공간이라고 한다면
	 * 	View는 응답 뷰에 대한 정보를 담을 수 있는 공간
	 */
	
	@RequestMapping("login.me")
	public ModelAndView loginMember(Member m, HttpSession session, ModelAndView mv) {
		/* 암호화 작업 전 로그인 하는 방법
		Member loginMember = mService.loginMember(m);
		
		if(loginMember == null) { //로그인 실패 => 에러메시지(/WEB-INF/views/common/errorPage.jsp) requestScope에 담아서 에러페이지 포워딩
			mv.addObject("errorMsg", "로그인 실패");
			mv.setViewName("common/errorPage");
		}else {//로그인 성공 => loginMember를 sessionScope에 담고 메인 페이지 url재요청
			session.setAttribute("loginMember", loginMember);
			mv.setViewName("redirect:/");
		}
		return mv;
		*/
		
		//암호화 작업 후 해야 하는 과정
		//Member m userId 필드 : 사용자가 입력한 아이디
		//Member m userPwd 필드 : 사용자가 입력한 비번(평문)
		Member loginMember = mService.loginMember(m);
		//loginMember : 오로지 아이디만을 가지고 조회된 회원
		// loginMember userPwd 필드 : db에 기록된 비번(암호화된 비번)
		
		if(loginMember != null && bcryptPasswordEncoder.matches(m.getUserPwd(), loginMember.getUserPwd())) {
			//로그인 성공
			session.setAttribute("loginMember", loginMember);
			mv.setViewName("redirect:/");
		}else {
			//로그인 실패
			mv.addObject("errorMsg", "로그인 실패");
			mv.setViewName("common/errorPage");
		}
		return mv;
		
	}
	
	@RequestMapping("logout.me")
	public String logoutMember(HttpSession session) {
		session.invalidate();
		return "redirect:/";
	}
	
	@RequestMapping("enrollForm.me")
	public String enrollForm() {
		//WEB-INF/views/member/memberEnrollForm.jsp 로 포워딩
		return "member/memberEnrollForm";
	}
	
	@RequestMapping("insert.me")
	public String insertMember(Member m, Model model, HttpSession session) {
	
		//1. 한글이 깨짐 => 스프링에서 제공하는 인코딩 필터 등록
		
		//2. 나이를 입력하지 않았을 경우 ""빈문자열이 넘어오는데 int형 필드에 담을 수 없어서 400 에러 발생
		//	=>Member 클래스에 age 필드를 int 형 -> String형으로 변경
		
		//3. 비밀번호가 사용자가 입력한 있는 그대로의 평문(있는 그대로의 값)
		//	=>Bcrypt 방식의 암호화를 통해서 암호문으로 변경
		//	=> 1. 스프링에서 제공하는 모듈 : 시큐리티 모듈에서 제공(라이브러리 추가 pom.xml)
		//	=> 2. BcryptPassWordEncoder 라는 클래스를 빈으로 등록(xml 방식)(spring-security.xml 파일에)
		// 	=> 3. web.xml에 spring-security.xml 파일을 pre-loading할 수 있도록 작성
		//System.out.println("평문 : " + m.getUserPwd());
		
		//암호화 작업(암호문을 만들어내는 과정)
		String encPwd = bcryptPasswordEncoder.encode(m.getUserPwd());
		System.out.println("암호문 : " + encPwd);
		
		m.setUserPwd(encPwd);//Member 객체에 userPwd에 평문이 아닌 암호문으로 변경
		
		int result = mService.insertMember(m);
		
		if(result>0) {//성공 => 메인페이지 url재요청
			session.setAttribute("alertMsg", "성공적으로 회원가입 되었습니다.");
			return "redirect:/";
		}else {//실패 => 에러문구 담아서 에러페이지 포워딩
			model.addAttribute("errorMsg", "회원 가입 실패");
			return "common/errorPage";
		}
		
	}
	
	@RequestMapping("myPage.me")
	public String myPage() {
		return "member/myPage";
	}
	
}
