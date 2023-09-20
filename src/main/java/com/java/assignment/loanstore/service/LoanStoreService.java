package com.java.assignment.loanstore.service;

import com.java.assignment.loanstore.model.LoanStore;
import com.java.assignment.loanstore.request.LoansRequest;
import com.java.assignment.loanstore.response.AggregateResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface LoanStoreService {
    List<LoanStore> getAllLoans();
    ResponseEntity<String> addLoan(LoansRequest request);

    LoanStore getLoanByLoanId(String id);
    List<LoanStore> getLoanByCustomerId(String customerId);
    List<LoanStore> getLoanByLenderId(String lenderId);

    AggregateResponse getAggregateLoanByLenderId(String lenderId);
    AggregateResponse getAggregateLoanByCustomerId(String customerId);
}
