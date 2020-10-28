package com.todolist.todolist.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Представляет класс списков дел.
 * Имеет идентификатор, название, время создания и последней модификации
 */
@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "todolists")
public class TodoList {

    @Id
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    private UUID id;

    @NotBlank(message = "Field 'name' should not be blank")
    @Column(nullable = false)
    private String name;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "list_id", referencedColumnName = "id")
    private List<Task> tasks;

    @CreationTimestamp
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss Z")
    @Column(name = "created_at", nullable = false, updatable = false)
    private Timestamp createdAt;

    @UpdateTimestamp
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss Z")
    @Column(name = "updated_at", nullable = false)
    private Timestamp updatedAt;

    public TodoList (String name){
        this.name = name;
        this.tasks = new ArrayList<>();
    }

    /**
     * Конструктор класса списка дел. Создаёт объект с указанным названием и идентификатором.
     * Если идентификатор не указан, генерируется новый
     * @param id идентификатор списка дел
     * @param name название списка дел
     */
    public TodoList(@JsonProperty("id") UUID id, @JsonProperty("name") @NotBlank String name) {
        this.id = id;
        this.name = name;
        this.tasks = new ArrayList<>();
    }
}
