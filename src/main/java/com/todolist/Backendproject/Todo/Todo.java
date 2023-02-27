package com.todolist.Backendproject.Todo;


import java.time.LocalDateTime;

import java.time.LocalDate;

public class Todo {

  private long id;
  private String name;
  private boolean done;
  private Priority priority;
  private LocalDate dueDate;
  private LocalDateTime creationDate;

  public Todo() {
  }

  public Todo(long id, String text, boolean done, Priority priority, LocalDate dueDate, LocalDateTime creationDate) {
    this.id = id;
    this.name = text;
    this.done = done;
    this.priority = priority;
    this.dueDate = dueDate;
    this.creationDate = creationDate;
  }

  public long getId() {
    return id;
  }
  public void setId(long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String text) {
    this.name = text;
  }

  public boolean isDone() {
    return done;
  }

  public void setDone(boolean done) {
    this.done = done;
  }
  
  public Priority getPriority() {
    return priority;
  }

  public void setPriority(Priority priority) {
    this.priority = priority;
  }

  public LocalDate getDueDate() {
    return dueDate;
  }

  public void setDueDate(LocalDate dueDate) {
    this.dueDate = dueDate;
  }

  public LocalDateTime getCreationDate() {
    return creationDate;
  }

  public void setCreationDate(LocalDateTime creationDate) {
    this.creationDate = creationDate;
  }
}
