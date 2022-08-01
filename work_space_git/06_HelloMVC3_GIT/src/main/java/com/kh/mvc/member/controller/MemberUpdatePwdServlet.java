package com.kh.mvc.member.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kh.common.util.MyHttpSertvlet;
import com.kh.mvc.member.model.service.MemberService;
import com.kh.mvc.member.model.vo.Member;

@WebServlet(name="updatePwd", urlPatterns = "/member/updatePwd")
public class MemberUpdatePwdServlet extends MyHttpSertvlet{
	private static final long serialVersionUID = 1L;
	
	private MemberService service = new MemberService();

	@Override
	public String getServletName() {
		return "MemberUpdatePwdServlet";
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher("/views/member/updatePwd.jsp").forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			Member member = getSessionMember(req);
			String newPwd = req.getParameter("userPwd");
			
			if(member == null) {
				sendCommonPage("잘못된 접근입니다.", "/member/updatePwd", req, resp);
				return;
			}
			
			int result = service.updatePassword(member.getNo(), newPwd);
			if(result > 0) {
				sendCommonPage("비밀번호가 수정되었습니다.", "/", "self.close();", req, resp);
				return;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			sendCommonPage("비밀번호를 수정할수 없습니다.", "/member/updatePwd", req, resp);
		}
	}

}
