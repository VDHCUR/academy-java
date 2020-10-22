package com.todolist.todolist.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.todolist.todolist.entity.TodoList;
import com.todolist.todolist.specification.TodoListSpecification;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class ListsGetAllRequestDTO {
    private int page;
    private String sortBy;
    private Sort.Direction sortOrder;
    private String filter;
    private String filterBy;

    public ListsGetAllRequestDTO(){
        this(0, "createdAt", "ASC", "", "");
    }

    public ListsGetAllRequestDTO(int page, String sortBy, String sortOrder, String filterBy, String filter) {
        Set<String> listProperties = getListProperties();
        this.page = page;
        this.sortBy = sortBy;
        if(!listProperties.contains(sortBy)){
            sortBy = "createdAt";
        }

        try {
            this.sortOrder = Sort.Direction.fromString(sortOrder);
        } catch (IllegalArgumentException ex){
            this.sortOrder = Sort.Direction.ASC;
        }

        if (listProperties.contains(filterBy)){
            this.filter = filter;
            this.filterBy = filterBy;
        } else {
            this.filter = "";
            this.filterBy = "";
        }
    }

    private Set<String> getListProperties(){
        Set<String> listProperties = new HashSet<>();
        for (Field f : TodoList.class.getDeclaredFields()){
            listProperties.add(f.getName());
        }
        listProperties.remove("id");
        return listProperties;
    }
}
