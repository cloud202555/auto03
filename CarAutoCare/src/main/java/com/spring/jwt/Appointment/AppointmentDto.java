package com.spring.jwt.Appointment;
import com.spring.jwt.entity.Appointment;
import lombok.Data;

@Data
public class AppointmentDto {
    private Integer appointmentId;
    private String appointmentDate;
    private String customerName;
    private String mobileNo;
    private String vehicleNo;
    private String vehicleMaker;
    private String vehicleModel;
    private String manufacturedYear;
    private String kilometerDriven;
    private String fuelType;
    private String workType;
    private String vehicleProblem;
    private String pickUpAndDropService;
    private Integer userId;
    private String status;

    public AppointmentDto(Appointment appointment) {
        this.appointmentId = appointment.getAppointmentId();
        this.appointmentDate = appointment.getAppointmentDate();
        this.customerName = appointment.getCustomerName();
        this.mobileNo = appointment.getMobileNo();
        this.vehicleNo = appointment.getVehicleNo();
        this.vehicleMaker = appointment.getVehicleMaker();
        this.vehicleModel = appointment.getVehicleModel();
        this.manufacturedYear = appointment.getManufacturedYear();
        this.kilometerDriven = appointment.getKilometerDriven();
        this.fuelType = appointment.getFuelType();
        this.workType = appointment.getWorkType();
        this.vehicleProblem = appointment.getVehicleProblem();
        this.pickUpAndDropService = appointment.getPickUpAndDropService();
        this.userId=appointment.getUserId();
        this.status=appointment.getStatus();

    }

    public AppointmentDto() {
    }
}
