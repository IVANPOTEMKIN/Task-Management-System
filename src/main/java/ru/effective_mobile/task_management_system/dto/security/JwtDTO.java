package ru.effective_mobile.task_management_system.dto.security;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Ответ c токеном доступа")
public class JwtDTO {

    @Schema(description = "Токен доступа", example = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJpdmFuQGdtYWlsLmNvbSIsImlhdCI6MTcyMjg4ODQ1MiwiZXhwIjoxNzIzMDMyNDUyfQ.r2ldS_Ba59gJE50Cbq9Vi25LT3mGLRV_UrGkx92pVKg")
    private String token;
}