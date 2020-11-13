package com.wale.quartz.cexa.service;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

import com.wale.quartz.cexa.PropUtils;
import com.wale.quartz.cexa.job.QuartzJob;
import com.wale.quartz.cexa.model.ScheduleDetails;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerKey;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ScheduleService {

  public ResponseEntity<ScheduleDetails> executeTask(String name) throws SchedulerException {
    Scheduler scheduler = createScheduler();
    JobDetail jobDetail = scheduleJob(scheduler, name);
    ScheduleDetails scheduleDetails = ScheduleDetails.builder().jobName(jobDetail.getKey().getName())
        .jobDescription(jobDetail.getDescription()).fileLocation(PropUtils.getJobDetailsFileLocation())
        .fileName(jobDetail.getKey().getName() + ".txt").build();
    return new ResponseEntity<>(scheduleDetails, HttpStatus.OK);
  }

  public Scheduler createScheduler() throws SchedulerException {
    return StdSchedulerFactory.getDefaultScheduler();
  }

  public JobDetail scheduleJob(Scheduler scheduler, String name) throws SchedulerException {
    JobDataMap jobDataMap = new JobDataMap();
    jobDataMap.put("Parent Corp", "ESQ");
    jobDataMap.put("Subsidiary", "CEXA");

    JobDetail jobDetail = newJob().ofType(QuartzJob.class).storeDurably()
        .withIdentity(JobKey.jobKey(name + "_" + UUID.randomUUID().toString())).withDescription("CEXA Quartz job")
        .setJobData(jobDataMap).build();

    Trigger trigger = createTrigger(jobDetail);
    scheduler.scheduleJob(jobDetail, trigger);
    scheduler.start();

    return jobDetail;
  }

  private Trigger createTrigger(JobDetail job) {
    int frequencyInSec = 0;
    return newTrigger().forJob(job).withIdentity(TriggerKey.triggerKey(job.getKey().getName() + "-Trigger"))
        .withSchedule(simpleSchedule().withIntervalInSeconds(frequencyInSec)).build();
  }
}
