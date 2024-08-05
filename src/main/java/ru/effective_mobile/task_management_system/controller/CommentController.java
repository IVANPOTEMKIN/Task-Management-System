package ru.effective_mobile.task_management_system.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.effective_mobile.task_management_system.dto.comments.CommentDTO;
import ru.effective_mobile.task_management_system.dto.comments.CreateOrUpdateCommentDTO;
import ru.effective_mobile.task_management_system.service.CommentService;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static ru.effective_mobile.task_management_system.controller.utils.Utils.*;

@RestController
@RequestMapping("/task/comment")
@RequiredArgsConstructor
@Tag(name = "Комментарии")
public class CommentController {

    private final CommentService commentService;

    @Operation(
            summary = "Создание нового комментария",
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
    @SecurityRequirement(name = "JWT")
    @PostMapping("/add/task/{id}")
    public ResponseEntity<?> addComment(@PathVariable Long id,
                                        @RequestBody CreateOrUpdateCommentDTO dto) {

        commentService.addComment(id, dto);
        return ResponseEntity.status(CREATED).build();
    }

    @Operation(
            summary = "Получение комментария по ID",
            responses = {
                    @ApiResponse(
                            responseCode = CODE_200,
                            description = DESCRIPTION_CODE_200,
                            content = @Content(
                                    mediaType = APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = CommentDTO.class)
                            )
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
    @SecurityRequirement(name = "JWT")
    @GetMapping("/get/{id}")
    public ResponseEntity<CommentDTO> getCommentById(@PathVariable Long id) {
        return ResponseEntity.ok(commentService.getCommentById(id));
    }

    @Operation(
            summary = "Получение всех комментариев по ID задачи",
            responses = {
                    @ApiResponse(
                            responseCode = CODE_200,
                            description = DESCRIPTION_CODE_200,
                            content = @Content(
                                    mediaType = APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(
                                            schema = @Schema(implementation = CommentDTO.class)
                                    )
                            )
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
    @SecurityRequirement(name = "JWT")
    @GetMapping("/getAll/task/{id}")
    public ResponseEntity<List<CommentDTO>> getAllCommentsByTaskId(@PathVariable Long id,
                                                                   @RequestParam Integer offset,
                                                                   @RequestParam Integer limit) {

        return ResponseEntity.ok(commentService.getAllCommentsByTaskId(id, offset, limit));
    }

    @Operation(
            summary = "Получение всех комментариев по ID автора",
            responses = {
                    @ApiResponse(
                            responseCode = CODE_200,
                            description = DESCRIPTION_CODE_200,
                            content = @Content(
                                    mediaType = APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(
                                            schema = @Schema(implementation = CommentDTO.class)
                                    )
                            )
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
    @SecurityRequirement(name = "JWT")
    @GetMapping("/getAll/author/{id}")
    public ResponseEntity<List<CommentDTO>> getAllCommentsByAuthorId(@PathVariable Long id,
                                                                     @RequestParam Integer offset,
                                                                     @RequestParam Integer limit) {

        return ResponseEntity.ok(commentService.getAllCommentsByAuthorId(id, offset, limit));
    }

    @Operation(
            summary = "Получение всех комментариев по ID задачи и ID автора",
            responses = {
                    @ApiResponse(
                            responseCode = CODE_200,
                            description = DESCRIPTION_CODE_200,
                            content = @Content(
                                    mediaType = APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(
                                            schema = @Schema(implementation = CommentDTO.class)
                                    )
                            )
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
    @SecurityRequirement(name = "JWT")
    @GetMapping("/getAll/task/{taskId}/author/{authorId}")
    public ResponseEntity<List<CommentDTO>> getAllCommentsByAuthorIdAndTaskId(@PathVariable Long taskId,
                                                                              @PathVariable Long authorId,
                                                                              @RequestParam Integer offset,
                                                                              @RequestParam Integer limit) {

        return ResponseEntity.ok(commentService.getAllCommentsByTaskIdAuthorId(taskId, authorId, offset, limit));
    }

    @Operation(
            summary = "Изменение текста комментария по ID",
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
                            responseCode = CODE_403,
                            description = DESCRIPTION_CODE_403,
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
    @SecurityRequirement(name = "JWT")
    @PatchMapping("/edit/{id}")
    public ResponseEntity<?> editTextCommentById(@PathVariable Long id,
                                                 @RequestBody CreateOrUpdateCommentDTO dto) {

        commentService.editTextComment(id, dto);
        return ResponseEntity.ok().build();
    }

    @Operation(
            summary = "Удаление комментария по ID",
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
                            responseCode = CODE_403,
                            description = DESCRIPTION_CODE_403,
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
    @SecurityRequirement(name = "JWT")
    @PatchMapping("/delete/{id}")
    public ResponseEntity<?> deleteCommentById(@PathVariable Long id) {

        commentService.deleteComment(id);
        return ResponseEntity.ok().build();
    }
}