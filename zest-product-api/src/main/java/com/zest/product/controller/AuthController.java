package com.zest.product.controller;

import com.zest.product.dto.LoginRequestDTO;
import com.zest.product.dto.LoginResponseDTO;
import com.zest.product.entity.RefreshToken;
import com.zest.product.entity.User;
import com.zest.product.repository.RefreshTokenRepository;
import com.zest.product.repository.UserRepository;
import com.zest.product.security.JwtAuthenticationFilter;
import com.zest.product.security.JwtUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final RefreshTokenRepository refreshTokenRepository;
    private final UserRepository userRepository;

    @PostMapping("/login")
    @Transactional
    public LoginResponseDTO login(@RequestBody LoginRequestDTO req) {
        Authentication authentication =
                authenticationManager.authenticate(new
                        UsernamePasswordAuthenticationToken(req.getUsername(), req.getPassword()));

        String accessToken = jwtUtil.generateToken(req.getUsername());

        User user = userRepository.findByUsername(req.getUsername()).orElseThrow();

        String refreshTokenValue = UUID.randomUUID().toString();

        RefreshToken refreshToken = refreshTokenRepository
                .findByUser(user)
                .orElse(RefreshToken.builder()
                        .user(user)
                        .build()
                );

        refreshToken.setToken(refreshTokenValue);
        refreshToken.setExpiryDate(LocalDateTime.now().plusDays(7));

        refreshTokenRepository.save(refreshToken);

        return  LoginResponseDTO.builder()
                .accessToken(accessToken)
                .refreshToken(refreshTokenValue)
                .build();
    }

    @PostMapping("/refresh")
    public LoginResponseDTO refresh(@RequestParam String refreshToken){

        RefreshToken token = refreshTokenRepository.findByToken(refreshToken)
                .orElseThrow(() -> new RuntimeException("Invliad refresh token"));

        if (token.getExpiryDate().isBefore(LocalDateTime.now())){
            throw new RuntimeException("Refresh token expired");
        }

        String newAccessToken = jwtUtil.generateToken(token.getUser().getUsername());

        String newRefreshTokenValue = UUID.randomUUID().toString();

        token.setToken(newRefreshTokenValue);
        token.setExpiryDate(LocalDateTime.now().plusDays(7));

        refreshTokenRepository.save(token);

        return LoginResponseDTO.builder()
                .accessToken(newAccessToken)
                .refreshToken(newRefreshTokenValue)
                .build();

    }
}
