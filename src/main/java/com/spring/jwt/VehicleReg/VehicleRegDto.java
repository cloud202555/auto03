package com.spring.jwt.VehicleReg;

import com.spring.jwt.entity.VehicleReg;
import lombok.Data;

@Data
public class VehicleRegDto {
    private Integer vehicleRegId;
    private Integer appointmentId;
    private String chasisNumber;
    private String customerAddress;
    private String customerAadharNo;
    private String customerGstin;
    private String superwiser;
    private String technician;
    private String worker;
    private String status;
    private Integer userId;
    private String date;

    public VehicleRegDto() {
    }

    public VehicleRegDto(VehicleReg vehicleReg) {
        this.vehicleRegId = vehicleReg.getVehicleRegId();
        this.appointmentId = vehicleReg.getAppointmentId();
        this.chasisNumber = vehicleReg.getChasisNumber();
        this.customerAddress = vehicleReg.getCustomerAddress();
        this.customerAadharNo = vehicleReg.getCustomerAadharNo();
        this.customerGstin = vehicleReg.getCustomerGstin();
        this.superwiser = vehicleReg.getSuperwiser();
        this.technician = vehicleReg.getTechnician();
        this.worker = vehicleReg.getWorker();
        this.status = vehicleReg.getStatus();
        this.userId = vehicleReg.getUserId();
        this.date=vehicleReg.getDate();
    }
}
