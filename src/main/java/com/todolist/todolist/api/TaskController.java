package com.todolist.todolist.api;

import com.sun.istack.NotNull;
import com.todolist.todolist.dto.TaskCreateDTO;
import com.todolist.todolist.dto.TaskPatchDTO;
import com.todolist.todolist.entity.Task;
import com.todolist.todolist.errors.EmptyRequestBodyException;
import com.todolist.todolist.service.TaskService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
    @ApiOperation("Creates new tasks in list with given ID")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Task created"),
            @ApiResponse(code = 404, message = "List not found"),
            @ApiResponse(code = 400, message = "Bad Request, validation failed")
    })
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/lists/{listId}")
    public Task createTask(@PathVariable(value = "listId") UUID listId, @RequestBody @Valid TaskCreateDTO task) {
        return taskService.createTask(listId, task);
    }

    /**
     * Запрашивает сервис пометить дело с данным идентификатором как выполненное
     * @param id идентификатор дела
     * @return Выполненное дело
     */
    @ApiOperation("Marks task with given id as done or not done, if were marked is done")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Task marked as done(not done)"),
            @ApiResponse(code = 404, message = "Task not found"),
    })
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
    @ApiOperation("Changes task with given id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Task successfully changed"),
            @ApiResponse(code = 404, message = "Task not found"),
            @ApiResponse(code = 400, message = "Bad Request, validation failed")
    })
    @PatchMapping(value = "/tasks/{id}")
    public Task changeTask(@PathVariable(value = "id") UUID id, @Valid @NotNull @RequestBody TaskPatchDTO updateData){
        if (updateData.isAllPropertiesNull()){
            throw new EmptyRequestBodyException();
        }
        return taskService.changeTask(id, updateData);
    }

    /**
     * Передаёт сервису запрос на удаление дела
     * @param id идентификатор дела
     */
    @ApiOperation("Deletes task with given id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Task successfully deleted"),
            @ApiResponse(code = 404, message = "Task not found"),
            @ApiResponse(code = 400, message = "Bad Request, validation failed")
    })
    @DeleteMapping(value = "/tasks/{id}")
    public void deleteTask(@PathVariable(value = "id") UUID id) {
        taskService.deleteTask(id);
    }
}
