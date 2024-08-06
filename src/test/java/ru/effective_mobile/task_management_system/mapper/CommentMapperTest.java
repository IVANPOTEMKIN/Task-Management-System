package ru.effective_mobile.task_management_system.mapper;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.effective_mobile.task_management_system.model.Comment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static ru.effective_mobile.task_management_system.mapper.CommentMapper.INSTANCE;
import static ru.effective_mobile.task_management_system.utils.UtilsMapper.*;

class CommentMapperTest {

    @Test
    @DisplayName(value = "Маппинг - успешно")
    void testCommentToCommentDTO() {
        var expected = commentDTO;
        var actual = INSTANCE.commentToCommentDTO(comment);

        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName(value = "Маппинг - успешно")
    void testCreateOrUpdateCommentDTOToComment() {
        var expected = Comment.builder()
                .text("Текст комментария")
                .build();

        var actual = INSTANCE.createOrUpdateCommentDTOToComment(createOrUpdateCommentDTO);

        assertNotNull(actual);
        assertEquals(expected, actual);
    }
}