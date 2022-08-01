package com.kh.mvc.member.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kh.common.util.MyHttpSertvlet;
import com.kh.mvc.member.model.service.MemberService;
import com.kh.mvc.member.model.vo.Member;

/**
 * 1. get 요청이 올 경우 -> 회원가입 페이지로 이동
 * 2. post 요청이 올 경우 -> 회원가입 기능 동작
 */
@WebServlet(name="enroll", urlPatterns = "/member/enroll")
public class MemberEnrollServlet extends MyHttpSertvlet{
	private static final long serialVersionUID = 1L;

	private MemberService service = new MemberService();
	
	
	@Override
	public String getServletName() {
		return "MemberEnrollServlet";
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.sendRedirect(req.getContextPath() + "/views/member/enroll.jsp");
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			Member member = new Member();
			member.setId(req.getParameter("newId").strip());
			member.setPassword(req.getParameter("userPwd").strip());
			member.setName(req.getParameter("userName").strip());
			member.setPhone(req.getParameter("phone").trim());
			member.setEmail(req.getParameter("email").trim());
			member.setAddress(req.getParameter("address").trim());
			
			if(req.getParameter("hobby") != null) {
				String hobby = String.join(",", req.getParameterValues("hobby"));
				member.setHobby(hobby.trim());
			}
			System.out.println("가입 요청 : " + member);
			
			int result = service.save(member);
			
			if(result > 0) {
				req.setAttribute("msg", "회원가입에 성공 하였습니다!!");
				req.setAttribute("location", "/");
			}else{
				req.setAttribute("msg", "회원가입 실패!! (code:101=DB이슈)");
				req.setAttribute("location", "/");
			}
			req.getRequestDispatcher("/views/common/msg.jsp").forward(req, resp);
		} catch (Exception e) {
			e.printStackTrace();
			req.setAttribute("msg", "회원가입 실패!! (code:100 = 입력값 문제)");
			req.setAttribute("location", "/member/enroll");
			req.getRequestDispatcher("/views/common/msg.jsp").forward(req, resp);
//			sendCommonPage("회원가입 실패!!", "/", req, resp);
			return;
		}
	}
}



