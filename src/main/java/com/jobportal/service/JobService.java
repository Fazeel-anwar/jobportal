package com.jobportal.service;

import com.jobportal.entity.Job;

import java.util.List;

public interface JobService {
    Job createJob(Job job);
    List<Job> getAllJobs();
}
