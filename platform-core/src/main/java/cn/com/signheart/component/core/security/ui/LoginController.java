package cn.com.signheart.component.core.security.ui;

import cn.com.signheart.common.platformbase.BaseController;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by ao.ouyang on 15-11-4.
 */
@Controller
@RequestMapping("/securi/")
public class LoginController extends BaseController {

    private static final transient Logger logger = LoggerFactory.getLogger(LoginController.class);


    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public ModelAndView login(HttpServletRequest request,@RequestParam(required = true) String userName,
                                                         @RequestParam(required = true)  String password) throws  Exception{
        String error = null;
        ModelAndView md = new ModelAndView();

        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(userName, password);
        try {
            subject.login(token);
        } catch (UnknownAccountException e) {
            error = "用户名不存在";
        } catch (IncorrectCredentialsException e) {
            error = "密码错误";
        } catch (AuthenticationException e) {
            //其他错误，比如锁定，如果想单独处理请单独catch处理
            error = "其他错误：" + e.getMessage();
        }
        if(error != null) {//出错了，返回登录页面
            md.addObject("error",error);
            md.setViewName("/default/log_in");
            return  md;
        } else {//登录成功
            md.setViewName("/default/login_success");
            return md;
        }



    }

}
