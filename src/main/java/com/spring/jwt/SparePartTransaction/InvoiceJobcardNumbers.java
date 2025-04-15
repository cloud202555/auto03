package com.spring.jwt.SparePartTransaction;

public class InvoiceJobcardNumbers {
        private int invoiceNumber;
        private int jobCardNumber;

        public InvoiceJobcardNumbers(int invoiceNumber, int jobCardNumber) {
            this.invoiceNumber = invoiceNumber;
            this.jobCardNumber = jobCardNumber;
        }

        // Getters
        public int getInvoiceNumber() {
            return invoiceNumber;
        }

        public int getJobCardNumber() {
            return jobCardNumber;
        }
}
