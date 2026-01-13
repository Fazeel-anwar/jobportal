package com.jobportal.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "jobs")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Job {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(length = 5000)
    private String description;

    private String location;

    private String experience;

    private LocalDate postedDate;
    @ManyToOne
    @JoinColumn(name = "job_provider_id", nullable = false)
    private User jobProvider;

}
