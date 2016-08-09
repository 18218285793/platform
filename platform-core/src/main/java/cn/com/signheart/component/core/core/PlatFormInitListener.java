package cn.com.signheart.component.core.core;

import cn.com.signheart.common.exception.DefaultException;
import cn.com.signheart.component.core.security.system.AuthMgr;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.net.InetAddress;

/**
 * Created by ao.ouyang on 15-11-5.
 */
public class PlatFormInitListener implements ServletContextListener, HttpSessionListener, HttpSessionAttributeListener {
    private static final transient Logger logger = LoggerFactory.getLogger(PlatFormInitListener.class);


    @Override
    public void contextInitialized(ServletContextEvent sce) {

        try {
            ServletContext e = sce.getServletContext();
            InetAddress addr = InetAddress.getLocalHost();
            PlatFormContext.setCurrentIP(addr.getHostAddress());
            WebApplicationContext ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(e);
            PlatFormContext.setSpringContext(ctx);
            AuthMgr.loadRolePermission();
            FrameLoader loader = new FrameLoader();
            loader.loadFrame();

        }catch (Exception e){
            e.printStackTrace();
            logger.error(e.getMessage(),e);
        } catch (DefaultException e) {
            logger.error(e.getMessage(),e);
        }

    }

    @Override
    public void attributeAdded(HttpSessionBindingEvent httpSessionBindingEvent) {

    }

    @Override
    public void attributeRemoved(HttpSessionBindingEvent httpSessionBindingEvent) {

    }

    @Override
    public void attributeReplaced(HttpSessionBindingEvent httpSessionBindingEvent) {

    }

    @Override
    public void sessionCreated(HttpSessionEvent httpSessionEvent) {

    }

    @Override
    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {

    }


    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
