package com.tomandjerry.giantmall.user;

import com.tomandjerry.giantmall.common.jwt.JwtTokenProvider;
import com.tomandjerry.giantmall.user.dto.LoginRequest;
import com.tomandjerry.giantmall.user.dto.SignUpRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "인증 API", description = "사용자 회원가입/로그인/로그아웃")
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;

    @Operation(summary = "회원가입", description = "새로운 사용자를 시스템에 등록합니다.")
    @ApiResponse(responseCode = "201", description = "회원가입 성공", content = @Content(schema = @Schema(implementation = String.class)))
    @PostMapping("/signup")
    public ResponseEntity<String> signUp(@Valid @RequestBody SignUpRequest signUpRequest) {
        userService.signUp(signUpRequest);

        return ResponseEntity.status(HttpStatus.CREATED)
            .body("회원가입이 성공적으로 완료되었습니다.");
    }

    @Operation(summary = "로그인", description = "이메일과 비밀번호로 로그인하고 JWT를 발급받습니다.")
    @ApiResponse(responseCode = "200", description = "로그인 성공 및 토큰 발급", content = @Content(schema = @Schema(implementation = String.class)))
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest,
        HttpServletResponse response) {

        // 1. AuthenticationManager에게 인증 위임
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                loginRequest.email(),
                loginRequest.password()
            )
        );
        // 2. 인증 성공 시 SecurityContext에 저장
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // 3. JWT 토큰 생성
        String accessToken = jwtTokenProvider.createToken(authentication);

        // 4. JWT를 HttpOnly 쿠키에 담기
        ResponseCookie cookie = ResponseCookie.from("accessToken", accessToken)
            .path("/")
            .httpOnly(true)
            .secure(false)
            .maxAge(60 * 60 * 24)
            .sameSite("Lax")
            .build();

        // 5. 응답 헤더에 쿠키 추가
        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());

        return ResponseEntity.ok("로그인에 성공했습니다.");
    }

    @PostMapping("/logout")
    @Operation(summary = "로그아웃", description = "로그아웃하고 쿠키에 저장된 JWT를 삭제합니다.")
    @ApiResponse(responseCode = "200", description = "로그아웃 및 토큰 삭제", content = @Content(schema = @Schema(implementation = String.class)))
    public ResponseEntity<String> logout(HttpServletResponse response) {

        ResponseCookie cookie = ResponseCookie.from("accessToken", "")
            .path("/")
            .httpOnly(true)
            .secure(false)
            .maxAge(0)
            .sameSite("Lax")
            .build();

        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());

        return ResponseEntity.ok("로그아웃 되었습니다.");
    }
}