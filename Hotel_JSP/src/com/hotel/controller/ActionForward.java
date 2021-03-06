package com.hotel.controller;
/*
 * ActionFoward 클래스
 * 1. Action 인터페이스에서 반환형으로 사용될 클래스
 * 2. 클래스의 구성 요소
 * 	1) isRedirect() 멤버 : boolean 타입
 * 		- *.do 페이지(true)
 * 		- jsp 페이지(false)
 * 	2) path 멤버 - String 타입
 * 		- 파일 경로 지정
 */
public class ActionForward {
	
	private boolean isRedirect;
	private String path;
	
	public boolean isRedirect() {
		return isRedirect;
	}
	public void setRedirect(boolean isRedirect) {
		this.isRedirect = isRedirect;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}

	
}
