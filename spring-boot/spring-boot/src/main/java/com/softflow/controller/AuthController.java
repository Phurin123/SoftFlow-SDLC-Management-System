package com.softflow.controller;


import com.softflow.auth.JwtUtil;
import com.softflow.dto.request.LoginRequest;
import com.softflow.dto.response.LoginResponse;
import com.softflow.entity.User;
import com.softflow.enums.UserRole;
import com.softflow.repository.UserRepository;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "${cors.allowed-origins}")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    request.getEmail(),
                    request.getPassword()
                )
            );

            User user = userRepository.findByEmail(request.getEmail())
                    .orElseThrow(() -> new BadCredentialsException("ไม่พบผู้ใช้งาน"));

            if (!user.getIsActive()) {
                return ResponseEntity.badRequest()
                    .body(createErrorResponse("บัญชีนี้ถูกระงับการใช้งาน"));
            }

            String token = jwtUtil.generateToken(user.getEmail());

            LoginResponse response = LoginResponse.builder()
                    .token(token)
                    .id(user.getId())
                    .email(user.getEmail())
                    .fullName(user.getFullName())
                    .role(user.getRole())
                    .message("เข้าสู่ระบบสำเร็จ")
                    .build();

            return ResponseEntity.ok(response);

        } catch (BadCredentialsException e) {
            return ResponseEntity.status(401)
                .body(createErrorResponse("อีเมลหรือรหัสผ่านไม่ถูกต้อง"));
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody LoginRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            return ResponseEntity.badRequest()
                .body(createErrorResponse("อีเมลนี้มีผู้ใช้งานแล้ว"));
        }

        User user = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .fullName(request.getEmail().split("@")[0])
                .role(com.softflow.enums.UserRole.VIEWER)
                .isActive(true)
                .build();

        userRepository.save(user);
        return ResponseEntity.ok(createSuccessResponse("สมัครสมาชิกสำเร็จ"));
    }

    private Map<String, String> createErrorResponse(String message) {
        Map<String, String> map = new HashMap<>();
        map.put("error", message);
        return map;
    }

    private Map<String, String> createSuccessResponse(String message) {
        Map<String, String> map = new HashMap<>();
        map.put("message", message);
        return map;
    }
}