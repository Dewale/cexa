package com.wale.quartz.cexa.job;

import com.wale.quartz.cexa.PropUtils;
import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.Map;

@Component
public class QuartzJob implements Job {

  private static final Logger logger = LoggerFactory.getLogger(QuartzJob.class);
  private static final String BASE_DIRECTORY = PropUtils.getJobDetailsFileLocation();
  private static final String TXT = ".txt";
  private static final String LINE_SEPARATOR = "line.separator";

  public void execute(JobExecutionContext context) {
    executeJob(context.getJobDetail());
  }

  public void executeJob(JobDetail jobDetail) {
    String fileName = jobDetail.getKey().getName() + TXT;
    createFile(fileName);
    writeToFile(jobDetail, fileName);
  }

  private void createFile(String fileName) {
    try {
      File baseFolder = new File(BASE_DIRECTORY);
      File jobDetailsFile = new File(BASE_DIRECTORY + fileName);
      baseFolder.mkdirs();
      if (jobDetailsFile.createNewFile()) {
        logger.info("Job Details file created: {}", fileName);
      } else {
        logger.error(String.format("File: %s already exists", fileName));
      }
    } catch (IOException ex) {
      logger.error("File creation error", ex);
    }
  }

  private void writeToFile(JobDetail jobDetail, String fileName) {
    try {
      int count = 1;
      FileWriter fileWriter = new FileWriter(new File(BASE_DIRECTORY + fileName));
      fileWriter.write("Job Name: " + jobDetail.getKey());
      fileWriter.write(System.getProperty(LINE_SEPARATOR));
      fileWriter.write("Job Description: " + jobDetail.getDescription());
      fileWriter.write(System.getProperty(LINE_SEPARATOR));
      fileWriter.write("Job Execution time: " + new Date());
      fileWriter.write(System.getProperty(LINE_SEPARATOR));

      for (Map.Entry<String, Object> entry : jobDetail.getJobDataMap().entrySet()) {
        fileWriter.write(String.format("Additional Info %d: Key -> %s | Value -> %s", count, entry.getKey(), entry.getValue()));
        fileWriter.write(System.getProperty(LINE_SEPARATOR));
        count++;
      }

      fileWriter.close();
    } catch (Exception ex) {
      logger.error("Error occurred while writing to file", ex);
    }
  }
}
