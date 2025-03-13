package com.spring.jwt.SparePartTransaction.Pdf.PDFEntity;


import jakarta.persistence.*;

@Entity
@Table(name = "counters")
public class Counter {

    @Id
    private String name;

    private long value;

    // Getters and setters
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public long getValue() {
        return value;
    }
    public void setValue(long value) {
        this.value = value;
    }
}