package ru.effective_mobile.task_management_system.mapper;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.effective_mobile.task_management_system.model.Task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static ru.effective_mobile.task_management_system.enums.PriorityTask.MEDIUM;
import static ru.effective_mobile.task_management_system.enums.StatusTask.IS_PENDING;
import static ru.effective_mobile.task_management_system.mapper.TaskMapper.INSTANCE;
import static ru.effective_mobile.task_management_system.utils.Constants.DESCRIPTION;
import static ru.effective_mobile.task_management_system.utils.Constants.HEADER;
import static ru.effective_mobile.task_management_system.utils.Naming.MAPPING;
import static ru.effective_mobile.task_management_system.utils.UtilsMapper.*;

class TaskMapperTest {

    @Test
    @DisplayName(MAPPING)
    void testTaskToTaskDTO() {
        var expected = taskDTO;
        var actual = INSTANCE.taskToTaskDTO(task);

        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName(MAPPING)
    void testCreateTaskDTOToTask() {
        var expected = Task.builder()
                .header(HEADER)
                .description(DESCRIPTION)
                .status(IS_PENDING)
                .priority(MEDIUM)
                .build();

        var actual = INSTANCE.createTaskDTOToTask(createTaskDTO);

        assertNotNull(actual);
        assertEquals(expected, actual);
    }
}