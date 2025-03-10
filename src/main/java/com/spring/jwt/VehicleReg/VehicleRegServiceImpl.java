package com.spring.jwt.VehicleReg;

import com.spring.jwt.Appointment.AppointmentRepository;
import com.spring.jwt.entity.Appointment;
import com.spring.jwt.entity.VehicleReg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class VehicleRegServiceImpl implements VehicleRegService {

    @Autowired
    private VehicleRegRepository vehicleRegRepository;
    @Autowired
    private AppointmentRepository appointmentRepository;

    @Override
    public VehicleRegDto getVehicleRegById(Integer vehicleRegId) {
        VehicleReg vehicleReg = vehicleRegRepository.findById(vehicleRegId).orElseThrow(() -> new RuntimeException("VehicleReg not found"));
        return new VehicleRegDto(vehicleReg);
    }

    @Override
    public List<VehicleRegDto> getAllVehicleRegs() {
        List<VehicleReg> vehicleRegs = vehicleRegRepository.findAll();
        return vehicleRegs.stream().map(VehicleRegDto::new).collect(Collectors.toList());
    }

    @Override
    public VehicleRegDto createVehicleReg(VehicleRegDto vehicleRegDto) {

        VehicleReg vehicleReg = new VehicleReg();
        copyDtoToEntity(vehicleRegDto, vehicleReg);
        vehicleReg = vehicleRegRepository.save(vehicleReg);
        return new VehicleRegDto(vehicleReg);
    }

    @Override
    public VehicleRegDto updateVehicleReg(Integer vehicleRegId, VehicleRegDto vehicleRegDto) {
        VehicleReg vehicleReg = vehicleRegRepository.findById(vehicleRegId)
                .orElseThrow(() -> new RuntimeException("VehicleReg not found"));
        copyDtoToEntity(vehicleRegDto, vehicleReg);
        vehicleReg = vehicleRegRepository.save(vehicleReg);
        return new VehicleRegDto(vehicleReg);
    }

    @Override
    public void deleteVehicleReg(Integer vehicleRegId) {
        VehicleReg vehicleReg = vehicleRegRepository.findById(vehicleRegId)
                .orElseThrow(() -> new RuntimeException("VehicleReg not found"));
        vehicleRegRepository.delete(vehicleReg);
    }

    @Override
    public List<VehicleRegDto> getByStatus(String status) {
        List<VehicleReg> vehicleRegs = vehicleRegRepository.findByStatus(status);

        if (vehicleRegs.isEmpty()) {
            throw new RuntimeException("No vehicle registrations found with status: " + status);
        }
        return vehicleRegs.stream()
                .map(VehicleRegDto::new)
                .collect(Collectors.toList());
    }



    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    public List<VehicleRegDto> getByDateRange(String startDate, String endDate) {
        try {

            Date start = dateFormat.parse(startDate);
            Date end = dateFormat.parse(endDate);

            List<VehicleReg> vehicleRegs = vehicleRegRepository.findByDateBetween(start, end);


            return vehicleRegs.stream()
                    .map(VehicleRegDto::new)
                    .collect(Collectors.toList());
        } catch (ParseException e) {
            throw new RuntimeException("Invalid date format. Use 'yyyy-MM-dd'");
        }
    }

    @Override
    public VehicleRegDto getVehicleRegByAppointmentId(Integer appointmentId) {
        VehicleReg vehicleReg = (VehicleReg) vehicleRegRepository.findByAppointmentId(appointmentId)
                .orElseThrow(() -> new RuntimeException("Vehicle registration not found for appointment ID: " + appointmentId));
        return new VehicleRegDto(vehicleReg);
    }

    private void copyDtoToEntity(VehicleRegDto vehicleRegDto, VehicleReg vehicleReg) {
        vehicleReg.setVehicleRegId(vehicleRegDto.getVehicleRegId());
        vehicleReg.setAppointmentId(vehicleRegDto.getAppointmentId());

        vehicleReg.setVehicleNumber(vehicleRegDto.getVehicleNumber());
        vehicleReg.setVehicleBrand(vehicleRegDto.getVehicleBrand());
        vehicleReg.setVehicleModelName(vehicleRegDto.getVehicleModelName());
        vehicleReg.setVehicleVariant(vehicleRegDto.getVehicleVariant());
        vehicleReg.setEngineNumber(vehicleRegDto.getEngineNumber());
        vehicleReg.setChasisNumber(vehicleRegDto.getChasisNumber());
        vehicleReg.setNumberPlateColour(vehicleRegDto.getNumberPlateColour());

        vehicleReg.setCustomerId(vehicleRegDto.getCustomerId());
        vehicleReg.setCustomerName(vehicleRegDto.getCustomerName());
        vehicleReg.setCustomerAddress(vehicleRegDto.getCustomerAddress());
        vehicleReg.setCustomerMobileNumber(vehicleRegDto.getCustomerMobileNumber());
        vehicleReg.setCustomerAadharNo(vehicleRegDto.getCustomerAadharNo());
        vehicleReg.setCustomerGstin(vehicleRegDto.getCustomerGstin());

        vehicleReg.setSuperwiser(vehicleRegDto.getSuperwiser());
        vehicleReg.setTechnician(vehicleRegDto.getTechnician());
        vehicleReg.setWorker(vehicleRegDto.getWorker());
        vehicleReg.setStatus(vehicleRegDto.getStatus());
        vehicleReg.setUserId(vehicleRegDto.getUserId());
        vehicleReg.setDate(vehicleRegDto.getDate());
    }

}