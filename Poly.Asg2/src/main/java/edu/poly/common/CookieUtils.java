package edu.poly.common;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookieUtils {
	public static String get(String name, HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();
		System.out.println("Dang o Cookie - get ");
//		System.out.println(cookies.length);
			if (cookies != null) {
			for(Cookie cookie:cookies) {
				System.out.println("Cookie get la " + cookie);
				System.out.println("Value Cookie get la " + cookie.getName());
				if (cookie.getName().equals(name)) {
					System.out.println("Value Cookie get la dung ");
					return cookie.getValue();
					
					
				}
			}
			
		}
		return null;
	}
	public static Cookie add(String name, String value, int hours, HttpServletResponse response) {
		System.out.println("Dang o Cookie - add ");
		Cookie cookie = new Cookie(name, value);
		cookie.setMaxAge(60*60*hours);
		cookie.setPath("/");
		response.addCookie(cookie);
		System.out.println("Cookie add la " + cookie);
		System.out.println("Value Cookie add la " + cookie.getValue());
		return cookie;
	}
}
