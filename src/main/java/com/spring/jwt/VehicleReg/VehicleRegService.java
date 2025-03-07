package com.spring.jwt.VehicleReg;

import com.spring.jwt.entity.VehicleReg;

import java.util.List;

public interface VehicleRegService {
    VehicleRegDto getVehicleRegById(Integer vehicleRegId);
    List<VehicleRegDto> getAllVehicleRegs();
    VehicleRegDto createVehicleReg(VehicleRegDto vehicleRegDto);
    VehicleRegDto updateVehicleReg(Integer vehicleRegId, VehicleRegDto vehicleRegDto);
    void deleteVehicleReg(Integer vehicleRegId);

    List<VehicleRegDto> getByStatus(String status);

    public List<VehicleRegDto> getByDateRange(String startDate, String endDate);

    VehicleRegDto getVehicleRegByAppointmentId(Integer appointmentId);
}