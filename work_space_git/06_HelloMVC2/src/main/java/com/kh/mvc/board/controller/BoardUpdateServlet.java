package com.kh.mvc.board.controller;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kh.common.util.MyFileRenamePolicy;
import com.kh.common.util.MyHttpSertvlet;
import com.kh.mvc.board.model.service.BoardService;
import com.kh.mvc.board.model.vo.Board;
import com.kh.mvc.member.model.vo.Member;
import com.oreilly.servlet.MultipartRequest;


@WebServlet("/board/update")
public class BoardUpdateServlet extends MyHttpSertvlet{
	private static final long serialVersionUID = 1L;

	private BoardService service = new BoardService();
	
	@Override
	public String getServletName() {
		return "BoardUpdate";
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int boardNo = Integer.parseInt(req.getParameter("boardNo"));
		Board board = service.findBoardByNo(boardNo, false);  
		req.setAttribute("board", board);
		req.getRequestDispatcher("/views/board/update.jsp").forward(req, resp);
	}

	// 1. 파라메터 가져온다.
	// 2. DB CRUD + FILE 관리★★★★★
	// 3. 결과에 따라 페이지 보내준다. 파라메터와 함께(if)
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			String path = getServletContext().getRealPath("/resources/upload/board");
			int maxSize = 104857600; // 100MB;
			String encoding = "UTF-8";
			MultipartRequest mr = new MultipartRequest(req, path, maxSize, encoding, new MyFileRenamePolicy());
			Member loginMember = getSessionMember(req);
			
			// writer = login된 세션 writer 다를때 보안상 튕겨낸다.
			if(loginMember == null
					|| loginMember.getId().equals(mr.getParameter("writer")) == false) {
				if(loginMember.getRole().equals("ROLE_ADMIN") == false) {
					sendCommonPage("잘못된 접근입니다. (code=201)", "/board/list", req, resp);
					return;
				}
			}
			
			int boardNo = Integer.parseInt(mr.getParameter("boardNo"));
			Board board = service.findBoardByNo(boardNo, false);
			
			// 실제 게시물 writer와 로그인된 writer가 다를경우도 튕겨낸다.
			if(board.getWriter_id().equals(loginMember.getId()) == false) {
				if(loginMember.getRole().equals("ROLE_ADMIN") == false){
					sendCommonPage("잘못된 접근입니다. (code=202)", "/board/list", req, resp);
				}
			}
			
			board.setNo(Integer.parseInt(mr.getParameter("boardNo")));
			board.setTitle(mr.getParameter("title").strip());
			board.setWriter_id(mr.getParameter("writer").strip());
			board.setWriter_no(loginMember.getNo());
			board.setContent(mr.getParameter("content").strip());

			// 기존 파일이름
//			String original_filename = mr.getParameter("original_filename");
			String renamed_filename = mr.getParameter("renamed_filename");
			
			// 재업로드 파일 이름
			String originalReloadFileName = mr.getOriginalFileName("upfile");
			String renamedReloadFileName = mr.getFilesystemName("upfile");
			
			if(originalReloadFileName != null && originalReloadFileName.length() > 0) {
				// 파일 수정이 된 상태
				try {
					// 이전 파일 삭제한다.
					File deleteFile = new File(path,renamed_filename);
					deleteFile.delete();
				} catch (Exception e) {}
				board.setOriginal_filename(originalReloadFileName);
				board.setRenamed_filename(renamedReloadFileName);
			}
			
			int result = service.save(board); // DB에 게시글 업데이트
			if(result > 0) {
				sendCommonPage("게시글이 정상적으로 업데이트 되었습니다.", "/board/list", req, resp);
			} else {
				sendCommonPage("게시글을 업데이트 할수 없습니다!! (code=202)", "/board/list", req, resp);
			}
		} catch (Exception e) {
			sendCommonPage("게시글을 수정할수 없습니다. (code=204)", "/board/list", req, resp);
		}
	}
	
}










