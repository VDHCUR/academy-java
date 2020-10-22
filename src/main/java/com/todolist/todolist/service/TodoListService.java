package com.todolist.todolist.service;

import com.todolist.todolist.dto.ListsGetAllRequestDTO;
import com.todolist.todolist.dto.TodoListDTO;
import com.todolist.todolist.entity.TodoList;
import com.todolist.todolist.errors.*;
import com.todolist.todolist.repository.TodoListRepository;
import com.todolist.todolist.specification.TodoListSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Сервис для действий со списками дел
 */
@Service
public class TodoListService {

    private final TodoListRepository todoListRepository;

    /**
     * Конструктор класса. Подключает репозиторий
     * @param todoListRepository репозиторий списков
     */
    @Autowired
    public TodoListService(TodoListRepository todoListRepository){
        this.todoListRepository = todoListRepository;
    }

    /**
     * Получает из репозитория списки дел с применением дополнительных параметров
     * @param additionalData дополнительные параметры о номере страницы, сортировке
     * @return страницу со списками дел, обработанную в соответсвии с доп. параметрами
     */
    public Page<TodoListDTO> getAllLists(ListsGetAllRequestDTO additionalData){
        Pageable pageable = PageRequest.of(
                additionalData.getPage(),
                10,
                additionalData.getSortOrder(),
                additionalData.getSortBy());

        Page<TodoList> page = todoListRepository.findAll(
                new TodoListSpecification(
                        additionalData.getFilterBy(),
                        additionalData.getFilter()),
                pageable);
        int totalElements = (int) page.getTotalElements();
        return new PageImpl<>(
                page.stream()
                        .map(TodoListDTO::new)
                        .collect(Collectors.toList()), pageable, totalElements);
    }

    /**
     * Получает из репозитория список дел с указанным идентификатором
     * @param id идентификатор списка дел
     * @return Список дел с указанным идентификатором или вызывает ошибку "Не найдено"
     */
    public TodoListDTO getListById(UUID id){
        TodoList todoList = todoListRepository.findById(id).orElseThrow(() -> new NotFoundException("List", id));
        return new TodoListDTO(todoList);
    }


    /**
     * Сохраняет список дел в репозиторий
     * @param todoList данные о списке для сохранения
     * @return Созданный список дел
     */
    public TodoListDTO createList(TodoList todoList){
        return new TodoListDTO(todoListRepository.save(todoList));
    }

    public TodoListDTO updateListNameById(Map<String, String> updateData, UUID id){
        if (updateData.isEmpty()){
            throw new EmptyRequestBodyException();
        }
        return todoListRepository.findById(id).map(x -> {
            if (!updateData.containsKey("name")){
                throw new UnsupportedFieldPatchException(updateData.keySet());
            }
            String name = updateData.get("name");
            if (name != null && !name.trim().isEmpty()) {
                x.setName(name);
                return new TodoListDTO(todoListRepository.save(x));
            } else {
                throw new ListIncorrectNameException();
            }
        }).orElseThrow(() -> new NotFoundException("List", id));
    }

    /**
     * Удаляет список дел с указанным идентификатором
     * @param id идентификатор списка дел
     */
    public void deleteListById(UUID id){
        if (todoListRepository.existsById(id)){
            todoListRepository.deleteById(id);
        } else {
            throw new NotFoundException("List", id);
        }
    }
}
