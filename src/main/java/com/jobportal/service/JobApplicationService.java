package com.jobportal.service;

import com.jobportal.entity.Job;
import com.jobportal.entity.JobApplication;
import com.jobportal.entity.Role;
import com.jobportal.entity.User;
import com.jobportal.repository.JobApplicationRepository;
import com.jobportal.repository.JobRepository;
import com.jobportal.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class JobApplicationService {

    private final JobRepository jobRepository;
    private final UserRepository userRepository;
    private final JobApplicationRepository jobApplicationRepository;



    public JobApplication apply(Long jobId, Long userId, String resumeUrl) {

        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new RuntimeException("Job not found"));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        // Provider cannot apply own job
        if (job.getJobProvider().getId().equals(user.getId())) {
            throw new RuntimeException("Job provider cannot apply for own job");
        }

        // Only job seekers can apply
        if (user.getRole() != Role.JOB_SEEKER) {
            throw new RuntimeException("Only job seekers can apply");
        }

        // Cannot apply twice
        if (jobApplicationRepository.existsByJobIdAndUserId(jobId, userId)) {
            throw new RuntimeException("You already applied for this job");
        }
        JobApplication application = new JobApplication();
        application.setJob(job);
        application.setUser(user);
        application.setResumeUrl(resumeUrl);
        application.setStatus("APPLIED");

        return jobApplicationRepository.save(application);
    }

    public List<JobApplication> getApplicationsForJob(Long jobId) {
        return jobApplicationRepository.findByJobId(jobId);
    }

    public JobApplication updateStatus(Long applicationId, String status) {

        JobApplication app = jobApplicationRepository.findById(applicationId)
                .orElseThrow(() -> new RuntimeException("Application not found"));

        app.setStatus(status);
        return jobApplicationRepository.save(app);
    }
}
