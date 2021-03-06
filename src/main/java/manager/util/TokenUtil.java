package manager.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class TokenUtil {
    private static final long EXPIRE_TIME = 2 * 120 * 60 * 1000;
    private static final String TOKEN_SECRET = "thefirsttoken123";

    /**
     * 生成签名，15分钟过期
     * @param **username**
     * @param **password**
     * @return
     */
    public static String sign(String username, String password,Integer roleId) {
        try {
            // 设置过期时间
            Date date = new Date(System.currentTimeMillis() + EXPIRE_TIME);
            // 私钥和加密算法
            Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
            // 设置头部信息
            Map<String, Object> header = new HashMap<>(2);
            header.put("Type", "Jwt");
            header.put("alg", "HS256");
            // 返回token字符串
            return JWT.create()
                    .withHeader(header)
                    .withClaim("username", username)
                    .withClaim("password", password)
                    .withClaim("roleId", roleId)
                    .withExpiresAt(date)
                    .sign(algorithm);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 检验token是否正确
     * @param **token**
     * @return
     */
    public static boolean verify(String token){
        if (null == token){
            return false;
        }
        try {
            Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
            JWTVerifier verifier = JWT.require(algorithm).build();
            verifier.verify(token);
            return true;
        } catch (Exception e){
            return false;
        }
    }
    /**
     * 从token中获取username信息
     * 此处username等同于sn
     * @param **token**
     * @return
     */
    public static String getUserName(String token){
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim("username").asString();
        } catch (JWTDecodeException e){
            e.printStackTrace();
            return null;
        }
    }
    //获取角色信息
    public static Integer getRoleid(String token){
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim("roleId").asInt();
        } catch (JWTDecodeException e){
            e.printStackTrace();
            return null;
        }
    }
}
