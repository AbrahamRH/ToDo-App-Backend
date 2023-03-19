package com.todolist.Backendproject.Component;

import java.util.Comparator;

public class ComparatorTodo implements Comparator<Todo>{

  private enum Direction {
    ASCENDING,
    DESCENDING
  };

  private String field;
  private Direction direction;

  public ComparatorTodo(String field, String direction ){
    this.field = field;
    if (direction.equals("asc")) {
      this.direction = Direction.ASCENDING;
    } else if (direction.equals("desc")) {
      this.direction = Direction.DESCENDING;
    }

  }


  public int comparePriority(Todo todo1, Todo todo2) {
    return todo1.getPriority().getValue() - todo2.getPriority().getValue();
  }

  public int compareDueDate(Todo todo1, Todo todo2){
    if( todo1.getDueDate() == null && todo2.getDueDate() == null ) {
      return 0;
    } else if (todo1.getDueDate() == null ){
      return -1;
    } else if (todo2.getDueDate() == null){
      return 1;
    }
    if (todo1.getDueDate().isBefore(todo2.getDueDate())){
      return -1;
    } else if (todo1.getDueDate().isAfter(todo2.getDueDate())) {
      return 1;
    } else { return 0; }
  }

  @Override
  public int compare(Todo todo1, Todo todo2){

    if (direction == Direction.ASCENDING){
      if ( field == "priority"){
        return comparePriority(todo1, todo2);
      } else {
        return compareDueDate(todo1, todo2);
      }
    } else {
      if (field == "priority"){
        return comparePriority(todo2, todo1);
      } else {
        return compareDueDate(todo2, todo1);
      }

    }
  }
  
}
