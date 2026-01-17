package com.jobportal.service;

import com.jobportal.dto.GeminiRequest;
import com.jobportal.dto.GeminiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GeminiAiService {

    @Value("${gemini.api.key}")
    private String apiKey;

    @Value("${gemini.api.url}")
    private String apiUrl;

    private final RestTemplate restTemplate = new RestTemplate();
    private String extractText(GeminiResponse response) {

        if (response == null ||
                response.getCandidates() == null ||
                response.getCandidates().isEmpty() ||
                response.getCandidates().get(0).getContent() == null ||
                response.getCandidates().get(0).getContent().getParts() == null ||
                response.getCandidates().get(0).getContent().getParts().isEmpty()) {

            throw new RuntimeException("Invalid response from Gemini AI");
        }

        return response.getCandidates()
                .get(0)
                .getContent()
                .getParts()
                .get(0)
                .getText();
    }


    public Double scoreResume(String resume, String jobDescription) {

        String prompt = """
        You are a recruiter AI.
        Score the resume from 0 to 100 based on job description.
        Consider skills, experience, relevance, and seniority.
        Return only a number.

        Job Description:
        %s

        Resume:
        %s
        """.formatted(jobDescription, resume);

        GeminiRequest request = new GeminiRequest();
        GeminiRequest.Part part = new GeminiRequest.Part();
        part.setText(prompt);

        GeminiRequest.Content content = new GeminiRequest.Content();
        content.setParts(List.of(part));

        request.setContents(List.of(content));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<GeminiRequest> entity = new HttpEntity<>(request, headers);

        String finalUrl = apiUrl + "?key=" + apiKey;

        GeminiResponse response = restTemplate.postForObject(finalUrl, entity, GeminiResponse.class);

        String text = extractText(response);

        try {
            return Double.valueOf(text.trim());
        } catch (Exception e) {
            return 0.0;
        }

    }
}
