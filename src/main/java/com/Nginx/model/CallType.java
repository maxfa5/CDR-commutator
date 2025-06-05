package com.Nginx.model;

public enum CallType {
  INCOMING(1),
  OUTGOING(2);
  
  private final int value;
  
  CallType(int value) {
    this.value = value;
  }
  
  public int getValue() {
    return value;
  }
}