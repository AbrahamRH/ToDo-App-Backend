package com.todolist.Backendproject.Repository;
import org.springframework.stereotype.Repository;

import com.todolist.Backendproject.Component.Priority;
import com.todolist.Backendproject.Component.Todo;

import java.util.List;
import java.util.ArrayList;
import java.time.LocalDate;
import java.util.Collections;
import java.util.stream.Collectors;

@Repository
public class TodoRepository {
  private List<Todo> todos = new ArrayList<Todo>();

  public TodoRepository() { }

  public void add(Todo todo) {
    this.todos.add(todo);
  }

  public List<Todo> findAll() {
    return Collections.unmodifiableList(todos);
  }

  public Todo findById(long id) {
    return this.todos.stream().filter(todo -> todo.getId() == id)
               .findFirst().orElse(null);
  }

  public List<Todo> filterByName(String name) {
    return todos.stream()
                .filter(todo -> todo.getName()
                                    .toLowerCase()
                                    .contains(name.toLowerCase()))
                .collect(Collectors.toList());
  }

  public List<Todo> filterByPriority(Priority prio){
    return todos.stream()
                .filter(todo -> todo.getPriority().equals(prio))
                .collect(Collectors.toList());
  }

  public List<Todo> filterByDone(boolean done){
    return todos.parallelStream()
                .filter(todo -> todo.isDone() == done)
                .collect(Collectors.toList());
  }

  public boolean delete(long id){
    return todos.removeIf(todo -> (todo.getId() == id));
  }

  public boolean update(long id, String name, Priority priority, LocalDate dueDate){
    Todo newTodo = findById(id);
    if(newTodo == null) return false;
    newTodo.setName(name);
    newTodo.setPriority(priority);
    newTodo.setDueDate(dueDate);
    return true;
  }
}
