package com.spring.jwt.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(
        name = "vendor_part",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"vendor", "part_number"})
        }
)
public class VendorPart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "vendorPart")
    @Column(name = "vendorPartId")
    private Integer vendorPartId;

    private Integer sparePartId;

    @Column(name = "part_name", nullable = false)
    private String partName;

    @Column(name = "description", nullable = false)
    @Size(max = 5000, message = "Description cannot exceed 5000 characters")
    private String description;

    @Column(name = "manufacturer", nullable = false)
    private String manufacturer;

    @Column(name = "price", nullable = false)
    private Long price;

    @Column(name = "update_At", nullable = false)
    private LocalDate updateAt;

    @Column(name = "part_number", nullable = false)
    private String partNumber;

    @Column
    private Integer cGST;

    @Column
    private Integer sGST;

    @Column
    private Integer totalGST;

    @Column
    private Integer buyingPrice;

    @Column(nullable = false)
    private String vendor;
}
