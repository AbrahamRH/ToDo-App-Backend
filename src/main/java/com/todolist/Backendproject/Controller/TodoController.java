package com.todolist.Backendproject.Controller;

import java.util.ArrayList;
import java.util.List;
import java.net.URI;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;

import com.todolist.Backendproject.Component.Priority;
import com.todolist.Backendproject.Component.Todo;
import com.todolist.Backendproject.Service.TodoService;

@RestController
@CrossOrigin
public class TodoController {

  @Autowired
  private TodoService service;

  @GetMapping(value = "/todos", produces = "application/json")
  public ResponseEntity<List<Todo>> findAll(@RequestParam(defaultValue = "") String name,
      @RequestParam(defaultValue = "ALL") String priority, @RequestParam(defaultValue = "All") String done) {
    if (service.isEmpty()) {
      return ResponseEntity.noContent().build();
    } else {
      List<Todo> filteredTodos = new ArrayList<>();
      if (name != "") {
        filteredTodos = service.filterByName(name);
      } else if (priority != "ALL") {
        Priority prio = Priority.valueOf(priority);
        if (filteredTodos.size() == 0) {
          filteredTodos = service.filterByPriority(prio);
        } else {
          List<Todo> tempList = new ArrayList<>();
          for (Todo todo : service.filterByPriority(prio)) {
            if (filteredTodos.contains(todo)) {
              tempList.add(todo);
            }
          }
          filteredTodos = tempList;
        }
      } else if (done != "All") {
        boolean isDone = (done.equals(done)) ? true : false;
        if (filteredTodos.size() == 0) {
          filteredTodos = service.filterByDone(isDone);
        } else {
          List<Todo> tempList = new ArrayList<>();
          for (Todo todo : service.filterByDone(isDone)) {
            if (filteredTodos.contains(todo)) {
              tempList.add(todo);
            }
          }
          filteredTodos = tempList;
        }
      }

      if (filteredTodos.isEmpty()) {
        return ResponseEntity.ok().body(service.findAll());
      } else {
        return ResponseEntity.ok().body(filteredTodos);
      }
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
    if (!service.update(id, todo.getName(), todo.getPriority(), todo.getDueDate())) {
      return ResponseEntity.notFound().build();
    } else {
      return ResponseEntity.ok().build();
    }
  }

  @PostMapping(value = "/todos/{id}/done")
  public ResponseEntity<Void> setDone(@PathVariable long id) {
    service.findById(id).setDone(true);
    service.findById(id).setDoneDate(LocalDateTime.now());
    return ResponseEntity.ok().build();
  }

  @PutMapping(value = "/todos/{id}/undone")
  public ResponseEntity<Void> setUndone(@PathVariable long id) {
    service.findById(id).setDone(false);
    service.findById(id).setDoneDate(null);
    return ResponseEntity.ok().build();
  }

  @DeleteMapping(value = "/todos/{id}")
  public ResponseEntity<Void> deleteTodo(@PathVariable long id) {
    if (!service.delete(id)) {
      return ResponseEntity.notFound().build();
    } else {
      return ResponseEntity.ok().build();
    }
  }

}
