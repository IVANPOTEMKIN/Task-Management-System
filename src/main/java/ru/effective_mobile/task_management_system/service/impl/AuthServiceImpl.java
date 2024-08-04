package ru.effective_mobile.task_management_system.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import ru.effective_mobile.task_management_system.dto.security.JwtDTO;
import ru.effective_mobile.task_management_system.dto.security.LoginDTO;
import ru.effective_mobile.task_management_system.dto.security.RegisterDTO;
import ru.effective_mobile.task_management_system.exception.EmailAlreadyUse;
import ru.effective_mobile.task_management_system.exception.UserNotFoundException;
import ru.effective_mobile.task_management_system.model.User;
import ru.effective_mobile.task_management_system.model.UserDetailsImpl;
import ru.effective_mobile.task_management_system.repository.UserRepository;
import ru.effective_mobile.task_management_system.service.AuthService;
import ru.effective_mobile.task_management_system.service.JwtService;

import static ru.effective_mobile.task_management_system.enums.Role.USER;

@Service
@Validated
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    @Override
    public JwtDTO register(RegisterDTO registerDTO) {

        if (userRepository.findByEmail(registerDTO.getEmail()).isPresent()) {
            throw new EmailAlreadyUse();
        }

        var userDetails = UserDetailsImpl
                .builder()
                .user(User.builder()
                        .firstName(registerDTO.getFirstName())
                        .lastName(registerDTO.getLastName())
                        .email(registerDTO.getEmail())
                        .password(passwordEncoder.encode(registerDTO.getPassword()))
                        .role(USER)
                        .build())
                .build();

        userRepository.save(userDetails.getUser());

        return new JwtDTO(jwtService.generateToken(userDetails));
    }

    @Override
    public JwtDTO login(LoginDTO loginDTO) {
        var user = userRepository.findByEmail(loginDTO.getEmail())
                .orElseThrow(UserNotFoundException::new);

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDTO.getEmail(),
                loginDTO.getPassword()
        ));

        return new JwtDTO(
                jwtService.generateToken(
                        UserDetailsImpl
                                .builder()
                                .user(user)
                                .build())
        );
    }
}