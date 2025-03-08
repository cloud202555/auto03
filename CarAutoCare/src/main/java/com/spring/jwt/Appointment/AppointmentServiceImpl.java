package com.spring.jwt.Appointment;

import com.spring.jwt.MapperClasses.AppointmentMapper;
import com.spring.jwt.entity.Appointment;
import com.spring.jwt.exception.UserNotFoundExceptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class AppointmentServiceImpl implements AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Override
    public List<AppointmentDto> getAllAppointments() {
        return appointmentRepository.findAll().stream()
                .map(AppointmentDto::new)
                .collect(Collectors.toList());
    }

    @Override
    public AppointmentDto getAppointmentById(Integer id) {
        return appointmentRepository.findById(id)
                .map(AppointmentDto::new)
                .orElseThrow(() -> new NoSuchElementException("Appointment not found with ID: " + id));
    }

    @Override
    public AppointmentDto saveAppointment(AppointmentDto appointmentDto) {
        Appointment appointment = AppointmentMapper.toEntity(appointmentDto);
        appointment = appointmentRepository.save(appointment);
        return AppointmentMapper.toDto(appointment);
    }

    @Override
    public void deleteAppointment(Integer id) {
        if (!appointmentRepository.existsById(id)) {
            throw new NoSuchElementException("Appointment not found with ID: " + id);
        }
        appointmentRepository.deleteById(id);
    }


    @Override
    public List<AppointmentDto> getByStatus(String status) {
        List<Appointment> appointments = appointmentRepository.findByStatus(status);
        return appointments.stream()
                .map(AppointmentDto::new)
                .collect(Collectors.toList());
    }

    @Override
    public List<AppointmentDto> getByUserId(Integer userId) {
        List<Appointment> appointments = appointmentRepository.findByUserId(userId);

        if (appointments.isEmpty()) {
            throw new UserNotFoundExceptions("No appointments found for user ID: " + userId);
        }
        return appointments.stream()
                .map(AppointmentDto::new)
                .collect(Collectors.toList());
    }

    @Override
    public List<AppointmentDto> getByUserIdAndStatus(Integer userId, String status) {
        List<Appointment> appointments = appointmentRepository.findByUserIdAndStatus(userId, status);
        if (appointments.isEmpty()) {
            throw new UserNotFoundExceptions("No appointments found for user ID: " + userId + " with status: " + status);
        }
        return appointments.stream()
                .map(AppointmentDto::new) // Using the constructor in AppointmentDto
                .collect(Collectors.toList());
    }
}
