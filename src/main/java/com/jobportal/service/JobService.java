package com.jobportal.service;

import com.jobportal.dto.JobCreateRequest;
import com.jobportal.entity.Job;

import java.util.List;

public interface JobService {
    Job createJob(JobCreateRequest request);

    List<Job> getAllJobs();
}
