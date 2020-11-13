package com.wale.quartz.cexa.web;

import com.wale.quartz.cexa.model.ScheduleDetails;
import com.wale.quartz.cexa.service.ScheduleService;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin("*")
@RestController
public class ScheduleController {

  @Autowired
  private ScheduleService scheduleService;

  // Swagger - https://app.swaggerhub.com/apis/Dewale/quartz-scheduler/0.1#/
  @GetMapping("/schedule")
  public ResponseEntity<ScheduleDetails> executeJob(@RequestParam(value = "name", defaultValue = "CEXA") String name)
      throws SchedulerException {
    return scheduleService.executeTask(name);
  }
}
