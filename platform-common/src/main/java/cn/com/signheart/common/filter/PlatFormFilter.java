
package cn.com.signheart.common.filter;

import cn.com.signheart.common.holder.RequestContextHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class PlatFormFilter extends OncePerRequestFilter {
    private static final transient Logger logger = LoggerFactory.getLogger(PlatFormFilter.class);
    public PlatFormFilter() {
    }

    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        RequestContextHolder.requestThreadLocal.set(request);
        RequestContextHolder.responseThreadLocal.set(response);

        try {
        } catch (Exception var6) {
            this.logger.error(var6.getMessage(), var6);
            response.sendError(500, var6.getMessage());
        }
        filterChain.doFilter(request, response);
        RequestContextHolder.clear();
    }
}
