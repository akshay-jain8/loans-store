package com.java.assignment.loanstore.controller;

import com.java.assignment.loanstore.exception.PaymentDateAfterDueDateException;
import com.java.assignment.loanstore.model.LoanStore;
import com.java.assignment.loanstore.request.LoansRequest;
import com.java.assignment.loanstore.response.AggregateResponse;
import com.java.assignment.loanstore.service.LoanStoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/loans")
public class Controller {

    @Autowired
    private LoanStoreService loanStoreService;

    @PostMapping("/add")
    public ResponseEntity<String> addLoan(@RequestBody LoansRequest request) {
        return loanStoreService.addLoan(request);
    }
    @GetMapping
    public ResponseEntity<List<LoanStore>> getAllLoans() {
        return ResponseEntity.ok(loanStoreService.getAllLoans());
    }

    @GetMapping("/loan/{loanId}")
    public ResponseEntity<LoanStore> getLoansByLoanId(@PathVariable String loanId) {
        return ResponseEntity.ok(loanStoreService.getLoanByLoanId(loanId));
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<LoanStore>> getLoansByCustomerId(@PathVariable String customerId) {
        return ResponseEntity.ok(loanStoreService.getLoanByCustomerId(customerId));
    }

    @GetMapping("/lender/{lenderId}")
    public ResponseEntity<List<LoanStore>> getLoansByLenderId(@PathVariable String lenderId) {
        return ResponseEntity.ok(loanStoreService.getLoanByLenderId(lenderId));
    }

    @GetMapping("/aggregate/lender/{lenderId}")
    public ResponseEntity<AggregateResponse> getAggregateLoansByLenderId(@PathVariable String lenderId) {
        return ResponseEntity.ok(loanStoreService.getAggregateLoanByLenderId(lenderId));
    }

    @GetMapping("/aggregate/customer/{customerId}")
    public ResponseEntity<AggregateResponse> getAggregateLoansByCustomerId(@PathVariable String customerId) {
        return ResponseEntity.ok(loanStoreService.getAggregateLoanByCustomerId(customerId));
    }
}
