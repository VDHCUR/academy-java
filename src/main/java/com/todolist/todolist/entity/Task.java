package com.todolist.todolist.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

/**
 * Представляет класс дел.
 * Имеет поля идентификатор, название, описание, срочность, сделано или нет, дата создания и последней модификации
 */
@Entity
@Table(name = "tasks")
@Setter
@Getter
@NoArgsConstructor
public class Task{
    @Id
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "list_id", referencedColumnName = "id")
    @JsonBackReference
    @Setter
    private TodoList todoList;

    @NotBlank(message = "Name should not be blank")
    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @Min(value = 1, message = "Minimum value for urgency is 1")
    @Max(value = 5, message = "Minimum value for urgency is 5")
    @Column(nullable = false)
    private int urgency;

    @Column(nullable = false)
    private int isDone;

    @CreationTimestamp
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss Z")
    @Column(name = "created_at", nullable = false, updatable = false)
    private Timestamp createdAt;

    @UpdateTimestamp
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss Z")
    @Column(name = "updated_at", nullable = false)
    private Timestamp updatedAt;


    /**
     * Конструктор класса дел. Создаёт объект с указанным названием, описанием, идентификатором и срочностью.
     * Если идентификатор не указан, генерируется новый.
     * По умолчанию, дело не выполнено
     * @param id идентификатор дела
     * @param name название дела
     * @param description описание дела
     * @param urgency срочность дела от 1 до 5
     */
    public Task(@JsonProperty("id") UUID id,
                @JsonProperty("name") @NotBlank String name,
                @JsonProperty("description") String description,
                @JsonProperty("urgency") int urgency){
        this.id = id;
        this.name = name;
        if (Objects.isNull(description)) {
            description = "";
        }
        this.description = description;
        if (urgency > 5){
            urgency = 5;
        } else if(urgency < 1){
            urgency = 1;
        }
        this.urgency = urgency;
        isDone = 0;
    }

    /**
     * Помечает дело как выполненое
     */
    public void markAsDone(){
        if (isDone == 0) {
            isDone = 1;
        } else {
            isDone = 0;
        }
    }

    /**
     * Присваевает срочность делу.
     * @param urgency срочность от 1 до 5. Если параметр меньше или больше ограничения, то срочность будет равна 1 или 5 соответственно
     */
    public void setUrgency(int urgency){
        if (urgency > 5){
            urgency = 5;
        } else if(urgency < 1){
            urgency = 1;
        }
        this.urgency = urgency;
    }

    /**
     * Присваивает выполенность делу
     * @param isDone число, представляющее выполенность. Если 0 - то дело назначается не выполненным, иначе - выполнено.
     */
    public void setIsDone(int isDone) {
        if (isDone == 0) {
            this.isDone = 0;
        } else {
            this.isDone = 1;
        }
    }
}