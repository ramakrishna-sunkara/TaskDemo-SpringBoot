package com.ram.demo.serviceImpl;

import com.ram.demo.entity.Task;
import com.ram.demo.entity.Users;
import com.ram.demo.exception.ApiException;
import com.ram.demo.payload.TaskDto;
import com.ram.demo.repository.TaskRepository;
import com.ram.demo.repository.UserRepository;
import com.ram.demo.service.TaskService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public TaskDto saveTask(long userId, TaskDto taskDto) {
        Users users = userRepository.findById(userId).orElseThrow(
                () -> new RuntimeException(String.format("User with id %s not found", userId))
        );
        Task task = mapper.map(taskDto, Task.class);
        task.setUsers(users);
        Task savedTask = taskRepository.save(task);
        return mapper.map(savedTask, TaskDto.class);
    }

    @Override
    public List<TaskDto> getAllTasks(long userId) {
        userRepository.findById(userId).orElseThrow(
                () -> new RuntimeException(String.format("User with id %s not found", userId))
        );
        List<Task> tasks = taskRepository.findAllByUsersId(userId);
        return tasks.stream().map(
                task -> mapper.map(task, TaskDto.class)
        ).collect(Collectors.toList());
    }

    @Override
    public TaskDto getTask(long userId, long taskId) {
        Users users = userRepository.findById(userId).orElseThrow(
                () -> new RuntimeException(String.format("User with id %s not found", userId))
        );
        Task task = taskRepository.findById(taskId).orElseThrow(
                () -> new RuntimeException(String.format("Task with id %s not found", taskId))
        );
        if (users.getId() != task.getUsers().getId()) {
            throw new ApiException(String.format("Task id %d is not belong to user id %d", taskId, users.getId()));
        }
        return modelMapper.map(task, TaskDto.class);
    }

    @Override
    public void deleteTask(long userId, long taskId) {
        Users users = userRepository.findById(userId).orElseThrow(
                () -> new RuntimeException(String.format("User with id %s not found", userId))
        );
        Task task = taskRepository.findById(taskId).orElseThrow(
                () -> new RuntimeException(String.format("Task with id %s not found", taskId))
        );
        if (users.getId() != task.getUsers().getId()) {
            throw new ApiException(String.format("Task id %d is not belong to user id %d", taskId, users.getId()));
        }
        taskRepository.deleteById(taskId);
    }
}
