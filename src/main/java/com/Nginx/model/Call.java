package com.Nginx.model;

import lombok.Data;

@Data
public abstract class Call {
  public Call(){
    totalTime = "00:00:00";
  }
  String totalTime;
  public void setTotalTime(String totalTime) {
    this.totalTime = totalTime;
  }
}
