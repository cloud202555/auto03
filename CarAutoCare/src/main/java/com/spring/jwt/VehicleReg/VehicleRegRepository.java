package com.spring.jwt.VehicleReg;

import com.spring.jwt.entity.VehicleReg;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface VehicleRegRepository extends JpaRepository<VehicleReg, Integer> {

    @Query("SELECT v FROM VehicleReg v WHERE v.date BETWEEN :startDate AND :endDate")
    List<VehicleReg> findByDateBetween(@Param("startDate") Date startDate, @Param("endDate") Date endDate);

    List<VehicleReg> findByStatus(String status);
}
