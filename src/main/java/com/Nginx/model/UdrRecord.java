package com.Nginx.model;

import lombok.Data;

@Data
public class UdrRecord {
  String msisdn;
  String totalTime;
  public UdrRecord(PhoneNumber phoneNumber) {
    this.msisdn = phoneNumber.getNumber();
  }
}
