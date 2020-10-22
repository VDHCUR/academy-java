package com.todolist.todolist.specification;

import com.todolist.todolist.entity.TodoList;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

@Getter
@Setter
public class TodoListSpecification implements Specification<TodoList> {
    private String property;
    private String filter;

    public TodoListSpecification(String property, String filter) {
        this.property = property;
        this.filter = filter;
    }

    @Override
    public Predicate toPredicate(Root<TodoList> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        if (property == null || filter.isEmpty()){
            return criteriaBuilder.isTrue(criteriaBuilder.literal(true)); // Если true, то фильтрации не будет
        }
        if (property.equals("createdAt") || property.equals("updatedAt")){
            try {
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
                Date dBegin = dateFormat.parse(filter + " 00:00:00");
                Date dEnd = dateFormat.parse(filter + " 23:59:59");
                return criteriaBuilder.between(root.get(property), dBegin, dEnd);
            } catch (ParseException e) {
                System.out.println("ParseException");
                return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
            }

        }
        return criteriaBuilder.like(criteriaBuilder.lower(root.get(property)), "%" + filter.toLowerCase() + "%");
    }
}
