package com.lumiere.jewelryapi.service;

import com.lumiere.jewelryapi.dto.AuthResponse;
import com.lumiere.jewelryapi.dto.LoginRequest;
import com.lumiere.jewelryapi.dto.RegisterRequest;
import com.lumiere.jewelryapi.entity.User;
import com.lumiere.jewelryapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthResponse register(RegisterRequest req) {
        if (userRepository.existsByEmail(req.email())) {
            throw new IllegalArgumentException("Bu email zaten kayıtlı");
        }

        User user = User.builder()
                .firstName(req.firstName())
                .lastName(req.lastName())
                .email(req.email())
                .phone(req.phone())
                .password(passwordEncoder.encode(req.password()))
                .role("USER")
                .build();

        User saved = userRepository.save(user);
        String token = jwtService.generateToken(saved.getEmail());

        return new AuthResponse(
                token,
                "Kayıt başarılı",
                saved.getId(),
                saved.getEmail(),
                saved.getFirstName(),
                saved.getLastName()
        );
    }

    public AuthResponse login(LoginRequest req) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(req.email(), req.password())
            );
        } catch (BadCredentialsException e) {
            throw new IllegalArgumentException("Email veya şifre hatalı");
        }

        User user = userRepository.findByEmail(req.email())
                .orElseThrow(() -> new IllegalArgumentException("Kullanıcı bulunamadı"));

        String token = jwtService.generateToken(user.getEmail());

        return new AuthResponse(
                token,
                "Giriş başarılı",
                user.getId(),
                user.getEmail(),
                user.getFirstName(),
                user.getLastName()
        );
    }
}