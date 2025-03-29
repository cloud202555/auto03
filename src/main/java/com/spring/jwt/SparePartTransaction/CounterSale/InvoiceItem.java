package com.spring.jwt.SparePartTransaction.CounterSale;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InvoiceItem {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        private String spareName;

        private String spareNo;
        private int quantity;
        private double rate;
        private double discountPercent;
        private double discountAmt;
        private double taxableValue;

        private double cgstPercent;
        private double amount;
        private double sgstPercent;
        private double cgstAmt;
        private double sgstAmt;

        @ManyToOne
        @JoinColumn(name = "invoice_id", referencedColumnName = "id")
        @JsonBackReference
        private Invoice invoice;
}
