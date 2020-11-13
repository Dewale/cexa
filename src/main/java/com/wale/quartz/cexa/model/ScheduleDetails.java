package com.wale.quartz.cexa.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ScheduleDetails {
  private String jobName;
  private String jobDescription;
  private String fileName;
  private String fileLocation;
}
