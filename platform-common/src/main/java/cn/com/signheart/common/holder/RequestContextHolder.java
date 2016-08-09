
package cn.com.signheart.common.holder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RequestContextHolder {
    public static ThreadLocal<HttpServletRequest> requestThreadLocal = new ThreadLocal();
    public static ThreadLocal<HttpServletResponse> responseThreadLocal = new ThreadLocal();

    public RequestContextHolder() {
    }

    public static void clear() {
        requestThreadLocal.remove();
        responseThreadLocal.remove();
    }
}
