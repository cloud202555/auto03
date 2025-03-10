package com.spring.jwt.VehicleReg;

import com.spring.jwt.entity.VehicleReg;
import lombok.Data;

@Data
public class VehicleRegDto {
    private Integer vehicleRegId;
    private Integer appointmentId;

    private String vehicleNumber;
    private String vehicleBrand;
    private String vehicleModelName;
    private String vehicleVariant;
    private String engineNumber;
    private String chasisNumber;
    private String numberPlateColour;

    private Integer customerId;
    private String customerName;
    private String customerAddress;
    private String customerMobileNumber;
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
        this.vehicleNumber = vehicleReg.getVehicleNumber();
        this.vehicleBrand = vehicleReg.getVehicleBrand();
        this.vehicleModelName = vehicleReg.getVehicleModelName();
        this.vehicleVariant = vehicleReg.getVehicleVariant();
        this.engineNumber = vehicleReg.getEngineNumber();
        this.chasisNumber = vehicleReg.getChasisNumber();
        this.numberPlateColour = vehicleReg.getNumberPlateColour();
        this.customerId = vehicleReg.getCustomerId();
        this.customerName = vehicleReg.getCustomerName();
        this.customerAddress = vehicleReg.getCustomerAddress();
        this.customerMobileNumber = vehicleReg.getCustomerMobileNumber();
        this.customerAadharNo = vehicleReg.getCustomerAadharNo();
        this.customerGstin = vehicleReg.getCustomerGstin();
        this.superwiser = vehicleReg.getSuperwiser();
        this.technician = vehicleReg.getTechnician();
        this.worker = vehicleReg.getWorker();
        this.status = vehicleReg.getStatus();
        this.userId = vehicleReg.getUserId();
        this.date = vehicleReg.getDate();
    }
}
