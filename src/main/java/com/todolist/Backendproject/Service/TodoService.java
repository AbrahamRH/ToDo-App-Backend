package com.todolist.Backendproject.Service;

import java.util.List;

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
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'filterByDone'");
  }

  @Override
  public List<Todo> sort() {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'sort'");
  }

}
