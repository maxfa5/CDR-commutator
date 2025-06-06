package com.Nginx.controller;

import com.Nginx.model.UdrRecord;
import com.Nginx.service.UdrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * Rest Controller для обработки http запросов по пути /api
 */
@RestController
@RequestMapping("/api")
public class UdrController {
  private UdrService udrService;
  
  @Autowired
  UdrController(UdrService udrService) {
    this.udrService = udrService;
  }
  
  @GetMapping("/udrLastMonth")
  public List<UdrRecord> getUdrLastMonth() {
    return udrService.getAllUdrRecordsByMonth();
  }
  
  @GetMapping("/udr/{id}")
  public UdrRecord getUdrById(@PathVariable long   id) {
    return udrService.getAllUdrRecordsById(id);
  }
  
}
