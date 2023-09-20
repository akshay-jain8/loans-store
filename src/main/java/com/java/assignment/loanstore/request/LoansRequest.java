package com.java.assignment.loanstore.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoansRequest {
    private String loanId;

    private String customerId;
    private String lenderId;
    private int amount;
    private int remainingAmount;
    private LocalDate paymentDate;
    private int interest;
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDate dueDate;
    private double penalty;
}
