package com.spring.jwt.SavePart;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;



@Setter
@Getter
@Entity
@AllArgsConstructor
@Table(name = "SavePart", uniqueConstraints = {@UniqueConstraint(columnNames = {"sparePartId", "userId"})})
public class SavePart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "saveCarId", nullable = false)
    private Integer savePartId;

    private  Integer sparePartId;

    private Integer userId;


    public SavePart(SavePartDto saveCarDto) {
        this.savePartId = saveCarDto.getSavePartId();
        this.sparePartId = saveCarDto.getSavePartId();
        this.userId = saveCarDto.getUserId();
    }

    public SavePart() {
    }

    public void setUser(int user) {
    }

    public void SparePart(SavePart savePart) {
    }



}
