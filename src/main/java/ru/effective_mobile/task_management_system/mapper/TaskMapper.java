package ru.effective_mobile.task_management_system.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.effective_mobile.task_management_system.dto.tasks.CreateOrUpdateTaskByAuthorDTO;
import ru.effective_mobile.task_management_system.dto.tasks.TaskDTO;
import ru.effective_mobile.task_management_system.dto.tasks.UpdateTaskByPerformerDTO;
import ru.effective_mobile.task_management_system.model.Task;

@Mapper
public interface TaskMapper {

    @Mapping(source = "task.author.username", target = "author")
    @Mapping(source = "task.author.email", target = "authorEmail")
    @Mapping(source = "task.performer.username", target = "performer")
    @Mapping(source = "task.performer.email", target = "performerEmail")
    TaskDTO taskToTaskDTO(Task task);

    @Mapping(source = "createOrUpdateTaskByAuthor.performer", target = "performer.username")
    Task createOrUpdateTaskByAuthorToTask(CreateOrUpdateTaskByAuthorDTO createOrUpdateTaskByAuthor);

    Task updateTaskByPerformerToTask(UpdateTaskByPerformerDTO updateTaskByPerformer);
}