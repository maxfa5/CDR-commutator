package com.Nginx.repository;

import com.Nginx.model.CdrRecord;
import com.Nginx.model.PhoneNumber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

@Repository
public interface NumbersRepository extends JpaRepository<PhoneNumber, Long> {
  @Query("SELECT p.id FROM PhoneNumber p")
  List<Long> findAllIds();
}
