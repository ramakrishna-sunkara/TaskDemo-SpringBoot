package com.ram.demo.controller;

import com.ram.demo.payload.TaskDto;
import com.ram.demo.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @PostMapping("/{userid}/tasks")
    public ResponseEntity<TaskDto> saveTask(
            @PathVariable(name = "userid") long userid,
            @RequestBody TaskDto taskDto
    ) {
        return new ResponseEntity<>(taskService.saveTask(userid, taskDto), HttpStatus.CREATED);
    }

    @GetMapping("/{userid}/tasks")
    public ResponseEntity<List<TaskDto>> getAllTasks(
            @PathVariable(name = "userid") long userid
    ) {
        return new ResponseEntity<>(taskService.getAllTasks(userid), HttpStatus.OK);
    }

    @PreAuthorize(value = "ROLE_ADMIN")
    @GetMapping("/{user_id}/tasks/{task_id}")
    public ResponseEntity<TaskDto> getAllTasks(
            @PathVariable(name = "user_id") long userId,
            @PathVariable(name = "task_id") long taskId
    ) {
        return new ResponseEntity<>(taskService.getTask(userId, taskId), HttpStatus.OK);
    }

    @DeleteMapping("/{user_id}/tasks/{task_id}")
    public ResponseEntity<String> deleteTask(
            @PathVariable(name = "user_id") long userId,
            @PathVariable(name = "task_id") long taskId
    ) {
        taskService.deleteTask(userId, taskId);
        return new ResponseEntity<>("Task deleted successfully!!", HttpStatus.OK);
    }
}

