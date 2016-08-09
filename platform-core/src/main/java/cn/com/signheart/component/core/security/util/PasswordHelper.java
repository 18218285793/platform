package cn.com.signheart.component.core.security.util;

import cn.com.signheart.component.core.user.model.TbUserPO;
import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

/**
 * Created by ao.ouyang on 15-11-4.
 */
public final class PasswordHelper {

    private static RandomNumberGenerator randomNumberGenerator = null;
    private static String algorithmName = "md5";
    private static int hashIterations = 2;


    public static void encryptPassword(TbUserPO user) {
        randomNumberGenerator = new SecureRandomNumberGenerator();
        user.setSalt(randomNumberGenerator.nextBytes().toHex());

        String newPassword = new SimpleHash(
                algorithmName,
                user.getPassword(),
                ByteSource.Util.bytes(user.getCredentialsSalt()),
                hashIterations).toHex();

        user.setPassword(newPassword);
    }
}
