package com.todolist.Backendproject.Service;

import com.todolist.Backendproject.Component.Priority;
import com.todolist.Backendproject.Component.Todo;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import java.util.List;

import java.time.LocalDate;

public interface ITodoService {

  void createTodo(Todo todo);

  boolean delete(long id);

  boolean update(long id, String name, Priority priority, LocalDate dueDate);

  List<Todo> findAll(); 

  Page<Todo> findAll(Pageable pageable);

  Todo findById(long id);

  List<Todo> filter(String name, String priority, String done);

  Page<Todo> filter(String name, String priority, String done, Pageable pageable);

  List<Todo> filterByName(String name);

  List<Todo> filterByPriority(Priority priority);

  List<Todo> filterByDone(boolean done);

  List<Todo> sort(boolean byPriority, boolean pAscending, boolean byDueDate, boolean dAscending, boolean firstPrio);

  boolean isEmpty();

  long average();
  
}
