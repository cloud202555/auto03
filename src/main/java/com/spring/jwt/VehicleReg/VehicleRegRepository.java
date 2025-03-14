package com.spring.jwt.VehicleReg;

import com.spring.jwt.entity.VehicleReg;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface VehicleRegRepository extends JpaRepository<VehicleReg, Integer> {

    @Query("SELECT v FROM VehicleReg v WHERE v.date BETWEEN :startDate AND :endDate")
    List<VehicleReg> findByDateBetween(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    List<VehicleReg> findByStatus(String status);

    Optional<Object> findByAppointmentId(Integer appointmentId);

    Optional<VehicleReg> findUserIdByVehicleRegId(Integer vehicleRegId);

}
