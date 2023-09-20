package com.java.assignment.loanstore.controller;

import com.java.assignment.loanstore.exception.PaymentDateAfterDueDateException;
import com.java.assignment.loanstore.service.LoanStoreService;
import com.java.assignment.loanstore.util.TestUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;

@RunWith(SpringRunner.class)
public class ControllerTest extends TestUtil {

    @InjectMocks
    Controller controller;

    @Mock
    LoanStoreService loanStoreService;

    @Test
    public void addLoanTest() throws PaymentDateAfterDueDateException {
        Mockito.when(loanStoreService.addLoan(any())).thenReturn(ResponseEntity.ok("Loan Successfully added"));
        assertEquals("Loan Successfully added", controller.addLoan(any()).getBody());
        assertEquals(HttpStatus.OK, controller.addLoan(any()).getStatusCode());
    }

    @Test
    public void getAllLoansTest() {
        Mockito.when(loanStoreService.getAllLoans()).thenReturn(loanStores());
        assertEquals(loanStores(), controller.getAllLoans().getBody());
        assertEquals(HttpStatus.OK, controller.getAllLoans().getStatusCode());
    }

    @Test
    public void getLoansByLoanIdTest() {
        Mockito.when(loanStoreService.getLoanByLoanId("L1")).thenReturn(loanStores().get(0));
        assertEquals(loanStores().get(0), controller.getLoansByLoanId("L1").getBody());
        assertEquals(HttpStatus.OK, controller.getLoansByLoanId("L1").getStatusCode());
    }

    @Test
    public void getLoansByLenderIdTest() {
        Mockito.when(loanStoreService.getLoanByLenderId("LEN1")).thenReturn(loanStores());
        assertEquals(loanStores(), controller.getLoansByLenderId("LEN1").getBody());
        assertEquals(HttpStatus.OK, controller.getLoansByLenderId("LEN1").getStatusCode());
    }

    @Test
    public void getLoansByCustomerIdTest() {
        Mockito.when(loanStoreService.getLoanByCustomerId("C1")).thenReturn(loanStores());
        assertEquals(loanStores(), controller.getLoansByCustomerId("C1").getBody());
        assertEquals(HttpStatus.OK, controller.getLoansByCustomerId("C1").getStatusCode());
    }

    @Test
    public void getAggregateLoansByLenderIdTest() {
        Mockito.when(loanStoreService.getAggregateLoanByLenderId("LEN1")).thenReturn(getAggregateAmount());
        assertEquals(getAggregateAmount(), controller.getAggregateLoansByLenderId("LEN1").getBody());
        assertEquals(HttpStatus.OK, controller.getAggregateLoansByLenderId("LEN1").getStatusCode());
    }

    @Test
    public void getAggregateLoansByCustomerIdTest() {
        Mockito.when(loanStoreService.getAggregateLoanByCustomerId("C1")).thenReturn(getAggregateAmount());
        assertEquals(getAggregateAmount(), controller.getAggregateLoansByCustomerId("C1").getBody());
        assertEquals(HttpStatus.OK, controller.getAggregateLoansByCustomerId("C1").getStatusCode());
    }

}
