package com.spring.jwt.Appointment;


import java.util.List;

public interface AppointmentService {
    List<AppointmentDto> getAllAppointments();

    AppointmentDto getAppointmentById(Integer id);

    AppointmentDto saveAppointment(AppointmentDto appointmentDto);

    void deleteAppointment(Integer id);

    List<AppointmentDto> getByStatus(String status);

    List<AppointmentDto> getByUserId(Integer userId);

    List<AppointmentDto> getByUserIdAndStatus(Integer userId,String status);




}

