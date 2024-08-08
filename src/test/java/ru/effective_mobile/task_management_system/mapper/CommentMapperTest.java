package ru.effective_mobile.task_management_system.mapper;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.effective_mobile.task_management_system.model.Comment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static ru.effective_mobile.task_management_system.mapper.CommentMapper.INSTANCE;
import static ru.effective_mobile.task_management_system.utils.Constants.TEXT;
import static ru.effective_mobile.task_management_system.utils.Naming.MAPPING;
import static ru.effective_mobile.task_management_system.utils.UtilsMapper.*;

class CommentMapperTest {

    @Test
    @DisplayName(MAPPING)
    void test_commentToCommentDTO() {
        var expected = commentDTO;
        var actual = INSTANCE.commentToCommentDTO(comment);

        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName(MAPPING)
    void test_createOrUpdateCommentDTOToComment() {
        var expected = Comment.builder()
                .text(TEXT)
                .build();

        var actual = INSTANCE.createOrUpdateCommentDTOToComment(createOrUpdateCommentDTO);

        assertNotNull(actual);
        assertEquals(expected, actual);
    }
}