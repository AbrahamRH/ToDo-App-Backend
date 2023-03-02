package com.todolist.Backendproject.Service;

import com.todolist.Backendproject.Component.Priority;
import com.todolist.Backendproject.Component.Todo;
import java.util.List;

public interface ITodoService {

  void createTodo(Todo todo);

  boolean delete(long id);

  List<Todo> findAll(); 

  Todo findById(long id);

  List<Todo> filterByName(String name);

  List<Todo> filterByPriority(Priority priority);

  List<Todo> filterByDone(boolean done);

  List<Todo> sort();
  
}
