package com.spring.jwt.VehicleReg.VehicleServiceUsed;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface VehicleServicesUsedRepository extends JpaRepository<VehicleServicesUsed, Integer> {
    List<VehicleServicesUsed> findByVehicleId(Integer vehicleId);

    List<VehicleServicesUsed> findByServiceName(String serviceName);

    @Query("SELECT COUNT(v) FROM VehicleServicesUsed v WHERE v.vehicleId = :vehicleId")
    Long countByVehicleId(@Param("vehicleId") Integer vehicleId);

    Optional<VehicleServicesUsed> findFirstByVehicleId(Integer vehicleId);


    List<VehicleServicesUsed> findByDateBetween(LocalDate startDate, LocalDate endDate);
}