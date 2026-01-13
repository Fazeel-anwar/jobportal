package com.jobportal.service;

import com.jobportal.dto.JobCreateRequest;
import com.jobportal.entity.Job;
import com.jobportal.entity.User;
import com.jobportal.repository.JobRepository;
import com.jobportal.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class JobServiceImpl implements JobService {

    private final JobRepository jobRepository;
    private final UserRepository userRepository;

    @Override
    public Job createJob(JobCreateRequest request) {

        User provider = userRepository.findById(request.getProviderId())
                .orElseThrow(() -> new RuntimeException("Provider not found"));

        Job job = Job.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .location(request.getLocation())
                .experience(request.getExperience())
                .postedDate(LocalDate.now())
                .jobProvider(provider)
                .build();

        return jobRepository.save(job);
    }


    @Override
    public List<Job> getAllJobs() {
        return jobRepository.findAll();
    }
}
