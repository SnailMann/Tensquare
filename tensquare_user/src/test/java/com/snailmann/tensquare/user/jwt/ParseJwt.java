package com.snailmann.tensquare.user.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

/**
 * {jti=666, sub=SnailMann, iat=1565102328}
 * 用户ID/JWT ID: 666
 * 用户名: SnailMann
 * 登录时间/签发时间: Tue Aug 06 22:38:48 CST 2019
 */
public class ParseJwt {

    public static void main(String[] args) {
        Claims claims = Jwts.parser( )
                .setSigningKey("tensquare")
                .parseClaimsJws("eyJ0eXBlIjoiand0IiwiYWxnIjoiSFMyNTYifQ.eyJqdGkiOiI2NjYiLCJzdWIiOiJTbmFpbE1hbm4iLCJpYXQiOjE1NjUxMDIzMjh9.FBuviL-iEarBboNWTD6lg_XwMhDq5Q5-CQX8pBDw9OE")
                .getBody();
        System.out.println(claims);
        System.out.println("用户ID/JWT ID: " + claims.getId());
        System.out.println("用户名: " + claims.getSubject());
        System.out.println("登录时间/签发时间: " +claims.getIssuedAt());

    }
}
