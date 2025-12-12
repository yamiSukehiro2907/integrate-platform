package com.integrate.identity.services;

import com.integrate.identity.domain.User;
import com.integrate.identity.dto.ApiResponse;
import com.integrate.identity.dto.LoginRequest;
import com.integrate.identity.dto.SignUpRequest;
import com.integrate.identity.dto.UserDto;
import com.integrate.identity.helpers.AuthHelper;
import com.integrate.identity.helpers.UserHelper;
import com.integrate.identity.repository.UserRepository;
import com.integrate.identity.security.CustomUserDetails;
import com.integrate.identity.utils.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RefreshTokenService refreshTokenService;
    private final JwtUtil jwtUtil;

    public ResponseEntity<?> registerUser(SignUpRequest signUpRequest) {
        Optional<User> optionalUser = userRepository.findByEmail(signUpRequest.email());
        if (optionalUser.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(ApiResponse.error("Email already exists."));
        }
        User user = new User();
        user.setEmail(signUpRequest.email());
        user.setPassword(passwordEncoder.encode(signUpRequest.password()));
        user.setFullName(signUpRequest.name());
        userRepository.save(user);
        UserDto userDto = UserHelper.createUserDto(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success("User created successfully!", userDto));
    }

    public ResponseEntity<?> loginUser(LoginRequest loginRequest, HttpServletResponse httpServletResponse) {
        Optional<User> optionalUser = userRepository.findByEmail(loginRequest.email());
        if (optionalUser.isPresent()) {
            if (!passwordEncoder.matches(loginRequest.password(), optionalUser.get().getPassword())) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ApiResponse.error("Incorrect password!"));
            }
            createAndSaveTokens(httpServletResponse, optionalUser.get());
            return ResponseEntity.ok(ApiResponse.success("Login successful!", UserHelper.createUserDto(optionalUser.get())));
        }
        return ResponseEntity.badRequest().body(ApiResponse.error("User not found!"));
    }

    public ResponseEntity<?> refreshUser(HttpServletRequest request, HttpServletResponse response) {
        try {
            String refreshToken = AuthHelper.getRefreshTokenFromRequest(request);
            if (refreshToken == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ApiResponse.error("Refresh token is empty!"));
            }
            refreshTokenService.deleteToken(refreshToken);
            String email = jwtUtil.getEmail(refreshToken);
            Optional<User> user = userRepository.findByEmail(email);
            if (user.isEmpty())
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.error("User Not Found!"));
            if (!jwtUtil.isValidRefreshToken(refreshToken, new CustomUserDetails(user.get()))) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ApiResponse.error("Refresh token is empty!"));
            }
            createAndSaveTokens(response, user.get());
            return ResponseEntity.ok(ApiResponse.success("Refresh successful!"));
        } catch (Exception _) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ApiResponse.error("Internal Server Error!"));
        }
    }

    private void createAndSaveTokens(HttpServletResponse response, User user) {
        String accessToken = jwtUtil.generateAccessToken(new CustomUserDetails(user));
        String newRefreshToken = jwtUtil.generateRefreshToken(new CustomUserDetails(user));
        response.addCookie(AuthHelper.createAccessTokenCookie(accessToken, 60 * 60));
        response.addCookie(AuthHelper.createRefreshTokenCookie(newRefreshToken, 7 * 24 * 60 * 60));
        refreshTokenService.createRefreshToken(newRefreshToken, user);
    }

    public ResponseEntity<?> logoutUser(HttpServletRequest request, HttpServletResponse response) {
        try {
            String refreshToken = AuthHelper.getRefreshTokenFromRequest(request);
            if (refreshToken != null) refreshTokenService.deleteToken(refreshToken);
        } catch (Exception _) {
        }
        return ResponseEntity.ok(ApiResponse.success("Logout successful!"));
    }
}
