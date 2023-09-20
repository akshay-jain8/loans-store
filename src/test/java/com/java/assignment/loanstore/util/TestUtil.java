package com.java.assignment.loanstore.util;

import com.java.assignment.loanstore.model.LoanStore;
import com.java.assignment.loanstore.request.LoansRequest;
import com.java.assignment.loanstore.response.AggregateResponse;

import java.time.LocalDate;
import java.util.List;

public class TestUtil {

    protected List<LoanStore> loanStores() {
        return List.of(LoanStore.builder()
                .loanId("L1")
                .customerId("C1")
                .lenderId("LEN!")
                .amount(10000)
                .remainingAmount(2000)
                .paymentDate(LocalDate.of(2023, 6, 5))
                .dueDate(LocalDate.of(2023, 7, 5))
                .penalty(0.01)
                .interest(1)
                .build());
    }

    protected LoansRequest request1() {
        return LoansRequest.builder()
                .loanId("L1")
                .customerId("C1")
                .lenderId("LEN!")
                .amount(10000)
                .remainingAmount(2000)
                .paymentDate(LocalDate.of(2023, 5, 10))
                .dueDate(LocalDate.of(2023, 6, 5))
                .penalty(0.01)
                .interest(1)
                .build();
    }

    protected LoansRequest request2() {
        return LoansRequest.builder()
                .loanId("L1")
                .customerId("C1")
                .lenderId("LEN!")
                .amount(10000)
                .remainingAmount(2000)
                .paymentDate(LocalDate.of(2023, 7, 10))
                .dueDate(LocalDate.of(2023, 6, 5))
                .penalty(0.01)
                .interest(1)
                .build();
    }

    protected AggregateResponse getAggregateAmount() {
        return AggregateResponse.builder().aggregateAmount(List.of(5015)).build();
    }
}
