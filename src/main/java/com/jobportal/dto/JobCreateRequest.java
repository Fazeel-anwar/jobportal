package com.jobportal.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JobCreateRequest {
    private String title;
    private String description;
    private String location;
    private String experience;
    private Long providerId;
}
