package com.todolist.Backendproject.Todo;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public class TodoRepository {
  private ArrayList<Todo> todos = new ArrayList<Todo>();

  public TodoRepository() { }



  
}
