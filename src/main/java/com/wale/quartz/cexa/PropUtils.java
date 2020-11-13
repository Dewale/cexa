package com.wale.quartz.cexa;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropUtils {

  private static final Logger logger = LoggerFactory.getLogger(PropUtils.class);

  public static String getJobDetailsFileLocation() {
    String fileLocation = "";
    ClassLoader loader = Thread.currentThread().getContextClassLoader();
    try (InputStream input = loader.getResourceAsStream("application.properties")) {
      Properties prop = new Properties();
      prop.load(input);
     fileLocation =  prop.getProperty("quartz.job.file.location");
    } catch (IOException ex) {
      logger.error("Error retrieving properties: ", ex);
    }
    return fileLocation;
  }
}
