package com.todolist.todolist.dto;

import com.todolist.todolist.errors.InvalidRequestTypesException;

import java.util.HashMap;
import java.util.Map;

public abstract class TaskDTORequest {
    protected String checkUrgency(String urgency){
        try {
            int intCheck = Integer.parseInt(urgency);
            if (intCheck > 5){
                intCheck = 5;
            } else if(intCheck < 1){
                intCheck = 1;
            }
            return Integer.toString(intCheck);
        } catch (NumberFormatException ex){
            throw new InvalidRequestTypesException(
                    new HashMap<>(Map.of("urgency", "int"))
            );
        }
    }

    protected String checkIsDone(String isDone){
        try {
            int intCheck = Integer.parseInt(isDone);
            if (intCheck != 0) {
                intCheck = 1;
            }
            return Integer.toString(intCheck);
        } catch (NumberFormatException ex){
            throw new InvalidRequestTypesException(
                    new HashMap<>(Map.of("isDone", "int"))
            );
        }
    }
}
