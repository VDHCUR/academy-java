package com.todolist.todolist.api;

import com.todolist.todolist.dto.ListsGetAllRequestDTO;
import com.todolist.todolist.dto.TodoListDTO;
import com.todolist.todolist.entity.TodoList;
import com.todolist.todolist.service.TodoListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Контроллер для действий со списками дел
 */
@RequestMapping("/lists")
@RestController
public class TodoListController {

    private final TodoListService todoListService;

    /**
     * Конструктор класса контроллера списка дел. Подключает сервис
     * @param todoListService сервис для работы со списками дел
     */
    @Autowired
    public TodoListController(TodoListService todoListService){
        this.todoListService = todoListService;
    }


    /**
     * Запрашивает у сервиса все списки
     * @param additionalData дополнительные параметры: сортировка, номер страницы, фильтрация
     * @return Страница со списками дел
     */
    @PostMapping
    public Page<TodoListDTO> getAllLists(@RequestBody @Nullable ListsGetAllRequestDTO additionalData){
        if (additionalData == null){
            additionalData = new ListsGetAllRequestDTO();
        }

        return todoListService.getAllLists(additionalData);
    }

    /**
     * Запрашивает у сервиса список с данным идентификатором
     * @param id идентификатор списка
     * @return Список дел с данным идентификатором
     */
    @GetMapping(path = "{id}")
    public TodoListDTO getListById(@PathVariable("id") UUID id){
        return todoListService.getListById(id);
    }

    /**
     * Передаёт запрос сервису на сохранение дела в репозиторий
     * @param todoList данные о создаваемом списке, имя должно быть не пустым
     * @return Созданный список
     */
    @ResponseStatus(value = HttpStatus.CREATED)
    @PostMapping("/create")
    public TodoListDTO createList(@Valid @NotBlank @RequestBody TodoList todoList){
        return todoListService.createList(todoList);
    }

    /**
     * Передаёт запрос сервису на удаление списка дел из репозитория
     * @param id идентификатор списка дел
     */
    @DeleteMapping(path = "{id}")
    public void deleteListById(@PathVariable("id") UUID id){
        todoListService.deleteListById(id);
    }

    /**
     * Передаёт сервису запрос на изменение списка
     * @param updateData Пары "Свойство: значение" для обновления списка дел
     * @param id идентификатор обновляемого списка дел
     * @return изменённый список дел
     */
    @PatchMapping(path = "{id}")
    public TodoListDTO updateListName(@RequestBody Map<String, String> updateData, @PathVariable("id") UUID id){
        return todoListService.updateListNameById(updateData, id);
    }
}
