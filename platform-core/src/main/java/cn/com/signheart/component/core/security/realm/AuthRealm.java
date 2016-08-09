package cn.com.signheart.component.core.security.realm;

import cn.com.signheart.component.core.user.model.TbUserPO;
import cn.com.signheart.component.core.user.service.IUserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;

/**
 * Created by ao.ouyang on 15-11-4.
 */
public class AuthRealm extends AuthorizingRealm {
    private static final transient Logger logger = LoggerFactory.getLogger(AuthRealm.class);

    @Resource
    private IUserService userService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        String username = (String) principalCollection.getPrimaryPrincipal();
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        try {
            authorizationInfo.setRoles(userService.searchUserRole(username));
            authorizationInfo.setStringPermissions(userService.searchUserPermissions(username));
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
        }
        return authorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String username = (String)authenticationToken.getPrincipal();
        TbUserPO userPO = null;
        try {
            userPO = userService.searchUser(username);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        if(userPO == null) {
            throw new UnknownAccountException();
        }
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                userPO.getUserName(),
                userPO.getPassword(),
                ByteSource.Util.bytes(userPO.getCredentialsSalt()),
                getName()
        );
        return authenticationInfo;
    }
}
