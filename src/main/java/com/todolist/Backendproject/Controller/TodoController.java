package com.todolist.Backendproject.Controller;

import java.net.URI;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
import com.todolist.Backendproject.Repository.Metrics;
import com.todolist.Backendproject.Service.TodoService;

@RestController
@CrossOrigin
public class TodoController {

  @Autowired
  private TodoService service;

  @GetMapping(value = "/todos", produces = "application/json")
  public ResponseEntity<Page<Todo>> findAll( @RequestParam(defaultValue = "0") int pageNumber,
    @RequestParam(defaultValue = "") String name, @RequestParam(defaultValue = "ALL") String priority, @RequestParam(defaultValue = "ALL") String done) {

    name = ( name == "") ? null : name;
    priority = (priority.equals("ALL")) ? null : priority;
    done = (done.equals("ALL")) ? null : done;

    Pageable todoPage = PageRequest.of(pageNumber,10);

    if( name == priority && name == done){
      if (service.isEmpty()) {
        return ResponseEntity.noContent().build();
      } else { 
        return ResponseEntity.ok().body(service.findAll(todoPage));
      }
    } else {
      Page<Todo> filteredTodos = service.filter(name, priority, done, todoPage);
      if (filteredTodos.isEmpty()) {
        return ResponseEntity.noContent().build();
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

  @GetMapping(value = "/todos/metrics", produces = "application/json")
  public ResponseEntity<Metrics>  getMetrics(){
    long totalAvg = service.average();
    long lowAverage = service.averageByPriority(Priority.LOW);
    long mediumAverage = service.averageByPriority(Priority.MEDIUM);
    long highAverage = service.averageByPriority(Priority.HIGH);
    Metrics metrics = new Metrics(totalAvg, lowAverage, mediumAverage, highAverage);
    return ResponseEntity.ok().body(metrics);
    
  }

}
