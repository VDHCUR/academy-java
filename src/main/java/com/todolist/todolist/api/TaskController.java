package com.todolist.todolist.api;

import com.todolist.todolist.entity.Task;
import com.todolist.todolist.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;
import java.util.UUID;

/**
 * Контроллер для действий с делами
 */
@RestController
public class TaskController {
    private final TaskService taskService;

    /**
     * Конструктор контроллера дел. Подключает сервис по обработке дел.
     * @param taskService сервис по работе с делами.
     */
    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    /**
     * Передаёт запрос на сохранение дела в список сервису
     * @param listId идентификатор списка дел, к которому привязано дело
     * @param task данные о деле
     * @return созданное дело
     */
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/lists/{listId}")
    public Task createTask(@PathVariable(value = "listId") UUID listId, @RequestBody @Valid Task task) {
        return taskService.createTask(listId, task);
    }

    /**
     * Запрашивает сервис пометить дело с данным идентификатором как выполненное
     * @param id идентификатор дела
     * @return Выполненное дело
     */
    @PostMapping(value = "/tasks/{id}/mark-done")
    public Task markTaskAsDone(@PathVariable(value = "id") UUID id) {
        return taskService.markTaskAsDone(id);
    }

    /**
     * Передаёт сервису запрос на изменение дела
     * @param id идентификатор дела
     * @param updateData Пары "Свойство: Значение" для обновления дела
     * @return Изменённое дело
     */
    @PatchMapping(value = "/tasks/{id}")
    public Task changeTask(@PathVariable(value = "id") UUID id, @RequestBody Map<String, Object> updateData){
        return taskService.changeTask(id, updateData); //taskService.changeTask(id);
    }

    /**
     * Передаёт сервису запрос на удаление дела
     * @param id идентификатор дела
     */
    @DeleteMapping(value = "/tasks/{id}")
    public void deleteTask(@PathVariable(value = "id") UUID id) {
        taskService.deleteTask(id);
    }
}
