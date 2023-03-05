package com.todolist.Backendproject.Service;

import java.util.List;
import java.time.LocalDate;

import com.todolist.Backendproject.Repository.TodoRepository;
import com.todolist.Backendproject.Component.Priority;
import com.todolist.Backendproject.Component.Todo;

public class TodoService implements ITodoService{

  private final TodoRepository repository;

  public TodoService(TodoRepository repository){
    this.repository = repository;
  }

  @Override
  public void createTodo(Todo todo) {
    repository.add(todo);
  }

  @Override
  public boolean delete(long id){
    return repository.delete(id);
  }

  @Override
  public boolean update(long id, String name, Priority priority, LocalDate dueDate){
    return repository.update(id, name, priority, dueDate);
  }

  @Override
  public List<Todo> findAll() {
    return repository.findAll();
  }

  @Override
  public Todo findById(long id) {
    return repository.findById(id);
  }

  @Override
  public List<Todo> filterByName(String name) {
    return repository.filterByName(name);
  }

  @Override
  public List<Todo> filterByPriority(Priority priority) {
    return repository.filterByPriority(priority);
  }

  @Override
  public List<Todo> filterByDone(boolean done) {
    return repository.filterByDone(done);
  }

  @Override
  public List<Todo> sort(boolean byPriority, boolean ascending, boolean firstPrio){
    return repository.sort(byPriority, ascending, firstPrio);
  }

}
