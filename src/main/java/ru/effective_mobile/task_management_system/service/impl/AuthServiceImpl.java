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
import ru.effective_mobile.task_management_system.exception.user.UserEmailNotFoundException;
import ru.effective_mobile.task_management_system.model.User;
import ru.effective_mobile.task_management_system.model.UserDetailsImpl;
import ru.effective_mobile.task_management_system.repository.UserRepository;
import ru.effective_mobile.task_management_system.service.AuthService;
import ru.effective_mobile.task_management_system.service.JwtService;

import static org.apache.commons.lang3.StringUtils.capitalize;
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
    public JwtDTO register(RegisterDTO dto) {

        if (userRepository.findByEmail(dto.getEmail().toLowerCase()).isPresent()) {
            throw new EmailAlreadyUse();
        }

        var userDetails = UserDetailsImpl
                .builder()
                .user(User.builder()
                        .firstName(capitalize(dto.getFirstName().toLowerCase()))
                        .lastName(capitalize(dto.getLastName().toLowerCase()))
                        .email(dto.getEmail().toLowerCase())
                        .password(passwordEncoder.encode(dto.getPassword()))
                        .role(USER)
                        .build())
                .build();

        userRepository.save(userDetails.getUser());

        return new JwtDTO(jwtService.generateToken(userDetails));
    }

    @Override
    public JwtDTO login(LoginDTO dto) {
        var user = userRepository.findByEmail(dto.getEmail().toLowerCase())
                .orElseThrow(UserEmailNotFoundException::new);

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                dto.getEmail().toLowerCase(),
                dto.getPassword()
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