package com.Nginx.model;

import lombok.Data;

@Data
public class UdrRecord {
  String msisdn;
  IncomingCall incomingCall;
  OutComingCall outcomingCall;
  public UdrRecord(PhoneNumber phoneNumber) {
    this.msisdn = phoneNumber.getNumber();
    this.incomingCall = new IncomingCall();
    this.outcomingCall = new OutComingCall();
  }
}
