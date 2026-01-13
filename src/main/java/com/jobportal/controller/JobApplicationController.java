package com.jobportal.controller;

import com.jobportal.service.JobApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/applications")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class JobApplicationController {

    private final JobApplicationService service;

    @PostMapping
    public ResponseEntity<?> apply(@RequestBody Map<String, String> req) {

        Long jobId = Long.valueOf(req.get("jobId"));
        Long userId = Long.valueOf(req.get("userId"));
        String resumeUrl = req.get("resumeUrl");

        return ResponseEntity.ok(service.apply(jobId, userId, resumeUrl));
    }
    @GetMapping("/job/{jobId}")
    public ResponseEntity<?> getByJob(@PathVariable Long jobId) {
        return ResponseEntity.ok(service.getApplicationsForJob(jobId));
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<?> updateStatus(
            @PathVariable Long id,
            @RequestParam String status) {

        return ResponseEntity.ok(service.updateStatus(id, status));
    }
}
