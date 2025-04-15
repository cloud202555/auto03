package com.spring.jwt.VehicleReg.VehicleJobCard;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/vehicleJobCards")
public class VehicleJobCardController {

    private final VehicleJobCardService service;

    public VehicleJobCardController(VehicleJobCardService service) {
        this.service = service;
    }

    @PostMapping("/add")
    public ResponseEntity<VehicleJobCard> create(@RequestBody VehicleJobCard vehicleJobCard) {
        VehicleJobCard created = service.create(vehicleJobCard);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @GetMapping("/getById/{id}")
    public ResponseEntity<VehicleJobCard> getById(@PathVariable Integer id) {
        VehicleJobCard found = service.getById(id);
        return ResponseEntity.ok(found);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<VehicleJobCard>> getAll() {
        List<VehicleJobCard> list = service.getAll();
        return ResponseEntity.ok(list);
    }

    @PatchMapping("update/{id}")
    public ResponseEntity<VehicleJobCard> update(@PathVariable Integer id, @RequestBody Map<String, Object> updates) {
        VehicleJobCard updated = service.update(id, updates);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/getByVehicleId")
    public ResponseEntity<?>getbyVehicleId(@RequestParam Integer vehicleId){
        List<VehicleJobCard>  byVehicleId = service.getByVehicleId(vehicleId);
        return ResponseEntity.ok(byVehicleId);
    }
}
