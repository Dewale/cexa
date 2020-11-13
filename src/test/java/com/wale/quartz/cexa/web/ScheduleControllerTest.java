package com.wale.quartz.cexa.web;

import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wale.quartz.cexa.model.ScheduleDetails;
import com.wale.quartz.cexa.service.ScheduleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

class ScheduleControllerTest {

  @InjectMocks
  private ScheduleController controller;

  @Mock
  private ScheduleService scheduleService;

  private MockMvc mockMvc;

  @BeforeEach
  void setup() {
    controller = new ScheduleController();
    mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    MockitoAnnotations.initMocks(this);
  }

  @Test
  void executeJob() throws Exception {
    ScheduleDetails scheduleDetails = ScheduleDetails.builder()
        .jobName("CEXA_001").jobDescription("CEXA Quartz").fileName("CEXA.txt").fileLocation("/Documents").build();
    when(scheduleService.executeTask(anyString())).thenReturn(ResponseEntity.ok(scheduleDetails));

    MvcResult result = mockMvc.perform(get("/schedule"))
        .andDo(print()).andExpect(status().isOk())
        .andReturn();
    String responseBody = result.getResponse().getContentAsString();

    ObjectMapper objectMapper = new ObjectMapper();
    ScheduleDetails resultDetails = objectMapper.readValue(responseBody, ScheduleDetails.class);

    verify(scheduleService).executeTask(anyString());
    assertTrue(resultDetails.getFileLocation().equalsIgnoreCase(scheduleDetails.getFileLocation()));
    assertTrue(resultDetails.getJobName().equalsIgnoreCase(scheduleDetails.getJobName()));
    assertTrue(resultDetails.getJobDescription().equalsIgnoreCase(scheduleDetails.getJobDescription()));
    assertTrue(resultDetails.getFileName().equalsIgnoreCase(scheduleDetails.getFileName()));
  }
}
