package com.snailmann.tensquare.user.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;

import java.time.LocalDateTime;
import java.util.Date;

public class JwtTest {


    public static void main(String[] args) throws InterruptedException {
        String jwt = createJwt();
        Thread.sleep(11000); //模拟JWT过期
        parseJwt(jwt);
    }

    private static void parseJwt(String jwt) {
        Claims claims = Jwts.parser( )
                .setSigningKey("tensquare")
                .parseClaimsJws(jwt)
                .getBody();
        System.out.println(claims);
        System.out.println("用户ID/JWT ID: " + claims.getId());
        System.out.println("用户名: " + claims.getSubject());
        System.out.println("登录时间/签发时间: " +claims.getIssuedAt());
        System.out.println("过期时间: " + claims.getExpiration());
    }

    private static String createJwt() {
        JwtBuilder jwtBuilder = Jwts.builder()
                .setId("666") //jti : JWT ID
                .setHeaderParam("type","jwt") // 头部参数，自定义
                .setSubject("SnailMann") // sub : 用户名
                .setIssuedAt(new Date()) // jat: 签发时间
                .setExpiration(DateUtils.addSeconds(new Date(),10))
                .signWith(SignatureAlgorithm.HS256,"tensquare");


        String jwt = jwtBuilder.compact();
        System.out.println("签发：" + jwt);
        return jwt;
    }
}
