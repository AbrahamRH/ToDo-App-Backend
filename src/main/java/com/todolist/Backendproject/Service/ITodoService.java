package com.todolist.Backendproject.Service;

import com.todolist.Backendproject.Component.Priority;
import com.todolist.Backendproject.Component.Todo;
import java.util.List;

import java.time.LocalDate;

public interface ITodoService {

  void createTodo(Todo todo);

  boolean delete(long id);

  boolean update(long id, String name, Priority priority, LocalDate dueDate);

  List<Todo> findAll(); 

  Todo findById(long id);

  List<Todo> filter(String name, String priority, String done);

  List<Todo> filterByName(String name);

  List<Todo> filterByPriority(Priority priority);

  List<Todo> filterByDone(boolean done);

  List <Todo> sort(List<Todo> todos, String[] params);

  boolean isEmpty();

  long average();

  long averageByPriority(Priority priority);
  
}
