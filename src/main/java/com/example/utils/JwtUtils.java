package com.example.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.Map;


public class JwtUtils {
    private static String key = "test";
    private static Long expire = 60 * 1000 * 60 * 12L;


    /**
     * 生成jwt
     */
    public static String GenerateJwt(Map claims) {
        String jwt = Jwts.builder().signWith(SignatureAlgorithm.HS256, key)
                .setClaims(claims)
                .setExpiration(new Date(System.currentTimeMillis() + expire))
                .compact();
        return jwt;

    }

    /**
     * 解析jwt
     * @param jwt
     * @return
     */
    public static Claims parseJwt(String jwt){
        Claims claims = Jwts.parser().setSigningKey(key)
                .parseClaimsJws(jwt)
                .getBody();
        return  claims;
    }
}
