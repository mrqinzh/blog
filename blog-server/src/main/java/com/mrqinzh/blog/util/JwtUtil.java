package com.mrqinzh.blog.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtUtil {

    private static final long EXPIRE_TIME = 1000 * 60 * 60;    // 过期时间 1h

    private static final String TOKEN_SECRET = "@#%DT$%GDG";    // 签名秘钥

    /**
     * 生成 token
     * 不需要存放用户信息
     */
    public static String getToken(){
        Map<String, Object> map = new HashMap<>();
        map.put("alg", "HS256");
        map.put("typ", "JWT");
        String token = JWT.create()
                .withHeader(map)
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRE_TIME)) // 过期时间
                .sign(Algorithm.HMAC256(TOKEN_SECRET)); // 秘钥
        System.out.println(token);
        return token;
    }

    /**
     * 生成 token
     * @param claims 返回token的body中需要存放的信息
     */
    public static String getTokenWithClaim(Map<String,String> claims) {
        JWTCreator.Builder builder = JWT.create();
        for (Map.Entry<String, String> entry : claims.entrySet()) {
            builder.withClaim(entry.getKey(), entry.getValue());
        }
        builder.withIssuer("auth0")
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRE_TIME)); // 过期时间
        return builder.sign(Algorithm.HMAC256(TOKEN_SECRET));
    }

    /**
     * 验证 token
     */
    public static boolean verifyToken(String token){
        try {
            JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(TOKEN_SECRET)).withIssuer("auth0").build(); // 创建token验证器
            jwtVerifier.verify(token);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * 解析token中存放的用户信息
     * @param name 需要获取信息的键名 key
     */
    public static String parseToken(String token, String name){
        String res = "";
        DecodedJWT decode = JWT.decode(token);
        Map<String, Claim> claims = decode.getClaims();
        if (claims.containsKey(name)) {
            res = decode.getClaim(name).asString();
        }
        return res;
    }
}
