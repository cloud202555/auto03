package com.spring.jwt.VehicleReg.JobCard;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobCardRepository extends JpaRepository<JobCard, Integer> {
    List<JobCard> findByJobNameContainingIgnoreCaseOrJobTypeContainingIgnoreCase(String jobName, String jobType);

}