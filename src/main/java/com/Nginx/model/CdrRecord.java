package com.Nginx.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Data
@Entity
public class CdrRecord {
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;
  @Column(nullable = false)
  private CallType callType;
  @ManyToOne()
  @JoinColumn(name = "initiator_number_id", referencedColumnName = "id")
  private PhoneNumber initiator;
  
  @ManyToOne()
  @JoinColumn(name = "recipient_number_id", referencedColumnName = "id")
  private PhoneNumber recipient;
  @Column(nullable = false)
  private LocalDateTime startTime;
  
  @Column(nullable = false)
  private LocalDateTime endTime;
  public CdrRecord(CallType callType, PhoneNumber initiator_id, PhoneNumber recipient_id, LocalDateTime startTime, LocalDateTime endTime){
   this.callType = callType;
   this.initiator = initiator_id;
   this.recipient = recipient_id;
   this.startTime = startTime;
   this.endTime = endTime;
  }
  
  public CdrRecord() {
  
  }
}
