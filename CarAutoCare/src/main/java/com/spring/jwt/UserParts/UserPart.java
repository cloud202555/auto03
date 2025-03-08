package com.spring.jwt.UserParts;
import com.spring.jwt.SparePart.SparePart;
import jakarta.persistence.*;
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
public class UserPart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userPartId;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Column(name = "lastUpdate", nullable = false)
    private String lastUpdate;

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

    @ElementCollection
    @Column(name = "photo", columnDefinition = "LONGBLOB")
    private List<byte[]> photo;

    @Column(name = "part_number", nullable = false)
    private Long partNumber;


    @ManyToOne
    @JoinColumn(name = "sparePartId", referencedColumnName = "sparePartId")
    private SparePart sparePart;


}
