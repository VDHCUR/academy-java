package com.todolist.todolist.service;

import com.todolist.todolist.dto.TaskCreateDTO;
import com.todolist.todolist.dto.TaskPatchDTO;
import com.todolist.todolist.dto.TaskPatchDTOMapper;
import com.todolist.todolist.entity.Task;
import com.todolist.todolist.entity.TodoList;
import com.todolist.todolist.errors.NotFoundException;
import com.todolist.todolist.repository.TaskRepository;
import com.todolist.todolist.repository.TodoListRepository;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Представляет сервис по работе с делами
 */
@Service
public class TaskService {
    private final TaskRepository taskRepository;
    private final TodoListRepository todoListRepository;

    /**
     * Конструктор класса сервиса дел. Подключает репозитории дел и списков дел
     * @param taskRepository репозиторий дел
     * @param todoListRepository репозиторий списков дел
     */
    @Autowired
    public TaskService(TaskRepository taskRepository, TodoListRepository todoListRepository) {
        this.taskRepository = taskRepository;
        this.todoListRepository = todoListRepository;
    }

    /**
     * Сохраняет дело в репозиторий
     * @param listId идентификатор списка дел, к которому привязано дело
     * @param createDTO данные о деле
     * @return Созданное дело
     */
    public Task createTask(UUID listId, TaskCreateDTO createDTO){
        TodoList todolist = todoListRepository.findById(listId).orElseThrow(() -> new NotFoundException("List", listId));
        Task task = new Task(
                createDTO.getName(),
                createDTO.getDescription(),
                createDTO.getUrgency()
        );
        task.setTodoList(todolist);
        return taskRepository.save(task);
    }

    /**
     * Помечает дело с данным идентификатором как выполненное и сохраняет в репозиторий или вызывает "Не найдено"
     * @param id - идентификатор дела
     * @return Изменённое дело
     */
    public Task markTaskAsDone(UUID id){
        return taskRepository.findById(id).map(task -> {
            task.markAsDone();
            return taskRepository.save(task);
        }).orElseThrow(() -> new NotFoundException("Task", id));
    }

    /**
     * Удаляет дело с данным репозиторием или вызывает "Не найдено"
     * @param id - идентификатор дела
     */
    public void deleteTask(UUID id){
        if (taskRepository.existsById(id)){
            taskRepository.deleteById(id);
        } else {
            throw new NotFoundException("Task", id);
        }
    }

    /**
     * Изменяет дело с указанным идентификатором
     * @param id - идентификатор дела
     * @param updateData - Пары "Свойство: Значение" для обновления дела
     * @return Изменённое дело
     */
//    public Task changeTask(UUID id, Map<String, Object> updateData){
//        if (updateData.isEmpty()){
//            throw new EmptyRequestBodyException();
//        }
//
//        // получаем поля дела, которые можно обновлять
//        Set<String> taskProperties = new HashSet<>();
//        for (Field field : Task.class.getDeclaredFields()){
//            taskProperties.add(field.getName());
//        }
//
//        taskProperties.removeAll(Arrays.asList("id", "todoList", "createdAt", "updatedAt"));
//
//        if (taskProperties.stream().noneMatch(updateData::containsKey)){
//            Set<String> unsupportedFields = updateData.keySet();
//            unsupportedFields.removeAll(taskProperties);
//            throw new UnsupportedFieldPatchException(unsupportedFields);
//        }
//
//        // выбираем только те поля, которые нужны
//        Set<String> propsToUpdate = new HashSet<>(updateData.keySet())
//                .stream()
//                .filter(taskProperties::contains)
//                .collect(Collectors.toSet());
//
//        return taskRepository.findById(id).map(x -> {
//            HashMap<String, String> errors = new HashMap<>();
//            propsToUpdate.forEach(prop -> {
//                try {
//                    // получение сеттера для свойства и, если типы совпадают, вызов сеттера на конкретной таске
//                    // если не совпадают, добавляем к ошибкам
//                    Class type = Task.class.getDeclaredField(prop).getType();
//                    if (updateData.get(prop) == null){
//                        errors.put(prop, type.getSimpleName().toLowerCase());
//                    } else {
//                        String setterName = "set" + prop.substring(0, 1).toUpperCase() + prop.substring(1);
//                        Class typeOfUpdateProp = updateData.get(prop).getClass();
//                        if (typeOfUpdateProp.equals(Integer.class)){
//                            typeOfUpdateProp = int.class;
//                        }
//                        Method setter = Task.class.getMethod(setterName, type);
//                        if (!type.equals(typeOfUpdateProp)){
//                            errors.put(prop, type.getSimpleName().toLowerCase());
//                        } else {
//                            setter.invoke(x, updateData.get(prop));
//                        }
//                    }
//                } catch (NoSuchMethodException | NoSuchFieldException | IllegalAccessException | InvocationTargetException e){
//                    throw new CustomException(e.getMessage());
//                }
//            });
//            if (!errors.isEmpty()){
//                throw new InvalidRequestTypesException(errors);
//            }
//            return taskRepository.save(x);
//        }).orElseThrow(() -> new NotFoundException("Task", id));
//    }

    public Task changeTask(UUID id, TaskPatchDTO updateData){
        TaskPatchDTOMapper mapper = Mappers.getMapper(TaskPatchDTOMapper.class);
        return taskRepository.findById(id).map(x ->{
            mapper.updateTaskFromDto(updateData, x);
            return taskRepository.save(x);
        }).orElseThrow(() -> new NotFoundException("Task", id));
    }
}
