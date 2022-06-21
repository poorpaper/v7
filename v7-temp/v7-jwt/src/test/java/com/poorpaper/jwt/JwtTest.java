package com.poorpaper.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.Test;

import java.util.Date;

public class JwtTest {

    @Test
    public void createJwtTokenTest() {
        JwtBuilder builder = Jwts.builder()
                .setId("666").setSubject("行走在行走的路上")
                .setIssuedAt(new Date())
//                .claim("role", "admin")
                .setExpiration(new Date(new Date().getTime()+30000))
                .signWith(SignatureAlgorithm.HS256, "poorpaper");
        String jwtToken = builder.compact();
        System.out.println(jwtToken);
        // eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiI2NjYiLCJzdWIiOiLooYzotbDlnKjooYzotbDnmoTot6_kuIoiLCJpYXQiOjE2NTU3OTI5OTAsImV4cCI6MTY1NTc5MzAyMH0.FXBpakD76TPuSDtHILXFULaAhpLCAZvvO-zsmubSIcs
    }

    @Test
    public void parseToken() {
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiI2NjYiLCJzdWIiOiLooYzotbDlnKjooYzotbDnmoTot6_kuIoiLCJpYXQiOjE2NTU3OTMyMDMsImV4cCI6MTY1NTc5MzIzM30.K4XVQpAnB03AY9Za5yo62oD_RzgjskH1ifM3dUbGXY8";
        Claims claims = Jwts.parser().setSigningKey("poorpaper")
                .parseClaimsJws(token).getBody();
        System.out.println(claims.getId());
        System.out.println(claims.getSubject());
        System.out.println(claims.getIssuedAt());
        System.out.println(claims.getExpiration());

        // 获取自定义属性
        // System.out.println(claims.get("role");

        // 过期的时候，会抛出异常：ExpiredJwtException
        // 令牌存在问题的时候，会抛出异常：SignatureException
    }
}
