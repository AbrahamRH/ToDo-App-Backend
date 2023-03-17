package com.todolist.Backendproject.Repository;

public class Metrics {

  long totalAverage;
  //By priority
  long lowAverage;
  long mediumAverage;
  long highAverage;


  public Metrics(long totalAverage, long lowAverage, long mediumAverage, long highAverage) {
    this.totalAverage = totalAverage;
    this.lowAverage = lowAverage;
    this.mediumAverage = mediumAverage;
    this.highAverage = highAverage;
  }


  public void setHighAverage(long highAverage) {
    this.highAverage = highAverage;
  }
  

  public long getTotalAverage() {
    return totalAverage;
  }


  public void setTotalAverage(long totalAverage) {
    this.totalAverage = totalAverage;
  }


  public long getLowAverage() {
    return lowAverage;
  }


  public void setLowAverage(long lowAverage) {
    this.lowAverage = lowAverage;
  }


  public long getMediumAverage() {
    return mediumAverage;
  }


  public void setMediumAverage(long mediumAverage) {
    this.mediumAverage = mediumAverage;
  }


  public long getHighAverage() {
    return highAverage;
  }


  public void format(long seconds) {
    long sec = seconds % 60;
    long minutes = seconds % 3600 / 60;
    long hours = seconds % 86400 / 3600;
    long days = seconds / 86400;
  }

}
