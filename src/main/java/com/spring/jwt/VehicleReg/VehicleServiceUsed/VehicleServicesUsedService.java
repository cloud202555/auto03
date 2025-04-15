package com.spring.jwt.VehicleReg.VehicleServiceUsed;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class VehicleServicesUsedService {

    private final VehicleServicesUsedRepository repository;

    public VehicleServicesUsedService(VehicleServicesUsedRepository repository ) {
        this.repository = repository;
    }

    public VehicleServicesUsed createService(VehicleServicesUsed serviceUsed) {
        return repository.save(serviceUsed);
    }


    public VehicleServicesUsed getServiceById(Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Service not found with id " + id));
    }

    public List<VehicleServicesUsed> getAllServices() {
        return repository.findAll();
    }

    public List<VehicleServicesUsed> getServicesByVehicleId(Integer vehicleId) {
        List<VehicleServicesUsed> list = repository.findByVehicleId(vehicleId);
        if(list.isEmpty()){
            throw new RuntimeException("No services found for vehicle id " + vehicleId);
        }
        return list;
    }

    public boolean deleteServiceById(Integer id) {
        Optional<VehicleServicesUsed> serviceOptional = repository.findById(id);
        if (serviceOptional.isPresent()) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }

    public List<VehicleServicesUsed> getServicesByDateRange(LocalDate startDate, LocalDate endDate) {
        return repository.findByDateBetween(startDate, endDate);
    }

}
