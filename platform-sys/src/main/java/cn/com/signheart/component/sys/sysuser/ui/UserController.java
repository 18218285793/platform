package cn.com.signheart.component.sys.sysuser.ui;

import cn.com.signheart.common.platformbase.BaseController;
import cn.com.signheart.common.util.EhcacheUtil;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by ao.ouyang on 16-1-8.
 */
@Controller
@RequestMapping("/user")
public class UserController extends BaseController {


    @RequestMapping("/ckpre")
    public void checkPer()throws Exception{
        EhcacheUtil.getInstance().get("authorizationCache");
        System.out.println(SecurityUtils.getSubject().isPermitted("test1"));
    }

    @RequestMapping("/test")
    public String test(){
        return "/test";
    }




}
