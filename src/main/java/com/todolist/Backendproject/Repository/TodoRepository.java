package com.todolist.Backendproject.Repository;
import org.springframework.stereotype.Repository;

import com.todolist.Backendproject.Component.ComparatorTodo;
import com.todolist.Backendproject.Component.Priority;
import com.todolist.Backendproject.Component.Todo;

import java.util.List;
import java.util.ArrayList;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
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

  public List<Todo> filter(String name, String priority, String done) {
    List<Todo> list = List.copyOf(this.todos);
    List<Todo> filteredTodos = new ArrayList<>(list);
    if(name != null){
      filteredTodos = filterByName(name);
    }
    if(priority != null){
      filteredTodos = intersection(filteredTodos,filterByPriority(Priority.valueOf(priority)));
    }
    if(done != null){
      filteredTodos = intersection(filteredTodos,filterByDone(done.equals("Done")));
    }
    return filteredTodos;
  }

  private List<Todo> intersection(List<Todo> l1, List<Todo> l2 ){
    List<Todo> temp = new ArrayList<>();
    for(Todo todo : l2) {
      if(l1.contains(todo)){
        temp.add(todo);
      }
    }
    return temp;
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

  public List<Todo> sort(List<Todo> todos, List<ComparatorTodo> comparators){
    if(comparators.size() == 1) {
      Collections.sort(todos, comparators.get(0));
    } else {
      Collections.sort(todos, comparators.get(0).thenComparing(comparators.get(1)));
    }
    return todos;
  }

  public long totalAverage() {
    return getAverange(todos);
  }

  public long averageByPriority(Priority priority){
    return getAverange(filterByPriority(priority));
  }

  /**
   * 
   * @return Gives the average time in seconds to complete their tasks
   */
  private long getAverange(List<Todo> todosDone){
    ArrayList<Long> differences = new ArrayList<>();
    todosDone.forEach( todo -> {
      if(todo.isDone()){
        long creationDateEpoch = todo.getCreationDate().toEpochSecond(ZoneOffset.UTC);
        long doneDateEpoch = todo.getDoneDate().toEpochSecond(ZoneOffset.UTC);
        differences.add((doneDateEpoch - creationDateEpoch));
      }
    });
    if (differences.size() == 0) return 0;
    Long sum = differences.stream().mapToLong(Long::longValue).sum();
    return sum/differences.size();
  }

}
