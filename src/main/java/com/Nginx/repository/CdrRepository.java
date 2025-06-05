package com.Nginx.repository;

import com.Nginx.model.CdrRecord;
import com.Nginx.model.PhoneNumber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CdrRepository extends JpaRepository<CdrRecord, Long> {
  List<CdrRecord> findAllByInitiator_id(long initiator_number_id);

}