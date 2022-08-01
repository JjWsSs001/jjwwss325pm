package com.kh.mvc.member.model.dao;


import static com.kh.common.jdbc.JDBCTemplate.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.kh.mvc.member.model.vo.Member;

public class MemberDAO {

	public Member findMemberById(Connection conn, String id) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String query = "SELECT * FROM MEMBER WHERE ID = ? AND STATUS='Y'";

		try {
			pstmt = conn.prepareStatement(query); // 기본 쿼리 셋팅
			pstmt.setString(1, id); // ? 구간을 특정 문자열로 채워주는 코드
			rs = pstmt.executeQuery(); // 실제 DB에 쿼리를 요청하여 결과값을 받아오는 코드
			
			if(rs.next()) {
				Member m = new Member();
				m.setNo(rs.getInt("no"));
				m.setId(rs.getString("id"));
				m.setPassword(rs.getString("password"));
				m.setRole(rs.getString("role"));
				m.setName(rs.getString("name"));
				m.setPhone(rs.getString("phone"));
				m.setEmail(rs.getString("email"));
				m.setAddress(rs.getString("address"));
				m.setHobby(rs.getString("hobby"));
				m.setStatus(rs.getString("status"));
				m.setEnroll_date(rs.getDate("enroll_date"));
				m.setModify_date(rs.getDate("modify_date"));
				return m;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
			close(rs);
		}
		return null;
	}
	
	public int insertMember(Connection conn, Member member) {
		PreparedStatement pstmt = null;
		String query = "INSERT INTO MEMBER VALUES(SEQ_UNO.NEXTVAL,?,?,DEFAULT,?,?,?,?,?,DEFAULT,DEFAULT,DEFAULT)";
		int result = 0;
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, member.getId()); 
			pstmt.setString(2, member.getPassword()); 
			pstmt.setString(3, member.getName()); 
			pstmt.setString(4, member.getPhone()); 
			pstmt.setString(5, member.getEmail()); 
			pstmt.setString(6, member.getAddress()); 
			pstmt.setString(7, member.getHobby()); // "게임,야구,축구....." 
			result = pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		return result;
	}
	
	// 사용자 정보 수정
	public int updateMember(Connection conn, Member member) {
		PreparedStatement pstmt = null;
		String query = "UPDATE MEMBER SET NAME=?,PHONE=?,"
				+ 		" EMAIL=?,ADDRESS=?,HOBBY=?,MODIFY_DATE=SYSDATE WHERE NO=?";
		int result = 0;
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, member.getName()); 
			pstmt.setString(2, member.getPhone()); 
			pstmt.setString(3, member.getEmail()); 
			pstmt.setString(4, member.getAddress()); 
			pstmt.setString(5, member.getHobby()); 
			pstmt.setInt(6, member.getNo()); 
			
			result = pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		return result;
	}
	
	// 탈퇴하기
	public int updateStatus(Connection conn, int userNo,  String status) {
		PreparedStatement pstmt = null;
		String query = " UPDATE MEMBER SET STATUS=? WHERE NO=? ";
		int result = 0;
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, status); 
			pstmt.setInt(2, userNo); 
			result = pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		return result;
	}
	
	// 패스워드 변경하기
	public int updatePassword(Connection conn, int userNo, String password) {
		PreparedStatement pstmt = null;
		String query = "UPDATE MEMBER SET PASSWORD=? WHERE NO=?";
		int result = 0;
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, password); 
			pstmt.setInt(2, userNo); 
			result = pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		return result;
	}
	
//	public static void main(String[] args) {
//		Connection conn = getConnection();
//		MemberDAO dao = new MemberDAO();
//		
//		// 조회하기
//		Member member = dao.findMemberById(conn, "admin");
//		System.out.println(member.toString().replace(",", ",\n"));
//		System.out.println("------------------------------------------\n");
//		
//		// 회원 가입하기
//		Member member2 = new Member();
//		member2.setId("test12");
//		member2.setPassword("1234");
//		member2.setName("최길동");
//		member2.setPhone("010-1234-5678");
//		member2.setEmail("test2@email.com");
//		member2.setAddress("서울시 강남구 도곡동");
//		member2.setHobby("게임,야구,농구");
//		
//		int result = dao.insertMember(conn, member2);
//		Member member3 = null;
//		if(result == 1) {
//			System.out.println("회원가입 성공");
//			member3 = dao.findMemberById(conn, member2.getId());
//			System.out.println(member3.toString().replace(",", ",\n"));
//		}else {
//			System.out.println("회원가입 실패!!");
//		}
//		System.out.println("------------------------------------------\n");
//		
//		// 회원 업데이트
//		member3.setAddress("경기도 안양시 동안구");
//		member3.setPhone("010-4321-4444");
//		member3.setName("차은우");
//		result = dao.updateMember(conn, member3);
//		if(result == 1) {
//			System.out.println("수정 성공");
//			member3 = dao.findMemberById(conn, member3.getId());
//			System.out.println(member3.toString().replace(",", ",\n"));
//		}else {
//			System.out.println("수정 실패!!");
//		}
//		System.out.println("------------------------------------------\n");
//		
//		// 패스워드 변경
//		result = dao.updatePassword(conn, member3.getNo(), "4321");
//		if(result == 1) {
//			System.out.println("비밀번호 변경 성공");
//			member3 = dao.findMemberById(conn, member3.getId());
//			System.out.println(member3.toString().replace(",", ",\n"));
//		}else {
//			System.out.println("비밀번호 변경 실패!!");
//		}
//		System.out.println("------------------------------------------\n");
//		
//		// 회원탈퇴
//		result = dao.updateStatus(conn, member3.getNo(), "N");
//		if(result == 1) {
//			System.out.println("탈퇴 성공");
//			member3 = dao.findMemberById(conn, member3.getId());
//			System.out.println(member3);
//		}else {
//			System.out.println("탈퇴 실패!!");
//		}
//		System.out.println("------------------------------------------\n");
//	}
}






