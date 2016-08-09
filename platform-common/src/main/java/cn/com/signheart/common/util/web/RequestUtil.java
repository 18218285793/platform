

package cn.com.signheart.common.util.web;


import cn.com.signheart.common.holder.RequestContextHolder;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public class RequestUtil {
    public RequestUtil() {
    }

    public static String getRequestParameter(String key) {
        HttpServletRequest request = (HttpServletRequest) RequestContextHolder.requestThreadLocal.get();
        if(request != null && key != null && !"".equals(key)) {
            String value = request.getParameter(key);
            return value != null && !"".equals(value)?value.trim():"";
        } else {
            return "";
        }
    }

    public static String getUTF8RequestParameter(String key) {
        HttpServletRequest request = (HttpServletRequest)RequestContextHolder.requestThreadLocal.get();
        if(request != null && key != null && !"".equals(key)) {
            String value;
            try {
                value = URLDecoder.decode(request.getParameter(key), "UTF-8");
            } catch (UnsupportedEncodingException var3) {
                value = "";
            }

            return value != null && !"".equals(value)?value.trim():"";
        } else {
            return "";
        }
    }

    public static Object getRequestAttribute(String key) {
        HttpServletRequest request = (HttpServletRequest)RequestContextHolder.requestThreadLocal.get();
        if(request != null && key != null && !"".equals(key)) {
            Object value = request.getAttribute(key);
            return value != null && !"".equals(value)?value:"";
        } else {
            return "";
        }
    }

    public static String getRequestParameter(String key, String defaultStr) {
        HttpServletRequest request = (HttpServletRequest)RequestContextHolder.requestThreadLocal.get();
        if(request != null && key != null && !"".equals(key)) {
            String value = request.getParameter(key);
            return value != null && !"".equals(value)?value.trim():defaultStr;
        } else {
            return defaultStr;
        }
    }

    public static Object getRequestAttribute(String key, Object defaultObj) {
        HttpServletRequest request = (HttpServletRequest)RequestContextHolder.requestThreadLocal.get();
        if(request != null && key != null && !"".equals(key)) {
            Object value = request.getAttribute(key);
            return value != null && !"".equals(value)?value:defaultObj;
        } else {
            return defaultObj;
        }
    }

    public static String getIpAddr() {
        HttpServletRequest request = (HttpServletRequest)RequestContextHolder.requestThreadLocal.get();
        String ip = request.getHeader("x-forwarded-for");
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }

        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }

        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }

        return ip;
    }
}
