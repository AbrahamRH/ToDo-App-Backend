package com.todolist.Backendproject.Repository;
import org.springframework.stereotype.Repository;

import com.todolist.Backendproject.Component.ComparatorTodo;
import com.todolist.Backendproject.Component.Priority;
import com.todolist.Backendproject.Component.Todo;

import java.util.List;
import java.util.ArrayList;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.stream.Collectors;

@Repository
public class TodoRepository {
  private static int nextId = 0; 
  private List<Todo> todos = new ArrayList<Todo>();

  public TodoRepository() { }

  public Todo add(Todo todo) {
    todo.setId(++nextId);
    todo.setCreationDate(LocalDateTime.now());
    this.todos.add(todo);
    return todo;
  }

  public List<Todo> findAll() {
    return Collections.unmodifiableList(todos);
  }

  public Todo findById(long id) {
    return this.todos.parallelStream().filter(todo -> todo.getId() == id)
               .findFirst().orElse(null);
  }

  public List<Todo> filterByName(String name) {
    return todos.parallelStream()
                .filter(todo -> todo.getName()
                                    .toLowerCase()
                                    .contains(name.toLowerCase()))
                .collect(Collectors.toList());
  }

  public List<Todo> filterByPriority(Priority prio){
    return todos.parallelStream()
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

  public List<Todo> sort(boolean byPriority, boolean pAscending, boolean byDueDate, boolean dAscending, boolean firstPrio){
    ComparatorTodo comparatorPriority = new ComparatorTodo(byPriority, pAscending);
    ComparatorTodo comparatorDueDate = new ComparatorTodo(byDueDate, dAscending);
    List<Todo> sortedTodos = new ArrayList<Todo>(todos);
    if (byPriority && byDueDate) {
      if (firstPrio){ 
        Collections.sort(sortedTodos, comparatorPriority.thenComparing(comparatorDueDate));
      } else {
        Collections.sort(sortedTodos, comparatorDueDate.thenComparing(comparatorPriority));
      }
    } else if (byPriority) {
      Collections.sort(sortedTodos, comparatorPriority);
    } else {
      Collections.sort(sortedTodos, comparatorDueDate);
    }
    return sortedTodos;
  }
}
