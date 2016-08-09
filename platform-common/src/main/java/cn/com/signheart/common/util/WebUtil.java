
package cn.com.signheart.common.util;


import cn.com.signheart.common.reflation.ClassType;
import cn.com.signheart.common.reflation.ObjectUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WebUtil {
    public static HashMap contentTypeMap = new HashMap();
    public static HashMap charMap = new HashMap();

    public WebUtil() {
    }

    public static String outSelected(String str1, String str2) {
        return !AssertUtil.isEmpty(str1) && !AssertUtil.isEmpty(str2)?(str1.equals(str2)?" selected ":""):"";
    }

    public static String getParm(HttpServletRequest _request, String _parmName, String _defaultValue) {
        Object rs = _request.getParameter(_parmName);
        if(AssertUtil.isEmpty(rs)) {
            rs = _request.getAttribute(_parmName);
        }

        return AssertUtil.isEmpty(rs)?_defaultValue:rs.toString();
    }

    public static String[] getParms(HttpServletRequest _request, String _parmName, String[] _defaultValue) {
        Object rs = _request.getParameter(_parmName);
        if(AssertUtil.isEmpty(rs)) {
            rs = _request.getAttribute(_parmName);
        }

        return AssertUtil.isEmpty(rs)?_defaultValue:(String[])((String[])rs);
    }

    public static String outChecked(String str1, String str2) {
        return !AssertUtil.isEmpty(str1) && !AssertUtil.isEmpty(str2)?(str1.equals(str2)?" checked ":""):"";
    }

    public static boolean ParmIsNull(String str) {
        return AssertUtil.isEmpty(str) || str.equalsIgnoreCase("null");
    }

    public static String outString(Object str) {
        if(AssertUtil.isEmpty(str)) {
            return "&nbsp;";
        } else {
            Set keySet = charMap.keySet();
            String value = str.toString();
            Iterator ri = keySet.iterator();

            while(ri.hasNext()) {
                String key = (String)ri.next();
                if(str.getClass().equals(ClassType.sqlTimeType)) {
                    value = value.substring(0, 19);
                }

                if(str.getClass().equals(ClassType.dateType)) {
                    value = value.substring(0, 10);
                } else {
                    value = StringUtil.replace(value, key, charMap.get(key).toString());
                }
            }

            return value;
        }
    }

    public static String outNull(Object str) {
        if(AssertUtil.isEmpty(str)) {
            return "";
        } else {
            Set keySet = charMap.keySet();
            String value = str.toString();
            Iterator ri = keySet.iterator();

            while(ri.hasNext()) {
                String key = (String)ri.next();
                if(str.getClass().equals(ClassType.sqlTimeType)) {
                    value = value.substring(0, 19);
                }

                if(str.getClass().equals(ClassType.dateType)) {
                    value = value.substring(0, 10);
                } else {
                    value = StringUtil.replace(value, key, charMap.get(key).toString());
                }
            }

            return value;
        }
    }

    public static String outString(Object str, int size) {
        String rStr = "&nbsp";
        if(AssertUtil.isEmpty(str)) {
            return rStr;
        } else {
            try {
                String e = (String)str;
                if(e.length() > size) {
                    str = e.substring(0, size) + "....";
                }
            } catch (Exception var4) {
                var4.printStackTrace();
            }

            return str.toString();
        }
    }

    public static String outString(int length, int number, int mode) {
        char[] eachNumb = new char[length];
        String numStr = Integer.toString(number);
        char[] tempChar = numStr.toCharArray();
        int i;
        if(tempChar.length > length) {
            for(i = 0; i < length; ++i) {
                if(mode == 0) {
                    eachNumb[i] = tempChar[tempChar.length - length + i];
                }

                if(mode == 1) {
                    eachNumb[i] = tempChar[i];
                }
            }
        } else {
            for(i = 0; i < length; ++i) {
                if(i < length - tempChar.length) {
                    eachNumb[i] = 48;
                } else {
                    eachNumb[i] = tempChar[tempChar.length - (length - i)];
                }
            }
        }

        return new String(eachNumb);
    }

    public static String outNull(Object obj, String MethName) throws Exception {
        return outNull(ObjectUtil.invokMeth(obj, MethName));
    }

    public static String outString(Object obj, String MethName) throws Exception {
        return outString(ObjectUtil.invokMeth(obj, MethName));
    }

    public static String getReadyOnly() {
        return " style=\'width:100%;background-color:#cccccc\' readonly=\'true\'";
    }

    public static String getContentNameByFileExtendName(String _extendName) throws Exception {
        if(AssertUtil.isEmpty(_extendName)) {
            throw new Exception("错误，根据文件扩展名取ContextType时发生错误：未传入文件扩展名！");
        } else {
            return (String)contentTypeMap.get(StringUtil.CnvSmallChr(_extendName));
        }
    }

    public static String getWebChar(String _chr) {
        Object temp = charMap.get(_chr);
        return temp == null?_chr:temp.toString();
    }

    public static String transferAllParm(HttpServletRequest request) {
        StringBuilder sb = new StringBuilder();
        Enumeration parmList = request.getParameterNames();

        while(parmList.hasMoreElements()) {
            String parmName = parmList.nextElement().toString();
            if(!parmName.equals("_aido_currentPage")) {
                sb.append("<input type=hidden name=");
                sb.append(parmName);
                sb.append(" value=\"");
                sb.append(getWebChar(request.getParameter(parmName)));
                sb.append("\">");
            }
        }

        return sb.toString();
    }

    public static String transferPageParm(HttpServletRequest request) {
        StringBuilder sb = new StringBuilder();
        Enumeration parmList = request.getParameterNames();

        while(parmList.hasMoreElements()) {
            String parmName = parmList.nextElement().toString();
            sb.append("<input type=hidden name=");
            sb.append(parmName);
            sb.append(" value=\"");
            sb.append(getWebChar(request.getParameter(parmName)));
            sb.append("\">");
        }

        return sb.toString();
    }

    public static String getTxtWithoutHTMLElement(String element, String regx) {
        if(null != element && !"".equals(element.trim())) {
            regx = "(<" + regx + "[^<|^>]*>|</" + regx + ">)";
            Pattern pattern = Pattern.compile(regx);
            Matcher matcher = pattern.matcher(element);
            StringBuffer txt = new StringBuffer();

            while(matcher.find()) {
                String group = matcher.group();
                if(group.matches("<[\\s]*>")) {
                    matcher.appendReplacement(txt, group);
                } else {
                    matcher.appendReplacement(txt, "");
                }
            }

            matcher.appendTail(txt);
            return txt.toString();
        } else {
            return element;
        }
    }

    public static <T> T getFromRequestAttr(HttpServletRequest request, String key, Class<T> pagerClass) {
        Object tempObj = request.getAttribute(key);
        if(tempObj == null) {
            tempObj = request.getParameter(key);
        }

        if(tempObj == null) {
            tempObj = request.getSession().getAttribute(key);
        }

        if(tempObj == null) {
            tempObj = request.getSession().getAttribute(key);
        }

        Object obj = null;
        if(tempObj != null) {
            obj = tempObj;
        }

        return (T) obj;
    }

    static {
        contentTypeMap.put("gif", "image/gif");
        contentTypeMap.put("jpg", "image/jpeg");
        contentTypeMap.put("jpeg", "image/jpeg");
        contentTypeMap.put("png", "image/png");
        contentTypeMap.put("tif", "image/tiff");
        contentTypeMap.put("tiff", "image/tiff");
        contentTypeMap.put("xbm", "image/x-xbitmap");
        contentTypeMap.put("xpm", "image/x-xpixmap");
        contentTypeMap.put("xls", "application/vnd.ms-excel");
        contentTypeMap.put("doc", "application/msword");
        charMap.put(">", "&gt;");
        charMap.put("<", "&lt;");
        charMap.put(">=", "&gt;=");
        charMap.put("<=", "&lt;=");
        charMap.put("<>", "&lt;&gt;");
        charMap.put("\"", "&#34;");
        charMap.put("\'", "&#39;");
    }
}
