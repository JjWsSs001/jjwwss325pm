package com.kh.mvc.board.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kh.common.util.PageInfo;
import com.kh.mvc.board.model.service.BoardService;
import com.kh.mvc.board.model.vo.Board;

@WebServlet("/board/list")
public class BoardListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private BoardService service = new BoardService();
	
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int page = 1;
		int boardCount = 0;
		PageInfo pageInfo = null;
		List<Board> list = null;
		Map<String, String> serachMap = new HashMap<String, String>();
		
		try {
			String searchValue = req.getParameter("searchValue");
			if(searchValue != null && searchValue.length() > 0) {
				String searchType = req.getParameter("searchType");
				serachMap.put(searchType, searchValue);
			}
			
			page = Integer.parseInt(req.getParameter("page"));
		} catch (Exception e) {}
		
		boardCount = service.getBoardCount(serachMap);
		
		pageInfo = new PageInfo(page, 10, boardCount, 10);
//		pageInfo = new PageInfo(page, 10, boardCount, 20); // 게시글 수 = 20, 페이지 최대 숫자 = 10
//		pageInfo = new PageInfo(page, 4, boardCount, 10); // 게시글 수 = 10, 페이지 최대 숫자 = 4
		
		list = service.getBoardList(pageInfo, serachMap);
		
		req.setAttribute("list", list);
		req.setAttribute("pageInfo", pageInfo);
		req.getRequestDispatcher("/views/board/list.jsp").forward(req, resp);
	}
	
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}
}
