package com.todolist.Backendproject.Component;

import java.util.Comparator;

public class ComparatorTodo implements Comparator<Todo>{

  private boolean cPriority;
  private boolean cDueDate;
  private boolean prioAscending;
  private boolean dueAscending;
  private boolean firstPriority;

  /**
   * 
   * @param cPriority If we want to compare priority
   * @param cDueDate If we want to compare dueDate
   * @param prioAscending true = ascending, false = descending
   * @param dueAscending true = ascending, false = descending
   * @param firstPriority flag if we should sort first the priority or the due date
   */
  public ComparatorTodo(boolean cPriority, boolean cDueDate, 
                        boolean prioAscending, boolean dueAscending,
                        boolean firstPriority){
    this.cDueDate = cDueDate;
    this.cPriority = cPriority;
    this.prioAscending = prioAscending;
    this.dueAscending = dueAscending;
    this.firstPriority = firstPriority;
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
    if (this.cPriority == true){
      return comparePriority(todo1, todo2);
    } else {
      return compareDueDate(todo1, todo2);
    }
  }
  
}
