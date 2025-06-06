package com.Nginx.service;

import com.Nginx.model.CallType;
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
  public List<UdrRecord> getAllUdrRecordsByMonth() {
    List<PhoneNumber> allCdr = numbersRepository.findAll();
    List<UdrRecord> result = new ArrayList<UdrRecord>();
    for (PhoneNumber phoneNumber : allCdr) {
      UdrRecord newUdr = new UdrRecord(phoneNumber);
      newUdr.getIncomingCall().setTotalTime((getTotalTime(phoneNumber, true)));
      newUdr.getIncomingCall().setTotalTime((getTotalTime(phoneNumber, false)));
      result.add(newUdr);
    }
    return result;
  }
  
  public String getTotalTime(PhoneNumber phoneNumber, boolean isIncoming) {
    // 1. Получаем записи CDR за последний месяц
    LocalDateTime now = LocalDateTime.now();
    LocalDateTime monthAgo =now.minusMonths(1);
    
    List<CdrRecord> cdrs = isIncoming
        ? cdrRepository.findIncomingCallsLastMonth(phoneNumber.getId(), monthAgo, now)
        : cdrRepository.findOutgoingCallsLastMonth(phoneNumber.getId(), monthAgo, now);
    System.out.println(cdrs);
    System.out.println(monthAgo);
    // 2. Считаем общую продолжительность
    long totalSeconds = cdrs.stream()
        .mapToLong(cdr -> Duration.between(cdr.getStartTime(), cdr.getEndTime()).getSeconds())
        .sum();
    
    // 3. Форматируем результат
    return formatDuration(totalSeconds);
  }
  
  // Вспомогательный метод для форматирования
  private String formatDuration(long totalSeconds) {
    long hours = totalSeconds / 3600;
    long minutes = (totalSeconds % 3600) / 60;
    long seconds = totalSeconds % 60;
    
    return String.format("%02d:%02d:%02d", hours, minutes, seconds);
  }
  public UdrRecord getAllUdrRecordsById(long id) {
    PhoneNumber phoneNumber = numbersRepository.findById(id).orElseThrow();
    UdrRecord newUdr = new UdrRecord(phoneNumber);
    newUdr.getIncomingCall().setTotalTime((getTotalTime(phoneNumber, true)));
    newUdr.getIncomingCall().setTotalTime((getTotalTime(phoneNumber, false)));
    return newUdr;
  }
}
