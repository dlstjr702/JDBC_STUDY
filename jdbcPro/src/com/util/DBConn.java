package com.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConn {
	
	//Double Checked Locking (DCL) Conn 객체가 완전히 생성되기전에 다른 스레드가 접근하는 문제를 방지하는 중요한 역할
	private static volatile Connection conn = null;
	
	private DBConn() {}
	
	
	//오버로딩
	public static Connection getConnection(String url,String user,String password ) {
		
		if (conn == null) {
			//1. 로딩 작업
			String className = "oracle.jdbc.driver.OracleDriver";
			
			// Connection 객체를 생성
			synchronized ( DBConn.class ) { //동기화처리/동기화처리할공유자원
				if( conn==null ) {
					try {
						Class.forName(className);
						conn = DriverManager.getConnection(url,user,password);
						
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					} catch (SQLException e) {
						e.printStackTrace();
					}//try,catch
				}//if
			}//synchronized
			
			
		}//if
		
		return conn;
	}//getConnection
	
	
	
	
	public static Connection getConnection() {
		
		if (conn == null) {
			//1. 로딩 작업
			String className = "oracle.jdbc.driver.OracleDriver";
			String url =  "jdbc:oracle:thin:@localhost:1521/XEPDB1";
			String user = "scott";
			String password = "tiger";
			// Connection 객체를 생성
			
			synchronized ( DBConn.class ) { //동기화처리/동기화처리할공유자원
				if( conn==null ) {
					try {
						Class.forName(className);
						conn = DriverManager.getConnection(url,user,password);
						
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					} catch (SQLException e) {
						e.printStackTrace();
					}//try,catch
				}//if
			}//synchronized
			
			
		}//if
		
		return conn;
	}//getConnection
	
	public static void close() {
		try {
			if (conn != null && !conn.isClosed()) {
				conn.close();				
			}//if
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			conn = null;// ***** 초기화...
		}//try,catch
	}//close
	
}
