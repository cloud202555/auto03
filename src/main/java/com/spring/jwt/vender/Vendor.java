package com.spring.jwt.vender;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "vendor", uniqueConstraints = @UniqueConstraint(columnNames = "name"))
public class Vendor {
    @Id
    @Column(nullable = false, updatable = false)
    @SequenceGenerator(
            name = "primary_sequence",
            sequenceName = "primary_sequence",
            allocationSize = 1,
            initialValue = 10000
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "primary_sequence"
    )
    private Integer vendorId;

    @Column(unique = true, nullable = false)
    private String name;

    @Column
    private String GSTno;

    @Column
    private Long mobileNumber;

    @Column
    private String panNo;

    @Column
    private String address;
}
