package com.tomandjerry.giantmall.user.dto;

import com.tomandjerry.giantmall.user.User;
import com.tomandjerry.giantmall.user.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

// record를 사용하면 Getter, 생성자 등을 자동으로 만들어주어 편리합니다.
public record SignUpRequest(
    @NotBlank(message = "이메일은 필수 입력 값입니다.")
    @Email(message = "이메일 형식이 올바르지 않습니다.")
    String email,

    @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
    @Size(min = 4, message = "비밀번호는 4자 이상이어야 합니다.")
    String password,

    @NotBlank(message = "이름은 필수 입력 값입니다.")
    String name,

    @NotBlank(message = "전화번호는 필수 입력 값입니다.")
    @Pattern(regexp = "^\\d{3}-\\d{3,4}-\\d{4}$", message = "전화번호 형식이 올바르지 않습니다. (예: 010-1234-5678)")
    String phone,

    // 회원가입 시 역할을 선택적으로 받도록 설정 (null이 아니어야 함)
    @NotBlank(message = "역할은 필수 입력 값입니다. (SELLER, BUYER, ADMIN)")
    String role
) {

    // DTO를 User 엔티티로 변환하는 메소드 (비밀번호 암호화는 서비스 계층에서 처리)
    public User toEntity(String encodedPassword) {
        return User.builder()
            .email(this.email)
            .password(encodedPassword)
            .name(this.name)
            .phone(this.phone)
            .role(UserRole.valueOf(this.role.toUpperCase())) // 문자열을 Enum으로 변환
            .build();
    }
}