package com.todolist.todolist.dto;

import com.todolist.todolist.entity.TodoList;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Sort;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
public class ListsGetAllRequestDTO implements Serializable {
    private int page;
    private String sortBy;
    private Sort.Direction sortOrder;
    private String filter;
    private String filterBy;

    public ListsGetAllRequestDTO(){
        this.page = 0;
        this.sortBy = "createdAt";
        this.sortOrder = Sort.Direction.DESC;
        this.filterBy = "";
        this.filter = "";
    }

    private List<String> getListProperties(){
        List<String> listProperties = new ArrayList<>();
        for (Field f : TodoList.class.getDeclaredFields()){
            listProperties.add(f.getName());
        }
        listProperties.remove("id");
        return listProperties;
    }

    private String getValidFilterBy(String filterByToCheck){
        if (getListProperties().contains(filterByToCheck)){
            return filterByToCheck;
        } else {
            return "";
        }
    }

    private String getValidSortBy(String strToCheck){
        if(!getListProperties().contains(strToCheck)){
            return "createdAt";
        } else {
            return strToCheck;
        }
    }

    private Sort.Direction getValidSortOrder(String strToCheck){
        try {
            return Sort.Direction.fromString(strToCheck);
        } catch (IllegalArgumentException ex){
            return switch (this.sortBy) {
                case "createdAt", "updatedAt" -> Sort.Direction.DESC;
                default -> Sort.Direction.ASC;
            };
        }
    }

    public void setPage(String pageNum){
        try {
            this.page = Integer.parseInt(pageNum);
        }
        catch (NumberFormatException ex){
            this.page = 0;
        }
    }

    public void setSortBy(String sortBy) {
        this.sortBy = getValidSortBy(sortBy);
    }

    public void setSortOrder(String sortOrder) {
        this.sortOrder = getValidSortOrder(sortOrder);
    }

    public void setFilterBy(String filterBy){
        this.filterBy = getValidFilterBy(filterBy);
    }

    public void setFilter(String filter){
        if (this.filterBy.isEmpty()){
            this.filter = "";
        } else {
            this.filter = filter;
        }
    }
}
