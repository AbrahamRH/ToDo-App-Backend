package com.todolist.Backendproject.Controller;

import java.util.List;
import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;

import com.todolist.Backendproject.Component.Todo;
import com.todolist.Backendproject.Service.TodoService;

@RestController
@CrossOrigin
public class TodoController {

  @Autowired
  private TodoService service;

  @GetMapping(value = "/todos", produces = "application/json")
  public ResponseEntity<List<Todo>> findAll() {
    if (service.isEmpty()) {
      return ResponseEntity.noContent().build();
    } else {
      return ResponseEntity.ok().body(service.findAll());
    }
  }

  @GetMapping("/todos/{id}")
  public ResponseEntity<Todo> findTodoById(@PathVariable long id) {
    Todo todo = service.findById(id);
    if (todo == null) {
      return ResponseEntity.notFound().build();
    } else {
      return ResponseEntity.ok().body(todo);
    }
  }

  @PostMapping(value = "/todos", consumes = "application/json", produces = "application/json")
  public ResponseEntity<Todo> createTodo(@RequestBody Todo todo) {
    service.createTodo(todo);
    URI url = URI.create("/todos/" + todo.getId());
    return ResponseEntity.created(url).body(todo);
  }

  @PutMapping(value = "/todos/{id}", consumes = "application/json")
  public ResponseEntity<Void> updateProduct(@PathVariable long id, @RequestBody Todo todo) {
    if(!service.update(id, todo.getName(), todo.getPriority(), todo.getDueDate())) {
      return ResponseEntity.notFound().build();
    } else {
      return ResponseEntity.ok().build();
    }

  }
}
