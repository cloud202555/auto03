package com.spring.jwt.VehicleReg.VehicleJobCard;

import com.spring.jwt.VehicleReg.VehicleRegRepository;
import com.spring.jwt.entity.VehicleReg;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class VehicleJobCardService {

    private final VehicleJobCardRepository repository;

    private  final VehicleRegRepository vehicleRegRepository;

    public VehicleJobCardService(VehicleJobCardRepository repository, VehicleRegRepository vehicleRegRepository) {
        this.repository = repository;
        this.vehicleRegRepository = vehicleRegRepository;
    }

    public VehicleJobCard create(VehicleJobCard vehicleJobCard) {
        Optional<VehicleReg> vehicledata =vehicleRegRepository.findById(vehicleJobCard.getVehicleId());
        String vehicleNumber = vehicledata.get().getVehicleNumber();
        vehicleJobCard.setVehicleNumber(vehicleNumber);
        return repository.save(vehicleJobCard);
    }

    public VehicleJobCard getById(Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("VehicleJobCard not found with id " + id));
    }

    public List<VehicleJobCard> getAll() {
        return repository.findAll();
    }

    public VehicleJobCard update(Integer id, Map<String, Object> updates) {
        VehicleJobCard existing = getById(id);

        if (updates.containsKey("jobName")) {
            existing.setJobName((String) updates.get("jobName"));
        }
        if (updates.containsKey("vehicleId")) {

            existing.setVehicleNumber((String) updates.get("vehicleId"));
        }
        if (updates.containsKey("customerNote")) {
            existing.setCustomerNote((String) updates.get("customerNote"));
        }
        if (updates.containsKey("workShopNote")) {
            existing.setWorkShopNote((String) updates.get("workShopNote"));
        }
        if (updates.containsKey("jobType")) {
            existing.setJobType((String) updates.get("jobType"));
        }
        return repository.save(existing);
    }

    public void delete(Integer id) {
        VehicleJobCard existing = getById(id);
        repository.delete(existing);
    }

    public List<VehicleJobCard> getByVehicleId(Integer vehicleId) {
        List<VehicleJobCard> byVehicleId = repository.findByVehicleId(vehicleId);
        return byVehicleId;
    }

}
