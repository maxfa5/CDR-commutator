package com.Nginx.repository;

import com.Nginx.model.CdrRecord;
import com.Nginx.model.PhoneNumber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CdrRepository extends JpaRepository<CdrRecord, Long> {
  
  @Query("SELECT c FROM CdrRecord c WHERE c.recipient.id = :phoneNumberId " +
      "AND c.callType = com.Nginx.model.CallType.INCOMING " +
      "AND c.startTime BETWEEN :monthAgo AND :now")
  List<CdrRecord> findIncomingCallsLastMonth(
      @Param("phoneNumberId") Long phoneNumberId,
      @Param("monthAgo") LocalDateTime monthAgo,
      @Param("now") LocalDateTime now);
  
  
  @Query("SELECT c FROM CdrRecord c WHERE c.initiator.id = :phoneNumberId " +
      "AND c.callType = com.Nginx.model.CallType.OUTGOING " +
      "AND c.startTime BETWEEN :monthAgo AND :now")
  List<CdrRecord> findOutgoingCallsLastMonth(
      @Param("phoneNumberId") Long phoneNumberId,
      @Param("monthAgo") LocalDateTime monthAgo,
      @Param("now") LocalDateTime now);
  
}