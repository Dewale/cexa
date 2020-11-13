import {Component, OnInit} from '@angular/core';
import {ScheduleDetails} from "../model/schedule-details";
import {HttpClient} from "@angular/common/http";

const SCHEDULE_PATH = 'https://localhost:8081/schedule';

@Component({
  selector: 'quartz-scheduler',
  templateUrl: './quartz-scheduler.component.html',
  styleUrls: ['./quartz-scheduler.component.scss']
})
export class QuartzSchedulerComponent implements OnInit {
  detailsExist = false;
  jobName: string;
  jobDescription: string;
  jobDetailsList: any[];

  constructor(
    private httpClient: HttpClient
  ) {
  }

  ngOnInit() {
    this.jobDetailsList = [];
  }

  schedule() {
    this.httpClient.get<ScheduleDetails>(SCHEDULE_PATH).subscribe(res => {
      this.buildJobList(res);
    }, error => {
      console.log("Call failed: ", error)
    })
  }

  buildJobList(res: any) {
    const list = {
      jobName: res.jobName,
      jobDescription: res.jobDescription,
      fileName: res.fileName,
      fileLocation: res.fileLocation
    };
    this.jobDetailsList.push(list);
    this.detailsExist = true;
  }
}
