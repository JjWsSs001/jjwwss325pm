package com.kh.mvc.member.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.kh.mvc.member.model.service.MemberService;
import com.kh.mvc.member.model.vo.Member;

@WebServlet(name="login", urlPatterns="/login")
public class MemberLoginServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	
	private MemberService service = new MemberService();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.sendRedirect(req.getContextPath()+"/"); // get 요청 튕겨내기!
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 패턴 1. 파라메터 읽어오기 -> id/pw
		String userId = req.getParameter("userId");
		String userPwd = req.getParameter("userPwd");
		String saveId = req.getParameter("saveId");
		System.out.println(userId + ", " + userPwd + ", " + saveId);
		
		// 패턴 2. service를 통한 DB 데이터 호출 또는 확인 -> login
		Member loginMember = service.login(userId, userPwd);
		
		// 쿠키로 사용자 아이디 저장하는 기능
		saveCookie(resp, saveId, userId);
		
		// 패턴 3. 응답할 페이지를 선택하고 필요시 파라메터 셋팅하기 그리고 페이지 이동 -> 성공/실패
		if(loginMember != null) { // 로그인 성공한 경우
			// 세션에 로그인 정보를 저장하고, 브라우저가 종료 될때 까지 로그인 상태 상태 유지
			HttpSession session = req.getSession();
			session.setAttribute("loginMember", loginMember); // 로그인 정보를 세션에 저장하는 코드
			resp.sendRedirect(req.getContextPath() + "/"); // 처음 페이지로 이동하는 코드
			// 이 코드가 실행되면 로그인 성공 폼으로 변경됨
		}else { // 로그인 실패한 경우
			req.setAttribute("msg", "사용자의 아이디나 비밀번호가 맞지 않습니다.");
			req.setAttribute("location", "/");
			
			// 공통페이지로 이동하여 결과를 알려주고 처음 페이지로 이동하는 코드
			// 메세지를 넘겨야함으로 forward로 넘겨야함
			req.getRequestDispatcher("/views/common/msg.jsp").forward(req, resp);
		}
	}

	private void saveCookie(HttpServletResponse resp, String saveId, String userId) {
		// 사용자의 요청으로 id를 브라우저 레벨에 저장하는 기능
		if(saveId != null) { // 저장 요청
			Cookie c = new Cookie("saveId", userId); // 사용자 id를 쿠키로 구움
			c.setMaxAge(60 * 60); // 1시간
			resp.addCookie(c);
		}else { // 저장 요청 안함
			Cookie c = new Cookie("saveId", ""); // 아이디 초기화
			c.setMaxAge(0); // 시간도 초기화
			resp.addCookie(c);
		}
	}
}









