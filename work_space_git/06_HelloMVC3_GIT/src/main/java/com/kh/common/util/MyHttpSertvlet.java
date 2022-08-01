package com.kh.common.util;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.kh.mvc.member.model.vo.Member;

// Servlet 공통 코드를 모으는 코드
public abstract class MyHttpSertvlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	
	// 공통 문자열 영역
	public static final String SESSION_USER_ID = "loginMember";

	abstract public String getServletName();
	
	public Member getSessionMember(HttpServletRequest req) {
		try {
			HttpSession session = req.getSession();
			Member loginMember = (Member) session.getAttribute(SESSION_USER_ID);
			return loginMember;
		} catch (Exception e) {}
		return null;
	}
	
	// 공통 common page 호출 기능
	public void sendCommonPage(String msg, String path, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setAttribute("msg", msg);
		req.setAttribute("location", path);
		req.getRequestDispatcher("/views/common/msg.jsp").forward(req, resp);
	}
	
	// 공통 common page 호출 기능2
	public void sendCommonPage(String msg, String path, String script, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setAttribute("msg", msg);
		req.setAttribute("location", path);
		req.setAttribute("script", script);
		req.getRequestDispatcher("/views/common/msg.jsp").forward(req, resp);
	}
	
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("Call! name : " + getServletName() +", method : " + req.getMethod());
		super.service(req, resp);
	}
	
	public void setSessionMember(HttpServletRequest req, Member member) {
		try {
			HttpSession session = req.getSession();
			session.setAttribute(SESSION_USER_ID, member);
		} catch (Exception e) {
		}
	}
	
	public void deleteFile(String fileName) {
		if(fileName == null || fileName.length() <= 0) {
			return;
		}
		try {
			String path = getServletContext().getRealPath("/resources/upload/board");
			File deleteFile = new File(path,fileName);
			deleteFile.delete();
		} catch (Exception e) {}
	}
}
