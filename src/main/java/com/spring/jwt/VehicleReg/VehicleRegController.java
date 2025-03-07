package com.spring.jwt.VehicleReg;

import com.spring.jwt.Appointment.ResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vehicle-reg")
public class VehicleRegController {

    @Autowired
    private VehicleRegService vehicleRegService;

    @GetMapping("/getById")
    public ResponseDto<VehicleRegDto> getVehicleRegById(@RequestParam Integer vehicleRegId) {
        try {
            VehicleRegDto vehicleRegDto = vehicleRegService.getVehicleRegById(vehicleRegId);
            return ResponseDto.success("Vehicle Registration found", vehicleRegDto);
        } catch (Exception e) {
            return ResponseDto.error("Vehicle Registration not found", e.getMessage());
        }
    }

    @GetMapping("/getAll")
    public ResponseDto<List<VehicleRegDto>> getAllVehicleRegs() {
        try {
            List<VehicleRegDto> vehicleRegs = vehicleRegService.getAllVehicleRegs();
            return ResponseDto.success("All Vehicle Registrations retrieved", vehicleRegs);
        } catch (Exception e) {
            return ResponseDto.error("Error fetching vehicle registrations", e.getMessage());
        }
    }

    @PostMapping("/add")
    public ResponseEntity<ResponseDto<VehicleRegDto>> createVehicleReg(@RequestBody VehicleRegDto vehicleRegDto) {
        try {
            VehicleRegDto createdVehicleReg = vehicleRegService.createVehicleReg(vehicleRegDto);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(ResponseDto.success("Vehicle Registration created successfully", createdVehicleReg));
        } catch (BadRequestException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ResponseDto.error("Invalid input", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ResponseDto.error("Unexpected error", e.getMessage()));
        }
    }


    @PutMapping("/update")
    public ResponseDto<VehicleRegDto> updateVehicleReg(@RequestParam Integer vehicleRegId, @RequestBody VehicleRegDto vehicleRegDto) {
        try {
            VehicleRegDto updatedVehicleReg = vehicleRegService.updateVehicleReg(vehicleRegId, vehicleRegDto);
            return ResponseDto.success("Vehicle Registration updated successfully", updatedVehicleReg);
        } catch (Exception e) {
            return ResponseDto.error("Error updating vehicle registration", e.getMessage());
        }
    }

    @DeleteMapping("/delete")
    public ResponseDto<String> deleteVehicleReg(@RequestParam Integer vehicleRegId) {
        try {
            vehicleRegService.deleteVehicleReg(vehicleRegId);
            return ResponseDto.success("Vehicle Registration deleted successfully", "Deleted ID: " + vehicleRegId);
        } catch (Exception e) {
            return ResponseDto.error("Error deleting vehicle registration", e.getMessage());
        }
    }

    @GetMapping("/GetStatus")
    public ResponseDto<List<VehicleRegDto>> getByStatus(@RequestParam String status) {
        try {
            List<VehicleRegDto> vehicleRegs = vehicleRegService.getByStatus(status);
            return ResponseDto.success("Vehicle Registrations retrieved by status", vehicleRegs);
        } catch (Exception e) {
            return ResponseDto.error("Error fetching vehicle registrations by status", e.getMessage());
        }
    }

    @GetMapping("/date-range")
    public ResponseDto<List<VehicleRegDto>> getByDateRange(@RequestParam String startDate, @RequestParam String endDate) {
        try {
            List<VehicleRegDto> vehicleRegs = vehicleRegService.getByDateRange(startDate, endDate);
            return ResponseDto.success("Vehicle Registrations retrieved by date range", vehicleRegs);
        } catch (Exception e) {
            return ResponseDto.error("Error fetching vehicle registrations by date range", e.getMessage());
        }
    }
    @GetMapping("/getByAppointmentId")
    public ResponseDto<VehicleRegDto> getVehicleRegByAppointmentId(@RequestParam Integer appointmentId) {
        try {
            VehicleRegDto vehicleRegDto = vehicleRegService.getVehicleRegByAppointmentId(appointmentId);
            return ResponseDto.success("Vehicle Registration found for appointment ID", vehicleRegDto);
        } catch (Exception e) {
            return ResponseDto.error("Vehicle Registration not found for appointment ID", e.getMessage());
        }
    }

}
