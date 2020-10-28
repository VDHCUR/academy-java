package com.todolist.todolist.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.todolist.todolist.entity.Task;
import com.todolist.todolist.entity.TodoList;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

/**
 * Представляет класс данных о списке дел для возврата на запросы
 */
@Getter
@Setter
public class TodoListResponseDTO implements Serializable {
    private UUID id;
    private String name;
    private List<Task> tasks;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss Z")
    private Timestamp createdAt;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss Z")
    private Timestamp updatedAt;
    private int tasksDone;
    private int tasksNotDone;

    /**
     * Конструктор класса, создающий "транспортный объект" из отдельных свойств списка дел
     * @param id идентификатор списка дел
     * @param name название списка дел
     * @param tasks список дел
     * @param createdAt дата создания
     * @param updatedAt дата последнего изменения
     */
    public TodoListResponseDTO(UUID id, String name, List<Task> tasks, Timestamp createdAt, Timestamp updatedAt) {
        this.id = id;
        this.name = name;
        this.tasks = tasks;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;

        this.tasksDone = (int) tasks.stream().filter(task -> task.getIsDone() == 1).count();
        this.tasksNotDone = this.tasks.size() - tasksDone;
    }
    /**
     * Конструктор класса, преобразующий список дел в DTO
     * @param todoList список дел для преобразования
     */
    public TodoListResponseDTO(TodoList todoList){
        this.id = todoList.getId();
        this.name = todoList.getName();
        this.tasks = todoList.getTasks();
        this.createdAt = todoList.getCreatedAt();
        this.updatedAt = todoList.getUpdatedAt();

        this.tasksDone = (int) tasks.stream().filter(task -> task.getIsDone() == 1).count();
        this.tasksNotDone = this.tasks.size() - tasksDone;
    }
}
