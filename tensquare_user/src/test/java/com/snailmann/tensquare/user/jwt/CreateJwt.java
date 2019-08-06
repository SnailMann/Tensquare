package com.snailmann.tensquare.user.jwt;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

/**
 * 简单实用JJWT工具，签发JWT字符串
 * output :
 * eyJ0eXBlIjoiand0IiwiYWxnIjoiSFMyNTYifQ.eyJqdGkiOiI2NjYiLCJzdWIiOiJTbmFpbE1hbm4iLCJpYXQiOjE1NjUxMDE3NDl9.pHpqToaxie9mtJM775AaL5j_p_mKoIC9XtY4NJ59_bg
 * {"type":"jwt","alg":"HS256"}.{"jti":"666","sub":"SnailMann","iat":1565101749}.xxxxxxxx...
 */
public class CreateJwt {

    public static void main(String[] args) {
        JwtBuilder jwtBuilder = Jwts.builder()
                .setId("666") //jti : JWT ID
                .setHeaderParam("type","jwt") // 头部参数，自定义
                .setSubject("SnailMann") // sub : 用户名
                .setIssuedAt(new Date()) // jat: 签发时间
                .signWith(SignatureAlgorithm.HS256,"tensquare");
        System.out.println(jwtBuilder.compact());

    }
}