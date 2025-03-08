package com.spring.jwt.Appointment;

import com.spring.jwt.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Integer> {
    List<Appointment> findByStatus(String status);
    List<Appointment> findByUserId(Integer userId);

    List<Appointment> findByUserIdAndStatus(Integer userId, String status);
}
