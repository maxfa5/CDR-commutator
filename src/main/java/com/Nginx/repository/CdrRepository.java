package com.Nginx.repository;

import com.Nginx.model.CdrRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CdrRepository extends JpaRepository<CdrRecord, Long> {

}