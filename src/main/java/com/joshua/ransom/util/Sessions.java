package com.joshua.ransom.util;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Joshua Ransom on 2/2/2020.
 *
 * Provides helper methods and classes for sessions.
 */
public class Sessions {

	public static HttpSession current() {
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		return attr.getRequest().getSession();
	}

	public static boolean has() {
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		return attr.getRequest().getSession(false) != null;
	}

	public static void end() {
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		HttpSession session = attr.getRequest().getSession(false);

		// If we have an active session, invalidate it.
		if (session != null) {
			session.invalidate();
		}
	}

	public static String getClientIp(HttpServletRequest request) {
		String remoteAddr = "";

		if (request != null) {
			remoteAddr = request.getHeader("CF-Connecting-IP");
			if (remoteAddr == null || "".equals(remoteAddr)) {
				remoteAddr = request.getHeader("X-FORWARDED-FOR");
				if (remoteAddr == null || "".equals(remoteAddr)) {
					remoteAddr = request.getRemoteAddr();
				}
			}
		}
		return remoteAddr;
	}

	public static String getCountry(HttpServletRequest request) {
		String header = "";

		if (request != null) {
			header = request.getHeader("cf-ipcountry");
		}
		return header;
	}

	public static String getUserAgent(HttpServletRequest request) {
		String header = "";

		if (request != null) {
			header = request.getHeader("user-agent");
		}
		return header;
	}

}
