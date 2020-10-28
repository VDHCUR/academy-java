package com.todolist.todolist.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
public class TaskCreateDTO extends TaskDTORequest implements Serializable {
    @NotBlank
    private String name;
    @Nullable
    private String description;
    @Nullable
    private String urgency;

    public TaskCreateDTO(@NotBlank String name, String description, String urgency) {
        this.name = name.trim();
        this.description = checkDescription(description);
        this.urgency = checkUrgency(urgency);
    }

    public void setName(String name) {
        this.name = name.trim();
    }

    public void setDescription(String description) {
        this.description = Objects.requireNonNullElse(description, "");
    }

    public void setUrgency(String urgency) {
        this.urgency = checkUrgency(urgency);
    }

    public int getUrgency() {
        return Integer.parseInt(urgency);
    }

    private String checkDescription(String description){
        return Objects.requireNonNullElse(description, "");
    }
}
