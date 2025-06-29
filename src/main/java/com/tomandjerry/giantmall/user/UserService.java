package com.tomandjerry.giantmall.user;

import com.tomandjerry.giantmall.user.dto.SignUpRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void signUp(SignUpRequest request) {
        // 1. 이메일 중복 확인
        if (userRepository.findByEmail(request.email()).isPresent()) {
            throw new IllegalArgumentException("이미 사용 중인 이메일입니다.");
        }

        // 2. DTO를 엔티티로 변환 (비밀번호 암호화 포함)
        String encodedPassword = passwordEncoder.encode(request.password());
        User newUser = request.toEntity(encodedPassword);

        // 3. 데이터베이스에 저장
        userRepository.save(newUser);
    }
}