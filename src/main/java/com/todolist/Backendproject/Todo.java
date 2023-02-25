package com.todolist.Backendproject;

import java.time.LocalDateTime;
import java.time.LocalDate;

public class Todo {
  private long id;
  private String text;
  private boolean done;
  private Priority priority;
  private LocalDate dueDate;
  private LocalDateTime creationDate;

  public Todo() {
  }

  public Todo(long id, String text, boolean done, Priority priority, LocalDate dueDate, LocalDateTime creationDate) {
    this.id = id;
    this.text = text;
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

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
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
