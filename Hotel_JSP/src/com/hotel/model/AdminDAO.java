package com.hotel.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
 
public class AdminDAO {

	Connection con = null;			// DB 연결하는 객체.
	PreparedStatement pstmt = null;	// DB에 SQL문을 전송하는 객체.
	ResultSet rs = null;			// SQL문을 실행 후 결과 값을 가지고 있는 객체.

	String sql = null;				// 쿼리문을 저장할 객체.

	// 싱글톤 방식으로 객체를 만들기 위해서는 우선적으로 기본생성자의 접근제어자를 private static으로 선언
	private static AdminDAO instance = null;

	// 외부에서 객체 생성을 하지 못하게 private으로 접근을 제어
	private AdminDAO() {}

	// 기본 생성자 대신에 싱긑턴 객체를 return을 해 주는 getInstance() 메서드를 만들어서 여기에 접근하게 하는 방법
	public static AdminDAO getInstance() {
		if (instance == null) {
			instance = new AdminDAO();
		}
		return instance;
	}

	// DB 연동하는 작업을 진행하는 메서드 - DBCP방식으로 연결 진행
	public void openConn() {

		try {
			// JNDI 서버 객체 생성.
			Context ctx = new InitialContext();

			// lookup() 메서드를 이용하여 매칭되는 커넥션을 찾는다.
			DataSource ds = (DataSource) ctx.lookup("java:comp/env/jdbc/ec2");

			// DataSource 객체를 이용하여 커넥션 객체를 하나 가져온다.
			con = ds.getConnection();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// DB에 연결된 객체를 종료하는 메서드
	public void closeConn(ResultSet rs, PreparedStatement pstmt, Connection con) {

		try {
			if (rs != null)
				rs.close();
			if (pstmt != null)
				pstmt.close();
			if (con != null)
				con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
	
	 
	// 관리자 로그인 메서드
	public int adminLogin(String loginId, String loginPwd) {
		
		int result = 0;
		
		
		try {
			openConn();
			
			sql = "select * from hotel_admin where admin_id = ?";
			
			pstmt = con.prepareStatement(sql);
			
			pstmt.setString(1, loginId);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {	// 비밀번호가 같은 경우
				if(loginPwd.equals(rs.getString("admin_pwd"))) {
					// 비밀번호가 같은 경우
					result = 1;
				} else {	
					// 비밀번호가 틀린 경우
					result = -1;
				}
			} else {
				// 관리자 아이디가 없는 경우
				result = -2;
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeConn(rs, pstmt, con);
		}
		return result;
		
	} // adminLogin() 메서드 end
	
	
	// 입력한 id 관리자 정보 가져오는 메서드
	public AdminDTO getAdmin(String adminId) {
		
		AdminDTO dto = new AdminDTO();
		
		try {
			openConn();
			
			sql = "select * from hotel_admin where admin_id = ?";
			
			pstmt = con.prepareStatement(sql);
			
			pstmt.setString(1, adminId);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				dto.setAdminId(rs.getString("admin_id"));
				dto.setAdminPwd(rs.getString("admin_pwd"));
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeConn(rs, pstmt, con);
		}
		return dto;
		
	} // getAdmin() 메서드 end
	
	// 관리자 정보 가져오는 메서드
public List<AdminDTO> adminInfo() {
		
		List<AdminDTO> list = new ArrayList<AdminDTO>();
		
		try {
			
			openConn();
			
			sql = "select * from hotel_admin";
			
			pstmt = con.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				AdminDTO dto = new AdminDTO();
				dto.setAdminId(rs.getString("admin_id"));
				dto.setAdminPwd(rs.getString("admin_pwd"));
				
				list.add(dto);
			}
			
			System.out.println("List : " + list);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeConn(rs, pstmt, con);
		}
		return list;
		
	} // adminInfo() 메서드 end
}

