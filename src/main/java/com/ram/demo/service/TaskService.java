package com.ram.demo.service;

import com.ram.demo.entity.Task;
import com.ram.demo.payload.TaskDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public interface TaskService {
    public TaskDto saveTask(long userId, TaskDto taskDto);
    public List<TaskDto> getAllTasks(long userId);
    public TaskDto getTask(long userId, long taskId);
    public void deleteTask(long userId, long taskId);
}
