package com.todolist.todolist.dto;

import com.todolist.todolist.errors.InvalidRequestTypesException;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.Nullable;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
public class TaskPatchDTO extends TaskDTORequest implements Serializable  {
    @Nullable
    private String name;
    @Nullable
    private String description;
    @Nullable
    private String urgency;
    @Nullable
    private String isDone;

    public TaskPatchDTO(@Nullable String name, @Nullable String description, String urgency, String isDone) {
        if (name != null && name.isBlank()){
            throw new InvalidRequestTypesException(
                    new HashMap<>(Map.of("name", "Field 'name' should not be empty"))
            );
        } else {
            this.name = name;
        }
        this.description = description;
        this.urgency = checkUrgency(urgency);
        this.isDone = checkIsDone(isDone);
    }

    public Integer getUrgency() {
        if (urgency == null) return null;
        return Integer.parseInt(urgency);
    }

    public Integer getIsDone(){
        if (isDone == null) return null;
        return Integer.parseInt(isDone);
    }

    public void setName(String name){
        if (name != null && name.isBlank()){
            throw new InvalidRequestTypesException(
                    new HashMap<>(Map.of("name", "Field 'name' should not be empty"))
            );
        } else if (name != null){
            this.name = name.trim();
        } else {
            this.name = null;
        }
    }

    public boolean isAllPropertiesNull(){
        return this.name == null && this.urgency == null && this.description == null && this.isDone == null;
    }
}
