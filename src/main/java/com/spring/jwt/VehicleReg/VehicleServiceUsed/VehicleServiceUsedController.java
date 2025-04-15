package com.spring.jwt.VehicleReg.VehicleServiceUsed;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;


@RestController
@RequestMapping("/serviceUsed")
public class VehicleServiceUsedController {

    private final VehicleServicesUsedService serviceService;

    public VehicleServiceUsedController(VehicleServicesUsedService serviceService) {
        this.serviceService = serviceService;
    }

    @PostMapping("/AddService")
    public ResponseEntity<VehicleServicesUsed> createService(@RequestBody VehicleServicesUsed serviceUsed) {
        try {
            VehicleServicesUsed createdService = serviceService.createService(serviceUsed);
            return new ResponseEntity<>(createdService, HttpStatus.CREATED);
        } catch (Exception ex) {
            throw ex;
        }
    }

    @GetMapping("/getById/{id}")
    public ResponseEntity<VehicleServicesUsed> getServiceById(@PathVariable Integer id) {
        try {
            VehicleServicesUsed service = serviceService.getServiceById(id);
            return ResponseEntity.ok(service);
        } catch (Exception ex) {
            throw ex;
        }
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<VehicleServicesUsed>> getAllServices() {
        try {
            List<VehicleServicesUsed> services = serviceService.getAllServices();
            return ResponseEntity.ok(services);
        } catch (Exception ex) {
            throw ex;
        }
    }

    @GetMapping("/getByVehicleId/{vehicleId}")
    public ResponseEntity<List<VehicleServicesUsed>> getServicesByVehicleId(@PathVariable Integer vehicleId) {
        try {
            List<VehicleServicesUsed> services = serviceService.getServicesByVehicleId(vehicleId);
            return ResponseEntity.ok(services);
        } catch (Exception ex) {
            throw ex;
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteServiceById(@PathVariable Integer id) {
        try {
            boolean isDeleted = serviceService.deleteServiceById(id);
            if (isDeleted) {
                return ResponseEntity.ok("Service with ID " + id + " deleted successfully.");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Service with ID " + id + " not found.");
            }
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error deleting service with ID " + id + ": " + ex.getMessage());
        }
    }

    @GetMapping("/getByDateRange")
    public ResponseEntity<List<VehicleServicesUsed>> getByDateRange(
            @RequestParam("startDate") String startDateStr,
            @RequestParam("endDate") String endDateStr
    ) {
        try {
            LocalDate startDate = LocalDate.parse(startDateStr);
            LocalDate endDate = LocalDate.parse(endDateStr);
            List<VehicleServicesUsed> services = serviceService.getServicesByDateRange(startDate, endDate);
            return ResponseEntity.ok(services);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }



}