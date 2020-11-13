# CEXA Quartz Scheduler Instructions

- Chrome does not recognize self-signed certificates, to bypass this paste `chrome://flags/#allow-insecure-localhost` in your browser and click `Enable`.
- Use `application.properties` to set your job file location. Property name | `quartz.job.file.location`
- Start the Spring Boot server in any IDE.
- Navigate to the `src/angular/my-app` folder.
- Run `ng serve --open` to start the client. Navigate to `http://localhost:4200/`.
