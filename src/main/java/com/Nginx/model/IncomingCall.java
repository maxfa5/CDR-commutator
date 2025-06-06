package com.Nginx.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class IncomingCall extends Call {
  public IncomingCall() {
    super();
  }
  @Override
  @JsonProperty("totalTime")
  public String getTotalTime() {
    return super.getTotalTime();
  }
}
