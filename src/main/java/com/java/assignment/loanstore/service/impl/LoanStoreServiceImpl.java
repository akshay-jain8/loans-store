package com.java.assignment.loanstore.service.impl;

import com.java.assignment.loanstore.exception.PaymentDateAfterDueDateException;
import com.java.assignment.loanstore.model.LoanStore;
import com.java.assignment.loanstore.repository.LoanStoreRepository;
import com.java.assignment.loanstore.request.LoansRequest;
import com.java.assignment.loanstore.response.AggregateResponse;
import com.java.assignment.loanstore.service.LoanStoreService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class LoanStoreServiceImpl implements LoanStoreService {
    public static final Logger LOGGER = LoggerFactory.getLogger(LoanStoreServiceImpl.class);
    private final LoanStoreRepository repository;

    @Override
    public List<LoanStore> getAllLoans() {
        List<LoanStore> loanStores = repository.findAll();
        for (LoanStore loanStore : loanStores) {
            checkDueDateCross(loanStore);
        }
        return loanStores;
    }

    @Override
    public ResponseEntity<String> addLoan(LoansRequest request) {
        try {
            if (request.getPaymentDate().isAfter(request.getDueDate())) {
                throw new PaymentDateAfterDueDateException("The payment date can’t be greater than the due date");
            }
        } catch (PaymentDateAfterDueDateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        LoanStore loanStore = LoanStore.builder()
                .loanId(request.getLoanId())
                .customerId(request.getCustomerId())
                .lenderId(request.getLenderId())
                .dueDate(request.getDueDate())
                .paymentDate(request.getPaymentDate())
                .amount(request.getAmount())
                .remainingAmount(request.getRemainingAmount())
                .interest(request.getInterest())
                .penalty(request.getPenalty())
                .build();

        repository.save(loanStore);

        URI url =
                ServletUriComponentsBuilder.fromCurrentRequest()
                        .path("/add")
                        .buildAndExpand(request.getLoanId())
                        .toUri();

        return ResponseEntity.created(url).body("Loan successfully added");
    }

    @Override
    public LoanStore getLoanByLoanId(String id) {
        LoanStore loanStore = repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Loan details not found for customer id: " + id));
        checkDueDateCross(loanStore);
        return loanStore;
    }

    @Override
    public List<LoanStore> getLoanByCustomerId(String customerId) {
        List<LoanStore> loansByCustomerId = repository.findByCustomerId(customerId).orElseGet(ArrayList::new);
        if (loansByCustomerId.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Loan details not found for customer id: " + customerId);
        }
        for (LoanStore loanStore : loansByCustomerId) {
            checkDueDateCross(loanStore);
        }
        return loansByCustomerId;
    }

    @Override
    public List<LoanStore> getLoanByLenderId(String lenderId) {
        List<LoanStore> loansByLenderId = repository.findByLenderId(lenderId).orElseGet(ArrayList::new);
        if (loansByLenderId.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Loan details not found for lender id: " + lenderId);
        }
        for (LoanStore loanStore : loansByLenderId) {
            checkDueDateCross(loanStore);
        }
        return loansByLenderId;
    }

    @Override
    public AggregateResponse getAggregateLoanByLenderId(String lenderId) {
        List<LoanStore> loansByLenderId = getLoanByLenderId(lenderId);
        List<Integer> aggregateAmountList = new ArrayList<>();
        for (LoanStore loanStore : loansByLenderId) {
            int aggregateAmount = getAggregateAmount(loanStore);
            aggregateAmountList.add(aggregateAmount);
        }

        return AggregateResponse.builder()
                .aggregateAmount(aggregateAmountList)
                .build();
    }

    @Override
    public AggregateResponse getAggregateLoanByCustomerId(String customerId) {
        List<LoanStore> loansByCustomerId = getLoanByCustomerId(customerId);
        List<Integer> aggregateAmountList = new ArrayList<>();
        for (LoanStore loanStore : loansByCustomerId) {
            int aggregateAmount = getAggregateAmount(loanStore);
            aggregateAmountList.add(aggregateAmount);
        }
        return AggregateResponse.builder()
                .aggregateAmount(aggregateAmountList)
                .build();
    }

    private int getAggregateAmount(LoanStore loanStore) {
        int aggregateAmount;
        int amount = loanStore.getAmount();
        int remainingAmount = loanStore.getRemainingAmount();
        int interestRate = loanStore.getInterest();
        double penaltyRate = loanStore.getPenalty();
        LocalDate paymentDate = loanStore.getPaymentDate();
        LocalDate dueDate = loanStore.getDueDate();
        int interestTime = (int) ChronoUnit.DAYS.between(paymentDate, dueDate);
        int interestAmount = (amount * interestRate/100) * interestTime;
        aggregateAmount = remainingAmount + interestAmount;

        //assuming penalty will be charge only after due date cross
        if (checkDueDateCross(loanStore)) {
            long penaltyTime = ChronoUnit.DAYS.between(dueDate, LocalDate.now());
            double penaltyAmount = (remainingAmount * penaltyRate/100) * penaltyTime;
            aggregateAmount = (int) (aggregateAmount + penaltyAmount);
        }
        return aggregateAmount;
    }

    private boolean checkDueDateCross(LoanStore loanStore) {
        if (loanStore.getDueDate().isBefore(LocalDate.now())) {
            LOGGER.info("Alert: Loan crosses the due date for customerId: " + loanStore.getCustomerId());
            return true;
        }
        return false;
    }
}
