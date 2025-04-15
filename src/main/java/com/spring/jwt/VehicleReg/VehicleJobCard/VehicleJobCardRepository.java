package com.spring.jwt.VehicleReg.VehicleJobCard;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VehicleJobCardRepository extends JpaRepository<VehicleJobCard, Integer> {
    List<VehicleJobCard> findByVehicleId(Integer vehicleId);
}