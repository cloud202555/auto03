package com.spring.jwt.VehicleReg.JobCard;

import java.util.List;
import java.util.Map;

public interface JobCardService {
    JobCard createJobCard(JobCard jobCard);
    JobCard getJobCardById(Integer id);

    List<JobCard> getAllJobCards();

    JobCard updateJobCardPartial(Integer id, Map<String, Object> updates);

    void deleteJobCard(Integer id);

    List<JobCard> searchJobCards(String query);
}
