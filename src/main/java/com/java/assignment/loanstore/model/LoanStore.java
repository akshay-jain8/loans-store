package com.java.assignment.loanstore.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Entity
@Data
@Builder
@Table(name = "loans")
@AllArgsConstructor
@NoArgsConstructor
public class LoanStore {

    @Id
    @Column(name = "Loan ID")
    private String loanId;

    @Column(name = "Customer Id")
    private String customerId;

    @Column(name = "Lender Id")
    private String lenderId;

    @Column(name = "Amount")
    private int amount;

    @Column(name = "Remaining Amount")
    private int remainingAmount;

    @Column(name = "Payment Date")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE, fallbackPatterns = { "dd.MM.yyyy" })
    private LocalDate paymentDate;

    @Column(name = "Interest Per Day(%)")
    private int interest;

    @Column(name = "Due Date")
    private LocalDate dueDate;

    @Column(name = "Penalty/Day(%)")
    private double penalty;



}
