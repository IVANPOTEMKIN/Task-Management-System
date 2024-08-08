package ru.effective_mobile.task_management_system.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ru.effective_mobile.task_management_system.dto.tasks.CreateTaskDTO;
import ru.effective_mobile.task_management_system.dto.tasks.TaskDTO;
import ru.effective_mobile.task_management_system.model.Task;

import static ru.effective_mobile.task_management_system.mapper.utils.Utils.EXPRESSION_FOR_CONCAT_FULL_NAME_OF_AUTHOR_TASK;
import static ru.effective_mobile.task_management_system.mapper.utils.Utils.EXPRESSION_FOR_CONCAT_FULL_NAME_OF_PERFORMER_TASK;

@Mapper
public interface TaskMapper {

    TaskMapper INSTANCE = Mappers.getMapper(TaskMapper.class);

    @Mapping(expression = EXPRESSION_FOR_CONCAT_FULL_NAME_OF_AUTHOR_TASK, target = "author")
    @Mapping(source = "task.author.email", target = "authorEmail")
    @Mapping(expression = EXPRESSION_FOR_CONCAT_FULL_NAME_OF_PERFORMER_TASK, target = "performer")
    @Mapping(source = "task.performer.email", target = "performerEmail")
    TaskDTO taskToTaskDTO(Task task);

    Task createTaskDTOToTask(CreateTaskDTO createTaskDTO);
}