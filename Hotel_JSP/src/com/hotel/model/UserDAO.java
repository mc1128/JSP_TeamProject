
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
 
public class UserDAO {

	Connection con = null;			// DB 연결하는 객체.
	PreparedStatement pstmt = null;	// DB에 SQL문을 전송하는 객체.
	ResultSet rs = null;			// SQL문을 실행 후 결과 값을 가지고 있는 객체.

	String sql = null;				// 쿼리문을 저장할 객체.

	// 싱글톤 방식으로 객체를 만들기 위해서는 우선적으로 기본생성자의 접근제어자를 private static으로 선언
	private static UserDAO instance = null;

	// 외부에서 객체 생성을 하지 못하게 private으로 접근을 제어
	private UserDAO() {}

	// 기본 생성자 대신에 싱긑턴 객체를 return을 해 주는 getInstance() 메서드를 만들어서 여기에 접근하게 하는 방법
	public static UserDAO getInstance() {
		if (instance == null) {
			instance = new UserDAO();
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

	
	// 회원 체크하는 메서드
	public int userCheck(String userId, String userPwd) {
		
		int result = 0;

		try {
			openConn();
			
			sql = "select * from hotel_user where user_id = ?";
			
			pstmt = con.prepareStatement(sql);
			
			pstmt.setString(1, userId);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				if(userPwd.equals(rs.getString("user_pwd"))) {
					result = 1;
				} else {	// 비밀번호가 틀린 경우
					result = -1;
				}
			} else {	// 회원 아이디가 없는 경우(회원 아닌 경우)
				result = -2;
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeConn(rs, pstmt, con);
		}
		return result;
		
	} // userCheck() 메서드 end
	
	
	// id에 해당하는 유저 정보 가져오는 메서드
	public UserDTO getUser(String userId) {
		
		UserDTO dto = new UserDTO();

		try {
			openConn();
			
			sql = "select * from hotel_user where user_id = ?";
			
			pstmt = con.prepareStatement(sql);
			
			pstmt.setString(1, userId);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				dto.setUserId(rs.getString("user_id"));
				dto.setUserPwd(rs.getString("user_pwd"));
				dto.setUserName(rs.getString("user_name"));
				dto.setUserGen(rs.getString("user_gen"));
				dto.setUserPhone(rs.getString("user_phone"));
				dto.setUserAddr(rs.getString("user_addr"));
				dto.setUserEmail(rs.getString("user_email"));
				dto.setUserPoint(rs.getInt("user_point"));
				dto.setUserDate(rs.getString("user_date"));
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeConn(rs, pstmt, con);
		}
		return dto;
		
	} // getMember() 메서드 end
	
	
	// 회원가입 메서드
	public int userJoin(UserDTO dto) {
		int result = 0;
	
		try {
			openConn();
			
			sql = "insert into hotel_user values(?,?,?,?,?,?,?,default,sysdate)";
			
			pstmt = con.prepareStatement(sql);
			
			pstmt.setString(1, dto.getUserId());
			pstmt.setString(2, dto.getUserPwd());
			pstmt.setString(3, dto.getUserName());
			pstmt.setString(4, dto.getUserGen());
			pstmt.setString(5, dto.getUserPhone());
			pstmt.setString(6, dto.getUserAddr());
			pstmt.setString(7, dto.getUserEmail());
			
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeConn(rs, pstmt, con);
		}
		return result;
		
	} // userJoin() 메서드 end
	
	
	// id 중복체크 확인하는 메서드,
	public int idCheck(String userId) {
		
		int result = 0;

		if(! userId.equals("admin") && ! userId.equals("ADMIN")) {
			// 중복확인 아이디가 admin과 ADMIN이 아닐 때 회원가입 가능
			
			try {
				openConn();
				
				sql = "select * from hotel_user where user_id = ?";
				
				pstmt = con.prepareStatement(sql);
				
				pstmt.setString(1, userId);
				
				rs = pstmt.executeQuery();
				
				if(rs.next()) {
					result = 1;
				} 
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				closeConn(rs, pstmt, con);
			}
			
		} else {
			result = -1;
		}
		
		return result;

	} // idCheck()메서드 end
	
	
	// 아이디 찾기 메서드
	public String idSearch(String userName, String userPhone) {
		
		String result = "";

		try {
			openConn();
			
			sql = "select user_id from hotel_user where user_name = ? and user_phone = ?";
			
			pstmt = con.prepareStatement(sql);
			
			pstmt.setString(1, userName);
			pstmt.setString(2, userPhone);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				result = rs.getString(1);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeConn(rs, pstmt, con);
		}
		return result;
	
	} // idSearch() 메서드 end

	
	// 비밀번호 찾기 메서드
	public String pwdSearch(String userId, String userName) {
		
		String result = "";
		
		try {
			openConn();
			
			sql = "select user_pwd from hotel_user where user_id = ? and user_name =?";
			
			pstmt = con.prepareStatement(sql);
			
			pstmt.setString(1, userId);
			pstmt.setString(2, userName);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				result = rs.getString(1);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeConn(rs, pstmt, con);
		}
		return result;
		
	} // pwdSearch() 메서드 end

	// 회원 이름을 통해 정보 찾기
	public UserDTO getId(String name, String phone) {
		UserDTO dto = new UserDTO();
		try {
			openConn();
			
			sql = "select * from hotel_user where user_name = ? and user_phone = ?";
			
			pstmt = con.prepareStatement(sql);
			
			pstmt.setString(1, name);
			pstmt.setString(2, phone);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				dto.setUserId(rs.getString("USER_ID"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return dto;
	} // getId() 메서드 end
	

	// 유저 탈퇴하는 메서드
	public int userDel(String userId, String userPwd) {
		
		int result = 0;

		try {
			openConn();
			
			sql = "select * from hotel_user where user_id = ?";
			
			pstmt = con.prepareStatement(sql);
			
			pstmt.setString(1, userId);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				if(userPwd.equals(rs.getString("user_pwd"))) {
					// 디비 비밀번호 = 삭제 비밀번호가 같은 경우
					sql = "delete from hotel_user where user_id = ?";
					
					pstmt = con.prepareStatement(sql);
					
					pstmt.setString(1, userId);
					
					result = pstmt.executeUpdate();
				} else {
					// 디비 비밀번호 != 삭제 비밀번호가 다른 경우
					result = -1;
				}
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeConn(rs, pstmt, con);
		}
		return result;
		
	} // userDel() 메서드 end

	
	// 회원 전체 리스트 가져오기
	public String getMemberList() {
		String result="";
		
		try {
			openConn();
			
			sql = "select * from hotel_user order by user_name";
			
			pstmt = con.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
			result += "<users>";
			while(rs.next()) {
				result += "<user>";
				
				result += "<id>" + rs.getString("user_id") + "</id>";
				result += "<name>" + rs.getString("user_name") + "</name>";
				result += "<gen>" + rs.getString("user_gen") + "</gen>";
				result += "<phone>" + rs.getString("user_phone") + "</phone>";
				result += "<email>" + rs.getString("user_email") + "</email>";
				result += "<addr>" + rs.getString("user_addr") + "</addr>";
				result += "<point>" + rs.getInt("user_point") +"</point>";
				result += "</user>";
			}
			result += "</users>";
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeConn(rs, pstmt, con);
		}
		
		return result;
	} // getMemberList() 메서드 end
	
	
	// 회원 id에 맞는 리스트 가져오기
	public String getMemberList_id(String id, String phone) {
		
		String result="";
		
		try {
			openConn();
			
			sql = "select * from hotel_user where user_id = ? or user_phone = ? order by user_name";
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setString(2, phone);
			
			rs = pstmt.executeQuery();
			
			result += "<users>";
			while(rs.next()) {
				result += "<user>";
				
				result += "<id>" + rs.getString("user_id") + "</id>";
				result += "<name>" + rs.getString("user_name") + "</name>";
				result += "<gen>" + rs.getString("user_gen") + "</gen>";
				result += "<phone>" + rs.getString("user_phone") + "</phone>";
				result += "<email>" + rs.getString("user_email") + "</email>";
				result += "<addr>" + rs.getString("user_addr") + "</addr>";
				result += "<point>" + rs.getInt("user_point") +"</point>";
				result += "</user>";
			}
			result += "</users>";
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeConn(rs, pstmt, con);
		}
		
		return result;
	} // getMemberList_id() 메서드 end
	
	public int memberDelete(String id, String phone) {
		int result = 0;
		
		try {
			openConn();
			
			sql = "delete from hotel_user where user_id = ? or user_phone = ?";
			
			pstmt = con.prepareStatement(sql);
			
			pstmt.setString(1, id);
			pstmt.setString(2, phone);
			
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeConn(rs, pstmt, con);
		}
		
		return result;
	} // memberDelete() 메서드 end
	
	
	// 비밀번호 변경 메서드
	public int updatePwd(String userId, String originalPwd , String NewPwd) {
		
		int result = 0;

		try {
			openConn();
			
			sql = "select user_pwd from hotel_user where user_id = ?";
			
			pstmt = con.prepareStatement(sql);
			
			pstmt.setString(1, userId);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				if(originalPwd.equals(rs.getString("user_pwd"))) {
					// 입력된 (기존) 비밀번호와 디비 비밀번호가 같은 경우
					sql = "update hotel_user set user_pwd = ? where user_id = ?";
					
					pstmt = con.prepareStatement(sql);
					
					pstmt.setString(1, NewPwd);
					pstmt.setString(2, userId);
					
					result = pstmt.executeUpdate();
				} else {
					result = -1;
				}
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeConn(rs, pstmt, con);
		}
		return result;
		
	} // updatePwd() 메서드 end
	
	
	// 회원 정보 수정하는 메서드
	public int userInfoUpdate(UserDTO dto) {
		
		int result = 0;

		try {
			openConn();
			
			sql = "update hotel_user set user_phone = ?, user_addr = ?,"
					+ " user_email = ? where user_id = ?";
			
			pstmt = con.prepareStatement(sql);
			
			pstmt.setString(1,dto.getUserPhone());
			pstmt.setString(2, dto.getUserAddr());
			pstmt.setString(3, dto.getUserEmail());
			pstmt.setString(4, dto.getUserId());
			
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeConn(rs, pstmt, con);
		}
		return result;
		
	} // userInfoUpdate() 메서드 end
	

	
}