package com.spring.jwt.SparePart;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SparePart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "sparePart_seq")
    @SequenceGenerator(name = "sparePart_seq", sequenceName = "sparePart_seq", allocationSize = 1, initialValue = 10000)
    @Column(name = "sparePartId")
    private Integer sparePartId;

    @Column(name = "part_name", nullable = false)
    private String partName;

    @Column(name = "description", nullable = false)
    @NotBlank(message = "manufacturer name cannot be blank")
    @Size(max = 5000, message = "Description cannot exceed 5000 characters")
    private String description;

    @Column(name = "manufacturer", nullable = false)
    @NotBlank(message = "manufacturer name cannot be blank")
    private String manufacturer;

    @Column(name = "price", nullable = false)
    private Long price;

    @Column(name = "update_At", nullable = false)
    private LocalDate updateAt;

    @ElementCollection
    @CollectionTable(name = "spare_part_photos", joinColumns = @JoinColumn(name = "spare_part_id"))
    @Column(name = "photo", columnDefinition = "LONGBLOB")
    private List<byte[]> photo;

    @Column(name = "part_number", nullable = false)
    private String partNumber;


}
