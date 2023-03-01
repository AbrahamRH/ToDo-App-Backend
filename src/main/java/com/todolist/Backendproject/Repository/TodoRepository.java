package com.todolist.Backendproject.Repository;
import org.springframework.stereotype.Repository;

import com.todolist.Backendproject.Component.Priority;
import com.todolist.Backendproject.Component.Todo;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.stream.Collectors;

@Repository
public class TodoRepository {
  private List<Todo> todos = new ArrayList<Todo>();

  public TodoRepository() { }

  public void add(Todo todo) {
    this.todos.add(todo);
  }

  public List<Todo> getTodos() {
    return Collections.unmodifiableList(todos);
  }

  public Todo findById(long id) {
    return this.todos.stream().filter(todo -> todo.getId() == id)
               .findFirst().orElse(null);
  }

  public List<Todo> filterByName(String name) {
    return todos.stream().filter(todo -> todo.getName().contains(name))
                .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
  }

  public List<Todo> filterByPriority(Priority prio){
    return todos.stream().filter(todo -> todo.getPriority().equals(prio))
                .collect(Collectors.toList());
  }
}
