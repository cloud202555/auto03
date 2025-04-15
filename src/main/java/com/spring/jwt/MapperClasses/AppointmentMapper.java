package com.spring.jwt.MapperClasses;

import com.spring.jwt.Appointment.AppointmentDto;
import com.spring.jwt.entity.Appointment;

public class AppointmentMapper {

    public static Appointment toEntity(AppointmentDto dto) {
        Appointment appointment = new Appointment();
        appointment.setAppointmentDate(dto.getAppointmentDate());
        appointment.setCustomerName(dto.getCustomerName());
        appointment.setMobileNo(dto.getMobileNo());
        appointment.setVehicleNo(dto.getVehicleNo());
        appointment.setVehicleMaker(dto.getVehicleMaker());
        appointment.setVehicleModel(dto.getVehicleModel());
        appointment.setManufacturedYear(dto.getManufacturedYear());
        appointment.setKilometerDriven(dto.getKilometerDriven());
        appointment.setFuelType(dto.getFuelType());
        appointment.setWorkType(dto.getWorkType());
        appointment.setVehicleProblem(dto.getVehicleProblem());
        appointment.setPickUpAndDropService(dto.getPickUpAndDropService());
        appointment.setStatus(dto.getStatus());
        appointment.setUserId(dto.getUserId());
        return appointment;
    }

    public static AppointmentDto toDto(Appointment appointment) {
        return new AppointmentDto(appointment);
    }
}
