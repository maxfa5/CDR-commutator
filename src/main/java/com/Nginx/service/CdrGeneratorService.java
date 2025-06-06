package com.Nginx.service;

import com.Nginx.model.CallType;
import com.Nginx.model.CdrRecord;
import com.Nginx.model.PhoneNumber;
import com.Nginx.repository.CdrRepository;
import com.Nginx.repository.NumbersRepository;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import java.util.List;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Random;

/**
 * Сервис для генерации CDR и номеров телефонов пользователей.
 */
@Service
public class CdrGeneratorService {
  private Random random;
  private CdrRepository cdrRepository;
  private NumbersRepository numbersRepository;
  
  @Autowired
  CdrGeneratorService(CdrRepository cdrRepository, NumbersRepository numbersRepository) {
    this.cdrRepository = cdrRepository;
    this.numbersRepository = numbersRepository;
    this.random = new Random();
  }
  
  @PostConstruct
  public void GenerateCdr() {
    System.out.println("Метод init() выполняется после создания бина");
    generateNumbers(10);
    generateCdrPerYear();
  }
  
  @Transactional
  public void generateCdrPerYear(){
    int countCals = random.nextInt(110) + 10;
    List<Long> allNumberIds = numbersRepository.findAllIds();
    int totalNumbers = allNumberIds.size();
    for (int i = 0; i < countCals; i++) {
      CallType type = CallType.values()[random.nextInt(CallType.values().length)];
      
      long initiatorId = random.longs(1, 1, totalNumbers + 1).findFirst().getAsLong();
      long recipientId;
      do {
        recipientId = random.longs(1, 1, totalNumbers + 1).findFirst().getAsLong();
      } while (recipientId == initiatorId);
      LocalDateTime startTime = GetStartTime();
      LocalDateTime endTime = startTime.plusMinutes(random.nextInt(30));
      PhoneNumber initializeNumber = numbersRepository.findById(initiatorId)
          .orElseThrow(() -> new RuntimeException("PhoneNumber not found: " + initiatorId));
      PhoneNumber recipientNumber = numbersRepository.findById(recipientId)
          .orElseThrow(() -> new RuntimeException("PhoneNumber recipient not found: "));
      
      cdrRepository.save(new CdrRecord(type,initializeNumber,recipientNumber,startTime,endTime));
    }
  }
  
  private LocalDateTime GetStartTime() {
    int currentYear = LocalDateTime.now().getYear();
    
    // Начало года
    LocalDateTime startOfYear = LocalDateTime.of(currentYear, 1, 1, 0, 0);
    
    // Конец года
    LocalDateTime endOfYear = LocalDateTime.of(currentYear, 12, 31, 23, 59, 59);
    
    // Общее количество минут в текущем году
    long totalMinutes = ChronoUnit.MINUTES.between(startOfYear, endOfYear);
    
    // Генерация случайного количества минут
    long randomMinutes = random.nextInt((int) totalMinutes);
    
    // Получаем случайное время в пределах текущего года
    LocalDateTime randomDateTime = startOfYear.plusMinutes(randomMinutes);
    return randomDateTime;
  }
  
  public void generateNumbers(int counts){
    for (int i = 0; i < counts; i++) {
      try {
        long newNumber = generateRandomPhoneNumber();
      } catch (DataIntegrityViolationException e) {
        i--;
      } catch (Exception e) {
        System.err.println("Ошибка: " + e.getMessage());
      }
    }
  }
  
  private long generateRandomPhoneNumber() {
    // Генерация номера в формате 79XXXXXXXXX (для российских номеров)
    StringBuilder newNumberstr = new StringBuilder("79");
    
    // Добавляем 9 случайных цифр
    for (int i = 0; i < 9; i++) {
      newNumberstr.append(random.nextInt(10));
    }
    PhoneNumber newNumber = new PhoneNumber(newNumberstr.toString());
    numbersRepository.save(newNumber);
    return newNumber.getId();
  }
}
