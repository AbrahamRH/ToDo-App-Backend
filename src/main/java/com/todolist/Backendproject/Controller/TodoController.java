package com.todolist.Backendproject.Controller;

import java.util.List;
import java.time.LocalDate;
import com.todolist.Backendproject.Component.Todo;
import com.todolist.Backendproject.Component.Priority;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.todolist.Backendproject.Service.TodoService;

@RestController
@RequestMapping("/todos")
@CrossOrigin
public class TodoController {

  @Autowired
  private TodoService service;

  @PostMapping
  public void createTodo(@RequestBody Todo todo) {
    service.createTodo(todo);
  }

  @GetMapping(value="/all", produces="application/json" )
  public List<Todo> findAll(){
    return service.findAll();
  }

  @GetMapping("/{id}")
  public Todo findTodoById(@PathVariable long id) {
    return service.findById(id);
  }
  
}
