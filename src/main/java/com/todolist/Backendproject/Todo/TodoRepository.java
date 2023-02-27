package com.todolist.Backendproject.Todo;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

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
    return this.todos.stream().filter(todo -> todo.getId() == id).findFirst().orElse(null);
  }

  public ArrayList<Todo> filterByName(String name) {
    return todos.stream().filter(todo -> todo.getName().contains(name)).collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
  }

}
