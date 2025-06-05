package com.Nginx.service;

import com.Nginx.model.CdrRecord;
import com.Nginx.model.PhoneNumber;
import com.Nginx.model.UdrRecord;
import com.Nginx.repository.CdrRepository;
import com.Nginx.repository.NumbersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class UdrService {
  private NumbersRepository numbersRepository;
  private CdrRepository cdrRepository;
  
  @Autowired
  UdrService(NumbersRepository numbersRepository, CdrRepository cdrRepository) {
    this.numbersRepository = numbersRepository;
    this.cdrRepository = cdrRepository;
  }
  public List<UdrRecord> getAllUdrRecords() {
    List<PhoneNumber> allCdr = numbersRepository.findAll();
    List<UdrRecord> result = new ArrayList<UdrRecord>();
    for (PhoneNumber phoneNumber : allCdr) {
      UdrRecord newUdr = new UdrRecord(phoneNumber);
      newUdr.setTotalTime(getTotalTime(phoneNumber));
      result.add(newUdr);
    }
    return result;
  }
  
  public String getTotalTime(PhoneNumber phoneNumber){
    List<CdrRecord> cdrs= cdrRepository.findAllByInitiator_id(phoneNumber.getId());
    long totalSeconds = 0;
    for (CdrRecord cdrRecord : cdrs ) {
      LocalDateTime start = cdrRecord.getStartTime();
      LocalDateTime end = cdrRecord.getEndTime();
      Duration duration = Duration.between(start, end);
      totalSeconds += duration.getSeconds();    }
    long hours = totalSeconds / 3600;
    long minutes = (totalSeconds % 3600) / 60;
    long seconds = totalSeconds % 60;
    
    return String.format("%02d:%02d:%02d", hours, minutes, seconds);
  }
}
