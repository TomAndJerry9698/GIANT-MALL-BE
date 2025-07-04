package com.tomandjerry.giantmall.common.jwt;

import com.tomandjerry.giantmall.user.CustomUserDetailsService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.util.Date;
import java.util.stream.Collectors;
import javax.crypto.SecretKey;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class JwtTokenProvider {

    @Autowired
    private final CustomUserDetailsService userDetailsService;

    private static final String AUTHORITIES_KEY = "auth";
    private final SecretKey secretKey;
    private final long tokenValidityInMilliseconds;
    
    // @Value : 설정 파일(application.yml)에 정의된 값을 자바 코드의 변수로 직접 주입
    public JwtTokenProvider(
        @Value("${jwt.secret}") String secret, CustomUserDetailsService userDetailsService, @Value("${jwt.token-validity-in-seconds}") long tokenValidityInSeconds
    ) {
        this.userDetailsService = userDetailsService;
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        this.secretKey = Keys.hmacShaKeyFor(keyBytes);
        this.tokenValidityInMilliseconds = tokenValidityInSeconds * 1000;
    }

    /**
     * Authentication 객체로 JWT 토큰 생성 및 반환
     */
    public String createToken(Authentication authentication) {
        // 1. 권한 정보를 comma-separated string으로 변환
        String authorities = authentication.getAuthorities().stream()
            .map(GrantedAuthority::getAuthority)
            .collect(Collectors.joining(","));

        Date now = new Date();
        Date validity = new Date(now.getTime() + this.tokenValidityInMilliseconds);

        return Jwts.builder()
            .subject(authentication.getName())
            .claim(AUTHORITIES_KEY, authorities) // payload에 권한 정보 저장
            .issuedAt(now) // 토큰 발행 시간
            .expiration(validity) // 토큰 만료 시간
            .signWith(secretKey) // 서명 키 지정 (알고리즘은 키에 따라 자동 지정)
            .compact();
    }

    /**
     * 토큰으로 Authentication 객체 생성 및 반환
     */
    public Authentication getAuthentication(String token) {
        Claims claims = Jwts.parser()
            .verifyWith(secretKey)
            .build()
            .parseSignedClaims(token)
            .getPayload();

        String email = claims.getSubject();
        UserDetails userDetails = userDetailsService.loadUserByUsername(email);

        return new UsernamePasswordAuthenticationToken(userDetails, token, userDetails.getAuthorities());
    }

    /**
     * 토큰 유효성 + 만료일자 확인
     */
    public boolean validateToken(String token) {
        try {
            // 파싱 과정에서 예외가 발생하지 않으면 유효한 토큰
            Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token);
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            log.info("잘못된 JWT 서명입니다.");
        } catch (ExpiredJwtException e) {
            log.info("만료된 JWT 토큰입니다.");
        } catch (UnsupportedJwtException e) {
            log.info("지원되지 않는 JWT 토큰입니다.");
        } catch (IllegalArgumentException e) {
            log.info("JWT 토큰이 잘못되었습니다.");
        }
        return false;
    }
}