package com.kh.spring.board.controller;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.kh.spring.board.model.service.BoardServiceImpl;
import com.kh.spring.board.model.vo.Board;
import com.kh.spring.board.model.vo.Reply;
import com.kh.spring.common.model.vo.PageInfo;
import com.kh.spring.common.template.Pagination;

@Controller
public class BoardController {

	@Autowired
	private BoardServiceImpl bService;
	
	//메뉴바 클릭시 /list.bo  (기본적으로 1번 페이지 요청)
	//페이징바 클릭시 /list.bo?cpage=요청하는 페이지수
	/*
	@RequestMapping("list.bo")
	public String selectList(@RequestParam(value="cpage" , defaultValue = "1") int currentPage, Model model) {
		
		int listCount = bService.selectListCount();
		
		PageInfo pi = Pagination.getPageInfo(listCount, currentPage, 10, 5);
		
		ArrayList<Board> list = bService.selectList(pi);
		
		model.addAttribute("pi", pi);
		model.addAttribute("list", list);
		
		//포워딩할 뷰(/WEB-INF/views/board/boardListView.jsp)
		return "board/boardListView";
		
	}
	*/
	
	@RequestMapping("list.bo")
	public ModelAndView selectList(@RequestParam(value="cpage" , defaultValue = "1") int currentPage, ModelAndView mv) {
		
		int listCount = bService.selectListCount();
		
		PageInfo pi = Pagination.getPageInfo(listCount, currentPage, 10, 5);
		
		ArrayList<Board> list = bService.selectList(pi);
		
		/*
		mv.addObject("pi", pi);
		mv.addObject("list", list);
		
		mv.setViewName("board/boardListView");
		*/
		
		mv.addObject("pi",pi).addObject("list", list).setViewName("board/boardListView");
		
		//포워딩할 뷰(/WEB-INF/views/board/boardListView.jsp)
		return mv;
		
	}
	
	@RequestMapping("enrollForm.bo")
	public String enrollForm() {
		// /WEB-INF/views/board/boardEnrollForm.jsp
		return "board/boardEnrollForm";
	}
	
	//**만약 다중 파일 업로드 기능 시??
	//	여러개의 input type="file" 요소에 다 동일한 키값(name)으로 부여 ex)upfile
	//	MultipartFile[] upfile로 받으면 됨(0번 인덱스부터 차곡차곡 첨부파일 담겨있음)
	
	@RequestMapping("insert.bo")
	public String insertBoard(Board b, MultipartFile upfile, HttpSession session, Model model) {
		//System.out.println(b);
		//System.out.println(upfile); //첨부파일을 선택했든 안했든 생성되는 객체임(다만 filename에 원본명이 있냐, ""이냐)
		
		//전달된 파일이 있을 경우 => 파일명 수정 작업 후 서버 업로드 => 원본명, 서버 업로드된 경로을 b에 이어서 담기
		if(!upfile.getOriginalFilename().equals("")) {
			
			//파일명 수정 작업 후 서버에 업로드 시키기("flower.png" => "2023100412153012345.png")
			/*
			String originName = upfile.getOriginalFilename(); //"flower.png"
			
			//"20231004154605"(년월일시분초)
			String currentTime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());  //"20231004154627"
			
			int ranNum = (int)(Math.random() * 90000 + 10000); // 51346(5자리 랜덤값 생성)
			
			String ext = originName.substring(originName.lastIndexOf(".")); //확장자 => 원본파일명에 확장자 있음  ".png"
			
			String changeName = currentTime + ranNum + ext; //"2023100415462751346.png"
			
			//업로드 시키고자 하는 폴더의 물리적인 경로를 알아내기
			String savePath = session.getServletContext().getRealPath("/resources/uploadFiles/");
			
			//폴더에 파일을 넣기 위한 과정
			try {
				upfile.transferTo(new File(savePath + changeName));
			} catch (IllegalStateException | IOException e) {
				e.printStackTrace();
			}
			*/
			
			//리펙토링 작업
			String changeName = saveFile(upfile,session);
			
			//원본명, 서버업로드된 경로를 Board b에 이어서 담기 (db를 위한 작업)
			b.setOriginName(upfile.getOriginalFilename());
			b.setChangeName("resources/uploadFiles/" + changeName);
			
		}
		//넘어온 첨부파일 있을 경우 b : 제목, 작성자, 내용,파일원본명, 파일저장경로
		//넘어온 파일이 없을 경우 b : 제목, 작성자, 내용
	
		int result = bService.insertBoard(b);
		
		if(result>0) { //성공 => 게시글 리스트페이지(list.bo url재요청)
			session.setAttribute("alertMsg", "성공적으로 게시글이 등록되었습니다.");
			return "redirect:list.bo";
		}else { //실패 => 에러페이지 포워딩
			model.addAttribute("errorMsg", "게시글 등록 실패");
			return "common/errorPage";
		}
	}
	
	//현재 넘어온 첨부파일 그 자체를 서버의 폴더에 저장 시키는 역할
	public String saveFile(MultipartFile upfile, HttpSession session) {
		//파일명 수정 작업 후 서버에 업로드 시키기("flower.png" => "2023100412153012345.png")
		String originName = upfile.getOriginalFilename(); //"flower.png"
		
		//"20231004154605"(년월일시분초)
		String currentTime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());  //"20231004154627"
		
		int ranNum = (int)(Math.random() * 90000 + 10000); // 51346(5자리 랜덤값 생성)
		
		String ext = originName.substring(originName.lastIndexOf(".")); //확장자 => 원본파일명에 확장자 있음  ".png"
		
		String changeName = currentTime + ranNum + ext; //"2023100415462751346.png"
		
		//업로드 시키고자 하는 폴더의 물리적인 경로를 알아내기
		String savePath = session.getServletContext().getRealPath("/resources/uploadFiles/");
		
		//폴더에 파일을 넣기 위한 과정
		try {
			upfile.transferTo(new File(savePath + changeName));
		} catch (IllegalStateException | IOException e) {
			e.printStackTrace();
		}
		return changeName;
	}
	
	/*  //상세보기 방법1
	@RequestMapping("detail.bo")
	public String selectBoard(int bno,HttpSession session, Model model) {
		//bno에는 상세조회를 하고자 하는 해당 게시글 번호 담겨 있다. 
		
		//해당 게시글 조회수 증가 서비스 호출 결과 받기(update하고 옴)
		int result = bService.increaseCount(bno);
		
		//>>성공적으로 조회수 증가
		//	>>boardDetailView.jsp상에 필요한 데이터 조회(게시글 상세정보 조회 서비스 호출
		if(result > 0) {//성공
			//	>>조회된 데이터 담아서
			//	>>board/boardDetailView.jsp로 포워딩
			Board board = bService.selectBoard(bno);
			session.setAttribute("b", board);
			return "board/boardDetailView";
			
		}else {//실패
			//>>조회수 증가 실패
			//	>>에러 문구 담아서 에러페이지 포워딩
			model.addAttribute("errorMsg", "게시글 상세보기에 실패했습니다.");
			return "common/errorPage";
		}	
	}
	*/
	
	//상세 보기 방법2
	@RequestMapping("detail.bo")
	public ModelAndView selectBoard(int bno,HttpSession session, ModelAndView mv) {
		//bno에는 상세조회를 하고자 하는 해당 게시글 번호 담겨 있다. 
		
		//해당 게시글 조회수 증가 서비스 호출 결과 받기(update하고 옴)
		int result = bService.increaseCount(bno);
		
		//>>성공적으로 조회수 증가
		//	>>boardDetailView.jsp상에 필요한 데이터 조회(게시글 상세정보 조회 서비스 호출
		if(result > 0) {//성공
			//	>>조회된 데이터 담아서
			//	>>board/boardDetailView.jsp로 포워딩
			Board board = bService.selectBoard(bno);
			mv.addObject("b", board).setViewName("board/boardDetailView");
			
		}else {//실패
			//>>조회수 증가 실패
			//	>>에러 문구 담아서 에러페이지 포워딩
			mv.addObject("errorMsg", "게시글 상세보기에 실패했습니다.").setViewName("common/errorPage");
		}	
		return mv; //modelandview 사용시 mv를 return 해야 한다.
	}
	
	@RequestMapping("delete.bo")
	public String deleteBoard(int bno, String filePath, HttpSession session, Model model) { // 파일이 있을 경우 filePath = "resources/uploadFiles/xxxxx.jsp" | 없을 경우 ""
		int result = bService.deleteBoard(bno);
		
		if(result>0) { //삭제 성공
			
			if(!filePath.equals("")) {//첨부파일이 있었을 경우 => 파일 삭제
				new File(session.getServletContext().getRealPath(filePath)).delete();
			}
			
			//게시판 리스트 페이지 list.bo url재요청
			session.setAttribute("alertMsg", "성공적으로 게시글이 삭제되었습니다.");
			return "redirect:list.bo";
			
		}else {//삭제 실패 => 에러문구 담아서 에러페이지
			model.addAttribute("errorMsg", "게시글 삭제에 실패했습니다.");
			return "common/errorPage";
		}
		
	}
	
	@RequestMapping("updateForm.bo")
	public String updateForm(int bno,Model model) {
		model.addAttribute("b", bService.selectBoard(bno));
		return "board/boardUpdateForm";
	}
	
	@RequestMapping("update.bo")
	public String updateBoard(@ModelAttribute Board b, MultipartFile reupfile, HttpSession session, Model model) {
		
		//새로 넘어온 첨부파일이 있을 경우
		if(!reupfile.getOriginalFilename().equals("")) {
			
			//기존에 첨부파일이 있었을 경우 => 기존 첨부파일 지우기
			if(b.getOriginName() != null) { //기존 첨부파일이 있다.
				new File(session.getServletContext().getRealPath(b.getChangeName())).delete();
			}
	
			//새로 넘어온 첨부파일 서버 업로드 시키기
			String changeName = saveFile(reupfile, session);
			
			//b에 새로 넘어온 첨부파일에 대한 원본명, 저장경로 담기
			b.setOriginName(reupfile.getOriginalFilename());
			b.setChangeName("resources/uploadFiles/" + changeName);
		}
		/*
		 *  b에 boardNo, boardTitle, boardContent 무조건 담겨 있다.!!
		 *  
		 *  1. 새로 첨부된 파일 x, 기존 파일 x => originName : null , changeName : null
		 *  
		 *  2. 새로 첨부된 파일 x, 기존 파일 o => originName : 기존 첨부 파일 원본명 , changeName : 기존 첨파 저장 경로
		 *  
		 *  3. 새로 첨부된 파일 o, 기조 파일 x => 새로 첨부된 파일 서버에 업로드 
		 *  							=> originName : 새로운 첨부파일 원본명 , changeName : 새로운 첨부파일 저장 경로
		 *  
		 *  4. 새로 첨부된 파일 o, 기존 파일 o => 기존 파일 삭제
		 *  							=> 새로운 전달된 파일 서버에 업로드
		 *  							=> originName : 새로운 첨부파일 원본명 , changeName : 새로운 첨부파일 저장 경로
		 */
		
		int result = bService.updateBoard(b);
		
		if(result>0) {//수정성공 => 상세페이지 detail.bo?bno=해당 수정하고 있는 게시글 번호 => url재요청
			session.setAttribute("alertMsg", "성공적으로 게시글이 수정되었습니다.");
			return "redirect:detail.bo?bno="+b.getBoardNo();
		}else {//수정 실패 => 에러페이지
			model.addAttribute("errorMsg", "게시글 수정에 실패했습니다.");
			return "common/errorPage";
		}

	}
	@ResponseBody
	@RequestMapping(value="rlist.bo", produces = "application/json; charset=UTF-8")
	public String ajaxSelectReplyList(int bno) {
		ArrayList<Reply> list = bService.selectReplyList(bno);
		
		return new Gson().toJson(list);
	}
	
	@ResponseBody
	@RequestMapping(value="rinsert.bo")
	public String ajaxInsertReply(Reply r) {
		int result = bService.insertReply(r);
		
		return result>0?"success":"fail";
	}
	
}
