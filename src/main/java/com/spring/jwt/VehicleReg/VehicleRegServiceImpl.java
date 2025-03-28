package com.spring.jwt.VehicleReg;

import com.spring.jwt.Appointment.AppointmentRepository;
import com.spring.jwt.entity.Appointment;
import com.spring.jwt.entity.VehicleReg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.lang.reflect.Field;
import java.util.Map;

import org.springframework.util.ReflectionUtils;
import org.springframework.stereotype.Service;

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

        if (vehicleRegDto.getVehicleNumber() != null) {
            vehicleReg.setVehicleNumber(vehicleRegDto.getVehicleNumber());
        }
        if (vehicleRegDto.getVehicleBrand() != null) {
            vehicleReg.setVehicleBrand(vehicleRegDto.getVehicleBrand());
        }
        if (vehicleRegDto.getVehicleModelName() != null) {
            vehicleReg.setVehicleModelName(vehicleRegDto.getVehicleModelName());
        }
        if (vehicleRegDto.getManufactureYear() != null) {
            vehicleReg.setManufactureYear(vehicleRegDto.getManufactureYear());
        }
        if (vehicleRegDto.getAdvancePayment() != null) {
            vehicleReg.setAdvancePayment(vehicleRegDto.getAdvancePayment());
        }
        if (vehicleRegDto.getVehicleVariant() != null) {
            vehicleReg.setVehicleVariant(vehicleRegDto.getVehicleVariant());
        }
        if (vehicleRegDto.getEngineNumber() != null) {
            vehicleReg.setEngineNumber(vehicleRegDto.getEngineNumber());
        }
        if (vehicleRegDto.getChasisNumber() != null) {
            vehicleReg.setChasisNumber(vehicleRegDto.getChasisNumber());
        }
        if (vehicleRegDto.getNumberPlateColour() != null) {
            vehicleReg.setNumberPlateColour(vehicleRegDto.getNumberPlateColour());
        }
        if (vehicleRegDto.getKmsDriven() != null) {
            vehicleReg.setKmsDriven(vehicleRegDto.getKmsDriven());
        }
        if (vehicleRegDto.getCustomerId() != null) {
            vehicleReg.setCustomerId(vehicleRegDto.getCustomerId());
        }
        if (vehicleRegDto.getCustomerName() != null) {
            vehicleReg.setCustomerName(vehicleRegDto.getCustomerName());
        }
        if (vehicleRegDto.getCustomerAddress() != null) {
            vehicleReg.setCustomerAddress(vehicleRegDto.getCustomerAddress());
        }
        if (vehicleRegDto.getCustomerMobileNumber() != null) {
            vehicleReg.setCustomerMobileNumber(vehicleRegDto.getCustomerMobileNumber());
        }
        if (vehicleRegDto.getCustomerAadharNo() != null) {
            vehicleReg.setCustomerAadharNo(vehicleRegDto.getCustomerAadharNo());
        }
        if (vehicleRegDto.getCustomerGstin() != null) {
            vehicleReg.setCustomerGstin(vehicleRegDto.getCustomerGstin());
        }
        if (vehicleRegDto.getSuperwiser() != null) {
            vehicleReg.setSuperwiser(vehicleRegDto.getSuperwiser());
        }
        if (vehicleRegDto.getTechnician() != null) {
            vehicleReg.setTechnician(vehicleRegDto.getTechnician());
        }
        if (vehicleRegDto.getWorker() != null) {
            vehicleReg.setWorker(vehicleRegDto.getWorker());
        }
        if (vehicleRegDto.getStatus() != null) {
            vehicleReg.setStatus(vehicleRegDto.getStatus());
        }
        if (vehicleRegDto.getUserId() != null) {
            vehicleReg.setUserId(vehicleRegDto.getUserId());
        }
        if (vehicleRegDto.getDate() != null) {
            vehicleReg.setDate(vehicleRegDto.getDate());
        }
        if (vehicleRegDto.getVehicleInspection() != null) {
            vehicleReg.setVehicleInspection(vehicleRegDto.getVehicleInspection());
        }
        if (vehicleRegDto.getJobCard() != null) {
            vehicleReg.setJobCard(vehicleRegDto.getJobCard());
        }
        if (vehicleRegDto.getInsuranceStatus() != null) {
            vehicleReg.setInsuranceStatus(vehicleRegDto.getInsuranceStatus());
        }
        if (vehicleRegDto.getInsuredFrom() != null) {
            vehicleReg.setInsuredFrom(vehicleRegDto.getInsuredFrom());
        }
        if (vehicleRegDto.getInsuredTo() != null) {
            vehicleReg.setInsuredTo(vehicleRegDto.getInsuredTo());
        }

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

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY-MM-DD");

    @Override
    public List<VehicleRegDto> getByDateRange(String startDate, String endDate) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate start = LocalDate.parse(startDate, formatter);
            LocalDate end = LocalDate.parse(endDate, formatter);

            List<VehicleReg> vehicleRegs = vehicleRegRepository.findByDateBetween(start, end);

            return vehicleRegs.stream()
                    .map(VehicleRegDto::new)
                    .collect(Collectors.toList());
        } catch (DateTimeParseException e) {
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
        vehicleReg.setKmsDriven(vehicleRegDto.getKmsDriven());

        vehicleReg.setVehicleNumber(vehicleRegDto.getVehicleNumber());
        vehicleReg.setVehicleBrand(vehicleRegDto.getVehicleBrand());
        vehicleReg.setVehicleModelName(vehicleRegDto.getVehicleModelName());
        vehicleReg.setVehicleVariant(vehicleRegDto.getVehicleVariant());
        vehicleReg.setEngineNumber(vehicleRegDto.getEngineNumber());
        vehicleReg.setChasisNumber(vehicleRegDto.getChasisNumber());
        vehicleReg.setNumberPlateColour(vehicleRegDto.getNumberPlateColour());
        vehicleReg.setManufactureYear(vehicleRegDto.getManufactureYear());
        vehicleReg.setAdvancePayment(vehicleRegDto.getAdvancePayment());

        vehicleReg.setCustomerId(vehicleRegDto.getCustomerId());
        vehicleReg.setCustomerName(vehicleRegDto.getCustomerName());
        vehicleReg.setCustomerAddress(vehicleRegDto.getCustomerAddress());
        vehicleReg.setCustomerMobileNumber(vehicleRegDto.getCustomerMobileNumber());
        vehicleReg.setCustomerAadharNo(vehicleRegDto.getCustomerAadharNo());
        vehicleReg.setCustomerGstin(vehicleRegDto.getCustomerGstin());
        vehicleReg.setJobCard(vehicleRegDto.getJobCard());
        vehicleReg.setVehicleInspection(vehicleRegDto.getVehicleInspection());
        vehicleReg.setInsuranceStatus(vehicleRegDto.getInsuranceStatus());
        vehicleReg.setInsuredFrom(vehicleRegDto.getInsuredFrom());
        vehicleReg.setInsuredTo(vehicleRegDto.getInsuredTo());
        vehicleReg.setSuperwiser(vehicleRegDto.getSuperwiser());
        vehicleReg.setTechnician(vehicleRegDto.getTechnician());
        vehicleReg.setWorker(vehicleRegDto.getWorker());
        vehicleReg.setStatus(vehicleRegDto.getStatus());
        vehicleReg.setUserId(vehicleRegDto.getUserId());
        vehicleReg.setDate(vehicleRegDto.getDate());
    }

}