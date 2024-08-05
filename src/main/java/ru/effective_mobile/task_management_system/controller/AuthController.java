package ru.effective_mobile.task_management_system.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.effective_mobile.task_management_system.dto.security.JwtDTO;
import ru.effective_mobile.task_management_system.dto.security.LoginDTO;
import ru.effective_mobile.task_management_system.dto.security.RegisterDTO;
import ru.effective_mobile.task_management_system.service.AuthService;

import static org.springframework.http.HttpStatus.CREATED;
import static ru.effective_mobile.task_management_system.controller.utils.Utils.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Аутентификация")
public class AuthController {

    private final AuthService authService;

    @Operation(
            summary = "Регистрация пользователя",
            responses = {
                    @ApiResponse(
                            responseCode = CODE_201,
                            description = DESCRIPTION_CODE_201,
                            content = @Content()
                    ),
                    @ApiResponse(
                            responseCode = CODE_400,
                            description = DESCRIPTION_CODE_400,
                            content = @Content()
                    ),
                    @ApiResponse(
                            responseCode = CODE_500,
                            description = DESCRIPTION_CODE_500,
                            content = @Content()
                    )
            }
    )
    @PostMapping("/register")
    public ResponseEntity<JwtDTO> register(@RequestBody RegisterDTO dto) {
        return ResponseEntity
                .status(CREATED)
                .body(authService.register(dto));
    }

    @Operation(
            summary = "Авторизация пользователя",
            responses = {
                    @ApiResponse(
                            responseCode = CODE_200,
                            description = DESCRIPTION_CODE_200,
                            content = @Content()
                    ),
                    @ApiResponse(
                            responseCode = CODE_400,
                            description = DESCRIPTION_CODE_400,
                            content = @Content()
                    ),
                    @ApiResponse(
                            responseCode = CODE_404,
                            description = DESCRIPTION_CODE_404,
                            content = @Content()
                    ),
                    @ApiResponse(
                            responseCode = CODE_500,
                            description = DESCRIPTION_CODE_500,
                            content = @Content()
                    )
            }
    )
    @PostMapping("/login")
    public ResponseEntity<JwtDTO> login(@RequestBody LoginDTO dto) {
        return ResponseEntity
                .ok(authService.login(dto));
    }
}