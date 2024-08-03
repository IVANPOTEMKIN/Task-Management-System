package ru.effective_mobile.task_management_system.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ru.effective_mobile.task_management_system.dto.comments.CommentDTO;
import ru.effective_mobile.task_management_system.dto.comments.CreateOrUpdateCommentDTO;
import ru.effective_mobile.task_management_system.model.Comment;

@Mapper
public interface CommentMapper {

    CommentMapper INSTANCE = Mappers.getMapper(CommentMapper.class);

    @Mapping(source = "comment.author.username", target = "author")
    CommentDTO commentToCommentDTO(Comment comment);

    Comment createOrUpdateCommentDTOToComment(CreateOrUpdateCommentDTO createOrUpdateCommentDTO);
}