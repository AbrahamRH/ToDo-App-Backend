package com.todolist.Backendproject.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;

import com.todolist.Backendproject.Repository.TodoRepository;
import com.todolist.Backendproject.Component.ComparatorTodo;
import com.todolist.Backendproject.Component.Priority;
import com.todolist.Backendproject.Component.Todo;

@Service
public class TodoService implements ITodoService {

  @Autowired
  private final TodoRepository repository;

  public TodoService(TodoRepository repository) {
    this.repository = repository;
  }

  @Override
  public void createTodo(Todo todo) {
    repository.add(todo);
  }

  @Override
  public boolean delete(long id) {
    return repository.delete(id);
  }

  @Override
  public boolean update(long id, String name, Priority priority, LocalDate dueDate) {
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
  public List<Todo> filter(String name, String priority, String done) {
    return repository.filter(name, priority, done);
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
  public List<Todo> sort(List<Todo> todos, String[] params) {
    List<ComparatorTodo> comparators = new ArrayList<>();
    if (params != null) {
      if (params[0].contains(",")) { // We need to sort different fields
        for (String order : params) {
          String[] _sort = order.split(",");
          comparators.add(new ComparatorTodo(_sort[0], _sort[1]));
        }
      } else {
        comparators.add(new ComparatorTodo(params[0], params[1]));
      }
    } else {
      return todos;
    }
    return repository.sort(todos, comparators);
  }

  @Override
  public boolean isEmpty() {
    return (repository.findAll().size() == 0);
  }

  @Override
  public long average() {
    return repository.totalAverage();
  }

  @Override
  public long averageByPriority(Priority priority) {
    return repository.averageByPriority(priority);
  }

}
