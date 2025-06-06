package com.Nginx.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class PhoneNumber {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;
  @Column(unique = true, nullable = false)
  private String number;
  public PhoneNumber(String number) {
    this.number = number;
  }
  
  public PhoneNumber() {
    number = "";
  }
  
}
