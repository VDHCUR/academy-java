package com.todolist.todolist.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "tasks")
public class Task {
    @Id
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    private UUID id;

    @NotBlank(message = "Name should not be blank")
    @Column(nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "list_id", referencedColumnName = "id")
    @JsonBackReference
    private TodoList todoList;

    @Column(nullable = false)
    private String description;

    @Min(value = 1, message = "Minimum value for urgency is 1")
    @Max(value = 5, message = "Minimum value for urgency is 5")
    @NotNull(message = "Urgency should be a number from 1 to 5")
    @Column(nullable = false)
    private int urgency;

    @NotNull
    @Column(nullable = false)
    private int isDone;

    @CreationTimestamp
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss Z")
    @Column(name = "created_at", nullable = false, updatable = false)
    private Timestamp createdAt;

    @UpdateTimestamp
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss Z")
    @Column(name = "updated_at", nullable = false)
    private Date updatedAt;

    /**
     * Parameterless constructor for Hibernate ORM
     */
    public Task() {}

    public Task(@JsonProperty("id") UUID id,
                @JsonProperty("name") @NotBlank String name,
                @JsonProperty("description") String description,
                @JsonProperty("urgency") int urgency){
        this.id = id;
        this.name = name;
        if (Objects.isNull(description)){
            description = "";
        }
        this.description = description;
        this.urgency = urgency;
        isDone = 0;
    }

    public void markAsDone(){
        if (isDone == 0){
            isDone = 1;
        } else {
            isDone = 0;
        }
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public TodoList getTodoList() {
        return todoList;
    }

    public String getDescription() {
        return description;
    }

    public int getUrgency() {
        return urgency;
    }

    public int getIsDone() {
        return isDone;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setUrgency(int urgency) {
        this.urgency = urgency;
    }

    public void setIsDone(int isDone) {
        this.isDone = isDone;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public void setTodoList(TodoList todoList) {
        this.todoList = todoList;
    }
}