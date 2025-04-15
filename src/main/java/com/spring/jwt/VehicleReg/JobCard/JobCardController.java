package com.spring.jwt.VehicleReg.JobCard;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequestMapping("/registerJobCard")
@RestController
public class JobCardController {
    private final JobCardService jobCardService;

    @Autowired
    public JobCardController(JobCardService jobCardService) {
        this.jobCardService = jobCardService;
    }

    @PostMapping("/add")
    public ResponseEntity<JobCard> createJobCard(@RequestBody JobCard jobCard) {
        JobCard createdJobCard = jobCardService.createJobCard(jobCard);
        return ResponseEntity.ok(createdJobCard);
    }

    @GetMapping("/getById/{id}")
    public ResponseEntity<JobCard> getJobCardById(@PathVariable Integer id) {
        JobCard jobCard = jobCardService.getJobCardById(id);
        return ResponseEntity.ok(jobCard);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<JobCard>> getAllJobCards() {
        List<JobCard> jobCards = jobCardService.getAllJobCards();
        return ResponseEntity.ok(jobCards);
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<JobCard> updateJobCardPartial(@PathVariable Integer id,
                                                        @RequestBody Map<String, Object> updates) {
        JobCard updatedJobCard = jobCardService.updateJobCardPartial(id, updates);
        return ResponseEntity.ok(updatedJobCard);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteJobCard(@PathVariable Integer id) {
        jobCardService.deleteJobCard(id);
        return ResponseEntity.ok("JobCard deleted successfully!");
    }
    @GetMapping("/search")
    public ResponseEntity<List<JobCard>> searchJobCards(@RequestParam("query") String query) {
        List<JobCard> jobCards = jobCardService.searchJobCards(query);
        return ResponseEntity.ok(jobCards);
    }
}
