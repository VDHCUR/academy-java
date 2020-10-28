package com.todolist.todolist.service;

import com.todolist.todolist.dto.ListsGetAllRequestDTO;
import com.todolist.todolist.dto.TodoListRequestDTO;
import com.todolist.todolist.dto.TodoListResponseDTO;
import com.todolist.todolist.entity.Task;
import com.todolist.todolist.entity.TodoList;
import com.todolist.todolist.errors.*;
import com.todolist.todolist.repository.TodoListRepository;
import com.todolist.todolist.specification.TodoListSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

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
    public Page<TodoListResponseDTO> getAllLists(ListsGetAllRequestDTO additionalData){
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
                        .map(TodoListResponseDTO::new)
                        .collect(Collectors.toList()), pageable, totalElements);
    }

    /**
     * Получает из репозитория список дел с указанным идентификатором
     * @param id идентификатор списка дел
     * @return Список дел с указанным идентификатором или вызывает ошибку "Не найдено"
     */
    public TodoListResponseDTO getListById(UUID id){
        TodoList todoList = todoListRepository.findById(id).orElseThrow(() -> new NotFoundException("List", id));
        return new TodoListResponseDTO(todoList);
    }


    /**
     * Сохраняет список дел в репозиторий
     * @param todoList данные о списке для сохранения
     * @return Созданный список дел
     */
    public TodoListResponseDTO createList(TodoListRequestDTO todoList){

        return new TodoListResponseDTO(todoListRepository.save(new TodoList(
                todoList.getName()
        )));
    }

    public TodoListResponseDTO updateListNameById(TodoListRequestDTO updateData, UUID id){
        if (updateData == null){
            throw new EmptyRequestBodyException();
        }
        return todoListRepository.findById(id).map(x -> {
            x.setName(updateData.getName());
            return new TodoListResponseDTO(todoListRepository.save(x));
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
