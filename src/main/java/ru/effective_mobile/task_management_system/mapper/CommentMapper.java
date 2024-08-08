package ru.effective_mobile.task_management_system.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ru.effective_mobile.task_management_system.dto.comments.CommentDTO;
import ru.effective_mobile.task_management_system.dto.comments.CreateOrUpdateCommentDTO;
import ru.effective_mobile.task_management_system.model.Comment;

import static ru.effective_mobile.task_management_system.mapper.utils.Utils.EXPRESSION_FOR_CONCAT_FULL_NAME_OF_AUTHOR_COMMENT;

@Mapper
public interface CommentMapper {

    CommentMapper INSTANCE = Mappers.getMapper(CommentMapper.class);

    @Mapping(expression = EXPRESSION_FOR_CONCAT_FULL_NAME_OF_AUTHOR_COMMENT, target = "author")
    CommentDTO commentToCommentDTO(Comment comment);

    Comment createOrUpdateCommentDTOToComment(CreateOrUpdateCommentDTO createOrUpdateCommentDTO);
}