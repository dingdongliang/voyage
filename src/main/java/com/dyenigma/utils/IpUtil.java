package com.dyenigma.utils;

import javax.servlet.http.HttpServletRequest;

/**
 * 处理IP地址信息，获取访问的真实IP地址
 * <p>
 * author dyenigma
 * create 2016/3/1 9:56
 */
public class IpUtil {

    private IpUtil() {
    }

    public static String getIpAddr(HttpServletRequest request) {
        if (request == null) {
            return "unknown";
        }

        // 取X-Forwarded-For中第一个非unknown的有效IP字符串。
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
}
