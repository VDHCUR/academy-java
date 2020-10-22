package com.todolist.todolist.repository;

import com.todolist.todolist.entity.TodoList;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;

import java.util.Optional;
import java.util.UUID;

public interface TodoListRepository extends JpaRepositoryImplementation<TodoList, UUID> {

    /**
     * Возвращает страницу со списками дел
     * @param pageable данные для пагинации
     * @return страница, со списками дел
     */
    Page<TodoList> findAll(Pageable pageable);

    /**
     * Возвращает Optional со списком дел с указанным идентификатором или null
     * @param id идентификатор списка дел
     * @return Optional содержащий список дел или null
     */
    Optional<TodoList> findById (UUID id);

    /**
     * Возвращает страницу со списками дел, с применённым фильтром
     * @param filterSpec спецификация фильтрации
     * @param pageable данные для пагинации
     * @return страницу с результатами поиска
     */
    Page<TodoList> findAll(Specification<TodoList> filterSpec, Pageable pageable);
}
