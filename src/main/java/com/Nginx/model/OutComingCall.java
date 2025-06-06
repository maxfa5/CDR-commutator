package com.Nginx.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class OutComingCall extends Call {
  public OutComingCall() {
    super();
  }
  @Override
  @JsonProperty("totalTime")
  public String getTotalTime() {
    return super.getTotalTime();
  }
}
