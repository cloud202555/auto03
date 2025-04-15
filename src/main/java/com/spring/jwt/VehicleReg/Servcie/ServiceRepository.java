package com.spring.jwt.VehicleReg.Servcie;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServiceRepository extends JpaRepository<ADDService, Integer> {

    @Query("SELECT s FROM ADDService s WHERE LOWER(s.serviceName) LIKE LOWER(CONCAT('%', :serviceName, '%'))")
    List<ADDService> findByServiceName(@Param("serviceName") String serviceName);
}