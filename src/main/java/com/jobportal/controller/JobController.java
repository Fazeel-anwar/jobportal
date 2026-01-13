package com.jobportal.controller;

import com.jobportal.dto.JobCreateRequest;
import com.jobportal.entity.Job;
import com.jobportal.service.JobService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/jobs")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class JobController {

    private final JobService jobService;

    @PostMapping
    public Job createJob(@RequestBody JobCreateRequest request) {
        return jobService.createJob(request);
    }


    @GetMapping
    public List<Job> getAllJobs() {
        return jobService.getAllJobs();
    }
}
