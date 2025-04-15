package com.spring.jwt.VehicleReg;

import com.spring.jwt.entity.VehicleReg;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface VehicleRegRepository extends JpaRepository<VehicleReg, Integer> {

    @Query("SELECT v FROM VehicleReg v WHERE v.date BETWEEN :startDate AND :endDate")
    List<VehicleReg> findByDateBetween(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    List<VehicleReg> findByStatus(String status);

    Optional<Object> findByAppointmentId(Integer appointmentId);

    Optional<VehicleReg> findUserIdByVehicleRegId(Integer vehicleRegId);

        @Query("SELECT v FROM VehicleReg v WHERE LOWER(REPLACE(v.status, ' ', '')) IN :statuses")
        List<VehicleReg> findByNormalizedStatusIn(@Param("statuses") List<String> statuses);

    @Query("SELECT v FROM VehicleReg v WHERE v.vehicleRegId IN (" +
            "SELECT MIN(v2.vehicleRegId) FROM VehicleReg v2 " +
            "WHERE LOWER(v2.vehicleNumber) LIKE LOWER(CONCAT('%', :query, '%')) " +
            "GROUP BY LOWER(v2.vehicleNumber))")
    List<VehicleReg> findBySearchQuery(@Param("query") String query);

    List<VehicleReg> findByVehicleNumber(String vehicleNumber);

    @Query(value = """
        SELECT 
            vr.chasis_number AS chasisNumber,
            vr.engine_number AS engineNumber,
            vr.vehicle_variant AS vehicleVariant,
            vr.number_plate_colour AS numberPlateColour
        FROM vehicle_reg vr
        WHERE vr.vehicle_number = :vehicleNumber
        LIMIT 1
        """, nativeQuery = true)
    VehicleRegDetailsDto findTopByVehicleNumber(@Param("vehicleNumber") String vehicleNumber);


    List<VehicleReg> findByInsuredToBefore(LocalDate currentDate);
    List<VehicleReg> findByInsuredToGreaterThanEqual(LocalDate currentDate);


}


