package com.java.assignment.loanstore.service;

import com.java.assignment.loanstore.repository.LoanStoreRepository;
import com.java.assignment.loanstore.service.impl.LoanStoreServiceImpl;
import com.java.assignment.loanstore.util.TestUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponents;

import java.net.URI;
import java.util.ArrayList;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


@RunWith(SpringRunner.class)
public class LoanStoreServiceImplTest extends TestUtil {

    @InjectMocks
    LoanStoreServiceImpl service;

    @Mock
    LoanStoreRepository repository;

    @Mock
    ServletUriComponentsBuilder servletUriComponentsBuilder;

    @Mock
    UriComponents uriComponents;

    @Mock
    ResponseEntity.BodyBuilder bodyBuilder;

    @Mock
    URI uri;

    @Test
    public void getAllLoansTest() {
        Mockito.when(repository.findAll()).thenReturn(loanStores());
        assertEquals(loanStores(), service.getAllLoans());
    }

    @Test
    public void addLoanExceptionHandlingTest() {
        assertTrue(service.addLoan(request2()).getBody().contains("The payment date can’t be greater than the due date"));
        assertEquals(HttpStatus.BAD_REQUEST, service.addLoan(request2()).getStatusCode());
    }

    @Test
    public void addLoanTest() {
        try (MockedStatic<ServletUriComponentsBuilder> mockUtils = Mockito.mockStatic(ServletUriComponentsBuilder.class)) {
            mockUtils.when(ServletUriComponentsBuilder::fromCurrentRequest).thenReturn(servletUriComponentsBuilder);
            when(servletUriComponentsBuilder.path(anyString())).thenReturn(servletUriComponentsBuilder);
            when(servletUriComponentsBuilder.buildAndExpand(anyString())).thenReturn(mock(UriComponents.class));
            when(uriComponents.toUri()).thenReturn(uri);
            try (MockedStatic<ResponseEntity> responseEntityMockedStatic = Mockito.mockStatic(ResponseEntity.class)){
                responseEntityMockedStatic.when(() -> ResponseEntity.created(null)).thenReturn(bodyBuilder);
                when(bodyBuilder.build()).thenReturn(new ResponseEntity<>(HttpStatus.CREATED));
                service.addLoan(request1());
            }
        }
    }

    @Test
    public void getLoanByLoanIdTest() {
        when(repository.findById("L1")).thenReturn(Optional.ofNullable(loanStores().get(0)));
        assertEquals(loanStores().get(0), service.getLoanByLoanId("L1"));
    }

    @Test
    public void getLoanByLoanIdExceptionTest() {
        when(repository.findById("L2")).thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "Loan details not found for customer id: L1"));
        assertThrows(ResponseStatusException.class, ()-> service.getLoanByLenderId("L2"));
    }

    @Test
    public void getLoanByCustomerIdTest() {
        when(repository.findByCustomerId("C1")).thenReturn(Optional.ofNullable(loanStores()));
        assertEquals(loanStores(), service.getLoanByCustomerId("C1"));
    }

    @Test
    public void getLoanByCustomerIdExceptionTest() {
        when(repository.findByCustomerId("C1")).thenReturn(Optional.of(new ArrayList<>()));
        assertThrows(ResponseStatusException.class, ()-> service.getLoanByCustomerId("C1"));
    }

    @Test
    public void getLoanByLenderIdTest() {
        when(repository.findByLenderId("L2")).thenReturn(Optional.ofNullable(loanStores()));
        assertEquals(loanStores(), service.getLoanByLenderId("L2"));
    }

    @Test
    public void getLoanByLenderIdExceptionTest() {
        when(repository.findByLenderId("L2")).thenReturn(Optional.of(new ArrayList<>()));
        assertThrows(ResponseStatusException.class, ()-> service.getLoanByLenderId("L2"));
    }

    @Test
    public void getAggregateLoanByLenderIdTest() {
        when(repository.findByLenderId("L1")).thenReturn(Optional.ofNullable(loanStores()));
        assertEquals(getAggregateAmount(), service.getAggregateLoanByLenderId("L1"));
    }

    @Test
    public void getAggregateLoanByCustomerIdTest() {
        when(repository.findByCustomerId("C1")).thenReturn(Optional.ofNullable(loanStores()));
        assertEquals(getAggregateAmount(), service.getAggregateLoanByCustomerId("C1"));
    }
}
