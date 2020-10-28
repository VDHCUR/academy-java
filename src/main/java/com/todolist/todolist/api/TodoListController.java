package com.todolist.todolist.api;

import com.todolist.todolist.dto.ListsGetAllRequestDTO;
import com.todolist.todolist.dto.TodoListRequestDTO;
import com.todolist.todolist.dto.TodoListResponseDTO;
import com.todolist.todolist.service.TodoListService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.UUID;

/**
 * Контроллер для действий со списками дел
 */

@RestController
@RequestMapping("/lists")
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
    @ApiOperation("Get todolists paginated, sorted, filtered (if specified)")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Todolists successfully received, sorted and filtered if were specified"),
    })
    @PostMapping
    public Page<TodoListResponseDTO> getAllLists(@RequestBody @Nullable ListsGetAllRequestDTO additionalData){
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
    @ApiOperation("Get todolists with given id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Todolist successfully received"),
            @ApiResponse(code = 404, message = "List was not found")
    })
    @GetMapping(path = "{id}")
    public TodoListResponseDTO getListById(@PathVariable("id") UUID id){
        return todoListService.getListById(id);
    }

    /**
     * Передаёт запрос сервису на сохранение дела в репозиторий
     * @param todoList данные о создаваемом списке, имя должно быть не пустым
     * @return Созданный список
     */
    @ApiOperation("Creates todolists")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Todolist successfully received"),
            @ApiResponse(code = 400, message = "Name should not be empty")
    })
    @ResponseStatus(value = HttpStatus.CREATED)
    @PostMapping("/create")
    public TodoListResponseDTO createList(@Valid @NotBlank @RequestBody TodoListRequestDTO todoList){
        return todoListService.createList(todoList);
    }

    /**
     * Передаёт запрос сервису на удаление списка дел из репозитория
     * @param id идентификатор списка дел
     */
    @ApiOperation("Deletes todolists with given id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Todolist successfully deleted"),
            @ApiResponse(code = 404, message = "List was not found")
    })
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
    @ApiOperation("Changes todolists with given id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Todolist successfully updated"),
            @ApiResponse(code = 400, message = "Name should not be empty"),
            @ApiResponse(code = 404, message = "List was not found")
    })
    @PatchMapping(path = "{id}")
    public TodoListResponseDTO updateListName(@Valid @RequestBody TodoListRequestDTO updateData, @PathVariable("id") UUID id){
        return todoListService.updateListNameById(updateData, id);
    }
}
